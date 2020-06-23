package gui;

import controller.menus.CustomerMenu;
import controller.menus.LoginMenu;
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
import model.off.Sale;
import model.productRelated.Product;

import java.io.IOException;
import java.util.Objects;

public class ViewSaleFx {
    @FXML private Label startSaleLabel;
    @FXML private TableColumn<Product, String> saleProduct;
    @FXML private Label endSaleLabel;
    @FXML private Label saleAmountLabel;
    @FXML private TableView<Product> saleProducts;
    @FXML private Label saleIdLabel;

    private static Sale curSale;
    public static ObservableList list = FXCollections.observableArrayList();
    private static Parent root;
    private static Parent priRoot;

    public static void setPriRoot(Parent priRoot) {
        ViewSaleFx.priRoot = priRoot;
    }

    public static Sale getCurSale() {
        return curSale;
    }

    public static void setCurSale(Sale curSale) {
        ViewSaleFx.curSale = curSale;
    }




    private void makeTree() {
        saleProduct.setCellValueFactory(new PropertyValueFactory<Product, String>("productId"));

       list.clear();
       list.addAll(curSale.getAllSaleProducts());
        //  usersList.getColumns().addAll(UserId,userName,userLast,userBirth,userPhoneNo,userEmail);
        saleProducts.setEditable(true);
        saleProducts.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        saleProducts.getSelectionModel().setCellSelectionEnabled(true);

        saleProducts.setItems(list);


    }
    @FXML
    public void initialize()  {
        makeTree();
        saleIdLabel.setText(String.valueOf(curSale.getOffId()));
        startSaleLabel.setText(String.valueOf(curSale.getStartOfSalePeriod()));
        endSaleLabel.setText(String.valueOf(curSale.getEndOfSalePeriod()));
        saleAmountLabel.setText(String.valueOf(curSale.getSaleAmount()));


    }





    public void userMenu(ActionEvent actionEvent) throws IOException {
       Parent curRoot = FXMLLoader.load(Objects.requireNonNull(ViewSaleFx.class.getClassLoader().getResource("viewSaleFx.fxml")));
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
