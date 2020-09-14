package com.dukewallet.models.assets;

import java.time.LocalDateTime;
import java.util.SortedSet;

public interface PriceHistory extends Iterable<AssetValue> {
    void addValue(AssetValue value);
    double ATH();
    double ATL();
    LocalDateTime firstDate();
    LocalDateTime lastDate();
    SortedSet<AssetValue> values();
    int size();
    PriceHistory slice(LocalDateTime from, LocalDateTime to);
    PriceHistory slice(AssetValue from, AssetValue to);
}
