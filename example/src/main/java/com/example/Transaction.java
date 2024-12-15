package com.example;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.TransactionBody;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

public class Transaction {
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void main(String[] args) {
        String connectionString = "mongodb+srv://admin:admin@cluster0.rzsj7nu.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";

        final MongoClient client = MongoClients.create(connectionString);
        final ClientSession clientSession = client.startSession();

        TransactionBody txnBody = new TransactionBody<String>() {
            // Transactions
            public String execute() {
                MongoCollection<Document> bankingCollection = client.getDatabase("bank").getCollection("accounts");

                Bson fromAccount = Filters.eq("account_id", "MDB310054629");
                Bson withdrawal = Updates.inc("balance", -200);

                Bson toAccount = Filters.eq("account_id", "MDB643731035");
                Bson deposit = Updates.inc("balance", 200);

                System.out.println("This is from Account " + fromAccount.toBsonDocument().toJson()
                        + " withdrawn " + withdrawal.toBsonDocument().toJson());

                System.out.println("This is to Account " + toAccount.toBsonDocument().toJson()
                        + " deposited " + deposit.toBsonDocument().toJson());

                bankingCollection.updateOne(clientSession, fromAccount, withdrawal);
                bankingCollection.updateOne(clientSession, toAccount, deposit);

                return "Transferred funds from John Doe to Mary Doe";
            }
        };

        try {
            clientSession.withTransaction(txnBody);
        } catch (RuntimeException e) {
            System.out.println(e);
        } finally {
            clientSession.close();
        }

    }
}
