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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.accounts.Account;
import model.accounts.Customer;
import model.accounts.Manager;
import model.accounts.Seller;
import model.request.*;
import model.sort.Sort;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;

public class RequestsFx {
    @FXML
    private TableView<Request> requests;
    @FXML
    private TableColumn<Request, String> requestId;
    @FXML
    private TableColumn<Request, Date> requestDate;
    @FXML
    private TableColumn<Request, Account> requestSeller;

    public static final ObservableList list = FXCollections.observableArrayList();
    private static Parent root;
    private static Parent priRoot;

    public static void setPriRoot(Parent priRoot) {
        RequestsFx.priRoot = priRoot;
    }

    @FXML
    public void initialize() throws IOException {
        makeTree();
    }

    private void makeList() throws IOException {
        list.clear();
        list.addAll(Request.getAllRequests());
    }

    private void makeTree() throws IOException {

        requestId.setCellValueFactory(new PropertyValueFactory<Request, String>("requestText"));
        requestSeller.setCellValueFactory(new PropertyValueFactory<Request, Account>("seller"));
        requestDate.setCellValueFactory(new PropertyValueFactory<Request, Date>("now"));

        makeList();
        requests.setEditable(true);
        requests.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        requests.getSelectionModel().setCellSelectionEnabled(true);

        requests.setItems(list);
    }


    public void showRequest(MouseEvent mouseEvent) throws IOException {
        String im = "";
        TablePosition tablePosition = requests.getSelectionModel().getSelectedCells().get(0);
        int row = tablePosition.getRow();
        Request item = requests.getItems().get(row);
        TableColumn tableColumn = tablePosition.getTableColumn();

        try {

            im = (String) tableColumn.getCellObservableValue(item).getValue();

        } catch (NullPointerException e) {
            System.out.println("you cant press here");
        }

        if (requests.getSelectionModel().getSelectedItem() != null) {
            Parent curRoot   = FXMLLoader.load(Objects.requireNonNull(RequestsFx.class.getClassLoader().getResource("requestsFx.fxml")));
            Request request = Request.getRequestFromID(im);
            if (request instanceof AccountRequest) {
                ViewAccountFx.setRequest(request);
                ViewAccountFx.setPriRoot(curRoot);
                root = FXMLLoader.load(Objects.requireNonNull(ViewAccountFx.class.getClassLoader().getResource("viewAccountFx.fxml")));
                goToPage();
            } else if (request instanceof ProductRequest) {
                ProductMenuFX.setPriRoot(curRoot);
                ProductMenuFX.setRequest(request);
                root = FXMLLoader.load(Objects.requireNonNull(ProductMenuFX.class.getClassLoader().getResource("productMenu.fxml")));
                goToPage();
            } else if (request instanceof CommentRequest) {
                CommentRequest commentRequest = (CommentRequest) request;
                ViewComment.setPriRoot(curRoot);
                ViewComment.setCommentRequest(commentRequest);
                root = FXMLLoader.load(Objects.requireNonNull(ViewComment.class.getClassLoader().getResource("viewCommentFx.fxml")));
                goToPage();
            } else if (request instanceof SaleRequest) {
                ViewSaleFx.setRequest(request);
                ViewSaleFx.setPriRoot(curRoot);
                root = FXMLLoader.load(Objects.requireNonNull(ViewSaleFx.class.getClassLoader().getResource("viewSaleFx.fxml")));
                goToPage();
            }

        }
    }

    public void declineRequest(MouseEvent mouseEvent) throws IOException {
        String text = null;
        if (requests.getSelectionModel().getSelectedItem() != null) {
            Request request = requests.getSelectionModel().getSelectedItem();
            text = OutputMassageHandler.showOutputWithString(ManagerMenu.declineRequest(request.getRequestText()));
            makeTree();
        } else text = " you have to select request first";
        show(text);
    }

    public void acceptRequest(MouseEvent mouseEvent) throws IOException {
        String text = null;
        if (requests.getSelectionModel().getSelectedItem() != null) {
            Request request = requests.getSelectionModel().getSelectedItem();
            text = OutputMassageHandler.showOutputWithString(ManagerMenu.acceptRequest(request.getRequestText()));
            makeTree();
        } else text = " you have to select request first";
        show(text);
    }

    private static void goToPage() {
        Scene pageTwoScene = new Scene(root);
        //Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        Main.primStage.setScene(pageTwoScene);
        Main.primStage.show();
    }

    private void show(String text) {
        Label label = new Label();
        StackPane root = new StackPane();
        root.getChildren().add(label);
        label.setText(text);
        Stage massage = new Stage();
        massage.setScene(new Scene(root, 500, 500));
        massage.show();
    }


    public void userMenu(ActionEvent actionEvent) throws IOException {
        Parent curRoot = FXMLLoader.load(Objects.requireNonNull(RequestsFx.class.getClassLoader().getResource("requestsFx.fxml")));
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

    public void sortRequest(MouseEvent mouseEvent) {

        requestId.setCellValueFactory(new PropertyValueFactory<Request, String>("requestText"));
        requestSeller.setCellValueFactory(new PropertyValueFactory<Request, Account>("seller"));
        requestDate.setCellValueFactory(new PropertyValueFactory<Request, Date>("requestDate"));

        list.clear();
        Sort.setNewArrayOfRequest(Request.getAllRequests());
        list.addAll(Sort.sortRequest());
        requests.setEditable(true);
        requests.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        requests.getSelectionModel().setCellSelectionEnabled(true);

        requests.setItems(list);
    }
}