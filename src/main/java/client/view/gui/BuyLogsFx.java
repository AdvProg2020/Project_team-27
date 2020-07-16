package client.view.gui;

import server.menus.LoginMenu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.accounts.Customer;
import model.accounts.Manager;
import model.accounts.Seller;
import model.log.BuyLog;
import model.log.BuyLogShow;
import model.log.DeliveryStatus;
import model.log.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class BuyLogsFx {

    @FXML
    private TableView<BuyLogShow> buyLogs = new TableView<>();
    @FXML
    private TableColumn<BuyLogShow, LocalDateTime> buyLogsDate = new TableColumn<>();
    @FXML
    private TableColumn<BuyLogShow, String> buyLogsId = new TableColumn<>("id");
    @FXML
    private TableColumn<BuyLogShow, Double> buyLogsTotalPaidAmount = new TableColumn<>("price");
    @FXML
    private Button showScoreButton;
    @FXML
    private static Parent root;
    @FXML
    private static Parent priRoot;
    @FXML
    private Label ms;

    private static ArrayList<BuyLog> allBuyLogs = new ArrayList<>();

    public static ObservableList<BuyLogShow> data = FXCollections.observableArrayList();

    public static Parent getPriRoot() {
        return priRoot;
    }

    public static void setPriRoot(Parent priRoot) {
        BuyLogsFx.priRoot = priRoot;
    }

    public static ArrayList<BuyLog> getAllBuyLogs() {
        return allBuyLogs;
    }

    public static void setAllBuyLogs(ArrayList<BuyLog> allBuyLogs) {
        BuyLogsFx.allBuyLogs = allBuyLogs;
    }


    public static void initializeObserverList() throws FileNotFoundException {
        listIni();
        for (BuyLogShow buyLogShow : BuyLogShow.list) {
            data.clear();
            if (!data.contains(buyLogShow)) {
                data.add(buyLogShow);
            }
        }
    }

    public static void listIni() throws FileNotFoundException {
        for (BuyLog buyLog : BuyLog.getAllCustomersLog()) {
            BuyLogShow buyLogShow = new BuyLogShow();
            buyLogShow.holePrice = buyLog.holePrice;
            buyLogShow.price = buyLog.price;
            buyLogShow.buyLogId = buyLog.getLogId();
            buyLogShow.localDateTime = buyLog.getLocalDateTimeForLog();
        }

    }


    @FXML
    public void initialize() throws IOException {
        buyLogsId.setCellValueFactory(new PropertyValueFactory<BuyLogShow, String>("buyLogId"));
        buyLogsTotalPaidAmount.setCellValueFactory(new PropertyValueFactory<BuyLogShow, Double>("price"));
        buyLogsDate.setCellValueFactory(new PropertyValueFactory<BuyLogShow, LocalDateTime>("localDateTime"));

        initializeObserverList();
        buyLogs.getColumns().addAll(buyLogsId, buyLogsTotalPaidAmount, buyLogsDate);
        buyLogs.setItems(data);
    }


    private static void goToPage() {
        Scene pageTwoScene = new Scene(root);
        Main.primStage.setScene(pageTwoScene);
        Main.primStage.show();
    }

    public void userMenu(ActionEvent actionEvent) throws IOException {
        Parent curRoot = FXMLLoader.load(Objects.requireNonNull(BuyLogsFx.class.getClassLoader().getResource("buyLogsFx.fxml")));
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


    public void rateProduct(MouseEvent mouseEvent) throws IOException {
//        Parent curRoot = FXMLLoader.load(Objects.requireNonNull(ProductMenuFX.class.getClassLoader().getResource("productMenu.fxml")));
//        ProductMenuFX.setProductInPage(product);
//        ProductMenuFX.setPriRoot(curRoot);
//        root = FXMLLoader.load(Objects.requireNonNull(ProductMenuFX.class.getClassLoader().getResource("productMenu.fxml")));
//        goToPage();
    }


    public void delivered(MouseEvent mouseEvent) {
        if (buyLogs.getSelectionModel().getSelectedItem() != null) {
            BuyLogShow buyLog = buyLogs.getSelectionModel().getSelectedItem();
            if (Log.getLogWithId(buyLog.getBuyLogId()) instanceof BuyLog) {
                BuyLog buyLog1 = (BuyLog) Log.getLogWithId(buyLog.getBuyLogId());
                buyLog1.setDeliveryStatus(DeliveryStatus.SENDING);
                ms.setText("changed");
            }
        }
    }

    public void showOrdersFromBuyLog(MouseEvent mouseEvent) throws IOException {
        if (buyLogs.getSelectionModel().getSelectedItem() != null) {
            BuyLogShow buyLog = buyLogs.getSelectionModel().getSelectedItem();
            if(Log.getLogWithId(buyLog.getBuyLogId()) instanceof  BuyLog) {
                BuyLog buyLog1 = (BuyLog) Log.getLogWithId(buyLog.getBuyLogId());
                Parent curRoot = FXMLLoader.load(Objects.requireNonNull(BuyLogsFx.class.getClassLoader().getResource("buyLogsFx.fxml")));
                ViewBuyLogFx.setCurBuyLog(buyLog1);
                ViewBuyLogFx.setPriRoot(curRoot);
              Parent root = FXMLLoader.load(Objects.requireNonNull(ViewBuyLogFx.class.getClassLoader().getResource("viewBuyLog.fxml")));
                Scene pageTwoScene = new Scene(root);
                Main.primStage.setScene(pageTwoScene);
                Main.primStage.show();
            }
        }
    }

    public void clickedColumn(MouseEvent mouseEvent) throws IOException {
//        TablePosition tablePosition = buyLogs.getSelectionModel().getSelectedCells().get(0);
//        int row = tablePosition.getRow();
//        BuyLogShow item = buyLogs.getItems().get(row);
//        TableColumn tableColumn = tablePosition.getTableColumn();
//
//        try {
//            String im = (String) tableColumn.getCellObservableValue(item).getValue();
//            if (BuyLog.getLogWithId(im) instanceof BuyLog) {
//                BuyLog buyLog = (BuyLog) BuyLog.getLogWithId(im);
//                HashMap<Product, Integer> boughtPro = buyLog.getChosenProduct();
//                BuyLogFx.setCurBuylog(buyLog);
//                Parent curRoot = FXMLLoader.load(Objects.requireNonNull(BuyLogFx.class.getClassLoader().getResource("buyLogFx.fxml")));
//                BuyLogFx.setPriRoot(curRoot);
//                root = FXMLLoader.load(Objects.requireNonNull(ViewBuyLogFx.class.getClassLoader().getResource("viewBuyLog.fxml")));
//                goToPage();
//
//            }
//
//        } catch (NullPointerException e) {
//            System.out.println("you cant press here");
//        }
//    }
    }

}