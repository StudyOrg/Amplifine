package amplifine.gen.utils

import java.text.SimpleDateFormat

@Deprecated
class DataPair<T> {
    DataPair(field_name, value) {
        this.value = value
        this.field_name = field_name
    }
    String field_name
    T value
}

class Tools {

    static String wrap(String str, String bracket) {
        return bracket + str + bracket;
    }

    @Deprecated
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