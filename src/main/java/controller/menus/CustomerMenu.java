package controller.menus;

import model.accounts.Account;
import model.filtar.Filter;
import model.log.BuyLog;
import model.log.Log;
import model.off.DiscountCode;
import model.productRelated.Product;
import model.productRelated.Score;
import view.*;

import java.io.FileNotFoundException;
import java.io.IOException;


public class CustomerMenu {
    private static int outputNo;
    private static String productID;
    private static boolean hasDiscount;

    public boolean isHasDiscount() {
        return hasDiscount;
    }

    private static boolean isThereBuyLog() {
        if (ProductMenu.getBuyLog() != null) {
            return true;
        }
        outputNo = 12;
        return false;
    }

    //gson
    public static void processViewCart() throws FileNotFoundException {
        if (isThereBuyLog()) {
            OutputHandler.showCustomerLog(ProductMenu.getBuyLog().getId());
            CommandProcessor.setSubMenuStatus(SubMenuStatus.VIEWCART);
        } else OutputMassageHandler.showCustomerOutput(outputNo);
    }

    //gson
    public static void showTotalPrice() throws FileNotFoundException {
        if (isThereBuyLog()) {
            OutputHandler.showTotalPrice(ProductMenu.getBuyLog().getId());
        } else OutputMassageHandler.showCustomerOutput(outputNo);
    }

    //product.......................................................................................
    private static boolean checkProduct(String productID) {
        // if (productID.matches("((?!^ +$)^.+$)")) {
        if (Product.isThereProductWithId(productID)) {
            return true;
        } else outputNo = 1;
        // } else outputNo = 0;
        return false;
    }

    //gson
    public static void showProducts() throws FileNotFoundException {
        if (isThereBuyLog()) {
            OutputHandler.showProducts(Filter.getNewArrayOfProductFilter());
        } else OutputMassageHandler.showCustomerOutput(outputNo);
    }

    //GSON
    public static void viewProduct(String productID) throws FileNotFoundException {
        if (checkProduct(productID)) {
            OutputHandler.showProduct(productID);
            CommandProcessor.setSubMenuStatus(SubMenuStatus.MAINMENU);
            CommandProcessor.setMenuStatus(MenuStatus.PRODUCTMENU);
        } else OutputMassageHandler.showCustomerOutput(outputNo);
    }

    public static void increaseProductNumber(String productID) {
        if (isThereBuyLog()) {
            if (checkProduct(productID)) {
                CustomerMenu.productID = productID;
                CommandProcessor.setSubMenuStatus(SubMenuStatus.INCREASEPRODUCTNUMBER);
                outputNo = 2;
            }
        }
        OutputMassageHandler.showCustomerOutput(outputNo);

    }

    public static void increaseLogProduct(String number) {
        if (number.matches("\\d+")) {
            Product product = Product.getProductById(productID);
            // if (product.getNumberOfProducts() >= p) {
            ProductMenu.getBuyLog().addProductToBuyLog(productID, Integer.parseInt(number));
            // }
        }
    }

    public static void decreaseLogProduct(String number) {
        if (number.matches("\\d+")) {
            ProductMenu.getBuyLog().deleteProductFromBuyLog(productID, Integer.parseInt(number));
        }

    }


    public static void decreaseProductNumber(String productID) {
        if (isThereBuyLog()) {
            if (checkProduct(productID)) {
                CustomerMenu.productID = productID;
                CommandProcessor.setSubMenuStatus(SubMenuStatus.DECREASEPRODUCTNUMBER);
               outputNo=3;
            }
        }OutputMassageHandler.showCustomerOutput(outputNo);
    }


    //purches............................................................................
    public static void purchase() {
        if (LoginMenu.isLogin()) {
            if (LoginMenu.getLoginAccount().getRole().equals("customer")) {
                CommandProcessor.setMenuStatus(MenuStatus.PURCHASE);
                CommandProcessor.setSubMenuStatus(SubMenuStatus.RECIVERINFORMATION);
                CommandProcessor.setInternalMenu(InternalMenu.CHANGEDETAILS);
                outputNo = 5;
            } else outputNo = 9;
        } else outputNo = 6;
        OutputMassageHandler.showCustomerOutput(outputNo);
    }

    private static boolean checkDiscountCode(String discountCodeID) {
        //if (discountCodeID.matches("")) {
        if (DiscountCode.isThereDiscountWithId(discountCodeID)) {
            return true;
        } else outputNo = 7;
        // } else outputNo = ;
        return false;
    }

    public static void haveDiscount(String have) {
        if (have.matches("(?i)(?:yes|no)")) {
            if (have.equalsIgnoreCase("yes")) {
                hasDiscount = true;
                CommandProcessor.setSubMenuStatus(SubMenuStatus.CHECKDISCOUNTCODE);
                outputNo = 1;
            } else {
                hasDiscount = false;
                CommandProcessor.setSubMenuStatus(SubMenuStatus.PAYMENT);
                outputNo = 3;
            }
        } else outputNo = 2;
        OutputMassageHandler.showPurchaseOutput(outputNo);
    }


    public static void discountCodeValidation(String discountCodeId) {
        Account loginAccount = LoginMenu.getLoginAccount();
        DiscountCode discountCode = DiscountCode.getDiscountWithId(discountCodeId);
        if (checkDiscountCode(discountCodeId)) {
            if (discountCode.discountMatchAccount(loginAccount.getUsername())) {
                if (discountCode.discountDateValid()) {
                    if (loginAccount.getUsedDiscount() < DiscountCode.getDiscountWithId(discountCodeId).getTotalTimesOfUse()) {
                        CommandProcessor.setSubMenuStatus(SubMenuStatus.PAYMENT);
                        loginAccount.increaseDiscountUsed();
                        outputNo = 2;
                    } else outputNo = 5;
                } else outputNo = 4;
            } else outputNo = 3;
        }
        OutputMassageHandler.showPurchaseOutput(outputNo);
    }

    public static void payment() {
        if (ProductMenu.getBuyLog().holePriceWithDiscount() <= LoginMenu.getLoginAccount().getCredit()) {
            finishingPayment();
            CommandProcessor.setSubMenuStatus(SubMenuStatus.MAINMENU);
            CommandProcessor.setMenuStatus(MenuStatus.MAINMENU);
            CommandProcessor.setInternalMenu(InternalMenu.MAINMENU);
            outputNo = 0;
        } else {
            outputNo = 0;
        }
        OutputMassageHandler.showPurchaseOutput(outputNo);
    }

    private static void finishingPayment() {
        Account loginAccount = LoginMenu.getLoginAccount();
        double money = loginAccount.getCredit() - ProductMenu.getBuyLog().holePriceWithDiscount();
        loginAccount.setCredit(money);
        //set buy log
        //set say log
        //set manager creadit
        //set seller credit
        //decrease product

    }

    //log.............................................................................

    private static boolean checkLog(String orderID) {
        // if (orderID.matches("(?!^ +$)^.+$")) {
        if (Log.isThereLogWithID(orderID)) {
            return true;
        } else outputNo = 8;
        //} else outputNo = 0;
        return false;
    }

    //gson
    public static void processViewOrders() throws FileNotFoundException {
        OutputHandler.showOrders();
        CommandProcessor.setSubMenuStatus(SubMenuStatus.VIEWORDERS);
    }


    //gson
    public static void showOrder(String orderID) throws FileNotFoundException {
        if (checkLog(orderID)) {
              OutputHandler.showOrder(Log.getLogWithId(orderID));
        }

    }

    //score.............................................................
    public static void rateProduct(String productID, int number) throws IOException {
        if (checkProduct(productID)) {
            if (number >= 1 && number <= 5) {
                for (BuyLog buyLog : LoginMenu.getLoginAccount().getAllBuyLogs()) {
                    if (buyLog.checkIfProductIsBought(productID)){
                        Score newScore = new Score(LoginMenu.getLoginAccount(), Product.getProductById(productID), number);
                        OutputMassageHandler.showOutputWith2String(productID, String.valueOf(number), 1);
                    }
                }
            } else outputNo = 11;
        }
    }

    //GSON
    public static void processViewBalance() throws FileNotFoundException {
        OutputHandler.showBalance(LoginMenu.getLoginAccount().getUsername());
    }

    //GSON
    public static void processViewDiscountCodes() throws FileNotFoundException {
        OutputHandler.showDiscountCodes();
    }

}