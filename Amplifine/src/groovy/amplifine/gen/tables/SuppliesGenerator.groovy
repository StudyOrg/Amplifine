package amplifine.gen.tables

import amplifine.gen.MongoGenerator
import amplifine.gen.dictionaries.GoodsTypesDictionary
import amplifine.gen.dictionaries.SupplierDictionary
import com.mongodb.client.MongoCursor
import com.mongodb.client.MongoDatabase
import mongodb.MongoDBUtil
import org.bson.Document

class SuppliesGenerator implements MongoGenerator {

    SuppliesGenerator() {
        Random rn = new Random(System.nanoTime());

        // Объект базы данных
        MongoDatabase db = MongoDBUtil.getDB()
        boolean status
        def record

        MongoCursor<Document> goodsRecords = db.getCollection("goods").find().iterator()
        def workersRecords = MongoDBUtil.getAllRecords("workers")
        def shopsRecords = MongoDBUtil.getAllRecords("shops")

        println "Генерация/вставка поставок..."

        while (goodsRecords.hasNext()) {
            def supplier = SupplierDictionary.generateRandomSupplier()

            def goods = []
            def randomGood = goodsRecords.next()

            for (int j = 1; j <= rn.nextInt(10) + 1; j++) {
                goods.push([
                        goodId     : randomGood._id,
                        description: randomGood.manufacturer + " " + randomGood.model,
                        qty        : rn.nextInt(5) + 1,
                        retailPrice: GoodsTypesDictionary.price
                ])
            }

            Date date = new Date()

            record = [:]

            record << [supplier: supplier]
            record << [goods: goods]
            record << [date: date.toString()]
            record << [shop: shopsRecords[rn.nextInt(shopsRecords.size())]._id['$oid']]
            record << [worker: workersRecords[rn.nextInt(workersRecords.size())]._id['$oid']]

            status = (db.getCollection("supplies") << record)
            if (!status) {
                break
            }

//            for (int i = 0; i < 3; i++) {
//                if (goodsRecords.hasNext())
//                    goodsRecords.next()
//                else
//                    break
//            }
        }
        println "Поставки сгенерированы/вставлены"
    }

    Boolean insertAll() {
//        def db = MongoDBUtil.getDB()
//
//        def record
//
//        println "Вставка поставок..."
//
//        Boolean status = true
//        for (result in data) {
//            record = [:]
//
//            record << [supplier: result.supplier]
//            record << [goods: result.goods]
//            record << [date: result.date]
//            record << [shop: result.shop._id['$oid']]
//            record << [worker: result.worker._id['$oid']]
//
//            status = (db.getCollection("supplies") << record)
//            if (!status) {
//                break
//            }
//        }
//
//        return status
    }

}
