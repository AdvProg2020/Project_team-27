package client.view.gui;

import server.menus.LoginMenu;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.accounts.Customer;
import model.accounts.Manager;
import model.accounts.Seller;

import java.io.IOException;
import java.util.Objects;

public class EditFirmFx {
    @FXML
    private Label firmPhoneNoCrMs;
    @FXML
    private Label firmEmailCrMs;
    @FXML
    private TextField firmAddressCr;
    @FXML
    private Label firmAddressCrMs;
    @FXML
    private TextField firmEmailCr;
    @FXML
    private TextField firmPhoneCr;

    private String type;
    private static Parent root;
    private static Parent priRoot;

    public static void setPriRoot(Parent priRoot) {
        EditFirmFx.priRoot = priRoot;
    }

    public void addFirm(MouseEvent mouseEvent) throws IOException {
        if(LoginMenu.getLoginAccount() instanceof Seller) {
            Seller seller = (Seller) LoginMenu.getLoginAccount();
            LoginMenu.firmName(seller.getFirm().getName());
            firmEmailCrMs.setText(OutputMassageHandler.showFirmOutput(LoginMenu.editFirm(firmEmailCr.getText(), "email")));
            firmAddressCrMs.setText(OutputMassageHandler.showFirmOutput(LoginMenu.editFirm(firmAddressCr.getText(), "address")));
            firmPhoneNoCrMs.setText(OutputMassageHandler.showFirmOutput(LoginMenu.editFirm(firmPhoneCr.getText(), "phone number")));
        }

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



    private static void goToPage() {
        Scene pageTwoScene = new Scene(root);
        //Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        Main.primStage.setScene(pageTwoScene);
        Main.primStage.show();
    }
}
