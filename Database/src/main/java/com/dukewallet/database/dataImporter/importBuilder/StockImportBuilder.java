package com.dukewallet.database.dataImporter.importBuilder;

import com.dukewallet.models.assets.stocks.StockSymbol;
import com.dukewallet.database.dataImporter.Importer;
import com.dukewallet.database.dataImporter.YahooStockHistoryImporter;

import java.time.LocalDate;

public class StockImportBuilder implements ImportBuilder{
    private LocalDate start;
    private LocalDate end;
    private StockSymbol symbol;

    public StockImportBuilder(StockSymbol symbol){
        this.symbol = symbol;
        this.start = LocalDate.now().minusYears(1);
        this.end = LocalDate.now();
    }

    @Override
    public Importer buildImporter() {
        return new YahooStockHistoryImporter(symbol, start, end);
    }

    public LocalDate getStart() {
        return start;
    }

    public StockImportBuilder setStart(LocalDate start) {
        this.start = start;
        return this;
    }

    public LocalDate getEnd() {
        return end;
    }

    public StockImportBuilder setEnd(LocalDate end) {
        this.end = end;
        return this;
    }

    public StockSymbol getSymbol() {
        return symbol;
    }

    public StockImportBuilder setSymbol(StockSymbol symbol) {
        this.symbol = symbol;
        return this;
    }

}
