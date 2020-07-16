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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.accounts.Customer;
import model.accounts.Manager;
import model.accounts.Seller;
import model.log.BuyLog;
import model.log.BuyLogShoo;
import model.productRelated.Product;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.Objects;

public class ViewBuyLogFx {
    private static String buyLogId;

    public static Parent getPriRoot() {
        return priRoot;
    }

    public static void setPriRoot(Parent priRoot) {
        ViewBuyLogFx.priRoot = priRoot;
    }

    public static void setCurBuyLog(BuyLog curBuyLog) {
        ViewBuyLogFx.CurBuyLog = curBuyLog;
    }

    @FXML
    private TableView<BuyLogShoo> buyLogTable = new TableView<>();
    @FXML
    private Label buyLogIdLabel = new Label();
    @FXML
    private TableColumn<BuyLogShoo, String> productName = new TableColumn<>("productName");

    @FXML
    private TableColumn<BuyLogShoo, String> numberOfPro = new TableColumn<>("numberOfProduct");

    @FXML
    private TableColumn<BuyLogShoo, String> LogId = new TableColumn<>("logId");

    @FXML
    private TableColumn<BuyLogShoo, Double> buyLogPrice = new TableColumn<>("price");

    private TableColumn<BuyLogShoo, ImageView> proImage = new TableColumn<>("imageView");

    private TableColumn<BuyLogShoo, Double> hole = new TableColumn<>("Hole Price");

    @FXML
    private Label buyLogDate;
    @FXML
    private Label totalPriceLabel;
    @FXML
    private static Parent root;
    private static Parent priRoot;
    // public CustomerMenu ;
    private static BuyLog CurBuyLog;
    private static Product product;
    private static boolean first = true;
    public ObservableList<BuyLogShoo> data = FXCollections.observableArrayList();

    public static BuyLog getCurBuyLog() {
        return CurBuyLog;
    }



    public void initializeObserverList() throws FileNotFoundException {
        listIni();
        for (BuyLogShoo buyLogShow : BuyLogShoo.list) {
            data.clear();
            if (!data.contains(buyLogShow)) {
                data.add(buyLogShow);
            }
        }
    }

    public static void listIni() throws FileNotFoundException {
        for (Product product : CurBuyLog.getChosenProduct().keySet()) {
            BuyLogShoo buyLogShoo = new BuyLogShoo();
            buyLogShoo.logId = CurBuyLog.getLogId();
            buyLogShoo.productName = product.getId();
            buyLogShoo.numberOfProduct = CurBuyLog.getChosenProduct().get(product);
            buyLogShoo.price = product.getPrice();
            buyLogShoo.hole = buyLogShoo.price * buyLogShoo.numberOfProduct;
            File file = new File(product.getProductImage());
            Image image = new Image(new FileInputStream(file));
            buyLogShoo.imageView.setImage(image);
            buyLogShoo.imageView.setFitWidth(200);
            buyLogShoo.imageView.setFitHeight(200);
        }
    }

    @FXML
    public void initialize() throws IOException {
        buyLogDate.setText(String.valueOf(CurBuyLog.getLocalDateTimeForLog()));
        productName.setCellValueFactory(new PropertyValueFactory<BuyLogShoo, String>("productName"));
        numberOfPro.setCellValueFactory(new PropertyValueFactory<BuyLogShoo, String>("numberOfProduct"));
        LogId.setCellValueFactory(new PropertyValueFactory<BuyLogShoo, String>("logId"));
        buyLogPrice.setCellValueFactory(new PropertyValueFactory<BuyLogShoo, Double>("price"));
        hole.setCellValueFactory(new PropertyValueFactory<BuyLogShoo, Double>("hole"));
        proImage.setCellValueFactory(new PropertyValueFactory<BuyLogShoo, ImageView>("imageView"));

        buyLogTable.setEditable(true);
        buyLogTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        buyLogTable.getSelectionModel().setCellSelectionEnabled(true);
        initializeObserverList();
        if(first) {
            buyLogTable.getColumns().addAll(productName, numberOfPro, LogId, buyLogPrice, hole, proImage);
        }
        buyLogTable.setItems(data);
        first = false;
    }

    public void showTotalPriceBuyLog(MouseEvent MouseClick) throws IOException {
        if (CurBuyLog instanceof BuyLog) {
            totalPriceLabel.setText(String.valueOf(CurBuyLog.getHolePrice()));
            initialize();
        }

    }

    public void userMenu(ActionEvent actionEvent) throws IOException {
        Parent curRoot = FXMLLoader.load(Objects.requireNonNull(ViewBuyLogFx.class.getClassLoader().getResource("viewBuyLog.fxml")));
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

    public void viewProductFromBuyLog(MouseEvent mouseEvent) throws IOException {
        BuyLogShoo a = buyLogTable.getSelectionModel().getSelectedItem();
        product = Product.getProductById(a.productName);
        if (product != null) {
            Parent curRoot = FXMLLoader.load(Objects.requireNonNull(BuyLogFx.class.getClassLoader().getResource("buyLogFx.fxml")));
            ProductMenuFX.setProductInPage(product);
            ProductMenuFX.setPriRoot(curRoot);
            root = FXMLLoader.load(Objects.requireNonNull(ProductMenuFX.class.getClassLoader().getResource("productMenu.fxml")));
            goToPage();
        }
    }


    private static void goToPage() {
        Scene pageTwoScene = new Scene(root);
        //Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        Main.primStage.setScene(pageTwoScene);
        Main.primStage.show();
    }

    public void handleScore(ActionEvent actionEvent) {
    }

//        if (buyLog.getSelectionModel().getSelectedItem() != null) {
//            Product selectedItem = buyLog.getSelectionModel().getSelectedItem();
////            ProductMenuFX.setCurProduct(selectedItem);
//            ProductMenuFX.productInPage = selectedItem;
//            root = FXMLLoader.load(Objects.requireNonNull(ProductMenuFX.class.getClassLoader().getResource("productMenuFx.fxml")));
//            ;
//            goToPage();
//        }


        /*  public static void initializeObserverList() {
        data.clear();
        data.addAll(ProductMenu.getBuyLog());
    }

    @FXML
    public void initialize() throws IOException {

        buyLogProduct.setCellValueFactory(new PropertyValueFactory<DiscountCode, String>("discountId"));
        buyLogProductNumber.setCellValueFactory(new PropertyValueFactory<DiscountCode, Number>("discountAmount"));
        buyLogDate.setCellValueFactory(new PropertyValueFactory<DiscountCode, String>("startOfDiscountPeriod"));
        buyLogReduceAmount.setCellValueFactory(new PropertyValueFactory<DiscountCode,String>("endOfDiscountPeriod"));
        buyLogRecievedAmount.setCellValueFactory(new PropertyValueFactory<DiscountCode,Number>("maxDiscountAmount"));


        initializeObserverList();
        buyLog.getColumns().addAll(buyLogProduct,buyLogProductNumber,buyLogDate,buyLogReduceAmount,buyLogRecievedAmount);
        buyLog.setEditable(true);
       // buyLog.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
      //  buyLog.getSelectionModel().setCellSelectionEnabled(true);
        buyLog.setItems(data);
    }


   */
}