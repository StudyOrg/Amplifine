package amplifine.gen.dictionaries

import amplifine.gen.utils.Randomifier

class AcousticGuitarsDictionary {

    static String[] manufacturers = [
            "Hofner", "Yamaha", "Ibanez", "Sigma Guitars", "Cole Clark", "Kahler", "Kremona", "Tobias", "Kay"
    ]

    static ArrayList<String> getModels(int num) {
        ArrayList<String> brands = []
        int count = 0
        int forceLimit = 1000

        Random rn = new Random(System.nanoTime())
        Randomifier rf = new Randomifier(rn)

        def patterns = ["AADD-AAAA", "AADD-ADDAAADDADA", "ADDDDD"]

        while (count < num) {
            String serial = rf.generateFromFormat(patterns[rn.nextInt(patterns.size())])

            if (brands.findIndexOf { it == serial } == -1) {
                brands << serial
                ++count
            } else {
                --forceLimit
                if (forceLimit < 0) {
                    println "Limit reached"
                    break
                }
            }
        }

        return brands
    }


}
