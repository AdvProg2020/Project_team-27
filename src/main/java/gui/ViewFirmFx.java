package gui;

import controller.menus.LoginMenu;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import model.accounts.Account;
import model.accounts.Customer;
import model.accounts.Manager;
import model.accounts.Seller;
import model.request.AccountRequest;
import model.request.Request;

import java.io.IOException;
import java.util.Objects;

public class ViewFirmFx {

    @FXML
    private Label firmPhoneNo;

    @FXML
    private Label firmAddress;

    @FXML
    private Label firmName;

    @FXML
    private Label firmEmail;

    private static Parent priRoot;
    private static Account account;
    private static Request request;
    private static Parent root;

    public static void setPriRoot(Parent priRoot) {
        ViewFirmFx.priRoot = priRoot;
    }

    public static void setRequest(Request request) {
        ViewFirmFx.request = request;
    }

    public static void setAccount(Account account) {
        ViewFirmFx.account = account;
    }

    @FXML
    public void initialize()  {
        if(LoginMenu.getLoginAccount() instanceof Seller) {
            Seller seller = (Seller) account;
            firmName.setText(seller.getFirm().getName());
            firmEmail.setText(seller.getFirm().getEmail());
            firmPhoneNo.setText(String.valueOf(seller.getFirm().getPhoneNO()));
            firmAddress.setText(seller.getFirm().getAddress());
        }else firmRequest();
    }

    private void firmRequest() {
        AccountRequest accountRequest = null;
        if (request instanceof AccountRequest){
            accountRequest = (AccountRequest) request;
        }
        firmName.setText(accountRequest.getFirmName());
        firmEmail.setText(accountRequest.getFirmEmail());
        firmPhoneNo.setText(String.valueOf(accountRequest.getFirmPhoneNO()));
        firmAddress.setText(accountRequest.getFirmAddress());
    }

    private void remove(){
        account = null;
        request = null;
    }


    public void userMenu(ActionEvent actionEvent) throws IOException {
        if(LoginMenu.getLoginAccount() instanceof Seller){
            root = FXMLLoader.load(Objects.requireNonNull(SellerMenuFx.class.getClassLoader().getResource("sellerMenuFx.fxml")));
        } else if(LoginMenu.getLoginAccount() instanceof Manager){
            root = FXMLLoader.load(Objects.requireNonNull(ManagerMenuFx.class.getClassLoader().getResource("managerMenuFx.fxml")));
        }else if(LoginMenu.getLoginAccount() instanceof Customer){
            root = FXMLLoader.load(Objects.requireNonNull(CustomerMenuFx.class.getClassLoader().getResource("customerMenuFx.fxml")));
        }
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
    private static void goToPage() {
        Scene pageTwoScene = new Scene(root);
        //Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        Main.primStage.setScene(pageTwoScene);
        Main.primStage.show();
    }
}