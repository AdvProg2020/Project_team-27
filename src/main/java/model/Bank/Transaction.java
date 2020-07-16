package model.Bank;

import com.google.gson.reflect.TypeToken;
import view.FileHandling;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Random;

public class Transaction {
    String id;
    String sourceAccountI;
    String destAccountID;
    double money;
    String description;
    String receiptType;
    boolean paid = false;
    //int paid;
    static String toBeJson;
    public static ArrayList<Transaction> allTransactions = new ArrayList<>();

    public Transaction(String sourceAccountI, String destAccountID, double money, String description, String receiptTyp) {
        this.sourceAccountI = sourceAccountI;
        this.destAccountID = destAccountID;
        this.money = money;
        this.description = description;
        this.receiptType = receiptTyp;
        Random rand = new Random();
        id =  String.valueOf(rand.nextInt(1000));
        allTransactions.add(this);
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

    public String getSourceAccountI() {
        return sourceAccountI;
    }

    public void setSourceAccountI(String sourceAccountI) {
        this.sourceAccountI = sourceAccountI;
    }

    public String getDestAccountID() {
        return destAccountID;
    }

    public void setDestAccountID(String destAccountID) {
        this.destAccountID = destAccountID;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
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
}