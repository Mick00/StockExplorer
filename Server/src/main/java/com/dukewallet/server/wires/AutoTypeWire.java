package com.dukewallet.server.wires;

import graphql.schema.PropertyDataFetcher;
import graphql.schema.idl.TypeRuntimeWiring;

import java.lang.reflect.Field;

public abstract class AutoTypeWire<T> {
    public TypeRuntimeWiring getRuntimeWiring(){
        Class<T> clazz = getWireableClass();
        TypeRuntimeWiring.Builder wiring = TypeRuntimeWiring.newTypeWiring(clazz.getSimpleName());
        for (Field properties : clazz.getDeclaredFields()) {
            if (isScalar(properties.getType())){
                wiring.dataFetcher(properties.getName(), propertyFetcher(properties.getName()));
            }
        }
        addFields(wiring);
        return wiring.build();
    }

    public void addFields(TypeRuntimeWiring.Builder wiringBuilder){}

    public abstract Class<T> getWireableClass();

    private boolean isScalar(Class<?> clazz){
        return clazz.equals(int.class) ||
                clazz.equals(Integer.class) ||
                clazz.equals(double.class) ||
                clazz.equals(Double.class) ||
                clazz.equals(float.class) ||
                clazz.equals(Float.class) ||
                clazz.equals(String.class) ||
                clazz.equals(boolean.class) ||
                clazz.equals(Boolean.class) ||
                clazz.isEnum();
    }

    private PropertyDataFetcher<T> propertyFetcher(String propertyName){
        return new PropertyDataFetcher<T>(propertyName);
    }

}
