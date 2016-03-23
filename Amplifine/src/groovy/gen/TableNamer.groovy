package gen

class TableNamer {

    static final String prefix = "\"S158374\""

    static String getActualName(String table) {
        return prefix + "." + gen.utils.Tools.wrap(table, "\"")
    }

    static final int OFFSET = 100

}
