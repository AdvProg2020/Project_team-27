package model.request;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controller.menus.LoginMenu;
import model.accounts.Account;
import model.accounts.Seller;
import model.firms.Firm;
import model.productRelated.Category;
import model.productRelated.Product;
import model.productRelated.ProductStatus;
import view.FileHandling;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class ProductRequest extends Request {

    private String productId = null;
    private String productName = null;
    private double price = 0;
    private String companyName = null;
    private String categoryName = null;
    private String lastCategory = null;
    private String additionalDetail = null;
    private int numberOfProduct = 0;
    private static ArrayList<ProductRequest> allProductRequests = new ArrayList<>();
    // private  HashMap<String,String> specialValue = new HashMap<>();
    private ArrayList<String> specialValue = new ArrayList<>();
    public static Type productRequestType = new TypeToken<ArrayList<ProductRequest>>() {
    }.getType();


    public ArrayList<String> getSpecialValue() {
        return specialValue;
    }

    public void setSpecialValue(ArrayList<String> specialValue) throws IOException {
        this.specialValue = specialValue;
        writeInJ();
    }

    public ProductRequest(String requestID) throws IOException {
        super(requestID);
        allProductRequests.add(this);
        if (Account.getAccountWithUsername(this.getSeller()) instanceof Seller) {
            Seller seller = (Seller) Account.getAccountWithUsername(this.getSeller());
            seller.addProductRequest(this);
        }
        writeInJ();
    }


    @Override
    public void declineRequest() throws IOException {
        getAllRequests().remove(this);
        allProductRequests.remove(this);
        Product.getProductList().remove(this);
        if (Account.getAccountWithUsername(this.getSeller()) instanceof Seller) {
            Seller seller = (Seller) Account.getAccountWithUsername(this.getSeller());
            seller.removeProductRequest(this);
        }
        writeInJ();
    }

    @Override
    public void acceptRequest() throws IOException {
        Seller seller = null;
        if (Account.getAccountWithUsername(this.getSeller()) instanceof Seller) {
            seller = (Seller) Account.getAccountWithUsername(this.getSeller());
        }
        Product newProduct = Product.getProductById(productId);
        newProduct.setDetailProduct(newProduct.getProductImage(), productName, price, Category.getCategoryWithName(categoryName), seller, seller.getFirm(), numberOfProduct);
        newProduct.setAdditionalDetail(additionalDetail);
        newProduct.setProductCategorySpecifications(specialValue);
        // newProduct.getCategorySpecifications().putAll(specialValue);
        if (lastCategory != null) {
            Category.getCategoryWithName(lastCategory).removeProductToCategory(newProduct);
        }
        Category.getCategoryWithName(categoryName).addProductToCategory(newProduct);

        newProduct.setProductStatus(ProductStatus.CONFIRMED);
        getAllRequests().remove(this);
        allProductRequests.remove(this);
        seller.removeProductRequest(this);

    }

//    public void addHashmapValue(String key, String value) throws IOException {
//        specialValue.put(key,value);
//        writeInJ();
//    }

    public void setProductId(String productId) throws IOException {
        this.productId = productId;
        writeInJ();

    }

    public void setProductName(String productName) throws IOException {
        this.productName = productName;
        writeInJ();

    }

    public void setAdditionalDetail(String additionalDetail) throws IOException {
        this.additionalDetail = additionalDetail;
        writeInJ();

    }

    public void setPrice(double price) throws IOException {
        this.price = price;
        writeInJ();

    }

    public void setCompanyName(String companyName) throws IOException {
        this.companyName = companyName;
        writeInJ();
    }

    public void setNumberOfProduct(int numberOfProduct) throws IOException {
        this.numberOfProduct = numberOfProduct;
        writeInJ();

    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setLastCategory(String lastCategory) {
        this.lastCategory = lastCategory;
    }

    public static void setAllProductRequests(ArrayList<ProductRequest> allProductRequests) {
        ProductRequest.allProductRequests = allProductRequests;
    }

    public static ArrayList<ProductRequest> getAllProductRequests() {
        return allProductRequests;
    }

    public static void writeInJ() throws IOException {
        FileHandling.setGson(new Gson());
        String json = FileHandling.getGson().toJson(ProductRequest.allProductRequests, productRequestType);
        FileHandling.writeInFile(json, "productRequest.json");
    }

    //    public void addKey(){
//        for (String tr : categoryName.getTraits()) {
//            specialValue.put(tr, null);
//        }
//    }
//
//    public HashMap<String, String> getSpecialValue() {
//        return specialValue;
//    }
}