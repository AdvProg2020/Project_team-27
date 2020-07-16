package client.view.gui;

import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
//import javafx.scene.media.Media;
//import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import model.accounts.Account;
import model.accounts.Customer;
import model.accounts.Manager;
import model.accounts.Seller;
import model.firms.Firm;
import model.log.BuyLog;
import model.off.Auction;
import model.off.DiscountCode;
import model.off.Sale;
import model.productRelated.Category;
import model.productRelated.Comment;
import model.productRelated.Product;
import model.request.*;
import client.view.FileHandling;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;


public class Main extends Application {

    private final int widthScene = 1800;
    private final int heightScene = 700;
    static Stage primStage;
    public static String[] a;
    Parent root;


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


      //  removeAuction();
     //   removeSale();

        if(Manager.getAllManagers().size() != 0){
            if (Customer.getAllCustomers().size() >= 2) {
                randomDiscount();
            }
        }
        Application.launch(args);

    }

    private static void removeSale() {
        for (Sale allSale : Sale.getAllSales()) {
            LocalDate localDate = LocalDate.now();
            if(allSale.getEndOfSalePeriod().isAfter(localDate)){
                Sale.getAllSales().remove(allSale);
            }
        }
    }

    private static void removeAuction() throws IOException {
        for (Auction allAuction : Auction.getAllAuctions()) {
            LocalDate localDate = LocalDate.now();
            if(allAuction.getEndOfPeriod().isAfter(localDate)){
                finishingAuction(allAuction);
                Auction.getAllAuctions().remove(allAuction);
            }
        }
    }

    private static void finishingAuction(Auction auction) throws IOException {
        String uniqueID = UUID.randomUUID().toString();
        BuyLog buyLog = new BuyLog(uniqueID);
        buyLog.addProductToBuyLog(auction.getProduct(), 1);
        Customer.getCustomerWithUsername(auction.getCustomer()).reduceCredit(auction.getMoney());
        Seller.getSellerWithUsername(Product.getProductById(auction.getProduct()).getSeller()).increaseCredit(auction.getMoney());
    }

    private static void gson() throws IOException {

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
            if (seller.getAllSales().size() != 0){
                Sale.getAllSales().addAll(seller.getAllSales());
            }
        }
        BuyLog buyLog = new BuyLog("firstBuyLog");
        buyLog.holePrice = 10;
        buyLog.price = 20;
        buyLog.getChosenProduct().put(Product.getProductById("haha"), 3);
        BuyLog buyLog1 = new BuyLog("secondBuyLog");
        buyLog1.holePrice = 40;
        buyLog1.price = 30;
        buyLog1.getChosenProduct().put(Product.getProductById("third"), 8);

        for (Product product : Product.getAllProduct()) {
            for (Comment proComment : product.proComments) {
                System.out.println(proComment.getId());
            }

            Comment.allComments.addAll(product.proComments);
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