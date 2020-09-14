package com.dukewallet.database.collections;

import com.dukewallet.database.adaptors.Adaptor;
import com.dukewallet.database.adaptors.Adaptors;
import com.dukewallet.database.adaptors.NoAdaptorsException;
import com.dukewallet.models.assets.stocks.Stock;
import com.dukewallet.models.assets.stocks.StockSymbol;

import java.time.LocalDateTime;

public class Stocks {

    public static Stock get(StockSymbol symbol){
        try {
            return getAdaptor().getByID(symbol.getSymbol());
        } catch (NoAdaptorsException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Stock getStockFrom(StockSymbol symbol, LocalDateTime from){
        return null;
    }

    public static Stock getStockFromTo(Stock symbol, LocalDateTime from, LocalDateTime to){
        return null;
    }

    public static Adaptor<Stock> getAdaptor() throws NoAdaptorsException {
        return Adaptors.forClass(Stock.class);
    }
}
