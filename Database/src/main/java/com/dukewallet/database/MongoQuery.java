package com.dukewallet.database;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

public interface MongoQuery {

    void exec(MongoCollection<Document> collection, Document doc);

}
