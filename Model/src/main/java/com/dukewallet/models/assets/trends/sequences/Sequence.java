package com.dukewallet.models.assets.trends.sequences;

import com.dukewallet.models.assets.AssetValue;
import com.dukewallet.models.assets.PriceHistory;

import java.time.LocalDateTime;

public interface Sequence extends Iterable<AssetValue> {

    SequenceType getType();
    double getHigh();
    double getLow();
    double fromValue();
    double toValue();
    double delta();
    double relativeDelta();
    LocalDateTime getStart();
    LocalDateTime getEnd();
    PriceHistory getPriceHistory();

}
