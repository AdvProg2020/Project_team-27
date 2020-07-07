package controller.menus.serverClient;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    private static ServerSocket serverSocket;
    private static DataInputStream dataInputStream;
    private static DataOutputStream dataOutputStream;
    private static Socket clientServer;
    private static Scanner scanner;

    public static void main(String[] args) {
        run();
    }

    private static void run() {
        try {
            serverSocket = new ServerSocket(8888);
            System.out.println("Waiting for client");
            waitForClient();
        } catch (IOException e) {
            System.err.println("Error newing server");
        }
    }

    private static void waitForClient() {
        try {
            clientServer = serverSocket.accept();
            System.out.println("client connected");
            handleConnection();
        } catch (IOException e) {
            System.err.println("Error client to be connected on server side");
        }
    }

    private static void handleConnection() {
        try {

            dataOutputStream = new DataOutputStream(new BufferedOutputStream(clientServer.getOutputStream()));
            dataInputStream = new DataInputStream(new BufferedInputStream(clientServer.getInputStream()));
            Scanner scanner = new Scanner(System.in);
            String input = "";
        }catch (IOException e){
            System.err.println("Error handling connection on server side");
        }
    }
}
