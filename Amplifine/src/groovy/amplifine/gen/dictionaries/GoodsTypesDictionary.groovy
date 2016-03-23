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

    static float getPrice() {
        Random rn = new Random(System.nanoTime());

        float minPrice = 10000.0f
        float maxPrice = 50000.0f

        return rn.nextFloat() * (maxPrice - minPrice) + minPrice
    }

    static String getAnyType() {
        Random rn = new Random(System.nanoTime());

        return data[rn.nextInt(data.size())]
    }

}
