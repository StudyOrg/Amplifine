package amplifine.gen

@Deprecated
interface Generator {
    static final String tableName
    List<String> asQueryAll()
}
