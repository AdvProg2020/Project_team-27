package model.off;

import server.menus.LoginMenu;
import model.accounts.Seller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Auction {
    private String id;
    private LocalDate startOfPeriod;
    private LocalDate endOfPeriod;
    private String Product;
    private String customer;
    private double money;


   // public ArrayList<Product> allProducts = new ArrayList<>();
    private static ArrayList<Auction> allAuctions = new ArrayList<>();

    public Auction(String id) throws IOException {
        this.id = id;
        allAuctions.add(this);
        if(LoginMenu.getLoginAccount() instanceof Seller){
            Seller seller = (Seller) LoginMenu.getLoginAccount();
            seller.getAllAuctions().add(this);
            Seller.writeInJ();
        }
    }

    public static ArrayList<Auction> getAllAuctions() {
        return allAuctions;
    }

    public static void setAllAuctions(ArrayList<Auction> allAuctions) {
        Auction.allAuctions = allAuctions;
    }


    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getProduct() {
        return Product;
    }

    public void setProduct(String product) {
        Product = product;
    }

    public LocalDate getStartOfPeriod() {
        return startOfPeriod;
    }

    public void setStartOfPeriod(LocalDate startOfPeriod) {
        this.startOfPeriod = startOfPeriod;
    }

    public LocalDate getEndOfPeriod() {
        return endOfPeriod;
    }

    public void setEndOfPeriod(LocalDate endOfPeriod) {
        this.endOfPeriod = endOfPeriod;
    }

    public static Auction getWithId(String id) {
        for (Auction allAuction : allAuctions) {
            if (allAuction.id.equals(id)) {
                return allAuction;
            }
        }
        return null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static boolean isThereWithId(String id) {
        for (Auction allAuction : allAuctions) {
            if (allAuction.id.equals(id)) {
                return true;
            }
        }
        return false;
    }
}
