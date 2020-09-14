package com.dukewallet.database;

import com.dukewallet.database.adaptors.Adaptor;

public class NotACollectionException extends Exception {

    public NotACollectionException(Adaptor<?> adaptor){
        super(adaptor.getClass().getSimpleName()+" is not a collection");
    }

}
