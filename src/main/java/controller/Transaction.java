package controller;

import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import model.request.AccountRequest;
import view.FileHandling;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Transaction {
    String id;
    int sourceAccountI;
    int destAccountID;
    double money;
    String description;
    String receiptTyp;
    int paid;
    String toBeJson;
    public static ArrayList<Transaction> allTransactions = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
