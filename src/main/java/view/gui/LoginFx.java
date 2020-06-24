package view.gui;

import controller.menus.LoginMenu;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import view.OutputMassageHandler;

import java.io.IOException;
import java.util.Objects;

public class LoginFx {
    private static Parent root;
    @FXML
    private TextField userLogin;

    @FXML
    private PasswordField passLogin;

    @FXML
    private Label passLoginMs;

    @FXML
    private Label userLoginMs;
    private static Parent priRoot;

    public static void setPriRoot(Parent priRoot) {
        LoginFx.priRoot = priRoot;
    }

    public static void goToMenu(String role) throws IOException {
        Parent curRoot  = FXMLLoader.load(Objects.requireNonNull(LoginFx.class.getClassLoader().getResource("loginFx.fxml")));
        if(role.equalsIgnoreCase("manager")) {
            ManagerMenuFx.setPriRoot(curRoot);
             root = FXMLLoader.load(Objects.requireNonNull(ManagerMenuFx.class.getClassLoader().getResource("managerMenuFx.fxml")));
        }else  if(role.equalsIgnoreCase("seller")) {
            SellerMenuFx.setPriRoot(curRoot);
             root = FXMLLoader.load(Objects.requireNonNull(SellerMenuFx.class.getClassLoader().getResource("sellerMenuFx.fxml")));
        }else  if(role.equalsIgnoreCase("customer")) {
            CustomerMenuFx.setPriRoot(curRoot);
             root = FXMLLoader.load(Objects.requireNonNull(CustomerMenuFx.class.getClassLoader().getResource("customerMenuFx.fxml")));
        }

        goToPage();
    }


    public void login(MouseEvent mouseEvent) throws IOException {
        String username = userLogin.getText();
        String password = passLogin.getText();
        int user =  LoginMenu.processLogin(username);
        int pass =  LoginMenu.checkPassword(password);
        userLoginMs.setText(OutputMassageHandler.showAccountOutput(user));
        passLoginMs.setText(OutputMassageHandler.showAccountOutput(pass));
    }

    public void back(MouseEvent mouseEvent) {
        root = priRoot;
        goToPage();

    }

    public void exit(MouseEvent mouseEvent) {
        System.exit(0);
    }

    public void signUpMenu(MouseEvent mouseEvent) throws IOException {
        Parent curRoot = FXMLLoader.load(Objects.requireNonNull(LoginFx.class.getClassLoader().getResource("LoginFx.fxml")));
        SignUpFx.setPriRoot(curRoot);
        root = FXMLLoader.load(Objects.requireNonNull(SignUpFx.class.getClassLoader().getResource("signUpFx.fxml")));
       goToPage();

    }

    private static void goToPage(){
        Scene pageTwoScene = new Scene(root);
        //Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        Main.primStage.setScene(pageTwoScene);
        Main.primStage.show();
    }




}