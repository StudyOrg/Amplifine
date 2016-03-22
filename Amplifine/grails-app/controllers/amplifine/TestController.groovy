package amplifine

class TestController {

    def index() {
        def records = Person.getAll()

        render(view: "initial", model: [records: records[0]])
    }

    def make() {
        def record = new Person(name: "KillDom", surname: "SomeOne")

        if (record.save(flush: true)) {

            render(view: "initial", model: [records: record])
        } else {
            render(view: "initial", model: [records: "failed"])
        }

    }
}
