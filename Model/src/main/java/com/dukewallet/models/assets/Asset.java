package com.dukewallet.models.assets;

import com.dukewallet.models.assets.stocks.StockSymbol;

public class Asset implements StockSymbol {

    private AssetType assetType;
    private String symbol;

    public Asset(){ }

    public Asset(AssetType assetType, String symbol){
        this.assetType = assetType;
        this.symbol = symbol;
    }

    public AssetType getType(){
        return assetType;
    }

    public String getSymbol(){
        return this.symbol;
    }
}
