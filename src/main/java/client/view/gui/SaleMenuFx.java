package client.view.gui;

import client.Main;
import server.menus.LoginMenu;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.accounts.Customer;
import model.accounts.Manager;
import model.accounts.Seller;
import model.firms.Firm;
import model.off.Sale;
import model.productRelated.Category;
import model.productRelated.Product;
import model.productRelated.ProductInSaleShow;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class SaleMenuFx {
    public TextField searchProInSale;
    public TableView ProductsInOffSearch = new TableView();

    @FXML
    public TableColumn<ProductInSaleShow, String> firstColumn = new TableColumn<>("id");

    @FXML
    public TableColumn<ProductInSaleShow, ImageView> productImageViewTableColumn = new TableColumn<>("productImage");

    @FXML
    public TableColumn<ProductInSaleShow, String> secondColumn = new TableColumn<>("name");
    @FXML
    public TableColumn<ProductInSaleShow, String> forthColumn = new TableColumn<>("price");
    @FXML
    public TableColumn<ProductInSaleShow, String> fifthColumn = new TableColumn<>("seller");

    @FXML
    public TableColumn<ProductInSaleShow, String> sixthColumn = new TableColumn<>("Specifications");

    public TableColumn<ProductInSaleShow , Category> seventh = new TableColumn<>("category");

    public TableColumn<ProductInSaleShow , String> eight = new TableColumn<>("saleAmount");

    public TableColumn<ProductInSaleShow , Date> ninth = new TableColumn<>("startOfSalePeriod");

    public TableColumn<ProductInSaleShow , Date> tenth = new TableColumn<>("endOfSalePeriod");

    public ArrayList<CheckBox> filterCatCheck = new ArrayList<>();

    @FXML
    public static ObservableList<ProductInSaleShow> data = FXCollections.observableArrayList();

    public static FilteredList<ProductInSaleShow> filteredList = new FilteredList<>(data, b -> true);

    public static Scene prevScene;
    public static Stage thisStage;
    public AnchorPane CategoryFilterPane;
    public AnchorPane CompnyNameFilterPane;
    public AnchorPane SellerNameFilterPane;
    public CheckBox IsAvailableChoiceBox;
    public AnchorPane ProductNameFilterPane;
    private static  Parent priRoot;
    private static  Parent root;

    public static Parent getPriRoot() {
        return priRoot;
    }

    public static void setPriRoot(Parent priRoot) {
        SaleMenuFx.priRoot = priRoot;
    }

    public static void initializeObserverList() throws FileNotFoundException {
        listIni();
        for (ProductInSaleShow saleShow : ProductInSaleShow.list) {
            if (!data.contains(saleShow)){
                data.add(saleShow);
            }
        }
    }


    public static void listIni() throws FileNotFoundException {

        for (Product product : Sale.allProSale) {
            ProductInSaleShow show = new ProductInSaleShow(product.getId());
            show.name = product.getProductName();
            show.additionalDetail = product.getAdditionalDetail();
            show.category = product.getCategory().getName();
            show.id = product.getId();
            show.price = product.getPrice();
           // show.comment = product.getComment();
            show.seller = product.getSeller();
            show.firm = product.getFirm().getName();
            File file = new File(product.getProductImage());
            Image image = new Image(new FileInputStream(file));
            show.productImage = new ImageView();
            show.productImage.setFitWidth(100);
            show.productImage.setFitHeight(100);
            show.productImage.setImage(image);
            for (Seller seller : Seller.getAllSellers()) {
                for (Sale sale : seller.getAllSales()) {
                    if (sale.getAllSaleProducts().contains(product)){
                        show.saleAmount = sale.getSaleAmount();
                        show.startOfSalePeriod=sale.getStartOfSalePeriod();
                        show.endOfSalePeriod = sale.getEndOfSalePeriod();
                    }
                }
            }
        }

    }


    public void dataInFilterCheck() {
        for (Category category : Category.getAllCategories()) {
            int n = 30;
            CheckBox checkBox = new CheckBox("category " + category.getName());
            checkBox.setLayoutY((n) * (Category.getAllCategories().indexOf(category) + 1));
            checkBox.setLayoutX(10 + Category.getAllCategories().indexOf(category));
            filterCatCheck.add(checkBox);
            CategoryFilterPane.getChildren().add(checkBox);
        }

        for (Firm firm : Firm.getAllFirms()) {
            int n = 30;
            CheckBox checkBox = new CheckBox("firm " + firm.getName());
            checkBox.setLayoutY((n) * (Firm.getAllFirms().indexOf(firm) + 1));
            checkBox.setLayoutX(10 + Firm.getAllFirms().indexOf(firm));
            filterCatCheck.add(checkBox);
            CompnyNameFilterPane.getChildren().add(checkBox);
        }

        for (Seller seller : Seller.getAllSellers()) {
            int n = 30;
            CheckBox checkBox = new CheckBox("seller " + seller.getUsername());
            checkBox.setLayoutY((n) * (Seller.getAllSellers().indexOf(seller) + 1));
            checkBox.setLayoutX(10 + Seller.getAllSellers().indexOf(seller));
            filterCatCheck.add(checkBox);
            SellerNameFilterPane.getChildren().add(checkBox);
        }

        for (Product product : Product.getAllProduct()) {
            int n = 30;
            CheckBox checkBox = new CheckBox("product " + product.getProductName());
            checkBox.setLayoutY((n) * (Product.getAllProduct().indexOf(product) + 1));
            checkBox.setLayoutX(10 + Product.getAllProduct().indexOf(product));
            filterCatCheck.add(checkBox);
            ProductNameFilterPane.getChildren().add(checkBox);
        }


        filterCatCheck.add(IsAvailableChoiceBox);

    }

    @FXML
    public void initialize() throws IOException {
        dataInFilterCheck();
        firstColumn.setCellValueFactory(new PropertyValueFactory<ProductInSaleShow, String>("id"));
        secondColumn.setCellValueFactory(new PropertyValueFactory<ProductInSaleShow, String>("name"));
        productImageViewTableColumn.setCellValueFactory(new PropertyValueFactory<ProductInSaleShow, ImageView>("productImage"));
        forthColumn.setCellValueFactory(new PropertyValueFactory<ProductInSaleShow, String>("price"));
        fifthColumn.setCellValueFactory(new PropertyValueFactory<ProductInSaleShow, String>("seller"));
        sixthColumn.setCellValueFactory(new PropertyValueFactory<ProductInSaleShow, String>("additionalDetail"));
        seventh.setCellValueFactory(new PropertyValueFactory<ProductInSaleShow,Category>("category"));
        eight.setCellValueFactory(new PropertyValueFactory<ProductInSaleShow,String >("saleAmount"));
        ninth.setCellValueFactory(new PropertyValueFactory<ProductInSaleShow, Date>("startOfSalePeriod"));
        tenth.setCellValueFactory(new PropertyValueFactory<ProductInSaleShow,Date>("endOfSalePeriod"));

        initializeObserverList();
        ProductsInOffSearch.getColumns().addAll(firstColumn, secondColumn, productImageViewTableColumn, forthColumn, fifthColumn, sixthColumn,seventh,eight,ninth,tenth);
        ProductsInOffSearch.setItems(data);


        searchProInSale.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(product -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (product.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                else if (product.getCategory().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                else return false;
            });

        });

        for (CheckBox checkBox : filterCatCheck) {
            checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {

                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    filteredList.setPredicate(obj -> {
                        if (!checkBox.isSelected()) {
                            return true;
                        }
                        if (checkBox.isSelected() && checkBox.getText().contains("category")) {
                            String[] s = checkBox.getText().split(" ");
                            if (s[1].equals(obj.getCategory())) {
                                return true;
                            }
                        }
                        if (checkBox.isSelected() && checkBox.getText().equals("isAvailable") && obj.getNumberOfProduct() != 0){
                            if (!filteredList.contains(obj)){
                                return true;
                            }
                        }
                        if (checkBox.isSelected() && checkBox.getText().contains("firm")) {
                            String[] s = checkBox.getText().split(" ");
                            if (s[1].equals(obj.getFirm())) {
                                return true;
                            }
                        }
                        if (checkBox.isSelected() && checkBox.getText().contains("seller")){
                            String[] s = checkBox.getText().split(" ");
                            if (s[1].equals(obj.getSeller())){
                                return true;
                            }
                        }
                        if (checkBox.isSelected() && checkBox.getText().contains("product")){
                            String[] s = checkBox.getText().split(" ");
                            if (s[1].equals(obj.name)){
                                return true;
                            }
                        }
                        return false;
                    });
                }
            });
        }


        ProductsInOffSearch.setEditable(true);



        SortedList<ProductInSaleShow> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(ProductsInOffSearch.comparatorProperty());
        ProductsInOffSearch.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        ProductsInOffSearch.getSelectionModel().setCellSelectionEnabled(true);
        ProductsInOffSearch.setItems(sortedList);

    }

    public void clickedColumn(MouseEvent mouseEvent) throws IOException {
        TablePosition tablePosition = (TablePosition) ProductsInOffSearch.getSelectionModel().getSelectedCells().get(0);
        int row = tablePosition.getRow();
        ProductInSaleShow item = (ProductInSaleShow) ProductsInOffSearch.getItems().get(row);
        TableColumn tableColumn = tablePosition.getTableColumn();

        try {

            ImageView im = (ImageView) tableColumn.getCellObservableValue(item).getValue();
            String id = ProductInSaleShow.getIdWithImage(im);
            ProductMenuFX.productInPage = Product.getProductById(id);
            gotoProductPage(ProductMenuFX.productInPage);

        } catch (NullPointerException e) {
            System.out.println("you cant press here");
        }
    }

    public static void gotoProductPage(Product product) throws IOException {
        AnchorPane root = FXMLLoader.load(Objects.requireNonNull(ProductMenuFX.class.getClassLoader().getResource("productMenu.fxml")));
        prevScene = new Scene(root);
        thisStage = new Stage();
        thisStage.setScene(prevScene);
        ProductMenuFX.productInPage = product;
        thisStage.show();
    }


    public void login(ActionEvent actionEvent) throws IOException {
        Parent curRoot = FXMLLoader.load(Objects.requireNonNull(SaleMenuFx.class.getClassLoader().getResource("saleMenuFx.fxml")));
        LoginFx.setPriRoot(curRoot);
        AnchorPane root = FXMLLoader.load(Objects.requireNonNull(LoginFx.class.getClassLoader().getResource("loginFx.fxml")));
        Scene pageTwoScene = new Scene(root);
        //Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        Main.primStage.setScene(pageTwoScene);
        Main.primStage.show();
    }

    private static void goToPage() {
        Scene pageTwoScene = new Scene(root);
        //Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        Main.primStage.setScene(pageTwoScene);
        Main.primStage.show();
    }


    public void back(ActionEvent actionEvent) throws IOException {
        if(priRoot == null){
            root = FXMLLoader.load(Objects.requireNonNull(MainMenuFx.class.getClassLoader().getResource("mainMenuFx.fxml")));
        }else {
            root = priRoot;
        }
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

    public void UserMenu(ActionEvent actionEvent) throws IOException {
        Parent curRoot = FXMLLoader.load(Objects.requireNonNull(SaleMenuFx.class.getClassLoader().getResource("saleMenuFx.fxml")));
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
}
