package model.productRelated;

import com.google.gson.reflect.TypeToken;
import model.accounts.Account;
import model.accounts.Customer;
import model.firms.Firm;
import client.view.FileHandling;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

public class Product {

    private String productId;
    private String productName;
    private double price;
    private String seller;
    private ProductStatus productStatus;
    private Category category;
    private int numberOfProducts;
    private boolean isInSale;
    private String additionalDetail;
    private int numberOfViews = 0;
    private int totalNumberOfBuyers;
    private boolean isBought;
    private boolean inAuction = false;
    String productImageId;
    String productVideoId;
    private String categoryName;
    private File file;

    private ArrayList<Customer> listOfBuyers = new ArrayList<>();
    private static ArrayList<Product> allProduct = new ArrayList<Product>();
    private static ArrayList<Product> checkAllProduct = new ArrayList<Product>();
    public ArrayList<Comment> proComments = new ArrayList<Comment>();
    public ArrayList<String> productCategorySpecifications = new ArrayList<>();

  //  private Comment comment;
    public double score = 0;
    public int scorePeople = 0;
    private String sale;
    private Firm firm;


    public Product(String productId) throws IOException {
        if (productId != null) {
            this.productId = productId;
        }
        allProduct.add(this);
        // listOfId.add(productId);
        //writeInJ();
    }


    public void setFirm(Firm firm) {
        this.firm = firm;
    }

    public static ArrayList<Product> getCheckAllProduct() {
        return checkAllProduct;
    }

    public static void setCheckAllProduct(ArrayList<Product> checkAllProduct) {
        Product.checkAllProduct = checkAllProduct;
    }

    public void setAllProduct(String productId, String productName, double price, String seller, ProductStatus productStatus, int numberOfProducts, String additionalDetail, double score) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.seller = seller;
        this.productStatus = productStatus;
        this.numberOfProducts = numberOfProducts;
        this.additionalDetail = additionalDetail;
        this.score = score;
        checkAllProduct.add(this);
    }

    public String getProductVideoId() {
        return productVideoId;
    }

    public void setProductVideoId(String productVideoId) {
        this.productVideoId = productVideoId;
    }

    //id,productImage,name,price,category,seller
    public void setDetailProduct(String productImageId, String name, double price, Category category, Account selle, Firm firm, int numberOfProducts) throws IOException {
        if (name != null) {
            this.productName = name;
        }
        if (firm != null) {
            this.firm = firm;
        }
        if (price != 0) {
            this.price = price;
        }
        if (selle != null) {
            this.seller = selle.getUsername();
        }
        if (numberOfProducts != 0) {
            this.numberOfProducts = numberOfProducts;
        }
        if (category != null) {
            this.category = category;
            categoryName = category.getName();
        }
        if (productImageId != null) {
            this.productImageId = productImageId;
        }
        writeInJ();
    }

    //settersAndGetters----------------------------------------------------------------------------------
    public ArrayList<String> getProductCategorySpecifications() {
        return productCategorySpecifications;
    }

    public void setProductCategorySpecifications(ArrayList<String> productCategorySpecifications) {
        this.productCategorySpecifications = productCategorySpecifications;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public boolean isInAuction() {
        return inAuction;
    }

    public void setInAuction(boolean inAuction) {
        this.inAuction = inAuction;
    }

    public String getProductImage() {
        return productImageId;
    }

    public void setProductImage(String productImage) {
        this.productImageId = productImage;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public static void setAllProduct(ArrayList<Product> allProduct) {
        Product.allProduct = allProduct;
    }

    public ArrayList<Customer> getListOfBuyers() {
        return listOfBuyers;
    }

    public void setIsBought(Boolean isBought) {
        this.isBought = isBought;
    }

    public boolean getIsBought() {
        return isBought;
    }

    public Firm getFirm() {
        return firm;
    }

    public void setScore(int score) {
        this.score = score;
        // proScores.add(score);
    }

    public void setComment(Comment comment) {
        //this.comment = comment;
        proComments.add(comment);
    }

    public String getId() {
        return productId;
    }

    public void setNumberOfProducts(int numberOfProducts) {
        this.numberOfProducts = numberOfProducts;
    }

    public int getNumberOfProducts() {
        return numberOfProducts;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public int getNumberOfView() {
        return numberOfViews;
    }

    public void setNumberOfViews(int numberOfViews) {
        this.numberOfViews = numberOfViews;
    }

    public void setAdditionalDetail(String additionalDetail) {
        this.additionalDetail = additionalDetail;
    }

    public String getAdditionalDetail() {
        return additionalDetail;
    }

    public void setProductStatus(ProductStatus status) {
        productStatus = status;
    }

    public ProductStatus getProductStatus() {
        return productStatus;
    }

    public boolean getInSale() {
        return isInSale;
    }

    public void setInSale(boolean inSale) {
        isInSale = inSale;
    }

    public Category getCategory() {
        return category;
    }

    public String getProductName() {
        return productName;
    }

    public double getScore() {
        return score;
    }

    public String getSale() {
        return sale;
    }

    public void setSale(String sale) {
        this.sale = sale;
        setInSale(true);
    }

    public void getSaleP(String sale) {
        this.sale = sale;
        setInSale(false);
    }

    public void setTotalNumberOfBuyers(int totalNumberOfBuyers) {
        this.totalNumberOfBuyers = totalNumberOfBuyers;
    }

    public int getTotalNumberOfBuyers() {
        return totalNumberOfBuyers;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }



    //othersTobeHandel-------------------------------------------------------------------------------
    public static Product getProductById(String id) {
        for (Product product : allProduct) {
            if (product.productId.equalsIgnoreCase(id)) {
                return product;
            }
        }
        return null;
    }

    public static void deleteProduct(String productId) {
        Product product = getProductById(productId);
        Iterator iterator = allProduct.iterator();
        while (iterator.hasNext()) {
            Product product1 = (Product) iterator.next();
            if (product1.equals(product)) {
                iterator.remove();
            }
        }
    }

    public static boolean isThereProductWithId(String productId) {
        Product product = getProductById(productId);
        return product != null;
    }

    public static ArrayList<Product> getProductList() {
        return allProduct;
    }

    public static Product getProductWithName(String name) {
        for (Product product : allProduct) {
            if (product.getProductName().equals(name)) {
                return product;
            }
        }
        return null;
    }

    public void deleteProductByCategory(String categoryId) {
        Category category = Category.getCategoryWithName(categoryId);
        category.getAllProducts().removeAll(category.getAllProducts());
    }

    public static ArrayList<Product> getAllProduct() {
        return allProduct;
    }

    public static Comparator<Product> productComparatorForView = new Comparator<Product>() {
        public int compare(Product s1, Product s2) {

            int productView1 = s1.getNumberOfView();
            int productView2 = s2.getNumberOfView();
            return productView1 - productView2;

        }
    };

    public static Product getProductWithImage(String imageView) {
        for (Product product : allProduct) {
            if (product.getProductImage().equals(imageView)) {

                return product;
            }
        }
        return null;
    }

    public static void writeInJ() throws IOException {
        Type collectionType = new TypeToken<ArrayList<Product>>() {
        }.getType();
        String json = FileHandling.getGson().toJson(Product.allProduct, collectionType);
        FileHandling.writeInFile(json, "product.json");
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", seller=" + seller +
                ", productStatus=" + productStatus +
                ", category=" + category +
                ", numberOfProducts=" + numberOfProducts +
                ", isInSale=" + isInSale +
                ", additionalDetail='" + additionalDetail + '\'' +
                ", numberOfViews=" + numberOfViews +
                ", totalNumberOfBuyers=" + totalNumberOfBuyers +
                ", isBought=" + isBought +
           //     ", comment=" + comment +
                ", score=" + score +
                ", sale=" + sale +
                '}';
    }

}