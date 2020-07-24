package client.view.gui;


//import controller.ProductMenu;
import client.Main;
import server.menus.LoginMenu;
import javafx.animation.*;
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
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.accounts.Customer;
import model.accounts.Manager;
import model.accounts.Seller;
import model.firms.Firm;
import model.productRelated.Category;
import model.productRelated.Product;
import model.productRelated.ProductInMenusShow;
import model.productRelated.ProductStatus;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Objects;


public class ProductsMenuFX {

//    public static Timeline timeline = new Timeline();

    public static int mtime;
    public static boolean trueFalse = true;
    @FXML
    public TableView<ProductInMenusShow> tableView = new TableView<>();
    @FXML
    public TableColumn<ProductInMenusShow, String> firstColumn = new TableColumn<>("id");

    @FXML
    public TableColumn<ProductInMenusShow, Integer> twelve = new TableColumn<>("number of views");

    @FXML
    public TableColumn<ProductInMenusShow, ImageView> productImageViewTableColumn = new TableColumn<>("productImage");

    @FXML
    public TableColumn<ProductInMenusShow, String> secondColumn = new TableColumn<>("name");
    @FXML
    public TableColumn<ProductInMenusShow, Double> forthColumn = new TableColumn<>("price");
    @FXML
    public TableColumn<ProductInMenusShow, String> fifthColumn = new TableColumn<>("seller");

    @FXML
    public TableColumn<ProductInMenusShow, String> sixthColumn = new TableColumn<>("Specifications");

    public TableColumn<ProductInMenusShow, Category> seventh = new TableColumn<>("category");

    public TableColumn<ProductInMenusShow, String> eleventh = new TableColumn<>("firm");

    public TableColumn<ProductInMenusShow, Label> isSaleOrN = new TableColumn<>("status");

    public TableColumn<ProductInMenusShow, String> tenth = new TableColumn<>("numberOfProduct");

    public TableColumn<ProductInMenusShow, Double> ninth = new TableColumn<>("score");

    @FXML
    public static ObservableList<ProductInMenusShow> data = FXCollections.observableArrayList();


    public static ImageView productPic = new ImageView();

    public static FilteredList<ProductInMenusShow> filteredList = new FilteredList<>(data, b -> true);


    @FXML
    public TableColumn<Category, String> catName = new TableColumn<>("name");

    @FXML
    public TableColumn<Category, ArrayList<String>> traits = new TableColumn<>("traits");

    public Label move;
    public CheckBox numberOfViewSortCheck = new CheckBox();
    public CheckBox scoreSortCheck = new CheckBox();
    public CheckBox priceSort = new CheckBox();
    public TextField searchCategoryTraitsField;
    public Pagination pagination;
    public Label errorNumberFormat;
    boolean first = true;

    // public  Image isFinish = new Image("images/productIsFinish.png");
    @FXML
    private void move() {
        TranslateTransition transition = new TranslateTransition();
        transition.setNode(move);
        transition.setDuration(Duration.seconds(1.5));
        transition.setFromX(-1000);
        transition.setToX(1000);
        transition.setAutoReverse(true);
        transition.setCycleCount(Timeline.INDEFINITE);
        transition.play();
    }


    private static Parent priRoot;
    private static Parent root;

    public static Scene prevScene;
    public static Stage thisStage;
    public TextField searchField;
    public AnchorPane topSplitPane;
    public TableView categoriesListView = new TableView();
    public CheckBox filterAscending;
    public CheckBox filterIsAvailable;
    public AnchorPane FilterCategoryPane;
    public ObservableList<Category> dataCat = FXCollections.observableArrayList();
    public ArrayList<CheckBox> filterCatCheck = new ArrayList<>();
    public SortedList<ProductInMenusShow> sortedList = new SortedList<>(filteredList);

    public AnchorPane companyNamePaneFilter;
    public TextField maxPriceTextField;
    public TextField minPriceTextField;
    public AnchorPane SellerNameFilterPane;
    public AnchorPane ProductNameFilterPane;
    public AnchorPane TraitFilterPane;
    public
    FilteredList<Category> filteredListCat = new FilteredList<>(dataCat, b -> true);
    public SortedList<Category> sortedList1 = new SortedList<>(filteredListCat);

    public static void setPriRoot(Parent priRoot) {
        ProductsMenuFX.priRoot = priRoot;
    }

    public void initializeObserverList() throws FileNotFoundException {
        ProductInMenusShow.list.clear();
        listIni();
        data.clear();
        System.out.println(data.size());
        sortedList1.clear();
        sortedList.clear();
        filteredList.clear();
        filteredListCat.clear();
        for (ProductInMenusShow show : ProductInMenusShow.list) {
//            data.clear();
            if (!data.contains(show)) {
                data.add(show);
            }
        }
    }

    public static void listIni() throws FileNotFoundException {

        for (Product product : Product.getProductList()) {
            if (product.getProductStatus().equals(ProductStatus.CONFIRMED)){
                if (product.getProductName() != null && product.getProductImage() != null && product.getFirm() != null){
                    if (!product.isInAuction()){
                        ProductInMenusShow show = new ProductInMenusShow(product.getId());
                        show.name = product.getProductName();
                        show.additionalDetail = product.getAdditionalDetail();
                        show.category = product.getCategory().getName();
                        show.id = product.getId();
                        show.price = product.getPrice();
                        show.comment = product.getComment();
                        show.seller = product.getSeller();
                        File file = new File(product.getProductImage());
                        Image image = new Image(new FileInputStream(file));
                        show.productImage = new ImageView();
                        show.productImage.setFitWidth(100);
                        show.productImage.setFitHeight(100);
                        show.productImage.setImage(image);
                        show.numberOfProduct = product.getNumberOfProducts();
                        final SoftReference<Image> softRef = new SoftReference<Image>(show.productImage.getImage());

                        if (product.getInSale()){
                            image = new Image("images/PngItem_147577.png");
                            show.productImage.setImage(image);
                            show.inSaleOrFinishLabel.setVisible(true);
                            show.inSaleOrFinishLabel.setTextFill(Color.FIREBRICK);
                            show.inSaleOrFinishLabel.setText("IN SALE");
                        }

                        //dark ya light mikone
//            ColorAdjust colorAdjust = new ColorAdjust();
//            colorAdjust.setBrightness(-0.5);

                        //color filter
                        Lighting lighting = new Lighting();
                        lighting.setDiffuseConstant(1.0);
                        lighting.setSpecularConstant(0.0);
                        lighting.setSpecularExponent(0.0);
                        lighting.setSurfaceScale(0.0);
                        lighting.setLight(new Light.Distant(45, 45, Color.RED));
                        if (product.getNumberOfProducts() == 0) {
                            image = new Image("images/productIsFinish.png");
                            show.productImage.setImage(image);
                            show.productImage.setEffect(lighting);
                            show.inSaleOrFinishLabel.setVisible(true);
                            show.inSaleOrFinishLabel.setTextFill(Color.FIREBRICK);
                            show.inSaleOrFinishLabel.setText("FINISHED");
                        }
//            if (product.getNumberOfProducts() == 0){
                        //change hole image
//                ImageView finishImage = new ImageView(new Image("images/productIsFinish.png"));
//                finishImage.setFitWidth(100);
//                finishImage.setFitHeight(100);
//                finishImage.setLayoutY(show.productImage.getLayoutY());
//                finishImage.setLayoutX(show.productImage.getLayoutX());
//                finishImage.setEffect();

                        //
//                show.productImage.setEffect(lighting);
                        //           }
                        if (product.getInSale()){
                            show.inSaleOrFinishLabel.setText("IS IN SALE");
                            show.inSaleOrFinishLabel.setVisible(true);
                        }
                        show.firm = Seller.getAccountWithUsername(product.getSeller()).getFirm().getName();
                        show.numberOfProduct = product.getNumberOfProducts();
                    }
                }

            }

        }
    }


    @FXML
    public void initialize() {
//        String path = "src/main/java/client.view/music/background.mp3";
//        Media media = new Media(new File(path).toURI().toString());
//        MediaPlayer mediaPlayer = new MediaPlayer(media);
//        mediaPlayer.setAutoPlay(true);
        move();
        dataInFilterCheck();
        firstColumn.setCellValueFactory(new PropertyValueFactory<ProductInMenusShow, String>("id"));
        secondColumn.setCellValueFactory(new PropertyValueFactory<ProductInMenusShow, String>("name"));
        productImageViewTableColumn.setCellValueFactory(new PropertyValueFactory<ProductInMenusShow, ImageView>("productImage"));
        forthColumn.setCellValueFactory(new PropertyValueFactory<ProductInMenusShow, Double>("price"));
        fifthColumn.setCellValueFactory(new PropertyValueFactory<ProductInMenusShow, String>("seller"));
        sixthColumn.setCellValueFactory(new PropertyValueFactory<ProductInMenusShow, String>("additionalDetail"));
        seventh.setCellValueFactory(new PropertyValueFactory<ProductInMenusShow, Category>("category"));
        tenth.setCellValueFactory(new PropertyValueFactory<ProductInMenusShow, String>("numberOfProduct"));
        ninth.setCellValueFactory(new PropertyValueFactory<ProductInMenusShow, Double>("score"));
        isSaleOrN.setCellValueFactory(new PropertyValueFactory<ProductInMenusShow, Label>("inSaleOrFinishLabel"));
        eleventh.setCellValueFactory(new PropertyValueFactory<ProductInMenusShow, String>("firm"));
        twelve.setCellValueFactory(new PropertyValueFactory<ProductInMenusShow, Integer>("numberOfViews"));

        try {
            initializeObserverList();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(first){
            tableView.getColumns().addAll(firstColumn, secondColumn, productImageViewTableColumn, forthColumn, fifthColumn, sixthColumn, seventh, eleventh, tenth,ninth,twelve,isSaleOrN);
        }first = false;
//        tableView.setItems(data);


        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(product -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (product.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (product.getCategory().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else return false;
            });

        });

        minPriceTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(product -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                try{
                    int priceFilter = Integer.parseInt(newValue);
                    if (product.getPrice() > priceFilter) {
                        return true;
                    }
                }catch (NumberFormatException e){
                    errorNumberFormat.setVisible(true);
                }
                return false;
            });

        });

        maxPriceTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(product -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                try{
                    int priceFilter = Integer.parseInt(newValue);
                    if (product.getPrice() < priceFilter) {
                        return true;
                    }
                }catch (NumberFormatException e){
                    errorNumberFormat.setVisible(true);
                }
                return false;
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
                        if (checkBox.isSelected() && checkBox.getText().contains("number")){
                            sortedList.comparatorProperty().bind(tableView.comparatorProperty());
                            tableView.setItems(sortedList);
                            tableView.getSortOrder().addAll(tenth);
                            return true;
                        }
                        if (checkBox.isSelected() && checkBox.getText().contains("score")){
                            sortedList.comparatorProperty().bind(tableView.comparatorProperty());
                            tableView.setItems(sortedList);
                            tableView.getSortOrder().addAll(ninth);
                            return true;
                        }
                        if (checkBox.isSelected() && checkBox.getText().contains("price")){
                            sortedList.comparatorProperty().bind(tableView.comparatorProperty());
                            tableView.setItems(sortedList);
                            tableView.getSortOrder().addAll(forthColumn);
                            return true;
                        }
                        return false;
                    });
                }
            });

//            checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
//                @Override
//                public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
//                    filteredListCat.setPredicate(obj -> {
//                        String[] s = checkBox.getText().split(" ");
//                        if (!checkBox.isSelected()) {
//                            return true;
//                        }
//                        if (checkBox.isSelected()){
//                            if (obj.getTraits().contains(s[1])){
//                                return true;
//                            }
//                        }
//                        return false;
//                    });
//                }
//            });
        }
        tableView.setEditable(true);
        catName.setCellValueFactory(new PropertyValueFactory<Category, String>("name"));
        traits.setCellValueFactory(new PropertyValueFactory<Category, ArrayList<String>>("traits"));
        dataInListView();
        categoriesListView.getColumns().addAll(catName, traits);
        categoriesListView.setItems(dataCat);
        sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tableView.getSelectionModel().setCellSelectionEnabled(true);
        tableView.setItems(sortedList);



        sortedList1 = new SortedList<>(filteredListCat);
        sortedList1.comparatorProperty().bind(categoriesListView.comparatorProperty());
        categoriesListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        categoriesListView.getSelectionModel().setCellSelectionEnabled(true);
        categoriesListView.setItems(sortedList1);


    }




    public static void gotoProductPage(Product product) throws IOException {
        Parent curRoot = FXMLLoader.load(Objects.requireNonNull(ProductsMenuFX.class.getClassLoader().getResource("productsMenu.fxml")));
        ProductMenuFX.setPriRoot(curRoot);
        AnchorPane root = FXMLLoader.load(Objects.requireNonNull(ProductMenuFX.class.getClassLoader().getResource("productMenu.fxml")));
        prevScene = new Scene(root);
        ProductMenuFX.prevScene = prevScene;
        thisStage = new Stage();
        thisStage.setScene(prevScene);
        ProductMenuFX.productInPage = product;
        thisStage.show();
    }

    public void clickedColumn(MouseEvent mouseEvent) throws IOException {
        TablePosition tablePosition = tableView.getSelectionModel().getSelectedCells().get(0);
        int row = tablePosition.getRow();
        ProductInMenusShow item = tableView.getItems().get(row);
        TableColumn tableColumn = tablePosition.getTableColumn();

        try {

            ImageView im = (ImageView) tableColumn.getCellObservableValue(item).getValue();
            String id = ProductInMenusShow.getIdWithImage(im);
            ProductMenuFX.productInPage = Product.getProductById(id);
            gotoProductPage(ProductMenuFX.productInPage);

        } catch (NullPointerException e) {
            System.out.println("you cant press here");
        }
    }

    public void dataInListView() {
        for (Category category : Category.getAllCategories()) {
            if (!dataCat.contains(category)) {
                dataCat.add(category);
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
            FilterCategoryPane.getChildren().add(checkBox);
        }

        for (Firm firm : Firm.getAllFirms()) {
            int n = 30;
            CheckBox checkBox = new CheckBox("firm " + firm.getName());
            checkBox.setLayoutY((n) * (Firm.getAllFirms().indexOf(firm) + 1));
            checkBox.setLayoutX(10 + Firm.getAllFirms().indexOf(firm));
            filterCatCheck.add(checkBox);
            companyNamePaneFilter.getChildren().add(checkBox);
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

        for (Category category : Category.getAllCategories()) {
            for (String trait : category.getTraits()) {
                int n = 30;
                CheckBox checkBox = new CheckBox("trait " + trait);
                checkBox.setLayoutY((n) * (category.getTraits().indexOf(trait) + 1));
                checkBox.setLayoutX(10 + category.getTraits().indexOf(trait));
                filterCatCheck.add(checkBox);
                TraitFilterPane.getChildren().add(checkBox);
            }
        }

        filterCatCheck.add(filterIsAvailable);
        filterCatCheck.add(filterAscending);
        filterCatCheck.add(numberOfViewSortCheck);
        filterCatCheck.add(priceSort);
        filterCatCheck.add(scoreSortCheck);
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

    private static void goToPage() {
        Scene pageTwoScene = new Scene(root);
        //Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        Main.primStage.setScene(pageTwoScene);
        Main.primStage.show();
    }

    public void login(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(LoginFx.class.getClassLoader().getResource("loginFx.fxml")));
        goToPage();
    }

    public void userMenu(ActionEvent actionEvent) throws IOException {
        Parent curRoot = FXMLLoader.load(Objects.requireNonNull(ProductsMenuFX.class.getClassLoader().getResource("productsMenu.fxml")));
        if (LoginMenu.getLoginAccount() instanceof Manager) {
            ManagerMenuFx.setPriRoot(curRoot);
            root = FXMLLoader.load(Objects.requireNonNull(ManagerMenuFx.class.getClassLoader().getResource("managerMenuFx.fxml")));
        } else if (LoginMenu.getLoginAccount() instanceof Seller) {
            SellerMenuFx.setPriRoot(curRoot);
            root = FXMLLoader.load(Objects.requireNonNull(SellerMenuFx.class.getClassLoader().getResource("sellerMenuFx.fxml")));
        } else if (LoginMenu.getLoginAccount() instanceof Customer) {
            CustomerMenuFx.setPriRoot(curRoot);
            root = FXMLLoader.load(Objects.requireNonNull(CustomerMenuFx.class.getClassLoader().getResource("customerMenuFx.fxml")));
        }

        goToPage();
    }
}