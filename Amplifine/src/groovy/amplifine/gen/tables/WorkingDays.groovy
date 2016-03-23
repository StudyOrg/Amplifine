package amplifine.gen.tables

import amplifine.gen.Generator
import amplifine.gen.TableNamer
import amplifine.gen.utils.DataPair
import amplifine.gen.utils.Ring
import amplifine.gen.utils.Tools

class WorkingDays implements Generator {

    static final String tableName = "WORKINGDAYS"

    static def d = []

    static int getMaxPossibleRecords() {
        return d.size()
    }

    WorkingDays() {

        Random rn = new Random(System.nanoTime());

        Workers.d.eachWithIndex { val, vi ->

            int idShop = rn.nextInt(Shops.getMaxPossibleRecords()) + TableNamer.OFFSET
            int timeStart = rn.nextInt(4) + 8
            int timeEnd = rn.nextInt(4) + 16

            Ring dayRing = new Ring(-1, 5, 1)
            for(int i = 0; i < 60; i++) {

                int day = dayRing.nextInt()
                if( day in [-1, 0] )
                    continue

                Date workingDate = new Date(2015, (11 + (i / 30)).intValue(), (i % 30) + 1)

                d << [
                        id_worker: vi + TableNamer.OFFSET,
                        id_shop: idShop,
                        working_date: workingDate,
                        time_start: timeStart,
                        time_finish: timeEnd
                ]

            }

        }

    }

    List<String> asQueryAll() {

        def queries = []

        d.each {

            def pairs = []
            pairs << new DataPair("id_worker", it.id_worker)
            pairs << new DataPair("id_shop", it.id_shop)
            pairs << new DataPair("working_date", it.working_date)
            pairs << new DataPair("time_start", it.time_start)
            pairs << new DataPair("time_finish", it.time_finish)
            queries << Tools.getSqlInsert(TableNamer.getActualName(tableName), pairs, true)

        }

        return queries

    }

}
