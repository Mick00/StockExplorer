package com.dukewallet.database.adaptors;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Adaptors {

    private static final List<Adaptor<?>> adaptors = new ArrayList<>();

    public static void init(){
        adaptors.add(new AssetAdaptor());
        adaptors.add(new StockAdaptor());
        adaptors.add(new AssetValueAdaptor());
        adaptors.add(new PriceHistoryAdaptor());
        adaptors.add(new TrendHistoryAdaptor());
        adaptors.add(new SequenceAdaptor());
    }

    public static void add(Adaptor<?> adaptor){
        adaptors.add(adaptor);
    }

    public static List<Adaptor<?>> all(){
        return adaptors;
    }

    public static <T> Adaptor<T> forClass(Class<T> clazz) throws NoAdaptorsException {
        Optional<Adaptor<?>> found = adaptors.stream()
                .filter(adaptor -> adaptor.getAdaptableClass().equals(clazz))
                .findFirst();
        if (found.isEmpty()){
            throw new NoAdaptorsException(clazz);
        }
        return (Adaptor<T>) found.get();
    }

    public static <T> Adaptor<T> forObject(T object) throws NoAdaptorsException {
        Class<T> clazz = (Class<T>) object.getClass();
        return Adaptors.forClass(clazz);
    }
}
