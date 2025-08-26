# mongodb-hibernate-poc

This project demonstrates how to integrate MongoDB with Hibernate using the MongoDB Hibernate dialect.

## Prerequisites

- Java 17+ (tested with Corretto 17 and 21)
- Maven 3.9+
- Docker (to run a MongoDB replica set)
- Local clone of the [mongo-hibernate](https://github.com/mongodb/mongo-hibernate) project

## Running the sample

### Step 1 - Run MongoDB Replica Set
 
The Hibernate MongoDB dialect requires a replica set
Start a local MongoDB container:

```
docker run --rm -p 27017:27017 --name mongo-rs mongo:8.0 \
--replSet rs0 --bind_ip_all
```

Initialize the replica set:
```
mongosh
```
Then:
```
rs.initiate({
  _id: "rs0",
  members: [{ _id: 0, host: "localhost:27017" }]
})
```
### Step 3 - Install the mongo-hibernate library locally
This project depends on the [mongo-hibernate](https://github.com/mongodb/mongo-hibernate)
library, which is not yet published on Maven Central.

```
git clone https://github.com/mongodb/mongo-hibernate.git
cd mongo-hibernate

./gradlew publishToMavenLocal

```

### Step 4 - Running the POC
```
git clone https://github.com/ricardohsmello/mongodb-hibernate-poc.git
cd mongodb-hibernate-poc

mvn compile exec:java -Dexec.mainClass="com.mongodb.Main"
```
In the terminal, you’ll see the menu:

````
==== MongoDB Hibernate POC ====
1 - Create a new Book
2 - List all Books
3 - Find Book by Title
4 - Delete Book by ID
0 - Exit
Choose an option:
```