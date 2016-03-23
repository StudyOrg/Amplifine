package gen.tables

import gen.Generator
import gen.TableNamer
import gen.utils.DataPair
import gen.utils.Tools

class Goodstypes implements Generator {

    static final String tableName = "GOODSTYPES"

    static String[] d = [
            "Guitar",
            "Piano",
            "Violin",
            "Bass-guitar",
            "Synthesizer",
            "Harp",
            "Accessory"
    ]

    static int getMaxPossibleRecords() {
        return d.size()
    }

    List<String> asQueryAll() {

        def queries = []

        d.each {

            def pairs = []
            pairs << new DataPair("name", it)
            queries << Tools.getSqlInsert(TableNamer.getActualName(tableName), pairs, true)

        }

        return queries

    }

}
