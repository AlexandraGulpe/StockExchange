package StockMarket;

import java.util.ArrayList;
import java.util.Collection;

public class Main {
    public static void main(String[] args) {
        StockMarket stockMarketSimulation = new StockMarket();
        stockMarketSimulation.addStock(new Stock("AAPL", 44.6, 1000));
        stockMarketSimulation.addStock(new Stock("MSFT", 56.7, 1000));

        int numberOfBuyers = 10;
        int numberOfSellers = 8;

        Collection<Thread> threads = new ArrayList<>();

        for (int i = 0; i < numberOfBuyers; i++){
            Thread buyerThread = new Thread(new Buyer(stockMarketSimulation, "AAPL", 10, 44.6, 1234, 0));
            buyerThread.setName("Buyer " + i);
            buyerThread.start();
            threads.add(buyerThread);
        }

        for (int i = 0 ; i < numberOfSellers; i++){
            Thread sellerThread = new Thread(new Seller(stockMarketSimulation, "MSFT", 50, 56.7, 1234, 0));
            sellerThread.setName("Seller " + i);
            sellerThread.start();
            threads.add(sellerThread);
        }

        for (var thread : threads){
            try {
                thread.join();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        stockMarketSimulation.closeChannel();
        Collection<Stock> stocks = stockMarketSimulation.getAllStocks();
        for (var stock : stocks){
            System.out.println("Remaining quantity for " + stock.getSymbol() + " : " + stock.getQuantity());
        }
    }
}
