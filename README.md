## MongoDB

* Data is organized into documents, collections, and databases.

* Documents are stored in BSON, which supports a large range of data types, including all JSON data types, dates, numbers, and ObjectIds.

* Every document requires an _id field, which acts as a primary key or unique identifier. If an inserted document doesn’t have an _id field, MongoDB automatically generates one.

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

A proper data model
- Makes it easier to manage your data
- Makes queries more efficient
- Uses less memory and CPU
- Reduces costs

Types of relationships among data:
- One-to-one
- One-to-many
- Many-to-many

Embedded documents store related data in a single document.

Reference relationships store data by linking references in one document to another document.
