package com.dukewallet.models.assets;

public interface Value {
    public double getOpen();

    public double getHigh();

    public double getLow();

    public double getClose();

    public int getVolume();
}
