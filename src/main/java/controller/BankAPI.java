package controller;

import model.accounts.Account;
import model.accounts.Customer;
import model.accounts.Manager;
import model.accounts.Seller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

/**
 * This class handles initiating connection to bankAPI ,sending requests to Bank server
 * and also responses from Bank server.
 */
public class BankAPI {
    public static final int PORT = 8383;
   // public static final String IP = "192.168.1.4";
   public static final String IP = "192.168.43.238";

    private static DataOutputStream outputStream;
    private static DataInputStream inputStream;
    public static String input;
    static Socket socket;
    static boolean first= true;

    /**
     * This method is used to add initiating socket and IN/OUT data stream .
     *
     * @throws IOException when IP/PORT hasn't been set up properly.
     */
    public static void ConnectToBankServer() throws IOException {
        try {
          //  if(first) {
                socket = new Socket(IP, PORT);
           //     first = false;
           // }
            outputStream = new DataOutputStream(socket.getOutputStream());
            inputStream = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new IOException("Exception while initiating connection:");
        }
    }

    /**
     * This method is used to start a Thread ,listening on IN data stream.
     */
    public static void StartListeningOnInput() {
        new Thread(() -> {
            while (true) {
                try {
                    input = inputStream.readUTF();
                    System.out.println(inputStream.readUTF());
                } catch (IOException e) {
                    System.out.println("disconnected");
                    System.exit(0);
                }
            }
        }).start();
    }

    public static void StartListeningOnInputRe(Account account) {
        new Thread(() -> {
            while (true) {
                try {
                    System.out.println("*+"+inputStream.readUTF());
                    account.setAccountId(inputStream.readUTF());
                    System.out.println("account: "+account.getAccountId());
                    Manager.writeInJ();
                    Seller.writeInJ();
                    Customer.writeInJ();
                } catch (IOException e) {
                    System.out.println("disconnected");
                    System.exit(0);
                }
            }
        }).start();
    }

    public static void StartListeningOnInputLo(Account account) {
        new Thread(() -> {
            while (true) {
                try {
                    System.out.println("*+"+inputStream.readUTF());
                    account.setToken(inputStream.readUTF());
                    Date date = new Date();
                    account.setTokenDate(date.getHours());
                    System.out.println("token: "+account.getToken());
                    System.out.println("token date: "+account.getTokenDate());
                    Manager.writeInJ();
                    Seller.writeInJ();
                    Customer.writeInJ();
                } catch (IOException e) {
                    System.out.println("disconnected");
                    System.exit(0);
                }
            }
        }).start();
    }

    public static void StartListeningOnInputTr(Account account) {
        new Thread(() -> {
            while (true) {
                try {
                    System.out.println("*+"+inputStream.readUTF());
                    account.getTransactions().add(Integer.valueOf(inputStream.readUTF()));
                    System.out.println("tran: "+account.getTransactions().get(account.getTransactions().size()-1));
                    Manager.writeInJ();
                    Seller.writeInJ();
                    Customer.writeInJ();
                } catch (IOException e) {
                    System.out.println("disconnected");
                    System.exit(0);
                }
            }
        }).start();
    }
    /**
     * This method is used to send message with value
     *
     * @param msg to Bank server.
     * @throws IOException when OUT data stream been interrupted.
     */
    public static void SendMessage(String msg) throws IOException {
        try {
            outputStream.writeUTF(msg);
        } catch (IOException e) {
            throw new IOException("Exception while sending message:");
        }
      //  System.out.println(inputStream.readUTF());
       // return inputStream.readUTF();
    }
    public static void startRegister(String start, Account account) throws IOException {
        try {
            ConnectToBankServer();
            StartListeningOnInputRe(account);
            // Scanner scanner = new Scanner(System.in);
            //while (true) {
            SendMessage(start);
            // }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void startTran(String start, Account account) throws IOException {
        try {
            ConnectToBankServer();
            StartListeningOnInputTr(account);
            // Scanner scanner = new Scanner(System.in);
            //while (true) {
            SendMessage(start);
            // }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void startLogin(String start, Account account) throws IOException {
        try {
            ConnectToBankServer();
            StartListeningOnInputLo(account);
            // Scanner scanner = new Scanner(System.in);
            //while (true) {
            SendMessage(start);
            // }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    public static void start(String start) throws IOException {
        try {
            ConnectToBankServer();
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

    /**
     * This method is used to illustrate an example of using methods of this class.
     */
    public static void main(String[] args) {
        try {
            ConnectToBankServer();
            StartListeningOnInput();
            Scanner scanner = new Scanner(System.in);
            while (true) {
                SendMessage(scanner.nextLine());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }


}
