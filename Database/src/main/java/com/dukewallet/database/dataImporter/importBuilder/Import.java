package com.dukewallet.database.dataImporter.importBuilder;

import com.dukewallet.models.assets.stocks.StockSymbol;

public class Import {
    public static StockImportBuilder stock(StockSymbol symbol){
        return new StockImportBuilder(symbol);
    }
}
