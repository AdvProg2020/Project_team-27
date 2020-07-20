package model.bank;

import com.google.gson.reflect.TypeToken;
import client.view.FileHandling;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.UUID;

public class Transaction {
    String id;
    String sourceAccountID;
    String destAccountID;
    int money;
    String description;
    String receiptType;
    boolean paid = false;
    //int paid;
    static String toBeJson;
    public static ArrayList<Transaction> allTransactions = new ArrayList<>();
    public static Type transactionType = new TypeToken<ArrayList<Transaction>>() {
    }.getType();

    public Transaction(String sourceAccountI, String destAccountID, int money, String receiptTyp) throws IOException {
        this.sourceAccountID = sourceAccountI;
        this.destAccountID = destAccountID;
        this.money = money;
        this.receiptType = receiptTyp;
        id= UUID.randomUUID().toString();
        //Random rand = new Random();
        // id =  String.valueOf(rand.nextInt(1000));
        allTransactions.add(this);
        writeInJ();
    }

    public static boolean isThereAccountWithId(String username) {
        for (Transaction account : allTransactions) {
            if (account.id.equalsIgnoreCase(username)) return true;
        }
        return false;
    }


    public static Transaction getAccountWithid(String username) {
        for (Transaction account : allTransactions) {
            if (account.id.equalsIgnoreCase(username)) return account;
        }
        return null;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static String getToBeJson() {
        return toBeJson;
    }

    public static void setToBeJson(String toBeJson) {
        Transaction.toBeJson = toBeJson;
    }

    public static ArrayList<Transaction> getAllTransactions() {
        return allTransactions;
    }

    public String getReceiptType() {
        return receiptType;
    }

    public void setReceiptType(String receiptType) {
        this.receiptType = receiptType;
    }

    public String getSourceAccountID() {
        return sourceAccountID;
    }

    public void setSourceAccountID(String sourceAccountID) {
        this.sourceAccountID = sourceAccountID;
    }

    public String getDestAccountID() {
        return destAccountID;
    }

    public void setDestAccountID(String destAccountID) {
        this.destAccountID = destAccountID;
    }

    public int getMoney() {
        return money;
    }

    public static Type getTransactionType() {
        return transactionType;
    }

    public static void setTransactionType(Type transactionType) {
        Transaction.transactionType = transactionType;
    }



    public void setMoney(int money) {
        this.money = money;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public static ArrayList<Transaction> getTransaction(String toBeJson){
        toBeJson = toBeJson.replace("*",",");
        Type transactionReType = new TypeToken<ArrayList<Transaction>>() {
        }.getType();
        ArrayList<Transaction> transactionArrayList = FileHandling.getGson().fromJson(toBeJson, transactionReType);
        if (null == transactionArrayList) {
            transactionArrayList = new ArrayList<>();
        }
        setAllTransactions(transactionArrayList);
        return transactionArrayList;
    }

    public static void setAllTransactions(ArrayList<Transaction> allTransactions) {
        Transaction.allTransactions = allTransactions;
    }

    public static void writeInJ() throws IOException {

        String json = FileHandling.getGson().toJson(Transaction.allTransactions, transactionType);
        FileHandling.writeInFile(json, "transaction.json");

    }}
