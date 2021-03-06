package client.view.gui;

import client.Client;
import client.Main;

import model.bank.BankAPI;
import server.menus.ProductMenu;
import server.menus.LoginMenu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.accounts.Account;
import model.accounts.Customer;
import model.off.Auction;
import model.off.DiscountCode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class CustomerMenuFx {
    public static ObservableList data = FXCollections.observableArrayList();
    private static Parent root;
    private static Parent priRoot;
    ArrayList<DiscountCode> discounts = new ArrayList<>();

    public static void setPriRoot(Parent priRoot) {
        CustomerMenuFx.priRoot = priRoot;
    }

    public void viewPersonalInfo(MouseEvent mouseEvent) throws IOException {
        //     String path = "src/main/java/client.view/music/drop.mp3";
//            Media media = new Media(new File(path).toURI().toString());
//            MediaPlayer mediaPlayer = new MediaPlayer(media);
//            mediaPlayer.setAutoPlay(true);
        Client.start("viewAc "+ LoginMenu.getLoginAccount());
        Parent curRoot = FXMLLoader.load(Objects.requireNonNull(CustomerMenuFx.class.getClassLoader().getResource("customerMenuFx.fxml")));
        ViewAccountFx.setPriRoot(curRoot);
        ViewAccountFx.setAccount(LoginMenu.getLoginAccount());
        root = FXMLLoader.load(Objects.requireNonNull(ViewAccountFx.class.getClassLoader().getResource("viewAccountFx.fxml")));
        goToPage();
    }

    public void viewBalance(MouseEvent mouseEvent) throws IOException {
        //     String path = "src/main/java/client.view/music/drop.mp3";
//            Media media = new Media(new File(path).toURI().toString());
//            MediaPlayer mediaPlayer = new MediaPlayer(media);
//            mediaPlayer.setAutoPlay(true);
        String balance = "your credit is: " + String.valueOf(LoginMenu.getLoginAccount().getCredit());
        show("balance: " + balance);
    }


    public void viewCart(MouseEvent mouseEvent) throws IOException {
        //     String path = "src/main/java/client.view/music/drop.mp3";
//            Media media = new Media(new File(path).toURI().toString());
//            MediaPlayer mediaPlayer = new MediaPlayer(media);
//            mediaPlayer.setAutoPlay(true);
        if (LoginMenu.getLoginAccount() instanceof Customer) {
            if (ProductMenu.getBuyLog() != null) {
                BuyLogFx.setCurBuylog(ProductMenu.getBuyLog());
                //  BuyLogFx.getCurBuyLog().setBuyLogCustomer(LoginMenu.getLoginAccount());
                Parent curRoot = FXMLLoader.load(Objects.requireNonNull(CustomerMenuFx.class.getClassLoader().getResource("customerMenuFx.fxml")));
                BuyLogFx.setPriRoot(curRoot);
                root = FXMLLoader.load(Objects.requireNonNull(BuyLogFx.class.getClassLoader().getResource("buyLogFx.fxml")));
                goToPage();
            } else show("cart is empty");
        }
    }

    public void exchange(MouseEvent mouseEvent) throws IOException {
        Account account = LoginMenu.getLoginAccount();
        Date date = new Date();
        //  BankAPI.startLogin("get_token " + account.getUsername()+" " + account.getPassword(), account);
        if (account.getBankTokenDate() - date.getTime() >= 3600000) {
            BankAPI.startLogin("get_token " + account.getUsername() + " " + account.getPassword(), account);
        }
        BankAPI.startGetBa("get_balance " + account.getBankToken() , account);
        Parent curRoot = FXMLLoader.load(Objects.requireNonNull(CustomerMenuFx.class.getClassLoader().getResource("customerMenuFx.fxml")));
        Exchange.setCustomer(true);
        Exchange.setPriRoot(curRoot);
        root = FXMLLoader.load(Objects.requireNonNull(Exchange.class.getClassLoader().getResource("exchange.fxml")));
        goToPage();
    }

    public void auctions(MouseEvent mouseEvent) throws IOException {
        Parent curRoot = FXMLLoader.load(Objects.requireNonNull(CustomerMenuFx.class.getClassLoader().getResource("customerMenuFx.fxml")));
        AuctionsFx.setPriRoot(curRoot);
        AuctionsFx.setAllAuctions(Auction.getAllAuctions());
        root = FXMLLoader.load(Objects.requireNonNull(AuctionsFx.class.getClassLoader().getResource("auctionsFx.fxml")));
        goToPage();

    }

    public void supporters(MouseEvent mouseEvent) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(SupportersFx.class.getClassLoader().getResource("supportersFx.fxml")));
        goToPage();
    }

    public void viewOrders(MouseEvent mouseEvent) throws IOException {
        //     String path = "src/main/java/client.view/music/drop.mp3";
//            Media media = new Media(new File(path).toURI().toString());
//            MediaPlayer mediaPlayer = new MediaPlayer(media);
//            mediaPlayer.setAutoPlay(true);
        if (LoginMenu.getLoginAccount() instanceof Customer) {
            Parent curRoot = FXMLLoader.load(Objects.requireNonNull(CustomerMenuFx.class.getClassLoader().getResource("customerMenuFx.fxml")));
            BuyLogsFx.setPriRoot(curRoot);
            Customer customer = (Customer) LoginMenu.getLoginAccount();
            BuyLogsFx.setAllBuyLogs(customer.getBuyLogsHistory());
            root = FXMLLoader.load(Objects.requireNonNull(BuyLogsFx.class.getClassLoader().getResource("buyLogsFx.fxml")));
            goToPage();
        }
    }


    public void viewCustomerDiscount(MouseEvent mouseEvent) throws IOException {
        dis();
        Parent curRoot = FXMLLoader.load(Objects.requireNonNull(CustomerMenuFx.class.getClassLoader().getResource("customerMenuFx.fxml")));
        DiscountCodesFx.setDiscounts(discounts);
        DiscountCodesFx.setPriRoot(curRoot);
        root = FXMLLoader.load(Objects.requireNonNull(DiscountCodesFx.class.getClassLoader().getResource("DiscountCodesFx.fxml")));
        goToPage();
    }

    private void dis() {
        //  data.clear();
        if (LoginMenu.getLoginAccount() instanceof Customer) {
            Customer customer = (Customer) LoginMenu.getLoginAccount();
            for (DiscountCode allDiscountCode : DiscountCode.getAllDiscountCodes()) {
                for (Customer customer1 : allDiscountCode.getAllCustomersWithDiscountCode()) {
                    if (customer1.getUsername().equals(customer.getUsername())) {
                        discounts.add(allDiscountCode);
                    }
                }
            }
            //  data.addAll(discounts);
        }
        //  showList();
    }


    public void transactions(MouseEvent mouseEvent) throws IOException {
        Parent curRoot = FXMLLoader.load(Objects.requireNonNull(CustomerMenuFx.class.getClassLoader().getResource("customerMenuFx.fxml")));
        Account account = LoginMenu.getLoginAccount();
        Date date = new Date();
        if (account.getBankTokenDate() - date.getTime() >=3600000) {
            BankAPI.startLogin("get_token " + account.getUsername() + " " + account.getPassword(), account);
        }
        BankAPI.startGetTra("get_transactions " + account.getBankToken() + " " + "*");
        TransactionsFx.setPriRoot(curRoot);
        root = FXMLLoader.load(Objects.requireNonNull(TransactionsFx.class.getClassLoader().getResource("transactionsFx.fxml")));
        goToPage();

    }

    private static void goToPage() {
        Scene pageTwoScene = new Scene(root);
        //Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        Main.primStage.setScene(pageTwoScene);
        Main.primStage.show();
    }

    private void showList() {
        ListView listView = new ListView(data);
        StackPane root = new StackPane();
        root.getChildren().add(listView);
        Stage list = new Stage();
        list.setScene(new Scene(root, 200, 250));
        list.show();
    }

    private void show(String text) {
        Label label = new Label();
        StackPane rot = new StackPane();
        rot.getChildren().add(label);
        label.setText(text);
        Stage massage = new Stage();
        massage.setScene(new Scene(rot, 500, 500));
        massage.show();
    }


    public void back(ActionEvent actionEvent) {
        root = priRoot;
        goToPage();
    }

    public void exit(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void logout(ActionEvent actionEvent) throws IOException {
        LoginMenu.processLogout();
        root = FXMLLoader.load(Objects.requireNonNull(MainMenuFx.class.getClassLoader().getResource("mainMenuFx.fxml")));
        goToPage();
    }


    public void mainMenu(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(MainMenuFx.class.getClassLoader().getResource("mainMenuFx.fxml")));
        goToPage();
    }


}
