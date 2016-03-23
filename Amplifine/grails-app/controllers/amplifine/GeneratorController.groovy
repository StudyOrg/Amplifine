package amplifine

import amplifine.gen.MongoGenerator
import amplifine.gen.tables.GoodsGenerator
import amplifine.gen.tables.ShopsGenerator
import amplifine.utils.TypesUtil
import mongodb.MongoDBUtil

class GeneratorController {
    def index() {
        render(view: "index")
    }

    def goods() {
        Integer count = TypesUtil.parseInt(params.count)
        def notify = []

        if (count && count > 0) {
            def oldCount = GoodsGenerator.data.size()
            MongoGenerator goodsGen = new GoodsGenerator(count)

            def results = goodsGen.data
            if (results && results.size() > 0) {
                notify << "Успешно сгенерировано записей о товарах: ${results.size() - oldCount} из ${count} запрошенных."

                def status = goodsGen.insertAll()

                if (!status) {
                    notify << "При занесении записей возникла ошибка!"
                } else {
                    notify << "Все записи сохранены."
                }

            } else {
                notify << "При создании товаров возникла ошибка."
            }
        } else {
            notify << "Количество товаров не задано, запрос отклонен."
        }

        render(view: "index", model: [notify: notify])
    }

    def all() {
        def notify = []

        def db = MongoDBUtil.getDB()

        String count = params.count
        Integer goodsCount = TypesUtil.parseInt(count)

        if (goodsCount != null) {
            String[] collections = ["goods", "shops"]
            MongoGenerator[] generators = [new GoodsGenerator(goodsCount), new ShopsGenerator()]

            collections.each {
                db.getCollection(it).deleteMany([:])
            }
            notify << "Коллекции усечены"

            generators.each {
                it.insertAll()
            }
            notify << "Значения вставлены"
        } else {
            notify << "Количество товаров не задано, запрос отклонен."
        }

        render(view: "index", model: [notify: notify])
    }
}
