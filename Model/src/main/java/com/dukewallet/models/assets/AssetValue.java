package com.dukewallet.models.assets;

import com.dukewallet.models.assets.trends.sequences.SequenceType;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AssetValue implements Value, Comparable<AssetValue> {

    private LocalDateTime at;
    private double open, high, low, close;
    private int volume;

    public AssetValue(LocalDateTime at){
        this.at = at;
        open = high = low = close = volume = 0;
    }

    public AssetValue(LocalDateTime at, double open, double high, double low, double close, int volume) {
        this.at = at;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
    }

    public LocalDateTime atDate() {
        return at;
    }

    public double getOpen() {
        return open;
    }

    public double getHigh() {
        return high;
    }

    public double getLow() {
        return low;
    }

    public double getClose() {
        return close;
    }

    public int getVolume() {
        return volume;
    }

    public boolean priceIncreased(){
        return open < close;
    }

    public boolean priceDecreased(){
        return open > close;
    }

    @Override
    public int compareTo(AssetValue o) {
        return atDate().compareTo(o.atDate());
    }

    public String toString(){
        return "{"+atDate().toString()+", "+open+", "+high+", "+low+", "+close+", "+volume+"}";
    }
}
