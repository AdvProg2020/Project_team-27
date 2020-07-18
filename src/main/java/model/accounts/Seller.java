package model.accounts;

import com.google.gson.reflect.TypeToken;
import model.fileTransfer.SelectFileChooserExample;
import model.firms.Firm;
import model.log.SaleLog;
import model.off.Auction;
import model.off.Sale;
import model.productRelated.Product;
import model.request.AccountRequest;
import model.request.ProductRequest;
import model.request.SaleRequest;
import client.view.FileHandling;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Seller extends Account {

    private static ArrayList<AccountRequest> allAccountRequests = new ArrayList<>();
    private static ArrayList<Seller> allSellers = new ArrayList<>();
    private ArrayList<SaleLog> saleLogsHistory = new ArrayList<>();
    private ArrayList<Product> allProduct = new ArrayList<>();
    private ArrayList<Sale> allSales = new ArrayList<>();
    private ArrayList<ProductRequest> allProductRequests = new ArrayList<>();
    private ArrayList<SaleRequest> allSaleRequests = new ArrayList<>();
    private ArrayList<Auction> allAuctions = new ArrayList<>();
    public static String [] a ;
    public  ArrayList<File> files = new ArrayList<>();

    public static Type SellerType = new TypeToken<ArrayList<Seller>>() {
    }.getType();

    public Seller(String username) throws IOException {
        super(username);
        role = "seller";
        allSellers.add(this);
        writeInJ();
    }


    public void sellFile(){
        SelectFileChooserExample.setSeller(this);
        SelectFileChooserExample.main(a);

    }

    public  File getFileWithName(String name){
        for (File file : files) {
            if (file.getName().equals(name)){
                return file;
            }
        }
        return null;
    }

    public ArrayList<File> getFiles() {
        return files;
    }

    public void addSale(Sale sale) throws IOException {
        allSales.add(sale);
        writeInJ();
    }

    public ArrayList<Auction> getAllAuctions() {
        return allAuctions;
    }

    public static Seller getSellerWithUsername(String username) {
        for (Seller seller : allSellers) {
            if (seller.username.equalsIgnoreCase(username)) return seller;
        }
        return null;
    }

    public void setAllAuctions(ArrayList<Auction> allAuctions) {
        this.allAuctions = allAuctions;
    }

    public void removeProduct(Product product) throws IOException {
        allProduct.remove(product);
        writeInJ();
    }

    public void removeProductRequest(ProductRequest productRequest) throws IOException {
        allProductRequests.remove(productRequest);
        writeInJ();
    }

    public void removeAccountRequest(AccountRequest productRequest) throws IOException {
        allAccountRequests.remove(productRequest);
        writeInJ();
    }

    public void removeSaleRequest(SaleRequest productRequest) throws IOException {
        allSaleRequests.remove(productRequest);
        writeInJ();
    }

    public void setAllProduct(ArrayList<Product> allProduct) {
        this.allProduct = allProduct;
    }

    public void addSaleRequest(SaleRequest saleRequest) throws IOException {
        allSaleRequests.add(saleRequest);
        writeInJ();
    }

    public void addAccountRequest(AccountRequest accountRequest) throws IOException {
        allAccountRequests.add(accountRequest);
        writeInJ();
    }

    public void addProductRequest(ProductRequest productRequest) throws IOException {
        allProductRequests.add(productRequest);
        writeInJ();
    }

    public ArrayList<ProductRequest> getAllProductRequests() {
        return allProductRequests;
    }

    public void setAllProductRequests(ArrayList<ProductRequest> allProductRequests) {
        this.allProductRequests = allProductRequests;
    }

    public ArrayList<SaleRequest> getAllSaleRequests() {
        return allSaleRequests;
    }

    public void setAllSaleRequests(ArrayList<SaleRequest> allSaleRequests) {
        this.allSaleRequests = allSaleRequests;
    }

    public ArrayList<AccountRequest> getAllAccountRequests() {
        return allAccountRequests;
    }

    public void setAllAccountRequests(ArrayList<AccountRequest> allAccountRequests) {
        this.allAccountRequests = allAccountRequests;
    }

    public ArrayList<Sale> getAllSales() {
        return allSales;
    }

    public void setAllSales(ArrayList<Sale> allSales) {
        this.allSales = allSales;
    }

    public void setSaleLogsHistory(ArrayList<SaleLog> saleLogsHistory) {
        this.saleLogsHistory = saleLogsHistory;
    }

    public void addProduct(Product product) throws IOException {
        allProduct.add(product);
        writeInJ();
    }

    public ArrayList<Product> getAllProduct() {
        return allProduct;
    }

    public void addLog(SaleLog saleLog) throws IOException {
        saleLogsHistory.add(saleLog);
        writeInJ();
    }


    public static void setAllSellers(ArrayList<Seller> allSellers) {
        Seller.allSellers = allSellers;
    }

    public ArrayList<SaleLog> getSaleLogsHistory() {
        return saleLogsHistory;
    }

    public Firm getFirm() {
        return firm;
    }

    public static ArrayList<Seller> getAllSellers() {
        return allSellers;
    }

    public static void writeInJ() throws IOException {

        String json = FileHandling.getGson().toJson(Seller.allSellers, SellerType);
        FileHandling.writeInFile(json, "seller.json");

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
                //   ", allDiscountCodes=" + allDiscountCodes +
                '}';
    }
}