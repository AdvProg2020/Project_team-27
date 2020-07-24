package serverClient;


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
}

