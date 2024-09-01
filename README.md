## MongoDB

* Data is organized into documents, collections, and databases.

* Documents are stored in BSON, which supports a large range of data types, including all JSON data types, dates, numbers, and ObjectIds.

* Every document requires an ```_id``` field, which acts as a primary key or unique identifier. If an inserted document doesn’t have an ```_id``` field, MongoDB automatically generates one.

* MongoDB has a flexible schema, which means that documents with different structures can be stored in the same collection.

---

## Document Structure

The values in a document can be any data type, including strings, objects, arrays, booleans, nulls, dates, ObjectIds, and more. 

```json
// Syntax
{
    "key": value,
    "key": value,
    "key": value
}

// Example
{
    "_id": 1,
    "name": "AC3 Phone",
    "colors": ["black", "silver"],
    "price": 200,
    "available": true
}
```

MongoDB has a flexible schema model, which means that documents in the same collection are not required to share a common structure of fields and value types by default.

```json
// Document A
{
    "username": "vreddy",
    "name": "Vasanti Reddy",
    "email": "vreddy1@gmail.com",
    "location": {
        "city": "Delhi",
        "country": "India"
    }
}

// Document B
{
    "username": "avasa",
    "name": "Asad Vasa",
    "email": "avasa1@yahoo.com",
    "social_media": {
        "Twitter": "avasa",
        "Instagram": "Asad101",
        "LinkedIn": "AsadVasa"
    },
    "location": {
        "city": "Los Angeles",
        "country": "United States"
    }
}
```

---

## Data Modeling

Data modeling is the process of defining how data is stored and the process of defining the relationships that exist among different entities in the data.

A proper data model:
- Makes it easier to manage your data
- Makes queries more efficient
- Uses less memory and CPU
- Reduces costs

Types of relationships among data:
- **One-to-one:** relationship where a data entity in one set is connected to exactly one data entity in another set.
- **One-to-many:** relationship where a data entity in one set is connected to any number of data entities in another set.
- **Many-to-many:** relationship where any number of data entities in one set are connected to any number of data entities in another set.

**Embedded** documents store related data in a single document. Embedding data simplifies queries because it avoids application joins. It fulfills the principle that data that is accessed together should be stored together. 

- Embedding data provides better performance for read operations. 
- Embedded documents enable you to store all kinds of related information in a single document.
- Embedding data will make the document larger and impact write performance. As more data is added to each document, the entire document is rewritten into MongoDB data storage.
- Unbounded documents caused by embedding will eventually run into storage problems by exceeding the maximum document size of 16 MB.
- To prevent unbounded document sizes that may result from embedding, you can break up your data into multiple collecitons and use references to keep frequently accessed data together.

**Reference** relationships store data by linking references in one document to another document. 

- Referencing allows you to store data in two different collections and ensure that the collections are related. This avoids duplication of data. 
- Referencing avoids duplication of data and, in most cases, results in smaller documents.
- References save the ```_id``` field of one document in another document as a link between the two.

---

## Connection

#### MongoDB Shell

```bash
mongosh "mongodb+srv://mdb-training-cluster.swnn5.mongodb.net/myFirstDatabase" --apiVersion 1 --username MDBUser
```

#### MongoDB Compass

```bash
mongodb+srv://MDBUser:<password>@mdb-training-cluster.swnn5.mongodb.net/test
```

#### Application

```bash
mongodb+srv://MDBUser:<password>@mdb-training-cluster.swnn5.mongodb.net/myFirstDatabase?retryWrites=true&w=majority
```

---

## CRUD Operations

#### Insert

```js
/* single document */
db.collection.insertOne(document)

/* multiple documents */
db.collection.insertMany([document1, document2, ...])
```

#### Find

```js
db.collection.find({ field: value })
```

- **Comparison Operators**

```js
/* equals values in the array*/
db.collection.find({ field: { $in: [value1, value2, ....] } })

/* not in the values in the array*/
db.collection.find({ field: { $nin: [value1, value2, ....] } })

/* equals */
db.collection.find({ field: { $eq: value } })

/* not equals */
db.collection.find({ field: { $ne: value } })

/* greater than */
db.collection.find({ field: { $gt: value } })

/* less than */
db.collection.find({ field: { $lt: value } })

/* greater than or equal to */
db.collection.find({ field: { $gte: value } })

/* less than or equal to */
db.collection.find({ field: { $lte: value } })
```

- **Logical Operators**

```js
db.collection.find({ $and: [{ expression1 }, { expression2 }, ...] })

db.collection.find({ field: { $not: { operator-expression } } })

db.collection.find({ $nor: [{ expression1 }, { expression2 }, ...] })

db.collection.find({ $or: [{ expression1 }, { expression2 }, ...] })

```

- **Querying on Array**

```js
/* all elements */
db.collection.find({ field: { $all: [value1, value2, ...] } })

/* querying on array elements */
db.collection.find({ field: { $elemMatch: { query1, query2, ... } } })

db.collection.find({ field: { $size: value } })
```

#### Update

```js
/* replaces a single document that match a specified filter */
db.collection.replaceOne(filter, replacement, { options })

/* updates a single document that match a specified filter */
db.collection.updateOne(filter, update, { options })

/* update all documents that match a specified filter */
db.collection.updateMany(filter, update, { options })

/* find and replace a single document */
db.collection.findAndModify(document)

/* example */
db.podcasts.findAndModify({
    query: { _id: ObjectId("6261a92dfee1ff300dc80bf1") },
    update: { $inc: { subscribers: 1 } },
    new: true,
})
```

- **$set:** replaces the value of a field with the specified value.

```js
db.podcasts.updateOne(
    { _id: ObjectId("5e8f8f8f8f8f8f8f8f8f8f8") },
    { $set: { subscribers: 98562 } }
)

db.books.updateMany(
    { publishedDate: { $lt: new Date("2019-01-01") } },
    { $set: { status: "LEGACY" } }
)
```

- **upsert:** creates a new document if no documents match the filtered criteria.

```js
db.podcasts.updateOne(
    { title: "The Developer Hub" },
    { $set: { topics: ["databases", "MongoDB"] } },
    { upsert: true }
)
```

- **$push:** adds a new value to the hosts array field.

```js
db.podcasts.updateOne(
    { _id: ObjectId("5e8f8f8f8f8f8f8f8f8f8f8") },
    { $push: { hosts: "Nic Raboy" } }
)
```

---

🔗 Links
* [MongoDB University](https://learn.mongodb.com)  
* [Documentation](https://www.mongodb.com/pt-br/docs/manual/tutorial/getting-started/)
