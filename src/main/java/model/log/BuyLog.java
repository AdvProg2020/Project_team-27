package model.log;


import server.menus.CustomerMenu;
import model.accounts.Account;
import model.accounts.Seller;
import model.off.DiscountCode;
import model.off.Sale;
import model.productRelated.Product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class BuyLog extends Log {

    int numberOfChosenPro = 0;
    private static boolean firstProduct = true;
    public double holePrice;
    public double price;
    private double salePrice;

    //  private HashMap<Product, Integer> allBoughtProduct = new HashMap<>();
    private HashMap<Product, Integer> chosenProduct = new HashMap<>();
    public static ArrayList<BuyLog> allCustomersLog = new ArrayList<BuyLog>();


    public BuyLog(String logId) throws IOException {
        super(logId);
        allCustomersLog.add(this);
    }


    public double getHolePrice() {
        holePrice =totalPrice() - salePrice;
        return holePrice;
    }

    public void setHolePrice(double holePrice) {
        this.holePrice = holePrice;
    }

    public static ArrayList<BuyLog> getAllCustomersLog() {
        return allCustomersLog;
    }

    public static boolean getFirstProduct() {
        return firstProduct;
    }



    public HashMap<Product, Integer> getChosenProduct() {
        return chosenProduct;
    }

    public double calculateHolePrice() {
        if (CustomerMenu.isHasDiscount()) {
            DiscountCode discountCode = DiscountCode.getDiscountWithId(CustomerMenu.getDiscountID());
            holePrice = discountCode.calculate(totalPrice() - withSale());
            discountCode.setTotalTimesOfUse(discountCode.getTotalTimesOfUse() - 1);
        } else {
            holePrice =totalPrice() - withSale();
        }
        return holePrice;
    }




    public double withSale(){
        for (Product p : chosenProduct.keySet()) {
            if(p.getInSale()){
                if(Sale.getSaleWithId(p.getSale()).checkSale()) {
                    salePrice =salePrice+( chosenProduct.get(p) * Sale.getSaleWithId(p.getSale()).withSale(p));
                }
            }
        }
        return salePrice;
    }

    public double totalPrice() {
        for (Product p : chosenProduct.keySet()) {
            price = chosenProduct.get(p) * p.getPrice();
        }
        return price;
    }

    public void addProductToBuyLog(String productId, int amount) {
        Product product = Product.getProductById(productId);
        for (Product product1 : chosenProduct.keySet()) {
            if (product1.equals(product)){
                numberOfChosenPro = chosenProduct.get(product);
            }else {
                chosenProduct.put(product,1);
            }
        }

        if (product.getNumberOfProducts() > amount) {
            numberOfChosenPro = numberOfChosenPro + amount;
            //product.setNumberOfProducts(product.getNumberOfProducts() - amount);
            chosenProduct.put(product, numberOfChosenPro);
        } else if (product.getNumberOfProducts() == amount) {
            numberOfChosenPro = numberOfChosenPro + amount;
           // product.setNumberOfProducts(0);
          //  product.setIsBought(true);
            chosenProduct.put(product, numberOfChosenPro);
        } else if (product.getNumberOfProducts() < amount) {
            numberOfChosenPro = numberOfChosenPro + product.getNumberOfProducts();
           // product.setNumberOfProducts(0);
            chosenProduct.put(product, product.getNumberOfProducts());
           // product.setIsBought(true);
        }
    }


    public void reduceNumberOfProduct(String productId, int amount) {
        Product product = Product.getProductById(productId);
        for (Product product1 : chosenProduct.keySet()) {
            if (product1.equals(product)){
                numberOfChosenPro = chosenProduct.get(product);
            }
        }
        if (amount < numberOfChosenPro) {
            chosenProduct.put(product, chosenProduct.get(product) - amount);
           // product.setNumberOfProducts(product.getNumberOfProducts() + amount);
        } else if (amount == numberOfChosenPro) {
            chosenProduct.remove(product);
           // product.setNumberOfProducts(product.getNumberOfProducts() + amount);
        } else if (amount > chosenProduct.get(product)) {
           // product.setNumberOfProducts(chosenProduct.get(product));
            chosenProduct.remove(product);
        }
    }


    public ArrayList<Seller> getSellers() {
        ArrayList<Seller> sellers = new ArrayList();
        for (Product p : chosenProduct.keySet()) {
            if(Account.getAccountWithUsername(p.getSeller()) instanceof  Seller) {
                Seller seller = (Seller) Account.getAccountWithUsername(p.getSeller());
                sellers.add(seller);
            }
        }
        return sellers;
    }

    public  boolean checkIfProductIsBought(String productID){
        for (Product product : chosenProduct.keySet()) {
            if(product.getId().equalsIgnoreCase(productID)){
                return true;
            }
        }
        return false;
    }

    public static void setAllCustomersLog(ArrayList<BuyLog> allCustomersLog) {
        BuyLog.allCustomersLog = allCustomersLog;
    }

    @Override
    public String toString() {
        return "BuyLog{" +
                "numberOfChosenPro=" + numberOfChosenPro +
                ", holePrice=" + holePrice +
                ", price=" + price +
                ", salePrice=" + salePrice +
                ", chosenProduct=" + chosenProduct +
                ", logId='" + logId + '\'' +
                ", localDateTimeForLog=" + localDateTimeForLog +
                ", deliveryStatus=" + deliveryStatus +
                '}';
    }

/*
        public void increaseNumberOfProduct(String productId, int amount) {
        Product product = Product.getProductById(productId);
        int numberOfPro = chosenProduct.get(product);
        if (product.getNumberOfProducts() > amount) {
            chosenProduct.put(product, chosenProduct.get(product) + amount);
            product.setNumberOfProducts(product.getNumberOfProducts() - amount);
        } else if (product.getNumberOfProducts() == amount) {
            chosenProduct.put(product, chosenProduct.get(product) + amount);
            product.setNumberOfProducts(0);
            product.setIsBought(true);
        } else if (product.getNumberOfProducts() < amount) {
            chosenProduct.put(product, chosenProduct.get(product) + product.getNumberOfProducts());
            product.setNumberOfProducts(0);
            product.setIsBought(true);
        }
    }

       //done
    public double totalPrice() {
        for (Product p : chosenProduct.keySet()) {
            price = chosenProduct.get(p) * p.getPrice();
        }
        return price;
    }
     */
}