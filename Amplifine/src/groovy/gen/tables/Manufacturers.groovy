package gen.tables

import gen.Generator
import gen.TableNamer
import gen.utils.*
import gen.utils.DataPair
import groovy.transform.Canonical

@Canonical
class ManufacturesData {
    String name = "Noname"
    String country = "USA"
    String parent = null
}

class Manufacturers implements Generator {

    static final String tableName = "MANUFACTURERS"

    static ManufacturesData[] d = [
            ["Fender", "USA", null],
            ["Squier", "China", "Fender"],
            ["Peavy", "USA", null],
            ["ChinaCraft", "China", null],
            ["HandGuitar", "China", "ChinaCraft"],
            ["Ibanez", "USA", null],
            ["Ibanez Korea", "Korea", "Ibanez"],
            ["Roland", "Japan", null],
            ["Hofner", "Germany", null],
            ["Stentor", "Australia", null],
            ["Aeolian Company", "USA", null],
            ["American Piano Company", "USA", "Aeolian Company"],
            ["Heintzman and Co", "Canada", null],
            ["Petzold", "France", null],
            ["Dunlop", null]
    ]

    int getMaxPossibleRecords() {
        return d.size()
    }

    Manufacturers() {

    }

    List<String> asQueryAll() {

        def queries = []

        d.each { val ->

            def pairs = []
            pairs.add( new DataPair("name", val.name) )
            pairs.add( new DataPair("country", val.country) )
            if( val.parent == null ) {
                pairs.add( new DataPair("parent_manufacturer", null) )
            } else {
                int parent_id = d.findIndexOf {
                    val.parent == it.name
                }
                assert parent_id != -1
                parent_id += TableNamer.OFFSET
                pairs << new DataPair("parent_manufacturer", parent_id)
            }

            queries << Tools.getSqlInsert(
                            TableNamer.getActualName(tableName),
                            pairs,
                            true
            )

        }

        return queries

    }
}
