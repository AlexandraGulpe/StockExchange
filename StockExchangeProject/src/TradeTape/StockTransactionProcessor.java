package TradeTape;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StockTransactionProcessor {

    public static List<Map<String, Object>> processTransactions(List<String> transactionStrings) {
        List<Map<String, Object>> allTransactionDetails = new ArrayList<>();

        for (String transactionString : transactionStrings) {
            Map<String, Object> transactionDetails = processTransaction(transactionString);
            allTransactionDetails.add(transactionDetails);
        }

        return allTransactionDetails;
    }

    public static Map<String, Object> processTransaction(String transactionString) {
        Map<String, Object> transactionDetails = new HashMap<>();

        // Split the input string into individual components
        String[] transactionComponents = transactionString.split(",");

        // Validate if the input has correct number of components
        if (transactionComponents.length == 3) {
            String symbol = transactionComponents[0].trim();
            double price = Double.parseDouble(transactionComponents[1].trim());
            int quantity = Integer.parseInt(transactionComponents[2].trim());


            // Add components to the result map
            transactionDetails.put("Symbol", symbol);
            transactionDetails.put("Price", price);
            transactionDetails.put("Quantity", quantity);
        } else {
            // Handle invalid input
            transactionDetails.put("Error", "Invalid transaction format");
        }

        return transactionDetails;
    }
}