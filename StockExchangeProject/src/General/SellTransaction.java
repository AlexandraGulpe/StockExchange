package General;

public class SellTransaction extends Transaction{
    public SellTransaction() {
    }

    public SellTransaction(double quantity, double price, String symbol, String timestamp) {
        super(quantity, price, symbol, "sell", timestamp);
    }

    @Override
    public String toString() {
        return "SellTransaction{} " + super.toString();
    }
}
