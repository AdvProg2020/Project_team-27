package serverClient;


import client.view.FileHandling;
import com.google.gson.reflect.TypeToken;
import model.accounts.Customer;
import model.accounts.Seller;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.InetAddress;
import java.util.ArrayList;


public class ServerClient {
    public int port;
    public String name;
    public InetAddress address;
    public int ID;
    public int  attempt=0;
    public String userId;



    public ServerClient(String name,InetAddress address, int port,final int ID,String userId){
        this.port=port;
        this.name=name;
        this.address=address;
        this.ID=ID;
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public InetAddress getAddress() {
        return address;
    }

    public int getAttempt() {
        return attempt;
    }

    public int getPort() {
        return port;
    }

    public String getUserId() {
        return userId;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }

    public void setAttempt(int attempt) {
        this.attempt = attempt;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ServerClient{" +
                "port=" + port +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", ID=" + ID +
                ", attempt=" + attempt +
                ", userId='" + userId + '\'' +
                '}';
    }
}