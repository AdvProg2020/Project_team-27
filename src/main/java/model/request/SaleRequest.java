package model.request;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import server.menus.SellerMenu;
import model.accounts.Account;
import model.accounts.Seller;
import model.off.Sale;
import model.off.SaleStatus;
import model.productRelated.Product;
import client.view.FileHandling;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;

public class SaleRequest extends Request {
    private String offId = null;
    private LocalDate startOfSalePeriod = null;
    private LocalDate endOfSalePeriod = null;
    private int saleAmount = 0;
    //private String product = null;
    private ArrayList<Product> allSaleProducts = new ArrayList<>();
    private Sale sale;
    private static ArrayList<SaleRequest> allSaleRequests = new ArrayList<>();
    public static Type saleRequestType = new TypeToken<ArrayList<SaleRequest>>() {
    }.getType();


    public SaleRequest(String requestID) throws IOException {
        super(requestID);
        allSaleRequests.add(this);
        if (Account.getAccountWithUsername(this.getSeller()) instanceof Seller) {
            Seller seller = (Seller) Account.getAccountWithUsername(this.getSeller());
            seller.addSaleRequest(this);
        }
        writeInJ();
    }

    @Override
    public void declineRequest() throws IOException {
        if(!SellerMenu.doEdit) {
            getAllRequests().remove(this);
            allSaleRequests.remove(this);
            //  getAllRequests().remove(this);
            if (Account.getAccountWithUsername(this.getSeller()) instanceof Seller) {
                Seller seller = (Seller) Account.getAccountWithUsername(this.getSeller());
                // seller.getAllProductRequests().remove(this);
                seller.removeSaleRequest(this);
                for (Product allSaleProduct : allSaleProducts) {
                    allSaleProduct.getSaleP(null);
                    //  allSaleProduct.setInSale(true);
                }
                seller.getAllSales().remove(Sale.getSaleWithId(offId));
            }
        }
        writeInJ();
    }

    @Override
    public void acceptRequest() throws IOException {
        sale = Sale.getSaleWithId(offId);
        sale.setSaleDetails(SaleStatus.CONFIRMED, startOfSalePeriod, endOfSalePeriod, saleAmount,this.getSeller());
     //   if(sale.getAllSaleProducts().size() == 0){
            sale.getAllSaleProducts().addAll(allSaleProducts);
      //  }
//        else {
//            for (Product allSaleProduct : sale.getAllSaleProducts()) {
//                for (Product saleProduct : allSaleProducts) {
//                    if (!(allSaleProduct.getId() == saleProduct.getId())) {
//                        sale.getAllSaleProducts().add(saleProduct);
//                    }
//                }
//            }
//        }

 //       Sale.allProInSale.addAll(sale.getAllSaleProducts());
        sale.setSaleStatus(SaleStatus.CONFIRMED);
        ArrayList<String> arrayList = new ArrayList<>();
        for (Product allSaleProduct : allSaleProducts) {
            allSaleProduct.setSale(sale.getOffId());
          //  allSaleProduct.setInSale(true);
        }
        getAllRequests().remove(this);
        allSaleRequests.remove(this);
        if (Account.getAccountWithUsername(this.getSeller()) instanceof Seller) {
            Seller seller = (Seller) Account.getAccountWithUsername(this.getSeller());
            seller.removeSaleRequest(this);
          //  seller.addSale(sale);
        }
        writeInJ();
    }

    public boolean isThereProduct(Product p){
        for (Product allSaleProduct : allSaleProducts) {
            allSaleProduct.equals(p);
            return true;
        }
        return false;
    }


//    public void addProduct(Product product) throws IOException {
//        allSaleProducts.add(product);
//        writeInJ();
//    }

    public void removeProduct(Product product) throws IOException {
        allSaleProducts.remove(product);
        writeInJ();
    }


    public void addProductToSale(Product product) throws IOException {
        allSaleProducts.add(product);
        writeInJ();

    }

    public void setOffId(String offId) throws IOException {
        this.offId = offId;
        writeInJ();

    }

    public void setStartOfSalePeriod(LocalDate startOfSalePeriod) throws IOException {
        this.startOfSalePeriod = startOfSalePeriod;
        writeInJ();

    }

    public void setEndOfSalePeriod(LocalDate endOfSalePeriod) throws IOException {
        this.endOfSalePeriod = endOfSalePeriod;
        writeInJ();

    }

    public void setSaleAmount(int saleAmount) throws IOException {
        this.saleAmount = saleAmount;
        writeInJ();

    }



    public static void setAllSaleRequests(ArrayList<SaleRequest> allSaleRequests) {
        SaleRequest.allSaleRequests = allSaleRequests;
    }

    public static ArrayList<SaleRequest> getAllSaleRequests() {
        return allSaleRequests;
    }

    public static void writeInJ() throws IOException {
        FileHandling.setGson(new Gson());
        String json = FileHandling.getGson().toJson(SaleRequest.allSaleRequests, saleRequestType);
        FileHandling.writeInFile(json, "saleRequest.json");
    }

    public String getOffId() {
        return offId;
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

    public Sale getSale() {
        return sale;
    }
}