package client.view.gui;

import client.Client;
import client.Main;
import javafx.scene.Node;
import server.menus.ProductMenu;
import server.menus.LoginMenu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.accounts.Account;
import model.accounts.Customer;
import model.accounts.Manager;
import model.accounts.Seller;
import model.off.DiscountCode;
import model.request.AccountRequest;
import model.request.Request;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static client.view.gui.LoginFx.mouseEvent;

public class ViewAccountFx {
    @FXML
    private Label birthday = new Label();
    @FXML
    private Label lastName = new Label();
    @FXML
    private Label role = new Label();
    @FXML
    private Label name = new Label();
    @FXML
    private ImageView accountImg = new ImageView();
    @FXML
    private Label credit = new Label();
    @FXML
    private Label phoneNo = new Label();
    @FXML
    private Label email = new Label();
    @FXML
    private Label username = new Label();

    public static final ObservableList data = FXCollections.observableArrayList();
    private static ArrayList<DiscountCode> dis = new ArrayList();
    private static Parent root;
    private static Account account;
    private static Request request;
    private static Parent priRoot;
    private static MouseEvent mouseEvent;
    private Customer customer;

    public static void setPriRoot(Parent priRoot) {
        ViewAccountFx.priRoot = priRoot;
    }

    public static void setAccount(Account account) {
        ViewAccountFx.account = account;
    }

    public static void setRequest(Request request) {
        ViewAccountFx.request = request;
    }

    @FXML
    public void initialize() throws FileNotFoundException {
        if (request == null) {
            String[] s = Client.getInput().split("\\s+");
            username.setText(s[0]);
            name.setText(s[1]);
            lastName.setText(s[2]);
            role.setText(s[3]);
            phoneNo.setText(s[4]);
            email.setText(s[5]);
            credit.setText(s[6]);
            birthday.setText(s[7]);
            File file = new File(s[8]);
           // Image image = new Image(new FileInputStream(file));
          //  accountImg.setImage(image);

        } else showRequest();
    }

 /*   @FXML
    public void initialize() throws FileNotFoundException {
        if (request == null) {
            Account curAccount = account;
            username.setText(curAccount.getUsername());
            name.setText(curAccount.getName());
            lastName.setText(curAccount.getLastname());
            role.setText(curAccount.getRole());
            phoneNo.setText(String.valueOf(curAccount.getPhoneNo()));
            email.setText(curAccount.getEmail());
            credit.setText(String.valueOf(curAccount.getCredit()));
            birthday.setText(String.valueOf(curAccount.getBirthdayDate()));
            File file = new File(curAccount.getImageId());
            Image image = new Image(new FileInputStream(file));
            accountImg.setImage(image);

        } else showRequest();
    }

  */
    private void showRequest() {
        AccountRequest accountRequest = null;
        if (request instanceof AccountRequest) {
            accountRequest = (AccountRequest) request;
        }
        username.setText(accountRequest.getUsername());
        name.setText(accountRequest.getName());
        lastName.setText(accountRequest.getLastname());
        role.setText("seller");
        phoneNo.setText(String.valueOf(accountRequest.getPhoneNo()));
        email.setText(accountRequest.getEmail());
        credit.setText(null);
        birthday.setText(String.valueOf(accountRequest.getBirthdayDate()));
    }


    public void viewCart(MouseEvent mouseEvent) throws IOException {
        if (LoginMenu.getLoginAccount() instanceof Customer) {
            if(ProductMenu.getBuyLog() != null) {
                Parent curRoot = FXMLLoader.load(Objects.requireNonNull(SaleLogFx.class.getClassLoader().getResource("saleLogFx.fxml")));
                BuyLogFx.setCurBuylog(ProductMenu.getBuyLog());
                BuyLogFx.setPriRoot(curRoot);
                root = FXMLLoader.load(Objects.requireNonNull(BuyLogFx.class.getClassLoader().getResource("buyLogFx.fxml")));
                goToPage();
            }else show("dont have cart");
        } else show("you can't see cart");
    }


    public void viewCustomerDiscount(MouseEvent mouseEvent) throws IOException {
        Parent curRoot = FXMLLoader.load(Objects.requireNonNull(ViewAccountFx.class.getClassLoader().getResource("viewAccountFx.fxml")));
        if (LoginMenu.getLoginAccount() instanceof Customer) {
            customer = (Customer) LoginMenu.getLoginAccount();
            findDis();
            DiscountCodesFx.setDiscounts(dis);
            DiscountCodesFx.setPriRoot(curRoot);
            root = FXMLLoader.load(Objects.requireNonNull(DiscountCodesFx.class.getClassLoader().getResource("discountCodesFx.fxml")));
            goToPage();
        } else if (LoginMenu.getLoginAccount() instanceof Manager) {
            Manager customer = (Manager) LoginMenu.getLoginAccount();
            DiscountCodesFx.setDiscounts(customer.getAllDiscountCodes());
            DiscountCodesFx.setPriRoot(curRoot);
            root = FXMLLoader.load(Objects.requireNonNull(DiscountCodesFx.class.getClassLoader().getResource("discountCodesFx.fxml")));
            goToPage();
        } else show("you can't see discounts");


    }

    private void findDis() {
        if (LoginMenu.getLoginAccount() instanceof Customer) {
            Customer customer = (Customer) LoginMenu.getLoginAccount();
            for (DiscountCode allDiscountCode : DiscountCode.getAllDiscountCodes()) {
                for (Customer customer1 : allDiscountCode.getAllCustomersWithDiscountCode()) {
                    if (customer1.getUsername().equals(customer.getUsername())) {
                        dis.add(allDiscountCode);
                    }
                }
            }
        }
    }


    public void viewLogs(MouseEvent mouseEvent) throws IOException {
        Parent curRoot = FXMLLoader.load(Objects.requireNonNull(ViewAccountFx.class.getClassLoader().getResource("viewAccountFx.fxml")));
        if (LoginMenu.getLoginAccount() instanceof Seller) {
            Seller seller = (Seller) LoginMenu.getLoginAccount();
            SaleLogsFx.setPriRoot(curRoot);
            SaleLogsFx.setAllSaleLogs(seller.getSaleLogsHistory());
            root = FXMLLoader.load(Objects.requireNonNull(SaleLogsFx.class.getClassLoader().getResource("saleLogsFx.fxml")));
            goToPage();
        } else if (LoginMenu.getLoginAccount() instanceof Customer) {
            Customer customer = (Customer) LoginMenu.getLoginAccount();
            BuyLogsFx.setPriRoot(curRoot);
            BuyLogsFx.setAllBuyLogs(customer.getBuyLogsHistory());
            root = FXMLLoader.load(Objects.requireNonNull(BuyLogsFx.class.getClassLoader().getResource("vuyLogsFx.fxml")));
            goToPage();
        } else show("you can't see logs");
    }


    private static void goToPage() {
        remove();
        Scene pageTwoScene = new Scene(root);
        Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        window.setScene(pageTwoScene);
        window.show();
    }

    private void show(String text) {
        Label label = new Label();
        StackPane root = new StackPane();
        root.getChildren().add(label);
        label.setText(text);
        Stage massage = new Stage();
        massage.setScene(new Scene(root, 500, 500));
        massage.show();
    }

    private static void remove() {
        account = null;
        request = null;
    }

    public void viewFirm(MouseEvent mouseEvent) throws IOException {
        Parent curRoot = FXMLLoader.load(Objects.requireNonNull(ViewAccountFx.class.getClassLoader().getResource("viewAccountFx.fxml")));
        if (LoginMenu.getLoginAccount() instanceof Seller) {
            ViewFirmFx.setAccount(LoginMenu.getLoginAccount());
            ViewFirmFx.setPriRoot(curRoot);
            this.mouseEvent = mouseEvent;
            root = FXMLLoader.load(Objects.requireNonNull(ViewFirmFx.class.getClassLoader().getResource("viewFirmFx.fxml")));
            goToPage();
        } else if (LoginMenu.getLoginAccount() instanceof Manager) {
            if(request != null) {
                ViewFirmFx.setRequest(request);
                ViewFirmFx.setPriRoot(curRoot);
                this.mouseEvent = mouseEvent;
                root = FXMLLoader.load(Objects.requireNonNull(ViewFirmFx.class.getClassLoader().getResource("viewFirmFx.fxml")));
                goToPage();
            }
        }

    }

    public void edit(MouseEvent mouseEvent) throws IOException {
        Parent curRoot = FXMLLoader.load(Objects.requireNonNull(ViewAccountFx.class.getClassLoader().getResource("viewAccountFx.fxml")));
        EditAccountFx.setPriRoot(curRoot);
        this.mouseEvent = mouseEvent;
        root = FXMLLoader.load(Objects.requireNonNull(EditAccountFx.class.getClassLoader().getResource("editAccountFx.fxml")));
        goToPage();
    }

    public void userMenu(ActionEvent actionEvent) throws IOException {

        Parent curRoot = FXMLLoader.load(Objects.requireNonNull(ViewAccountFx.class.getClassLoader().getResource("viewAccountFx.fxml")));
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
        Scene pageTwoScene = new Scene(root);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(pageTwoScene);
        window.show();
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
        Scene pageTwoScene = new Scene(root);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(pageTwoScene);
        window.show();
    }

}
