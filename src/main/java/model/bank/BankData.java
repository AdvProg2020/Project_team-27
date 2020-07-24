package model.bank;

import model.accounts.Account;
import model.firms.Firm;
import model.productRelated.Category;
import model.productRelated.Product;
import model.productRelated.ProductStatus;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class BankData {

    private static Connection connection;
    private static Statement statement;

    static String Burl = "jdbc:sqlite:src/main/java/model/Bank/BankDb.db";



    private static void connectBankData(String sqls) {
        try {
            if (new File("BankDb.db").exists()) {
              createBankAc();
              createBankTr();
            }
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(Burl);
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            String sql = sqls;
            statement.executeUpdate(sql);
            statement.close();
            connection.commit();
            connection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Records created successfully");
    }

    private static void createBankAc(){
        String sql =
                "   create table BANK_ACCOUNT (\n" +
                        "ID TEXT constraint PRODUCT_pk primary key,\n" +
                        "USER TEXT,\n" +
                        "NAME TEXT,\n" +
                        "LASTNAME TEXT,\n" +
                        "PASS TEXT,\n" +
                        "MONEY REAL default 500,\n" +
                        "TOKEN TEXT,\n" +
                        "TOKEN_DATE REAL)";
        connectDatas(sql);
    }

    private static void createBankTr(){
        String sql =
                "   create table TRANSACTION (\n" +
                        "ID TEXT constraint PRODUCT_pk primary key,\n" +
                        "PAID NUMERIC default false,\n" +
                        "MONEY REAL,\n" +
                        "DESCRIPTION TEXT,\n" +
                        "DEST TEXT,\n" +
                        "SOURCE TEXT,\n" +
                        "TYPE TEXT)";
        connectDatas(sql);
    }

    public static void insert(BankAccount bankAccount) {
        String sql = "INSERT INTO BANK_ACCOUNT (ID,NAME,LASTNAME,USER, PASS)" +
                "VALUES ('" + bankAccount.getId() + "','" + bankAccount.getName() + "','" + bankAccount.getLastname() + "','" + bankAccount.getUsername() + "', '" + bankAccount.getPassword() + "')";
        connectBankData(sql);
    }

    public static void insertTransaction(Transaction transaction) {
        String sql;
        if(transaction.getDescription() != null) {
             sql = "INSERT INTO TRANSACTION (TYPE,SOURCE,DEST,MONEY,DESCRIPTION,ID)" +
                    "VALUES ('" + transaction.getReceiptType() + "','" + transaction.getSourceAccountID() + "','" + transaction.getDestAccountID() + "','" + transaction.getMoney() + "', '" + transaction.getDescription() + "', '" + transaction.getId() + "')";
        }else {
            sql = "INSERT INTO TRANSACTION (TYPE,SOURCE,DEST,MONEY,ID)" +
                    "VALUES ('" + transaction.getReceiptType() + "','" + transaction.getSourceAccountID() + "','" + transaction.getDestAccountID() + "','" + transaction.getMoney() + "', '" + transaction.getId() + "')";

        }
        connectBankData(sql);
    }

    public static void insertTok(BankAccount bankAccount) {
        String sql = "INSERT INTO BANK_ACCOUNT (ID,NAME,LASTNAME,USER, PASS,TOKEN,TOKEN_DATE)" +
                "VALUES ('" + bankAccount.getId() + "','" + bankAccount.getName() + "','" + bankAccount.getLastname() + "','" + bankAccount.getUsername()
                + "', '" + bankAccount.getPassword() + "', '" + bankAccount.getToken() + "', '" + bankAccount.getTokenDate() + "')";
        connectBankData(sql);
    }

    public static void insertToken(BankAccount bankAccount) {
        String sql = "UPDATE BANK_ACCOUNT set TOKEN = " + bankAccount.getToken() + " where ID=" + bankAccount.getId()  ;
        connectBankData(sql);
    }

    public static void deleteAccount(BankAccount account) {
        String sql = "DELETE from BANK_ACCOUNT where ID = '" + account.getId() + "'";
        connectBankData(sql);
    }

    public static void deleteTran(Transaction account) {
        String sql = "DELETE from TRANSACTION where ID = '" + account.getId() + "'";
        connectBankData(sql);
    }

    public static void insertTokenDate(BankAccount bankAccount) {
        String sql = "UPDATE BANK_ACCOUNT set TOKEN_DATE = " + bankAccount.getTokenDate() + " where ID=" + bankAccount.getId();
        connectBankData(sql);
    }


    public static void updatePay(Transaction transaction) {
        String sql = "UPDATE BANK_ACCOUNT set PAID = '" + "TRUE" + "' where ID='" + transaction.getId() + "'";

        connectBankData(sql);
    }



    public static void getAcc() {
        try {
            if (new File("BankDb.db").exists()) {
                createBankAc();
                createBankTr();
            }
            connection = DriverManager.getConnection(Burl);
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM BANK_ACCOUNT;");
            while (resultSet.next()) {
                String id = resultSet.getString("ID");
                String user = resultSet.getString("USER");
                String name = resultSet.getString("NAME");
                String last = resultSet.getString("LASTNAME");
                String pass = resultSet.getString("PASS");
                int mon = resultSet.getInt("MONEY");
                String tok = resultSet.getString("TOKEN");
                double token_date = resultSet.getDouble("TOKEN_DATE");
                BankAccount product;
                if (BankAccount.isThereAccountWithid(id)) {
                    product = BankAccount.getAccountWithid(id);
                } else {
                    product = new BankAccount(user,name,last,pass);
                }
                product.setMoney((int) mon);
                if (tok != null) {
                    product.setToken(tok);
                }
                if (token_date != 0)
                    product.setTokenDate((long) token_date);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Operation done successfully");
    }

    public static void getATr() {
        try {
            if (new File("BankDb.db").exists()) {
                createBankAc();
                createBankTr();
            }
            connection = DriverManager.getConnection(Burl);
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM TRANSACTION;");
            while (resultSet.next()) {
                String id = resultSet.getString("ID");
                String type = resultSet.getString("TYPE");
                String source = resultSet.getString("SOURCE");
                String dest = resultSet.getString("DEST");
                double money = resultSet.getDouble("MONEY");
                String description = resultSet.getString("DESCRIPTION");
                boolean paid = resultSet.getBoolean("PAID");

                Transaction transaction;
                if (Transaction.isThereAccountWithId(id)) {
                    transaction = Transaction.getAccountWithid(id);
                } else {
                    transaction = new Transaction(source,dest, (int) money,type);
                }
                transaction.setPaid(paid);
                if (description != null) {
                    transaction.setDescription(description);
                }
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Operation done successfully");
    }


    private static void connectDatas(String sqls) {
        try {

            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(Burl);
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            String sql = sqls;
            statement.executeUpdate(sql);
            statement.close();
            connection.commit();
            connection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Records created successfully");
    }
}
