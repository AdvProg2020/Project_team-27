package client.view.gui;

import client.Main;
import model.bank.BankAPI;
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
import model.accounts.Manager;
import model.accounts.Seller;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;

public class SellerMenuFx {
    public static final ObservableList data = FXCollections.observableArrayList();
    private static Parent root;
    private static Parent priRoot;

    public static void setPriRoot(Parent priRoot) {
        SellerMenuFx.priRoot = priRoot;
    }

    public void viewPersonalInfo(MouseEvent mouseEvent) throws IOException {
        Parent curRoot = FXMLLoader.load(Objects.requireNonNull(SellerMenuFx.class.getClassLoader().getResource("sellerMenuFx.fxml")));
        ViewAccountFx.setPriRoot(curRoot);
        ViewAccountFx.setAccount(LoginMenu.getLoginAccount());
        root = FXMLLoader.load(Objects.requireNonNull(ViewAccountFx.class.getClassLoader().getResource("viewAccountFx.fxml")));
        goToPage();
    }

    public void viewCompanyInfo(MouseEvent mouseEvent) throws IOException {
        Parent curRoot = FXMLLoader.load(Objects.requireNonNull(SellerMenuFx.class.getClassLoader().getResource("sellerMenuFx.fxml")));
        ViewFirmFx.setPriRoot(curRoot);
        ViewFirmFx.setAccount(LoginMenu.getLoginAccount());
        root = FXMLLoader.load(Objects.requireNonNull(ViewFirmFx.class.getClassLoader().getResource("viewFirmFx.fxml")));
        goToPage();
    }

    public void viewSalesHistory(MouseEvent mouseEvent) throws IOException {
        if (LoginMenu.getLoginAccount() instanceof Seller) {
            Seller seller = (Seller) LoginMenu.getLoginAccount();
            SaleLogsFx.setAllSaleLogs(seller.getSaleLogsHistory());
            Parent curRoot = FXMLLoader.load(Objects.requireNonNull(SellerMenuFx.class.getClassLoader().getResource("sellerMenuFx.fxml")));
            SaleLogsFx.setPriRoot(curRoot);

            root = FXMLLoader.load(Objects.requireNonNull(SaleLogsFx.class.getClassLoader().getResource("saleLogsFx.fxml")));
            SaleLogsFx.setPriRoot(root);
            goToPage();
        }
    }

    public void addProduct(MouseEvent mouseEvent) throws IOException {
        Parent curRoot = FXMLLoader.load(Objects.requireNonNull(SellerMenuFx.class.getClassLoader().getResource("sellerMenuFx.fxml")));
        AddProductMenuFX.setPriRoot(curRoot);
        root = FXMLLoader.load(Objects.requireNonNull(AddProductMenuFX.class.getClassLoader().getResource("addProduct.fxml")));
        goToPage();
    }

    public void viewBalance(MouseEvent mouseEvent) throws IOException {
        String balance = String.valueOf(LoginMenu.getLoginAccount().getCredit());
        show("balance: " + balance);
    }

    public void viewOffs(MouseEvent mouseEvent) throws IOException {
        if (LoginMenu.getLoginAccount() instanceof Seller) {
            Parent curRoot = FXMLLoader.load(Objects.requireNonNull(SellerMenuFx.class.getClassLoader().getResource("sellerMenuFx.fxml")));
            SalesFx.setPriRoot(curRoot);
            Seller seller = (Seller) LoginMenu.getLoginAccount();
            SalesFx.setAllSales(seller.getAllSales());
            root = FXMLLoader.load(Objects.requireNonNull(SalesFx.class.getClassLoader().getResource("salesFx.fxml")));
            goToPage();
        }
    }

    public void showCategories(MouseEvent mouseEvent) throws IOException {
        Parent curRoot = FXMLLoader.load(Objects.requireNonNull(SellerMenuFx.class.getClassLoader().getResource("sellerMenuFx.fxml")));
        CategoriesFX.setPriRoot(curRoot);
        root = FXMLLoader.load(Objects.requireNonNull(CategoriesFX.class.getClassLoader().getResource("categoriesFX.fxml")));
        goToPage();
    }


    public void exchange(MouseEvent mouseEvent) throws IOException {
        Parent curRoot = FXMLLoader.load(Objects.requireNonNull(SellerMenuFx.class.getClassLoader().getResource("sellerMenuFx.fxml")));
        Account account = LoginMenu.getLoginAccount();
        Date date = new Date();
        if (account.getTokenDate() - date.getTime() >= 3600000) {
            BankAPI.startLogin("get_token " + account.getUsername() + " " + account.getPassword(), account);
        }
            BankAPI.startGetBa("get_balance " + account.getToken() , account);
            Exchange.setCustomer(false);
            Exchange.setPriRoot(curRoot);
            root = FXMLLoader.load(Objects.requireNonNull(Exchange.class.getClassLoader().getResource("exchange.fxml")));
            goToPage();

    }

    public void auction(MouseEvent mouseEvent) throws IOException {
        Parent curRoot = FXMLLoader.load(Objects.requireNonNull(SellerMenuFx.class.getClassLoader().getResource("sellerMenuFx.fxml")));
        AddAuctionFx.setPriRoot(curRoot);
        root = FXMLLoader.load(Objects.requireNonNull(AddAuctionFx.class.getClassLoader().getResource("addAuctionFx.fxml")));
        goToPage();
    }

    public void addFile(MouseEvent mouseEvent) throws IOException {
        Parent curRoot = FXMLLoader.load(Objects.requireNonNull(SellerMenuFx.class.getClassLoader().getResource("sellerMenuFx.fxml")));
        AddProductMenuFX.setPriRoot(curRoot);
        root = FXMLLoader.load(Objects.requireNonNull(AddProductMenuFX.class.getClassLoader().getResource("addFileFx.fxml")));
        goToPage();
    }
    public void transactions(MouseEvent mouseEvent) throws IOException {
        Account account = LoginMenu.getLoginAccount();
        Date date = new Date();
        if (account.getTokenDate() - date.getTime() >=3600000) {
            BankAPI.startLogin("get_token " + account.getUsername() + " " + account.getPassword(), account);
        }
        BankAPI.startGetTra("get_transactions " + account.getToken() + " " + "*");
        root = FXMLLoader.load(Objects.requireNonNull(TransactionsFx.class.getClassLoader().getResource("transactionsFx.fxml")));
        goToPage();
    }
    public void manageProducts(MouseEvent mouseEvent) throws IOException {
        if (LoginMenu.getLoginAccount() instanceof Seller) {
            Seller seller = (Seller) LoginMenu.getLoginAccount();
            ProductsFx.setAllProducts(seller.getAllProduct());
            Parent curRoot = FXMLLoader.load(Objects.requireNonNull(SellerMenuFx.class.getClassLoader().getResource("sellerMenuFx.fxml")));
            ProductsFx.setPriRoot(curRoot);
            root = FXMLLoader.load(Objects.requireNonNull(ProductsFx.class.getClassLoader().getResource("productsFx.fxml")));
            goToPage();
        }
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


    public void userMenu(ActionEvent actionEvent) throws IOException {
        Parent curRoot = FXMLLoader.load(Objects.requireNonNull(SellerMenuFx.class.getClassLoader().getResource("sellerMenuFx.fxml")));
        if (LoginMenu.getLoginAccount() instanceof Seller) {
            SellerMenuFx.setPriRoot(curRoot);
            root = FXMLLoader.load(Objects.requireNonNull(SellerMenuFx.class.getClassLoader().getResource("sellerMenuFx.fxml")));
        } else if (LoginMenu.getLoginAccount() instanceof Manager) {
            ManagerMenuFx.setPriRoot(curRoot);
            root = FXMLLoader.load(Objects.requireNonNull(ManagerMenuFx.class.getClassLoader().getResource("managerMenuFx.fxml")));
        } else if (LoginMenu.getLoginAccount() instanceof Customer) {
            CustomerMenuFx.setPriRoot(curRoot);
            root = FXMLLoader.load(Objects.requireNonNull(CustomerMenuFx.class.getClassLoader().getResource("customerMenuFx.fxml")));
        }
        goToPage();
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
