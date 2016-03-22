package amplifine

class TestController {

    static mapWith = "mongo"

    def index() {
        def records = Person.findAll()

        render(view:"initial", model:[records:records])
    }

    def make() {
        new Person(balls: 2).save(flush:true)

        render(view:"initial")
    }
}
