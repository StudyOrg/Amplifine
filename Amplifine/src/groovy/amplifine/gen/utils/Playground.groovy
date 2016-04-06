package amplifine.gen.utils

import amplifine.gen.dictionaries.CheapEGDictionary

Ring ring = new Ring(0, 20)

for (i in CheapEGDictionary.getModels(200)) {
    println i
}

for (i in ring) {
    print i + "_"
}