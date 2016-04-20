package amplifine.gen.tables

import amplifine.gen.MongoGenerator
import amplifine.gen.dictionaries.GoodsTypesDictionary
import amplifine.gen.dictionaries.UsersDictionary
import com.mongodb.client.MongoCursor
import com.mongodb.client.MongoDatabase
import mongodb.MongoDBUtil
import org.bson.Document

class SalesGenerator implements MongoGenerator {

    SalesGenerator() {
        Random rn = new Random(System.nanoTime());

        // Объект базы данных
        MongoDatabase db = MongoDBUtil.getDB()
        boolean status
        def record

        MongoCursor<Document> goodsRecords = db.getCollection("goods").find().iterator()
        def workersRecords = MongoDBUtil.getAllRecords("workers")
        def shopsRecords = MongoDBUtil.getAllRecords("shops")

        println "Генерация/вставка продаж..."

        while (goodsRecords.hasNext()) {
            def customer = UsersDictionary.generateRandomUser()

            def goods = []

            for (int j = 1; j <= rn.nextInt(5) + 1; j++) {
                def randomGood

                if (goodsRecords.hasNext())
                    randomGood = goodsRecords.next()
                else
                    break

                goods.push([
                        goodId     : randomGood._id,
                        description: randomGood.manufacturer + " " + randomGood.model,
                        qty        : rn.nextInt(5) + 1,
                        retailPrice: GoodsTypesDictionary.price
                ])
            }

            Date date = new Date()

            record = [:]

            record << [customer: customer]
            record << [goods: goods]
            record << [date: date.toString()]
            record << [shop: shopsRecords[rn.nextInt(shopsRecords.size())]._id['$oid']]
            record << [worker: workersRecords[rn.nextInt(workersRecords.size())]._id['$oid']]

            status = (db.getCollection("sales") << record)
            if (!status) {
                break
            }

            for (int i = 0; i < 2; i++) {
                if (goodsRecords.hasNext())
                    goodsRecords.next()
                else
                    break
            }
        }
        println "Продажи сгенерированы/вставлены"
    }

    Boolean insertAll() {
//        def db = MongoDBUtil.getDB()
//
//        def record
//
//        println "Вставка продаж..."
//
//        Boolean status = true
//        for (result in data) {
//            record = [:]
//
//            record << [customer: result.customer]
//            record << [goods: result.goods]
//            record << [date: result.date]
//            record << [shop: result.shop._id['$oid']]
//            record << [worker: result.worker._id['$oid']]
//
//            status = (db.getCollection("sales") << record)
//            if (!status) {
//                break
//            }
//        }
//
//        return status
    }

}
