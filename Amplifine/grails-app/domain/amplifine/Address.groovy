package amplifine

import org.bson.types.ObjectId

class Address {
    ObjectId id

    String city
    String street

    static mapWith = "mongo"

    static mapping = {
        version false

        collection "address"
    }
}
