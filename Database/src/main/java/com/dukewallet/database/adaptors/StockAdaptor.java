package com.dukewallet.database.adaptors;

import com.dukewallet.models.assets.Asset;
import com.dukewallet.models.assets.PriceHistory;
import com.dukewallet.models.assets.stocks.Stock;
import com.dukewallet.models.assets.trends.sequences.TrendHistory;
import org.bson.Document;

import java.util.List;

public class StockAdaptor extends SimpleAdaptor<Stock> {

    public StockAdaptor(){
        super("Assets");
    }

    @Override
    public Document toDocument(Stock stock) throws NoAdaptorsException {
        Document doc = Adaptors.forClass(Asset.class).toDocument(stock);
        List<Document> trends = Adaptors.forClass(TrendHistory.class)
                .toDocument(stock.getTrendHistory())
                .getList("trends", Document.class);
        doc.append("trends", trends);
        return doc;
    }

    @Override
    public Stock fromDocument(Document doc) throws NoAdaptorsException {
        List<Document> trendsHistory = doc.getList("trends", Document.class);
        return new Stock(doc.getString("_id"),
                Adaptors.forClass(TrendHistory.class)
                        .fromDocument(new Document("trends", trendsHistory)));
    }

    @Override
    public Class<Stock> getAdaptableClass() {
        return Stock.class;
    }
}
