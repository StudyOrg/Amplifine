package amplifine

import org.bson.types.ObjectId

class Users {

    ObjectId id

    String name
    String surname

    Map documents

    String city
    Date birthDate

    Date firedDate

    static mapWith = "mongo"

    static mapping = {
        version false

        collection "users"
    }

    static constraints = {
        name(nullable: false)
        surname(nullable: false)

        documents(nullable: false)

        city(nullable: false)
        birthDate(nullable:false)

        firedDate(nullable: true)
    }
}
