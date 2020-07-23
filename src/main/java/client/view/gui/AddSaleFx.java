package client.view.gui;

import client.Client;
import client.Main;
import client.view.OutputMassageHandler;
import server.menus.LoginMenu;
import server.menus.SellerMenu;
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
import model.productRelated.Product;
import model.productRelated.ProductStatus;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Objects;

public class AddSaleFx {

    @FXML
    private TextField saleAmount;
    @FXML
    private Label saleIdAlertLabel;
    @FXML
    private TextField startSaleDatePicker;
    @FXML
    private TableColumn<Product, String> addSaleProduct;
    @FXML
    private TextField endSaleDatePicker;
    @FXML
    private TextField saleIdTextField;
    @FXML
    private TableView<Product> addSaleProducts;
    @FXML
    private Label endSaleAlertLabel;
    @FXML
    private Label startSaleAlertLabel;
    @FXML
    private Label saleAmountAlertLabel;

    private boolean finish = false;
    private boolean oneProduct = false;
    public ObservableList list = FXCollections.observableArrayList();
    ArrayList<String> productsId = new ArrayList<>();
    private static Parent priRoot;
    private static Parent root;
    static ArrayList<Product> addAll = new ArrayList<>();


    public static void setPriRoot(Parent priRoot) {
        AddSaleFx.priRoot = priRoot;
    }

    @FXML
    public void initialize() throws IOException {
        makeTree();
    }

    public void makeTree() {
        addSaleProduct.setCellValueFactory(new PropertyValueFactory<Product, String>("productId"));
        list.clear();
        addAll.clear();
        list();
        list.addAll(addAll);
        addSaleProducts.setEditable(true);
        addSaleProducts.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        addSaleProducts.getSelectionModel().setCellSelectionEnabled(true);
        addSaleProducts.setItems(list);


    }

    private static void list() {
        if (LoginMenu.getLoginAccount() instanceof Seller) {
            Seller seller = (Seller) LoginMenu.getLoginAccount();
            for (Product product : seller.getAllProduct()) {
                if (product.getProductStatus() == ProductStatus.CONFIRMED) {
                    addAll.add(product);
                }
            }
        }
    }

    public void createSale(MouseEvent mouseEvent) throws IOException, ParseException {
        if (addSaleProducts.getSelectionModel().getSelectedItem() != null) {
            Product product = addSaleProducts.getSelectionModel().getSelectedItem();
            Client.start("addSale " + saleIdTextField.getText() + " " + startSaleDatePicker.getText() + " " + endSaleDatePicker.getText() + " " + saleAmount.getText() + " " + product.getId());
           /* if (SellerMenu.getCreate() == 0) {
                saleIdAlertLabel.setText(OutputMassageHandler.showSaleOutput(SellerMenu.setDetailsToSale(saleIdTextField.getText(), 0)));
            }
            if (SellerMenu.getCreate() == 1) {
                startSaleAlertLabel.setText(OutputMassageHandler.showSaleOutput(SellerMenu.setDetailsToSale(startSaleDatePicker.getText(), 1)));
                if (SellerMenu.getDetailMenu() == 2) {
                    endSaleAlertLabel.setText(OutputMassageHandler.showSaleOutput(SellerMenu.setDetailsToSale(endSaleDatePicker.getText(), 2)));
                }
                if (SellerMenu.getDetailMenu() == 3) {
                    saleAmountAlertLabel.setText(OutputMassageHandler.showSaleOutput(SellerMenu.setDetailsToSale(saleAmount.getText(), 3)));
                }
                if (SellerMenu.getDetailMenu() == 4) {
                    Product product = addSaleProducts.getSelectionModel().getSelectedItem();
                    saleAmountAlertLabel.setText(OutputMassageHandler.showSaleOutput(SellerMenu.setDetailsToSale(product.getId(), 4)));
                }
            }

            */
            saleIdAlertLabel.setText(Client.getInput());
        } else saleIdAlertLabel.setText("you have to select first");
    }

    public void editSale(MouseEvent mouseEvent) throws IOException, ParseException {
       // Client.start("editSale "+saleIdTextField.getText()+" "+ );
        if (SellerMenu.getEdit() == 0) {
            saleIdAlertLabel.setText(OutputMassageHandler.showSaleOutput(SellerMenu.editOff(saleIdTextField.getText())));
        }
        if (SellerMenu.getEdit() == 1) {
            startSaleAlertLabel.setText(OutputMassageHandler.showSaleOutput(SellerMenu.editOffField(startSaleDatePicker.getText(), "start")));
            endSaleAlertLabel.setText(OutputMassageHandler.showSaleOutput(SellerMenu.editOffField(endSaleDatePicker.getText(), "end")));
            saleAmountAlertLabel.setText(OutputMassageHandler.showSaleOutput(SellerMenu.editOffField(saleAmount.getText(), "amount")));

        }
        //else saleIdAlertLabel.setText("insert id first");
    }

    public void addProduct(MouseEvent mouseEvent) throws IOException, ParseException {
        if (SellerMenu.getSaleRequest() != null) {
            if (addSaleProducts.getSelectionModel().getSelectedItem() != null) {
                Product product = addSaleProducts.getSelectionModel().getSelectedItem();
                saleIdAlertLabel.setText(OutputMassageHandler.showSaleOutput(SellerMenu.editOffField(product.getId(), "add product")));
                makeTree();
            } else saleIdAlertLabel.setText("you have to select first");
        } else saleIdAlertLabel.setText("you have to insert name and edit first");
    }

    public void removeProduct(MouseEvent mouseEvent) throws IOException, ParseException {
        if (SellerMenu.getSaleRequest() != null) {
            if (addSaleProducts.getSelectionModel().getSelectedItem() != null) {
                Product product = addSaleProducts.getSelectionModel().getSelectedItem();
                saleIdAlertLabel.setText(OutputMassageHandler.showSaleOutput(SellerMenu.editOffField(product.getId(), "remove product")));
                makeTree();
            } else saleIdAlertLabel.setText("you have to select first");
        } else saleIdAlertLabel.setText("you have to insert name and edit first");

    }

    public void userMenu(ActionEvent actionEvent) throws IOException {
        Parent curRoot = FXMLLoader.load(Objects.requireNonNull(AddSaleFx.class.getClassLoader().getResource("addSaleFx.fxml")));
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

    private static void backToFirst() {
        SellerMenu.setCreate(0);
        SellerMenu.setEdit(0);
    }

    public void back(ActionEvent actionEvent) {
        backToFirst();
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

    public void finish(MouseEvent mouseEvent) {
        finish = true;
    }

    private static void goToPage() {
        backToFirst();
        Scene pageTwoScene = new Scene(root);
        //Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        Main.primStage.setScene(pageTwoScene);
        Main.primStage.show();
    }

}
