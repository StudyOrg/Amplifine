package amplifine.gen.tables

import amplifine.gen.Generator
import amplifine.gen.TableNamer
import amplifine.gen.utils.DataPair
import amplifine.gen.utils.Tools

import java.sql.Timestamp

class Sales implements Generator {

    static final String tableName = "SALES"

    static def d = []

    static int getMaxPossibleRecords() {
        return d.size()
    }

    Sales() {

        Random rn = new Random(System.nanoTime());

        for(int i = 0; i < GoodsGenerator.getMaxPossibleRecords() / Workers.getMaxPossibleRecords(); i++ ) {

            def moment = WorkingDays.d[rn.nextInt(WorkingDays.getMaxPossibleRecords())]
            GregorianCalendar tradeTime = new GregorianCalendar(
                    moment.working_date.getYear(),
                    moment.working_date.getMonth(),
                    moment.working_date.getDay(),
                    moment.time_start + rn.nextInt(2),
                    rn.nextInt(59) + 1,
                    rn.nextInt(59) + 1
            )
            d << [
                    id_worker: moment.id_worker,
                    sale_time: new Timestamp(tradeTime.getTimeInMillis())
            ]

        }

    }

    List<String> asQueryAll() {

        def queries = []

        d.each {

            def pairs = []
            pairs << new DataPair("id_worker", it.id_worker)
            pairs << new DataPair("sale_time", it.sale_time)
            queries << Tools.getSqlInsert(TableNamer.getActualName(tableName), pairs, true)

        }

        return queries

    }

}
