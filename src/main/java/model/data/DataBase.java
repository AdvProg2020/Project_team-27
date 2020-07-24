package model.data;


import model.accounts.*;
import model.firms.Firm;
import model.productRelated.Category;
import model.productRelated.Comment;
import model.productRelated.Product;
import model.productRelated.ProductStatus;
import server.Server;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataBase {
    private static Connection connection;
    private static Statement statement;

    static String url = "jdbc:sqlite:src/main/java/model/data/newDatabase.db";


    public static void createAccount() {
        String sql =
                "create table Accounts (\n" +
                        "ID TEXT  primary key,\n" +
                        "NAME TEXT not null,\n" +
                        "LASTNAME TEXT not null,\n" +
                        " ADDRESS TEXT,\n" +
                        " BIRTHDAY DATE,\n" +
                        " EMAIL text,\n" +
                        " PHONE REAL,\n" +
                        " ROLE TEXT,\n" +
                        " CREDIT NUMBER,\n" +
                        "FAST NUMERIC,\n" +
                        "CUR_PHONE REAL,\n" +
                        "FIRM TEXT,\n" +
                        "PASS TEXT)";
        connectDatas(sql);
    }

    public static void createProduct() {
        String sql =
                "   create table PRODUCT (\n" +
                        "ID TEXT constraint PRODUCT_pk primary key,\n" +
                        "NAME TEXT not null,\n" +
                        "PRICE REAL not null,\n" +
                        "NUMBER REAL,\n" +
                        "SELLER TEXT,\n" +
                        "STATUS TEXT,\n" +
                        "FIRM TEXT,\n" +
                        "SALE TEXT,\n" +
                        "IMG TEXT,\n" +
                        "INSALE NUMERIC default FALSE,\n" +
                        "INAUCTION NUMERIC default FALSE,\n" +
                        " CAT TEXT,\n" +
                        "SCORE REAL)";
        connectDatas(sql);
    }

    public static void createFirm() {
        String sql =
                "create table FIRM (\n" +
                        "ID int constraint FIRM_pk primary key,\n" +
                        "PHONE REAL,\n" +
                        "EMAIL TEXT,\n" +
                        " ADDRESS TEXT,\n" +
                        "TYPE TEXT)";
        connectDatas(sql);
    }

    public static void insertFirm(Firm firm) {
        String sql = "INSERT INTO FIRM (ID,PHONE,EMAIL,TYPE,ADDRESS)" +
                "VALUES ('" + firm.getName() + "','" + firm.getPhoneNO() + "','" + firm.getEmail() + "','" + firm.getType() + "','" + firm.getAddress() + "')";
        connectData(sql);
    }

    public static void insertAccount(Account account) {
        if(account.getFirm()!= null) {
            String sql = "INSERT INTO Accounts (ID,NAME,LASTNAME,BIRTHDAY,EMAIL,ROLE,CREDIT, PASS, FIRM)" +
                    "VALUES ('" + account.getUsername() + "','" + account.getName() + "','" + account.getLastname() + "','" + account.getBirthdayDate() + "', '" + account.getEmail() + "', '" + account.getRole() + "', '" + account.getCredit() + "', '" + account.getPassword() + "', '" + account.getFirm().getName() + "')";
            connectData(sql);
        }
        else {
            String sql = "INSERT INTO Accounts (ID,NAME,LASTNAME,BIRTHDAY,EMAIL,ROLE,CREDIT, PASS)" +
                    "VALUES ('" + account.getUsername() + "','" + account.getName() + "','" + account.getLastname() + "','" + account.getBirthdayDate() + "', '" + account.getEmail() + "', '" + account.getRole() + "', '" + account.getCredit() + "', '" + account.getPassword() + "')";
            connectData(sql);
        }
    }

    public static void insertAddress(Account account) {
        String sql = "UPDATE Accounts set ADDRESS = '" + account.getAddress() + "' where ID = '" + account.getUsername() + "'" +
                "UPDATE Accounts set CUR_PHONE = '" + account.getCurrentPhoneNo() + "' where ID = '" + account.getUsername() + "'";
        connectData(sql);
    }

    public static void deleteAccount(Account account) {
        String sql = "DELETE from Accounts where ID = '" + account.getUsername() + "'";
        connectData(sql);
    }

    public static void deleteFirm(Firm account) {
        String sql = "DELETE from FIRM where ID = '" + account.getName() + "'";
        connectData(sql);
    }
    public static void insertProduct(Product product) {


        String sql = "INSERT INTO PRODUCT (ID,NAME,PRICE,NUMBER,SELLER,STATUS,ADDITIONAL, SCORE,CAT,FIRM,SALE,IMG)" +
                "VALUES ('" + product.getId() + "','" + product.getProductName() + "','" + product.getPrice() + "','" + product.getNumberOfProducts() + "', '" +
                product.getSeller() + "', '" + product.getProductStatus() + "', '" + product.getAdditionalDetail() + "', '" + product.getScore() + "', '" + product.getCategory().getName() +
                "', '" + product.getFirm().getName() + "', '" + product.getSale() + "', '" + product.getProductImage() + "')";
        connectData(sql);
    }


    public static void deleteProduct(Product product) {
        String sql = "DELETE from PRODUCT where ID = '" + product.getId() + "'";
        connectData(sql);
    }

    public static void getProduct() {
        try {
            connection = DriverManager.getConnection(url);
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM PRODUCT;");
            while (resultSet.next()) {
                String id = resultSet.getString("ID");
                String name = resultSet.getString("NAME");
                double price = resultSet.getDouble("PRICE");
                int number = resultSet.getInt("NUMBER");
                String seller = resultSet.getString("SELLER");
                String status = resultSet.getString("STATUS");
                String additional = resultSet.getString("ADDITIONAL");
                double score = resultSet.getDouble("SCORE");
                String cat = resultSet.getString("CAT");
                String firm = resultSet.getString("FIRM");
                String sale = resultSet.getString("SALE");
                String img = resultSet.getString("IMG");
                Boolean inSale = resultSet.getBoolean("INSALE");
                Boolean inauction = resultSet.getBoolean("INAUCTION");
                Product product;
                if (Product.isThereProductWithId(id)) {
                    product = Product.getProductById(id);
                } else {
                    product = new Product(id);
                }
                product.setCategory(Category.getCategoryWithName(cat));
                product.setSale(sale);
                if (firm != null) {
                    product.setFirm(Firm.getFirmWithID(firm));
                }
                if (img != null)
                    product.setProductImage(img);
                product.setInAuction(inauction);
                product.setInSale(inSale);
                product.setAllProduct(id, name, price, seller, ProductStatus.valueOf(status), number, additional, score);


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


    public static void getFirm() {
        try {
            connection = DriverManager.getConnection(url);
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM FIRM;");
            while (resultSet.next()) {
                String id = resultSet.getString("ID");
                String type = resultSet.getString("TYPE");
                double phone = resultSet.getDouble("PHONE");
                String email = resultSet.getString("EMAIL");
                String address = resultSet.getString("ADDRESS");

                Firm firm;
                if (Firm.isThereFirmWithID(id)) {
                    firm = Firm.getFirmWithID(id);
                } else {
                    firm = new Firm(id);
                }
                firm.setDetailToFirm(phone, address, email);
                firm.setType(type);

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


    public static void getAccount() {
        try {
            connection = DriverManager.getConnection(url);
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Accounts;");
            while (resultSet.next()) {
                String role = resultSet.getString("Role");
                String id = resultSet.getString("ID");
                String name = resultSet.getString("NAME");
                String last = resultSet.getString("LASTNAME");
                String pass = resultSet.getString("PASS");
                String address = resultSet.getString("ADDRESS");
                String birthh = resultSet.getString("BIRTHDAY");
                String email = resultSet.getString("EMAIL");
                double phone = resultSet.getDouble("PHONE");
                boolean fast = resultSet.getBoolean("FAST");
                double cur_phone = resultSet.getDouble("CUR_PHONE");
                double credit = resultSet.getDouble("CREDIT");
                Customer customer;
                Manager manager;
                Seller seller;
                Supporter supporter;
                if (role.equalsIgnoreCase("customer")) {
                    if (Account.isThereAccountWithUsername(id)) {
                        customer = Customer.getCustomerWithUsername(id);
                    } else {
                        customer = new Customer(id);
                    }
                    customer.setAllAccount(id, name, last, pass, email, phone, credit, cur_phone, address, birthh, fast);
                } else if (role.equalsIgnoreCase("manager")) {
                    if (Manager.isThereAccountWithUsername(id)) {
                        manager = (Manager) Manager.getAccountWithUsername(id);
                    } else {
                        manager = new Manager(id);
                    }
                    manager.setAllAccount(id, name, last, pass, email, phone, credit, cur_phone, address, birthh, fast);
                } else if (role.equalsIgnoreCase("seller")) {
                    if (Seller.isThereAccountWithUsername(id))
                        seller = Seller.getSellerWithUsername(id);
                    else
                        seller = new Seller(id);
                    String firm = resultSet.getString("FIRM");
                    seller.setFirm(Firm.getFirmWithID(firm));
                    seller.setAllAccount(id, name, last, pass, email, phone, credit, cur_phone, address, birthh, fast);
                } else if (role.equalsIgnoreCase("supporter")) {
                    if(Supporter.isThereAccountWithUsername(id))
                        supporter = (Supporter) Supporter.getAccountWithUsername(id);
                    else

                    supporter = new Supporter(id);
                    supporter.setAllAccount(id, name, last, pass, email, phone, credit, cur_phone, address, birthh, fast);
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


    private static void connectData(String sqls) {
        try {
            if (new File("newDatabase.db").exists()) {
                createAccount();
                createProduct();
                createFirm();
            }
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);
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

    private static void connectDatas(String sqls) {
        try {

            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);
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






