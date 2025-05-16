package com.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.BsonValue;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;

public class Main {
	public static void main(String[] args) {
		String connectionString = "mongodb+srv://admin:admin@cluster0.rzsj7nu.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";

		MongoClient mongoClient = MongoClients.create(connectionString);

		// List Databases
		List<Document> databases = mongoClient.listDatabases().into(new ArrayList<>());
		databases.forEach(db -> System.out.println(db.toJson()));

		MongoDatabase db = mongoClient.getDatabase("bank");
		MongoCollection<Document> collection = db.getCollection("accounts");

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

		// Update One
		Bson queryUpdateOne = Filters.eq("account_id", "MDB87236121");
		Bson updatesOne = Updates.combine(
				Updates.set("account_status", "active"),
				Updates.inc("balance", 100));
		UpdateResult upResultOne = collection.updateOne(queryUpdateOne, updatesOne);
		System.out.println("Update One: " + upResultOne.getModifiedCount());

		// Update Many
		Bson queryUpdateMany = Filters.eq("account_type", "savings");
		Bson updatesMany = Updates.combine(Updates.set("minimum_balance", 100));
		UpdateResult upResultMany = collection.updateMany(queryUpdateMany, updatesMany);
		System.out.println("Update Many: " + upResultMany.getModifiedCount());

		// Delete One
		Bson queryDeleteOne = Filters.eq("account_holder", "john doe");
		DeleteResult delResultOne = collection.deleteOne(queryDeleteOne);
		System.out.println("Delete One: " + delResultOne.getDeletedCount());

		// Delete Many
		Bson queryDeleteMany = Filters.eq("account_status", "dormant");
		DeleteResult delResultMany = collection.deleteMany(queryDeleteMany);
		// DeleteResult delResultMany =
		// collection.deleteMany(Filters.eq("account_status", "dormant"));
		System.out.println("Delete Many: " + delResultMany.getDeletedCount());

		// Aggregate
		MongoCollection<Document> accounts2 = db.getCollection("accounts");

		System.out.println("\nMatch Stage:");
		matchStage(accounts2);

		System.out.println("\n\nMatch and Group Stages:");
		matchAndGroupStages(accounts2);

		System.out.println("\n\nMatch, Sort and Group Stages:");
		matchSortAndProjectStages(accounts2);

	}

	private static void matchStage(MongoCollection<Document> accounts) {
		Bson matchStage = Aggregates.match(Filters.eq("account_id", "MDB79101843"));
		System.out.println("Display aggregation results");
		accounts.aggregate(Arrays.asList(matchStage)).forEach(document -> System.out.print(document.toJson()));
	}

	private static void matchAndGroupStages(MongoCollection<Document> accounts) {
		Bson matchStage = Aggregates.match(Filters.eq("account_id", "MDB79101843"));
		Bson groupStage = Aggregates.group("$account_type",
				Accumulators.sum("total_balance", "$balance"),
				Accumulators.avg("average_balance", "$balance"));
		System.out.println("Display aggregation results");
		accounts.aggregate(Arrays.asList(matchStage, groupStage))
				.forEach(document -> System.out.print(document.toJson()));
	}

	private static void matchSortAndProjectStages(MongoCollection<Document> accounts) {
		Bson matchStage = Aggregates
				.match(Filters.and(Filters.gt("balance", 1500), Filters.eq("account_type", "checking")));
		Bson sortStage = Aggregates.sort(Sorts.orderBy(Sorts.descending("balance")));
		Bson projectStage = Aggregates
				.project(Projections.fields(
						Projections.include("account_id", "account_type", "balance"),
						Projections.computed("euro_balance", new Document("$divide", Arrays.asList("$balance", 1.20F))),
						Projections.excludeId()));
		System.out.println("Display aggregation results");
		accounts.aggregate(
				Arrays.asList(matchStage, sortStage, projectStage))
				.forEach(document -> System.out.print(document.toJson()));
	}

}