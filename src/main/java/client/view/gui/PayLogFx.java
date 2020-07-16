package client.view.gui;

import server.menus.ProductMenu;
import server.menus.CustomerMenu;
import server.menus.LoginMenu;
import server.menus.RegisterMenu;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.log.BuyLog;


import java.io.IOException;
import java.util.Objects;

public class PayLogFx {
    @FXML
    TextField PhoneNumberTextFieldPayLog;
    @FXML
    TextArea AddressTextPayLog;
    @FXML
    TextField hasDiscount;
    @FXML
    Label alertMessage;
    @FXML
    TextField discountPayLogTextField;
    boolean has = true;
    boolean choose = false;
    //int output;
    private static BuyLog preBuyLog;

    private static Parent priRoot;
    private static Parent root;
    private static String fast;


    public static Parent getPriRoot() {
        return priRoot;
    }

    public static void setPriRoot(Parent priRoot) {
        PayLogFx.priRoot = priRoot;
    }

    public static void setPreBuyLog(BuyLog preBuyLog) {
        PayLogFx.preBuyLog = preBuyLog;
    }

    public void market(MouseEvent mouseEvent) {
        CustomerMenu.setMarket(true);
        choose = true;
    }

    public void bank(MouseEvent mouseEvent) {
        CustomerMenu.setMarket(false);
        choose = true;
    }

    public void paymentProcess(MouseEvent mouseEvent) throws IOException {
        if (choose) {
            if (has) {
                if (fast != null) {
                    if (discountPayLogTextField.getText() != null) {
                        show(OutputMassageHandler.showPurchaseOutput(CustomerMenu.discountCodeValidation(discountPayLogTextField.getText())));
                    }
                    ///  if(CustomerMenu.getDiscountID() != null) {
                    alertMessage.setText(OutputMassageHandler.showReceiverInfo(RegisterMenu.receiverInformation(PhoneNumberTextFieldPayLog.getText(), 0)));
                    if (RegisterMenu.getDetailMenu() == 1) {
                        alertMessage.setText(OutputMassageHandler.showReceiverInfo(RegisterMenu.receiverInformation(AddressTextPayLog.getText(), 1)));
                    }
                    if (RegisterMenu.getDetailMenu() == 2) {
                        alertMessage.setText(OutputMassageHandler.showReceiverInfo(RegisterMenu.receiverInformation(fast, 2)));
                    }
                    if (RegisterMenu.ok) {
                        // alertMessage.setText(OutputMassageHandler.showReceiverInfo(CustomerMenu.payment()));
                        // if(CustomerMenu.ok) {
                        alertMessage.setText(OutputMassageHandler.showPurchaseOutput(CustomerMenu.payment()) + "\n" +
                                "your credit: " + LoginMenu.getLoginAccount().getCredit());
                        //}
                        if (CustomerMenu.getPrizeDiscountCode() != null) {
                            show("your discount prize: " + CustomerMenu.getPrizeDiscountCode());
                        }
                        finalP();
                        has = false;

                    }
                    //  }

                }
            }
        }
    }

    private void show(String text) {
        Label label = new Label();
        StackPane rot = new StackPane();
        rot.getChildren().add(label);
        label.setText(text);
        Stage massage = new Stage();
        massage.setScene(new Scene(rot, 500, 500));
        massage.show();
    }

    private void finalP() {
        choose = false;
        has = true;
        CustomerMenu.setDiscountID(null);
        ProductMenu.setSelectedProduct(null);
        ProductMenu.setProductId(null);
        ProductMenu.setBuyLog(null);
        CustomerMenu.setPrizeDiscountCode(null);
    }

    public void alertMessage() {
        Stage window = new Stage();
        Scene scene;
        window.initModality(Modality.APPLICATION_MODAL);
        VBox layout = new VBox();
        TextArea textArea = new TextArea("Payment successfully");
        Button button = new Button("go to account");
        layout.getChildren().addAll(button, textArea);
        layout.setAlignment(Pos.CENTER);
        button.setOnAction(e -> window.close());
        scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }


    public void yes(MouseEvent mouseEvent) {
        fast = "yes";
    }

    public void no(MouseEvent mouseEvent) {
        fast = "no";
    }

    public void exit(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void main(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(MainMenuFx.class.getClassLoader().getResource("mainMenuFx.fxml")));
        goToPage();

    }

    private static void goToPage() {
        if (root != null) {
            Scene pageTwoScene = new Scene(root);
            //Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            Main.primStage.setScene(pageTwoScene);
            Main.primStage.show();
        }
    }

    public void back(ActionEvent actionEvent) {
        root = priRoot;
        goToPage();
    }


//              if (hasDiscount.getText().matches("no")) {
//        discountPayLogTextField.setDisable(true);
//    } else {
//        CustomerMenu.checkDiscountCode(discountPayLogTextField.getText());
//        if (CustomerMenu.checkDiscountCode(discountPayLogTextField.getText())) {
//            CustomerMenu.discountCodeValidation(discountPayLogTextField.getText());
//        }
//    }
}