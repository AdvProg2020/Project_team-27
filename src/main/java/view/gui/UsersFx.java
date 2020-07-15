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
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.accounts.Account;
import model.accounts.Customer;
import model.accounts.Manager;
import model.accounts.Seller;
import model.sort.Sort;
import view.OutputMassageHandler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class UsersFx {

    @FXML
    private TableView<Account> usersList;
    @FXML
    private TableColumn<Account, String> UserId;
    @FXML
    private TableColumn<Account, Double> userPhoneNo;
    @FXML
    private TableColumn<Account, String> userEmail;
    @FXML
    private TableColumn<Account, String> userLast;
    @FXML
    private TableColumn<Account, Date> userBirth;
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

    public static void setPriRoot(Parent priRoot) {
        UsersFx.priRoot = priRoot;
    }

    @FXML
    public void initialize() throws IOException {
        makeTree();
    }

    public static void makeList() {
        list.clear();
        list.addAll(Account.getAllAccounts());
    }

    private void makeTree() {
        UserId.setCellValueFactory(new PropertyValueFactory<Account, String>("username"));
        userName.setCellValueFactory(new PropertyValueFactory<Account, String>("name"));
        userLast.setCellValueFactory(new PropertyValueFactory<Account, String>("lastname"));
        userBirth.setCellValueFactory(new PropertyValueFactory<Account, Date>("birthdayDate"));
        userPhoneNo.setCellValueFactory(new PropertyValueFactory<Account, Double>("phoneNo"));
        online.setCellValueFactory(new PropertyValueFactory<Account, String>("online"));
       // userEmail.setCellValueFactory(new PropertyValueFactory<Account, String>("email"));

        makeList();
        usersList.setEditable(true);
        usersList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        usersList.getSelectionModel().setCellSelectionEnabled(true);

        usersList.setItems(list);
    }


    public void viewUser(MouseEvent mouseEvent) throws IOException {
        if (usersList.getSelectionModel().getSelectedItem() != null) {
            Account a = usersList.getSelectionModel().getSelectedItem();
            Parent curRoot = FXMLLoader.load(Objects.requireNonNull(UsersFx.class.getClassLoader().getResource("usersFx.fxml")));
            ViewAccountFx.setPriRoot(curRoot);
            ViewAccountFx.setAccount(a);
            root = FXMLLoader.load(Objects.requireNonNull(ViewAccountFx.class.getClassLoader().getResource("viewAccountFx.fxml")));
            goToPage();
        } else usersMs.setText("you have to select first");
    }

    public void deleteUser(MouseEvent mouseEvent) throws IOException {
        if (usersList.getSelectionModel().getSelectedItem() != null) {
            Account a = usersList.getSelectionModel().getSelectedItem();
            usersMs.setText(OutputMassageHandler.showAccountOutput(ManagerMenu.deleteUser(a.getUsername())));
            makeTree();
        } else usersMs.setText("you have to select first");
    }

    public void AddManager(MouseEvent mouseEvent) throws IOException {
        SignUpFx.setRole("manager");
        SignUpFx.setSupport(true);
        root = FXMLLoader.load(Objects.requireNonNull(SignUpFx.class.getClassLoader().getResource("managerSignFx.fxml")));
        goToPage();
    }

    public void AddSupporter(MouseEvent mouseEvent) throws IOException {
        SignUpFx.setRole("supporter");
        SignUpFx.setSupport(true);
        root = FXMLLoader.load(Objects.requireNonNull(SignUpFx.class.getClassLoader().getResource("managerSignFx.fxml")));
        goToPage();
    }

    public void sortUser(MouseEvent mouseEvent) throws FileNotFoundException {
        UserId.setCellValueFactory(new PropertyValueFactory<Account, String>("username"));
        userName.setCellValueFactory(new PropertyValueFactory<Account, String>("name"));
        userLast.setCellValueFactory(new PropertyValueFactory<Account, String>("lastname"));
        userBirth.setCellValueFactory(new PropertyValueFactory<Account, Date>("birthdayDate"));
        userPhoneNo.setCellValueFactory(new PropertyValueFactory<Account, Double>("phoneNo"));
        userEmail.setCellValueFactory(new PropertyValueFactory<Account, String>("email"));

        list.clear();
        Sort.setNewArrayOfAccountSort(Account.getAllAccounts());
        list.addAll(Sort.accountSortUserName());

        usersList.setEditable(true);
        usersList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        usersList.getSelectionModel().setCellSelectionEnabled(true);

        usersList.setItems(list);

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
