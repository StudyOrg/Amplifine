package gen.tables

import gen.Generator
import gen.TableNamer
import gen.utils.DataPair
import gen.utils.Ring
import gen.utils.Tools

class Unitsales implements Generator {

    static final String tableName = "UNITSALES"

    static def d = []

    Unitsales() {

        Random rn = new Random(System.nanoTime());
        int goods = Goods.getMaxPossibleRecords()
        Ring rg = new Ring(0, goods - 1, rn.nextInt(goods))

        Sales.d.eachWithIndex { val, i ->

            (1..rn.nextInt(5)+5).each {

                int goodId = rg.nextInt()

                d << [
                        id_good: goodId + TableNamer.OFFSET,
                        id_sale: i + TableNamer.OFFSET,
                        amount: rn.nextInt(20) + 1,
                        full_retail_price: Goods.d[goodId].retail_price
                ]

            }

        }

    }

    List<String> asQueryAll() {

        def queries = []

        d.each {

            def pairs = []
            pairs << new DataPair("id_good", it.id_good)
            pairs << new DataPair("id_sale", it.id_sale)
            pairs << new DataPair("amount", it.amount)
            pairs << new DataPair("full_retail_price", it.full_retail_price)
            queries << Tools.getSqlInsert(TableNamer.getActualName(tableName), pairs, true)

        }

        return queries

    }

}
