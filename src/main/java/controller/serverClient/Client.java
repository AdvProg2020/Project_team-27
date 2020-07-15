package controller.serverClient;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static Scanner scanner;
    private static DataOutputStream dataOutputStream;
    private static DataInputStream dataInputStream;
    private static Socket clientSocket;

    public static void main(String[] args) {
        run();
    }

    private static void run() {
        try {
            clientSocket = new Socket("127.0.0.1", 8888);
            System.out.println("Successfully connected to server!");
            handleConnection();
        } catch (IOException e) {
            System.err.println("Error client connecting on client side");
        }
    }

    private static void handleConnection() {

        try {
            dataInputStream = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
            dataOutputStream = new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
            scanner = new Scanner(System.in);
            String userInput = "";
        } catch (IOException e) {
            System.err.println("Error handling connection on client side");
        }

    }

}
