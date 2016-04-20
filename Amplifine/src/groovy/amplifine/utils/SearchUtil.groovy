package amplifine.utils

import com.mongodb.BasicDBObject
import com.mongodb.DBObject
import com.mongodb.client.MongoCollection
import mongodb.MongoDBUtil
import org.apache.commons.lang.StringUtils
import org.bson.Document
import org.bson.types.ObjectId

class SearchUtil {

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
        str.each { char ch ->
            if (ch in ('а'..'я')) {
                return replaceCyrillicChars(str)
            }
        }

        return replaceLatinChars(str)
    }

    /**
     * Функция полнотекстового поиска с изменением раскладки.
     * Правило поиска - если было найдено менее 1% от общего размера базы, пробуем искать в другой раскладке
     * При этом если результирующий для другой раскладки больше, используем его в качестве результата
     * @param collection
     * @param pattern
     * @param limit
     * @param offset
     * @return Результат поиск
     */
    public static Object fullTextSearch(String collection, String pattern, int limit, int offset) {
        //        SearchResult result = new SearchResult()

        Closure request = { String text ->
            return ['$text': ['$search': text]]
        }

        Map requestMeta = ['$score': ['$meta': 'textScore']]

        MongoCollection salesCollection = MongoDBUtil.DB.getCollection(collection)
        int collectionSize = salesCollection.count()

        // Делаем запрос с исходной раскладкой
        def search = pattern
        int requestResultSize = salesCollection.find(request(search), requestMeta).count()

        def searchCyrillic = replaceLatinChars(pattern)

        DBObject findCommand = new BasicDBObject("\$text", new BasicDBObject("\$search", search))
        DBObject findCommandCyrillic = new BasicDBObject("\$text", new BasicDBObject("\$search", searchCyrillic))

        DBObject projectCommand = new BasicDBObject("score", new BasicDBObject("\$meta", "textScore"))


        def resultCyrillic = salesCollection.find(findCommandCyrillic, projectCommand).skip(offset).limit(limit).asList()

        def returnList = []
        def returnPattern = ""

//        if (result || resultCyrillic) {
//            returnList = (result.size() > resultCyrillic.size() ? result : resultCyrillic)
//            returnPattern = (result.size() > resultCyrillic.size() ? search : searchCyrillic)
//        }

        //def totalSize = salesCollection.find((result.size() > resultCyrillic.size() ? findCommand : findCommandCyrillic)).size()

        return [list: returnList, pattern: returnPattern, size: 5]
    }

    public static Object regexTextSearch(String pattern, Integer offset) {
        def salesCollection = MongoDBUtil.DB.getCollection("sales")

        def search = replaceCyrillicChars(pattern)
        def searchCyrillic = replaceLatinChars(pattern)

        def searchMap = ["goods.description": [$regex: '^' + search, $options: 'i']]
        def searchMapCyrillic = ["goods.description": [$regex: '^' + searchCyrillic, $options: 'i']]

        def result = salesCollection.find(searchMap).skip(offset).limit(LIMIT).asList()
        def resultCyrillic = salesCollection.find(searchMapCyrillic).skip(offset).limit(LIMIT).asList()

        def returnList = []
        def returnPattern = ""

        if (result || resultCyrillic) {
            returnList = (result.size() > resultCyrillic.size() ? result : resultCyrillic)
            returnPattern = (result.size() > resultCyrillic.size() ? search : searchCyrillic)
        }

        def totalSize = salesCollection.find((result.size() > resultCyrillic.size() ? searchMap : searchMapCyrillic)).size()

        return [list: returnList, pattern: returnPattern, size: totalSize]
    }

    public static Map getGoodDescription(ObjectId id) {
        def goodsCollection = MongoDBUtil.DB.getCollection("goods")
        Map good = [:]

        if (goodsList && goodsList.size() > 0) {
            good = goodsList.find { it._id == id }
        }

        if (!good) {
            def query = new Document("_id", new ObjectId(id.toString()))
            good = goodsCollection.findOne(query)

            if (good) {
                goodsList << good
            }
        }

        return good
    }

    public static List<Map> getPaginateMap(Integer offset, Long collectionSize) {
        def pages = (collectionSize / LIMIT).toInteger()
        def currentPage = (offset / LIMIT).toInteger() + 1

        def paginateList = []

        def firstPage = [num: 1, offset: 0]

        paginateList << firstPage

        if (currentPage - 2 <= 1) {
            for (def i = currentPage; i > 1; i--) {
                def newPage = [num: i, offset: ((i - 1) * LIMIT)]
                paginateList << newPage
            }
        } else {
            for (def i = currentPage - 2; i <= currentPage; i++) {
                def newPage = [num: i, offset: ((i - 1) * LIMIT)]
                paginateList << newPage
            }
        }

        for (def i = currentPage + 1; i < pages && i <= currentPage + 2; i++) {
            def newPage = [num: i, offset: ((i - 1) * LIMIT)]
            paginateList << newPage
        }

        return paginateList
    }
}
