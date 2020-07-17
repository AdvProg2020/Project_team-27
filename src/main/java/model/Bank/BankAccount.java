package model.Bank;

import model.accounts.Account;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class BankAccount {
    String id;
    String username = null;
    String name = null;
    String lastname = null;
    String password = null;
    double money = 500;
    String token;
    long tokenDate;
    static ArrayList<BankAccount> allBankAccount = new ArrayList();

    public BankAccount(String username, String name, String lastname, String password) {
        this.username = username;
        this.name = name;
        this.lastname = lastname;
        this.password = password;
        Random rand = new Random();
        id =  String.valueOf(rand.nextInt(1000));
        allBankAccount.add(this);
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

    public static boolean isThereAccountWithToken(String username) {
        for (BankAccount account : allBankAccount) {
            if (account.token.equalsIgnoreCase(username)) return true;
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
            if (account.token.equalsIgnoreCase(username)) return account;
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

    public void setTokenDate(long tokenDate) {
        this.tokenDate = tokenDate;
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

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
