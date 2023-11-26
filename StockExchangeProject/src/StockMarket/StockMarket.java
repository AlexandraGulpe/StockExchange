package StockMarket;

import General.Transaction;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class StockMarket {
    private ConcurrentHashMap<String, Stock> stockMarket = new ConcurrentHashMap<>();
//    KafkaProducer<String, String> buyProducer = KafkaProducerConfig.createProducer();
//    KafkaProducer<String, String> sellProducer = KafkaProducerConfig.createProducer();

    KafkaProducer<String, String> producer = KafkaProducerConfig.createProducer();
    // adding a Stock object in the list of StockMarket items
    public void addStock(Stock stock) {
        stockMarket.put(stock.getSymbol(), stock);
    }

    // getting a certain Stock by
    public Stock getStock(String symbol) {
        return stockMarket.get(symbol);
    }

    //getting all stocks
    public Collection<Stock> getAllStocks() {
        return stockMarket.values();
    }

    //function for buying a Stock for a certain quantity
    public void buyStock(String symbol, int quantity, double wantedPrice) throws Exception {
        Stock stock = stockMarket.get(symbol);
        if (stock != null) {
            double price = stock.getPrice();
            int availableQuantity = stock.getQuantity();
            if (availableQuantity >= quantity && wantedPrice == price) {
                stock.buy(quantity);
                System.out.println(Thread.currentThread().getName() + " bought " + quantity + " x " + symbol + " at $" + price + " remaning quantity " + stock.getQuantity());
                Transaction transaction = new Transaction(quantity, price, symbol, "buy", LocalDateTime.now().toString());
                String transactionString = transaction.toJson();
                KafkaProducerConfig.sendMessage(producer, "buychannel", transactionString);
            } else {
                System.out.println("Not enough quantity or price doesn't match");
            }
        }
    }

    // function for selling Stock
    public void sellStock(String symbol, int quantity, double wantedPrice) throws Exception {
        Stock stock = stockMarket.get(symbol);
        if (stock != null) {
            double price = stock.getPrice();
            if (price == wantedPrice){
                stock.sell(quantity);
                System.out.println(Thread.currentThread().getName() + " sold " + quantity + " x " + symbol + " at $" + price + " remaining " + stock.getQuantity());
                Transaction transaction = new Transaction(quantity, price, symbol, "sell", LocalDateTime.now().toString());
                String transactionString = transaction.toJson();
                KafkaProducerConfig.sendMessage(producer, "saleschannel", transactionString);

            } else {
                System.out.println("Price wanted does not correspond to the current stock price");
            }

        }
    }

    public void closeChannel(){
        producer.close();
    }
}

