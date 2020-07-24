package server.menus;

import model.bank.BankAPI;
import model.accounts.Account;
import model.accounts.Customer;
import model.accounts.Manager;
import model.accounts.Seller;
import model.data.DataBase;
import model.log.BuyLog;
import model.log.SaleLog;
import model.off.DiscountCode;
import model.off.Sale;
import model.productRelated.Product;
import server.Server;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Random;
import java.util.UUID;


public class CustomerMenu {
    private static int outputNo = 0;
    private static String productID = null;
    private static boolean hasDiscount = false;
    private static String discountID = null;
    // private static Customer customer = null;
    private static SaleLog saleLog;
    private static boolean market = true;
    public static boolean ok = false;
    private static DiscountCode prizeDiscountCode;


    public static boolean isMarket() {
        return market;
    }

    public static void setMarket(boolean market) {
        CustomerMenu.market = market;
    }


    public static DiscountCode getPrizeDiscountCode() {
        return prizeDiscountCode;
    }

    public static void setPrizeDiscountCode(DiscountCode prizeDiscountCode) {
        CustomerMenu.prizeDiscountCode = prizeDiscountCode;
    }

    public static String getDiscountID() {
        return discountID;
    }

    public static boolean isHasDiscount() {
        return hasDiscount;
    }

    public static void setDiscountID(String discountID) {
        CustomerMenu.discountID = discountID;
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

    //purches............................................................................
    public static int discountCodeValidation(String discountCodeId) {
        Account loginAccount = LoginMenu.getLoginAccount();
        DiscountCode discountCode = DiscountCode.getDiscountWithId(discountCodeId);
        if (DiscountCode.isThereDiscountWithId(discountCodeId)) {
            if (discountCode.discountMatchAccount(loginAccount.getUsername())) {
                if (discountCode.discountDateValid()) {
                    if (discountCode.getTotalTimesOfUse() > 0) {
                        // CommandProcessor.setSubMenuStatus(SubMenuStatus.PAYMENT);
                        discountID = discountCodeId;
                        hasDiscount = true;
                        outputNo = 2;
                    } else outputNo = 5;
                } else outputNo = 4;
            } else outputNo = 3;
        } else outputNo = 1;
        return outputNo;

        // OutputMassageHandler.showPurchaseOutput(outputNo);
    }


    public static int payment() throws IOException {
        //   if (ProductMenu.getBuyLog().calculateHolePrice() <= LoginMenu.getLoginAccount().getCredit()) {
        finishingPayment();
        ok = true;
        outputNo = 10;
        Seller.writeInJ();
        Customer.writeInJ();
        //} else outputNo = 9;
        return outputNo;
        //  OutputMassageHandler.showPurchaseOutput(outputNo);
    }

    public static void bankPayment(double hole ) throws IOException {
        Date date = new Date();
        Account account = LoginMenu.getLoginAccount();
        Account manager = Manager.getAllManagers().get(Manager.getAllManagers().size() - 1);
        if (account.getBankTokenDate() - date.getTime() >=3600000) {
            BankAPI.startLogin("get_token " + account.getUsername() + " " + account.getPassword(), account);
        }
        BankAPI.startTran("create_receipt " + account.getBankToken() + " " + "move " +(int) hole+" " + account.getAccountId() + " " + manager.getAccountId(), account);

    }

    private static void finishingPayment() throws IOException {
        BuyLog buyLog = ProductMenu.getBuyLog();
        double holePrice = buyLog.calculateHolePrice();
        Customer loginAccount = null;
        if (LoginMenu.getLoginAccount() instanceof Customer) {
            loginAccount = (Customer) LoginMenu.getLoginAccount();
        }

        if (market) {
            if (holePrice <= loginAccount.getCredit()) {
                double money = loginAccount.getCredit() - holePrice;
                loginAccount.setCredit(money);
            } else outputNo = 9;
        }


        loginAccount.addLog(buyLog);
        buyLog.setCustomer(loginAccount.getUsername());

        if (holePrice > 1000000) {
            getDiscountPrize(loginAccount);
        }


        for (Product p : buyLog.getChosenProduct().keySet()) {
            if (p.getFile() != null) {
                Server.file = p.getFile();
                try {
                    Server.serverSellFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //  Account.getAccountWithUsername(p.getSeller()).setCredit(Account.getAccountWithUsername(p.getSeller()).getCredit() + p.getPrice());
            int n = p.getNumberOfProducts() - buyLog.getChosenProduct().get(p);
            p.setNumberOfProducts(n);
            if (n == 0) {
                p.setIsBought(true);
            }
            p.getListOfBuyers().add(loginAccount);
        }

        buyLog.setItsFinal(true);

        for (Seller seller : buyLog.getSellers()) {
            for (Product p : buyLog.getChosenProduct().keySet()) {
                if (p.getSeller().equals(seller.getUsername())) {
                    if (!SaleLog.idThereSeller(seller.getUsername())) {
                        UUID id = UUID.randomUUID();
                        saleLog = new SaleLog(id.toString());
                        seller.addLog(saleLog);
                    } else {
                        saleLog = SaleLog.getLogWithSeller(seller.getUsername());
                    }
                    saleLog.setSeller(seller.getUsername());
                    saleLog.addPrice(p.getPrice() * buyLog.getChosenProduct().get(p));
                    saleLog.addProductToSaleLog(p.getId(), buyLog.getChosenProduct().get(p));
                    if (p.getInSale()) {
                        if (Sale.getSaleWithId(p.getSale()).checkSale()) {
                            saleLog.setReducedAmount(Sale.getSaleWithId(p.getSale()).withSale(p) * buyLog.getChosenProduct().get(p));
                        }
                    } else saleLog.setReducedAmount(0);
                }
            }
            double rec = saleLog.getReceivedAmount();
            double finalAmount = rec - ((rec * Manager.getAllManagers().get(0).getWage()) / 100);
            saleLog.setReceivedAmount(finalAmount);
            //if(market){
            seller.increaseCredit(finalAmount);
            if (!market) {
                bankPayment(holePrice);
            }
            DataBase.insertBuyLog(buyLog);
            DataBase.insertSaleLog(saleLog);
        }
    }

    private static void getDiscountPrize(Account loginAccount) throws IOException {
        LocalDate today = LocalDate.now();
        UUID id = UUID.randomUUID();
        prizeDiscountCode = new DiscountCode(id.toString());
        prizeDiscountCode.setTotalTimesOfUse(1);
        prizeDiscountCode.setDiscountAmount(10);
        prizeDiscountCode.setMaxDiscountAmount(100000);
        prizeDiscountCode.addAccount(loginAccount);
        prizeDiscountCode.setStartOfDiscountPeriod(today);
        DiscountCode.setEndOfDiscountPeriod(today.plusDays(10));
        Random rand = new Random();
        int randomIndex = rand.nextInt(Manager.getAllManagers().size());
        Manager.getAllManagers().get(randomIndex).addDiscount(prizeDiscountCode);
        Manager.writeInJ();
    }

    //score.............................................................
    public static int rateProduct(String productI, int number) throws IOException {
        if (checkProduct(productI)) {
            // if (number >= 1 && number <= 5) {
            //if (checkCustomer()) {
            if (isBought(productI)) {
                CustomerMenu.productID = productI;
                Product p = Product.getProductById(productI);
                int people = p.scorePeople + 1;
                p.score = ((p.score * p.scorePeople) + number) / people;
                //Score newScore = new Score(LoginMenu.getLoginAccount(), Product.getProductById(productID), number);
                // Product.getProductById(productID).score = newScore;
                // OutputMassageHandler.showOutputWith2String(productID, String.valueOf(number), 1);
                outputNo = 14;
            } else outputNo = 13;
            // }else outputNo = 9;
            // } else outputNo = 11;

        }//OutputMassageHandler.showCustomerOutput(outputNo);
        return outputNo;
    }

    private static boolean isBought(String productID) {
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
        private static boolean isThereBuyLog() {
        if (ProductMenu.getBuyLog() != null) {
            return true;
        }
        outputNo = 12;
        return false;
    }
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
    pu
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
 */


}