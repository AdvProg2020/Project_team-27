package controller.menus;

import controller.ProductMenu;
import model.accounts.Account;
import model.accounts.Customer;
import model.accounts.Seller;
import model.log.BuyLog;
import model.log.Log;
import model.log.SaleLog;
import model.off.DiscountCode;
import model.productRelated.Product;
import model.productRelated.Score;
import model.sort.Sort;
import view.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;


public class CustomerMenu {
    private static int outputNo = 0;
    private static String productID = null;
    private static boolean hasDiscount = false;
    private static String discountID = null;
    // private static Customer customer = null;
    private static SaleLog saleLog;
    public static boolean ok = false;

    public static String getDiscountID() {
        return discountID;
    }

    public static boolean isHasDiscount() {
        return hasDiscount;
    }

    private static boolean isThereBuyLog() {
        if (ProductMenu.getBuyLog() != null) {
            return true;
        }
        outputNo = 12;
        return false;
    }

    public static void setOutputNo(int outputNo) {
        CustomerMenu.outputNo = outputNo;
    }

    public static String getProductID() {
        return productID;
    }

    public static void setProductID(String productID) {
        CustomerMenu.productID = productID;
    }

    public static void setHasDiscount(boolean hasDiscount) {
        CustomerMenu.hasDiscount = hasDiscount;
    }

    public static void setDiscountID(String discountID) {
        CustomerMenu.discountID = discountID;
    }

    public static SaleLog getSaleLog() {
        return saleLog;
    }

    public static void setSaleLog(SaleLog saleLog) {
        CustomerMenu.saleLog = saleLog;
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

    public static int getOutputNo() {
        return outputNo;
    }


    public static void increaseLogProduct(String number) {
        // if (number.matches("\\d+")) {
        //  Product product = Product.getProductById(productID);
        ProductMenu.getBuyLog().addProductToBuyLog(productID, Integer.parseInt(number));
        //   outputNo = 10;
        // } else outputNo = 4;
        // OutputMassageHandler.showCustomerOutput(outputNo);
    }

    public static void decreaseLogProduct(String number) {
        //  if (number.matches("\\d+")) {
        ProductMenu.getBuyLog().reduceNumberOfProduct(productID, Integer.parseInt(number));
        //     outputNo = 15;
        // } else outputNo = 4;
        // OutputMassageHandler.showCustomerOutput(outputNo);
    }


    //purches............................................................................


    public static boolean checkDiscountCode(String discountCodeID) {
        //if (discountCodeID.matches("")) {
        if (DiscountCode.isThereDiscountWithId(discountCodeID)) {
            return true;
        } else outputNo = 7;
        // } else outputNo = ;
        return false;
    }

    public static int haveDiscount(String have) {
        if (have.matches("(?i)(?:yes|no)")) {
            if (have.equalsIgnoreCase("yes")) {
                hasDiscount = true;
                //  CommandProcessor.setSubMenuStatus(SubMenuStatus.CHECKDISCOUNTCODE);
                outputNo = 1;
            } else {
                hasDiscount = false;
                //  CommandProcessor.setSubMenuStatus(SubMenuStatus.PAYMENT);
                outputNo = 3;
            }
        } else outputNo = 2;
        return outputNo;
        // OutputMassageHandler.showPurchaseOutput(outputNo);
    }


    public static int discountCodeValidation(String discountCodeId) {
        Account loginAccount = LoginMenu.getLoginAccount();
        DiscountCode discountCode = DiscountCode.getDiscountWithId(discountCodeId);
        if (DiscountCode.isThereDiscountWithId(discountCodeId)) {
            if (discountCode.discountMatchAccount(loginAccount.getUsername())) {
                if (discountCode.discountDateValid()) {
                    if (discountCode.getTotalTimesOfUse() > 0) {
                        // CommandProcessor.setSubMenuStatus(SubMenuStatus.PAYMENT);
                        discountID = discountCodeId;
                        outputNo = 2;
                    } else outputNo = 5;
                } else outputNo = 4;
            } else outputNo = 3;
        } else outputNo = 7;
        return outputNo;

        //OutputMassageHandler.showPurchaseOutput(outputNo);
    }

    public static int payment() throws IOException {
        if (ProductMenu.getBuyLog().holePrice <= LoginMenu.getLoginAccount().getCredit()) {
            finishingPayment();
            ok = true;
            outputNo = 10;
            Seller.writeInJ();
            Customer.writeInJ();
        } else outputNo = 9;
        return outputNo;
        //  OutputMassageHandler.showPurchaseOutput(outputNo);
    }

    private static void finishingPayment() throws IOException {
        double holePrice = ProductMenu.getBuyLog().calculateHolePrice();
        Customer loginAccount = null;
        if (LoginMenu.getLoginAccount() instanceof Customer) {
            loginAccount = (Customer) LoginMenu.getLoginAccount();
        }
        double money = loginAccount.getCredit() - holePrice;
        loginAccount.setCredit(money);
        loginAccount.addLog(ProductMenu.getBuyLog());

        for (Product p : ProductMenu.getBuyLog().getChosenProduct().keySet()) {
            Account.getAccountWithUsername(p.getSeller()).setCredit(Account.getAccountWithUsername(p.getSeller()).getCredit() + p.getPrice());
            int n = p.getNumberOfProducts() - ProductMenu.getBuyLog().getChosenProduct().get(p);
            p.setNumberOfProducts(n);
            if (n == 0) {
                p.setIsBought(true);
            }
            p.getListOfBuyers().add(loginAccount);
        }

        ProductMenu.getBuyLog().setItsFinal(true);

        for (Seller seller : ProductMenu.getBuyLog().getSellers()) {
            for (Product p : ProductMenu.getBuyLog().getChosenProduct().keySet()) {
                if (p.getSeller().equals(seller.getUsername())) {
                    if (!SaleLog.idThereSeller(seller)) {
                        UUID id = UUID.randomUUID();
                        saleLog = new SaleLog(id.toString());
                        seller.addLog(saleLog);
                    } else {
                        saleLog = SaleLog.getLogWithSeller(seller.getUsername());
                    }
                    saleLog.setSeller(seller.getUsername());
                    saleLog.addPrice(p.getPrice());
                    saleLog.addProductToSaleLog(p.getId(), ProductMenu.getBuyLog().getChosenProduct().get(p));
                    if (p.getInSale()) {
                        if (p.getSale().checkSale()) {
                            saleLog.setReducedAmount(p.getSale().withSale(p));
                        }
                    } else saleLog.setReducedAmount(0);
                    saleLog.setReceivedAmount();
                }
            }
        }
    }


    //score.............................................................
    public static int rateProduct(String productI, int number) throws IOException {
        if (checkProduct(productI)) {
            if (number >= 1 && number <= 5) {
                //if (checkCustomer()) {
                if (isBought()) {
                    CustomerMenu.productID = productI;
                    Score newScore = new Score(LoginMenu.getLoginAccount(), Product.getProductById(productID), number);
                    Product.getProductById(productID).score = newScore;
                    // OutputMassageHandler.showOutputWith2String(productID, String.valueOf(number), 1);
                    outputNo = 14;
                } else outputNo = 13;
                // }else outputNo = 9;
            } else outputNo = 11;

        }//OutputMassageHandler.showCustomerOutput(outputNo);
        return outputNo;
    }

    private static boolean isBought() {
        if (LoginMenu.getLoginAccount() instanceof Customer) {
            Customer cus = (Customer) LoginMenu.getLoginAccount();
            for (BuyLog buyLog : cus.getBuyLogsHistory()) {
                if (buyLog.checkIfProductIsBought(productID)) {
                    return true;
                }
            }
        }
        return false;
    }


/*    //GSON
    public static void processViewBalance() throws FileNotFoundException {
        OutputHandler.showBalance(LoginMenu.getLoginAccount().getUsername());
    }

    //GSON
    public static void processViewDiscountCodes() throws FileNotFoundException {
        OutputHandler.showDiscountCodes();
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

    //gson
    public static void showProducts() throws FileNotFoundException {
        if (isThereBuyLog()) {
            OutputHandler.showProducts(Filter.getNewArrayOfProductFilter());
        } else OutputMassageHandler.showCustomerOutput(outputNo);
    }

    //GSON
    public static void viewProduct(String productID) throws FileNotFoundException {
        if (checkProduct(productID)) {
            Product.getProductById(productID).setNumberOfViews();
            OutputHandler.showProduct(productID);
            CommandProcessor.setSubMenuStatus(SubMenuStatus.MAINMENU);
            CommandProcessor.setMenuStatus(MenuStatus.PRODUCTMENU);
        } else OutputMassageHandler.showCustomerOutput(outputNo);
    }

    //gson
    public static void processViewOrders() throws FileNotFoundException {
        OutputHandler.showOrders();
        CommandProcessor.setSubMenuStatus(SubMenuStatus.VIEWORDERS);
    }

 //gson
    public static void showOrder(String orderID) throws FileNotFoundException {
        if (checkLog(orderID)) {
            OutputHandler.showOrder(orderID);
        }else OutputMassageHandler.showCustomerOutput(outputNo);

    }


    public static void decreaseProductNumber(String productID) {
        if (isThereBuyLog()) {
            if (checkProduct(productID)) {
                CustomerMenu.productID = productID;
              // CommandProcessor.setSubMenuStatus(SubMenuStatus.DECREASEPRODUCTNUMBER);
                outputNo = 3;
            }
        }
        OutputMassageHandler.showCustomerOutput(outputNo);
    }

        public static void increaseProductNumber(String productID) {
        if (isThereBuyLog()) {
            if (checkProduct(productID)) {
                CustomerMenu.productID = productID;
              //  CommandProcessor.setSubMenuStatus(SubMenuStatus.INCREASEPRODUCTNUMBER);
                outputNo = 2;
            }
        }
        OutputMassageHandler.showCustomerOutput(outputNo);

    }
        public static void purchase() {
        if (LoginMenu.isLogin()) {
            if (LoginMenu.getLoginAccount().getRole().equals("customer")) {
                //CommandProcessor.setMenuStatus(MenuStatus.PURCHASE);
               // CommandProcessor.setSubMenuStatus(SubMenuStatus.RECIVERINFORMATION);
                //CommandProcessor.setInternalMenu(InternalMenu.CHANGEDETAILS);
                outputNo = 7;
            } else outputNo = 8;
        } else outputNo = 6;
        OutputMassageHandler.showPurchaseOutput(outputNo);
    }

    private static boolean checkCustomer() {
        Account account = LoginMenu.getLoginAccount();
        if (account instanceof Customer) {
            customer = ((Customer) account);
            return true;
        }
        return false;
    }

    private static boolean checkLog(String orderID) {
        // if (orderID.matches("(?!^ +$)^.+$")) {
        if (Log.isThereLogWithID(orderID)) {
            return true;
        } else outputNo = 8;
        //} else outputNo = 0;
        return false;
    }


    public static void sortBy(String sort) throws FileNotFoundException {
        if (sort.matches("(?i)(?:log\\s+date)")) {
            if (sort.matches("log\\s+date")) {
                Customer customer = (Customer) LoginMenu.getLoginAccount();
                Sort.setNewArrayOfBuyLog(customer.getBuyLogsHistory());
                Sort.buyLogSortDate();
            }

        }
    }
 */


}