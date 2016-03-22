package amplifine

import org.bson.types.ObjectId

class Person {
    ObjectId id

    String name
    String surname

    static mapWith = "mongo"

    static mapping = {
        version false

        collection "person"
        database "test"
    }
}
