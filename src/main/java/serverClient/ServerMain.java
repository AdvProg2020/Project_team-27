package serverClient;

import java.util.Scanner;

public class ServerMain {
    int port;
    static Server server;

    public ServerMain(int port) {
        this.port = port;
        server = new Server(port);
        System.out.println("Chat Server Started ");
    }

    public static void startServer(int port) {
     //   System.out.println("Please Enter port number For Server ");
        new ServerMain(port);
    }
}