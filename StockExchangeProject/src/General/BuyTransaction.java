package General;


public class BuyTransaction extends Transaction {

    public BuyTransaction() {
    }

    public BuyTransaction(double quantity, double price, String symbol, String timestamp) {
        super(quantity, price, symbol, "buy", timestamp);
    }

    @Override
    public String toString() {
        return "BuyTransaction{} " + super.toString();
    }
}
