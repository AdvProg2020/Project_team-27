package model.log;


import com.google.gson.reflect.TypeToken;
import model.productRelated.Product;
import view.FileHandling;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class BuyLog extends Log {



    public BuyLog(String logId) throws IOException {
        super(logId);
        id=logId;
        localDateTimeForLog=LocalDateTime.now();
        allCustomersLog.add(this);
    }




    //detail
    private String id;
    public static LocalDateTime localDateTimeForLog;
    public double paidAmount;
    private double discountOnPrice;
    String buyerName;

    int numberOfChosenPro;
    private static boolean ifItsFinal;
    boolean isBought;
    private static boolean firstProduct=true;
    double amountAfterDis;

    //list
    private static ArrayList<ArrayList<Product>> allBoughtProduct = new ArrayList<>();
    private static ArrayList<Product> listOfOneProduct = new ArrayList<Product>();
    public static ArrayList<BuyLog> allCustomersLog = new ArrayList<BuyLog>();
    public static HashMap<ArrayList<Product>, Double> productWithPriceWithSale=new HashMap<>();
    public static HashMap<ArrayList<Product>, Double> productWithPriceWithoutSale=new HashMap<>();

    //setterAndGetter----------------------------------------------------

    public void setBuyLogDetail(String buyerName) throws IOException {
        this.buyerName = buyerName;
        allCustomersLog.add(this);
        writeInJ();
    }

    public String getId() {
        return id;
    }

    public void setIsBought(boolean bought) {
        isBought = bought;
    }

    public boolean getIsBought() {
        return isBought;
    }

    public static LocalDateTime getLocalDateTimeForLog() {
        return localDateTimeForLog;
    }

    public static ArrayList<BuyLog> getAllCustomersLog() {
        return allCustomersLog;
    }

    public static boolean getFirstProduct(){
        return firstProduct;
    }
    //other--------------------------------------------------------------


    //finish
    public static boolean isBought(String productId) {
        return ifItsFinal;
    }


    public void addProductToBuyLog(String productId, int amount) {
        Product product = Product.getProductById(productId);
        for (int i = 0; i < amount; i++) {
            assert product != null;
            if (product.getNumberOfProducts() != 0) {
                listOfOneProduct.add(product);
                numberOfChosenPro++;
            }
        }
        allBoughtProduct.add(listOfOneProduct);
        product.setTotalNumberOfBuyers(product.getTotalNumberOfBuyers() + 1);
        firstProduct=false;
        ifItsFinal = true;
    }

    public double holePriceWithOutDiscount() {
        double price = 0;
        for (ArrayList<Product> productArrayList : allBoughtProduct) {
            for (Product product1 : productArrayList) {
                price = +product1.getPrice();
            }
        }
        return price;
    }

    public double holePriceWithDiscount() {
        double price = 0;
        for (ArrayList<Product> productArrayList : allBoughtProduct) {
            for (Product product1 : productArrayList) {
//                if () {
//
//                }
            }
        }
        return price;
    }


    public void reduceNumberOfProduct() {
        for (ArrayList<Product> productArrayList : allBoughtProduct) {
            for (Product product1 : listOfOneProduct) {
                product1.setNumberOfProducts(product1.getNumberOfProducts() - 1);
            }
        }
    }

    public  void deleteProductFromBuyLog(String productId , int amount) {
        Product product = Product.getProductById(productId);
        ArrayList<Product> help = null;
        Iterator iterator = listOfOneProduct.iterator();
        while (iterator.hasNext()) {
            Product product1 = (Product) iterator.next();
            if (product1.equals(product)) {
                for (int i=0;i<amount;i++){
                    help.add(product1);
                    iterator.remove();
                }
            }
        }
        allBoughtProduct.remove(help);
    }


    public  int compareTo(BuyLog buyLog) {
        return getLocalDateTimeForLog().compareTo(getLocalDateTimeForLog());
    }

    public static void writeInJ() throws IOException {
        Type collectionType = new TypeToken<ArrayList<BuyLog>>(){}.getType();
        String json= FileHandling.getGson().toJson(BuyLog.getAllCustomersLog(),collectionType);
        FileHandling.turnToArray(json+" "+"buyLog.json");
    }

    @Override
    public String toString() {
        return "BuyLog{" +
                "id='" + id + '\'' +
                ", amountAfterDis=" + amountAfterDis +
                ", buyerName='" + buyerName + '\'' +
                ", numberOfChosenPro=" + numberOfChosenPro +
                ", isBought=" + isBought +
                '}';
    }
}