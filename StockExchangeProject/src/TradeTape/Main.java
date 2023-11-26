package TradeTape;

import General.Transaction;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    //Receiver / Trade Tape

    public static void main(String[] args) {
        Consumer<String, String> consumer = KafkaConsumerConfig.createConsumer("consumer");
        List<Transaction> obtainedList =  receiveMessages(consumer, "buychannel", "saleschannel");

        // print out obtained records
        for( var record : obtainedList){
            System.out.println(record.toString());
        }
    }

    public static List<Transaction> receiveMessages(Consumer<String, String> consumer, String topic1, String topic2){
        // Same consumer subscribes to the 2 specified topics
        consumer.subscribe(Arrays.asList(topic1, topic2));
        List<Transaction> transactionList = new ArrayList<>();
        // Wait for new transactions
        while (true) {
            // If there are 0 messages in the last 7 seconds, we stop the program
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(7));
            if (records.count() == 0){
                break;
            }
            // We log the transaction that happened
            records.forEach(record -> {
                System.out.println("Received Transaction: " + record.value() + " on topic " + record.topic());
                try {
                    // deserialize
                    Transaction extractedTransaction = Transaction.fromJson(record.value());
                    transactionList.add(extractedTransaction);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });
        }
        consumer.close();
        return transactionList;
    }
}