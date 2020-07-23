package client.view.gui;

import client.Client;
import client.Main;
import client.view.OutputMassageHandler;
import javafx.scene.Node;
import javafx.stage.Stage;
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
    private static MouseEvent mouseEvent;

    public static void setPriRoot(Parent priRoot) {
        EditAccountFx.priRoot = priRoot;
    }

    public void editAccount(MouseEvent mouseEvent) throws IOException {
        if (LoginMenu.getLoginAccount() instanceof Seller) {
            LoginMenu.edit();
            this.mouseEvent = mouseEvent;

            if (passSign.getText().trim().isEmpty()) {
                String s = "editSelAc " + passSign.getText() + " password";
                if (s.split("\\s+").length == 3)
                    Client.start(s);
                // passLoginMs.setText(OutputMassageHandler.showAccountOutput(LoginMenu.editSellerField(passSign.getText(), "password")));
            }
            if (lastNameSign.getText().trim().isEmpty()) {
                String s = "editSelAc " + lastNameSign.getText() + " last name";
                if (s.split("\\s+").length == 3)
                    Client.start(s);
                // lastNameLoginMs.setText(OutputMassageHandler.showAccountOutput(LoginMenu.editSellerField(lastNameSign.getText(), "last name")));
            }
            if (emailSign.getText().trim().isEmpty()) {
                String s = "editSelAc " + emailSign.getText() + " email";
                if (s.split("\\s+").length ==3)
                    Client.start(s);
                //  emailLoginMs.setText(OutputMassageHandler.showAccountOutput(LoginMenu.editSellerField(emailSign.getText(), "email")));
            }
            if (phoneNoSign.getText().trim().isEmpty()) {
                String s = "editSelAc " + phoneNoSign.getText() + " phone number";
                if (s.split("\\s+").length == 3)
                    Client.start(s);
                // phoneLoginMs.setText(OutputMassageHandler.showAccountOutput(LoginMenu.editSellerField(phoneNoSign.getText(), "phone number")));
            }
            if (nameSign.getText().trim().isEmpty()) {
                String s = "editSelAc " + nameSign.getText() + " name";
                if (s.split("\\s+").length == 3)
                    Client.start(s);
                //nameLoginMs.setText(OutputMassageHandler.showAccountOutput(LoginMenu.editSellerField(nameSign.getText(), "name")));
            }
        } else {
            if (passSign.getText().trim().isEmpty())
                Client.start("editAc " + passSign.getText() + " password");
            //passLoginMs.setText(OutputMassageHandler.showAccountOutput(LoginMenu.editAccount(passSign.getText(), "password")));
            if (lastNameSign.getText() != null)
                Client.start("editAc " + lastNameSign.getText() + " last name");
            //lastNameLoginMs.setText(OutputMassageHandler.showAccountOutput(LoginMenu.editAccount(lastNameSign.getText(), "last name")));
            if (emailSign.getText() != null)
                Client.start("editAc " + emailSign.getText() + " email");
            //emailLoginMs.setText(OutputMassageHandler.showAccountOutput(LoginMenu.editAccount(emailSign.getText(), "email")));
            if (phoneNoSign.getText() != null)
                Client.start("editAc " + phoneNoSign.getText() + " phone number");
            //phoneLoginMs.setText(OutputMassageHandler.showAccountOutput(LoginMenu.editAccount(phoneNoSign.getText(), "phone number")));
            if (nameSign.getText() != null) {
                Client.start("editAc " + nameSign.getText() + " name");
                //nameLoginMs.setText(OutputMassageHandler.showAccountOutput(LoginMenu.editAccount(nameSign.getText(), "name")));
            }
        }
        //passLoginMs.setText(Client.getInput());

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
        Scene pageTwoScene = new Scene(root);
//        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Stage window = Main.primStage;
        window.setScene(pageTwoScene);
        window.show();
    }

    public void i(ActionEvent actionEvent) {
        root = priRoot;
        Scene pageTwoScene = new Scene(root);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(pageTwoScene);
        window.show();
    }


    public void back(MouseEvent mouseEvent) {
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


    private static void goToPage() {
        Scene pageTwoScene = new Scene(root);
        Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        window.setScene(pageTwoScene);
        window.show();
    }
}
