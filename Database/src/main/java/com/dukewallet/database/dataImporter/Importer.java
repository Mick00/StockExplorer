package com.dukewallet.database.dataImporter;

public interface Importer {

    void importData() throws ImportException;
}
