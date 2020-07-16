package model.data;

import java.sql.*;
import java.util.Date;

public class DataBase {
    static final String JDBC_DRIVER = "test";
    static final String DB_URL = "jdbc:h2:~/test";
    static String url = "jdbc:sqlite:C:/Users/SA/IdeaProjects/Project_team/src/main/java/model/Bank/newDatabase.db";


    public static void insertAccount(String id, String name, String lastName, Date birthday, String email,String role, double credit) {
        Connection connection = null;
        Statement statement = null;

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);
            connection.setAutoCommit(false);
            System.out.println("Opened database successfully");

            statement = connection.createStatement();
            String sql = "INSERT INTO ACCOUNT (ID,NAME,LASTNAME,BIRTHDAY,EMAIL,ROLE,CREDIT)"  +
                    "VALUES (id, name, lastName, birthday, email , role, credit)";
            statement.executeUpdate(sql);

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

    public static void getAccount(String args[]) {

        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);
            connection.setAutoCommit(false);
            System.out.println("Opened database successfully");

            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ACCOUNT;");

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");
                float salary = resultSet.getFloat("salary");

                System.out.println(id);

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


    public static void update(String args[]) {

        Connection connection = null;
        Statement statement = null;

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);
            connection.setAutoCommit(false);
            System.out.println("Opened database successfully");

            statement = connection.createStatement();
            String sql = "UPDATE ACCOUNT set CREDIT = 25000.00 where ID=1;";
            statement.executeUpdate(sql);
            connection.commit();

            statement.close();
            connection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Operation done successfully");
    }

    public static void delete(String args[]) {
        Connection connection = null;
        Statement statement = null;

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);
            connection.setAutoCommit(false);
            System.out.println("Opened database successfully");

            statement = connection.createStatement();
            String sql = "DELETE from ACCOUNT where ID=2;";
            statement.executeUpdate(sql);
            connection.commit();

            statement.close();
            connection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Operation done successfully");
    }
}






