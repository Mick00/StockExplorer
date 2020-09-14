package com.dukewallet.database.adaptors;

import com.dukewallet.models.assets.PriceHistory;
import com.dukewallet.models.assets.trends.sequences.Sequence;
import com.dukewallet.models.assets.trends.sequences.SequenceType;
import com.dukewallet.models.assets.trends.sequences.SimpleSequence;
import org.bson.Document;

import java.util.List;

public class SequenceAdaptor extends SimpleAdaptor<Sequence> {

    public static final String ATH = "ATH";
    public static final String ATL = "ATL";
    public static final String DELTA = "delta";
    public static final String RELATIVE_DELTA = "relativeDelta";
    public static final String PRICE_HISTORY = "priceHistory";
    public static final String TYPE = "type";

    @Override
    public Document toDocument(Sequence sequence) throws NoAdaptorsException {
        Document doc = new Document();
        doc.append(TYPE, sequence.getType().toString())
                .append(ATH, sequence.getHigh())
                .append(ATL, sequence.getLow())
                .append(DELTA, sequence.delta())
                .append(RELATIVE_DELTA, sequence.relativeDelta())
                .append(PRICE_HISTORY, Adaptors.forClass(PriceHistory.class)
                    .toDocument(sequence.getPriceHistory()).getList(PriceHistoryAdaptor.PRICE_HISTORY, Document.class));
        return doc;
    }

    @Override
    public Sequence fromDocument(Document doc) throws NoAdaptorsException {
        List<Document> priceHistory = doc.getList(PRICE_HISTORY, Document.class);
        return new SimpleSequence(
                Adaptors.forClass(PriceHistory.class)
                        .fromDocument(new Document(PriceHistoryAdaptor.PRICE_HISTORY, priceHistory)),
                SequenceType.valueOf(doc.getString(TYPE)),
                doc.getDouble(ATH),
                doc.getDouble(ATL),
                doc.getDouble(DELTA),
                doc.getDouble(RELATIVE_DELTA)
        );
    }

    @Override
    public Class<Sequence> getAdaptableClass() {
        return Sequence.class;
    }
}
