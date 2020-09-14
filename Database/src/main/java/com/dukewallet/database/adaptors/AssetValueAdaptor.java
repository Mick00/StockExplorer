package com.dukewallet.database.adaptors;

import com.dukewallet.models.assets.AssetValue;
import org.bson.Document;

import java.time.*;

public class AssetValueAdaptor extends SimpleAdaptor<AssetValue> {

    @Override
    public Document toDocument(AssetValue assetValue) {
        String date = assetValue.atDate().toString();
        return new Document()
                .append("at",assetValue.atDate())
                .append("open", assetValue.getOpen())
                .append("high", assetValue.getHigh())
                .append("low", assetValue.getLow())
                .append("close", assetValue.getClose())
                .append("volume", assetValue.getVolume());
    }

    @Override
    public AssetValue fromDocument(Document doc) throws NoAdaptorsException {
        LocalDateTime at = LocalDateTime.ofEpochSecond(doc.getDate("at").getTime()/1000,0, ZoneOffset.ofHours(0));
        return new AssetValue(
                at,
                doc.getDouble("open"),
                doc.getDouble("high"),
                doc.getDouble("low"),
                doc.getDouble("close"),
                doc.getInteger("volume")
        );
    }

    @Override
    public Class<AssetValue> getAdaptableClass() {
        return AssetValue.class;
    }
}
