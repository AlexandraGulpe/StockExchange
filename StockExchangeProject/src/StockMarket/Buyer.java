package StockMarket;

import java.util.concurrent.ThreadLocalRandom;

public class Buyer implements Runnable {
    private StockMarket stockMarket;
    private String symbol;
    private int quantity;
    private double wantedPrice;
    private int taxID;
    private int nrSuccessfulTransactions;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getWantedPrice() {
        return wantedPrice;
    }

    public void setWantedPrice(double wantedPrice) {
        this.wantedPrice = wantedPrice;
    }

    public Buyer(StockMarket stockMarket, String symbol, int quantity, double wantedPrice, int taxID, int nrSuccessfulTransactions) {
        this.stockMarket = stockMarket;
        this.symbol = symbol;
        this.quantity = quantity;
        this.wantedPrice = wantedPrice;
        this.taxID = taxID;
        this.nrSuccessfulTransactions = nrSuccessfulTransactions;
    }

    // for now, the buyer is attempting to buy 1 time
    @Override
    public void run() {
        stockMarket.buyStock(symbol, quantity, wantedPrice);
    }
}
