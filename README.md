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

🔗 Links
* [MongoDB University](https://learn.mongodb.com)  
* [Documentation](https://www.mongodb.com/docs/manual)
