package com.dukewallet.database.adaptors;

public class NoAdaptorsException extends Exception {

    public NoAdaptorsException(Class<?> clazz){
        super("No adaptors for class "+ clazz.getSimpleName());
    }

}
