package view.gui;

import controller.menus.LoginMenu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.accounts.Customer;
import model.off.DiscountCode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class CustomerMenuFx {
    public static ObservableList data = FXCollections.observableArrayList();
    private static Parent root;
    private static Parent priRoot;
    ArrayList<DiscountCode> discounts = new ArrayList<>();

    public static void setPriRoot(Parent priRoot) {
        CustomerMenuFx.priRoot = priRoot;
    }

    public void viewPersonalInfo(MouseEvent mouseEvent) throws IOException {
        Parent curRoot = FXMLLoader.load(Objects.requireNonNull(CustomerMenuFx.class.getClassLoader().getResource("customerMenuFx.fxml")));
        ViewAccountFx.setPriRoot(curRoot);
        ViewAccountFx.setAccount(LoginMenu.getLoginAccount());
        root = FXMLLoader.load(Objects.requireNonNull(ViewAccountFx.class.getClassLoader().getResource("viewAccountFx.fxml")));
        goToPage();
    }

    public void viewBalance(MouseEvent mouseEvent) throws IOException {
        String balance = "your credit is: " + String.valueOf(LoginMenu.getLoginAccount().getCredit());
        show("balance: " + balance);
    }

    ///////////////////////////////////////////////////////
    public void viewCart(MouseEvent mouseEvent) throws IOException {
        if (LoginMenu.getLoginAccount() instanceof Customer) {
            //BuyLogFx.setCurBuyLog(LoginMenu.getLoginAccount().);
            Parent curRoot = FXMLLoader.load(Objects.requireNonNull(CustomerMenuFx.class.getClassLoader().getResource("customerMenuFx.fxml")));
           // BuyLogFx.setPriRoot(curRoot);
            root = FXMLLoader.load(Objects.requireNonNull(BuyLogFx.class.getClassLoader().getResource("buyLogFx.fxml")));
            goToPage();
        }
    }

    ////////////////////////////////////////////
    public void viewOrders(MouseEvent mouseEvent) throws IOException {
        if (LoginMenu.getLoginAccount() instanceof Customer) {
            Parent curRoot = FXMLLoader.load(Objects.requireNonNull(CustomerMenuFx.class.getClassLoader().getResource("customerMenuFx.fxml")));
            ViewAccountFx.setPriRoot(curRoot);
            BuyLogsFx.setAllBuyLogs(((Customer) LoginMenu.getLoginAccount()).getBuyLogsHistory());

            root = FXMLLoader.load(Objects.requireNonNull(BuyLogsFx.class.getClassLoader().getResource("buyLogsFx.fxml")));
            goToPage();
        }
    }

    ///////////////////////////
    public void viewCustomerDiscount(MouseEvent mouseEvent) throws IOException {
        dis();
        DiscountCodesFx.setDiscounts(discounts);
        root = FXMLLoader.load(Objects.requireNonNull(DiscountCodesFx.class.getClassLoader().getResource("DiscountCodesFx.fxml")));
        goToPage();
    }

    private void dis(){
        data.clear();
        if (LoginMenu.getLoginAccount() instanceof Customer) {
            Customer customer = (Customer) LoginMenu.getLoginAccount();
            for (DiscountCode allDiscountCode : DiscountCode.getAllDiscountCodes()) {
                for (Customer customer1 : allDiscountCode.getAllCustomersWithDiscountCode()) {
                    if (customer1.equals(customer)) {
                        discounts.add(allDiscountCode);
                    }
                }
            }
            data.addAll(discounts);
        }
        showList();
    }


    private static void goToPage() {
        Scene pageTwoScene = new Scene(root);
        //Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        Main.primStage.setScene(pageTwoScene);
        Main.primStage.show();
    }

    private void showList() {
        ListView listView = new ListView(data);
        StackPane root = new StackPane();
        root.getChildren().add(listView);
        Stage list = new Stage();
        list.setScene(new Scene(root, 200, 250));
        list.show();
    }

    private void show(String text) {
        Label label = new Label();
        StackPane rot = new StackPane();
        rot.getChildren().add(label);
        label.setText(text);
        Stage massage = new Stage();
        massage.setScene(new Scene(rot, 500, 500));
        massage.show();
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
