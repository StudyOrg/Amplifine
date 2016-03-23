package gen.utils

class Randomifier {

    Random rn;

    Randomifier(Random rn) {
        this.rn = rn
    }

    public getColour() {
        return ModelsWordbook.colours[rn.nextInt(ModelsWordbook.colours.size())]
    }

    public getNoun() {
        return ModelsWordbook.nouns[rn.nextInt(ModelsWordbook.nouns.size())]
    }

    public getAdjective() {
        return ModelsWordbook.adjectives[rn.nextInt(ModelsWordbook.adjectives.size())]
    }

    public getMaterial() {
        return ModelsWordbook.materials[rn.nextInt(ModelsWordbook.materials.size())]
    }

    public getAccessories() {
        StringBuilder str = new StringBuilder()
        def dice = rn.nextInt(1000)
        float coef = (ModelsWordbook.bands.size() * ModelsWordbook.books.size()) / ModelsWordbook.accessories.size()
        if(dice in 0..(500 * coef)) {
            str.append(
                    String.format("%s - %s",
                            ModelsWordbook.bands[rn.nextInt(ModelsWordbook.bands.size())],
                            ModelsWordbook.books[rn.nextInt(ModelsWordbook.books.size())]
                    )
            )
        } else {
            str.append(
                    ModelsWordbook.accessories[rn.nextInt(ModelsWordbook.accessories.size())]
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
