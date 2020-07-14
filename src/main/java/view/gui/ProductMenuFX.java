package view.gui;

import controller.menus.CustomerMenu;
import controller.menus.LoginMenu;
import controller.menus.ProductMenu;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.CacheHint;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.AnchorPane;
//import javafx.scene.media.Media;
//import javafx.scene.media.MediaPlayer;
//import javafx.scene.media.MediaView;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.accounts.Customer;
import model.accounts.Manager;
import model.accounts.Seller;
import model.log.BuyLog;
import model.log.SaleLogShow;
import model.off.Sale;
import model.productRelated.*;
import model.request.ProductRequest;
import model.request.Request;
import view.OutputMassageHandler;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.Date;
import java.util.Objects;

public class ProductMenuFX {

    @FXML
    public AnchorPane productPagePane;
    @FXML
    public ImageView productPic;
    public static Scene prevScene;
    public static Stage thisStage = new Stage();
    public static Product productInPage;


    public TextArea commentTextField;
    public TextField titleTextField;
    public Button backButtonAddComment;
    public Button sendCommentButton;
    public AnchorPane addCommentSectionPane = new AnchorPane();
    public Label nullAddCommentError = new Label();
    public TextArea productCategoryDetail;
    public TextArea productDetail;
    public TableView commentTableView = new TableView();

    public ObservableList<ProductInMenusShow> data1 = FXCollections.observableArrayList();

    public Button scoreButton;

    public ImageView scoreImageView = new ImageView();

    public TableView similarProductsTableView = new TableView();
    
    public TableView<ProductInMenusShow> comparisonTableView = new TableView();

    public AnchorPane ComparisonPane;
    public ImageView compareImage = new ImageView();
    public TextArea comparetextField ;
    @FXML
    private Label productNameLabel;
    @FXML
    private Button addToCardButton;
    @FXML
    private Button commentButton;
    @FXML
    private Label didntBuyToScoreOrProductIsFinish;
    @FXML
    public TableColumn<ProComments, String> titleColumn = new TableColumn<>("title");
    @FXML
    public TableColumn<ProComments, String> contentColumn = new TableColumn<>("content");
    @FXML
    public TableColumn<ProductInMenusShow, ImageView> thirdCo = new TableColumn<>("productImage");

    @FXML
    public TableColumn<ProductInMenusShow, String> secondCo = new TableColumn<>("name");


    @FXML
    public TableColumn<ProductInMenusShow, String> forth = new TableColumn<>("id");

    @FXML
    public TableColumn<ProductInMenusShow, Double> firstCo = new TableColumn<>("price");
    @FXML
    public TableColumn<SimilarShow, String> proName = new TableColumn<>("name");

    @FXML
    public TableColumn<SimilarShow, Double> proPrice = new TableColumn<>("price");

    @FXML
    public TableColumn<SimilarShow, ImageView> proImage = new TableColumn<>("productImage");

    @FXML
    public TableColumn<ProComments, String> personWhoCommented = new TableColumn<>("person commented on this");

    @FXML
    public static ObservableList<ProComments> data = FXCollections.observableArrayList();

    @FXML
    public static ObservableList<SimilarShow> similarShows = FXCollections.observableArrayList();


    @FXML
    private Label scoreMs;
    private int score =0;
    private static Parent priRoot;
    private static Parent root;
    private static Request request;
    final DoubleProperty zoomProperty = new SimpleDoubleProperty(200);
    public ScrollPane scrollPane = new ScrollPane();

    public static void setRequest(Request request) {
        ProductMenuFX.request = request;
    }

    public static void setPriRoot(Parent priRoot) {
        ProductMenuFX.priRoot = priRoot;
    }

    public static void setProductInPage(Product productInPage) {
        ProductMenuFX.productInPage = productInPage;
    }

    public void makeUpPage() throws IOException {

        String path = "src/main/java/view/music/background.mp3";
 /*       Media media1 = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer1 = new MediaPlayer(media1);
        mediaPlayer1.setAutoPlay(true);
        productInPage.setNumberOfViews(productInPage.getNumberOfView() + 1);
        for (ProductInMenusShow productInMenusShow : ProductInMenusShow.list) {
            if (productInMenusShow.getId().equals(productInPage.getId())){
                productInMenusShow.setNumberOfViews(productInPage.getNumberOfView());
            }
        }
        if (productInPage.getProductVideoId() != null){
            File fileVideo = new File(productInPage.getProductVideoId());
            Media media = new Media(fileVideo.toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setAutoPlay(true);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            MediaView mediaView = new MediaView(mediaPlayer);
            mediaView.setLayoutY(580);
            mediaView.setLayoutX(180);
            mediaView.setFitWidth(300);
            mediaView.setFitHeight(400);
            mediaView.setVisible(true);
            mediaPlayer.play();
            productPagePane.getChildren().add(mediaView);
        }

  */


        zoomProperty.addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable arg0) {
                productPic.setFitWidth(zoomProperty.get() * 4);
                productPic.setFitHeight(zoomProperty.get() * 3);
            }
        });

        scrollPane.addEventFilter(ScrollEvent.ANY, new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                if (event.getDeltaY() > 0) {
                    zoomProperty.set(zoomProperty.get() * 1.1);
                } else if (event.getDeltaY() < 0) {
                    zoomProperty.set(zoomProperty.get() / 1.1);
                }
            }
        });
        productPic.preserveRatioProperty().set(true);
        scrollPane.setContent(productPic);


//        if (productInPage.getScore().getScore() == 5){
//
//        }
//        else if (productInPage.getScore().getScore() == 4){
//
//        }
//        else if (productInPage.getScore().getScore() == 3){
//
//        }
//        else if (productInPage.getScore().getScore() == 2){
//
//        }
//        else if (productInPage.getScore().getScore() == 1){
//
//        }
/*
        if ( productInPage.getScore() >=0 && productInPage.getScore()<2){
            scoreImageView.setVisible(true);
            scoreImageView.setImage(new Image("icons/1.png"));
        }
        else if (productInPage.getScore() >= 2 && productInPage.getScore()<3){
            scoreImageView.setVisible(true);
            scoreImageView.setImage(new Image("icons/2.png"));
        }
        else if (productInPage.getScore() >= 3 && productInPage.getScore()<4){
            scoreImageView.setVisible(true);
            scoreImageView.setImage(new Image("icons/3.png"));
        }
        else if (productInPage.getScore() >= 4 && productInPage.getScore()<5){
            scoreImageView.setVisible(true);
            scoreImageView.setImage(new Image("icons/4.png"));
        }
        else if (productInPage.getScore() == 5){
            scoreImageView.setVisible(true);
            scoreImageView.setImage(new Image("icons/5.png"));
        }
*/

        if (productInPage.getInSale()){
            for (Sale sale : Sale.getAllSales()) {
                for (Product product : sale.getAllSaleProducts()) {
                    if (product.getId().equals(productInPage.getId())){
                        productInPage.setProductImage("C:\\Users\\ASA\\IdeaProjects\\backUp3\\src\\main\\resources\\images\\PngItem_147577.png");
                        TextArea textArea = new TextArea();
                        textArea.setText("Sale ID : " + productInPage.getSale()+ "\n" +
                                "Sale Amount : " + sale.getSaleAmount());
                        textArea.setEditable(false);
                        textArea.setLayoutX(200);
                        textArea.setLayoutY(100);
                        textArea.setPrefSize(200,300);
                        productPagePane.getChildren().add(textArea);
                    }
                }
            }
        }

        if(request == null) {
            productNameLabel.setText(productInPage.getProductName());
            File file = new File(productInPage.getProductImage());
            Image image = new Image(new FileInputStream(file));
            productPic.setImage(image);
            if (productInPage.getInSale()){
                productPic.setImage(new Image("images/PngItem_147577.png"));
                productInPage.setProductImage("C:\\Users\\ASA\\IdeaProjects\\backUp3\\src\\main\\resources\\images\\");
            }
            if (productInPage.getNumberOfProducts() == 0){
                productPic.setImage(new Image("images/productIsFinish.png"));
                productInPage.setProductImage("C:\\Users\\ASA\\IdeaProjects\\backUp3\\src\\main\\resources\\images\\productIsFinish.png");
            }
            productDetail.setText("Id : " + productInPage.getId() + "\n" +
                    "Name : " + productInPage.getProductName() + "\n" +
                    "Price : " + productInPage.getPrice() + "\n" +
                    "Seller : " + productInPage.getSeller() + "\n" +
                    "Category : " + productInPage.getCategory().getName() + "\n" +
                    "Number : " + productInPage.getNumberOfProducts() + "\n" +
                    "Average Score : " + productInPage.getScore() + "\n"
            );
            productDetail.setEditable(false);
            for (String productCategorySpecification : productInPage.productCategorySpecifications) {
                System.out.println(productCategorySpecification);
            }
            for (String specification : productInPage.productCategorySpecifications) {

                if (specification != null && !specification.equals("")) {
                    for (String productCategorySpecification : productInPage.productCategorySpecifications) {
                        if (!productCategorySpecification.equals(null)) {
                            if (!productCategoryDetail.getText().equals(handleProCatDetail())) {
                                productCategoryDetail.appendText(productCategorySpecification + "\n");
                            }
                        }
                    }
                } else {
                    System.out.println("trait is empty");
                }
            }
            productCategoryDetail.setEditable(false);
        }else makeRequest();
    }

    private void makeRequest() throws FileNotFoundException {
        if(request instanceof ProductRequest) {
            ProductRequest productRequest = (ProductRequest) request;
            productNameLabel.setText(productRequest.getProductName());
            File file = new File(productRequest.getImg());
            Image image = new Image(new FileInputStream(file));
            productPic.setImage(image);
            productDetail.setText("Id : " + productRequest.getProductId() + "\n" +
                    "Name : " + productRequest.getProductName() + "\n" +
                    "Price : " + productRequest.getPrice() + "\n" +
                    "Seller : " + productRequest.getSeller() + "\n" +
                    "Category : " + productRequest.getCategoryName() + "\n"
            );
            productDetail.setEditable(false);
            for (String productCategorySpecification : productRequest.getSpecialValue()) {
                System.out.println(productCategorySpecification);
            }
            for (String specification : productRequest.getSpecialValue()) {

                if (specification != null && !specification.equals("")) {
                    for (String productCategorySpecification : productRequest.getSpecialValue()) {
                        if (!productCategorySpecification.equals(null)) {
                            if (!productCategoryDetail.getText().equals(handleProCatDetail())) {
                                productCategoryDetail.appendText(productCategorySpecification + "\n");
                            }
                        }
                    }
                } else {
                    System.out.println("trait is empty");
                }
            }
            productCategoryDetail.setEditable(false);
        }
    }

    @FXML
    void popUpAddComment(MouseEvent mouseEvent) throws IOException {
        if (LoginMenu.isLogin()){
            Parent root = FXMLLoader.load(Objects.requireNonNull(ProductMenuFX.class.getClassLoader().getResource("comment.fxml")));
            prevScene = new Scene(root);
            thisStage = new Stage();
            thisStage.setScene(prevScene);
            thisStage.show();
        }else{
            nullAddCommentError.setVisible(true);
            nullAddCommentError.setText("sign In first");
            nullAddCommentError.setVisible(true);

        }
    }

    public void handleAddProductToLog(ActionEvent actionEvent) throws IOException {
       // if (LoginMenu.isLogin()){
            try {
                ProductMenu.setProductId(productInPage.getId());
                ProductMenu.addToCart();
            } catch (IOException e) {
                e.printStackTrace();
            }
            BuyLogFx.setPriRoot(productPagePane);
            BuyLogFx.setCurBuylog(ProductMenu.getBuyLog());



          //  BuyLogFx.getCurBuyLog().setBuyLogCustomer(LoginMenu.getLoginAccount());
            AnchorPane root = FXMLLoader.load(Objects.requireNonNull(BuyLogFx.class.getClassLoader().getResource("buyLogFx.fxml")));
            Scene scene = new Scene(root);
            Main.primStage.setScene(scene);
            Main.primStage.show();

       // }
//        else {
//            AnchorPane root = FXMLLoader.load(Objects.requireNonNull(LoginFx.class.getClassLoader().getResource("LoginFx.fxml")));
//            Scene scene = new Scene(root);
//            Main.primStage.setScene(scene);
//            Main.primStage.show();
//        }
    }

    public void handleBackAddCommentButton(ActionEvent actionEvent) throws IOException {
        thisStage.close();
    }

    public void handleSendComment(ActionEvent actionEvent) throws IOException {
        ProductMenu.setSelectedProduct(productInPage);
        nullAddCommentError.setText(OutputMassageHandler.showProductOutput(ProductMenu.addComments()));
        nullAddCommentError.setText(OutputMassageHandler.showProductOutput(ProductMenu.contentOfComment(commentTextField.getText())));
        nullAddCommentError.setText(OutputMassageHandler.showProductOutput(ProductMenu.titleOfComment(titleTextField.getText())));
    }

    public String handleProCatDetail() {

            String out = "";
        if(request == null) {
            for (String specification : productInPage.productCategorySpecifications) {
                out += specification + "\n";
            }
        }else{
            if(request instanceof  ProductRequest) {
                ProductRequest productRequest = (ProductRequest) request;
                for (String specification : productRequest.getSpecialValue()) {
                    out += specification + "\n";
                }
            }
        }
            return out;

    }

    public void initializeSimilar() throws FileNotFoundException {
        SimilarShow.list.clear();
        similarShows.clear();
        for (Product product : Product.getProductList()) {
            if (product.getProductName().equals(productInPage.getProductName()) && !product.getId().equals(productInPage.getId())){
                SimilarShow show = new SimilarShow();
                show.name = product.getProductName();
                show.price = product.getPrice();
                File file = new File(product.getProductImage());
                Image image = new Image(new FileInputStream(file));
                show.productImage = new ImageView();
                show.productImage.setFitWidth(100);
                show.productImage.setFitHeight(100);
                show.productImage.setImage(image);
            }
        }
    }

    public void ini() throws FileNotFoundException {
        initializeSimilar();
        for (SimilarShow show : SimilarShow.list) {
            similarShows.add(show);
        }
    }

    public void inCom(){
        data1.clear();
        for (ProductInMenusShow productInMenusShow : ProductInMenusShow.list) {
            data1.add(productInMenusShow);
        }
    }

    @FXML
    public void initialize() throws IOException {

        if(request == null) {
            ini();
            inCom();
            proName.setCellValueFactory(new PropertyValueFactory<SimilarShow,String>("name"));
            proPrice.setCellValueFactory(new PropertyValueFactory<SimilarShow,Double>("price"));
            proImage.setCellValueFactory(new PropertyValueFactory<SimilarShow,ImageView>("productImage"));

            firstCo.setCellValueFactory(new PropertyValueFactory<ProductInMenusShow,Double>("price"));
            secondCo.setCellValueFactory(new PropertyValueFactory<ProductInMenusShow,String>("name"));
            thirdCo.setCellValueFactory(new PropertyValueFactory<ProductInMenusShow,ImageView>("productImage"));
            forth.setCellValueFactory(new PropertyValueFactory<ProductInMenusShow,String>("id"));

            comparisonTableView.getColumns().addAll(forth,thirdCo,secondCo,firstCo);
            comparisonTableView.setItems(data1);

            similarProductsTableView.getColumns().addAll(proName,proPrice,proImage);
            similarProductsTableView.setItems(similarShows);

            initiaComment();
            titleColumn.setCellValueFactory(new PropertyValueFactory<ProComments, String>("title"));
            contentColumn.setCellValueFactory(new PropertyValueFactory<ProComments, String>("content"));
            personWhoCommented.setCellValueFactory(new PropertyValueFactory<ProComments,String>("personName"));
            for (Comment proComment : productInPage.proComments) {
                System.out.println(proComment);
            }
            for (ProComments datum : data) {
                System.out.println(datum+"@");
            }

            commentTableView.getColumns().addAll(titleColumn, contentColumn,personWhoCommented);
            commentTableView.setItems(data);

        }
    }

    private void initiaComment() {
        iniComment();
        for (ProComments proComments : ProComments.list) {
            data.add(proComments);
        }
    }

    private void iniComment() {
        ProComments.list.clear();
        data.clear();
        for (Product product : Product.getAllProduct()) {
            if (product.getId().equals(productInPage.getId())){
                for (Comment proComment : product.proComments) {
                    ProComments proComments = new ProComments();
                    proComments.title = proComment.getTitle();
                    proComments.content = proComment.getContent();
                    proComments.person=proComment.getPersonName();
                }
            }
        }
    }

    public void backToProductsMenu(ActionEvent event) throws IOException {
        AnchorPane root = FXMLLoader.load(Objects.requireNonNull(ProductMenuFX.class.getClassLoader().getResource("sample/fxFile/sample.fxml")));
        Scene scene = new Scene(root);
        thisStage.setScene(scene);
        thisStage.show();
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

    public void login(ActionEvent actionEvent)throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(LoginFx.class.getClassLoader().getResource("loginFx.fxml")));
        goToPage();
    }



    public void score5(MouseEvent mouseEvent) {
        score = 4;
    }

    public void score4(MouseEvent mouseEvent) {
        score = 4;
    }

    public void score3(MouseEvent mouseEvent) {
        score = 3;
    }

    public void score1(MouseEvent mouseEvent) {
        score = 1;
    }


    public void score(MouseEvent mouseEvent) throws IOException {
        if(score != 0){
            scoreMs.setText(OutputMassageHandler.showCustomerOutput( CustomerMenu.rateProduct( productInPage.getProductId(), score)));
        }else scoreMs.setText("you have to select first");
    }

    public void score2(MouseEvent mouseEvent) {
        score = 2;
    }

    public void handleScore(MouseEvent mouseEvent) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(ProductMenuFX.class.getClassLoader().getResource("ScoreFx.fxml")));
        Scene pageTwoScene = new Scene(root);
        Stage stage= new Stage();
        //Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setScene(pageTwoScene);
        stage.show();
    }

    public void userMenu(ActionEvent actionEvent)throws IOException {
        Parent curRoot  = FXMLLoader.load(Objects.requireNonNull(ProductMenuFX.class.getClassLoader().getResource("productMenu.fxml")));
        if(LoginMenu.getLoginAccount() instanceof Manager) {
            ManagerMenuFx.setPriRoot(curRoot);
            root = FXMLLoader.load(Objects.requireNonNull(ManagerMenuFx.class.getClassLoader().getResource("managerMenuFx.fxml")));
        }else  if(LoginMenu.getLoginAccount() instanceof Seller) {
            SellerMenuFx.setPriRoot(curRoot);
            root = FXMLLoader.load(Objects.requireNonNull(SellerMenuFx.class.getClassLoader().getResource("sellerMenuFx.fxml")));
        }else  if(LoginMenu.getLoginAccount() instanceof Customer) {
            CustomerMenuFx.setPriRoot(curRoot);
            root = FXMLLoader.load(Objects.requireNonNull(CustomerMenuFx.class.getClassLoader().getResource("customerMenuFx.fxml")));
        }
        goToPage();
    }

    public void doComparison(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(ProductMenuFX.class.getClassLoader().getResource("comparisonPro.fxml")));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }


    public void clickedColumn(MouseEvent mouseEvent) {
        TablePosition tablePosition = comparisonTableView.getSelectionModel().getSelectedCells().get(0);
        int row = tablePosition.getRow();
        ProductInMenusShow item = comparisonTableView.getItems().get(row);
        TableColumn tableColumn = tablePosition.getTableColumn();

        try {

            String im = (String) tableColumn.getCellObservableValue(item).getValue();
            comparetextField.setText(im);


        } catch (NullPointerException e) {
            System.out.println("you cant press here");
        }
    }
}
