package amplifine

import org.bson.types.ObjectId

class Address {
    ObjectId id

    String city
    String street

    Map houseFlatMap

    static mapWith = "mongo"

    static mapping = {
        version false

        collection "address"
    }

    static constraints = {
        city(nullable: false)
        street(nullable: false)

        houseFlatMap(nullable: true)
    }
}
