package client.view.gui;

import client.Main;
import model.bank.BankAPI;
import server.menus.LoginMenu;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.accounts.Account;
import model.accounts.Customer;
import model.accounts.Manager;
import model.accounts.Seller;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;

public class Exchange {
    @FXML
    private Label bankCreditLb;

    @FXML
    private Label marketCredit;
    @FXML
    private Label id;
    @FXML
    private TextField amount;
    @FXML
    private Button withdraw;
    private static Parent root;
    private static Parent priRoot;
    private static Account account;
    private static boolean customer = false;
    double bankCredit;

    public static Account getAccount() {
        return account;
    }

    public static void setAccount(Account account) {
        Exchange.account = account;
    }

    public static void setPriRoot(Parent priRoot) {
        Exchange.priRoot = priRoot;
    }


    public static boolean isCustomer() {
        return customer;
    }

    public static void setCustomer(boolean customer) {
        Exchange.customer = customer;
    }

    @FXML
    public void initialize() throws IOException {
        if (customer) {
            withdraw.setDisable(true);
            withdraw.setVisible(false);
        }
        account = LoginMenu.getLoginAccount();
        bankCredit = account.getBankMoney();
        bankCreditLb.setText(String.valueOf(bankCredit));
        marketCredit.setText(String.valueOf(account.getCredit()));
    }

    public void withdrawMoney(MouseEvent mouseEvent) throws IOException {
        //bardasht az market
        if (amount.getText().matches("\\d+")) {
            int curAmount = Integer.parseInt((amount.getText()));
            double marketCredit = account.getCredit();
            if (curAmount <= marketCredit) {
                Account account = LoginMenu.getLoginAccount();
                Date date = new Date();
                if (account.getBankTokenDate() - date.getTime() >= 3600000) {
                    BankAPI.startLogin("get_token " + account.getUsername() + " " + account.getPassword(), account);
                }
                if (Manager.getAllManagers().get(0).getMin() <= marketCredit - curAmount) {
                    BankAPI.startTran("create_receipt " + LoginMenu.getLoginAccount().getBankToken() + " " + "withdraw " + curAmount + " " + account.getAccountId() + " " + -1, account);
//                    id.setText(String.valueOf(account.getTransactions().get(account.getTransactions().size() - 1)));

                } else id.setText("not enough money");
            }else id.setText("not enough money");
        }

    }

    public void depositMoney(MouseEvent mouseEvent) throws IOException {
        //variz be market
        if (amount.getText().matches("\\d+")) {
            int curAmount = Integer.parseInt((amount.getText()));
            double marketCredit = account.getCredit();
            if (bankCredit >= curAmount) {
                Account account = LoginMenu.getLoginAccount();
                Date date = new Date();
                if (account.getBankTokenDate() - date.getTime() >= 3600000) {
                    BankAPI.startLogin("get_token " + account.getUsername() + " " + account.getPassword(), account);
                }
                if (Manager.getAllManagers().get(0).getMin() <= marketCredit - curAmount) {
                    BankAPI.startTran("create_receipt " + LoginMenu.getLoginAccount().getBankToken() + " " + "deposit " + curAmount + " " + -1 + " " + account.getAccountId(), account);
                    //id.setText(String.valueOf(account.getTransactions().get(account.getTransactions().size())));
                } else id.setText("not enough money");
            }

        }
    }


    private static void goToPage() {
        customer = false;
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