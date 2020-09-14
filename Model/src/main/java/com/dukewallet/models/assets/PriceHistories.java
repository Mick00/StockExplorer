package com.dukewallet.models.assets;

import java.util.ArrayList;
import java.util.Collection;

public class PriceHistories {

    public static PriceHistory concat(Collection<PriceHistory> histories){
        ArrayList<AssetValue> values = new ArrayList<>();
        for (PriceHistory ph : histories) {
            values.addAll(ph.values());
        }
        return new SimplePriceHistory(values);
    }
}
