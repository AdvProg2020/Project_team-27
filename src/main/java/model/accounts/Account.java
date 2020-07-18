package model.accounts;


import model.firms.Firm;

import java.io.IOException;
import java.util.*;

public abstract class Account {
    String username = null;
    String name = null;
    String lastname = null;
    String password = null;
    String email = null;
    double phoneNo = 0;
    double credit = 10000;
    String role = null;
    double currentPhoneNo = 0;
    String address = null;
    Date birthdayDate = null;
    public Firm firm = null;
    boolean fast = false;
    String imageId;
    String token;
    long tokenDate;
    String accountId = null;
    int bankMoney = 50;
    private String online = "off";
    private  ArrayList<String> transactions = new ArrayList<>();

    private static ArrayList<Account> allAccounts = new ArrayList<>();
    private static ArrayList<Date> birthdayDates = new ArrayList<>();
//    public static Type AccountType = new TypeToken<ArrayList<Account>>() {
//    }.getType();

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    public void setDetailsToAccount(String password, String name, String lastname, String Emai, double phoneNo, Date birthdayDat, Firm firm , String img) throws IOException {
        if (password != null) {
            this.password = password;
        }
        if (name != null) {
            this.name = name;
        }
        if (lastname != null) {
            this.lastname = lastname;
        }
        if (email != null) {
            this.email = Emai;
        }
        if (phoneNo != 0) {
            this.phoneNo = phoneNo;
        }
        if (birthdayDat != null) {
            if (birthdayDat instanceof Date) {
                this.birthdayDate = (Date) birthdayDat;
                birthdayDates.add(birthdayDate);
            }
        }
        if (firm != null) {
            this.firm = firm;
        }if (img != null) {
            this.imageId = img;
        }
//        writeInJ();
        Customer.writeInJ();
        Manager.writeInJ();
        Seller.writeInJ();
    }


    public Account(String username) throws IOException {
        this.username = username;
        allAccounts.add(this);

    }

    public String getPassword() {
        return password;
    }

    public static boolean isThereAccountWithUsername(String username) {
        for (Account account : allAccounts) {
            if (account.username.equalsIgnoreCase(username)) return true;
        }
        return false;
    }

    public static Account getAccountWithUsername(String username) {
        for (Account account : allAccounts) {
            if (account.username.equalsIgnoreCase(username)) return account;
        }
        return null;
    }

    public void reduceCredit(Double money){
        credit = credit - money;
    }

    public void increaseCredit(Double money){
        credit = credit + money;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public static void deleteAccount(String username) throws IOException {
        Account account = Account.getAccountWithUsername(username);
        allAccounts.remove(getAccountWithUsername(username));
        if(account instanceof Seller){
            Seller.getAllSellers().remove(account);
            Seller.writeInJ();
        }else if(account instanceof Customer){
            Customer.getAllCustomers().remove(account);
            Customer.writeInJ();
        } else if(account instanceof Manager){
            Manager.getAllManagers().remove(account);
            Manager.writeInJ();
        }

    }

    public static boolean isThereAccountWithUsernameAndPassword(String username, String password) {

        for (Account account : allAccounts) {
            if (account.username.equalsIgnoreCase(username) && account.password.equals(password)) return true;
        }
        return false;

    }


    public Account listUsers() {
        Iterator iterator = allAccounts.iterator();
        while (iterator.hasNext()) {
            Account selectedAccount = (Account) iterator.next();
            return selectedAccount;
        }
        return null;
    }

    public int compareTO(Account account) {
        return 0;
    }

    public String getRole() {
        return role;
    }

    //----------------------------------------------------------------
    public void setName(String name) throws IOException {
        this.name = name;
        Customer.writeInJ();
        Manager.writeInJ();
        Seller.writeInJ();

    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setLastname(String lastname) throws IOException {
        this.lastname = lastname;
//        writeInJ();

    }

    public double getBankMoney() {
        return bankMoney;
    }

    public void setBankMoney(int bankMoney) {
        this.bankMoney = bankMoney;
    }

    public ArrayList<String> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<String> transactions) {
        this.transactions = transactions;
    }

    public void setPassword(String password) throws IOException {
        this.password = password;
//        writeInJ();

    }

    public long getTokenDate() {
        return tokenDate;
    }

    public void setTokenDate(long tokenDate) {
        this.tokenDate = tokenDate;
    }

    public void setEmail(String email) throws IOException {
        this.email = email;
//        writeInJ();

    }

    public void setPhoneNo(double phoneNo) throws IOException {
        this.phoneNo = phoneNo;
//        writeInJ();

    }

    public Firm getFirm() {
        return firm;
    }

    public void setCredit(double credit) throws IOException {
        this.credit = credit;
//        writeInJ();

    }

    public void setCurrentPhoneNo(double currentPhoneNo) throws IOException {
        this.currentPhoneNo = currentPhoneNo;
//        writeInJ();

    }

    public void setAddress(String address) throws IOException {
        this.address = address;
//        writeInJ();

    }

    public void setFast(boolean fast) throws IOException {
        this.fast = fast;
//        writeInJ();

    }

    //-----------------------------------------------------------------
    public static ArrayList<Account> getAllAccounts() {
        return allAccounts;
    }

    public Date getBirthdayDate() {
        return birthdayDate;
    }

    public String getAddress() {
        return address;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public double getPhoneNo() {
        return phoneNo;
    }

    public double getCredit() {
        return credit;
    }

    public static void setAllAccounts(ArrayList<Account> allAccounts) {
        Account.allAccounts = allAccounts;
    }

    //......................................................................


    public static Comparator<Account> accountComparatorForUsername = new Comparator<Account>() {

        public int compare(Account s1, Account s2) {
            String name1 = s1.getName().toUpperCase();
            String name2 = s2.getName().toUpperCase();
            return name1.compareTo(name2);
        }
    };

    @Override
    public String toString() {
        return "Account{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phoneNo=" + phoneNo +
                ", credit=" + credit +
                ", role='" + role + '\'' +
                ", currentPhoneNo=" + currentPhoneNo +
                ", address='" + address + '\'' +
                ", birthdayDate=" + birthdayDate +
                // ", allDiscountCodes=" + allDiscountCodes +
                '}';
    }
}
