package com.dukewallet;

import com.dukewallet.database.Database;


public class StockExplorer {

    public StockExplorer(){
        setUp();
    }

    private void setUp(){
        Database.init();
    }
}
