package amplifine.gen.tables

import groovy.transform.Canonical

import amplifine.gen.MongoGenerator
import mongodb.MongoDBUtil

@Canonical
class WorkersData {
    String name
    String surname

    Map documents

    String city

    Date birthday
    Date fired
}

class WorkersGenerator implements MongoGenerator {

    def data = []

    int getMaxPossibleRecords() {
        return data.size()
    }

    WorkersGenerator() {
        def names = [
                "Anna", "Mark", "Maria", "Roman", "John", "Ching", "Andrew",
                "Nicko", "Inna", "Aaron", "Shy", "Christie", "Olga"
        ]

        def surnames = [
                "Chong", "Goldman", "Kaufman", "Brown", "Smith", "Barrel",
                "Finetuned", "Wellspeached", "Badwheel", "Goodman", "Niceman",
                "Lowlifeman", "Richguy", "Ross", "Crow", "Yammamotoe"
        ]

        def cities = [
                "Northumberland", "City 17", "Detroit", "Calimport", "Boomtown",
                "Waterdeep", "Empire Bay", "Crew City", "Los-Gattos", "San-Clarentino"
        ]

        Random rn = new Random()

        surnames.each { val ->
            (1..rn.nextInt(3)).each {
                def genCity = cities[rn.nextInt(cities.size())]

                data << [
                        name: names[rn.nextInt(names.size())],
                        surname: val,
                        city: genCity,
                        documents: [
                                passport: (rn.nextInt(325) + 4270).toString() + " in " + genCity,
                                inn: (rn.nextInt(665) + 1234270),
                                workerBook: (rn.nextInt(22) + 1000)
                        ],
                        birthday: new Date(rn.nextInt(20) + 1970, rn.nextInt(12), rn.nextInt(30)),
                        fired: rn.nextInt(1000) > 333 ?
                                new Date(rn.nextInt(5) + 2009, rn.nextInt(12), rn.nextInt(30)) : null,
                ]
            }
        }

        Collections.shuffle(data, rn)

    }

    Boolean insertAll() {
        def db = MongoDBUtil.getDB()

        def record

        Boolean status = true
        for (result in data) {
            record = [:]

            record << [name: result.name]
            record << [surname: result.surname]
            record << [documents: result.documents]
            record << [city: result.city]
            record << [birthday: result.birthday]

            if (result.fired)
                record << [fired: result.fired]

            status = (db.getCollection("workers") << record)
            if (!status) {
                break
            }
        }

        return status
    }
}
