package com.dukewallet.database.adaptors;


import com.dukewallet.database.Database;
import com.dukewallet.models.assets.stocks.Stock;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public abstract class SimpleAdaptor<T> implements Adaptor<T> {

    protected String collectionName = null;

    protected SimpleAdaptor(String collectionName){
        this.collectionName = collectionName;
    }

    protected SimpleAdaptor(){}

    public String getCollectionName(){
        return this.collectionName;
    }

    public MongoCollection<Document> getCollection(){
        return Database.db().getCollection(getCollectionName());
    }

    @Override
    public List<T> get(Bson bson) throws NoAdaptorsException{
        Iterator<Document> documents = getCollection().find(bson).iterator();
        List<T> results = new ArrayList<>();
        while (documents.hasNext()){
            T result = fromDocument(documents.next());
            results.add(result);
        }
        return results;
    }

    public T getByID(Object object) throws NoAdaptorsException {
        Optional<T> result = Adaptors.forClass(getAdaptableClass())
                .get(Filters.eq("_id", object))
                .stream().findFirst();
        return result.orElse(null);
    }

    public boolean isCollection(){
        return getCollectionName() != null;
    }

}
