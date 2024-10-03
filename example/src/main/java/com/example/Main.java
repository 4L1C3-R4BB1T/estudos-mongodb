package com.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.BsonValue;
import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.InsertOneResult;

public class Main {
	public static void main(String[] args) {
		String connectionString = "mongodb+srv://admin:admin@cluster0.rzsj7nu.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";

		MongoClient mongoClient = MongoClients.create(connectionString);

		// List Databases
		List<Document> databases = mongoClient.listDatabases().into(new ArrayList<>());
		databases.forEach(db -> System.out.println(db.toJson()));

		MongoDatabase database = mongoClient.getDatabase("bank");
		MongoCollection<Document> collection = database.getCollection("accounts");

		// Insert One
		Document inspection = new Document()
				.append("account_holder", "mary jhonson")
				.append("account_id", "MDB87236121")
				.append("balance", 987)
				.append("account_type", "checking");

		InsertOneResult resultOne = collection.insertOne(inspection);
		BsonValue id = resultOne.getInsertedId();

		System.out.println(id);

		// Insert Many
		Document doc1 = new Document()
				.append("account_holder", "john doe")
				.append("account_id", "MDB99115881")
				.append("balance", 1785)
				.append("account_type", "checking");

		Document doc2 = new Document()
				.append("account_holder", "jane doe")
				.append("account_id", "MDB79101843")
				.append("balance", 1468)
				.append("account_type", "checking");

		List<Document> accounts = Arrays.asList(doc1, doc2);

		InsertManyResult resultMany = collection.insertMany(accounts);
		resultMany.getInsertedIds().forEach((x, y) -> System.out.println(y.asObjectId()));

		// Find
		try (MongoCursor<Document> cursor = collection
				.find(Filters.and(
						Filters.gte("balance", 1000),
						Filters.eq("account_type", "checking")))
				.iterator()) {
			while (cursor.hasNext()) {
				System.out.println(cursor.next().toJson());
			}
		}

		// Find First
		Document doc = collection.find(Filters.and(
				Filters.gte("balance", 1000),
				Filters.eq("account_type", "checking"))).first();

		System.out.println("Find First: " + doc.toJson());

	}
}