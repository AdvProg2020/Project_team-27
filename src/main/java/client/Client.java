package client;

import client.view.gui.MainMenuFx;
import client.view.gui.SignUpFx;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.accounts.Manager;

import java.io.*;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

public class Client {

    private static Scanner scanner;
    private static DataOutputStream dataOutputStream;
    private static DataInputStream dataInputStream;
    private static Socket clientSocket;
    private static  String input;

    public static String getInput() {
        return input;
    }

    public static void setInput(String input) {
        Client.input = input;
    }

    public static void connect() {
        try {
            clientSocket = new Socket("localhost", 8888);
            //  System.out.println("Successfully connected to server!");
            dataInputStream = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
            dataOutputStream = new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));

        } catch (IOException e) {
            System.err.println("Error client connecting on client side");
        }
    }

    public static void SendMessage(String msg) throws IOException {
        try {
            //  System.out.println(msg);
            dataOutputStream.writeUTF(msg);
            dataOutputStream.flush();
        } catch (IOException e) {
            throw new IOException("Exception while sending message:");
        }
    }

    public static void StartListeningOnInput() {
        new Thread(() -> {
            while (true) {
                try {
                    input = dataInputStream.readUTF();
                    System.out.println(input);
                } catch (IOException e) {
                    System.out.println("disconnected");
                    System.exit(0);
                }
            }
        }).start();
    }

    public static void start(String start) throws IOException {
        try {
            connect();
            StartListeningOnInput();
            // Scanner scanner = new Scanner(System.in);
            //while (true) {
            SendMessage(start);
            // }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void buyingFile() throws IOException {
        int filesize = 1022386;
        int bytesRead;
        int currentTot = 0;
        Socket socket = new Socket("127.0.0.1", 15123);
        byte[] bytearray = new byte[filesize];
        InputStream is = socket.getInputStream();
        FileOutputStream fos = new FileOutputStream("copy2.pdf");
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        bytesRead = is.read(bytearray, 0, bytearray.length);
        currentTot = bytesRead;
        do {
            bytesRead = is.read(bytearray, currentTot, (bytearray.length - currentTot));
            if (bytesRead >= 0) currentTot += bytesRead;
        } while (bytesRead > -1);
        bos.write(bytearray, 0, currentTot);
        bos.flush();
        bos.close();
        System.out.println("file received");
        socket.close();
    }

}



