package amplifine.gen.tables

import amplifine.gen.Generator
import amplifine.gen.TableNamer
import amplifine.gen.utils.DataPair
import amplifine.gen.utils.Tools

class Unitsupplies implements Generator {

    static def d = []
    static final String tableName = "UNITSUPPLIES"

    Unitsupplies() {

        int numOfSupps = Supplies.getMaxPossibleRecords()

        Random rn = new Random(System.nanoTime());

        GoodsGenerator.data.eachWithIndex { val, i ->

            int idSupply = rn.nextInt(numOfSupps) + TableNamer.OFFSET
            int idGood = i + TableNamer.OFFSET
            float purchasingPrice = val.retail_price / 1.5
            final int MAX_POSSIBLE_AMOUNT = 50
            int amount = rn.nextInt(MAX_POSSIBLE_AMOUNT) + 1

            d << [
                    id_supply: idSupply,
                    id_good: idGood,
                    purchasing_price: purchasingPrice,
                    amount: amount
            ]

        }

        Collections.shuffle(d, rn)

    }

    static int getMaxPossibleRecords() {
        return d.size()
    }

    List<String> asQueryAll() {
        def queries = []

        d.each { val ->

            def pairs = []
            pairs << new DataPair("id_supply", val.id_supply)
            pairs << new DataPair("id_good", val.id_good)
            pairs << new DataPair("purchasing_price", val.purchasing_price)
            pairs << new DataPair("amount", val.amount)

            queries << Tools.getSqlInsert(TableNamer.getActualName(tableName), pairs, true)

        }

        return queries
    }

}
