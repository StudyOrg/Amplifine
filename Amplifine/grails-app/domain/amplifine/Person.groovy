package amplifine

class Person {

    String _id
    int balls

    static mongoTypeName = 'person'
    static mongoFields = [
            'b' : 'balls'
    ]

}

