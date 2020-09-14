package com.dukewallet.database.adaptors;

import com.dukewallet.models.assets.Asset;
import com.dukewallet.models.assets.AssetType;
import org.bson.Document;

public class AssetAdaptor extends SimpleAdaptor<Asset> {

    public AssetAdaptor() {
        super("Assets");
    }

    @Override
    public Document toDocument(Asset asset) {
        return new Document()
                .append("_id", asset.getSymbol())
                .append("type", asset.getType().toString());
    }

    @Override
    public Asset fromDocument(Document doc) throws NoAdaptorsException {
        return new Asset(
                AssetType.valueOf(doc.getString("type")),
                doc.getString("_id")
            );
    }

    @Override
    public Class<Asset> getAdaptableClass() {
        return Asset.class;
    }
}
