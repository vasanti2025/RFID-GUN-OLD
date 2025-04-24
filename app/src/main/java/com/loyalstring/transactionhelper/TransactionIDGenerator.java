package com.loyalstring.transactionhelper;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

public class TransactionIDGenerator {

    private static int counter = 1;

    public static String generateTransactionNumber(String type) {
        // Get the current timestamp
        LocalDateTime now = LocalDateTime.now();
        // Format the timestamp as a string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyyHHmmssSSS");
        String timestamp = now.format(formatter);
        // Generate a unique transaction number by combining the prefix and timestamp
        String transactionNumber = "TX" + type + timestamp;// + counter;
        // Increment the counter to ensure uniqueness for subsequent calls
        counter++;

        return transactionNumber;
    }

    public static String generateBillRepairTransactionID(String s) {
        String timestamp = new SimpleDateFormat("yyMMddHH").format(new Date());
        String uniqueID = UUID.randomUUID().toString().substring(0, 4); // Use only the first 4 characters of the UUID
        return s + "B" + timestamp + uniqueID;
    }

    public static String generateOrderTransactionID() {
        String timestamp = new SimpleDateFormat("yyMMddHH").format(new Date());
        String uniqueID = UUID.randomUUID().toString().substring(0, 4); // Use only the first 4 characters of the UUID
        return "O" + timestamp + uniqueID;
    }

    public static String generateRepairTransactionID() {
        String timestamp = new SimpleDateFormat("yyMMddHH").format(new Date());
        String uniqueID = UUID.randomUUID().toString().substring(0, 4); // Use only the first 4 characters of the UUID
        return "R" + timestamp + uniqueID;
    }


}
