package com.dukewallet.database.adaptors;

import com.dukewallet.models.assets.trends.sequences.Sequence;
import com.dukewallet.models.assets.trends.sequences.TrendHistory;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class TrendHistoryAdaptor extends SimpleAdaptor<TrendHistory>{
    @Override
    public Document toDocument(TrendHistory trends) throws NoAdaptorsException {
        Document doc = new Document();
        Adaptor<Sequence> seqAdaptor = Adaptors.forClass(Sequence.class);
        List<Document> sequences = new ArrayList<>();
        for (Sequence seq: trends){
            sequences.add(seqAdaptor.toDocument(seq));
        }
        doc.append("trends",sequences);
        return doc;
    }

    @Override
    public TrendHistory fromDocument(Document doc) throws NoAdaptorsException {
        Adaptor<Sequence> sequenceAdaptor = Adaptors.forClass(Sequence.class);
        List<Sequence> sequences = new ArrayList<>();
        for (Document sequenceDoc : doc.getList("trends", Document.class)) {
            sequences.add(sequenceAdaptor.fromDocument(sequenceDoc));
        }
        return new TrendHistory(sequences);
    }

    @Override
    public Class<TrendHistory> getAdaptableClass() {
        return TrendHistory.class;
    }
}
