package amplifine

import amplifine.gen.MongoGenerator
import amplifine.gen.tables.*
import amplifine.utils.TypesUtil
import mongodb.MongoDBUtil

class GeneratorController {
    def index() {
        render(view: "index", model: [notify: params.notify])
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
        ArrayList<String> notify = []

        def db = MongoDBUtil.getDB()
        String[] collections = ["goods", "shops", "workers", "sales", "supplies"]

        for (it in collections) {
            db.getCollection(it).drop()
        }

        println ">>> Генерация основных записей"

        MongoGenerator[] generators = [new GoodsGenerator(), new ShopsGenerator(), new WorkersGenerator()]

        for (it in generators) {
            it.insertAll()
        }

        println ">>> Генерация дополнительных записей"

        generators = [new SalesGenerator(), new SuppliesGenerator()]

        for (it in generators) {
            it.insertAll()
        }

        println ">>> Добавляем индексы"

        db.getCollection("sales").createIndex(['$**': "text"])

        notify << "Готово"

        redirect(action: "index", params: [notify: notify])
    }
}
