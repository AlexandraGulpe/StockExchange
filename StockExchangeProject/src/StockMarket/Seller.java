package StockMarket;
import java.util.concurrent.ThreadLocalRandom;

//the Seller thread
public class Seller implements Runnable {
    private StockMarket stockMarket;
    private String symbol;
    private int quantity;
    private double wantedPrice;

    public Seller(StockMarket stockMarket, String symbol, int quantity, double wantedPrice) {
        this.stockMarket = stockMarket;
        this.symbol = symbol;
        this.quantity = quantity;
        this.wantedPrice = wantedPrice;
    }

    @Override
    public void run() {
        try {
            stockMarket.sellStock(symbol, quantity, wantedPrice);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
