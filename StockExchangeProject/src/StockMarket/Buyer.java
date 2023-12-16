package StockMarket;

import java.util.concurrent.ThreadLocalRandom;

// the Buyer thread
public class Buyer implements Runnable {
    private StockMarket stockMarket;
    private String symbol;
    private int quantity;
    private double wantedPrice;


    public Buyer(StockMarket stockMarket, String symbol, int quantity, double wantedPrice) {
        this.stockMarket = stockMarket;
        this.symbol = symbol;
        this.quantity = quantity;
        this.wantedPrice = wantedPrice;
    }

    // for now, the buyer is attempting to buy 1 time
    @Override
    public void run() {
        try {
            stockMarket.buyStock(symbol, quantity, wantedPrice);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
