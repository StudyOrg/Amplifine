package amplifine

import org.bson.types.ObjectId

class Sales {

    ObjectId id

    Map customer
    List goods

    Date dateSale

    ObjectId shop
    ObjectId userSale

    static mapWith = "mongo"

    static mapping = {
        version false

        collection "sales"
    }
}
