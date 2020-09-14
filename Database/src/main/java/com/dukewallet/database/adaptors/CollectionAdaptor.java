package com.dukewallet.database.adaptors;

import org.bson.Document;

import java.util.List;

public interface CollectionAdaptor<T> {

    List<Document> toDocuments(T collection);
}
