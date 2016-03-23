package amplifine.gen.utils

import amplifine.gen.dictionaries.ModelsDictionary

class Randomifier {

    Random rn;

    Randomifier(Random rn) {
        this.rn = rn
    }

    public getColour() {
        return ModelsDictionary.colours[rn.nextInt(ModelsDictionary.colours.size())]
    }

    public getNoun() {
        return ModelsDictionary.nouns[rn.nextInt(ModelsDictionary.nouns.size())]
    }

    public getAdjective() {
        return ModelsDictionary.adjectives[rn.nextInt(ModelsDictionary.adjectives.size())]
    }

    public getMaterial() {
        return ModelsDictionary.materials[rn.nextInt(ModelsDictionary.materials.size())]
    }

    public getAccessories() {
        StringBuilder str = new StringBuilder()
        def dice = rn.nextInt(1000)
        float coef = (ModelsDictionary.bands.size() * ModelsDictionary.books.size()) / ModelsDictionary.accessories.size()
        if(dice in 0..(500 * coef)) {
            str.append(
                    String.format("%s - %s",
                            ModelsDictionary.bands[rn.nextInt(ModelsDictionary.bands.size())],
                            ModelsDictionary.books[rn.nextInt(ModelsDictionary.books.size())]
                    )
            )
        } else {
            str.append(
                    ModelsDictionary.accessories[rn.nextInt(ModelsDictionary.accessories.size())]
            )
        }
        return str.toString()
    }

    public String generateUniqueName() {
        StringBuilder str = new StringBuilder()
        def fmts = ["J N (C)", "J (C M)", "J N (AAAA-DDDD)", "J N (C M)"]
        def dice = rn.nextInt(fmts.size())
        return generateFromFormat(fmts[dice])
    }

    public String generateFromFormat(String format) {
        // 'D' - digits
        // 'A' - letters
        // 'N' - nouns
        // 'J' - adjectives
        // 'M' - materials
        // 'C' - colour
        int variants = ('Z' as char) - ('A' as char)
        StringBuilder str = new StringBuilder()

        for(def ch in format) {
            switch (ch) {
                case 'D':
                    str.append((rn.nextInt(10) + ('0' as char)) as char)
                    break
                case 'A':
                    str.append((rn.nextInt(variants) + ('A' as char)) as char)
                    break
                case 'N':
                    str.append(getNoun())
                    break
                case 'J':
                    str.append(getAdjective())
                    break
                case 'M':
                    str.append(getMaterial())
                    break
                case 'C':
                    str.append(getColour())
                    break
                default:
                    str.append(ch)
            }

        }

        return str.toString()
    }

}
