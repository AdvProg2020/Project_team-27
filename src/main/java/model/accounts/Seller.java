package model.accounts;

import com.google.gson.reflect.TypeToken;
import model.firms.Firm;
import model.log.SaleLog;
import view.FileHandling;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Seller extends Account {
    private Firm firm;
    private static ArrayList<SaleLog> saleLogsHistory = new ArrayList<>();
    private static ArrayList<Seller> allSellers = new ArrayList<>();

    public Seller(String username) throws IOException {
        super(username);
        role = "seller";
        allSellers.add(this);
        writeInJ();
    }

    public void addLog(SaleLog saleLog){
        saleLogsHistory.add(saleLog);
    }

    public static ArrayList<SaleLog> getSaleLogsHistory() {
        return saleLogsHistory;
    }

    public Firm getFirm() {
        return firm;
    }

    public static void writeInJ() throws IOException {
        Type collectionType = new TypeToken<ArrayList<Seller>>(){}.getType();
        String json= FileHandling.getGson().toJson(allSellers,collectionType);
        FileHandling.turnToArray(json+" "+"seller.json");
    }

    @Override
    public String toString() {
        return "Seller{" +
                "firm=" + firm +
                ", saleLogsHistory=" + saleLogsHistory +
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
                ", allDiscountCodes=" + allDiscountCodes +
                '}';
    }
}