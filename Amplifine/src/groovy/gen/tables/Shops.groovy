package gen.tables

import gen.Generator
import gen.TableNamer
import gen.utils.DataPair
import gen.utils.Tools
import groovy.transform.Canonical

@Canonical
class ShopsData {
    String name = null
    int shop_type = 0
    String address = null
    String tel = null
}

class Shops implements Generator {

    static final String tableName = "SHOPS"

    static ShopsData[] d = [
            ["General Music Store", 102, "Proletary street, 20, 1", "88135557070"],
            ["North Cither", 101, "Line-3 ave, 11, 345-5", "88125550002"],
            ["Syntesis", 104, "Radical street, 1, 110-2", "88125553412"],
            ["Mad Guitars", 100, "Round ave, 45-2, 1", "88125559987"],
            ["Music Stuff", 106, "Line-2 ave, 123, 32A", "012"],
            ["Classique", 105, "Central street, 1, 1B", "88125550001"],
            ["Deep Bass", 103, "Lowpass street, 4, 22", "88125550405"]
    ]

    static int getMaxPossibleRecords() {
        return d.size()
    }

    Shops() {

    }

    List<String> asQueryAll() {

        def queries = []

        d.each { val ->

            def pairs = []
            pairs.add( new DataPair("name", val.name) )
            pairs.add( new DataPair("shop_type", val.shop_type) )
            pairs.add( new DataPair("address", val.address) )
            pairs.add( new DataPair("tel", val.tel) )

            queries << Tools.getSqlInsert(
                    TableNamer.getActualName(tableName),
                    pairs,
                    true
            )

        }

        return queries

    }

}
