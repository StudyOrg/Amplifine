package amplifine.gen.dictionaries

import amplifine.gen.utils.Tools

class UsersDictionary {
    static String[] names = ["Kandy", "Carlota", "Jackqueline", "Cyndy", "Ivory", "Jonathon", "Lottie", "Kamala", "Dia", "Reid", "Carolyne", "Vanda",
                             "Debra", "Corliss", "Carole", "Shanae", "Laverne", "Thelma", "Carolann", "Arlen"]

    static String[] surnames = ["Brown", "Buckland", "Burgess", "Butler", "Cameron", "Campbell", "Carr", "Chapman", "Churchill", "Clark", "Clarkson", "Coleman", "Cornish",
                                "Davidson", "Davies", "Dickens", "Dowd", "Duncan"]

    static String generateRandomPhone() {
        Random rn = new Random(System.nanoTime());

        StringBuffer bf = new StringBuffer()

        bf.append("+")
        bf.append(rn.nextInt(200))
        bf.append(" (")
        bf.append(Tools.randomInRange(rn, 100, 999))
        bf.append(") ").append(Tools.randomInRange(rn, 100, 999)).append("-").append(Tools.randomInRange(rn, 10, 99)).append("-").append(Tools.randomInRange(rn, 10, 99))

        return bf.toString()
    }

    static generateRandomUser() {
        Random rn = new Random(System.nanoTime());
        def user = [:]

        user.name = names[rn.nextInt(names.size())]
        user.surnames = surnames[rn.nextInt(surnames.size())]
        user.phone = generateRandomPhone()

        return user
    }
}
