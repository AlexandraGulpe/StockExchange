package General;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

// parent transaction class
public class Transaction {
    private double quantity;
    private double price;
    private String symbol;
    private String messageType;
    private String timestamp;

    public Transaction(){

    }

    public Transaction(double quantity, double price, String symbol, String messageType, String timestamp) {
        this.quantity = quantity;
        this.price = price;
        this.symbol = symbol;
        this.messageType = messageType;
        this.timestamp = timestamp;
    }

    // method to transform Transaction Object to JSON string
    public String toJson() throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }

    // method to transform JSON string to Transaction Object
    public static Transaction fromJson(String json) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, Transaction.class);
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "{" +
                "quantity=" + quantity +
                ", price=" + price +
                ", symbol='" + symbol + '\'' +
                ", messageType='" + messageType + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
