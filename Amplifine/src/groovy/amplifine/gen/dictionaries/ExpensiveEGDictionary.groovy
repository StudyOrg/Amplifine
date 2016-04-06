package amplifine.gen.dictionaries

class ExpensiveEGDictionary {

    static String[] manufacturers = [
            "Fender", "Gibson", "Peavey"/*, "Ibanez", "Ernie Ball", "Epiphone", "Hofner", "Gretsch"*/
    ]

    static String[] modelsFirstPart = [
            "Crazy", "Super", "Smashed"/*, "Royal", "Deep", "Magnified", "Power", "Burned", "Smoked", "Drived", "Irritating", "Moaning", "Craven", "Mad", "Endless", "Stashed"*/,
            "Amazing"
    ]

    static String[] modelsSecondPart = [
            "Suitcase", "Flower", "Keys"/*, "Craft", "Wine", "Dragon", "Concrete", "Blowhole", "String", "Glimpse", "Echo", "Powder", "Rose", "Flash", "Phase", "Sound", "Moisture"*/
    ]

    static getAllCombinations() {
        ArrayList<String> resultList = []

        for (i in manufacturers) {
            for (j in modelsFirstPart) {
                for (k in modelsSecondPart) {
                    resultList << i + " " + j + " " + k
                }
            }
        }

        return resultList
    }
}
