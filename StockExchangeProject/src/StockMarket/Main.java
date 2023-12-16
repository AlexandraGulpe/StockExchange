package StockMarket;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        StockMarket stockMarketSimulation = new StockMarket();

        // 3 possible stock prices for the same symbol
        List<Stock> appleStocks = new ArrayList<>();
        List<Stock> teslaStocks = new ArrayList<>();
        Collections.addAll(appleStocks,
                new Stock("AAPL", 44.6, 1000),
                new Stock("AAPL", 50.0, 1000),
                new Stock("AAPL", 44.1, 50));


        Collections.addAll(teslaStocks,
                new Stock("TSLA", 251.5, 1000),
                new Stock("TSLA", 255.2, 50),
                new Stock("TSLA", 248.2, 1000));


        stockMarketSimulation.addStock(appleStocks);
        stockMarketSimulation.addStock(teslaStocks);

        //simulation scenario
        int numberOfBuyers = 10;
        int numberOfSellers = 8;

        Collection<Thread> threads = new ArrayList<>();

        for (int i = 0; i < numberOfBuyers; i++){
            Thread buyerThread = new Thread(new Buyer(stockMarketSimulation, "AAPL", 10, 44.6));
            buyerThread.setName("Buyer " + i);
            buyerThread.start();
            threads.add(buyerThread);
        }

        for (int i = 0 ; i < numberOfSellers; i++){
            Thread sellerThread = new Thread(new Seller(stockMarketSimulation, "TSLA", 50, 56.7));
            sellerThread.setName("Seller " + i);
            sellerThread.start();
            threads.add(sellerThread);
        }

        // joining threads in the end
        for (var thread : threads){
            try {
                thread.join();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        stockMarketSimulation.closeChannel();

        // printing the remaining quantities at the end of business hours
        Collection<List<Stock>> stocks = stockMarketSimulation.getAllStocks();
        for (var stock : stocks){
            for (var innerStock : stock){
                System.out.println("Remaining quantity for " + innerStock.getSymbol() + " at " + innerStock.getPrice() + " : " + innerStock.getQuantity());
            }
        }
    }
}
