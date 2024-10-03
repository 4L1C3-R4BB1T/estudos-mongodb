package com.example;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class Connection {
	public static void main(String[] args) {
		String connectionString = "mongodb+srv://admin:admin@cluster0.rzsj7nu.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";

		// try (MongoClient mongoClient = MongoClients.create(connectionString)) {
		// List<Document> databases = mongoClient.listDatabases().into(new
		// ArrayList<>());
		// databases.forEach(db -> System.out.println(db.toJson()));
		// }

		MongoClient mongoClient = MongoClients.create(connectionString);
		// MongoDatabase database = mongoClient.getDatabase("sample_training");
		// MongoCollection<Document> collection = database.getCollection("inspections");

		// Document inspection = new Document("_id", new ObjectId())
		// 		.append("id", "10021-2015-ENFO")
		// 		.append("certificate_number", 9278806)
		// 		.append("business_name", "ATLIXCO DELI GROCERY INC.")
		// 		.append("date",
		// 				Date.from(LocalDate.of(2015, 2, 20)
		// 						.atStartOfDay(ZoneId.systemDefault())
		// 						.toInstant()))
		// 		.append("result", "No Violation Issued")
		// 		.append("sector", "Cigarette Retail Dealer - 127")
		// 		.append("address",
		// 				new Document()
		// 						.append("city", "RIDGEWOOD")
		// 						.append("zip", 11385)
		// 						.append("street", "MENAHAN ST")
		// 						.append("number", 1712));

		// InsertOneResult result = collection.insertOne(inspection);
		// BsonValue id = result.getInsertedId();

		// System.out.println(id);

		MongoDatabase database = mongoClient.getDatabase("bank");
		MongoCollection<Document> collection = database.getCollection("accounts");

		try (MongoCursor<Document> cursor = collection
				.find(Filters.and(
						Filters.gte("balance", 1000),
						Filters.eq("account_type", "checking")))
				.iterator()) {
			while (cursor.hasNext()) {
				System.out.println(cursor.next().toJson());
			}
		}

		Document doc = collection.find(Filters.and(
				Filters.gte("balance", 1000),
				Filters.eq("account_type", "checking"))).first();

		System.out.println("Find First: " + doc.toJson());

	}
}