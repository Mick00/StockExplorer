package com.dukewallet.models.assets.stocks;

import com.dukewallet.models.assets.*;
import com.dukewallet.models.assets.trends.sequences.TrendHistory;

public class Stock extends Asset implements StockSymbol, HasPriceHistory {

    private TrendHistory trendHistory;

    public Stock(){super();}

    public Stock(String symbol) {
        super(AssetType.STOCK, symbol);
        trendHistory = new TrendHistory();
    }

    public Stock(String symbol, PriceHistory priceHistory){
        super(AssetType.STOCK, symbol);
        this.trendHistory = TrendHistory.fromPrices(priceHistory);
    }

    public Stock(String symbol, TrendHistory trendHistory){
        super(AssetType.STOCK, symbol);
        this.trendHistory = trendHistory;
    }

    @Override
    public PriceHistory getPriceHistory() {
        return trendHistory.getPriceHistory();
    }

    public TrendHistory getTrendHistory() {return trendHistory;}

    public static StockSymbol symbol(String symbol){
        return new Stock(symbol);
    }

    public String toString(){
        return "{"+getSymbol()+","+trendHistory+"}";
    }

}
