package amplifine.gen.tables

import amplifine.gen.MongoGenerator
import amplifine.gen.data.ShopData
import mongodb.MongoDBUtil

class ShopsGenerator implements MongoGenerator {

    static ShopData[] data = [
            [name: "General Music Store", address: [city: "Calimport", street: "Proletary street", house: "20, 1"], tel: "88135557070"],
            [name: "North Cither", address: [city: "Waterdeep", street: "Line-3 ave", house: "11, 345-5"], tel: "88125550002"],
            [name: "Syntesis", address: [city: "Boomtown", street: "Radical street 1", house: "110-2"], tel: "88125553412"],
            [name: "Mad Guitars", address: [city: "Calimport", street: "Round ave", house: "45-2"], tel: "88125559987"],
            [name: "Music Stuff", address: [city: "City 17", street: "Line-2 ave", house: "123-32A"], tel: "012"],
            [name: "Classique", address: [city: "Detroit", street: "Central street", house: "1-B"], tel: "88125550001"],
            [name: "Deep Bass", address: [city: "City 17", street: "Lowpass street", house: "4-22"], tel: "88125550405"],
            [name: "Sweet Sound House", address: [city: "Northumberland", street: "Lowland ave", house: "1-88"], tel: "+3634325550405"]
    ]

    static int getMaxPossibleRecords() {
        return data.size()
    }

    Boolean insertAll() {
        def db = MongoDBUtil.getDB()

        def record

        println "Вставка магазинов..."

        Boolean status = true
        for (result in data) {
            record = [:]

            record << [name: result.name]
            record << [address: result.address]
            record << [tel: result.tel]

            status = (db.getCollection("shops") << record)
            if (!status) {
                break
            }
        }

        return status
    }
}
