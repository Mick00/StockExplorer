package com.dukewallet;

import com.dukewallet.database.collections.Stocks;
import com.dukewallet.database.dataImporter.ImportException;
import com.dukewallet.database.dataImporter.importBuilder.Import;
import com.dukewallet.database.Database;
import com.dukewallet.models.assets.stocks.Stock;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args){
        StockExplorer stockExplorer = new StockExplorer();
        System.out.println(Stocks.get(Stock.symbol("SPY")));
        //importStock();
    }

    public static void importStock(){
        try {
            Import.stock(Stock.symbol("SPY"))
                    .setStart(LocalDate.now().minusYears(1))
                    .buildImporter()
                    .importData();
        } catch (ImportException e) {
            e.printStackTrace();
        }
    }
}
