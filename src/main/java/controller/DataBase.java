package controller;

import java.sql.*;

public class DataBase {
    public static void main(String[] args) {

        Connection connection;
        Statement statement;
        ResultSet resultSet;
        String query = null;
        String url = "jdbc:h2:~/test";

        try {
            //Class.forName(JDBC_DRIVER);
            System.out.println("Connecting to database...");
            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()){

                int id = resultSet.getInt("ID");
                String Name =resultSet.getString("Name");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }



    }

}
