package view.gui;

import controller.menus.LoginMenu;
import controller.menus.ManagerMenu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.accounts.Customer;
import model.accounts.Manager;
import model.accounts.Seller;
import model.off.DiscountCode;
import model.sort.Sort;
import view.OutputMassageHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class DiscountCodesFx {
    private static ArrayList<DiscountCode> discounts;

    @FXML
    private Label discountCodesMs;
    @FXML
    private TableView<DiscountCode> discountCodes;

    @FXML
    private TableColumn<DiscountCode, String> discountStart;

    @FXML
    private TableColumn<DiscountCode, Number> maxDiscountAmount;

    @FXML
    private TableColumn<DiscountCode, Number> discountAmount;

    @FXML
    private TableColumn<DiscountCode, Number> discountTotalTime;

    @FXML
    private TableColumn<DiscountCode, String> discountId;

    @FXML
    private TableColumn<DiscountCode, String> discountEnd;

    public static ObservableList<DiscountCode> data = FXCollections.observableArrayList();
    private static Parent root;
    private static Parent priRoot;

    public static void setPriRoot(Parent priRoot) {
        DiscountCodesFx.priRoot = priRoot;
    }

    @FXML
    public void initialize() throws IOException {
        makeTree();
    }




    public void makeTree() throws IOException {
        discountId.setCellValueFactory(new PropertyValueFactory<DiscountCode, String>("discountId"));
        discountAmount.setCellValueFactory(new PropertyValueFactory<DiscountCode, Number>("discountAmount"));
        discountStart.setCellValueFactory(new PropertyValueFactory<DiscountCode, String>("startOfDiscountPeriod"));
        discountEnd.setCellValueFactory(new PropertyValueFactory<DiscountCode,String>("endOfDiscountPeriod"));
        maxDiscountAmount.setCellValueFactory(new PropertyValueFactory<DiscountCode,Number>("maxDiscountAmount"));
        discountTotalTime.setCellValueFactory(new PropertyValueFactory<DiscountCode,Number>("totalTimesOfUse"));


        data.clear();
        data.addAll(discounts);
        discountCodes.setEditable(true);
        discountCodes.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        discountCodes.getSelectionModel().setCellSelectionEnabled(true);
        discountCodes.setItems(data);
    }
    public void edit(MouseEvent mouseEvent) throws IOException {
            root = FXMLLoader.load(Objects.requireNonNull(AddDiscountFx.class.getClassLoader().getResource("addDiscountFx.fxml")));
            goToPage();
    }

    public void remove(MouseEvent mouseEvent) throws IOException {
        if(discountCodes.getSelectionModel().getSelectedItem() != null) {
            DiscountCode discountCode = discountCodes.getSelectionModel().getSelectedItem();
            discountCodesMs.setText(OutputMassageHandler.showManagerOutput(ManagerMenu.removeDiscountCode(discountCode.getDiscountId())));
            makeTree();
        }

    }

    public void viewDiscount(MouseEvent mouseEvent) throws IOException {
        if(discountCodes.getSelectionModel().getSelectedItem() != null) {
            DiscountCode discountCode = discountCodes.getSelectionModel().getSelectedItem();
            ViewDiscountFx.setCurDiscountCode(discountCode);
            root = FXMLLoader.load(Objects.requireNonNull(ViewDiscountFx.class.getClassLoader().getResource("viewDiscountFx.fxml")));
            goToPage();
        }
    }

    private static void goToPage(){
        Scene pageTwoScene = new Scene(root);
        //Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        Main.primStage.setScene(pageTwoScene);
        Main.primStage.show();
    }

    public void sortDiscount(MouseEvent mouseEvent) {
        discountId.setCellValueFactory(new PropertyValueFactory<DiscountCode, String>("discountId"));
        discountAmount.setCellValueFactory(new PropertyValueFactory<DiscountCode, Number>("discountAmount"));
        discountStart.setCellValueFactory(new PropertyValueFactory<DiscountCode, String>("startOfDiscountPeriod"));
        discountEnd.setCellValueFactory(new PropertyValueFactory<DiscountCode,String>("endOfDiscountPeriod"));
        maxDiscountAmount.setCellValueFactory(new PropertyValueFactory<DiscountCode,Number>("maxDiscountAmount"));
        discountTotalTime.setCellValueFactory(new PropertyValueFactory<DiscountCode,Number>("totalTimesOfUse"));
        data.clear();
        Sort.setNewArrayOfDiscountCodeSort(DiscountCode.getAllDiscountCodes());
        data.addAll(   Sort.sortDiscountCodes());
        discountCodes.setEditable(true);
        discountCodes.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        discountCodes.getSelectionModel().setCellSelectionEnabled(true);
        discountCodes.setItems(data);

    }

    public static void setDiscounts(ArrayList<DiscountCode> discounts) {
        DiscountCodesFx.discounts = discounts;
    }


    public void userMenu(ActionEvent actionEvent) throws IOException {
        if(LoginMenu.getLoginAccount() instanceof Seller){
            root = FXMLLoader.load(Objects.requireNonNull(SellerMenuFx.class.getClassLoader().getResource("sellerMenuFx.fxml")));
        } else if(LoginMenu.getLoginAccount() instanceof Manager){
            root = FXMLLoader.load(Objects.requireNonNull(ManagerMenuFx.class.getClassLoader().getResource("managerMenuFx.fxml")));
        }else if(LoginMenu.getLoginAccount() instanceof Customer){
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


}
