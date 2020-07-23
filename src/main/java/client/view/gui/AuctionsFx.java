package client.view.gui;


import client.Main;
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
import model.accounts.Customer;
import model.accounts.Manager;
import model.accounts.Seller;
import model.log.BuyLog;
import model.off.Auction;
import model.productRelated.Product;
import server.menus.LoginMenu;
import serverClient.LoginWindow;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class AuctionsFx {

    @FXML
    private TableColumn<Auction, String> product = new TableColumn<>();
    @FXML
    private TableColumn<Auction, Number> max = new TableColumn<>();
    @FXML
    private TableColumn<Auction, String> saleId = new TableColumn<>();
    @FXML
    private TableColumn<Auction, Date> saleEnd = new TableColumn<>();
    @FXML
    private TableColumn<Auction, Date> saleStart = new TableColumn<>();
    @FXML
    private TableView<Auction> sales = new TableView<>();
    @FXML
    private Label salesMs = new Label();


    public static ObservableList<Auction> list = FXCollections.observableArrayList();
    private static Parent root;
    private static ArrayList<Auction> allAuctions = new ArrayList<>();
    private static Parent priRoot;


    public static ArrayList<Auction> getAllAuctions() {
        return allAuctions;
    }

    public static void setAllAuctions(ArrayList<Auction> allAuctions) {
        AuctionsFx.allAuctions = allAuctions;
    }

    public static Parent getPriRoot() {
        return priRoot;
    }

    public static void setPriRoot(Parent priRoot) {
        AuctionsFx.priRoot = priRoot;
    }

    @FXML
    public void initialize() throws IOException {
        makeTree();
    }

    public static void makeList() {
        list.clear();

        list.addAll(Auction.getAllAuctions());

    }

    private void makeTree() throws IOException {

        removeAuction();
        saleId.setCellValueFactory(new PropertyValueFactory<Auction, String>("id"));
        saleStart.setCellValueFactory(new PropertyValueFactory<Auction, Date>("startOfPeriod"));
        saleEnd.setCellValueFactory(new PropertyValueFactory<Auction, Date>("endOfPeriod"));
        max.setCellValueFactory(new PropertyValueFactory<Auction, Number>("money"));
        product.setCellValueFactory(new PropertyValueFactory<Auction, String>("Product"));


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



    public void viewSale(MouseEvent mouseEvent) throws IOException {
        if (sales.getSelectionModel().getSelectedItem() != null) {
            Auction auction = sales.getSelectionModel().getSelectedItem();
            LoginWindow loginWindow = new LoginWindow();
            loginWindow.label.setVisible(true);
            loginWindow.label.setLayoutX(500);
            loginWindow.label.setLayoutY(250);
            loginWindow.label.setText(String.valueOf(Product.getProductById(auction.getProduct())) + "  : Is based price");
            loginWindow.setAuction(auction);
            loginWindow.isSaleOrNot = true;
            loginWindow.setIfAllIsSale(Customer.getCustomerWithUsername(auction.getCustomer()), Product.getProductById(auction.getProduct()));
            try {
                loginWindow.start(Main.primStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
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
        Parent curRoot = FXMLLoader.load(Objects.requireNonNull(SalesFx.class.getClassLoader().getResource("salesFx.fxml")));
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
    private static void removeAuction() throws IOException {
        for (Auction allAuction : Auction.getAllAuctions()) {
            LocalDate localDate = LocalDate.now();
            if(allAuction.getEndOfPeriod().isBefore(localDate)){
                finishingAuction(allAuction);
                Auction.getAllAuctions().remove(allAuction);
            }
        }
    }

    private static void finishingAuction(Auction auction) throws IOException {
        if(auction.getCustomer() != null) {
            if(auction.getMoney() != 0) {
                  String uniqueID = UUID.randomUUID().toString();
                  BuyLog buyLog = new BuyLog(uniqueID);
                  buyLog.setCustomer(auction.getCustomer());
                  buyLog.addProductToBuyLog(auction.getProduct(), 1);
                Customer.getCustomerWithUsername(auction.getCustomer()).reduceCredit(auction.getMoney());
                Seller.getSellerWithUsername(Product.getProductById(auction.getProduct()).getSeller()).increaseCredit(auction.getMoney());
            }
        }
    }


}