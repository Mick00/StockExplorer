package com.dukewallet.database;

import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

public class MoreFilters {
    public static Bson id(Document doc){
        return id(doc.get("_id"));
    }

    public static Bson id(Object id){
        return Filters.eq("_id", id);
    }
}
