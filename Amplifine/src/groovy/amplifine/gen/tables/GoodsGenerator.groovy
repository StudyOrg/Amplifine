package amplifine.gen.tables

import amplifine.gen.MongoGenerator
import amplifine.gen.dictionaries.*
import mongodb.MongoDBUtil

class GoodsGenerator implements MongoGenerator {

    GoodsGenerator() {
        Random rn = new Random(System.nanoTime());
        // Центр и радиус окрестности для цены
        float priceBias
        float pricePivot

        // Объект базы данных
        def db = MongoDBUtil.getDB()
        boolean status
        def record

        // Производитель и тип
        int counter = 0

        // Предварительный список товаров
        List<Map<?,?>> goodsList = new ArrayList<Map<?,?>>(1_100_000)

        // Генерация дорогих электрогитар
        print "Генерация/вставка дорогих электрогитар... "
        pricePivot = 50_000.0
        priceBias = 20_000.0
        for (def y in MaterialsDictionary.materials) {
            for (def x in ColoursDictionary.colours) {
                for (def j in ExpensiveEGDictionary.modelsFirstPart) {
                    for (def k in ExpensiveEGDictionary.modelsSecondPart) {
                        for (def i in ExpensiveEGDictionary.manufacturers) {
                            record = [:]

                            record << [manufacturer: i]
                            record << [model: "$j $k ($x $y)".toString()]
                            record << [type: "Electric Guitar"]
                            record << [retailPrice: pricePivot + rn.nextFloat() * priceBias]

//                            status = (db.getCollection("goods") << record)
//                            if (!status) {
//                                throw new Exception("Иди в жопу, сказала мне база данных")
//                            }

                            goodsList.add(record)

                            ++counter
                        }
                    }
                }
            }
        }
        println "Сгенерировано/вставлено ${counter}"

        // Генерация дешевых электрогитар
        print "Генерация/вставка дешевых электрогитар... "
        pricePivot = 20_000.0
        priceBias = 5_000.0
        def models = CheapEGDictionary.getModels(120) //120
        counter = 0
        for (def y in MaterialsDictionary.materials) {
            for (def x in ColoursDictionary.colours) {
                for (def j in models) {
                    for (def i in CheapEGDictionary.manufacturers) {
                        record = [:]

                        record << [manufacturer: i]
                        record << [model: "$j ($x $y)".toString()]
                        record << [type: "Electric Guitar"]
                        record << [retailPrice: pricePivot + rn.nextFloat() * priceBias]

//                        status = (db.getCollection("goods") << record)
//                        if (!status) {
//                            throw new Exception("Иди в жопу, сказала мне база данных")
//                        }

                        goodsList.add(record)

                        ++counter
                    }
                }
            }
        }
        println "Сгенерировано/вставлено ${counter}"

        // Генерация акустических гитар
        print "Генерация/вставка акустических гитар... "
        pricePivot = 15_000.0
        priceBias = 2_000.0
        models = AcousticGuitarsDictionary.getModels(100) //100
        counter = 0
        for (def i in AcousticGuitarsDictionary.manufacturers) {
            for (def j in models) {
                for (def x in ColoursDictionary.colours) {
                    for (def y in MaterialsDictionary.materials) {
                        record = [:]

                        record << [manufacturer: i]
                        record << [model: "$j ($x $y)".toString()]
                        record << [type: "Acoustic Guitar"]
                        record << [retailPrice: pricePivot + rn.nextFloat() * priceBias]

//                        status = (db.getCollection("goods") << record)
//                        if (!status) {
//                            throw new Exception("Иди в жопу, сказала мне база данных")
//                        }

                        goodsList.add(record)

                        ++counter
                    }
                }
            }
        }
        println "Сгенерировано/вставлено ${counter}"

        // Генерация клавишных
        print "Генерация/вставка клавишных... "
        pricePivot = 50_000.0
        priceBias = 30_000.0
        counter = 0
        for (def x in ColoursDictionary.colours) {
            for (def i in KeyboardsDictionary.manufacturers) {
                for (def y in MaterialsDictionary.materials) {
                    for (def j in KeyboardsDictionary.models) {
                        record = [:]

                        record << [manufacturer: i]
                        record << [model: "$j ($x $y)".toString()]
                        record << [type: "Keyboards"]
                        record << [retailPrice: pricePivot + rn.nextFloat() * priceBias]

//                        status = (db.getCollection("goods") << record)
//                        if (!status) {
//                            throw new Exception("Иди в жопу, сказала мне база данных")
//                        }

                        goodsList.add(record)

                        ++counter
                    }
                }
            }
        }

        println "Вставляем..."

        Collections.shuffle(goodsList)

        for (good in goodsList) {
            status = (db.getCollection("goods") << good)
            if (!status) {
                throw new Exception("Возникла ошибка во время записи в базу")
            }
        }

        println "Сгенерировано/вставлено ${counter}"
    }

    Boolean insertAll() {

    }

}
