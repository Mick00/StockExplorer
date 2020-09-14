package com.dukewallet.server.dataFetchers;

import com.dukewallet.database.collections.Stocks;
import com.dukewallet.models.assets.stocks.Stock;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

public class StockDataFetcher implements DataFetcher<Stock> {

    @Override
    public Stock get(DataFetchingEnvironment env) throws Exception {
        return Stocks.get(Stock.symbol(env.getArgument("symbol")));
    }
}
