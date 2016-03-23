package amplifine.gen.dictionaries

class GoodsTypesDictionary {
    static String[] data = [
            "Guitar",
            "Piano",
            "Violin",
            "Bass-guitar",
            "Synthesizer",
            "Harp",
            "Accessory"
    ]

    static int getMaxPossibleRecords() {
        return data.size()
    }

    static String getAnyType() {
        Random rn = new Random(System.nanoTime());

        return data[rn.nextInt(data.size())]
    }

}
