package amplifine.gen.tables

import amplifine.Goods
import amplifine.gen.MongoGenerator
import amplifine.gen.dictionaries.GoodsTypesDictionary
import amplifine.gen.dictionaries.ManufacturerData
import amplifine.gen.dictionaries.Manufacturers
import amplifine.gen.utils.Randomifier

class GoodsGenerator implements MongoGenerator {
    static def data = []

    static int getMaxPossibleRecords() {
        return data.size()
    }

    GoodsGenerator(Integer num) {
        Random rn = new Random(System.nanoTime());
        Randomifier gen = new Randomifier(rn)

        // В зависимости от производителя и типа определяется ценовой коэффициент
        float priceCoef = 1.0

        // Производитель и тип
        String manufacturer
        String goodType

        // Вероятности генерации конкретного типа товара
        def rgGuitars = 0..<480
        def rgAccessories = 480..<500
        def rgAnother = 500..<1000
        // Алгоритм генерирует записи товаров в зависимости от типа и производителя
        for (int i = 0; i < num; i++) {
            StringBuffer sn = new StringBuffer()
            int dice = rn.nextInt(1000)

            // Генерируем записи для гитар и бас-гитар
            if (dice in rgGuitars) {

                def guitars = ["Fender", "Peavy", "Ibanez", "Ibanez Korea", "Roland", "Squier", "HandGuitar", "ChinaCraft"]
                def types = ["Guitar", "Bass-guitar"]

                def randomType = guitars[rn.nextInt(guitars.size())]
                ManufacturerData guitarManufacturer = Manufacturers.findByName(randomType)

                manufacturer = guitarManufacturer.name
                goodType = types[rn.nextInt(types.size())]

                if (guitarManufacturer.country in ["China", "Korea"]) {
                    def fmts = ["AA-DDDDDD (C M)", "AD-DDAADD", "AAADAADAD", "ADDDADDD", "AD-DDDDDD"]
                    sn.append(gen.generateFromFormat(fmts[rn.nextInt(fmts.size())]))
                    priceCoef = 0.5
                } else {
                    sn.append(gen.generateUniqueName())
                    priceCoef = 2.0
                }

            }

            // Генерируем аксесуары
            if (dice in rgAccessories) {
                sn.append(gen.getAccessories())
                manufacturer = "Dunlop"
                goodType = "Accessory"
                priceCoef = 0.01
            }

            // Генерируем все остальное
            if (dice in rgAnother) {
                def fmts = ["AADDADDAA", "C N (AADDAD)", "C N AD", "J N model D (C)", "J N (C M) (ADDADA)"]
                sn.append(gen.generateFromFormat(fmts[rn.nextInt(fmts.size())]))
                priceCoef = 1.3
                manufacturer = Manufacturers.anyManufacturer.name
                goodType = GoodsTypesDictionary.anyType
            }


            if (data.findIndexOf { it.name == sn.toString() } == -1) {
                float minPrice = 10000.0f
                float maxPrice = 50000.0f
                float price = rn.nextFloat() * (maxPrice - minPrice) + minPrice

                data << [model       : sn.toString(),
                         manufacturer: manufacturer,
                         type        : goodType,
                         retailPrice : price * priceCoef]

                Collections.shuffle(data, rn)
            }
        }
    }

    Boolean insertAll() {
        Goods record

        Boolean status = true
        for (result in data) {
            record = new Goods()

            record.manufacturer = result.manufacturer
            record.model = result.model
            record.type = result.type
            record.retailPrice = result.retailPrice

            status = record.insert()
            if (!status) {
                break
            }
        }

        return status
    }
}
