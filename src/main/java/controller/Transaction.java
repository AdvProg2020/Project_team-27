package controller;

public class Transaction {
    String id;
    int sourceAccountI;
    int destAccountID;
    double money;
    String description;
    String receiptTyp;
    int paid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
