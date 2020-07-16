package model.accounts;

import com.google.gson.reflect.TypeToken;
import model.log.BuyLog;
import model.request.CommentRequest;
import client.view.FileHandling;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Customer extends Account {
//    ArrayList<DiscountCode> allDiscountCodes = new ArrayList<>();
    private static ArrayList<Customer> allCustomers = new ArrayList<>();
    private ArrayList<BuyLog> BuyLogsHistory = new ArrayList<>();
    private ArrayList<CommentRequest> allCommentRequests = new ArrayList<>();
    public static Type CustomerType = new TypeToken<ArrayList<Customer>>() {
    }.getType();
    private BuyLog customerCart;

    public ArrayList<CommentRequest> getAllCommentRequests() {
        return allCommentRequests;
    }

    public void setAllCommentRequests(ArrayList<CommentRequest> allCommentRequests) {
        this.allCommentRequests = allCommentRequests;
    }

    public void addCommentRequest(CommentRequest commentRequest) throws IOException {
        allCommentRequests.add(commentRequest);
        writeInJ();
    }

    public void removeCommentRequest(CommentRequest commentRequest) throws IOException {
        allCommentRequests.remove(commentRequest);
        writeInJ();
    }

    public Customer(String username) throws IOException {
        super(username);
        role = "customer";
        allCustomers.add(this);
        writeInJ();
    }


    public static Customer getCustomerWithUsername(String username) {

        for (Customer customer: allCustomers) {
            if (customer.username.equalsIgnoreCase(username)) return customer;
        }
        return null;
    }
//    public ArrayList<DiscountCode> getAllDiscountCodes() {
//        return allDiscountCodes;
//    }
//
//    public void addDiscountCode(DiscountCode discountCode) throws IOException {
//        allDiscountCodes.add(discountCode);
//        writeInJ();
//
//    }
//
//    public void removeDiscountCode(DiscountCode discountCode) throws IOException {
//        allDiscountCodes.remove(discountCode);
//        writeInJ();
//    }

    public static void setAllCustomers(ArrayList<Customer> allCustomers) {
        Customer.allCustomers = allCustomers;
    }

    public static ArrayList<Customer> getAllCustomers() {
        return allCustomers;
    }

    public ArrayList<BuyLog> getBuyLogsHistory() {
        return BuyLogsHistory;
    }


    public void addLog(BuyLog buyLog) throws IOException {
        BuyLogsHistory.add(buyLog);
        writeInJ();

    }

    public static void writeInJ() throws IOException {

        String json = FileHandling.getGson().toJson(Customer.allCustomers, CustomerType);
        FileHandling.writeInFile(json, "customer.json");

    }

    @Override
    public String toString() {
        return "Customer{" +
                "BuyLogsHistory=" + BuyLogsHistory +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phoneNo=" + phoneNo +
                ", credit=" + credit +
                ", role='" + role + '\'' +
                ", currentPhoneNo=" + currentPhoneNo +
                ", address='" + address + '\'' +
                ", birthdayDate=" + birthdayDate +
                '}';
    }
}