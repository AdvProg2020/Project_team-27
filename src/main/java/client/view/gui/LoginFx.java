package client.view.gui;

import client.Client;
import client.Main;
import client.view.OutputMassageHandler;
import javafx.application.Platform;
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
    static Parent curRoot;
    static MouseEvent mouseEvent;

    @FXML
    public void initialize() throws IOException {

    }

    public static void setPriRoot(Parent priRoot) {
        LoginFx.priRoot = priRoot;
    }

    public static void goToMenu(String role) throws IOException {
        //  FXMLLoader fxmlLoader = new FXMLLoader(LoginFx.class.getResource("loginFx.fxml"));
        //  curRoot = (Parent) fxmlLoader.load();
        //     String path = "src/main/java/client.view/music/shot.mp3";
//            Media media = new Media(new File(path).toURI().toString());
//            MediaPlayer mediaPlayer = new MediaPlayer(media);
//            mediaPlayer.setAutoPlay(true);
        Platform.setImplicitExit(false);
        Platform.startup(() -> {
            try {
                curRoot = FXMLLoader.load(Objects.requireNonNull(LoginFx.class.getClassLoader().getResource("loginFx.fxml")));
                System.out.println("yeyyy");
                if (role.equalsIgnoreCase("manager")) {
                    ManagerMenuFx.setPriRoot(curRoot);
                    root = FXMLLoader.load(Objects.requireNonNull(ManagerMenuFx.class.getClassLoader().getResource("managerMenuFx.fxml")));
                    Scene pageTwoScene = new Scene(root);
                    Stage window = new Stage();
                    window.setScene(pageTwoScene);
                    window.show();
                } else if (role.equalsIgnoreCase("seller")) {
                    SellerMenuFx.setPriRoot(curRoot);
                    root = FXMLLoader.load(Objects.requireNonNull(SellerMenuFx.class.getClassLoader().getResource("sellerMenuFx.fxml")));
                    Scene pageTwoScene = new Scene(root);
                    Stage window = new Stage();
                    window.setScene(pageTwoScene);
                    window.show();
                } else if (role.equalsIgnoreCase("customer")) {
                    CustomerMenuFx.setPriRoot(curRoot);
                    root = FXMLLoader.load(Objects.requireNonNull(CustomerMenuFx.class.getClassLoader().getResource("customerMenuFx.fxml")));
                    Scene pageTwoScene = new Scene(root);
                    Stage window = new Stage();
                    window.setScene(pageTwoScene);
                    window.show();
                } else if (LoginMenu.getLoginAccount() instanceof Supporter) {
                    LoginWindow loginWindow = new LoginWindow();
                    try {
                        loginWindow.start(Main.primStage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Main.primStage.setScene(LoginWindow.getScene1());
                    Main.primStage.show();

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        });


    }


    public void login(MouseEvent mouseEvent) throws IOException {
        //     String path = "src/main/java/client.view/music/shot.mp3";
//            Media media = new Media(new File(path).toURI().toString());
//            MediaPlayer mediaPlayer = new MediaPlayer(media);
//            mediaPlayer.setAutoPlay(true);
        String user = userLogin.getText();
        String pass = passLogin.getText();
        //       int user = LoginMenu.processLogin(userLogin.getText());
//        int pass = 0;
//        if(LoginMenu.yes) {
//            pass = LoginMenu.checkPassword(passLogin.getText());
//        }
        Client.start("login " + user + " " + pass);


        userLoginMs.setText(Client.getInput());
        this.mouseEvent = mouseEvent;
        //       passLoginMs.setText(OutputMassageHandler.showAccountOutput(pass));
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
        this.mouseEvent = mouseEvent;
        root = FXMLLoader.load(Objects.requireNonNull(SignUpFx.class.getClassLoader().getResource("signUpFx.fxml")));
        goToPage();

    }

    private static void goToPage() {
        if (root != null) {
            Scene pageTwoScene = new Scene(root);
            Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            window.setScene(pageTwoScene);
            window.show();
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