package server;

import client.Main;
import client.view.OutputMassageHandler;
import model.accounts.Account;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;
import java.util.Scanner;

import server.menus.*;
import serverClient.ServerMain;

public class MarketServer {
    private static ServerSocket serverSocket;
    private static DataInputStream dataInputStream;
    private static DataOutputStream dataOutputStream;
    private static Socket clientServer;
    private static Scanner scanner;
    public static File file;

    public static void main(String[] args) {
        run();
    }

    private static void run() {
        try {
            Main.gson();
            ServerMain.startServer(4293);
            serverSocket = new ServerSocket(8888);
            System.out.println("market connected");
            while (true) {
                clientServer = serverSocket.accept();
              //  System.out.println("client connected");
                dataOutputStream = new DataOutputStream(new BufferedOutputStream(clientServer.getOutputStream()));
                dataInputStream = new DataInputStream(new BufferedInputStream(clientServer.getInputStream()));
                new HandleMarket(clientServer,dataInputStream,dataOutputStream).start();
            }
        } catch (IOException e) {
            System.err.println("Error newing server");
        }
    }


    public static void serverSellFile() throws IOException {
        ServerSocket serverSocket = new ServerSocket(15123);
        Socket socket = serverSocket.accept();
        System.out.println("Accepted connection : " + socket);
        File transferFile = file;
        byte[] bytearray = new byte[(int) transferFile.length()];
        FileInputStream fin = new FileInputStream(transferFile);
        BufferedInputStream bin = new BufferedInputStream(fin);
        bin.read(bytearray, 0, bytearray.length);
        OutputStream os = socket.getOutputStream();
        System.out.println("Sending Files...");
        os.write(bytearray, 0, bytearray.length);
        os.flush();
        socket.close();
        System.out.println("File transfer complete");
    }


}
