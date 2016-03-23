package amplifine

import amplifine.gen.MongoGenerator
import amplifine.gen.tables.GoodsGenerator

class GeneratorController {
    def index() {
        render(view: "index")
    }

    def goods() {
        Integer count = params.count as Integer
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
}
