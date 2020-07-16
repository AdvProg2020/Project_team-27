package client.view.gui;

import server.menus.LoginMenu;
import server.menus.ManagerMenu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.accounts.Customer;
import model.accounts.Manager;
import model.accounts.Seller;
import model.productRelated.Category;
import model.sort.Sort;

import java.io.IOException;
import java.util.Objects;

public class CategoriesFX {

    @FXML private TableColumn<Category, String> categoriesName;
    @FXML private TableView<Category> categories;


    public static ObservableList list = FXCollections.observableArrayList();
    private static Parent priRoot;
    private static Parent root;

    public static void setPriRoot(Parent priRoot) {
        CategoriesFX.priRoot = priRoot;
    }

    @FXML
    public void initialize() throws IOException {
        makeTree();
    }

    public static void makeList() {
        list.clear();
        list.addAll(Category.getAllCategories());
    }

    private void makeTree() {
        categoriesName.setCellValueFactory(new PropertyValueFactory<Category, String>("name"));


        makeList();
        categories.setEditable(true);
        categories.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        categories.getSelectionModel().setCellSelectionEnabled(true);

        categories.setItems(list);
    }


    public void editCategories(MouseEvent mouseEvent) throws IOException {
        if(LoginMenu.getLoginAccount() instanceof  Manager) {
            Parent curRoot = FXMLLoader.load(Objects.requireNonNull(CategoriesFX.class.getClassLoader().getResource("categoriesFx.fxml")));
            AddCategoryFx.setPriRoot(curRoot);
            root = FXMLLoader.load(Objects.requireNonNull(AddCategoryFx.class.getClassLoader().getResource("addCategoryFx.fxml")));
            goToPage();
        }
    }

    public void removeCategories(MouseEvent mouseEvent) {
        if(LoginMenu.getLoginAccount() instanceof  Manager) {
            if (categories.getSelectionModel().getSelectedItem() != null) {
                Category curCat = categories.getSelectionModel().getSelectedItem();
                OutputMassageHandler.showManagerOutput(ManagerMenu.removeCategory(curCat.getName()));
                makeTree();
            }
        }
    }


    public void sortCategories(MouseEvent mouseEvent) {
        categoriesName.setCellValueFactory(new PropertyValueFactory<Category, String>("name"));

        list.clear();
        Sort.setNewArrayOfCategory(Category.getAllCategories());
        list.addAll( Sort.categoryNameSort());

        categories.setEditable(true);
        categories.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        categories.getSelectionModel().setCellSelectionEnabled(true);

        categories.setItems(list);
    }

    public void addCategories(MouseEvent mouseEvent) throws IOException {
        if(LoginMenu.getLoginAccount() instanceof  Manager) {
            Parent curRoot = FXMLLoader.load(Objects.requireNonNull(CategoriesFX.class.getClassLoader().getResource("categoriesFx.fxml")));
            AddCategoryFx.setPriRoot(curRoot);
            root = FXMLLoader.load(Objects.requireNonNull(AddCategoryFx.class.getClassLoader().getResource("addCategoryFx.fxml")));
            goToPage();
        }
    }

    public void viewCategories(MouseEvent mouseEvent) throws IOException {
        if(categories.getSelectionModel().getSelectedItem() != null) {
            Category curCat = categories.getSelectionModel().getSelectedItem();
            Parent curRoot = FXMLLoader.load(Objects.requireNonNull(CategoriesFX.class.getClassLoader().getResource("categoriesFx.fxml")));
            ViewCategoryFx.setPriRoot(curRoot);
            ViewCategoryFx.setCurCat(curCat);
            root = FXMLLoader.load(Objects.requireNonNull(ViewCategoryFx.class.getClassLoader().getResource("ViewCategoryFx.fxml")));
            goToPage();
        }
    }
    private static void goToPage(){
        Scene pageTwoScene = new Scene(root);
        //Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        Main.primStage.setScene(pageTwoScene);
        Main.primStage.show();
    }


    public void userMenu(ActionEvent actionEvent) throws IOException {
        Parent curRoot = FXMLLoader.load(Objects.requireNonNull(CategoriesFX.class.getClassLoader().getResource("categoriesFx.fxml")));
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
