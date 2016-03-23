package gen.tables

import gen.Generator
import gen.TableNamer
import gen.utils.DataPair
import gen.utils.Tools

class Suppliers implements Generator {

    static final String tableName = "SUPPLIERS"

    static String[] d = [
            "Fender General Importer",
            "Music store",
            "Sound blast",
            "Flying bug music instruments",
            "MSGI",
            "RK and Partners",
            "Seaside Freight",
            "Mr. Sandman Import"
    ]

    static int getMaxPossibleRecords() {
        return d.size()
    }

    List<String> asQueryAll() {

        def queries = []

        d.each { val ->

            def pairs = []
            pairs << new DataPair("name", val)

            queries << Tools.getSqlInsert(TableNamer.getActualName(tableName), pairs, true)

        }

        return queries

    }

}
