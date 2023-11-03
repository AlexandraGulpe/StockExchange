package StockMarket;

import java.util.concurrent.ConcurrentHashMap;

public class StockMarket {
    private ConcurrentHashMap<String, Stock> stockMarket = new ConcurrentHashMap<>();

    // adding a Stock object in the list of StockMarket items
    public void addStock(Stock stock) {
        stockMarket.put(stock.getSymbol(), stock);
    }

    // getting a certain Stock by
    public Stock getStock(String symbol) {
        return stockMarket.get(symbol);
    }

    //function for buying a Stock for a certain quantity
    public void buyStock(String symbol, int quantity, double wantedPrice) {
        Stock stock = stockMarket.get(symbol);
        if (stock != null) {
            double price = stock.getPrice();
            int availableQuantity = stock.getQuantity();
            if (availableQuantity >= quantity && wantedPrice == price) {
                stock.buy(quantity);
                System.out.println("Bought " + quantity + " shares of " + symbol + " at $" + price);
            } else {
                System.out.println("Not enough quantity or price doesn't match");
            }
        }
    }

    // function for selling Stock
    public void sellStock(String symbol, int quantity, double wantedPrice) {
        Stock stock = stockMarket.get(symbol);
        if (stock != null) {
            double price = stock.getPrice();
            if (price == wantedPrice){
                stock.sell(quantity);
                System.out.println("Sold " + quantity + " shares of " + symbol + " at $" + price);
            } else {
                System.out.println("Price wanted does not correspond to the current stock price");
            }

        }
    }
}

