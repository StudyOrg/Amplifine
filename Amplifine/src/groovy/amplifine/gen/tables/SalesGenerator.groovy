package amplifine.gen.tables

import amplifine.gen.MongoGenerator
import amplifine.gen.dictionaries.GoodsTypesDictionary
import amplifine.gen.dictionaries.UsersDictionary
import mongodb.MongoDBUtil

class SalesGenerator implements MongoGenerator {
    def data = []

    SalesGenerator() {
        Random rn = new Random(System.nanoTime());

        def goodsRecords = MongoDBUtil.getAllRecords("goods")
        def workersRecords = MongoDBUtil.getAllRecords("workers")
        def shopsRecords = MongoDBUtil.getAllRecords("shops")

        for (def i = 0; i < goodsRecords.size() / workersRecords.size(); i += 1.0) {
            def customer = UsersDictionary.generateRandomUser()

            def goods = []
            for (int j = 1; j < rn.nextInt(10); j++) {
                def randomGood = goodsRecords[rn.nextInt(goodsRecords.size())]
                goods.push([
                        goodId     : randomGood._id['$oid'],
                        description: randomGood.manufacturer + " " + randomGood.model,
                        qty        : rn.nextInt(5) + 1,
                        retailPrice: GoodsTypesDictionary.price
                ])
            }

            Date date = new Date()

            data << [customer: customer,
                     goods   : goods,
                     date    : date.toString(),
                     shop    : shopsRecords[rn.nextInt(shopsRecords.size())],
                     worker  : workersRecords[rn.nextInt(workersRecords.size())]]
        }
    }

    Boolean insertAll() {
        def db = MongoDBUtil.getDB()

        def record

        Boolean status = true
        for (result in data) {
            record = [:]

            record << [customer: result.customer]
            record << [goods: result.goods]
            record << [date: result.date]
            record << [shop: result.shop._id['$oid']]
            record << [worker: result.worker._id['$oid']]

            status = (db.getCollection("sales") << record)
            if (!status) {
                break
            }
        }

        return status
    }

}
