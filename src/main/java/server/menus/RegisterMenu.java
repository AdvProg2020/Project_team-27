package server.menus;

import model.Bank.BankAPI;
import model.accounts.Account;
import model.accounts.Customer;
import model.accounts.Manager;
import model.accounts.Supporter;
import model.request.AccountRequest;
import model.request.Request;
import client.view.gui.OutputMassageHandler;
import client.view.justConsole.SubMenuStatus;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class RegisterMenu {
    private static int outputNo;
    private static int signUpNo = 0;
    private static Manager manager;
    private static Customer customer;
    private static Supporter supporter;
    private static AccountRequest accountRequest;
    private static int detailMenu = 0;
    private static boolean managerWant = false;
    private static boolean headManager = true;
    private static String role;
    private static String username;
    private static String password;
    private static String name;
    private static String lastName;
    private static String Email;
    private static Double phoneNo;
    private static Date birthdayDate;
    private static SubMenuStatus subMenuStatus;
    private static String img;
    public static boolean ok;
    static boolean first = true;

    public static boolean isHeadManager() {
        return headManager;
    }

    public static int getDetailMenu() {
        return detailMenu;
    }

    public static void setDetailMenu(int detailMenu) {
        RegisterMenu.detailMenu = detailMenu;
    }

    public static int getSignUpNo() {
        return signUpNo;
    }

    public static void setSignUpNo(int signUpNo) {
        RegisterMenu.signUpNo = signUpNo;
    }


    public static int processRegister(String role, String username, String image) throws IOException {
        if (username.matches("^[a-z0-9_-]{3,15}$")) {
            if (!Account.isThereAccountWithUsername(username)) {
                //  if (role.matches("(?i)(?:customer|manager|seller)")) {
                RegisterMenu.role = role;
                RegisterMenu.username = username;
                registerByRole(role, username);
                signUpNo = 1;
                outputNo = 0;
                img = image;
                //       subMenuStatus = CommandProcessor.getSubMenuStatus();
                //        CommandProcessor.setSubMenuStatus(SubMenuStatus.REGISTERATIONDETAILS);
                //        CommandProcessor.setInternalMenu(InternalMenu.CHANGEDETAILS);
                //   } else outputNo = 26;
            } else outputNo = 1;
        } else outputNo = 36;
        return outputNo;
        //  OutputMassageHandler.showAccountOutput(outputNo);
    }

    private static void registerByRole(String role, String username) throws IOException {
        if (role.equalsIgnoreCase("customer")) {
            customer = new Customer(username);
            outputNo = 0;
        } else if (role.equalsIgnoreCase("manager")) {
            createManagerAccount(username);
        } else if (role.equalsIgnoreCase("supporter")) {
            supporter = new Supporter(username);
        } else if (role.equalsIgnoreCase("seller")) {
            String id = username + " wants seller account";
            if (!Request.isThereRequestFromID(id)) {
                accountRequest = new AccountRequest(id);
            } else {
                if (Request.getRequestFromID(id) instanceof AccountRequest)
                    accountRequest = (AccountRequest) Request.getRequestFromID(id);
            }
            outputNo = 0;
        }
        //  OutputMassageHandler.showAccountOutput(outputNo);

    }

    private static void createManagerAccount(String username) throws IOException {
        if (managerWant || (headManager)) {
            manager = new Manager(username);
            headManager = false;
            managerWant = false;
            outputNo = 0;
            //  outputNo = 2;
        } else {
            //  CommandProcessor.setInternalMenu(InternalMenu.MAINMENU);
            // CommandProcessor.setSubMenuStatus(SubMenuStatus.MAINMENU);
            outputNo = 23;
        }
    }

    public static int completeRegisterProcess(String detail, int detailMen) throws IOException, ParseException {
        if (detailMen == 0) {
            if (detail.matches(".+")) {
                password = detail;
                detailMenu = 1;
                outputNo = 0;
            } else outputNo = 3;
        } else if (detailMen == 1) {
            if (detail.matches("(\\s*\\S+\\s*)+")) {
                name = detail;
                detailMenu = 2;
                outputNo = 0;
            } else outputNo = 5;
        } else if (detailMen == 2) {
            if (detail.matches("(\\s*\\S+\\s*)+")) {
                lastName = detail;
                detailMenu = 3;
                outputNo = 0;
            } else outputNo = 7;
        } else if (detailMen == 3) {
            if (detail.matches(".+")) {
                Email = detail;
                detailMenu = 4;
                outputNo = 0;
            } else outputNo = 9;
        } else if (detailMen == 4) {
            if (detail.matches(".+")) {
                phoneNo = Double.parseDouble(detail);
                detailMenu = 5;
                outputNo = 0;
            } else outputNo = 11;
        } else if (detailMen == 5) {
            if (detail.matches("^\\d{1,2}\\/\\d{1,2}\\/\\d{4}$")) {
                Date currentDate = new Date();
                Date inputDate = new SimpleDateFormat("dd/MM/yyyy").parse(detail);
                if (inputDate.before(currentDate)) {
//            if (detail.matches("^\\d{1,2}\\/\\d{1,2}\\/\\d{4}$")) {
//                DateFormat format = new SimpleDateFormat("dd/mm/yyyy");
                    //                   birthdayDate = format.parse(detail);
                    birthdayDate = inputDate;
                    detailMenu = 0;
                    setSignUpNo(6);
                    createAccountWithDetails();
                } else outputNo = 30;
            } else outputNo = 30;
        }
        OutputMassageHandler.showAccountOutput(outputNo);
        return outputNo;
    }

    public static int wage(String wage) {
        if (wage.matches("\\d+")) {
            manager.setWage(Integer.parseInt(wage));
            outputNo = 0;
        } else outputNo = 2;
        return outputNo;
    }

    public static int min(String wage) {
        if (wage.matches("\\d+")) {
            manager.setMin(Integer.parseInt(wage));
            outputNo = 0;
        } else outputNo = 2;
        return outputNo;
    }

    public static void createAccountWithDetails() throws IOException {
        if (role.equalsIgnoreCase("seller")) {
            accountRequest.sellerAccountDetails(username, password, name, lastName, Email, phoneNo, birthdayDate, img);
            //   CommandProcessor.setSubMenuStatus(SubMenuStatus.ADDFIRM);
            outputNo = 31;
        } else if (role.equalsIgnoreCase("customer")) {
            customer.setDetailsToAccount(password, name, lastName, Email, phoneNo, birthdayDate, null, img);
              BankAPI.startRegister("create_account " + name+" " + lastName+" " + username+" " +password+" " + password, customer);
            //  CommandProcessor.setSubMenuStatus(SubMenuStatus.MAINMENU);
            //  CommandProcessor.setInternalMenu(InternalMenu.MAINMENU);
            outputNo = 12;
        } else if (role.equalsIgnoreCase("manager")) {
            manager.setDetailsToAccount(password, name, lastName, Email, phoneNo, birthdayDate, null, img);
            if(Manager.getAllManagers().size() == 1) {
                   BankAPI.startRegister("create_account " + name+" " + lastName+" " + username+" " +password+" " + password, manager);
            }
            //  CommandProcessor.setSubMenuStatus(SubMenuStatus.MAINMENU);
            //  CommandProcessor.setInternalMenu(InternalMenu.MAINMENU);
            outputNo = 12;
        } else if (role.equalsIgnoreCase("supporter")) {
            supporter.setDetailsToAccount(password, name, lastName, Email, phoneNo, birthdayDate, null, img);
            //  CommandProcessor.setSubMenuStatus(SubMenuStatus.MAINMENU);
            //  CommandProcessor.setInternalMenu(InternalMenu.MAINMENU);
            outputNo = 12;
        }
    }

    public static void createBankAccount() throws IOException {
        //  BankAPI.ConnectToBankServer();
        // BankAPI.start("create_account " + name+" " + lastName+" " + username+" " +password+" " + password);
    }

    public static int createFirm(String detail, int detailMen) throws IOException {
        if (detailMen == 0) {
            if (detail.matches("(\\s*\\S+\\s*)+")) {
                accountRequest.setFirmName(detail);
                detailMenu++;
                outputNo = 0;
            } else outputNo = 3;
        } else if (detailMen == 1) {
            if (detail.matches("09[0-9]{9}")) {
                accountRequest.setPhoneNo(Double.parseDouble(detail));
                detailMenu++;
                outputNo = 0;
            } else outputNo = 6;
        } else if (detailMen == 2) {
            if (detail.matches("(\\s*\\S+\\s*)+")) {
                accountRequest.setFirmAddress(detail);
                detailMenu = 3;
                outputNo = 0;
            } else outputNo = 8;
        } else if (detailMen == 3) {
            if (detail.matches("(?i)(?:company|factory|work\\s*shop)")) {
                accountRequest.setFirmType(detail);
                detailMenu = 4;
                outputNo = 0;
            } else outputNo = 18;
        } else if (detailMen == 4) {
            if (detail.matches("^(.+)@(.+)$")) {
                accountRequest.setFirmEmail(detail);
                detailMenu = 0;
                //  CommandProcessor.setSubMenuStatus(SubMenuStatus.MAINMENU);
                //   CommandProcessor.setInternalMenu(InternalMenu.MAINMENU);
                outputNo = 0;
            } else outputNo = 10;
        }
        return outputNo;
        // OutputMassageHandler.showFirmOutput(outputNo);
    }

    public static int receiverInformation(String detail, int detailMen) throws IOException {
        if (detailMen == 0) {
            if (detail.matches("\\d+")) {
                LoginMenu.getLoginAccount().setCurrentPhoneNo(Double.parseDouble(detail));
                detailMenu = 1;
                outputNo = 0;
            } else outputNo = 1;
        } else if (detailMen == 1) {
            if (detail.matches(".+")) {
                LoginMenu.getLoginAccount().setAddress(detail);
                detailMenu = 2;
                outputNo = 0;
            } else outputNo = 3;
        } else if (detailMen == 2) {
            if (detail.matches("(?i)(?:yes|no)")) {
                if (detail.equalsIgnoreCase("yes")) {
                    LoginMenu.getLoginAccount().setFast(true);
                } else {
                    LoginMenu.getLoginAccount().setFast(false);
                }
                ok = true;
                detailMenu = 0;
                //CommandProcessor.setSubMenuStatus(SubMenuStatus.HAVEDISCOUNT);
                //CommandProcessor.setInternalMenu(InternalMenu.MAINMENU);
                //  outputNo = 6;
            } else outputNo = 5;
        }
        return outputNo;
        //  OutputMassageHandler.showReceiverInfo(outputNo);

    }

    /*
      public static int getDetailMenu() {
        return detailMenu;
    }

     */


}