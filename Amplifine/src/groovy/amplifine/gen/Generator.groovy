package amplifine.gen

interface Generator {
    static final String tableName
    List<String> asQueryAll()
}