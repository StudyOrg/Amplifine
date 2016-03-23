package amplifine.gen.tables

import amplifine.gen.Generator
import amplifine.gen.TableNamer
import amplifine.gen.utils.DataPair
import amplifine.gen.utils.Tools

class Supplies implements Generator {

    static final String tableName = "SUPPLIES"

    static def d = []

    static int getMaxPossibleRecords() {
        return d.size()
    }

    Supplies() {

        Random rn = new Random(System.nanoTime());

        int shops = ShopsGenerator.getMaxPossibleRecords()
        int supps = Suppliers.getMaxPossibleRecords()
        int goods = GoodsGenerator.getMaxPossibleRecords()

        for( int i = 0; i < goods / supps; i++ ) {

            Date suppDate = new Date(
                    rn.nextInt(3) + 2013,
                    rn.nextInt(12) + 1,
                    rn.nextInt(28)
            )

            int idSupplier = rn.nextInt(supps) + TableNamer.OFFSET
            int idShop = rn.nextInt(shops) + TableNamer.OFFSET

            d << [
                    id_supplier: idSupplier,
                    id_shop: idShop,
                    date_supply: suppDate
            ]

        }

    }

    List<String> asQueryAll() {

        def queries = []

        d.each {

            def pairs = []
            pairs << new DataPair("id_supplier", it.id_supplier)
            pairs << new DataPair("id_shop", it.id_shop)
            pairs << new DataPair("date_supply", it.date_supply)
            queries << Tools.getSqlInsert(TableNamer.getActualName(tableName), pairs, true)

        }

        return queries

    }

}
