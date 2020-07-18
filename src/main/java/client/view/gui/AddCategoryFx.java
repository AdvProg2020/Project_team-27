package client.view.gui;

import client.Main;
import client.view.OutputMassageHandler;
import server.menus.LoginMenu;
import server.menus.ManagerMenu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import model.accounts.Customer;
import model.accounts.Manager;
import model.accounts.Seller;
import model.productRelated.Category;


import java.io.IOException;
import java.util.Objects;

public class AddCategoryFx {

    @FXML
    private Label categoryIdMs;
    @FXML
    private TextField categoryId;
    @FXML
    private Label categoryTraitMs;
    @FXML
    private Label categoryAddTraitMs;
    @FXML
    private TextField categoryAddTrait;
    @FXML
    private ListView<String> categoryTraits;

    private String catName;
    public static ObservableList list = FXCollections.observableArrayList();
    private static Parent priRoot;
    private static Parent root;


    public static void setPriRoot(Parent priRoot) {
        AddCategoryFx.priRoot = priRoot;
    }

    @FXML
    public void initialize() throws IOException {
        if (catName != null) {
            makeTree();
        }
    }

    public void makeList() {
        list.clear();
       // categoryTraits.getItems().addAll(list);
    }

    public void makeTree() {
        categoryTraits.setEditable(true);
        categoryTraits.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        makeList();
        list.addAll(Category.getCategoryWithName(catName).getTraits());
        categoryTraits.getItems().addAll(list);
    }


    public void createCategory(MouseEvent mouseEvent) throws IOException {
        if (LoginMenu.getLoginAccount() instanceof Manager) {
            if (ManagerMenu.getCreate() == 0) {
                categoryIdMs.setText(OutputMassageHandler.showManagerOutput(ManagerMenu.addCategory(categoryId.getText())));
                catName = categoryId.getText();
            }
            if (ManagerMenu.getCreate() == 1) {
                categoryAddTraitMs.setText(OutputMassageHandler.showManagerOutput(ManagerMenu.setDetailToCategory(categoryAddTrait.getText())));
                makeTree();
            }
        } else categoryTraitMs.setText("only manager have access");
    }


    public void removeTrait(MouseEvent mouseEvent) throws IOException {
        if (LoginMenu.getLoginAccount() instanceof Manager) {
            if (ManagerMenu.getEditableCategory() != null) {
                categoryAddTraitMs.setText(OutputMassageHandler.showManagerOutput(ManagerMenu.removeTraitToCatEdit(categoryAddTrait.getText())));
                makeTree();
            } else categoryTraitMs.setText("you have to put name and edit first");
        } else categoryTraitMs.setText("only manager have access");
    }

    public void editCategory(MouseEvent mouseEvent) {
        categoryIdMs.setText(OutputMassageHandler.showManagerOutput(ManagerMenu.editCategory(categoryId.getText())));

    }

    public void addTrait(MouseEvent mouseEvent) throws IOException {
        if (LoginMenu.getLoginAccount() instanceof Manager) {
            if (ManagerMenu.getEditableCategory() != null) {
                categoryAddTraitMs.setText(OutputMassageHandler.showManagerOutput(ManagerMenu.addTraitToCatEdit(categoryAddTrait.getText())));
                makeTree();
            } else categoryTraitMs.setText("you have to put name and edit first");
        } else categoryTraitMs.setText("only manager have access");
    }



    public void userMenu(ActionEvent actionEvent) throws IOException {
        Parent curRoot = FXMLLoader.load(Objects.requireNonNull(AddCategoryFx.class.getClassLoader().getResource("addCategoryFx.fxml")));
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

    private static void backToFirst(){
        ManagerMenu.setCreate(0);
        ManagerMenu.setEdit(0);
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
        backToFirst();
        Scene pageTwoScene = new Scene(root);
        //Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        Main.primStage.setScene(pageTwoScene);
        Main.primStage.show();
    }

         /*  public void makeTree() {
        categoryTrait.setCellValueFactory(new PropertyValueFactory<Category, String>("traits"));
        categoryTraits.setEditable(true);
        categoryTraits.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        list.clear();
     //   for (int i = 0; i < Category.getTraits().size() ; i++) {
       //     usernames.add(Category.getTraits().get(i).);
       // }
        list.addAll(Category.getCategoryWithName(catName).getTraits());
        categoryTraits.setItems(list);
       // categoryTraits.getItems().addAll(list);
    }
     public void removeTrait(MouseEvent mouseEvent) throws IOException {
        if(categoryTraits.getSelectionModel().getSelectedItem() != null) {
            String str = categoryTraits.getSelectionModel().getSelectedItem();
            categoryTraitMs.setText(OutputMassageHandler.showManagerOutput(ManagerMenu.removeTraitToCatEdit(str)));
            makeTree();
        }else categoryTraitMs.setText("you have to select first");

    }

   */

}


