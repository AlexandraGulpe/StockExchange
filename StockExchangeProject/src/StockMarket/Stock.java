package StockMarket;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.AbstractOwnableSynchronizer;


public class Stock {
    private String symbol;
    private Double price;
    private AtomicInteger quantity;

    public Stock(String symbol, Double price, int quantity) {
        this.symbol = symbol;
        this.price = price;
        this.quantity = new AtomicInteger(quantity);
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double newPrice) {
        price = newPrice;
    }

    public int getQuantity() {
        return quantity.get();
    }

    public void buy(int amount) {
        quantity.addAndGet(-amount);
    }

    public void sell(int amount) {
        quantity.addAndGet(amount);
    }

//    public int getQuantity() {
//        return quantity;
//    }
//
//    public void buy(int amount) {
//        quantity=quantity-amount;
//    }
//
//    public void sell(int amount) {
//        quantity = quantity + amount;
//    }
}
