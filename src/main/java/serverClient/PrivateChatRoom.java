package serverClient;

import client.view.gui.SupportersFx;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import model.accounts.Customer;
import model.accounts.Supporter;

import java.io.IOException;
import java.util.Objects;

public class PrivateChatRoom {
    public Supporter supporter;
    public Customer customer;
    public Scene scene;

    public PrivateChatRoom(Supporter supporter, Customer customer) {
        this.supporter = supporter;
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Supporter getSupporter() {
        return supporter;
    }

    public void setSupporter(Supporter supporter) {
        this.supporter = supporter;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Scene startPage(){
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(PrivateChatRoom.class.getClassLoader().getResource("privateChat.fxml")));
            scene = new Scene(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("client in chatroom" + customer.getUsername());
        System.out.println("supporter in chatroom" + supporter.getUsername());
        return scene;
    }

}