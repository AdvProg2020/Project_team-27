package client.view.gui;

import client.Main;
import server.menus.LoginMenu;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import model.accounts.Customer;
import model.accounts.Manager;
import model.accounts.Seller;

import java.io.IOException;
import java.util.Objects;

public class MainMenuFx {
    private Parent root;

    public void users(MouseEvent mouseEvent) throws IOException {
        if(LoginMenu.isLogin()){
            Parent curRoot = FXMLLoader.load(Objects.requireNonNull(MainMenuFx.class.getClassLoader().getResource("mainMenuFx.fxml")));
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
        }
        else{
            root = FXMLLoader.load(Objects.requireNonNull(LoginFx.class.getClassLoader().getResource("loginFx.fxml")));

        }
        Parent curRoot = FXMLLoader.load(Objects.requireNonNull(MainMenuFx.class.getClassLoader().getResource("mainMenuFx.fxml")));
        LoginFx.setPriRoot(curRoot);
        goToPage();
    }


    public void products(MouseEvent mouseEvent) throws IOException {
        Parent curRoot = FXMLLoader.load(Objects.requireNonNull(MainMenuFx.class.getClassLoader().getResource("mainMenuFx.fxml")));
        ProductsMenuFX.setPriRoot(curRoot);
        root = FXMLLoader.load(Objects.requireNonNull(ProductsMenuFX.class.getClassLoader().getResource("productsMenu.fxml")));
        goToPage();

    }

    public void sales(MouseEvent mouseEvent) throws IOException {
        Parent curRoot = FXMLLoader.load(Objects.requireNonNull(MainMenuFx.class.getClassLoader().getResource("mainMenuFx.fxml")));
         SaleMenuFx.setPriRoot(curRoot);
         root = FXMLLoader.load(Objects.requireNonNull(SaleMenuFx.class.getClassLoader().getResource("saleMenuFx.fxml")));
        goToPage();

    }

    public void exit(MouseEvent mouseEvent) {
        System.exit(0);
    }

    private void goToPage() {
        Scene pageTwoScene = new Scene(root);
        //Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        Main.primStage.setScene(pageTwoScene);
        Main.primStage.show();
    }


}
