package amplifine

import org.bson.types.ObjectId

class Shops {

    ObjectId id

    String name
    Map address

    static mapWith = "mongo"

    static mapping = {
        version false

        collection "shops"
    }
}
