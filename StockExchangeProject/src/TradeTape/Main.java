package TradeTape;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.time.Duration;
import java.util.Arrays;

public class Main {
    //Receiver / Trade Tape

    public static void main(String[] args) {
        Consumer<String, String> consumer = KafkaConsumerConfig.createConsumer("consumer");
        receiveMessages(consumer, "buychannel", "saleschannel");
    }

    public static void receiveMessages(Consumer<String, String> consumer, String topic1, String topic2){
        // Same consumer subscribes to the 2 specified topics
        consumer.subscribe(Arrays.asList(topic1, topic2));;
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
            });
        }
        consumer.close();
    }
}