package gen.tables

import gen.Generator
import gen.TableNamer
import gen.utils.DataPair
import gen.utils.Tools

class Workers implements Generator {

    static final String tableName = "SHOPS"

    static def d = []

    static int getMaxPossibleRecords() {
        return d.size()
    }

    Workers() {

        def names = [
                "Anna", "Mark", "Maria", "Roman", "John", "Ching", "Andrew",
                "Nicko", "Inna", "Aaron", "Shy", "Christie", "Olga"
        ]

        def surnames = [
                "Chong", "Goldman", "Kaufman", "Brown", "Smith", "Barrel",
                "Finetuned", "Wellspeached", "Badwheel", "Goodman", "Niceman",
                "Lowlifeman", "Richguy", "Ross", "Crow"
        ]

        Random rn = new Random()
        StringBuilder sn = new StringBuilder()

        surnames.each { val ->
            (1..rn.nextInt(3)).each {
                d << [
                        surname: val,
                        name: names[rn.nextInt(names.size())],
                        birthday: new Date(rn.nextInt(20) + 1970, rn.nextInt(12), rn.nextInt(30)),
                        fired: rn.nextInt(1000) > 333 ?
                                new Date(rn.nextInt(5) + 2009, rn.nextInt(12), rn.nextInt(30)) : null,
                        enter: new Date(rn.nextInt(10) + 2005, rn.nextInt(12), rn.nextInt(30))
                ]
            }
        }

        Collections.shuffle(d, rn)

    }

    List<String> asQueryAll() {

        def queries = []

        d.each { val ->

            def pairs = []
            pairs << new DataPair("surname", val.surname)
            pairs << new DataPair("name", val.name)
            pairs << new DataPair("birthday", val.birthday)
            pairs << new DataPair("fired", val.fired)
            pairs << new DataPair("enter", val.enter)

            queries << Tools.getSqlInsert(
                    TableNamer.getActualName("WORKERS"),
                    pairs,
                    true
            )

        }

        return queries

    }

}
