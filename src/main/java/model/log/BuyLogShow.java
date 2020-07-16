package model.log;

import model.productRelated.Category;
import model.productRelated.ProductStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class BuyLogShow {
    public String productId;
    public String productName;
    public double price;
    public int numberOfProducts;
    public String productImageId;
    public double holePrice;
    public String buyLogId;
    public DeliveryStatus deliveryStatus;
    public LocalDateTime localDateTime;
    public  String address;
    public static ArrayList<BuyLogShow> list = new ArrayList<>();


    public BuyLogShow() {
        list.add(this);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public DeliveryStatus getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public static ArrayList<BuyLogShow> getList() {
        return list;
    }

    public String getBuyLogId() {
        return buyLogId;
    }

    public static void setList(ArrayList<BuyLogShow> list) {
        BuyLogShow.list = list;
    }

    public void setBuyLogId(String buyLogId) {
        this.buyLogId = buyLogId;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public String getProductImageId() {
        return productImageId;
    }

    public double getPrice() {
        return price;
    }

    public double getHolePrice() {
        return holePrice;
    }

    public int getNumberOfProducts() {
        return numberOfProducts;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductImageId(String productImageId) {
        this.productImageId = productImageId;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setHolePrice(double holePrice) {
        this.holePrice = holePrice;
    }

    public void setNumberOfProducts(int numberOfProducts) {
        this.numberOfProducts = numberOfProducts;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
