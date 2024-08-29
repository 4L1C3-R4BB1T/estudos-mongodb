package com.example;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class Connection {
    public static void main(String[] args) {
        String connectionString = "mongodb+srv://admin:admin@cluster0.rzsj7nu.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
        
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            List<Document> databases = mongoClient.listDatabases().into(new ArrayList<>());
            databases.forEach(db -> System.out.println(db.toJson()));
        }
    }
}