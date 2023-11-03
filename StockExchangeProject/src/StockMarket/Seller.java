package StockMarket;
import java.util.concurrent.ThreadLocalRandom;

public class Seller implements Runnable {
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

    public Seller(StockMarket stockMarket, String symbol, int quantity, double wantedPrice, int taxID, int nrSuccessfulTransactions) {
        this.stockMarket = stockMarket;
        this.symbol = symbol;
        this.quantity = quantity;
        this.wantedPrice = wantedPrice;
        this.taxID = taxID;
        this.nrSuccessfulTransactions = nrSuccessfulTransactions;
    }

    @Override
    public void run() {
        stockMarket.sellStock(symbol, quantity, wantedPrice);
    }
}
