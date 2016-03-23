package amplifine.gen.tables

import amplifine.gen.MongoGenerator
import groovy.transform.Canonical

@Canonical
class ShopsData {
    String name = null
    String address = null
    String tel = null
}

class ShopsGenerator implements MongoGenerator {

    static ShopsData[] data = [
            ["General Music Store", "Proletary street, 20, 1", "88135557070"],
            ["North Cither", "Line-3 ave, 11, 345-5", "88125550002"],
            ["Syntesis", "Radical street, 1, 110-2", "88125553412"],
            ["Mad Guitars", "Round ave, 45-2, 1", "88125559987"],
            ["Music Stuff", "Line-2 ave, 123, 32A", "012"],
            ["Classique", "Central street, 1, 1B", "88125550001"],
            ["Deep Bass", "Lowpass street, 4, 22", "88125550405"]
    ]

    static int getMaxPossibleRecords() {
        return data.size()
    }

    Boolean insertAll() {

    }
}
