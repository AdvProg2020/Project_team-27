package controller.request;

import model.accounts.Account;
import model.accounts.AccountStatus;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.ArrayList;

public class AccountRequest extends Request {

    private static String username = null;
    private static String password = null;
    private static String name = null;
    private static String lastname = null;
    private static String Email = null;
    private static double phoneNo = 0;
    private static Data birthdayDate = null;
    private static Account selectedAccount;
    private ArrayList<AccountRequest> allAccountRequests;

    public AccountRequest(String requestID) {
        super(requestID);
        allAccountRequests.add(this);
    }


    public void acceptRequest() throws IOException {
        selectedAccount = Account.getAccountWithUsername(username);
        selectedAccount.setDetailsToAccount(password, name, lastname, Email, phoneNo, birthdayDate);
        selectedAccount.setAccountStatus(AccountStatus.CONFIRMED);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setPhoneNo(double phoneNo) {
        this.phoneNo = phoneNo;
    }

    public void sellerAccountDetails(String username, String password, String name, String lastname, String Email, double phoneNo, Data birthdayDate) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.lastname = lastname;
        this.Email = Email;
        this.phoneNo = phoneNo;
    }


}