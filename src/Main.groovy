import com.mongodb.*

def host = "localhost"
def port = 27017
def databaseName = "test"

def client = new MongoClient(host, port)
def db = client.getDatabase(databaseName)
def cursor = db.getCollection(databaseName).find()

cursor.each { document ->
    document.keySet().each { key ->
        println "${key} : ${document[key]}"
    }
    println()
}