package amplifine.gen.data

import groovy.transform.Canonical

@Canonical
class WorkersData {
    String name
    String surname

    Map documents

    String city

    Date birthday
    Date fired
}
