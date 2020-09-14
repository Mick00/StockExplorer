package com.dukewallet.server.wires;

import com.dukewallet.models.assets.stocks.Stock;
import com.dukewallet.server.dataFetchers.LambdaFetcher;
import graphql.schema.idl.TypeRuntimeWiring;

public class StockRuntimeWire extends AutoTypeWire<Stock> {
    @Override
    public Class<Stock> getWireableClass() {
        return Stock.class;
    }

    @Override public void addFields(TypeRuntimeWiring.Builder builder){
        builder.dataFetcher("priceHistory", new LambdaFetcher<>(Stock::getPriceHistory));
    }
}
