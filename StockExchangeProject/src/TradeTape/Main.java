package TradeTape;

import General.Transaction;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import sun.misc.Signal;
import sun.misc.SignalHandler;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    //Receiver / Trade Tape

    public static List<Transaction> listOfTransactions = new ArrayList<>();
    public static void main(String[] args) {

        // handler in case the program is Exited
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Program interrupted, printing all stored data");
            for (var transaction : listOfTransactions) {
                System.out.println(transaction.toString());
            }
        }));

        Consumer<String, String> consumer = KafkaConsumerConfig.createConsumer("consumer");
        receiveMessages(consumer, "buychannel", "saleschannel");
    }

    // function that implements message receiving, on 2 different topics
    public static List<Transaction> receiveMessages(Consumer<String, String> consumer, String topic1, String topic2){
        // Same consumer subscribes to the 2 specified topics
        consumer.subscribe(Arrays.asList(topic1, topic2));
        List<Transaction> transactionList = new ArrayList<>();
        // run indedinitely, until force stopped
        while(true){
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(10));
            records.forEach(record -> {
                System.out.println("Received Transaction: " + record.value() + " on topic " + record.topic());
                try {
                    // deserialize
                    Transaction extractedTransaction = Transaction.fromJson(record.value());
                    transactionList.add(extractedTransaction);
                    listOfTransactions.add(extractedTransaction);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }
}