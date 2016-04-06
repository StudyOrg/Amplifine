package amplifine

import amplifine.utils.SearchUtil
import mongodb.MongoDBUtil
import org.bson.Document
import org.bson.types.ObjectId

class DataController {

    def index() {
        render(view: "index")
    }

    def textSearch() {
        def salesCollection = MongoDBUtil.DB.getCollection("sales")

        def searchRx = '^' + SearchUtil.replaceCyrillicChars(params.search)
        def searchRxCyrillic = '^' + SearchUtil.replaceLatinChars(params.search)

        def offset = (params.offset ? Integer.parseInt(params.offset) : 0)

        def searchMap = ["goods.description": [$regex: searchRx, $options: 'i']]
        def searchMapCyrillic = ["goods.description": [$regex: searchRxCyrillic, $options: 'i']]

        def result = []
        def resultCyrillic = []

        result += salesCollection.find(searchMap).skip(offset).limit(SearchUtil.LIMIT).asList()

        if (searchRx != searchRxCyrillic) {
            resultCyrillic += salesCollection.find(searchMapCyrillic).skip(offset).limit(SearchUtil.LIMIT).asList()
        }

        def searchResult = (result.size() > resultCyrillic.size() ? result : resultCyrillic)
        def searchString = (result.size() > resultCyrillic.size() ? searchRx : searchRxCyrillic).replace("^", "")

        def size = salesCollection.find((result.size() > resultCyrillic.size() ? searchMap : searchMapCyrillic)).size()

        def groupByShops = searchResult.groupBy { it.shop }

        def newShopsMap = [:]

        for (def key in groupByShops.keySet()) {
            def query = new Document("_id", new ObjectId(key.toString()))
            def shop = MongoDBUtil.DB.getCollection("shops").findOne(query)

            newShopsMap.put(shop, groupByShops[key])
        }

        render(view: "index", model: [result: newShopsMap, search: searchString, size: size, offset: offset])
    }
}
