package client.view.gui;

import client.Main;
import model.bank.BankAPI;
import model.bank.Transaction;
import server.menus.LoginMenu;
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
import model.accounts.Account;
import model.accounts.Customer;
import model.accounts.Manager;
import model.accounts.Seller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class TransactionsFx {


    @FXML
    private TableColumn<Transaction, Number> sourceAccount;

    @FXML
    private TableColumn<Transaction, Number> money;

    @FXML
    private Label usersMs;

    @FXML
    private TableColumn<Transaction, Number> destAccount;

    @FXML
    private TableColumn<Transaction, String> id;

    @FXML
    private TableView<Transaction> transactions;

    @FXML
    private TableColumn<Transaction, String> type;
    @FXML
    private TableColumn<Transaction, Number> paid;

    @FXML
    private TableColumn<Transaction, String> description;

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
        list.addAll(Transaction.getAllTransactions());
    }

    private void makeTree() {

        sourceAccount.setCellValueFactory(new PropertyValueFactory<Transaction, Number>("sourceAccountI"));
        destAccount.setCellValueFactory(new PropertyValueFactory<Transaction, Number>("destAccountID"));
        id.setCellValueFactory(new PropertyValueFactory<Transaction, String>("id"));
        type.setCellValueFactory(new PropertyValueFactory<Transaction, String>("receiptTyp"));
        money.setCellValueFactory(new PropertyValueFactory<Transaction, Number>("money"));
        paid.setCellValueFactory(new PropertyValueFactory<Transaction, Number>("paid"));
        description.setCellValueFactory(new PropertyValueFactory<Transaction, String>("description"));

        makeList();
        transactions.setEditable(true);
        transactions.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        transactions.getSelectionModel().setCellSelectionEnabled(true);

        transactions.setItems(list);
    }


    public void pay(MouseEvent mouseEvent) throws IOException {
        if (transactions.getSelectionModel().getSelectedItem() != null) {
            Transaction tr = transactions.getSelectionModel().getSelectedItem();
            BankAPI.start("pay " + tr.getId());
            payMarket(tr);
            usersMs.setText("paid");
        }
    }

    private void payMarket(Transaction transaction) throws IOException {
        if(transaction.getReceiptType().equalsIgnoreCase("deposit")){
            Account account =LoginMenu.getLoginAccount();
            account.increaseCredit(transaction.getMoney());
        }else if(transaction.getReceiptType().equalsIgnoreCase("withdraw")){
            Account account =LoginMenu.getLoginAccount();
            account.setCredit(account.getCredit()-transaction.getMoney());
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
