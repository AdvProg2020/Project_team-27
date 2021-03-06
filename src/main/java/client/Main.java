package client;

import client.view.gui.MainMenuFx;
import client.view.gui.SignUpFx;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
//import javafx.scene.media.Media;
//import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import model.bank.BankAccount;
import model.bank.BankData;
import model.bank.Transaction;
import model.accounts.*;
import model.data.DataBase;
import model.firms.Firm;
import model.off.Auction;
import model.off.DiscountCode;
import model.off.Sale;
import model.productRelated.Category;
import model.productRelated.Comment;
import model.productRelated.Product;
import model.request.*;
import client.view.FileHandling;
import serverClient.ServerMain;

import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;


public class Main extends Application {

    private final int widthScene = 1800;
    private final int heightScene = 700;
    public static Stage primStage;
    public static String[] a;
    Parent root;

    public static class Client extends Thread {
        private static Scanner scanner;
        private static DataOutputStream dataOutputStream;
        private static DataInputStream dataInputStream;
        private static Socket clientSocket;

        public static void buyingFile() throws IOException {
            int filesize = 1022386;
            int bytesRead;
            int currentTot = 0;
            Socket socket = new Socket("127.0.0.1", 15123);
            byte[] bytearray = new byte[filesize];
            InputStream is = socket.getInputStream();
            FileOutputStream fos = new FileOutputStream("copy2.pdf");
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            bytesRead = is.read(bytearray, 0, bytearray.length);
            currentTot = bytesRead;
            do {
                bytesRead = is.read(bytearray, currentTot, (bytearray.length - currentTot));
                if (bytesRead >= 0) currentTot += bytesRead;
            } while (bytesRead > -1);
            bos.write(bytearray, 0, currentTot);
            bos.flush();
            bos.close();
            System.out.println("file received");
            socket.close();
        }

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

//            String path = "src/main/java/client.view/music/background.mp3";
//            Media media = new Media(new File(path).toURI().toString());
//            MediaPlayer mediaPlayer = new MediaPlayer(media);
//            mediaPlayer.setAutoPlay(true);


        if (Manager.getAllManagers().size() == 0) {
            SignUpFx.setRole("manager");
            root = FXMLLoader.load(Objects.requireNonNull(SignUpFx.class.getClassLoader().getResource("managerSignFx.fxml")));
        } else {
            root = FXMLLoader.load(Objects.requireNonNull(MainMenuFx.class.getClassLoader().getResource("mainMenuFx.fxml")));
        }
        primaryStage.setTitle("market");
        primaryStage.setScene(new Scene(root, widthScene, heightScene));
        primStage = primaryStage;
        primaryStage.show();
    }


    public static void main(String[] args) throws IOException, ParseException {
        a = args;
        gson();
        data();
  //      removeAuction();
   //     removeSale();

        if (Manager.getAllManagers().size() != 0) {
            if (Customer.getAllCustomers().size() >= 2) {
                randomDiscount();
            }
        }
        Application.launch(args);

    }

    private static void data() {
        DataBase.getFirm();
        DataBase.getAccount();
        DataBase.getProduct();
        BankData.getAcc();
        DataBase.getBuy();
        DataBase.getSale();
        BankData.getATr();

        for (Account checkAllAccount : Account.getCheckAllAccounts()) {
            System.out.println(checkAllAccount);
        }
        for (Product product : Product.getCheckAllProduct()) {
            System.out.println(product);
        }

    }

    private static void removeSale() {
        for (Sale allSale : Sale.getAllSales()) {
            LocalDate localDate = LocalDate.now();
            if (allSale.getEndOfSalePeriod().isAfter(localDate)) {
                Sale.getAllSales().remove(allSale);
            }
        }
    }

    private static void removeAuction() throws IOException {
        for (Auction allAuction : Auction.getAllAuctions()) {
            LocalDate localDate = LocalDate.now();
            if (allAuction.getEndOfPeriod().isAfter(localDate)) {
                finishingAuction(allAuction);
                Auction.getAllAuctions().remove(allAuction);
            }
        }
    }

    private static void finishingAuction(Auction auction) throws IOException {
        //  String uniqueID = UUID.randomUUID().toString();
        //  BuyLog buyLog = new BuyLog(uniqueID);
        //  buyLog.setCustomer(auction.getCustomer());
        //  buyLog.addProductToBuyLog(auction.getProduct(), 1);
        Customer.getCustomerWithUsername(auction.getCustomer()).reduceCredit(auction.getMoney());
        Seller.getSellerWithUsername(Product.getProductById(auction.getProduct()).getSeller()).increaseCredit(auction.getMoney());
    }

    public static void gson() throws IOException {

        for (DiscountCode allDiscountCode : DiscountCode.getAllDiscountCodes()) {
            System.out.println(allDiscountCode.getDiscountId());
        }
        Type categoryType = new TypeToken<ArrayList<Category>>() {
        }.getType();
        try {
            JsonReader reader3 = new JsonReader(new FileReader("category.json"));
            ArrayList<Category> categoryArrayList = FileHandling.getGson().fromJson(reader3, categoryType);
            if (null == categoryArrayList) {
                categoryArrayList = new ArrayList<>();
            }
            Category.setAllCategories(categoryArrayList);
        } catch (IOException e) {
            FileHandling.writeInFile("", "category.json");
            Category.setAllCategories(new ArrayList<>());
        }


        Type supporterType = new TypeToken<ArrayList<Supporter>>() {
        }.getType();
        try {
            JsonReader reader12 = new JsonReader(new FileReader("supporter.json"));
            ArrayList<Supporter> supporterArrayList = FileHandling.getGson().fromJson(reader12, supporterType);
            if (null == supporterArrayList) {
                supporterArrayList = new ArrayList<>();
            }
            Supporter.setAllSupporters(supporterArrayList);
        } catch (IOException e) {
            FileHandling.writeInFile("", "supporter.json");
            Supporter.setAllSupporters(new ArrayList<>());
        }


        Type bankType = new TypeToken<ArrayList<BankAccount>>() {
        }.getType();
        try {
            JsonReader reader99 = new JsonReader(new FileReader("bankAccount.json"));
            ArrayList<BankAccount> bankAccountArrayList = FileHandling.getGson().fromJson(reader99, bankType);
            if (null == bankAccountArrayList) {
                bankAccountArrayList = new ArrayList<>();
            }
            BankAccount.setAllBankAccount(bankAccountArrayList);

        } catch (IOException e) {
            FileHandling.writeInFile("", "bankAccount.json");
            BankAccount.setAllBankAccount(new ArrayList<>());
        }


        Type transactionType = new TypeToken<ArrayList<Transaction>>() {
        }.getType();
        try {
            JsonReader reader100 = new JsonReader(new FileReader("transaction.json"));
            ArrayList<Transaction> transactionArrayList = FileHandling.getGson().fromJson(reader100, transactionType);
            if (null == transactionArrayList) {
                transactionArrayList = new ArrayList<>();
            }
            Transaction.setAllTransactions(transactionArrayList);
        } catch (IOException e) {
            FileHandling.writeInFile("", "transaction.json");
            Transaction.setAllTransactions(new ArrayList<>());
        }


        Type sellerType = new TypeToken<ArrayList<Seller>>() {
        }.getType();
        try {
            JsonReader reader5 = new JsonReader(new FileReader("seller.json"));
            ArrayList<Seller> sellersArrayList = FileHandling.getGson().fromJson(reader5, sellerType);
            if (null == sellersArrayList) {
                sellersArrayList = new ArrayList<>();
            }
            Seller.setAllSellers(sellersArrayList);
        } catch (IOException e) {
            FileHandling.writeInFile("", "seller.json");
            Seller.setAllSellers(new ArrayList<>());
        }


        Type managerType = new TypeToken<ArrayList<Manager>>() {
        }.getType();
        try {
            JsonReader reader6 = new JsonReader(new FileReader("manager.json"));
            ArrayList<Manager> managerArrayList = FileHandling.getGson().fromJson(reader6, managerType);
            if (null == managerArrayList) {
                managerArrayList = new ArrayList<>();
            }
            Manager.setAllManagers(managerArrayList);
        } catch (IOException e) {
            FileHandling.writeInFile("", "manager.json");
            Manager.setAllManagers(new ArrayList<>());
        }


        Type CustomerType = new TypeToken<ArrayList<Customer>>() {
        }.getType();
        try {
            JsonReader reader7 = new JsonReader(new FileReader("customer.json"));
            ArrayList<Customer> customerArrayList = FileHandling.getGson().fromJson(reader7, CustomerType);
            if (null == customerArrayList) {
                customerArrayList = new ArrayList<>();
            }
            Customer.setAllCustomers(customerArrayList);
        } catch (IOException e) {
            FileHandling.writeInFile("", "customer.json");
            Customer.setAllCustomers(new ArrayList<>());
        }

        Account.setAllAccounts(new ArrayList<>());
        Account.getAllAccounts().addAll(Customer.getAllCustomers());
        Account.getAllAccounts().addAll(Manager.getAllManagers());
        Account.getAllAccounts().addAll(Seller.getAllSellers());
        Account.getAllAccounts().addAll(Supporter.getAllSupporters());

        Type AccountReType = new TypeToken<ArrayList<AccountRequest>>() {
        }.getType();
        try {
            JsonReader reader8 = new JsonReader(new FileReader("accountRequest.json"));
            ArrayList<AccountRequest> accountRequestsArrayList = FileHandling.getGson().fromJson(reader8, AccountReType);
            if (null == accountRequestsArrayList) {
                accountRequestsArrayList = new ArrayList<>();
            }
            AccountRequest.setAllAccountRequests(accountRequestsArrayList);
        } catch (IOException e) {
            FileHandling.writeInFile("", "accountRequest.json");
            AccountRequest.setAllAccountRequests(new ArrayList<>());
        }


        Type CommentReType = new TypeToken<ArrayList<CommentRequest>>() {
        }.getType();
        try {
            JsonReader reader9 = new JsonReader(new FileReader("commentRequest.json"));
            ArrayList<CommentRequest> commentRequests = FileHandling.getGson().fromJson(reader9, CommentReType);
            if (null == commentRequests) {
                commentRequests = new ArrayList<>();
            }
            CommentRequest.setAllCommentRequests(commentRequests);
        } catch (IOException e) {
            FileHandling.writeInFile("", "commentRequest.json");
            CommentRequest.setAllCommentRequests(new ArrayList<>());
        }


        Request.setAllRequests(new ArrayList<>());
        Request.getAllRequests().addAll(CommentRequest.getAllCommentRequests());


        for (Seller seller : Seller.getAllSellers()) {
            Request.getAllRequests().addAll(seller.getAllAccountRequests());

            Request.getAllRequests().addAll(seller.getAllProductRequests());

            Request.getAllRequests().addAll(seller.getAllSaleRequests());

            Product.getProductList().addAll(seller.getAllProduct());

            Sale.getAllSales().addAll(seller.getAllSales());

        }

        for (Seller allSeller : Seller.getAllSellers()) {
            SaleRequest.getAllSaleRequests().addAll(allSeller.getAllSaleRequests());
            Sale.getAllSales().addAll(allSeller.getAllSales());
        }

        for (Sale sale : Sale.getAllSales()) {
            Sale.allProSale.addAll(sale.getAllSaleProducts());
            try {
                for (Product allSaleProduct : sale.getAllSaleProducts()) {
                    System.out.println(allSaleProduct.getProductName());

                }
            } catch (NullPointerException e) {
                System.out.println("null");
            }
        }

        for (Seller seller : Seller.getAllSellers()) {
            Firm.getAllFirms().add(seller.getFirm());

        }

        for (Manager manager : Manager.getAllManagers()) {
            DiscountCode.getAllDiscountCodes().addAll(manager.getAllDiscountCodes());
        }

        for (Seller seller : Seller.getAllSellers()) {
            if (seller.getAllSales().size() != 0) {
                Sale.getAllSales().addAll(seller.getAllSales());
            }
        }


        for (Product product : Product.getAllProduct()) {
            for (Comment proComment : product.proComments) {
                System.out.println(proComment.getId());
            }

            Comment.allComments.addAll(product.proComments);
        }
        for (Seller seller : Seller.getAllSellers()) {
            Auction.getAllAuctions().addAll(seller.getAllAuctions());
        }

    }

    private static void randomDiscount() throws IOException {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        if (day == 5) {
            ArrayList<Customer> randomElement = new ArrayList<>();
            int size = Customer.getAllCustomers().size();
            for (int i = 0; i < (size) / 2; i++) {
                Random rand = new Random();
                int randomIndex = rand.nextInt(size);
                randomElement.add(Customer.getAllCustomers().get(randomIndex));
            }
            LocalDate today = LocalDate.now();
            UUID id = UUID.randomUUID();
            DiscountCode prizeDiscountCode = new DiscountCode(id.toString());
            prizeDiscountCode.setTotalTimesOfUse(1);
            prizeDiscountCode.setDiscountAmount(11);
            prizeDiscountCode.setMaxDiscountAmount(100000);
            prizeDiscountCode.getAllCustomersWithDiscountCode().addAll(randomElement);
            prizeDiscountCode.setStartOfDiscountPeriod(today);
            DiscountCode.setEndOfDiscountPeriod(today.plusDays(10));
            Random rand = new Random();

            int randomIndex = rand.nextInt(Manager.getAllManagers().size());
            Manager.getAllManagers().get(randomIndex).addDiscount(prizeDiscountCode);
            Manager.writeInJ();


        }
    }


}