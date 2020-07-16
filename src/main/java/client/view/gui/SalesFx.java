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
import model.off.Sale;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;


public class SalesFx {

    @FXML
    private TableColumn<Sale, Number> saleAmount;
    @FXML
    private TableColumn<Sale, String> saleSeller;
    @FXML
    private TableColumn<Sale, String> saleId;
    @FXML
    private TableColumn<Sale, Date> saleEnd;
    @FXML
    private TableColumn<Sale, Date> saleStart;
    @FXML
    private TableColumn<Sale, String> saleStatus;
    @FXML
    private TableView<Sale> sales;
    @FXML
    private Label salesMs;


    public static ObservableList list = FXCollections.observableArrayList();
    private static Parent root;
    private static ArrayList<Sale> allSales = new ArrayList<>();
    private static Parent priRoot;

    public static ArrayList<Sale> getAllSales() {
        return allSales;
    }

    public static void setPriRoot(Parent priRoot) {
        SalesFx.priRoot = priRoot;
    }

    public static void setAllSales(ArrayList<Sale> allSales) {
        SalesFx.allSales = allSales;
    }

    @FXML
    public void initialize() throws IOException {
        makeTree();
    }

    public static void makeList() {
        list.clear();
        if (LoginMenu.getLoginAccount() instanceof Seller) {
            Seller seller = (Seller) LoginMenu.getLoginAccount();
            list.addAll(seller.getAllSales());
        }
    }

    private void makeTree() {
//        checkIfTimeEnds();
        saleId.setCellValueFactory(new PropertyValueFactory<Sale, String>("offId"));
        saleStart.setCellValueFactory(new PropertyValueFactory<Sale, Date>("startOfSalePeriod"));
        saleEnd.setCellValueFactory(new PropertyValueFactory<Sale, Date>("endOfSalePeriod"));
        saleStatus.setCellValueFactory(new PropertyValueFactory<Sale, String>("saleStatus"));
        saleSeller.setCellValueFactory(new PropertyValueFactory<Sale, String>("seller"));
        saleAmount.setCellValueFactory(new PropertyValueFactory<Sale, Number>("saleAmount"));

        makeList();
        sales.setEditable(true);
        sales.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        sales.getSelectionModel().setCellSelectionEnabled(true);

        sales.setItems(list);
    }

//    private void checkIfTimeEnds() {
//        ArrayList<Sale> sale1 = new ArrayList<>();
//        LocalDate localDate = LocalDate.now();
//        if(Sale.getAllSales().size() != 0) {
//            for (Sale sale : Sale.getAllSales()) {
//                if (sale.getEndOfSalePeriod() != null){
//                    if (sale.getEndOfSalePeriod().isAfter(localDate)) {
//                        sale1.add(sale);
//                    }
//                }
//            }
//            Sale.getAllSales().removeAll(sale1);
//        }
//        makeList();
//    }


    public void add(MouseEvent mouseEvent) throws IOException {
        Parent curRoot = FXMLLoader.load(Objects.requireNonNull(SalesFx.class.getClassLoader().getResource("salesFx.fxml")));
        AddSaleFx.setPriRoot(curRoot);
        root = FXMLLoader.load(Objects.requireNonNull(AddSaleFx.class.getClassLoader().getResource("addSaleFx.fxml")));
        goToPage();

    }

    public void edit(MouseEvent mouseEvent) throws IOException {
        Parent curRoot = FXMLLoader.load(Objects.requireNonNull(SalesFx.class.getClassLoader().getResource("salesFx.fxml")));
        AddSaleFx.setPriRoot(curRoot);
        root = FXMLLoader.load(Objects.requireNonNull(AddSaleFx.class.getClassLoader().getResource("addSaleFx.fxml")));
        goToPage();

    }

    public void viewSale(MouseEvent mouseEvent) throws IOException {
        if (sales.getSelectionModel().getSelectedItem() != null) {
            Sale sale = sales.getSelectionModel().getSelectedItem();
            Parent curRoot = FXMLLoader.load(Objects.requireNonNull(SalesFx.class.getClassLoader().getResource("salesFx.fxml")));
            ViewSaleFx.setPriRoot(curRoot);
            ViewSaleFx.setCurSale(sale);
            root = FXMLLoader.load(Objects.requireNonNull(ViewSaleFx.class.getClassLoader().getResource("viewSaleFx.fxml")));
            goToPage();
        } else salesMs.setText("you have to select first");

    }

    private static void goToPage() {
        Scene pageTwoScene = new Scene(root);
        //Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        Main.primStage.setScene(pageTwoScene);
        Main.primStage.show();
    }


    public void userMenu(ActionEvent actionEvent) throws IOException {
        Parent curRoot = FXMLLoader.load(Objects.requireNonNull(SalesFx.class.getClassLoader().getResource("salesFx.fxml")));
        if(LoginMenu.getLoginAccount() instanceof Seller){
            SellerMenuFx.setPriRoot(curRoot);
            root = FXMLLoader.load(Objects.requireNonNull(SellerMenuFx.class.getClassLoader().getResource("sellerMenuFx.fxml")));
        } else if(LoginMenu.getLoginAccount() instanceof Manager){
            ManagerMenuFx.setPriRoot(curRoot);
            root = FXMLLoader.load(Objects.requireNonNull(ManagerMenuFx.class.getClassLoader().getResource("managerMenuFx.fxml")));
        }else if(LoginMenu.getLoginAccount() instanceof Customer){
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
