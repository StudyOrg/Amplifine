package amplifine.gen.tables

import amplifine.gen.MongoGenerator
import amplifine.gen.dictionaries.AcousticGuitarsDictionary
import amplifine.gen.dictionaries.CheapEGDictionary
import amplifine.gen.dictionaries.ColoursDictionary
import amplifine.gen.dictionaries.ExpensiveEGDictionary
import amplifine.gen.dictionaries.KeyboardsDictionary
import amplifine.gen.dictionaries.MaterialsDictionary
import amplifine.gen.utils.Randomifier
import mongodb.MongoDBUtil

class GoodsGenerator implements MongoGenerator {
    def data = []

    GoodsGenerator() {
        Random rn = new Random(System.nanoTime());
        Randomifier gen = new Randomifier(rn)
        // Центр и радиус окрестности для цены
        float priceBias
        float pricePivot

        // Производитель и тип
        String manufacturer
        String goodType
        int counter = 0

        // Генерация дорогих электрогитар
        print "Генерация дорогих электрогитар... "
        pricePivot = 50_000.0
        priceBias = 20_000.0
        for (def i in ExpensiveEGDictionary.manufacturers) {
            for (def j in ExpensiveEGDictionary.modelsFirstPart) {
                for (def k in ExpensiveEGDictionary.modelsSecondPart) {
                    for (def x in ColoursDictionary.colours) {
                        for (def y in MaterialsDictionary.materials) {
                            data << [
                                    model       : "$j $k ($x $y)".toString(),
                                    manufacturer: i,
                                    type        : "Electric Guitar",
                                    retailPrice : pricePivot + rn.nextFloat() * priceBias
                            ]

                            ++counter
                        }
                    }
                }
            }
        }
        println "Сгенерировано ${counter}"

        // Генерация дешевых электрогитар
        print "Генерация дешевых электрогитар... "
        pricePivot = 20_000.0
        priceBias = 5_000.0
        def models = CheapEGDictionary.getModels(120)
        counter = 0
        for (def i in CheapEGDictionary.manufacturers) {
            for (def j in models) {
                for (def x in ColoursDictionary.colours) {
                    for (def y in MaterialsDictionary.materials) {
                        data << [
                                model       : "$j ($x $y)".toString(),
                                manufacturer: i,
                                type        : "Electric Guitar",
                                retailPrice : pricePivot + rn.nextFloat() * priceBias
                        ]

                        ++counter
                    }
                }
            }
        }
        println "Сгенерировано ${counter}"

        // Генерация акустических гитар
        print "Генерация акустических гитар... "
        pricePivot = 15_000.0
        priceBias = 2_000.0
        models = AcousticGuitarsDictionary.getModels(100)
        counter = 0
        for (def i in AcousticGuitarsDictionary.manufacturers) {
            for (def j in models) {
                for (def x in ColoursDictionary.colours) {
                    for (def y in MaterialsDictionary.materials) {
                        data << [
                                model       : "$j ($x $y)".toString(),
                                manufacturer: i,
                                type        : "Acoustic Guitar",
                                retailPrice : pricePivot + rn.nextFloat() * priceBias
                        ]

                        ++counter
                    }
                }
            }
        }
        println "Сгенерировано ${counter}"

        // Генерация клавишных
        print "Генерация клавишных... "
        pricePivot = 50_000.0
        priceBias = 30_000.0
        counter = 0
        for (def i in KeyboardsDictionary.manufacturers) {
            for (def j in KeyboardsDictionary.models) {
                for (def x in ColoursDictionary.colours) {
                    for (def y in MaterialsDictionary.materials) {
                        data << [
                                model       : "$j ($x $y)".toString(),
                                manufacturer: i,
                                type        : "Keybords",
                                retailPrice : pricePivot + rn.nextFloat() * priceBias
                        ]

                        ++counter
                    }
                }
            }
        }
        println "Сгенерировано ${counter}"

        Collections.shuffle(data)
    }

    Boolean insertAll() {
        def db = MongoDBUtil.getDB()

        def record

        Boolean status = true
        data.eachWithIndex { result, i ->
            record = [:]

            record << [manufacturer: result.manufacturer]
            record << [model: result.model]
            record << [type: result.type]
            record << [retailPrice: result.retailPrice]

            status = (db.getCollection("goods") << record)
            if (!status) {
                return status
            }
        }

        return status
    }

}
