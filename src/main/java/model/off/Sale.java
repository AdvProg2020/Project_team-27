package model.off;

import com.google.gson.reflect.TypeToken;
import model.accounts.Account;
import model.productRelated.Product;
import model.accounts.Seller;
import client.view.FileHandling;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;

public class Sale {
    private String offId;
    private SaleStatus saleStatus;
    private LocalDate startOfSalePeriod;
    private LocalDate endOfSalePeriod;
    private int saleAmount;
    private static String seller;
    public ArrayList<Product> allSaleProducts = new ArrayList<>();
   public static ArrayList<Product> allProSale = new ArrayList<>();
    private static ArrayList<Sale> allSales = new ArrayList<>();
    public static Type SaleType = new TypeToken<ArrayList<Sale>>() {
    }.getType();

    public Sale(String offId) throws IOException {
        this.offId = offId;
        allSales.add(this);
        getAllSales().add(this);
        writeInJ();
    }

    public void setSaleDetails(SaleStatus saleStatus, LocalDate startOfSalePeriod, LocalDate endOfSalePeriod, int saleAmount, String selle) throws IOException {
        this.saleStatus = saleStatus;
        if (startOfSalePeriod != null) {
            this.startOfSalePeriod = startOfSalePeriod;
        }
        if (endOfSalePeriod != null) {
            this.endOfSalePeriod = endOfSalePeriod;
        }
        if (saleAmount != 0) {
            this.saleAmount = saleAmount;
        }
        if (selle != null) {
            this.seller = selle;
        }
        writeInJ();
    }

    public void setOffId(String offId) {
        this.offId = offId;
    }

    public static void setAllSales(ArrayList<Sale> allSales) {
        Sale.allSales = allSales;
    }

    public static ArrayList<Sale> getAllSales() {
        return allSales;
    }

    public String getOffId() {
        return offId;
    }

    public SaleStatus getSaleStatus() {
        return saleStatus;
    }

    public LocalDate getStartOfSalePeriod() {
        return startOfSalePeriod;
    }

    public LocalDate getEndOfSalePeriod() {
        return endOfSalePeriod;
    }

    public int getSaleAmount() {
        return saleAmount;
    }

    public ArrayList<Product> getAllSaleProducts() {
        return allSaleProducts;
    }

    public ArrayList<Sale> listSales() {
        return allSales;
    }


    public static Sale getSaleWithId(String id) {
        for (Sale sale : allSales) {
            if (sale.getOffId().equals(id)) {
                return sale;
            }
        }
        return null;
    }

    public static boolean isThereSaleWithId(String id) {
        for (Sale sale : allSales) {
            if (sale.getOffId().equals(id)) {
                return true;
            }
        }
        return false;
    }


    public Sale getSaleWithSeller(Seller seller) {
        for (Sale sale : allSales) {
            if ((Account.getAccountWithUsername(sale.getSeller())) == (seller)) {
                return sale;
            }

        }
        return null;
    }

    public static String getSeller() {
        return seller;
    }

    public static void setSeller(String seller) {
        Sale.seller = seller;
    }

    public Sale getSaleWithProduct(Product product) {
        for (Sale sale : allSales) {
            for (Product products : sale.getAllSaleProducts()) {
                if (product.equals(products)) {
                    return sale;
                }
            }
        }
        return null;
    }

    public void setAllSaleProducts(ArrayList<Product> allSaleProducts) throws IOException {
        this.allSaleProducts = allSaleProducts;
        Seller.writeInJ();
    }

    public void setSaleStatus(SaleStatus saleStatus) {
        this.saleStatus = saleStatus;
    }

    public static void deleteSale(String id) {
        allSales.remove(id);
    }

    public boolean isTheProductInAnotherSale(Product product) {
        for (Sale sale : allSales) {
            if (sale.getAllSaleProducts().contains(product)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkSale() {
        if(startOfSalePeriod != null && endOfSalePeriod != null) {
            LocalDate localDate = LocalDate.now();
            if (startOfSalePeriod.isAfter(localDate) || startOfSalePeriod.isEqual(localDate)) {
                if (endOfSalePeriod.isBefore(localDate) || startOfSalePeriod.isEqual(localDate)) {
                    return true;
                }
            }
        }
        return false;
    }


 /*   public static double withSale(Product product){
        Sale s = null;
        double price = product.getPrice();
        for (Sale allSale : allSales) {
            for (Product allSaleProduct : allSale.allSaleProducts) {
                if (allSaleProduct.getId().equals(product.getId())) {
                    s = allSale;
                }
            }
        }if (s.checkSale()) {
            price = price - ((price * s.saleAmount) / 100);
        }
        return price;
    }

  */

    public double withSale(Product product) {
        double price = product.getPrice();
            price =(price * this.saleAmount) / 100;

        return price;
    }

    public static void writeInJ() throws IOException {

        String json = FileHandling.getGson().toJson(Sale.allSales, SaleType);
        FileHandling.writeInFile(json, "sale.json");

    }

//    public static void writeInJ() throws IOException {
//        Type collectionType = new TypeToken<ArrayList<Sale>>(){}.getType();
//        String json= FileHandling.getGson().toJson(Sale.allSales,collectionType);
//        FileHandling.turnToArray(json+" "+"sale.json");
//    }

    @Override
    public String toString() {
        return "Sale{" +
                "offId='" + offId + '\'' +
                ", saleStatus=" + saleStatus +
                ", startOfSalePeriod=" + startOfSalePeriod +
                ", endOfSalePeriod=" + endOfSalePeriod +
                ", saleAmount=" + saleAmount +
                ", seller=" + seller +
                ", allProducts=" + allSaleProducts +
                '}';
    }

}