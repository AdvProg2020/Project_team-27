package client.view.gui;

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
import model.accounts.Supporter;
import serverClient.LoginWindow;

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
        //     String path = "src/main/java/client.view/music/shot.mp3";
//            Media media = new Media(new File(path).toURI().toString());
//            MediaPlayer mediaPlayer = new MediaPlayer(media);
//            mediaPlayer.setAutoPlay(true);
        Parent curRoot = FXMLLoader.load(Objects.requireNonNull(LoginFx.class.getClassLoader().getResource("loginFx.fxml")));
        if (role.equalsIgnoreCase("manager")) {
            ManagerMenuFx.setPriRoot(curRoot);
            root = FXMLLoader.load(Objects.requireNonNull(ManagerMenuFx.class.getClassLoader().getResource("managerMenuFx.fxml")));
            goToPage();
        } else if (role.equalsIgnoreCase("seller")) {
            SellerMenuFx.setPriRoot(curRoot);
            root = FXMLLoader.load(Objects.requireNonNull(SellerMenuFx.class.getClassLoader().getResource("sellerMenuFx.fxml")));
            goToPage();
        } else if (role.equalsIgnoreCase("customer")) {
            CustomerMenuFx.setPriRoot(curRoot);
            root = FXMLLoader.load(Objects.requireNonNull(CustomerMenuFx.class.getClassLoader().getResource("customerMenuFx.fxml")));
            goToPage();
        }else if( LoginMenu.getLoginAccount() instanceof Supporter) {
            LoginWindow loginWindow = new LoginWindow();
            try {
                loginWindow.start(Main.primStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Main.primStage.setScene(LoginWindow.getScene1());
            Main.primStage.show();

        }


    }


    public void login(MouseEvent mouseEvent) throws IOException {
        //     String path = "src/main/java/client.view/music/shot.mp3";
//            Media media = new Media(new File(path).toURI().toString());
//            MediaPlayer mediaPlayer = new MediaPlayer(media);
//            mediaPlayer.setAutoPlay(true);
        int user = LoginMenu.processLogin(userLogin.getText());
        int pass = 0;
        if(LoginMenu.yes) {
            pass = LoginMenu.checkPassword(passLogin.getText());

        }
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

    private static void goToPage() {
        if (root != null) {
            Scene pageTwoScene = new Scene(root);
            //Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            Main.primStage.setScene(pageTwoScene);
            Main.primStage.show();
        }
    }


    public void mainMenu(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(MainMenuFx.class.getClassLoader().getResource("mainMenuFx.fxml")));
        goToPage();
    }

    public void logout(MouseEvent mouseEvent) throws IOException {
        LoginMenu.processLogout();
        root = FXMLLoader.load(Objects.requireNonNull(MainMenuFx.class.getClassLoader().getResource("mainMenuFx.fxml")));
        goToPage();
    }
}