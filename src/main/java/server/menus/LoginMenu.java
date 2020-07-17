package server.menus;

import model.Bank.BankAPI;
import model.accounts.Account;
import model.firms.Firm;
import model.request.AccountRequest;
import model.request.Request;
import client.view.gui.LoginFx;

import java.io.IOException;
import java.util.Date;

public class LoginMenu {
    private static int outputNo;
    private static Account loginAccount;
    // private static String field = null;
    private static String username = null;
    private static boolean login = false;
    // private static SubMenuStatus subMenuStatus;
    private static AccountRequest accountRequest;
    private static Firm firm;
    private static String firmName;
    public static boolean yes = false;
    //   private static String token;
    //  private static String sourceID;



    public static Firm getFirm() {
        return firm;
    }



    public static Account getLoginAccount() {
        return loginAccount;
    }

    public static boolean isLogin() {
        return login;
    }

    public static int processLogin(String username) {
        if (!login) {
            if (username.matches("(\\s*\\S+\\s*)+")) {
                if (Account.isThereAccountWithUsername(username)) {
                    LoginMenu.username = username;
                    outputNo = 0;
                    yes = true;
                    //subMenuStatus = CommandProcessor.getSubMenuStatus();
                    // CommandProcessor.setSubMenuStatus(SubMenuStatus.PASSWORD);
                } else outputNo = 13;
            } else outputNo = 32;
        } else outputNo = 24;
        return outputNo;
        //  OutputMassageHandler.showAccountOutput(outputNo);
    }

    public static int checkPassword(String password) throws IOException {
        if (password.matches(".+")) {
            if (Account.isThereAccountWithUsernameAndPassword(username, password)) {
                loginAccount = Account.getAccountWithUsername(username);
                login = true;
                loginAccount.setOnline("online");
                //token =
                Date date = new Date();
                //if (loginAccount.getTokenDate() - date.getTime() >= 3600000) {
                BankAPI.startLogin("get_token " + username + " " + password, loginAccount);
                //}
                // findRole();
                String role = loginAccount.getRole();
                yes = false;
                LoginFx.goToMenu(role);
                outputNo = 0;
                // CommandProcessor.setSubMenuStatus(subMenuStatus);
            } else outputNo = 14;
        } else outputNo = 3;
        return outputNo;
        //  OutputMassageHandler.showAccountOutput(outputNo);
    }


    public static void edit() throws IOException {
        if (loginAccount.getRole() == "seller") {
            String id = "seller " + LoginMenu.getLoginAccount().getUsername() + "wants edit account ";
            if (accountRequest.isThereRequestFromID(id)) {
                accountRequest = new AccountRequest(id);
                accountRequest.setLastname(LoginMenu.getLoginAccount().getUsername());
                accountRequest.setFirmName(firm.getName());
            } else {
                if (Request.getRequestFromID(id) instanceof AccountRequest)
                    accountRequest = (AccountRequest) Request.getRequestFromID(id);

            }
        }

    }

    public static int editAccount(String edit, String field) throws IOException {
        if (field.equalsIgnoreCase("password")) {
            if (edit.matches(".+")) {
                loginAccount.setPassword(edit);
                outputNo = 0;
                // outputNo = 17;
            } else outputNo = 3;
        } else if (field.equalsIgnoreCase("name")) {
            if (edit.matches("(\\s*\\S+\\s*)+")) {
                loginAccount.setName(edit);
                outputNo = 0;
                // outputNo = 18;
            } else outputNo = 5;
        } else if (field.matches("last\\s*name")) {
            if (edit.matches("(\\s*\\S+\\s*)+")) {
                loginAccount.setLastname(edit);
                outputNo = 0;
                // outputNo = 19;
            } else outputNo = 7;
        } else if (field.equalsIgnoreCase("Email")) {
            if (edit.matches("^(.+)@(.+)$")) {
                loginAccount.setEmail(edit);
                outputNo = 0;
                // outputNo = 20;
            } else outputNo = 9;
        } else if (field.matches("Phone\\s*number")) {
            if (edit.matches("09[0-9]{9}")) {
                loginAccount.setPhoneNo(Integer.parseInt(edit));
                outputNo = 0;
                // outputNo = 21;
            } else outputNo = 11;
        }
        return outputNo;
        //  OutputMassageHandler.showAccountOutput(outputNo);
    }

    public static int editSellerField(String edit, String field) throws IOException {
        if (field.equalsIgnoreCase("password")) {
            if (edit.matches("(\\s*\\S+\\s*)+")) {
                accountRequest.setPassword(edit);
                outputNo = 0;
            } else outputNo = 3;
        } else if (field.equalsIgnoreCase("name")) {
            if (edit.matches("(\\s*\\S+\\s*)+")) {
                accountRequest.setName(edit);
                outputNo = 0;
            } else outputNo = 5;
        } else if (field.matches("last\\s*name")) {
            if (edit.matches("(\\s*\\S+\\s*)+")) {
                accountRequest.setLastname(edit);
                outputNo = 0;
            } else outputNo = 7;
        } else if (field.equalsIgnoreCase("Email")) {
            if (edit.matches("^(.+)@(.+)$")) {
                accountRequest.setEmail(edit);
                outputNo = 0;
            } else outputNo = 9;
        } else if (field.matches("Phone\\s*number")) {
            if (edit.matches("09[0-9]{9}")) {
                accountRequest.setPhoneNo(Integer.parseInt(edit));
                outputNo = 0;
            } else outputNo = 11;
        }
        return outputNo;
        //OutputMassageHandler.showAccountOutput(outputNo);
    }

    public static void firmName(String name) throws IOException {
        // if (name.matches(".+")) {
        //  if (Firm.isThereFirmWithID(name)) {
        firmName = name;
        String id = loginAccount.getUsername() + " wants edit firm";
        accountRequest = new AccountRequest(id);
        accountRequest.setFirmType(loginAccount.getFirm().getType());
        accountRequest.setFirmName(firmName);
        //   outputNo = 0;
        //   CommandProcessor.setSubMenuStatus(SubMenuStatus.FIRMFIELD);
        //  outputNo = 5;
        //  } else outputNo = 12;
        //  } else outputNo = 3;
        //  return outputNo;
        // OutputMassageHandler.showFirmOutput(outputNo);
    }


    public static int editFirm(String detail, String field) throws IOException {
        if (field.matches("phone\\s*number")) {
            if (detail.matches("09[0-9]{9}")) {
                accountRequest.setFirmPhoneNO(Double.parseDouble(detail));
                outputNo = 7;
            } else outputNo = 6;
        } else if (field.equalsIgnoreCase("address")) {
            if (detail.matches("(\\s*\\S+\\s*)+")) {
                accountRequest.setFirmAddress(detail);
                outputNo = 9;
            } else outputNo = 8;
        } else if (field.equalsIgnoreCase("email")) {
            if (detail.matches("^(.+)@(.+)$")) {
                accountRequest.setFirmEmail(detail);
                outputNo = 11;
            } else outputNo = 10;
        }
        return outputNo;
    }

    public static void processLogout() {
        //   if (login) {
        loginAccount.setOnline("off");
        loginAccount = null;
        login = false;

        //  CommandProcessor.setMenuStatus(MenuStatus.MAINMENU);
        //  CommandProcessor.setSubMenuStatus(SubMenuStatus.MAINMENU);
        //   outputNo = 22;
        //} else outputNo = 25;
        //  OutputMassageHandler.showAccountOutput(outputNo);
    }


//    private static void findRole() {
//        String role = loginAccount.getRole();
//        MenuStatus menu = null;
//        if (role.equalsIgnoreCase("customer")) {
//            menu = MenuStatus.CUSTOMERMENU;
//        } else if (role.equalsIgnoreCase("manager")) {
//            menu = MenuStatus.MANAGERMENU;
//        } else if (role.equalsIgnoreCase("seller")) {
//            menu = MenuStatus.SELLERMENU;
//        }
//        CommandProcessor.setMenuStatus(menu);
//    }

//    //gson
//    public static void viewPersonalInfo() throws FileNotFoundException {
//        OutputHandler.showAccountInformation(loginAccount.getUsername());
//        CommandProcessor.setSubMenuStatus(SubMenuStatus.VIEWPERSONALINFO);
//    }

  /*  public static int processEdit(String field) throws IOException {
        // if (field.matches("(?i)(?:username|password|last\\s*name|email|phone\\s*number|firm)")) {
        if (loginAccount.getRole() == "seller") {
            String id = "seller " + LoginMenu.getLoginAccount().getUsername() + "wants edit account's " + field;
            if (accountRequest.isThereRequestFromID(id)) {
                accountRequest = new AccountRequest(id);
                accountRequest.setLastname(LoginMenu.getLoginAccount().getUsername());
                accountRequest.setFirmName(firm.getName());
            } else {
                if (Request.getRequestFromID(id) instanceof AccountRequest) {
                    accountRequest = (AccountRequest) Request.getRequestFromID(id);
                }
            }
            if (field.equalsIgnoreCase("firm")) {
                //  CommandProcessor.setSubMenuStatus(SubMenuStatus.FIRMNAME);
                ///  outputNo = 28;
            } else {
                //   CommandProcessor.setSubMenuStatus(SubMenuStatus.EDITSELLERACCOUNT);
                //   outputNo = 34;
            }
        } else {
            if (!field.equalsIgnoreCase("firm")) {
                //   CommandProcessor.setSubMenuStatus(SubMenuStatus.EDITACCOUNT);
                //   outputNo = 34;
            } else outputNo = 27;
        }
        LoginMenu.field = field;
        // OutputMassageHandler.showOutputWithString(field, 3);
        //} else outputNo = 16;
        return outputNo;
        // OutputMassageHandler.showAccountOutput(outputNo);
    }

   */

 /*   public static void firmField(String field) throws IOException {
        if (!field.matches("(?i)(?:name|address|email|phone\\s*number)")) {
            accountRequest.setFirmName(firmName);
            CommandProcessor.setSubMenuStatus(SubMenuStatus.EDITFIRM);
            LoginMenu.field = field;
            outputNo = 2;
        } else outputNo = 1;
        OutputMassageHandler.showFirmOutput(outputNo);
    }

    public static boolean checkFirm() {
        if (loginAccount instanceof Seller) {
            if (loginAccount instanceof Seller) {
                Seller seller = (Seller) loginAccount;
                if (seller.getFirm().getName().equalsIgnoreCase(firmName)) {
                    firm = seller.getFirm();
                    return true;
                }
            }
        }
        return false;
    }
  */

}