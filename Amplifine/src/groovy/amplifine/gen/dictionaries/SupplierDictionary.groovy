package amplifine.gen.dictionaries

class SupplierDictionary {
    static String[][] names = [
            ["Red Maple Import", "North"],
            ["Fine Tune", "Central"],
            ["Good Sound Music", "South"],
            ["Jeffery & Co", "Central"],
            ["Fender Authorized Trade", "East"],
            ["Take 5", "Central"],
            ["Olly and Brothers", "West"],
            ["123 Music", "East"],
            ["David Crossman Instruments", "North"],
            ["Music Life", "South"]
    ]


    static def generateRandomSupplier() {
        Random rn = new Random(System.nanoTime());
        def supplier = [:]

        def name = names[rn.nextInt(names.size())]

        supplier.name = name[0]
        supplier.surnames = name[1]

        return supplier
    }
}
