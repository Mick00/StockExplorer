package com.dukewallet.server;

import com.dukewallet.server.dataFetchers.StockDataFetcher;
import com.dukewallet.server.wires.StockRuntimeWire;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.TypeRuntimeWiring;

import java.util.ArrayList;
import java.util.List;

public class APIResolver {

    private List<TypeRuntimeWiring> typesWiring;

    public APIResolver(){
        this.typesWiring = new ArrayList<>();
        addTypesWiring();
    }

    public void addTypesWiring(){
        typesWiring.add((new StockRuntimeWire()).getRuntimeWiring());
    }

    public RuntimeWiring getWiring(){
        RuntimeWiring.Builder builder = RuntimeWiring.newRuntimeWiring();
        for (TypeRuntimeWiring typeWiring : typesWiring) {
            builder.type(typeWiring);
        }
        return addQueries(builder).build();
    }

    public RuntimeWiring.Builder addQueries(RuntimeWiring.Builder wireBuilder){
        wireBuilder.type("Query", builder -> builder.dataFetcher("stock", new StockDataFetcher()));
        return wireBuilder;
    }

}
