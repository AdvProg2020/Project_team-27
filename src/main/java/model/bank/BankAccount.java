package model.bank;

import client.view.FileHandling;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Random;

public class BankAccount {
    String id;
    String username = null;
    String name = null;
    String lastname = null;
    String password = null;
    int money = 500;
    String token;
    long tokenDate;
    static ArrayList<BankAccount> allBankAccount = new ArrayList();
    public static Type bankAccountType = new TypeToken<ArrayList<BankAccount>>() {
    }.getType();

    public BankAccount(String username, String name, String lastname, String password) throws IOException {
        this.username = username;
        this.name = name;
        this.lastname = lastname;
        this.password = password;
        Random rand = new Random();
        id =  String.valueOf(rand.nextInt(10000));
        allBankAccount.add(this);
        writeInJ();
    }

    public static boolean isThereAccountWithUsernameAndPassword(String username, String password) {
        for (BankAccount account : allBankAccount) {
            if (account.username.equalsIgnoreCase(username) && account.password.equals(password)) return true;
        }
        return false;
    }

    public static boolean isThereAccountWithUsername(String username) {
        for (BankAccount account : allBankAccount) {
            if (account.username.equalsIgnoreCase(username)) return true;
        }
        return false;
    }
    public static boolean isThereAccountWithid(String username) {
        for (BankAccount account : allBankAccount) {
            if (account.id.equalsIgnoreCase(username)) return true;
        }
        return false;
    }
    public static boolean isThereAccountWithToken(String username) {
        for (BankAccount account : allBankAccount) {
            if(account.token != null) {
                if (account.token.equalsIgnoreCase(username)) return true;
            }
        }
        return false;
    }
    public static boolean isThereAccountWithSource(String username) {
        for (BankAccount account : allBankAccount) {
            if (account.id.equalsIgnoreCase(username)) return true;
        }
        return false;
    }

    public static BankAccount getAccountWithUsername(String username) {
        for (BankAccount account : allBankAccount) {
            if (account.username.equalsIgnoreCase(username)) return account;
        }
        return null;
    }
    public static BankAccount getAccountWithid(String username) {
        for (BankAccount account : allBankAccount) {
            if (account.id.equalsIgnoreCase(username)) return account;
        }
        return null;
    }

    public static BankAccount getAccountWithToken(String username) {
        for (BankAccount account : allBankAccount) {
            if(account.token != null) {
                if (account.token.equalsIgnoreCase(username)) return account;
            }
        }
        return null;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTokenDate() {
        return tokenDate;
    }

    public void setTokenDate(long tokenDate) throws IOException {
        this.tokenDate = tokenDate;
        writeInJ();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) throws IOException {
        this.token = token;
        writeInJ();

    }

    public static void setAllBankAccount(ArrayList<BankAccount> allBankAccount) {
        BankAccount.allBankAccount = allBankAccount;
    }

    public static void writeInJ() throws IOException {

        String json = FileHandling.getGson().toJson(BankAccount.allBankAccount, bankAccountType);
        FileHandling.writeInFile(json, "bankAccount.json");

    }

    public static ArrayList<BankAccount> getAllBankAccount() {
        return allBankAccount;
    }
}
