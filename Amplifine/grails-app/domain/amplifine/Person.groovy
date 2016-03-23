package amplifine

import org.bson.types.ObjectId

class Person {
    ObjectId id

    String name
    String surname

    ObjectId address

    static mapWith = "mongo"

    static mapping = {
        version false

        collection "person"
    }

    static constraints = {
        name(nullable: false)
        surname(nullable: false)

        address(nullable: true)
    }
}
