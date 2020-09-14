package com.dukewallet.models.assets.trends.sequences;

import com.dukewallet.models.assets.AssetValue;
import com.dukewallet.models.assets.HasPriceHistory;
import com.dukewallet.models.assets.PriceHistories;
import com.dukewallet.models.assets.PriceHistory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class TrendHistory implements HasPriceHistory, Iterable<Sequence> {
    private final List<Sequence> trendHistory;

    public TrendHistory(){
        trendHistory = new ArrayList<>();
    }

    public TrendHistory(List<Sequence> trendHistory){
        this.trendHistory = trendHistory;
    }

    private void appendSequence(SimpleSequence sequence){
        trendHistory.add(sequence);
    }

    @Override
    public PriceHistory getPriceHistory() {

        return PriceHistories.concat(trendHistory.stream()
                .map(Sequence::getPriceHistory)
                .collect(Collectors.toList()));
    }

    @Override
    public Iterator<Sequence> iterator() {
        return trendHistory.iterator();
    }

    public static TrendHistory fromPrices(PriceHistory priceHistory){
        if (priceHistory.size() <= 1) return historyOfSizeOne(priceHistory);

        TrendHistory trends = new TrendHistory();
        Iterator<AssetValue> iterator = priceHistory.iterator();
        AssetValue startOfSequence = iterator.next();
        AssetValue last = startOfSequence, next = null;
        while (iterator.hasNext()){
            next = iterator.next();
            if (trendInverted(startOfSequence,next)){
                trends.appendSequence(new SimpleSequence(priceHistory.slice(startOfSequence, last)));
                startOfSequence = last = next;
            } else {
                last = next;
            }
        }
        return trends;
    }

    private static TrendHistory historyOfSizeOne(PriceHistory priceHistory){
        TrendHistory trendHistory = new TrendHistory();
        trendHistory.appendSequence(new SimpleSequence(priceHistory));
        return trendHistory;
    }

    public static boolean trendInverted(AssetValue start, AssetValue end){
        return (start.priceIncreased() && end.priceDecreased())||(start.priceDecreased() && end.priceIncreased());
    }

    public String toString(){
        return "{@TrendHistory, sequences:["+
                trendHistory.stream().map(Sequence::toString).collect(Collectors.joining())
                +"]}";
    }
}
