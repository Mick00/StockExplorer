package com.dukewallet.models.assets;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


public class SimplePriceHistory implements PriceHistory {

    SortedSet<AssetValue> values;

    public SimplePriceHistory(){
        values = new TreeSet<>();
    }

    public SimplePriceHistory(Collection<AssetValue> values){
        this.values = new TreeSet<>(values);
    }

    @Override
    public void addValue(AssetValue value){
        values.add(value);
    }

    @Override
    public double ATH() {
        return values.stream().max(Comparator.comparing(AssetValue::getHigh)).get().getHigh();
    }

    @Override
    public double ATL() {
        return values.stream().min(Comparator.comparing(AssetValue::getLow)).get().getLow();
    }

    @Override
    public LocalDateTime firstDate() {
        return values.first().atDate();
    }

    @Override
    public LocalDateTime lastDate() {
        return values.last().atDate();
    }

    @Override
    public SortedSet<AssetValue> values() {
        return values;
    }

    @Override
    public int size() {
        return values.size();
    }

    public String toString(){
        return "[Price History:\n"+
                values.stream().map(Object::toString).collect(Collectors.joining("\n"))+
                "]";
    }

    @Override
    public Iterator<AssetValue> iterator() {
        return values.iterator();
    }

    public PriceHistory slice(LocalDateTime from, LocalDateTime to){
        return slice(new AssetValue(from), new AssetValue(to));
    }

    public PriceHistory slice(AssetValue from, AssetValue to){
        if (from.atDate().equals(to.atDate())){
            SimplePriceHistory priceHistory = new SimplePriceHistory();
            priceHistory.addValue(from);
            return priceHistory;
        }
        return new SimplePriceHistory(values.subSet(from, to));
    }


    public SimplePriceHistory concat(PriceHistory priceHistory){
        ArrayList<AssetValue> list = new ArrayList<>(this.values);
        list.addAll(priceHistory.values());
        return new SimplePriceHistory(list);
    }

}
