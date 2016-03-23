package amplifine

import com.mongodb.BasicDBList
import com.mongodb.BasicDBObject
import com.mongodb.DBObject
import com.mongodb.MongoClient

class TestController {

    def index() {
        def records = Person.getAll()

        render(view: "initial", model: [records: records[1]])
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
        def host = "localhost"
        def port = 27017
        def databaseName = "test"

        def map1 = [house:9, flat:88, testMes:"test123"]
        def map2 = [house:123, flat:111]

        def list = []
        list << map1
        list << map2

        def client = new MongoClient(host, port)
        def db = client.getDatabase(databaseName)
        db.getCollection("address") << [city:"City17", street:"Street 17", houseFlat:list]

        DBObject document1 = new BasicDBObject()
        document1.put("house", 9)
        document1.put("flat", 85)
        document1.put("testMes", "test123")

        DBObject document2 = new BasicDBObject()
        document2.put("house", 10)
        document2.put("flat", 185)

        def map = []
        map2 = []

        map << document1
        map << document2

        map2 << 111
        map2 << 222

        //new Address(city:"City17", street:"Street 17", houseFlatMap: map2).save(flush: true)
        new Address(city:"City17", street:"Street 18", houseFlatMap: map).save(flush: true)
        new Address(city:"City18", street:"Street 19").save(flush: true)
        new Address(city:"City17", street:"Street 20").save(flush: true)

        render(view: "success")
    }
}
