# Stock Exchange
Team Members:
 - Alexandra Gulpe
 - Iulia Zbircea
 - Casian Fara
 - Razvan Brailoiu
   
## Description (5-6 paragraphs)


The Stock Exchange project’s scope is to create a simulated stock exchange system where shares from various companies are actively traded. In this virtual market, sellers offer a specific number of shares at specified prices, and buyers express their interest to purchase shares at particular price points. When an offer aligns with a corresponding request in terms of price and time, a transaction occurs, and a certain number of shares are traded between the parties. 

This project may have the following structure ( 3rd person view):

Sellers have the capability to list a specific number of shares for sale and indicate the price at which they are willing to sell these shares. They also have the flexibility to modify or update their offers at any time. This means that if a seller changes the details of an existing offer and a buyer had expressed their  interest in that offer, the transaction will be canceled / remade. 

Buyers express their intention to purchase a particular number of shares at specific prices. Similar to sellers, buyers can also adjust their purchase requests as market conditions change. 

In this simulated stock exchange, all participants, including sellers and buyers, have access to information related to current offers, requests, and the transaction history. 

One of the key challenges in building this stock exchange system is managing the concurrent operation of multiple threads representing buyers and sellers. Sellers and buyers need to be able to operate simultaneously, placing offers, expressing purchase intentions, and engaging in transactions. However, there might occur potential issues in terms of  concurrency.


## Concurrency issues:


We identified the following concurrency problems:
 1.  Two buyers / sellers attempting to buy at the same time -> race condition
      - This issue can be solved by using locks, so when two instances are attempting to modify / access the same value at the same time. But in real-life programming, there’s always at least one small difference in time. So, when using locks, the one that arrives there first, will lock the value until it finishes the modification process
      - However, the idea of locking certain values, can produce another issue -> deadlocks. Deadlocks occur when two or more threads are blocked indefinitely because they are each waiting for a resource held by the other. 
2. Overlapping Transactions - multiple transactions can occur concurrently that can lead to problems such as double-selling or double-buying shares. This may occur when multiple threads concurrently read and write to shared data without proper synchronisation.

## Architecture:

1. Stock Exchange Simulator Class (Main Class)  
 - This class serves as the entry point of the application.
 - Used to instantiate helper classes and start the whole process
 - Used to create different testing scenarios

2. Stock Market Class
 -  Represents the stock exchange itself.
 -  Contains a collection of stocks, their current prices, and trading history.
 -  Provides methods for querying stock data.
 -  Maybe a data structure (e.g., a list) to store details about past transactions, including date, price, buyer, seller, and stock

3. Stock Class
  - Represents a specific stock with attributes like symbol, price, and quantity. (e.g. AAPL, 50$, 0.66)
  - May include methods for updating the stock's price.

4. Trader Class / Interface (implements Runnable / Thread class)
  - Both Buyers and Sellers implement this interface / extend this class
  - Represents a trader in the stock exchange, either a buyer or a seller.
  - Each trader operates as a separate thread.
  - Contains attributes like the trader's name, account balance, and a list of owned stocks.
  - Implements methods to place buy/sell orders.

5. Transaction Manager Class
  - Manages the execution of stock transactions.
  - It operates as a separate thread 
  - Buyers and sellers communicate by placing offers and requests into shared data structures and periodically checking for matches.
When an offer matches a request in terms of price, a transaction occurs
  - Proper synchronization mechanisms (e.g., locks, condition variables) are used to prevent race conditions and deadlocks.
Error handling is implemented to handle scenarios such as insufficient shares, invalid offers/requests, and communication errors
  - Once a transaction is made / considered valid, it can be sent to the Stock Market class to be registered in the history

6. Thread Safety Mechanisms
 - To ensure thread safety, you can use synchronized methods or other concurrency mechanisms. For example, you might use synchronized blocks or data structures like ConcurrentHashMap to manage order books and prevent data corruption during concurrent operations.

7. Logging and Reporting
 - We can create a logger which logs every action on the market (intent to buy, intent to sell, successful transaction, failed transaction, and so on )

8. Error Handling
  - Implement error handling and exception handling to manage unexpected situations.

9. Testing
  - Develop unit tests to validate the functionality and correctness of the system.
  - Perform stress testing to ensure the system can handle a high volume of concurrent transactions.



