package model.Bank;

import javafx.scene.chart.PieChart;
import model.data.DataBase;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;
import java.util.UUID;

public class Bank {
     static boolean start = true;


    public static void main(String[] args) throws IOException {
        ServerSocket bankServer = new ServerSocket(9595);
        Socket socket = bankServer.accept();
        DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        System.out.println("hiiii");
        new handleClient(socket, dataInputStream, dataOutputStream, bankServer, socket).run();
    }


    static class handleClient extends Thread {
        private static ServerSocket serverSocket;
        private static DataInputStream dataInputStream;
        private static DataOutputStream dataOutputStream;
        private static Socket clientServer;
        private static String output;


        public handleClient(Socket clientServer, DataInputStream dataInputStream, DataOutputStream dataOutputStream, ServerSocket serverSocket,Socket socket) {
            this.clientServer = clientServer;
            this.dataInputStream = dataInputStream;
            this.dataOutputStream = dataOutputStream;
            this.serverSocket = serverSocket;
            this.clientServer = socket;
        }

        @Override
        public void run() {
            String input = null;
            Scanner scanner = null;
            try {
                scanner = new Scanner(dataInputStream.readUTF());
            } catch (IOException e) {
                e.printStackTrace();
            }

            while (true) {
               if (scanner.hasNext()) {
                   input = scanner.nextLine();
                   String[] inputs = input.split("\\s+");
                   try {
                   if (input.startsWith("create_account")) {
                           account(inputs);
                   } else if (input.startsWith("get_token")) {
                       token(inputs);
                   } else if (input.startsWith("create_receipt")) {
                       receipt(input);
                   } else if (input.startsWith("get_transactions")) {
                       transaction(inputs);
                   } else if (input.startsWith("pay")) {
                       pay(inputs);
                   } else if (input.startsWith("get_balance")) {
                       balance(inputs);
                   } else if (input.startsWith("exit")) {
                       output="bye";
                       handleOutput();
                       break;
                   }
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
               }

            }
        }

        private void token(String[] inputs) throws IOException {
            String username = inputs[1];
            System.out.println(username);
            if (!BankAccount.isThereAccountWithUsername(username)) {
                if (BankAccount.isThereAccountWithUsernameAndPassword(username, inputs[2])) {
                    BankAccount bankAccount = BankAccount.getAccountWithUsername(username);
                    String uniqueID = UUID.randomUUID().toString();
                    bankAccount.setToken(uniqueID);
                    Date date = new Date();
                    bankAccount.setTokenDate(date.getTime());
                    BankAccount.writeInJ();
                  //  DataBase.insertToken(bankAccount);
                    output = bankAccount.getToken();
                    System.out.println(output);
                } else output = "invalid username or password";
            } else output = "invalid username or password";
            handleOutput();
        }

        private void account(String[] inputs) throws IOException {
            String username = inputs[3];
            if (!BankAccount.isThereAccountWithUsername(username)) {
                if (inputs[4].equals(inputs[5])) {
                    BankAccount bankAccount = new BankAccount(username, inputs[1], inputs[2], inputs[4]);
                    output = bankAccount.getId();
                   // DataBase.insert(bankAccount);
                } else output = "passwords do not match";
            } else output = "username is not available";
            handleOutput();
        }

        private void receipt(String input) {
            String[] inputs = input.split("\\s+");
            String token = inputs[1];
            String type = inputs[2];
            String money = inputs[3];
            String source = inputs[4];
            String dest = inputs[5];
            if (!input.contains("*")) {
                if (!(inputs.length == 7 || inputs.length == 6)) {
                    if (type.matches("(?i)(?:deposit|withdraw|move)")) {
                        if (money.matches("\\d+")) {
                            if (source.equals(dest)) {
                                if (BankAccount.isThereAccountWithSource(source)) {
                                    if (BankAccount.isThereAccountWithSource(dest)) {
                                        if (type.equals("move") && (source.equals(-1) || dest.equals(-1))) {
                                            Date date = new Date();
                                            if (BankAccount.getAccountWithid(token).getTokenDate() - date.getTime() < 3600000) {
                                                if (!source.equals(-1)) {
                                                    if (BankAccount.getAccountWithid(source).getToken().equals(token)) {
                                                        Transaction transaction = new Transaction(source, dest, Long.parseLong(money), inputs[6], type);
                                                       // DataBase.insertTransaction(transaction);
                                                        output = transaction.getId();
                                                    } else output = "token expired";
                                                } else {
                                                    Transaction transaction = new Transaction(source, dest, Long.parseLong(money), inputs[6], type);
                                                   // DataBase.insertTransaction(transaction);
                                                    output = transaction.getId();
                                                }
                                            } else output = "token is invalid";
                                        } else output = "invalid account id";
                                    } else output = "dest account id is invalid";
                                } else output = "source account id is invalid";
                            } else output = "equal source and dest account";
                        } else output = "invalid money";
                    } else output = "invalid receipt type";
                } else output = "invalid parameters passed";
            } else output = "your input contains invalid characters";
            handleOutput();

        }

        private void transaction(String[] inputs) {
            String token = inputs[1];
            String type = inputs[2];
            Date date = new Date();
            if (Transaction.isThereAccountWithId(token)) {
                if (BankAccount.getAccountWithid(token).getTokenDate() - date.getTime() < 3600000) {
                    checkType(type);
                } else output = "token expired";
            } else output = "token is invalid";

        }

        private void checkType(String type) {
            if (type.equals("-")) {

            } else if (type.equals("*")) {

            } else if (type.equals("+")) {

            } else if (Transaction.isThereAccountWithId(type)) {

            } else output = "invalid receipt id";
            handleOutput();
        }

        private void pay(String[] inputs) {
            String id = inputs[1];
            if (Transaction.isThereAccountWithId(id)) {
                Transaction transaction = Transaction.getAccountWithid(id);
                if (!transaction.isPaid()) {
                    payReceipt(transaction);
                } else output = "”receipt is paid before";
            } else output = "invalid receipt id";
        }

        private void payReceipt(Transaction transaction) {
            String type = transaction.getReceiptType();
            if (type.equalsIgnoreCase("move")) {
                BankAccount account1 = BankAccount.getAccountWithid(transaction.getSourceAccountI());
                BankAccount account2 = BankAccount.getAccountWithid(transaction.getDestAccountID());
                double money = transaction.getMoney();
                if(money <= account1.getMoney()) {
                    account1.setMoney(account1.getMoney() - money);
                    account2.setMoney(account2.getMoney() + money);
                    transaction.setPaid(true);
                   // DataBase.updatePay();
                    output ="done successfully";
                }else output = "source account does not have enough money";
            } else if (type.equalsIgnoreCase("”deposit")) {
                BankAccount account2 = BankAccount.getAccountWithid(transaction.getDestAccountID());
                double money = transaction.getMoney();
                account2.setMoney(account2.getMoney() + money);
                transaction.setPaid(true);
               // DataBase.updatePay();
                output ="done successfully";
            } else if (type.equalsIgnoreCase("withdraw")) {
                BankAccount account1 = BankAccount.getAccountWithid(transaction.getSourceAccountI());
                double money = transaction.getMoney();
                if(money <= account1.getMoney()) {
                    account1.setMoney(account1.getMoney() - money);
                    transaction.setPaid(true);
                   // DataBase.updatePay();
                    output ="done successfully";
                }else output = "source account does not have enough money";
            }
            handleOutput();
        }

        private void balance(String[] inputs) {
            String token = inputs[1];
            Date date = new Date();
            if (BankAccount.isThereAccountWithToken(token)) {
                if (BankAccount.getAccountWithid(token).getTokenDate() - date.getTime() < 3600000) {
                    output = String.valueOf(BankAccount.getAccountWithToken(token).getMoney());
                } else output = "”token is invalid";
            } else output = "token expired";
           handleOutput();
        }

        private static void finish() {
            try {
                clientServer.close();
                dataInputStream.close();
                dataOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private  void handleOutput() {
            try {
                dataOutputStream.writeUTF(output);
                dataOutputStream.flush();
             //   finish();
            } catch (IOException e) {
                e.printStackTrace();
                finish();
            }
        }

    }

}
