package amplifine.gen

import amplifine.gen.utils.Tools

@Deprecated
class TableNamer {

    static final String prefix = "\"S158374\""

    static String getActualName(String table) {
        return prefix + "." + Tools.wrap(table, "\"")
    }

    static final int OFFSET = 100

}
