package StockMarket;

import java.util.concurrent.ThreadLocalRandom;

public class Buyer implements Runnable {
    private StockMarket stockMarket;
    private String symbol;
    private int quantity;
    private double wantedPrice;
    private int taxID;
    private int noSuccessfulTransactions;

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

    public Buyer(StockMarket stockMarket, String symbol, int quantity, double wantedPrice, int taxID, int noSuccessfulTransactions) {
        this.stockMarket = stockMarket;
        this.symbol = symbol;
        this.quantity = quantity;
        this.wantedPrice = wantedPrice;
        this.taxID = taxID;
        this.noSuccessfulTransactions = noSuccessfulTransactions;
    }

    @Override
    public void run() {
        stockMarket.buyStock(symbol, quantity, wantedPrice);
    }
}
