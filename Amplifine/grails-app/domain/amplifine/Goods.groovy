package amplifine

import org.bson.types.ObjectId

class Goods {

    ObjectId id

    String manufacturer
    String model

    String type

    BigDecimal retailPrice

    static mapWith = "mongo"

    static mapping = {
        version false

        collection "goods"
    }

    static constraints = {
        manufacturer(nullable: false)
        model(nullable: false)
        type(nullable: false)

        retailPrice(nullable: false)
    }
}
