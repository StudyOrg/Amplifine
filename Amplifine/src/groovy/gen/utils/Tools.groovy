package gen.utils

import groovy.sql.Sql

import javax.management.relation.RelationNotFoundException
import java.text.SimpleDateFormat
import java.util.Random

class DataPair<T> {
    DataPair(field_name, value) {
        this.value = value
        this.field_name = field_name
    }
    String field_name
    T value
}

class Tools {

    static dropTables(dataSource) {
        def sql = Sql.newInstance(dataSource)
        def dropScriptFile = new File("db_scripts\\DropTablesScript.sql")

        dropScriptFile.eachLine { line ->
            if (line) {
                sql.execute(line)
                println line
            }
        }
    }

    static createTables(dataSource) {
        def sql = Sql.newInstance(dataSource)
        def createScriptFile = new File("db_scripts\\CreateTablesScript.sql")

        def begin = false

        def createQuery = ""

        createScriptFile.eachLine { line ->
            if (!line) {
                createQuery = ""
            } else if (line.endsWith(";")) {
                if (line == "end;") {
                    line = " " + line + ";"
                    begin = false
                }

                if (begin) {
                    createQuery += (line + " ")
                } else {
                    createQuery += line.replaceFirst(";", "")
                    //println createQuery
                    sql.execute(createQuery)
                }
            } else {
                if (line == "begin")
                    begin = true

                createQuery += (line + " ")
            }
        }
    }

    static String wrap(String str, String bracket) {
        return bracket + str + bracket;
    }

    static String getSqlInsert(String table_name, ArrayList<DataPair> data, Boolean addSemicolon) {

        def fields = []
        def values = []

        for (def val : data) {

            fields << val.field_name

            if (val.value?.class?.name == "java.lang.String") {

                values << "\'${val.value}\'"

            } else if( val.value?.class?.name == "java.util.Date" ) {

                SimpleDateFormat df = new SimpleDateFormat("dd.MM.yy")
                String dateAsString = df.format(val.value)
                StringBuilder sn = new StringBuilder()
                sn.append("TO_DATE('")
                sn.append(dateAsString)
                sn.append("','DD.MM.RR')")
                values << sn.toString()

            } else if( val.value?.class?.name == "java.sql.Timestamp" ) {

                SimpleDateFormat df = new SimpleDateFormat("dd.MM.yy HH:mm:ss")
                String dateAsString = df.format(val.value)
                StringBuilder sn = new StringBuilder()
                sn.append("TO_TIMESTAMP('")
                sn.append(dateAsString)
                sn.append("','DD.MM.RR HH24:MI:SS')")
                values << sn.toString()

            }  else if( val.value?.class?.name in ["java.lang.Float", "java.lang.Double"] ) {

                values << String.format(Locale.US, "%.2f", val.value)

            } else {

                values << val.value

            }

        }

        StringBuilder buffer = new StringBuilder()
        buffer.append("INSERT INTO ${table_name} (")
        buffer.append(fields.join(", "))
        buffer.append(") VALUES (")
        buffer.append(values.join(", "))
        buffer.append(")")

        if (addSemicolon) {
            //buffer.append(";")
        }

        return buffer.toString()

    }

}