package amplifine

class TestController {

    def index() {
        def records = Person.getAll()

        render(view: "initial", model: [records: records[1]])
    }

    def make() {
        def record = new Person(name: "KillDom", surname: "SomeOne")

        if (record.save(flush: true)) {

            render(view: "initial", model: [records: record])
        } else {
            render(view: "error")
        }
    }

    def makeWithAddress() {
        def address = Address.findByStreet("Street 19")

        def record = new Person(name: "Roman", surname: "Hulberchen", address: address.id)

        if (record.save(flush: true)) {

            render(view: "initial", model: [records: record])
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
        new Address(city:"City17", street:"Street 17").save(flush: true)
        new Address(city:"City17", street:"Street 18").save(flush: true)
        new Address(city:"City18", street:"Street 19").save(flush: true)
        new Address(city:"City17", street:"Street 20").save(flush: true)

        render(view: "success")
    }
}
