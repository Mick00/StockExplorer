package com.dukewallet.database;

import com.dukewallet.database.adaptors.Adaptor;
import com.dukewallet.database.adaptors.Adaptors;
import com.dukewallet.database.adaptors.NoAdaptorsException;
import com.dukewallet.models.assets.stocks.Stock;
import com.dukewallet.models.assets.stocks.StockSymbol;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.Optional;

public class Database {
    private static MongoClient client = null;

    public static void init()  {
        if (client == null){
            client = MongoClients.create();
            Adaptors.init();
        }
    }

    public static MongoClient client(){
        return client;
    }

    public static MongoDatabase db(){
        return client().getDatabase("stock_explorer");
    }

    public static <T> void save(T object) throws NoAdaptorsException, NotACollectionException {
        exec(object, (MongoCollection::insertOne));
    }

    public static <T> void exec(T object, MongoQuery query) throws NotACollectionException, NoAdaptorsException {
        Adaptor<T> adaptor = Adaptors.forObject(object);
        if (adaptor.isCollection()){
            Document document = adaptor.toDocument(object);
            query.exec(adaptor.getCollection(), document);
        } else {
            throw new NotACollectionException(adaptor);
        }
    }

/*    public static <T> void update(T object) throws  NoAdaptorsException, NotACollectionException {
        exec(object, ((collection, doc) -> {
            collection.updateOne(
                    MoreFilters.id(doc), Updates.set(doc));
        }
                ));
    }*/



}
