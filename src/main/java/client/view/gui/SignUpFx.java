package client.view.gui;

import server.menus.LoginMenu;
import server.menus.RegisterMenu;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import model.accounts.Customer;
import model.accounts.Manager;
import model.accounts.Seller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Objects;

public class SignUpFx {

    public ImageView userImage = new ImageView();
    @FXML
    public TextField wage;
    @FXML
    public Label wageMs;
    @FXML
    private TextField phoneNoSign;
    @FXML
    private TextField userSign;
    @FXML
    private TextField lastNameSign;
    @FXML
    private PasswordField passSign;
    @FXML
    private TextField emailSign;
    @FXML
    private TextField nameSign;
    @FXML
    private TextField birthdaySign;
    @FXML
    private Label emailLoginMs;
    @FXML
    private Label userLoginMs;
    @FXML
    private Label birthLoginMs;
    @FXML
    private Label nameLoginMs;
    @FXML
    private Label phoneLoginMs;
    @FXML
    private Label passLoginMs;
    @FXML
    private Label lastNameLoginMs;
    @FXML
    private TextField min;
    private static String role;
    private static Parent root;
    private static Parent priRoot;
    private static boolean support = false;
    List<File> files;
    String imageId;

    public boolean isSupport() {
        return support;
    }

    public static void setSupport(boolean support) {
        SignUpFx.support = support;
    }

    public static void setPriRoot(Parent priRoot) {
        SignUpFx.priRoot = priRoot;
    }

    public static void setRole(String role) {
        SignUpFx.role = role;
    }


    @FXML
    public void initialize()  {
        if(support ){
            wage.setDisable(true);
            wage.setVisible(false);
            wageMs.setVisible(false);
            wageMs.setDisable(true);
            min.setVisible(false);
            min.setDisable(true);
        }
    }
    public void signUp(MouseEvent mouseEvent) throws IOException, ParseException {
        if (imageId != null) {
            if (role != null) {
                if (RegisterMenu.getSignUpNo() == 0) {
                    userLoginMs.setText(OutputMassageHandler.showAccountOutput(RegisterMenu.processRegister(role, userSign.getText(), imageId)));
                }
                if (RegisterMenu.getSignUpNo() == 1) {
                    passLoginMs.setText(OutputMassageHandler.showAccountOutput(RegisterMenu.completeRegisterProcess(passSign.getText(), 0)));
                    if (RegisterMenu.getDetailMenu() == 1) {
                        nameLoginMs.setText(OutputMassageHandler.showAccountOutput(RegisterMenu.completeRegisterProcess(nameSign.getText(), 1)));
                    }
                    if (RegisterMenu.getDetailMenu() == 2) {
                        lastNameLoginMs.setText(OutputMassageHandler.showAccountOutput(RegisterMenu.completeRegisterProcess(lastNameSign.getText(), 2)));
                    }
                    if (RegisterMenu.getDetailMenu() == 3) {
                        emailLoginMs.setText(OutputMassageHandler.showAccountOutput(RegisterMenu.completeRegisterProcess(emailSign.getText(), 3)));
                    }
                    if (RegisterMenu.getDetailMenu() == 4) {
                        phoneLoginMs.setText(OutputMassageHandler.showAccountOutput(RegisterMenu.completeRegisterProcess(phoneNoSign.getText(), 4)));
                    }
                    if (RegisterMenu.getDetailMenu() == 5) {
                        birthLoginMs.setText(OutputMassageHandler.showAccountOutput(RegisterMenu.completeRegisterProcess(birthdaySign.getText(), 5)));
                    }
                    if (role == "manager") {
                        wageMs.setText(OutputMassageHandler.showAccountOutput(RegisterMenu.wage(wage.getText())));
                        RegisterMenu.min(min.getText());
                    }
                }
                if (role != null && RegisterMenu.getSignUpNo() == 6) {
                    RegisterMenu.setSignUpNo(0);
                    RegisterMenu.setDetailMenu(0);
                    goToMenu();
                } else RegisterMenu.setSignUpNo(1);
            } else userLoginMs.setText("you have to select your role first");
        } else userLoginMs.setText("drag image first");
    }

    private void goToMenu() throws IOException {
        Parent curRoot = FXMLLoader.load(Objects.requireNonNull(SignUpFx.class.getClassLoader().getResource("signUpFx.fxml")));
        if (role == "seller") {
            FirmFx.setPriRoot(curRoot);
            root = FXMLLoader.load(Objects.requireNonNull(FirmFx.class.getClassLoader().getResource("firmFx.fxml")));
            // root = FXMLLoader.load(Objects.requireNonNull(MainMenuFx.class.getClassLoader().getResource("mainMenuFx.fxml")));

        } else if (role.equalsIgnoreCase("manager") && !RegisterMenu.isHeadManager()) {

            root = FXMLLoader.load(Objects.requireNonNull(ManagerMenuFx.class.getClassLoader().getResource("managerMenuFx.fxml")));
        } else {
            LoginFx.setPriRoot(curRoot);
            root = FXMLLoader.load(Objects.requireNonNull(LoginFx.class.getClassLoader().getResource("loginFx.fxml")));
        }
        Scene pageTwoScene = new Scene(root);
        // Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        Main.primStage.setScene(pageTwoScene);
        Main.primStage.show();

    }

    @FXML
    public void handleDragOver(DragEvent dragEvent) {
        if (dragEvent.getDragboard().hasFiles()) {
            dragEvent.acceptTransferModes(TransferMode.ANY);
        }
    }

    @FXML
    public void handleDrop(DragEvent dragEvent) throws FileNotFoundException {
        files = dragEvent.getDragboard().getFiles();
        System.out.println(files.get(0).getAbsolutePath());
        System.out.println(files.get(0).getPath());
        imageId = files.get(0).getPath();
        File file = new File(imageId);
        Image image = new Image(new FileInputStream(file));
        userImage.setImage(image);

    }


    public void sellerRole(MouseEvent mouseEvent) {
        if (!LoginMenu.isLogin()) {
            role = "seller";
        }
    }

    public void customerRole(MouseEvent mouseEvent) {
        if (!LoginMenu.isLogin()) {
            role = "customer";
        }
    }

    public void managerRole(MouseEvent mouseEvent) {
        if (RegisterMenu.isHeadManager()) {
            role = "manager";
            //RegisterMenu.setHeadManager(false);
        }
    }


    public void userMenu(ActionEvent actionEvent) throws IOException {
        Parent curRoot = FXMLLoader.load(Objects.requireNonNull(SignUpFx.class.getClassLoader().getResource("signUpFx.fxml")));
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
