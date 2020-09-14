package com.dukewallet.database.adaptors;

import com.dukewallet.models.assets.AssetValue;
import com.dukewallet.models.assets.PriceHistory;
import com.dukewallet.models.assets.SimplePriceHistory;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;

public class PriceHistoryAdaptor extends SimpleAdaptor<PriceHistory> {

    public static final String PRICE_HISTORY = "priceHistory";

    @Override
    public Document toDocument(PriceHistory priceHistory) throws NoAdaptorsException {
        Document doc = new Document();
        Adaptor<AssetValue> adaptor = Adaptors.forClass(AssetValue.class);
        List<Document> values = new ArrayList<>();
        for (AssetValue value : priceHistory) {
            values.add(adaptor.toDocument(value));
        }
        doc.append(PRICE_HISTORY, values);
        return doc;
    }

    @Override
    public PriceHistory fromDocument(Document doc) throws NoAdaptorsException {
        PriceHistory priceHistory = new SimplePriceHistory();
        for (Document value : doc.getList(PRICE_HISTORY, Document.class)) {
            priceHistory.addValue(Adaptors.forClass(AssetValue.class).fromDocument(value));
        }
        return priceHistory;
    }



    public Class<PriceHistory> getAdaptableClass() {
        return PriceHistory.class;
    }
}
