environments {
    development {
        mongo {
            host = "localhost"
            port = 27017
            databaseName = "test"
            connectionString = "mongodb://localhost/test"
        }
    }
    test {
        mongo {
            host = "localhost"
            port = 27017
            databaseName = "test"
            connectionString = "mongodb://localhost/test"
        }
    }
    production {
        mongo {
            host = "localhost"
            port = 27017
            databaseName = "test"
            connectionString = "mongodb://localhost/test"
        }
    }
}