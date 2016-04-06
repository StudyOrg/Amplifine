package mongodb

import com.mongodb.MongoClient
import com.mongodb.client.MongoCursor
import com.mongodb.client.MongoDatabase
import groovy.json.JsonSlurper
import org.bson.Document

class MongoDBUtil {
    private static final String HOST = "localhost"
    private static final Integer PORT = 27017
    private static final String DB_NAME = "test"

    private static client
    private static db

    public static MongoDatabase getDB() {
        if (!client) {
            client = new MongoClient(HOST, PORT)
        }

        if (!db) {
            db = client.getDatabase(DB_NAME)
        }

        return db
    }

    public static def getAllRecords(String collection) {
        def selectedCollection = getDB().getCollection(collection)

        def records = []
        MongoCursor<Document> iterator = selectedCollection.find().iterator()

        while (iterator.hasNext()) {
            records << new JsonSlurper().parseText(iterator.next().toJson())
        }

        return records
    }
}
