package amplifine.gen.dictionaries

import groovy.transform.Canonical

@Canonical
class ManufacturerData {
    String name = "Noname"
    String country = "USA"
}

class Manufacturers {
    static ManufacturerData[] data = [
            ["Fender", "USA"],
            ["Squier", "China"],
            ["Peavy", "USA"],
            ["ChinaCraft", "China"],
            ["HandGuitar", "China"],
            ["Ibanez", "USA"],
            ["Ibanez Korea", "Korea"],
            ["Roland", "Japan"],
            ["Hofner", "Germany"],
            ["Stentor", "Australia"],
            ["Aeolian Company", "USA"],
            ["American Piano Company", "USA"],
            ["Heintzman and Co", "Canada"],
            ["Petzold", "France"],
            ["Dunlop", null]
    ]

    static int getMaxPossibleRecords() {
        return data.size()
    }

    static ManufacturerData findByName(String name) {
        return data.find {
            it.name == name
        }
    }

    static ManufacturerData getAnyManufacturer() {
        Random rn = new Random(System.nanoTime());

        return data[rn.nextInt(data.size())]
    }
}
