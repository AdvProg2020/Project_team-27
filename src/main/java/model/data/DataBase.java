package model.data;

import model.Bank.BankAccount;
import model.Bank.Transaction;
import model.accounts.Account;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DataBase {

    static String Burl = "jdbc:sqlite:C:/Users/SA/IdeaProjects/Project_team/src/main/java/model/Bank/BankDb.db";
    static String url = "jdbc:sqlite:C:/Users/SA/IdeaProjects/Project_team/src/main/java/model/data/newDatabase.db";

    public static void insertAccount(Account account) {
        Connection connection = null;
        Statement statement = null;

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);
            connection.setAutoCommit(false);
            System.out.println("Opened database successfully");

            statement = connection.createStatement();

            String sql = "INSERT INTO ACCOUNT (ID,NAME,LASTNAME,BIRTHDAY,EMAIL,ROLE,CREDIT, PASS)"  +
                    "VALUES ('"+account.getUsername()+"','"+ account.getName()+"','"+ account.getLastname()+"','"+ account.getBirthdayDate()+"', '"+account.getEmail()+"', '"+account.getRole()+"', '"+account.getCredit()+"', '"+account.getPassword()+"')";
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

    public static void insert(BankAccount bankAccount) {
        Connection connection = null;
        Statement statement = null;

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(Burl);
            connection.setAutoCommit(false);
            System.out.println("Opened database successfully");

            statement = connection.createStatement();
            String sql = "INSERT INTO BANK_ACCOUNT (ID,NAME,LASTNAME,USER, PASS)"  +
                    "VALUES ('"+bankAccount.getId()+"','"+ bankAccount.getName()+"','"+ bankAccount.getLastname()+"','"+ bankAccount.getUsername()+"', '"+bankAccount.getPassword()+"')";
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

    public static void insertToken(BankAccount bankAccount) {
        Connection connection = null;
        Statement statement = null;

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(Burl);
            connection.setAutoCommit(false);
            System.out.println("Opened database successfully");

            statement = connection.createStatement();
            String sql = "UPDATE BANK_ACCOUNT set TOKEN = '"+bankAccount.getToken()+"' where ID='"+bankAccount.getId()+"' ;+UPDATE BANK_ACCOUNT set TOKEN_DATE = '"+bankAccount.getTokenDate()+"' where ID='"+bankAccount.getId()+"'";

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


    public static void updatePay() {
        Connection connection = null;
        Statement statement = null;

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(Burl);
            connection.setAutoCommit(false);
            System.out.println("Opened database successfully");

            statement = connection.createStatement();
                String sql = "INSERT INTO Transaction (PAID)"  +
                    "VALUES (TRUE )";
            statement.executeUpdate(sql);

            // statement.executeUpdate(sql);
            statement.close();
            connection.commit();
            connection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Records created successfully");
    }


    public static void insertTransaction(Transaction transaction) {
        Connection connection = null;
        Statement statement = null;

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(Burl);
            connection.setAutoCommit(false);
            System.out.println("Opened database successfully");

            statement = connection.createStatement();
            String sql = "INSERT INTO Transaction (TYPE,SOURCE,DEST,MONEY,DESCRIPTION,ID)"  +
                    "VALUES ('"+transaction.getReceiptType()+"','"+ transaction.getSourceAccountI()+"','"+ transaction.getDestAccountID()+"','"+ transaction.getMoney()+"', '"+transaction.getDescription()+"', '"+transaction.getId()+"')";
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






