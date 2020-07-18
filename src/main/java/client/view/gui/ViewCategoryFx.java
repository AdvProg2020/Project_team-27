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
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import model.accounts.Customer;
import model.accounts.Manager;
import model.accounts.Seller;
import model.productRelated.Category;
import model.productRelated.Product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class ViewCategoryFx {
    private static Category curCat;
    private static Parent priRoot;
    private static Parent root;
    public static ObservableList<String> data = FXCollections.observableArrayList();
    public static ObservableList<String> list = FXCollections.observableArrayList();
    private ArrayList<String> allProducts = new ArrayList<>();
    @FXML
    private ListView<String> traits;

    @FXML
    private Text nameTextField;

    @FXML
    private Label catNameLabel;

    @FXML
    private ListView<String> products;

    public static void setCurCat(Category curCat) {
        ViewCategoryFx.curCat = curCat;
    }

    public static void setPriRoot(Parent priRoot) {
        ViewCategoryFx.priRoot = priRoot;
    }

    public void initializeObserverList() {
        for (Product product : Product.getAllProduct()) {
            if (product.getCategory() != null) {
                if (product.getCategory().getName().equals(curCat.getName())) {
                    allProducts.add(product.getId());
                }
            }
        }
    }

    @FXML
    public void initialize() throws IOException {
        catNameLabel.setText(curCat.getName());
        data.clear();
        list.clear();
        data.addAll(curCat.getTraits());
        initializeObserverList();
        list.addAll(allProducts);
        products.getItems().addAll(list);
        traits.getItems().addAll(data);
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


    public void userMenu(ActionEvent actionEvent) throws IOException {
        Parent curRoot = FXMLLoader.load(Objects.requireNonNull(ViewCategoryFx.class.getClassLoader().getResource("ViewCategoryFx.fxml")));
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
//    @FXML
//    public void initialize() throws IOException {
//        catNameLabel.setText(curCat.getName());
//        productCatColumn.setCellValueFactory(new PropertyValueFactory<>("product"));
//        traitCatColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
//        initializeObserverList();
//        viewCategoryTable.setEditable(true);
//
//
//        viewCategoryTable.setItems(data);
//    }
}