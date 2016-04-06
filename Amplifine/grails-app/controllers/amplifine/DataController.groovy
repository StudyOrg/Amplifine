package amplifine

import amplifine.utils.SearchUtil
import com.mongodb.BasicDBObject
import com.mongodb.DBObject
import mongodb.MongoDBUtil
import org.bson.Document
import org.bson.types.ObjectId

class DataController {

    def index() {
        render(view: "index")
    }

    def textSearch() {
        def offset = (params.offset ? Integer.parseInt(params.offset) : 0)
        def pattern = params.search

        def fullTextSearch = SearchUtil.fullTextSearch(params.search, offset)
        def rxTextSearch = null

        if (!fullTextSearch || (fullTextSearch.list && fullTextSearch.list.size() < 1)) {
            rxTextSearch = SearchUtil.regexTextSearch(params.search, offset)
        }

        def searchResult = (fullTextSearch && fullTextSearch.list.size() > 0 ? fullTextSearch.list :
                (rxTextSearch && rxTextSearch.list.size() > 0 ? rxTextSearch.list : []))

        pattern = (fullTextSearch && fullTextSearch.pattern ? fullTextSearch.pattern :
                (rxTextSearch && rxTextSearch.pattern ? rxTextSearch.pattern : pattern))

        def size = (fullTextSearch && fullTextSearch.size ? fullTextSearch.size :
                (rxTextSearch && rxTextSearch.size ? rxTextSearch.size : 0))


        def groupByShops = searchResult.groupBy { it.shop }

        def newShopsMap = [:]

        for (def key in groupByShops.keySet()) {
            def query = new Document("_id", new ObjectId(key.toString()))
            def shop = MongoDBUtil.DB.getCollection("shops").findOne(query)

            newShopsMap.put(shop, groupByShops[key])
        }

        render(view: "index", model: [result: newShopsMap, search: pattern, size: size, offset: offset])
    }
}
