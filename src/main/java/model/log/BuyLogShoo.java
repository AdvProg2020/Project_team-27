package model.log;

import javafx.scene.image.ImageView;
import model.productRelated.Product;


import java.util.ArrayList;

public class BuyLogShoo {

    public String productName;
    public int numberOfProduct;
    public String logId;
    public double price;
    public static ArrayList<BuyLogShoo> list = new ArrayList<>();
    public double hole ;

    public ImageView imageView = new ImageView();


    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public BuyLogShoo() {
        list.add(this);
    }

    public double getHole() {
        return hole;
    }

    public void setHole(double hole) {
        this.hole = hole;
    }

    public String getProductName() {
        return productName;
    }

    public double getPrice() {
        return price;
    }

    public int getNumberOfProduct() {
        return numberOfProduct;
    }

    public ArrayList<BuyLogShoo> getList() {
        return list;
    }

    public String getLogId() {
        return logId;
    }

    public void setList(ArrayList<BuyLogShoo> list) {
        this.list = list;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setNumberOfProduct(int numberOfProduct) {
        this.numberOfProduct = numberOfProduct;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public static BuyLogShoo getProduct(String product){
        for (BuyLogShoo allLog : list) {
            if (allLog.productName.equals(product)) {
                return allLog;
            }
        }
        return null;
    }

    public static  boolean isThere(String product){
        for (BuyLogShoo allLog : list) {
            if (allLog.productName.equals(product)) {
                return true;
            }
        }
        return false;
    }

}
