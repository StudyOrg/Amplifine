package mongodb

import com.mongodb.MongoClient
import com.mongodb.client.*

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
}
