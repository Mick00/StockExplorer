package com.dukewallet.database.adaptors;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.List;

public interface Adaptor<T> {
    Document toDocument(T object) throws NoAdaptorsException;

    T fromDocument(Document doc) throws NoAdaptorsException;

    String getCollectionName();

    MongoCollection<Document> getCollection();

    List<T> get(Bson bson) throws NoAdaptorsException;

    T getByID(Object string) throws NoAdaptorsException;

    boolean isCollection();

    Class<T> getAdaptableClass();
}
