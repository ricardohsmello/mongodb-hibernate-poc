# mongodb-hibernate-poc

This project demonstrates how to integrate MongoDB with Hibernate using the MongoDB Hibernate dialect.

## Prerequisites

- Java 17+ (tested with Corretto 17 and 21)
- Maven 3.9+
- Docker (to run a MongoDB replica set)
- Local clone of the [mongo-hibernate](https://github.com/mongodb/mongo-hibernate) project

## Start MongoDB as a Replica Set
The Hibernate MongoDB dialect requires a replica set.
1. Start a MongoDB container:
    ```
    docker run --rm -p 27017:27017 --name mongo-rs mongo:8.0 \
    --replSet rs0 --bind_ip_all
    ```
2. Open a new terminal and connect to the node:
   ```
   mongosh
   ```
3. Initialize the replica set:
   ```
    rs.initiate({
    _id: "rs0",
    members: [{ _id: 0, host: "localhost:27017" }]
    })
    ```
4. Verify it’s running:
   ```
   rs.status()
   ```
You should see stateStr: "PRIMARY" for the member.

## Install the mongo-hibernate library locally
This project depends on the [mongo-hibernate](https://github.com/mongodb/mongo-hibernate)
library, which is not yet published on Maven Central.
Because of that, we need to build it manually and publish it to our local Maven repository `(~/.m2/repository)`.

Follow these steps:
1. Clone the repository **mongo-hibernate**:
   ```
   git clone https://github.com/mongodb/mongo-hibernate.git
   ```
2. Navigate into the folder:
   ```
   cd mongo-hibernate
   ```
3. Publish to your local Maven repository:
   ```
   ./gradlew publishToMavenLocal
    ```
    This will compile the project and place the generated artifacts under ~/.m2/repository, making them available as dependencies in other Maven projects (like this POC).
 
## - Running the POC
1. Clone this project:
   ```
   git clone https://github.com/ricardohsmello/mongodb-hibernate-poc.git
   ```
2. Navigate to the project folder:
   ```
   cd mongodb-hibernate-poc
   ```
3. Run the application:
   ```
   mvn compile exec:java -Dexec.mainClass="com.mongodb.Main"
   ```
4. You’ll see the interactive menu in the terminal:
    ````
    ==== MongoDB Hibernate POC ====
    1 - Create a new Book
    2 - List all Books
    3 - Find Book by Title
    4 - Find Book by ID
    5 - Delete Book by ID
    0 - Exit
    Choose an option:
    ```