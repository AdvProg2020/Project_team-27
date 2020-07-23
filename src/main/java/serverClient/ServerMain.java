package serverClient;

import java.util.Scanner;

public class ServerMain {
    public static int port = 4293;
    static Server server;

    public static void main(String[] args) {
        startServer(port);
    }

    public ServerMain(int port) {
        ServerMain.port = port;
        server = new Server(port);
        System.out.println("Server Started ");
    }

    public static void startServer(int port) {
        System.out.println("Please Enter port number For Server ");
        new ServerMain(port);
    }
}
