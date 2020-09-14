package com.dukewallet.database.dataImporter;

import com.dukewallet.models.assets.AssetValue;
import com.dukewallet.models.assets.PriceHistory;
import com.dukewallet.models.assets.SimplePriceHistory;
import com.dukewallet.models.assets.stocks.Stock;
import com.dukewallet.models.assets.stocks.StockSymbol;
import com.dukewallet.database.Database;
import com.dukewallet.database.NotACollectionException;
import com.dukewallet.database.adaptors.NoAdaptorsException;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class YahooStockHistoryImporter implements Importer{

    private final StockSymbol stock;
    private LocalDate start;
    private LocalDate end;

    public YahooStockHistoryImporter(StockSymbol stock, LocalDate start, LocalDate end){
        this.stock = stock;
        this.start = start;
        this.end = end;
    }

    public void importData() throws ImportException {
        try {
            String csvFileData = fetchData();
            Stock stock = parseData(csvFileData);
            Database.save(stock);
        } catch (IOException | InterruptedException | CsvValidationException | ParseException | NoAdaptorsException | NotACollectionException e){
            throw new ImportException(e.getMessage());
        }
    }

    private String fetchData() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(getURI())
                .timeout(Duration.ofMinutes(2))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(20))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200){
            throw new IOException("Request not ok");
        }
        return response.body();
    }

    private URI getURI(){
        long startTimeStamp = Timestamp.valueOf(start.atTime(LocalTime.of(1,0))).getTime()/1000;
        long endTimeStamp = Timestamp.valueOf(end.atTime(LocalTime.of(23,0))).getTime()/1000;
        return URI.create("https://query1.finance.yahoo.com/v7/finance/download/"+stock.getSymbol()+"?period1="+startTimeStamp+"&period2="+endTimeStamp+"&interval=1d&events=history");
    }

    private Stock parseData(String data) throws IOException, CsvValidationException, ParseException {
        CSVReader reader = new CSVReader(new StringReader(data));
        String[] fields = reader.readNext();//Skipping first row
        SimplePriceHistory priceHistory = new SimplePriceHistory();
        while((fields = reader.readNext()) != null){
            priceHistory.addValue(getAssetValue(fields));
        }
        return new Stock(stock.getSymbol(), priceHistory);
    }


    private AssetValue getAssetValue(String[] fields){
        return new AssetValue(
                LocalDate.parse(fields[0]).atTime(23,59),
                Double.parseDouble(fields[1]),
                Double.parseDouble(fields[2]),
                Double.parseDouble(fields[3]),
                Double.parseDouble(fields[4]),
                Integer.parseInt(fields[6])
        );
    }
}
