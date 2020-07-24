package server;

import client.view.OutputMassageHandler;
import model.accounts.Account;
import server.menus.LoginMenu;
import server.menus.RegisterMenu;
import server.menus.SellerMenu;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class HandleMarket extends Thread {
    private static DataInputStream dataInputStream;
    private static DataOutputStream dataOutputStream;
    private static Socket clientServer;
    private static String output;
   // private static MarketServer marketServer;
    private Account loginAccount;

    public  Account getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(Account loginAccount) {
        this.loginAccount = loginAccount;
    }

    public HandleMarket(Socket clientServer, DataInputStream dataInputStream, DataOutputStream dataOutputStream) {
        this.dataInputStream = dataInputStream;
        this.dataOutputStream = dataOutputStream;
        this.clientServer = clientServer;
    }


    @Override
    public void run() {
        String input = null;
        try {
            input = dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }while (true){
            System.out.println(input);
            String[] inputs = input.split("\\s+");
            try {
                if(input.startsWith("login")){
                    LoginMenu.processLogin(inputs[1]);
                    if(LoginMenu.yes) {
                      //  LoginMenu.checkPassword(inputs[2], this);
                    }
                    output = OutputMassageHandler.getText();
                }else if(input.startsWith("reg")){
                    register(inputs);
                    output = OutputMassageHandler.getText();
                }else if(input.startsWith("viewAc")){
                    getAcInfo(inputs);
                }else if(input.startsWith("editSelAc")){
                    LoginMenu.edit();
                    editSelAc(inputs);
                    output = OutputMassageHandler.getText();
                }else if(input.startsWith("editAc")) {
                    LoginMenu.edit();
                    editAc(inputs);
                    output = OutputMassageHandler.getText();
                }else if(input.startsWith("addSale")) {
                    addSale(inputs);
                    output = OutputMassageHandler.getText();
                }else if(input.startsWith("addCat")) {
                    addCat();
                    output = OutputMassageHandler.getText();
                }else if(input.startsWith("accReq")) {
                    addCat();
                    output = OutputMassageHandler.getText();
                }else if(input.startsWith("decReq")) {
                    addCat();
                    output = OutputMassageHandler.getText();
                }else if(input.startsWith("addPro")) {
                    addCat();
                    output = OutputMassageHandler.getText();
                }else if(input.startsWith("editPro")) {
                    addCat();
                    output = OutputMassageHandler.getText();
                }else if(input.startsWith("buy")) {
                    addCat();
                    output = OutputMassageHandler.getText();
                }

            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }

            handleOutput();
            break;


        }
    }

    private void addCat() {
    }

    private void editSale() {
    }

    private void addSale(String[] s) throws IOException, ParseException {
     /*   if (SellerMenu.getCreate() == 0) {
            SellerMenu.setDetailsToSale(s[1]);
        }
        if (SellerMenu.getCreate() == 1) {
            SellerMenu.setDetailsToSale(s[2]);
            if (SellerMenu.getDetailMenu() == 2) {
                SellerMenu.setDetailsToSale(s[3]);
            }
            if (SellerMenu.getDetailMenu() == 3) {
                SellerMenu.setDetailsToSale(s[4]);
            }
            if (SellerMenu.getDetailMenu() == 4) {
                SellerMenu.setDetailsToSale(s[5]);
            }
        }

      */

    }


    private synchronized void editAc(String[] inputs) throws IOException {
        if(inputs.length==3) {
            LoginMenu.editAccount(inputs[1], inputs[2]);
        }
    }

    private synchronized void editSelAc(String[] inputs) throws IOException {
        if(inputs.length==3) {
            LoginMenu.editSellerField(inputs[1], inputs[2]);
        }
    }

    private void getAcInfo(String[] inputs) {
        String pattern = "MM/dd/yyyy HH:mm:ss";
        DateFormat df = new SimpleDateFormat(pattern);
        Account curAccount = Account.getAccountWithUsername(inputs[1]);
        output = curAccount.getUsername()+" "+curAccount.getName()+" "+curAccount.getLastname()+" "+curAccount.getRole()+" "+
                String.valueOf(curAccount.getPhoneNo())+" "+ curAccount.getEmail()+" "+String.valueOf(curAccount.getCredit())+" "+df.format(curAccount.getBirthdayDate())+" "+curAccount.getImageId();
    }

    void handleOutput() {
        try {
            if(output == null)
                output = "done";
            dataOutputStream.writeUTF(output);
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private static void register(String[] inputs) throws IOException, ParseException {
        if (RegisterMenu.getSignUpNo() == 0) {
            RegisterMenu.processRegister(inputs[2], inputs[1], inputs[3]);
        }
      /*  if (RegisterMenu.getSignUpNo() == 1) {
            RegisterMenu.completeRegisterProcess(inputs[4]);
            if (RegisterMenu.getDetailMenu() == 1) {
                RegisterMenu.completeRegisterProcess(inputs[5]);
            }
            if (RegisterMenu.getDetailMenu() == 2) {
                RegisterMenu.completeRegisterProcess(inputs[6]);

            }
            if (RegisterMenu.getDetailMenu() == 3) {
                RegisterMenu.completeRegisterProcess(inputs[7]);
            }
            if (RegisterMenu.getDetailMenu() == 4) {
                RegisterMenu.completeRegisterProcess(inputs[8]);
            }
            if (RegisterMenu.getDetailMenu() == 5) {
                RegisterMenu.completeRegisterProcess(inputs[9]);
            }
            if (inputs[2] == "manager") {
                RegisterMenu.wage(inputs[10]);
                RegisterMenu.min(inputs[11]);
            }
        }

       */
    }


}
