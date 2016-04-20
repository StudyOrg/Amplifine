package amplifine.utils

import com.mongodb.client.FindIterable
import com.mongodb.client.MongoCollection
import mongodb.MongoDBUtil
import org.apache.commons.lang.StringUtils

class SearchUtil {

    public static enum SearchAlgo {
        LEVENSHTEIN, JARO_WINKLER, LCS
    }

    private static final String CYRILLIC_CHARS = "йцукенгшщзхъфывапролджэячсмитьбюёЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮЁ"
    private static final String LATIN_CHARS = "qwertyuiop[]asdfghjkl;'zxcvbnm,.`QWERTYUIOP{}ASDFGHJKL:\"ZXCVBNM<>~"

    public static final int LIMIT = 10

    private static List goodsList = []

    public static String replaceCyrillicChars(String str) {
        return StringUtils.replaceChars(str, CYRILLIC_CHARS, LATIN_CHARS)
    }

    public static String replaceLatinChars(String str) {
        return StringUtils.replaceChars(str, LATIN_CHARS, CYRILLIC_CHARS)
    }

    public static String replaceAuto(String str) {
        for (ch in str) {
            if (ch in ('а'..'я')) {
                return replaceCyrillicChars(str)
            }
        }

        return replaceLatinChars(str)
    }

    public static SearchResult search(String collectionName, String query, int limit, int page, SearchAlgo algo = SearchAlgo.LEVENSHTEIN) {
        Closure<Double> comparer = { String s1, String s2 ->
            switch (algo) {
                case SearchAlgo.JARO_WINKLER:
                    return FuzzySearch.jaroWinklerSimilarity(s1, s2)
                case SearchAlgo.LEVENSHTEIN:
                    return FuzzySearch.levensteinSimilarity(s1, s2)
                case SearchAlgo.LCS:
                    return FuzzySearch.lcsSimilarity(s1, s2)
            }
        }

        MongoCollection collection = MongoDBUtil.DB.getCollection(collectionName)

        FindIterable iterator = collection.find()

        double SIMILARITY_THRESHOLD = 0.8

        int skip = limit * page
        int balance = 1

        List<Object> resultList = new ArrayList<>()

        // Перебираем все записи в базе для заданной коллекции
        for (it in iterator) {

            // Выходим, если уже хватит
            if (limit == 0) break
            // Весовое значение для оценки релевантности
            double weight = 0

            // Перебираем все поля объекта коллекции
            for (Map.Entry field in it) {
                String fieldString = field.value.toString()
                // Для всех слов в строке
                for (fieldWord in fieldString.split("\\s")) {
                    // Найти все комбинации сравнений со словами запроса
                    for (word in query.split("\\s")) {
                        // Ищем расстояние через левенштейна для исходной раскладки...
                        double sim = comparer(fieldWord.toLowerCase(), word.toLowerCase())
                        // ...и для альтернативной
                        double simAlt = comparer(fieldWord.toLowerCase(), replaceAuto(word).toLowerCase())
                        // Выбрав наикротчайший вариант, проверяем по порогу
                        double max
                        if (sim >= simAlt) {
                            max = sim
                            ++balance
                        } else {
                            max = simAlt
                            --balance
                        }
                        if (max > SIMILARITY_THRESHOLD) {
                            weight += max
                        }
                    }
                }
            }

            if (weight > 0) {
                if (skip > 0) {
                    --skip
                } else {
                    resultList << [entry: it, weight: weight]
                    --limit
                }
            }
        }

        SearchResult result = new SearchResult()
        result.returnList = resultList.sort { a, b -> (b.weight <=> a.weight) }.collect { it.entry }
        result.realPattern = balance > 0 ? query : replaceAuto(query)

        return result
    }
}
