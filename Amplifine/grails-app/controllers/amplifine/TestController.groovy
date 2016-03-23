package amplifine

class TestController {

    def index() {
        def records = Person.getAll()

        render(view: "initial", model: [records: records[2]])
    }

    def make() {
        def record = new Person(name: "Nicky", surname: "Smirnov")

        if (record.save(flush: true)) {

            render(view: "initial", model: [records: record])
        } else {
            render(view: "error")
        }
    }

    def makeWithAddress() {
        def address = Address.findByStreet("Street 18")
        def address2 = Address.findByStreet("Street 17")

        def record = new Person(name: "Roman", surname: "Hulberchen", address: address.id)
        def record2 = new Person(name: "Test1", surname: "Tesst2", address: address2.id)

        if (record.save(flush: true) && record2.save(flush: true)) {

            render(view: "initial", model: [records: record2])
        } else {
            render(view: "error")
        }
    }

    def del() {
        def del = Person.withCriteria {
            "eq"("name", "Nicky")
        }[0]

        if (del && del.delete()) {
            render(view: "success")
        } else {
            render(view: "error")
        }
    }

    def makeAddress() {
        def map = [house:9, flat:85, testMes:"test123"]

        new Address(city:"City17", street:"Street 17").save(flush: true)
        new Address(city:"City17", street:"Street 18", houseFlatMap: map).save(flush: true)
        new Address(city:"City18", street:"Street 19").save(flush: true)
        new Address(city:"City17", street:"Street 20").save(flush: true)

        render(view: "success")
    }
}
