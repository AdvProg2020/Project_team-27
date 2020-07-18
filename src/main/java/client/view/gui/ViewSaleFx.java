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
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.media.Media;
//import javafx.scene.media.MediaPlayer;
import model.accounts.Customer;
import model.accounts.Manager;
import model.accounts.Seller;
import model.off.Sale;
import model.productRelated.Product;
import model.request.Request;
import model.request.SaleRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class ViewSaleFx {
    @FXML
    private Label startSaleLabel;
    @FXML
    private TableColumn<Product, String> saleProduct;
    @FXML
    private Label endSaleLabel;
    @FXML
    private Label saleAmountLabel;
    @FXML
    private TableView<Product> saleProducts;
    @FXML
    private Label saleIdLabel;

    private static Sale curSale;
    public static ObservableList list = FXCollections.observableArrayList();
    private static Parent root;
    private static Parent priRoot;
    private static Request request;
    static ArrayList<Product> arrayList = new ArrayList<>();

    public static void setPriRoot(Parent priRoot) {
        ViewSaleFx.priRoot = priRoot;
    }

    public static Sale getCurSale() {
        return curSale;
    }

    public static void setCurSale(Sale curSale) {
        ViewSaleFx.curSale = curSale;
    }

    public static Request getRequest() {
        return request;
    }

    public static void setRequest(Request request) {
        ViewSaleFx.request = request;
    }

    private void makeTree() {

        saleProduct.setCellValueFactory(new PropertyValueFactory<Product, String>("productId"));

        list.clear();
        list.addAll(curSale.getAllSaleProducts());
        saleProducts.setEditable(true);
        saleProducts.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        saleProducts.getSelectionModel().setCellSelectionEnabled(true);

        saleProducts.setItems(list);
    }



    @FXML
    public void initialize() {
        if (request == null) {
            makeTree();
            saleIdLabel.setText(String.valueOf(curSale.getOffId()));
            startSaleLabel.setText(String.valueOf(curSale.getStartOfSalePeriod()));
            endSaleLabel.setText(String.valueOf(curSale.getEndOfSalePeriod()));
            saleAmountLabel.setText(String.valueOf(curSale.getSaleAmount()));
        } else showRequest();

    }

    private void showRequest() {
        if (request instanceof SaleRequest) {
            if(request instanceof  SaleRequest) {
                SaleRequest saleRequest = (SaleRequest) request;
                makeRequestTree();
                saleIdLabel.setText(String.valueOf(saleRequest.getOffId()));
                startSaleLabel.setText(String.valueOf(saleRequest.getStartOfSalePeriod()));
                endSaleLabel.setText(String.valueOf(saleRequest.getEndOfSalePeriod()));
                saleAmountLabel.setText(String.valueOf(saleRequest.getSaleAmount()));
            }
        }
    }

    private void makeRequestTree() {
        if (request instanceof SaleRequest) {
            SaleRequest saleRequest = (SaleRequest) request;
            saleProduct.setCellValueFactory(new PropertyValueFactory<Product, String>("productId"));
            list.clear();
            list.addAll(saleRequest.getAllSaleProducts());
            //  usersList.getColumns().addAll(UserId,userName,userLast,userBirth,userPhoneNo,userEmail);
            saleProducts.setEditable(true);
            saleProducts.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            saleProducts.getSelectionModel().setCellSelectionEnabled(true);

            saleProducts.setItems(list);
        }
    }


    public void userMenu(ActionEvent actionEvent) throws IOException {
//        String path = "src/main/java/client.view/music/drop.mp3";
//        Media media = new Media(new File(path).toURI().toString());
//        MediaPlayer mediaPlayer = new MediaPlayer(media);
//        mediaPlayer.setAutoPlay(true);
        Parent curRoot = FXMLLoader.load(Objects.requireNonNull(ViewSaleFx.class.getClassLoader().getResource("viewSaleFx.fxml")));
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

    private static void goToPage() {
        Scene pageTwoScene = new Scene(root);
        //Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        Main.primStage.setScene(pageTwoScene);
        Main.primStage.show();
    }
}
