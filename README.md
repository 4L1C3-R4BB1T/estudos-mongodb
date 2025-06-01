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

- **Sorting**

```js
/* 1 for ascending order, and -1 for descending order */
db.collection.find(query).sort(sort)

/* sorted alphabetically from A to Z */
db.companies.find({ category_code: "music" }).sort({ name: 1 })

/* sorted alphabetically from A to Z, ensure consistent sort order */
db.companies.find({ category_code: "music" }).sort({ name: 1, _id: 1 })
```

- **Limiting**

```js
db.companies.find(query).limit(number)

/* the three music companies with the highest number of employees */
db.companies
    .find({ category_code: "music" })
    .sort({ number_of_employees: -1, _id: 1 })
    .limit(3)
```

- **Include a Field**

```js
db.collection.find(query, { field: 1 })

/* return business name, result, and _id fields only */
db.inspections.find(
    { sector: "Restaurant - 818" },
    { business_name: 1, result: 1 }
)
```

- **Exclude a Field**

```js
db.collection.find(query, { field: 0, field: 0 })

/* exclude date and zip code */
db.inspections.find(
    { result: { $in: ["Pass", "Warning"] } },
    { date: 0, "address.zip": 0 }
)
```

- **Count Documents**

```js
db.collection.countDocuments(query, options)

/* count number of docs in trip collection */
db.trips.countDocuments({})

/* count number of trips over 120 minutes by subscribers */
db.trips.countDocuments({ tripduration: { $gt: 120 }, usertype: "Subscriber" })
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

#### Delete

```js
/* removes a single document from a collection */
db.collection.deleteOne(filter, { options })

/* removes all documents that match the filter from a collection */
db.collection.deleteMany(filter, { options })
```

---

## Aggregation

- **Aggregation:** Collection and summary of data.
- **Stage:** One of the built-in methods that can be completed on the data, but does not permanently alter it.
- **Aggregation pipeline:** A series of stages completed on the data in order.

```js
db.collection.aggregate([
    {
        $stage1: {
            { expression1 },
            { expression2 }...
        },
        $stage2: {
            { expression1 }...
        }
    }
])
```

#### Stages

- **$match:** filters for documents that match specified conditions.

```js
db.collection.aggregate([
    {
        $match: {
            "field_name": "value"
        }
    }
])

/* example */
db.zips.aggregate([
    {   
        $match: { 
            state: "CA"
        }
    }
])
```

- **$group:** groups documents by a group key.

```js
db.collection.aggregate([
    {
        $group:
            {
                _id: <expression>, // Group key
                <field>: { <accumulator> : <expression> }
            }
    }
])

/* example */
db.zips.aggregate([
    {   
        $match: { 
            state: "CA"
        }
    },
    {
        $group: {
            _id: "$city",
            totalZips: { $count : { } }
        }
    }
])
```

- **$sort:** sorts all input documents and returns them to the pipeline in sorted order. We use 1 to represent ascending order, and -1 to represent descending order.

```js
db.collection.aggregate([
    {
        $sort: {
            "field_name": 1
        }
    }
])
```

- **$limit:** returns only a specified number of records.

```js
db.collection.aggregate([
    {
        $limit: 5
    }
])

/* example */
db.zips.aggregate([
    {
        $sort: {
            pop: -1
        }
    },
    {
        $limit:  5
    }
])
```

- **$project:** specifies the fields of the output documents. 1 means that the field should be included, and 0 means that the field should be supressed. The field can also be assigned a new value.

```js
db.collection.aggregate([
    {
        $project: {
            state: 1, 
            zip: 1,
            population: "$pop",
            _id: 0
        }
    }
])
```

- **$count:** creates a new document, with the number of documents at that stage in the aggregation pipeline assigned to the specified field name.

```js
db.collection.aggregate([
    {
        $count: "total_zips"
    }
])
```

- **$set:** creates new fields or changes the value of existing fields, and then outputs the documents with the new fields.

```js
db.collection.aggregate([
    {
        $set: {
            place: {
                $concat:["$city",",","$state"]
            },
            pop: 10000
        }
    }
])
```

- **$out:** writes the documents that are returned by an aggregation pipeline into a colection. Must be the last stage. Creates a nesw collection if it does not already exists. If collection exists, $out replaces the existing collection with new data.

```js
db.collection.aggregate([
    {
        $out: {
            db: "<db>",
            coll: "<newcollection>"
        }
    }
])

/* example */
db.zips.aggregate([
    {
        $group: {
            _id: "$state",
            total_pop: { $sum: "$pop" }
        }
    }, 
    {   
        $match: { 
            total_pop: { $lt: 1000000 }
        }
    },
    {
        $out: "small_states"
    }
])

```

---

## Indexes

### Single Field Indexes

#### Create a Single Field Index

```js
db.customers.createIndex({ birthdate: 1 })
```

#### Create a Unique Single Field Index

```js
db.customers.createIndex({ email: 1 }, { unique: true })
```

#### View the Indexes used in a Collection

```js
db.customers.getIndexes()
```

#### Check if an index is being used on a query

```js
db.customers.explain().find({ birthdate: { $gt: ISODate("1995-08-01") } })
```

### Multikey Indexes

Any index where one of the indexed fields contains an array. The array can hold nested objects or other field types. In a compound index, only one field can be an array per index. 

#### Create a Single field Multikey Index

```js
db.customers.createIndex({ accounts: 1 })
```

### Compound Indexes

Index on multiple fields. Can be a multikey index if it includes an array field. Maximum of one array field per index. Support queries that match on the prefix of the fields.

#### Create a Compound Index

```js
db.customers.createIndex({ active: 1, birthdate: -1, name: 1 })
```

#### Order of Fields in a Compound Index

The order of the fields matters when creating the index and the sort order. It is recommended to list the fields in the following order: Equality, Sort, and Range.

- Equality: field/s that matches on a single field value in a query
- Sort: field/s that orders the results by in a query
- Range: field/s that the query filter in a range of valid values

### Deleting Indexes

```js
/* delete index by name */
db.customers.dropIndex('active_1_birthdate_-1_name_1')

/* delete index by key */
db.customers.dropIndex({ active: 1, birthdate: -1, name: 1 })

/* delete all the indexes from a collection */ 
/* with the exception of the default index on _id */
db.customers.dropIndexes()

/* delete a specific list of indexes */
db.collection.dropIndexes(['index1name', 'index2name', 'index3name'])

/* hides an index */
/* assess the impact of removing the index on query performance */
db.customers.hideIndex({ email: 1 })
```

---

🔗 Links
* [MongoDB University](https://learn.mongodb.com)  
* [Documentation](https://www.mongodb.com/pt-br/docs/manual/tutorial/getting-started/)
