package view.gui;

import controller.menus.LoginMenu;
import controller.menus.ManagerMenu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.accounts.*;
import model.sort.Sort;
import serverClient.LoginWindow;
import view.OutputMassageHandler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class SupportersFx {


    @FXML
    private TableView<Account> usersList;
    @FXML
    private TableColumn<Account, String> UserId;
    @FXML
    private TableColumn<Account, Double> userPhoneNo;
    @FXML
    private TableColumn<Account, String> userLast;
    @FXML
    private TableColumn<Account, String> userName;
    @FXML
    private TableColumn<Account, String> online;
    @FXML
    private Label usersMs;

    public static ObservableList list = FXCollections.observableArrayList();
    private static Parent root;
    private static ArrayList<Account> accounts = new ArrayList<>();
    private static Parent priRoot;

   

    @FXML
    public void initialize() throws IOException {
        makeTree();
    }

    public static void makeList() {
        list.clear();
        list.addAll(Supporter.getAllSupporters());
    }

    private void makeTree() {
        UserId.setCellValueFactory(new PropertyValueFactory<Account, String>("username"));
        userName.setCellValueFactory(new PropertyValueFactory<Account, String>("name"));
        userLast.setCellValueFactory(new PropertyValueFactory<Account, String>("lastname"));
        userPhoneNo.setCellValueFactory(new PropertyValueFactory<Account, Double>("phoneNo"));
        online.setCellValueFactory(new PropertyValueFactory<Account, String>("online"));
        

        makeList();
        usersList.setEditable(true);
        usersList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        usersList.getSelectionModel().setCellSelectionEnabled(true);

        usersList.setItems(list);
    }

    public void viewUser(MouseEvent mouseEvent) {
        if (usersList.getSelectionModel().getSelectedItem() != null) {
            Account supporter = usersList.getSelectionModel().getSelectedItem();

            Main.primStage.setScene(LoginWindow.getScene1());
            Main.primStage.show();
        }
    }

    private static void goToPage() {
        Scene pageTwoScene = new Scene(root);
        //Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        Main.primStage.setScene(pageTwoScene);
        Main.primStage.show();
    }

    public void userMenu(ActionEvent actionEvent) throws IOException {
        Parent curRoot = FXMLLoader.load(Objects.requireNonNull(UsersFx.class.getClassLoader().getResource("usersFx.fxml")));
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


}
