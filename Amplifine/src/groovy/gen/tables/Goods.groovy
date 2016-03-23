package gen.tables

import gen.Generator
import gen.TableNamer
import gen.utils.DataPair
import gen.utils.Randomifier
import gen.utils.Tools

import java.util.Random

class GoodsData {
    String name = "Noname"
    int manufacturer = 0
    int goods_type = 0
    float retail_price = 0.0
    int amount = 1
}

class Goods implements Generator {

    static final String tableName = "GOODS"

    static def d = []
    int num

    static int getMaxPossibleRecords() {
        return d.size()
    }

    Goods(int num) {

        this.num = num
        int duplicates = 0

        Random rn = new Random(System.nanoTime());
        Randomifier gen = new Randomifier(rn)

        // В зависимости от производителя и типа определяется ценовой коэффициент
        float priceCoef = 1.0
        // Для некоторых типов товаров извлекаются соответствующие id производителя и id типа
        int manufacturerId = -1
        int typeId = -1
        // Вероятности генерации конкретного типа товара
        def rgGuitars = 0..<480
        def rgAccessories = 480..<500
        def rgAnother = 500..<1000
        // Алгоритм генерирует записи товаров в зависимости от типа и производителя
         for(int i = 0; i < num; i++) {

            StringBuffer sn = new StringBuffer()
            int dice = rn.nextInt(1000)

            // Генерируем записи для гитар и бас-гитар
            if(dice in rgGuitars) {

                def guitars = ["Fender", "Peavy", "Ibanez", "Ibanez Korea", "Roland", "Squier", "HandGuitar", "ChinaCraft"]
                def randomManufacturer = guitars[rn.nextInt(guitars.size())]
                int randomManufacturerId = Manufacturers.d.findIndexOf { mf -> mf.name == randomManufacturer }
                ManufacturesData tmp = Manufacturers.d[randomManufacturerId]
                int[] typeIds = [
                        Goodstypes.d.findIndexOf { gt -> gt == "Guitar" },
                        Goodstypes.d.findIndexOf { gt -> gt == "Bass-guitar" }
                ]

                typeId = typeIds[rn.nextInt(2)]
                manufacturerId = randomManufacturerId

                if (tmp.country in ["China", "Korea"]) {
                    def fmts = ["AA-DDDDDD (C M)", "AD-DDAADD", "AAADAADAD", "ADDDADDD", "AD-DDDDDD"]
                    sn.append(gen.generateFromFormat(fmts[rn.nextInt(fmts.size())]))
                    priceCoef = 0.5

                } else {
                    sn.append(gen.generateUniqueName())
                    priceCoef = 2.0
                }

            }
            // Генерируем аксесуары
            if(dice in rgAccessories) {

                sn.append(gen.getAccessories())
                priceCoef = 0.01
                typeId = Goodstypes.d.findIndexOf { gt -> gt == "Accessory" }
                manufacturerId = Manufacturers.d.findIndexOf { mf -> mf.name == "Dunlop" }

            }
             // Генерируем все остальное
             if(dice in rgAnother) {

                def fmts = ["AADDADDAA", "C N (AADDAD)", "C N AD", "J N model D (C)", "J N (C M) (ADDADA)"]
                sn.append(gen.generateFromFormat(fmts[rn.nextInt(fmts.size())]))
                priceCoef = 1.3
                manufacturerId = -1
                typeId = -1

             }


            if( d.findIndexOf { it.name == sn.toString() } != -1  ) {

                ++duplicates

            } else {

                float minPrice = 10000.0f
                float maxPrice = 50000.0f
                float price = rn.nextFloat() * (maxPrice - minPrice) + minPrice
                int amount = rn.nextInt(400) + 1

                d << [
                        name: sn.toString(),
                        manufacturer: manufacturerId == -1 ? rn.nextInt(Manufacturers.d.size()) + TableNamer.OFFSET : manufacturerId + 100,
                        goods_type: typeId == -1 ? rn.nextInt(Goodstypes.d.size()) + TableNamer.OFFSET : typeId + TableNamer.OFFSET,
                        retail_price: price * priceCoef,
                        amount: amount
                ]

                Collections.shuffle(d, rn)

            }

        }

        printf("Duplicates: %d, generated: %d\n", duplicates, d.size())

    }

    List<String> asQueryAll() {

        def queries = []

        d.each { val ->

            def pairs = []
            pairs << new DataPair("name", val.name)
            pairs << new DataPair("manufacturer", val.manufacturer)
            pairs << new DataPair("goods_type", val.goods_type)
            pairs << new DataPair("retail_price", val.retail_price)
            pairs << new DataPair("amount", val.amount)

            queries << Tools.getSqlInsert(TableNamer.getActualName(tableName), pairs, true)

        }

        return queries

    }

}
