package client.view.gui;

import client.Main;
import server.menus.ProductMenu;
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

public class BuyLogFx {
    private static String buyLogId;

    public static void setBuyLogId(String buyLogId) {
        BuyLogFx.buyLogId = buyLogId;
    }

    public static void setPriRoot(Parent priRoot) {
        BuyLogFx.priRoot = priRoot;
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
    private static BuyLog curbuylog;
    private static Product product;
    private static boolean first = true;
    public ObservableList<BuyLogShoo> data = FXCollections.observableArrayList();

    public static BuyLog getCurbuylog() {
        return curbuylog;
    }

    public static void setCurBuylog(BuyLog curbuylog) {
        BuyLogFx.curbuylog = curbuylog;
    }

    public void initializeObserverList() throws FileNotFoundException {
        listIni();
        if( BuyLogShoo.list.size() ==0){
            data.clear();
        }
        for (BuyLogShoo buyLogShow : BuyLogShoo.list) {
            data.clear();
            if (!data.contains(buyLogShow)) {
                data.add(buyLogShow);
            }
        }
    }

    public static void listIni() throws FileNotFoundException {
        for (Product product : curbuylog.getChosenProduct().keySet()) {
            BuyLogShoo buyLogShoo = new BuyLogShoo();
            buyLogShoo.logId = curbuylog.getLogId();
            buyLogShoo.productName = product.getId();
            buyLogShoo.numberOfProduct = curbuylog.getChosenProduct().get(product);
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
        buyLogDate.setText(String.valueOf(curbuylog.getLocalDateTimeForLog()));
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


    public void clickedColumn(MouseEvent mouseEvent) throws IOException {
       /* TablePosition tablePosition = buyLogTable.getSelectionModel().getSelectedCells().get(0);
        int row = tablePosition.getRow();
        BuyLogShoo item = buyLogTable.getItems().get(row);
        TableColumn tableColumn = tablePosition.getTableColumn();

        try {
            String im = (String) tableColumn.getCellObservableValue(item).getValue();
             product = Product.getProductById(im);

        } catch (NullPointerException e) {
            System.out.println("you cant press here");
        }

        */
    }

    public void increaseAmount(MouseEvent mouseEvent) throws IOException {
        if (buyLogTable.getSelectionModel().getSelectedItem() != null) {
            BuyLogShoo a = buyLogTable.getSelectionModel().getSelectedItem();
            product = Product.getProductById(a.productName);
            if (product != null) {
                ProductMenu.getBuyLog().addProductToBuyLog(product.getId(), 1);
                BuyLogShoo buyLogShoo= BuyLogShoo.getProduct(product.getId());
                BuyLogShoo.list.remove(buyLogShoo);
                initialize();
            }
        }

    }

    public void reduceAmount(MouseEvent MouseEvent) throws IOException {
        if (buyLogTable.getSelectionModel().getSelectedItem() != null) {
            BuyLogShoo a = buyLogTable.getSelectionModel().getSelectedItem();
            product = Product.getProductById(a.productName);
            ProductMenu.getBuyLog().reduceNumberOfProduct(product.getId(), 1);
          //  if(!ProductMenu.getBuyLog().getChosenProduct().containsKey(product) ){
               BuyLogShoo buyLogShoo= BuyLogShoo.getProduct(product.getId());
                BuyLogShoo.list.remove(buyLogShoo);
          //  }
            initialize();
        }

    }

    public void showTotalPriceBuyLog(MouseEvent MouseClick) throws IOException {
        if (curbuylog instanceof BuyLog) {
            totalPriceLabel.setText(String.valueOf(curbuylog.getHolePrice()));
            initialize();
        }

    }

    public void showLogLocalDate() {
        buyLogDate.setText(curbuylog.getLocalDateTimeForLog().toString());
    }

    public void purchase(MouseEvent mouseEvent) throws IOException {
        Parent curRoot = FXMLLoader.load(Objects.requireNonNull(BuyLogFx.class.getClassLoader().getResource("buyLogFx.fxml")));
        if (LoginMenu.isLogin()) {
            PayLogFx.setPreBuyLog(curbuylog);
            PayLogFx.setPriRoot(curRoot);
            root = FXMLLoader.load(Objects.requireNonNull(PayLogFx.class.getClassLoader().getResource("payLogFx.fxml")));
            goToPage();
        } else {
            LoginFx.setPriRoot(curRoot);
            root = FXMLLoader.load(Objects.requireNonNull(LoginFx.class.getClassLoader().getResource("loginFx.fxml")));
            goToPage();
        }

    }


    public void userMenu(ActionEvent actionEvent) throws IOException {
        Parent curRoot = FXMLLoader.load(Objects.requireNonNull(BuyLogFx.class.getClassLoader().getResource("buyLogFx.fxml")));
        if (LoginMenu.getLoginAccount() instanceof Seller) {
            SellerMenuFx.setPriRoot(curRoot);
            root = FXMLLoader.load(Objects.requireNonNull(SellerMenuFx.class.getClassLoader().getResource("sellerMenuFx.fxml")));
        } else if (LoginMenu.getLoginAccount() instanceof Manager) {
            ManagerMenuFx.setPriRoot(curRoot);
            root = FXMLLoader.load(Objects.requireNonNull(ManagerMenuFx.class.getClassLoader().getResource("managerMenuFx.fxml")));
        } else if (LoginMenu.getLoginAccount() instanceof Customer) {
            CustomerMenuFx.setPriRoot(curRoot);
            root = FXMLLoader.load(Objects.requireNonNull(CustomerMenuFx.class.getClassLoader().getResource("customerMenuFx.fxml")));
        }else {
            root = FXMLLoader.load(Objects.requireNonNull(MainMenuFx.class.getClassLoader().getResource("mainMenuFx.fxml")));
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
        first = true;
        Scene pageTwoScene = new Scene(root);
        //Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        Main.primStage.setScene(pageTwoScene);
        Main.primStage.show();
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