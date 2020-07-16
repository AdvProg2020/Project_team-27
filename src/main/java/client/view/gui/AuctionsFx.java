package client.view.gui;

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
import model.accounts.Customer;
import model.accounts.Manager;
import model.accounts.Seller;
import model.off.Auction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class AuctionsFx {

    @FXML
    private TableColumn<Auction, String> product;
    @FXML
    private TableColumn<Auction, Number> max;
    @FXML
    private TableColumn<Auction, String> saleId;
    @FXML
    private TableColumn<Auction, Date> saleEnd;
    @FXML
    private TableColumn<Auction, Date> saleStart;
    @FXML
    private TableView<Auction> sales;
    @FXML
    private Label salesMs;


    public static ObservableList list = FXCollections.observableArrayList();
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

            list.addAll(allAuctions);

    }

    private void makeTree() {
//        checkIfTimeEnds();
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
