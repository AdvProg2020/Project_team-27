package model.log;

import model.accounts.Seller;
import model.productRelated.Product;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class SaleLog extends Log {
    private double receivedAmount = 0;
    private double price = 0;
    private double reducedAmount = 0;
    private String seller;

    public SaleLog(String logId) throws IOException {
        super(logId);
        allSellersLog.add(this);
    }

    private HashMap<Product, Integer> chosenProduct = new HashMap<>();
    //  private ArrayList<Product> allSoldProduct = new ArrayList<Product>();
    private static ArrayList<SaleLog> allSellersLog = new ArrayList<SaleLog>();


    //setterAndGetter--------------------------------------------

    public void addPrice(double p) {
        price += p;
    }

    public static void setAllSellersLog(ArrayList<SaleLog> allSellersLog) {
        SaleLog.allSellersLog = allSellersLog;
    }

    public void setReducedAmount(double reducedAmount) {
        this.reducedAmount = reducedAmount;
    }

    public void setReceivedAmount(double amount) throws IOException {
        receivedAmount = amount;
        Seller.writeInJ();



    }

    public double getReceivedAmount() {
        return receivedAmount =price - reducedAmount;
    }

    public HashMap<Product, Integer> getChosenProduct() {
        return chosenProduct;
    }

    public void setChosenProduct(HashMap<Product, Integer> chosenProduct) {
        this.chosenProduct = chosenProduct;
    }

    public static ArrayList<SaleLog> getAllSellersLog() {
        return allSellersLog;
    }



    public double getReducedAmount() {
        return reducedAmount;
    }


    public void addProductToSaleLog(String productId, int number) {
        Product product = Product.getProductById(productId);
        chosenProduct.put(product, number);
    }

    public static boolean idThereSeller(String seller) {
        for (SaleLog saleLog : allSellersLog) {
            saleLog.seller.equals(seller);
            return true;
        }
        return false;
    }

    public static SaleLog getLogWithSeller(String seller) {
        for (SaleLog allLog : allSellersLog) {
            if (allLog.seller.equals(seller)) {
                return allLog;
            }
        }
        return null;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    @Override
    public String toString() {
        return "SaleLog{" +
                "receivedAmount=" + receivedAmount +
                ", price=" + price +
                ", reducedAmount=" + reducedAmount +
                ", seller='" + seller + '\'' +
                ", chosenProduct=" + chosenProduct +
                ", logId='" + logId + '\'' +
                ", localDateTimeForLog=" + localDateTimeForLog +
                ", deliveryStatus=" + deliveryStatus +
                '}';
    }
}