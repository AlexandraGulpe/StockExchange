package StockMarket;

import General.BuyTransaction;
import General.SellTransaction;
import General.Transaction;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class StockMarket {
    // Concurrent hashmap in order to deal with race conditions
    private ConcurrentHashMap<String, List<Stock>> stockMarket = new ConcurrentHashMap<>();
    KafkaProducer<String, String> producer = KafkaProducerConfig.createProducer();

    // adding a List of Stock object in the list of StockMarket items
    // Setting the symbol as the first element's symbol, for simplicity
    public void addStock(List<Stock> stockList) {
        stockMarket.put(stockList.get(0).getSymbol(), stockList);
    }

    //getting all stocks
    public Collection<List<Stock>> getAllStocks() {
        return stockMarket.values();
    }

    //function for buying a certain quantity of a Stock
    public void buyStock(String symbol, int wantedQuantity, double wantedPrice) throws Exception {
        // finding the closest Stock -> smallest price possible with available quantity
        Stock closestStock = findClosestStockBuy(stockMarket.get(symbol), wantedPrice, wantedQuantity);
        if (closestStock != null) {
            double stockPrice = closestStock.getPrice();
            closestStock.buy(wantedQuantity);
            System.out.println(Thread.currentThread().getName() + " bought " + wantedQuantity + " x " + symbol + " at $" + stockPrice + " remaning quantity " + closestStock.getQuantity());
            BuyTransaction transaction = new BuyTransaction(wantedQuantity, stockPrice, symbol, LocalDateTime.now().toString());
            String transactionString = transaction.toJson();
            KafkaProducerConfig.sendMessage(producer, "buychannel", transactionString);
        }
        else
        {
            System.out.println("BuyTransaction in Thread " +Thread.currentThread().getName() + " could not take place due to unmet conidtions");
        }

    }

    // function for selling a certain amount of a Stock
    public void sellStock(String symbol, int quantity, double wantedPrice) throws Exception {
        Stock closestStock = findClosestStockSell(stockMarket.get(symbol), wantedPrice);
        if (closestStock != null){
            double stockPrice = closestStock.getPrice();
            closestStock.sell(quantity);
            System.out.println(Thread.currentThread().getName() + " sold " + quantity + " x " + symbol + " at $" + stockPrice + " remaining " + closestStock.getQuantity());
            SellTransaction transaction = new SellTransaction(quantity, stockPrice, symbol, LocalDateTime.now().toString());
            String transactionString = transaction.toJson();
            KafkaProducerConfig.sendMessage(producer, "saleschannel", transactionString);
        }
        else {
            System.out.println("SellTransaction in Thread " +Thread.currentThread().getName() + " could not take place due to unmet conidtions");
        }
    }

    // out of the List<Stock>, find the Stock with the smallest price and at least requiredQuantity
    public static Stock findClosestStockBuy(List<Stock> stocksList, double targetPrice, int requiredQuantity) {
        double minPrice = Double.MAX_VALUE;
        Stock closestStock = null;

        for (Stock stock : stocksList) {
            double currentPrice = stock.getPrice();
            int currentQuantity = stock.getQuantity();

            if (currentQuantity >= requiredQuantity) {
                double priceDifference = Math.abs(currentPrice - targetPrice);

                // Update the closest price if the current stock is closer
                if (currentPrice < minPrice) {
                    minPrice = currentPrice;
                    closestStock = stock;

                }
            }
        }
        return closestStock;
    }


    // sell HIGH - find the Stock with the highest price
    public static Stock findClosestStockSell(List<Stock> stocksList, double targetPrice) {
        double maxPrice = Double.MIN_VALUE;
        Stock closestStock = null;

        for (Stock stock : stocksList) {
            double currentPrice = stock.getPrice();
            if (currentPrice > maxPrice){
                maxPrice = currentPrice;
                closestStock = stock;
            }
        }
        return closestStock;
    }


    public void closeChannel(){
        producer.close();
    }


}