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