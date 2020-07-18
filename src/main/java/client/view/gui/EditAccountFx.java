package client.view.gui;

import client.Main;
import client.view.OutputMassageHandler;
import server.menus.LoginMenu;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.accounts.Customer;
import model.accounts.Manager;
import model.accounts.Seller;

import java.io.IOException;
import java.util.Objects;

public class EditAccountFx {

    @FXML
    private TextField userSign;
    @FXML
    private TextField lastNameSign;
    @FXML
    private Label nameLoginMs;
    @FXML
    private Label phoneLoginMs;
    @FXML
    private PasswordField passSign;
    @FXML
    private TextField phoneNoSign;
    @FXML
    private Label emailLoginMs;
    @FXML
    private Label passLoginMs;
    @FXML
    private TextField emailSign;
    @FXML
    private TextField nameSign;
    @FXML
    private Label userLoginMs;
    @FXML
    private Label lastNameLoginMs;
    private static Parent priRoot;
    private static Parent root;

    public static void setPriRoot(Parent priRoot) {
        EditAccountFx.priRoot = priRoot;
    }

    public void editAccount(MouseEvent mouseEvent) throws IOException {
        if (LoginMenu.getLoginAccount() instanceof Seller) {
            LoginMenu.edit();
            if (passSign.getText() != null)
                passLoginMs.setText(OutputMassageHandler.showAccountOutput(LoginMenu.editSellerField(passSign.getText(), "password")));
            if (lastNameSign.getText() != null)
                lastNameLoginMs.setText(OutputMassageHandler.showAccountOutput(LoginMenu.editSellerField(lastNameSign.getText(), "last name")));
            if (emailSign.getText() != null)
                emailLoginMs.setText(OutputMassageHandler.showAccountOutput(LoginMenu.editSellerField(emailSign.getText(), "email")));
            if (phoneNoSign.getText() != null)
                phoneLoginMs.setText(OutputMassageHandler.showAccountOutput(LoginMenu.editSellerField(phoneNoSign.getText(), "phone number")));
            if (nameSign.getText() != null)
                nameLoginMs.setText(OutputMassageHandler.showAccountOutput(LoginMenu.editSellerField(nameSign.getText(), "name")));
        }else {
            if (passSign.getText().trim().isEmpty())
                passLoginMs.setText(OutputMassageHandler.showAccountOutput(LoginMenu.editAccount(passSign.getText(), "password")));
            if (lastNameSign.getText() != null)
                lastNameLoginMs.setText(OutputMassageHandler.showAccountOutput(LoginMenu.editAccount(lastNameSign.getText(), "last name")));
            if (emailSign.getText() != null)
                emailLoginMs.setText(OutputMassageHandler.showAccountOutput(LoginMenu.editAccount(emailSign.getText(), "email")));
            if (phoneNoSign.getText() != null)
                phoneLoginMs.setText(OutputMassageHandler.showAccountOutput(LoginMenu.editAccount(phoneNoSign.getText(), "phone number")));
            if (nameSign.getText() != null) {
                nameLoginMs.setText(OutputMassageHandler.showAccountOutput(LoginMenu.editAccount(nameSign.getText(), "name")));
            }
        }

    }

    public void userMenu(ActionEvent actionEvent) throws IOException {
        Parent curRoot = FXMLLoader.load(Objects.requireNonNull(EditAccountFx.class.getClassLoader().getResource("editAccountFx.fxml")));
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


    private static void goToPage() {
        Scene pageTwoScene = new Scene(root);
        //Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        Main.primStage.setScene(pageTwoScene);
        Main.primStage.show();
    }
}
