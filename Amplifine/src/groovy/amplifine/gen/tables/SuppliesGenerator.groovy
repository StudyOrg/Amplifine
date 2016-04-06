package amplifine.gen.tables

import amplifine.gen.MongoGenerator
import amplifine.gen.dictionaries.GoodsTypesDictionary
import amplifine.gen.dictionaries.SupplierDictionary
import mongodb.MongoDBUtil

class SuppliesGenerator implements MongoGenerator {
    def data = []

    SuppliesGenerator() {
        Random rn = new Random(System.nanoTime());

        def goodsRecords = MongoDBUtil.getAllRecords("goods")
        def workersRecords = MongoDBUtil.getAllRecords("workers")
        def shopsRecords = MongoDBUtil.getAllRecords("shops")

        println "Генерация поставок..."

        for (def i = 0; i < goodsRecords.size() / 5; i += 1.0) {
            def supplier = SupplierDictionary.generateRandomSupplier()

            def goods = []
            for (int j = 1; j <= rn.nextInt(10) + 1; j++) {
                def randomGood = goodsRecords[rn.nextInt(goodsRecords.size())]
                goods.push([
                        goodId     : randomGood._id['$oid'],
                        description: randomGood.manufacturer + " " + randomGood.model,
                        qty        : rn.nextInt(5) + 1,
                        retailPrice: GoodsTypesDictionary.price
                ])
            }

            Date date = new Date()

            data << [supplier: supplier,
                     goods   : goods,
                     date    : date.toString(),
                     shop    : shopsRecords[rn.nextInt(shopsRecords.size())],
                     worker  : workersRecords[rn.nextInt(workersRecords.size())]]
        }
    }

    Boolean insertAll() {
        def db = MongoDBUtil.getDB()

        def record

        println "Вставка поставок..."

        Boolean status = true
        for (result in data) {
            record = [:]

            record << [supplier: result.supplier]
            record << [goods: result.goods]
            record << [date: result.date]
            record << [shop: result.shop._id['$oid']]
            record << [worker: result.worker._id['$oid']]

            status = (db.getCollection("supplies") << record)
            if (!status) {
                break
            }
        }

        return status
    }

}
