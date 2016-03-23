package amplifine

import org.bson.types.ObjectId

class Supplies {

    ObjectId id

    Map supplier
    List goods

    Date dateCreated

    ObjectId shop
    ObjectId userCreated

    static mapWith = "mongo"

    static mapping = {
        version false

        collection "supplies"
    }
}
