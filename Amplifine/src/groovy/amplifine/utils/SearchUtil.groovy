package amplifine.utils

import com.mongodb.BasicDBObject
import com.mongodb.DBObject
import mongodb.MongoDBUtil
import org.apache.commons.lang.StringUtils
import org.bson.Document
import org.bson.types.ObjectId

class SearchUtil {

    public static final Integer LIMIT = 100

    private static final String CYRILLIC_CHARS = "йцукенгшщзхъфывапролджэячсмитьбюёЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮЁ"
    private static final String LATIN_CHARS = "qwertyuiop[]asdfghjkl;'zxcvbnm,.`QWERTYUIOP{}ASDFGHJKL:\"ZXCVBNM<>~"

    private static List goodsList = []

    public static String replaceCyrillicChars(String str) {
        return StringUtils.replaceChars(str, CYRILLIC_CHARS, LATIN_CHARS)
    }

    public static String replaceLatinChars(String str) {
        return StringUtils.replaceChars(str, LATIN_CHARS, CYRILLIC_CHARS)
    }

    public static Object fullTextSearch(String pattern, Integer offset) {
        def salesCollection = MongoDBUtil.DB.getCollection("sales")

        def search = replaceCyrillicChars(pattern)
        def searchCyrillic = replaceLatinChars(pattern)

        DBObject findCommand = new BasicDBObject("\$text",
                new BasicDBObject("\$search", search))
        DBObject findCommandCyrillic = new BasicDBObject("\$text",
                new BasicDBObject("\$search", searchCyrillic))

        DBObject projectCommand = new BasicDBObject("score", new BasicDBObject("\$meta", "textScore"))

        DBObject sortCommand = new BasicDBObject("score", new BasicDBObject("\$meta", "textScore"))

        def result = salesCollection.find(findCommand, projectCommand).sort(sortCommand).skip(offset).limit(LIMIT).asList()
        def resultCyrillic = salesCollection.find(findCommandCyrillic, projectCommand).sort(sortCommand).skip(offset).limit(LIMIT).asList()

        def returnList = []
        def returnPattern = ""

        if (result || resultCyrillic) {
            returnList = (result.size() > resultCyrillic.size() ? result : resultCyrillic)
            returnPattern = (result.size() > resultCyrillic.size() ? search : searchCyrillic)
        }

        def totalSize = salesCollection.find((result.size() > resultCyrillic.size() ? findCommand : findCommandCyrillic)).size()

        return [list: returnList, pattern: returnPattern, size: totalSize]
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
