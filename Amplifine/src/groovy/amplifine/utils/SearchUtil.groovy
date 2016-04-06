package amplifine.utils

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
        def lastPage = [num: pages, offset: collectionSize - LIMIT]

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

        if (!paginateList.find { it.num == pages }) {
            paginateList << lastPage
        }

        return paginateList
    }
}
