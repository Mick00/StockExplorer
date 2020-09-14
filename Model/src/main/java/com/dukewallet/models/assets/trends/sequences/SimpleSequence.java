package com.dukewallet.models.assets.trends.sequences;

import com.dukewallet.models.assets.AssetValue;
import com.dukewallet.models.assets.PriceHistory;

import java.time.LocalDateTime;
import java.util.Iterator;

public class SimpleSequence implements Sequence {

    private PriceHistory priceHistory;
    private SequenceType type;
    private double ATH, ATL, delta, relativeDelta;

    public SimpleSequence(PriceHistory priceHistory){
        this.priceHistory = priceHistory;
        this.ATH = priceHistory.ATH();
        this.ATL = priceHistory.ATL();
        double open = this.priceHistory.values().first().getOpen();
        double close = this.priceHistory.values().last().getClose();
        setType(open, close);
        this.delta = close - open;
        this.relativeDelta = this.delta/open;
    }

    public SimpleSequence(PriceHistory slice, SequenceType type, double ATH, double ATL, double delta, double relativeDelta) {
        this.priceHistory = slice;
        this.type = type;
        this.ATH = ATH;
        this.ATL = ATL;
        this.delta = delta;
        this.relativeDelta = relativeDelta;
    }

    private void setType(double open, double close){
        type = open <= close?SequenceType.POSITIVE:SequenceType.NEGATIVE;
    }

    @Override
    public SequenceType getType() {
        return type;
    }

    @Override
    public double getHigh() {
        return ATH;
    }

    @Override
    public double getLow() {
        return ATL;
    }

    @Override
    public double fromValue() {
        return priceHistory.values().first().getOpen();
    }

    @Override
    public double toValue() {
        return priceHistory.values().last().getClose();
    }

    @Override
    public double delta() {
        return delta;
    }

    @Override
    public double relativeDelta() {
        return relativeDelta;
    }

    @Override
    public LocalDateTime getStart() {
        return priceHistory.firstDate();
    }

    @Override
    public LocalDateTime getEnd() {
        return priceHistory.lastDate();
    }

    @Override
    public PriceHistory getPriceHistory() {
        return priceHistory;
    }

    @Override
    public Iterator<AssetValue> iterator() {
        return getPriceHistory().iterator();
    }

    public String toString(){
        return "{@Sequence, type:"+type+", ATH:"+ATH+ ", ATL:"+ATL+", priceHistory:"+priceHistory+"}";
    }
}
