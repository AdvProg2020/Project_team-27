package client.view.gui;

import client.Main;
import server.menus.LoginMenu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;

import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.accounts.Customer;
import model.accounts.Manager;
import model.accounts.Seller;
import model.log.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class SaleLogsFx {
    public static ArrayList<SaleLog> allSaleLogs = new ArrayList<>();
    //@FXML
    //  private TableColumn<SaleLog, DeliveryStatus> saleLogsDeliveryStatus;

    @FXML
    private TableColumn<SaleLog, Date> saleLogsDate = new TableColumn<SaleLog, Date>("Date");

    @FXML
    private TableView<SaleLog> saleLogsTableView = new TableView<>();

    @FXML
    private TableColumn<SaleLog, String> saleLogsId = new TableColumn<SaleLog, String>("SaleLog ID");
    private static Parent root;
    private static Parent priRoot;


    public static ArrayList<SaleLog> getAllSaleLogs() {
        return allSaleLogs;
    }

    public static void setAllSaleLogs(ArrayList<SaleLog> allSaleLogs) {
        SaleLogsFx.allSaleLogs = allSaleLogs;
    }

    @FXML
    private TableColumn<SaleLogShow, String> getSaleLogsId = new TableColumn<>();
    public  static ObservableList<SaleLog> data = FXCollections.observableArrayList();
    @FXML
    private TableColumn<SaleLogShow, LocalDateTime> date = new TableColumn<>();
    boolean first = true;



   /* TableColumn<String, Person> column2 = new TableColumn<>("Last Name");
    column2.setCellValueFactory(new PropertyValueFactory<>("lastName"));*/


//    public static void initializeObserverList() throws FileNotFoundException {
//       // listIni();
//
//        allSale
//        for (SaleLogShow buyLogShow : SaleLogShow.list) {
//            data.clear();
//            if (!data.contains(buyLogShow)) {
//                data.add(buyLogShow);
//            }
//        }
//    }

    public static void listIni() throws FileNotFoundException {
        for (SaleLog saleLog : allSaleLogs) {
            SaleLogShow saleLogShow = new SaleLogShow();
            saleLogShow.saleLogId = saleLog.getLogId();
            saleLogShow.localDateTime = saleLog.localDateTimeForLog;
        }
    }




    @FXML
    public void initialize() throws IOException {
        saleLogsId.setCellValueFactory(new PropertyValueFactory<SaleLog, String>("saleLogId"));
        //  saleLogsDate.setCellValueFactory(new PropertyValueFactory<SaleLogShow, Date>("localDateTime"));
        saleLogsDate.setCellValueFactory(new PropertyValueFactory<SaleLog, Date>("localDateTime"));
            data.clear();
            data.addAll(allSaleLogs);



        saleLogsTableView.setEditable(true);
        saleLogsTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        saleLogsTableView.getSelectionModel().setCellSelectionEnabled(true);
        if(first ) {
            saleLogsTableView.getColumns().addAll(saleLogsId, saleLogsDate);
        }first = false;
      //  saleLogsTableView.setEditable(true);

        saleLogsTableView.setItems(data);

    }



    public void logout(ActionEvent actionEvent) throws IOException {
        LoginMenu.processLogout();
        root = FXMLLoader.load(Objects.requireNonNull(MainMenuFx.class.getClassLoader().getResource("mainMenuFx.fxml")));
        goToPage();
    }


    public void userMenu(ActionEvent actionEvent) throws IOException {
        Parent curRoot = FXMLLoader.load(Objects.requireNonNull(SaleLogsFx.class.getClassLoader().getResource("saleLogsFx.fxml")));
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

    public static void setPriRoot(Parent priRoot) {
        SaleLogsFx.priRoot = priRoot;
    }

    public void back(ActionEvent actionEvent) {
        root=priRoot;
        goToPage();
    }

    public void exit(ActionEvent actionEvent) {
        System.exit(0);
    }

    private static void goToPage(){
        Scene pageTwoScene = new Scene(root);
        //Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        Main.primStage.setScene(pageTwoScene);
        Main.primStage.show();
    }
    public void viewSaleLogFromAllSaleLogs(MouseEvent mouseEvent) throws IOException {
        if(saleLogsTableView.getSelectionModel().getSelectedItem() != null) {
            SaleLog saleLog = saleLogsTableView.getSelectionModel().getSelectedItem();
           // if(Log.getLogWithId(saleLog.saleLogId) instanceof  SaleLog) {
                //SaleLog saleLog1 = (SaleLog) Log.getLogWithId(saleLog.saleLogId);
            Parent curRoot = FXMLLoader.load(Objects.requireNonNull(SaleLogsFx.class.getClassLoader().getResource("saleLogsFx.fxml")));

            SaleLogFx.setPriRoot(curRoot);
                SaleLogFx.setCurSaleLog(saleLog);
                root = FXMLLoader.load(Objects.requireNonNull(SaleLogFx.class.getClassLoader().getResource("saleLogFx.fxml")));
                goToPage();
           // }
        }

    }

}