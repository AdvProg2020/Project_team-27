package model.bank;

import client.view.FileHandling;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Bank {

    public static Type transactionType = new TypeToken<ArrayList<Transaction>>() {
    }.getType();
    public static void main(String[] args) throws IOException {
        gson();
        new Bank.BankImp().run();
    }

    private static void gson() throws IOException {
        Type bankType = new TypeToken<ArrayList<BankAccount>>() {
        }.getType();
        try {
            JsonReader reader99 = new JsonReader(new FileReader("bankAccount.json"));
            ArrayList<BankAccount> bankAccountArrayList = FileHandling.getGson().fromJson(reader99, bankType);
            if (null == bankAccountArrayList) {
                bankAccountArrayList = new ArrayList<>();
            }
            BankAccount.setAllBankAccount(bankAccountArrayList);
            for (BankAccount bankAccount : BankAccount.getAllBankAccount()) {
                // System.out.println(bankAccount);
            }

        } catch (IOException e) {
            FileHandling.writeInFile("", "bankAccount.json");
            BankAccount.setAllBankAccount(new ArrayList<>());
        }


        Type transactionType = new TypeToken<ArrayList<Transaction>>() {
        }.getType();
        try {
            JsonReader reader100 = new JsonReader(new FileReader("transaction.json"));
            ArrayList<Transaction> transactionArrayList = FileHandling.getGson().fromJson(reader100, transactionType);
            if (null == transactionArrayList) {
                transactionArrayList = new ArrayList<>();
            }
            Transaction.setAllTransactions(transactionArrayList);
        } catch (IOException e) {
            FileHandling.writeInFile("", "transaction.json");
            Transaction.setAllTransactions(new ArrayList<>());
        }
    }

    static class BankImp {
        String output;
        private static DataInputStream dataInputStream;
        private static DataOutputStream dataOutputStream;

        public void run() throws IOException {
            ServerSocket bankServer = new ServerSocket(9595);
            System.out.println("connect to bank");
            while (true) {
                try {
                    // System.out.println("client...");
                    Socket socket = bankServer.accept();
                    // System.out.println("client accept");
                    dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                    dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                    new handleClient(socket, dataInputStream, dataOutputStream, bankServer, socket, this).start();
                } catch (Exception e) {
                    System.out.println("error accepting");
                }
            }
        }


        void token(String[] inputs) throws IOException {
            String username = inputs[1];
            if (BankAccount.isThereAccountWithUsername(username)) {
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

        void account(String[] inputs) throws IOException {
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

        void receipt(String input) throws IOException {
            String[] inputs = input.split("\\s+");
            String token = inputs[1];
            String type = inputs[2];
            String money = inputs[3];
            int source = Integer.parseInt(inputs[4]);
            String dest = inputs[5];
            if (!input.contains("*")) {
                if ((inputs.length == 7 || inputs.length == 6)) {
                    if (type.matches("(?i)(?:deposit|withdraw|move)")) {
                        if (money.matches("\\d+")) {
                            if (source!= Integer.parseInt(dest)) {
                                if (source == -1 || BankAccount.isThereAccountWithSource(String.valueOf(source))) {
                                    if (source== -1 ||BankAccount.isThereAccountWithSource(dest)) {
                                        if (!(type.equals("move") && (source==(-1) && dest.equals(-1)))) {
                                            Date date = new Date();
                                            if (BankAccount.getAccountWithToken(token).getTokenDate() - date.getTime() < 3600000) {
                                                if (source!=(-1)) {
                                                    if (BankAccount.getAccountWithid(String.valueOf(source)).getToken().equals(token)) {
                                                        Transaction transaction = new Transaction(String.valueOf(source), dest, Integer.parseInt(money), type);
                                                        if(inputs.length == 7){
                                                            transaction.setDescription(inputs[6]);
                                                        }
                                                        // DataBase.insertTransaction(transaction);
                                                        output = transaction.getId();
                                                    } else output = "token expired";
                                                } else {
                                                    Transaction transaction = new Transaction(String.valueOf(source), dest, Integer.parseInt(money), type);
                                                    if(inputs.length == 7){
                                                        transaction.setDescription(inputs[6]);
                                                    }
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

        void transaction(String[] inputs) {
            String token = inputs[1];
            String type = inputs[2];
            Date date = new Date();
            if (BankAccount.isThereAccountWithToken(token)) {
                if (BankAccount.getAccountWithToken(token).getTokenDate() - date.getTime() < 3600000) {
                    checkType(type);
                } else output = "token expired";
            } else output = "token is invalid";

        }

        void checkType(String type) {
            String starJson = "";
            if (type.equals("-")) {
                starJson =  makeJsonWithStar(separateTransactionWithNegative());
            } else if (type.equals("*")) {
                starJson =  makeJsonWithStar(Transaction.getAllTransactions());
            } else if (type.equals("+")) {
                starJson = makeJsonWithStar(separateTransactionWithPlus());
            } else if (Transaction.isThereAccountWithId(type)) {
                starJson = makeObjJson(getTransactionWithId(type));
            } else output = "invalid receipt id";
            output = starJson;
            handleOutput();
        }

        public ArrayList<Transaction> separateTransactionWithPlus(){
            ArrayList<Transaction> list = new ArrayList<>();
            for (Transaction transaction : Transaction.getAllTransactions()) {
                if (transaction.getSourceAccountID().equals("-1")){
                    list.add(transaction);
                }
            }
            return null;
        }

        public ArrayList<Transaction> separateTransactionWithNegative(){
            ArrayList<Transaction> list = new ArrayList<>();
            for (Transaction transaction : Transaction.getAllTransactions()) {
                if (transaction.getDestAccountID().equals("-1")){
                    list.add(transaction);
                }
            }
            return null;
        }

        public Transaction getTransactionWithId(String type){
            for (Transaction transaction : Transaction.getAllTransactions()) {
                if (transaction.getId().equals(type)){
                    return transaction;
                }
            }
            return null;
        }

        public String  makeJsonWithStar(ArrayList<Transaction> list){
            String json = FileHandling.getGson().toJson(list, transactionType);
            json.replaceAll("},\\{","}\\*\\{");
            return json;
        }

        public String makeObjJson(Transaction transaction){
            String json = FileHandling.getGson().toJson(transaction, Transaction.class);
            return json;
        }


        void pay(String[] inputs) throws IOException {
            String id = inputs[1];
            if (Transaction.isThereAccountWithId(id)) {
                Transaction transaction = Transaction.getAccountWithid(id);
                if (!transaction.isPaid()) {
                    payReceipt(transaction);
                } else output = "”receipt is paid before";
            } else output = "invalid receipt id";
        }

        void payReceipt(Transaction transaction) throws IOException {
            String type = transaction.getReceiptType();
            if (type.equalsIgnoreCase("move")) {
                BankAccount account1 = BankAccount.getAccountWithid(transaction.getSourceAccountID());
                BankAccount account2 = BankAccount.getAccountWithid(transaction.getDestAccountID());
                int money = (int) transaction.getMoney();
                if (money <= account1.getMoney()) {
                    account1.setMoney(account1.getMoney() - money);
                    account2.setMoney(account2.getMoney() + money);
                    transaction.setPaid(true);
                    // DataBase.updatePay();
                    output = "done successfully";
                } else output = "source account does not have enough money";
            } else if (type.equalsIgnoreCase("deposit")) {
                BankAccount account2 = BankAccount.getAccountWithid(transaction.getDestAccountID());
                int money = (int) transaction.getMoney();
                account2.setMoney(account2.getMoney() + money);
                transaction.setPaid(true);
                // DataBase.updatePay();
                output = "done successfully";
            } else if (type.equalsIgnoreCase("withdraw")) {
                BankAccount account1 = BankAccount.getAccountWithid(transaction.getSourceAccountID());
                int money = (int) transaction.getMoney();
                if (money <= account1.getMoney()) {
                    account1.setMoney((int) (account1.getMoney() - money));
                    transaction.setPaid(true);
                    // DataBase.updatePay();
                    output = "done successfully";
                } else output = "source account does not have enough money";
            }
            Transaction.writeInJ();
            handleOutput();
        }

        void balance(String[] inputs) {
            String token = inputs[1];
            Date date = new Date();
            if (BankAccount.isThereAccountWithToken(token)) {
                BankAccount bankAccount = BankAccount.getAccountWithToken(token);
                if (bankAccount.getTokenDate() - date.getTime() < 3600000) {
                    output = String.valueOf(BankAccount.getAccountWithToken(token).getMoney());
                } else output = "”token is invalid";
            } else output = "token expired";
            handleOutput();
        }

        static void finish() {
            try {

                dataInputStream.close();
                dataOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        void handleOutput() {
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

    static class handleClient extends Thread {
        private static ServerSocket serverSocket;
        private static DataInputStream dataInputStream;
        private static DataOutputStream dataOutputStream;
        private static Socket clientServer;
        private static String output;
        private static Bank.BankImp bankImp;


        public handleClient(Socket clientServer, DataInputStream dataInputStream, DataOutputStream dataOutputStream, ServerSocket serverSocket, Socket socket, Bank.BankImp bankImp) {
            this.dataInputStream = dataInputStream;
            this.dataOutputStream = dataOutputStream;
            this.serverSocket = serverSocket;
            this.clientServer = clientServer;
            this.bankImp = bankImp;
        }

        @Override
        public void run() {
            String input = null;
            // Scanner scanner = null;
            // outer:
            while (true) {
                try {
                    // scanner = new Scanner(dataInputStream.readUTF());
                    input = dataInputStream.readUTF();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // if (scanner.hasNext()) {
                //   input = scanner.nextLine();
                String[] inputs = input.split("\\s+");
                try {
                    System.out.println(input);
                    if (input.startsWith("create_account")) {
                        bankImp.account(inputs);
                    } else if (input.startsWith("get_token")) {
                        bankImp.token(inputs);
                    } else if (input.startsWith("create_receipt")) {
                        bankImp.receipt(input);
                    } else if (input.startsWith("get_transactions")) {
                        bankImp.transaction(inputs);
                    } else if (input.startsWith("pay")) {
                        bankImp.pay(inputs);
                    } else if (input.startsWith("get_balance")) {
                        bankImp.balance(inputs);
                    } else if (input.startsWith("exit")) {
                        output = "bye";
                        bankImp.handleOutput();
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //  }

            }
        }
    }


}