package server;

import client.Client;
import client.Main;
import client.view.OutputMassageHandler;
import client.view.justConsole.InternalMenu;
import client.view.justConsole.MenuSituation;
import client.view.justConsole.MenuStatus;
import client.view.justConsole.SubMenuStatus;
import model.accounts.Account;
import model.bank.Bank;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import client.Main.*;
import server.justConsol.ProductsMenu;
import server.menus.*;
import serverClient.ServerMain;

public class Server {
    private static ServerSocket serverSocket;
    private static DataInputStream dataInputStream;
    private static DataOutputStream dataOutputStream;
    private static Socket clientServer;
    private static Scanner scanner;
    public static File file;

    public static void main(String[] args) {
        run();
    }

    private static void run() {
        try {
            ServerMain.startServer(4293);
            serverSocket = new ServerSocket(8888);
            System.out.println("market connected");
            while (true) {
                clientServer = serverSocket.accept();
              //  System.out.println("client connected");
                dataOutputStream = new DataOutputStream(new BufferedOutputStream(clientServer.getOutputStream()));
                dataInputStream = new DataInputStream(new BufferedInputStream(clientServer.getInputStream()));
                new handleMarket(clientServer,dataInputStream,dataOutputStream).start();
            }
        } catch (IOException e) {
            System.err.println("Error newing server");
        }
    }


    public static void serverSellFile() throws IOException {
        ServerSocket serverSocket = new ServerSocket(15123);
        Socket socket = serverSocket.accept();
        System.out.println("Accepted connection : " + socket);
        File transferFile = file;
        byte[] bytearray = new byte[(int) transferFile.length()];
        FileInputStream fin = new FileInputStream(transferFile);
        BufferedInputStream bin = new BufferedInputStream(fin);
        bin.read(bytearray, 0, bytearray.length);
        OutputStream os = socket.getOutputStream();
        System.out.println("Sending Files...");
        os.write(bytearray, 0, bytearray.length);
        os.flush();
        socket.close();
        System.out.println("File transfer complete");
    }

    static class handleMarket extends Thread {
        private static DataInputStream dataInputStream;
        private static DataOutputStream dataOutputStream;
        private static Socket clientServer;
        private static String output;
        private static Server server;

        public handleMarket(Socket clientServer, DataInputStream dataInputStream, DataOutputStream dataOutputStream) {
            this.dataInputStream = dataInputStream;
            this.dataOutputStream = dataOutputStream;
            this.clientServer = clientServer;
        }

        private String[] regex = {"(?i)create\\s+account\\s+(\\S+)\\s+((\\s*\\S+\\s*)+)",
                "(?i)login\\s+((\\s*\\S+\\s*)+)",//1
                "(?i)edit\\s+((\\s*\\S+\\s*)+)",//2
                "(?i)remove\\s+product\\s+((\\s*\\S+\\s*)+)",//3
                "(?i)show\\s+product\\s+((\\s*\\S+\\s*)+)",//4
                "(?i)compare\\s+((\\s*\\S+\\s*)+)",//5
                "(?i)client.view\\s+((\\s*\\S+\\s*)+)",//6
                "(?i)delete\\s+user((\\s*\\S+\\s*)+)",//7
                "(?i)remove\\s+((\\s*\\S+\\s*)+)",//8
                "(?i)client.view\\s+discount\\s+code((\\s*\\S+\\s*)+)",//9
                "(?i)edit\\s+discount\\s+code((\\s*\\S+\\s*)+)",//10
                "(?i)remove\\s+discount\\s+code((\\s*\\S+\\s*)+)",//11
                "(?i)details\\s+((\\s*\\S+\\s*)+)",//12
                "(?i)accept\\s+((\\s*\\S+\\s*)+)",//13
                "(?i)decline\\s+((\\s*\\S+\\s*)+)",//14
                "(?i)edit\\s+((\\s*\\S+\\s*)+)",//15
                "(?i)add\\s+((\\s*\\S+\\s*)+)",//16
                "(?i)remove\\s+((\\s*\\S+\\s*)+)",//17
                "(?i)client.view\\s+buyers\\s+((\\s*\\S+\\s*)+)",//18
                "(?i)increase((\\s*\\S+\\s*)+)",//19
                "(?i)decrease((\\s*\\S+\\s*)+)",//20
                "(?i)show\\s+order((\\s*\\S+\\s*)+)",//21
                "(?i)rate((\\s*\\S+\\s*)+)\\s+(\\d+)",//22
                "(?i)filter((\\s*\\S+\\s*)+)",//23
                "(?i)disable\\s+filter",//24
                "(?i)sort((\\s*\\S+\\s*)+)",//25
                "(?i)select\\s+seller",//26
                "((\\s*\\S+\\s*)+)",//27
                "(\\d+)\\s+(\\d+)",//28
                "(?i)sort\\s+by\\s+((\\s*\\S+\\s*)+)"//29

        };

        private Matcher getMatcher(String input, String regex) {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(input);
            matcher.find();
            return matcher;
        }


        @Override
        public void run() {
            String input = null;
            try {
                input = dataInputStream.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }while (true){
                String[] inputs = input.split("\\s+");
                try {
                if(input.startsWith("login")){
                    LoginMenu.processLogin(inputs[1]);
                    if(LoginMenu.yes) {
                        LoginMenu.checkPassword(inputs[2]);
                    }
                }else if(input.startsWith("reg")){
                   register(inputs);
                }else if(input.startsWith("viewAc")){
                    getAcInfo(inputs);
                }else if(input.startsWith("editSelAc")){
                    editSelAc();
                }else if(input.startsWith("editAc")) {
                    editAc();
                }

                } catch (IOException | ParseException e) {
                    e.printStackTrace();
                }
                output = OutputMassageHandler.getText();
                handleOutput();
                break;


            }
        }

        private void editAc() {
        }

        private void editSelAc() {
        }

        private void getAcInfo(String[] inputs) {
            Account curAccount = Account.getAccountWithUsername(inputs[1]);
             output = curAccount.getUsername()+" "+curAccount.getName()+" "+curAccount.getLastname()+" "+curAccount.getRole()+" "+
                    String.valueOf(curAccount.getPhoneNo())+" "+ curAccount.getEmail()+" "+String.valueOf(curAccount.getCredit())+" "+String.valueOf(curAccount.getBirthdayDate())+" "+curAccount.getImageId();
        }

        void handleOutput() {
            try {
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
            if (RegisterMenu.getSignUpNo() == 1) {
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
        }
  /*      @Override
        public void run() throws {
            boolean error = true;

            String input = null;
            try {
                input = dataInputStream.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (!(input.trim().equalsIgnoreCase("exit")) {
                //if (subMenuStatus == subMenuStatus.PASSWORD) {
                try {
                    if (input.matches(regex[27])) {
                        error = false;
                        LoginMenu.checkPassword(getMatcher(input, regex[27]).group(1));
                    }
                    //  } else if (subMenuStatus == subMenuStatus.REGISTERATIONDETAILS) {
                    if (input.matches(regex[27])) {
                        error = false;
                        RegisterMenu.completeRegisterProcess(getMatcher(input, regex[27]).group(1));
                    }
                    //} else if (subMenuStatus == subMenuStatus.ADDFIRM) {
                    if (input.matches(regex[27])) {
                        error = false;
                        RegisterMenu.createFirm(getMatcher(input, regex[27]).group(1));
                    }
                    // } else if (internalMenu == InternalMenu.MAINMENU) {
                    if (input.matches(regex[1])) {
                        error = false;
                        LoginMenu.processLogin(getMatcher(input, regex[1]).group(1));
                    } else if (input.equalsIgnoreCase("logout")) {
                        error = false;
                        LoginMenu.processLogout();
                    } else if (input.matches(regex[0])) {
                        error = false;
                        RegisterMenu.processRegister(getMatcher(input, regex[0]).group(1), getMatcher(input, regex[0]).group(2));
                        // } else if (menuStatus == MenuStatus.MAINMENU) {
                        if (input.equalsIgnoreCase("offs")) {
                            error = false;
                            SaleMenu.processOffs();
                        } else if (input.equalsIgnoreCase("products")) {
                            error = false;
                            ProductsMenu.processProducts();
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // if (menuStatus == MenuStatus.MANAGERMENU || ifManager()) {
                //ManagerMenu
                if (subMenuStatus == SubMenuStatus.MAINMENU) {
                    if (input.matches("manage\\s+users")) {
                        error = false;
                        ManagerMenu.processManageUsers();
                    } else if (input.matches("client.view\\s+personal\\s+info")) {
                        error = false;
                        LoginMenu.viewPersonalInfo();
                    } else if (input.matches("manage\\s+all\\s+products")) {
                        error = false;
                        ManagerMenu.processManageAllProducts();
                    } else if (input.matches("create\\s+discount\\s+code")) {
                        error = false;
                        ManagerMenu.processCreateDiscountCode();
                    } else if (input.matches("client.view\\s+discount\\s+codes")) {
                        error = false;
                        ManagerMenu.processViewDiscountCodes();
                    } else if (input.matches("manage\\s+requests")) {
                        error = false;
                        ManagerMenu.processManageRequests();
                    } else if (input.matches("manage\\s+categories")) {
                        error = false;
                        ManagerMenu.processManageCategories();
                    } else if (input.matches(regex[28])) {
                        error = false;
                        ManagerMenu.sortBy(getMatcher(input, regex[28]).group(1));
                    }

                } else if (subMenuStatus == SubMenuStatus.VIEWPERSONALINFO) {
                    if (input.matches(regex[2])) {
                        error = false;
                        LoginMenu.processEdit(getMatcher(input, regex[2]).group(1));
                    }
                } else if (subMenuStatus == SubMenuStatus.MANAGEUSERS) {
                    if (input.matches(regex[6])) {
                        error = false;
                        ManagerMenu.client.view(getMatcher(input, regex[6]).group(1));
                    } else if (input.matches(regex[7])) {
                        error = false;
                        ManagerMenu.deleteUser(getMatcher(input, regex[7]).group(1));
                    } else if (input.matches(" create\\s+manager\\s+profile ")) {
                        error = false;
                        ManagerMenu.createManagerProfile();
                    }
                } else if (subMenuStatus == SubMenuStatus.MANAGEALLPRODUCTS) {
                    if (input.matches(regex[8])) {
                        error = false;
                        ManagerMenu.removeProduct(getMatcher(input, regex[8]).group(1));
                    }
                } else if (subMenuStatus == SubMenuStatus.VIEWDISCOUNTCODES) {
                    if (input.matches(regex[9])) {
                        error = false;
                        ManagerMenu.viewDiscountCode(getMatcher(input, regex[9]).group(1));
                    } else if (input.matches(regex[10])) {
                        error = false;
                        ManagerMenu.editDiscountCode(getMatcher(input, regex[10]).group(1));
                    } else if (input.matches(regex[11])) {
                        error = false;
                        ManagerMenu.removeDiscountCode(getMatcher(input, regex[11]).group(1));
                    }
                } else if (subMenuStatus == SubMenuStatus.MANAGEREQUESTS) {
                    if (input.matches(regex[12])) {
                        error = false;
                        ManagerMenu.detailsRequest(getMatcher(input, regex[12]).group(1));
                    } else if (input.matches(regex[13])) {
                        error = false;
                        ManagerMenu.acceptRequest(getMatcher(input, regex[13]).group(1));
                    } else if (input.matches(regex[14])) {
                        error = false;
                        ManagerMenu.declineRequest(getMatcher(input, regex[14]).group(1));
                    }
                } else if (subMenuStatus == SubMenuStatus.MANAGECATEGORIES) {
                    if (input.matches(regex[15])) {
                        error = false;
                        ManagerMenu.editCategory(getMatcher(input, regex[15]).group(1));
                    } else if (input.matches(regex[16])) {
                        error = false;
                        ManagerMenu.addCategory(getMatcher(input, regex[16]).group(1));
                    } else if (input.matches(regex[17])) {
                        error = false;
                        ManagerMenu.removeCategory(getMatcher(input, regex[17]).group(1));
                    }
                }
                //sub
                else if (subMenuStatus.equals(SubMenuStatus.DISCOUNTCODEFIELD)) {
                    if (input.matches(regex[27])) {
                        error = false;
                        ManagerMenu.discountCodeField(getMatcher(input, regex[27]).group(1));
                    }
                } else if (subMenuStatus.equals(SubMenuStatus.EDITDISCOUNTCODE)) {
                    if (input.matches(regex[27])) {
                        error = false;
                        ManagerMenu.editDiscountCodeField(getMatcher(input, regex[27]).group(1));
                    }
                } else if (subMenuStatus.equals(SubMenuStatus.ADDDISCOUNTCODE)) {
                    if (input.matches(regex[27])) {
                        error = false;
                        ManagerMenu.createNewDiscountCode(getMatcher(input, regex[27]).group(1));
                    }
                } else if (subMenuStatus.equals(SubMenuStatus.DETAILDESCOUNTCODE)) {
                    if (input.matches(regex[27])) {
                        error = false;
                        ManagerMenu.setDetailToDiscountCode(getMatcher(input, regex[27]).group(1));
                    }
                } else if (subMenuStatus.equals(SubMenuStatus.DETAILCATEGORY)) {
                    if (input.matches(regex[27])) {
                        error = false;
                        ManagerMenu.setDetailToCategory(getMatcher(input, regex[27]).group(1));
                    }
                } else if (subMenuStatus.equals(SubMenuStatus.CATEGORYFIELD)) {
                    if (input.matches(regex[27])) {
                        error = false;
                        ManagerMenu.categoryField(getMatcher(input, regex[27]).group(1));
                    }
                } else if (subMenuStatus.equals(SubMenuStatus.EDITCATEGORY)) {
                    if (input.matches(regex[27])) {
                        error = false;
                        ManagerMenu.editCategoryField(getMatcher(input, regex[27]).group(1));
                    }
                } else if (subMenuStatus == subMenuStatus.EDITACCOUNT) {
                    if (input.matches(regex[27])) {
                        error = false;
                        LoginMenu.editAccount(getMatcher(input, regex[27]).group(1));
                    }
                } else if (subMenuStatus == subMenuStatus.CREATEMANAGERACCOUNT) {
                    if (input.matches(regex[27])) {
                        error = false;
                        RegisterMenu.processRegister("manager", getMatcher(input, regex[27]).group(1));
                    }
                } else if (subMenuStatus == subMenuStatus.EDITSELLERACCOUNT) {
                    if (input.matches(regex[27])) {
                        error = false;
                        LoginMenu.editSellerField(getMatcher(input, regex[27]).group(1));
                    }
                }
                //} else if (menuStatus == MenuStatus.SELLERMENU || ifSeller()) {
                // SellerMenu
                if (subMenuStatus == SubMenuStatus.MAINMENU) {
                    if (input.matches("client.view\\s+company\\s+information")) {
                        error = false;
                        SellerMenu.processViewCompanyInformation();
                    } else if (input.matches("client.view\\s+personal\\s+info")) {
                        error = false;
                        LoginMenu.viewPersonalInfo();
                    } else if (input.matches("client.view\\s+sales\\s+history")) {
                        error = false;
                        SellerMenu.processViewSalesHistory();
                    } else if (input.matches("manage\\s+products")) {
                        error = false;
                        SellerMenu.processManageProducts();
                    } else if (input.matches("add\\s+product")) {
                        error = false;
                        SellerMenu.processAddProduct();
                    } else if (input.matches(regex[3])) {
                        error = false;
                        SellerMenu.processRemoveProduct(getMatcher(input, regex[3]).group(1));
                    } else if (input.matches("show\\s+categories")) {
                        error = false;
                        SellerMenu.processShowCategories();
                    } else if (input.matches("client.view\\s+offs")) {
                        error = false;
                        SellerMenu.processViewOffs();
                    } else if (input.matches("client.view\\s+balance")) {
                        error = false;
                        SellerMenu.processViewBalance();
                    } else if (input.matches(regex[28])) {
                        error = false;
                        SellerMenu.sortBy(getMatcher(input, regex[28]).group(1));
                    }
                } else if (subMenuStatus == SubMenuStatus.MANAGEPRODUCTS) {
                    if (input.matches(regex[6])) {
                        error = false;
                        SellerMenu.viewProduct(getMatcher(input, regex[6]).group(1));
                    } else if (input.matches(regex[18])) {
                        error = false;
                        SellerMenu.viewBuyersProduct(getMatcher(input, regex[18]).group(1));
                    } else if (input.matches(regex[15])) {
                        error = false;
                        SellerMenu.editProduct(getMatcher(input, regex[15]).group(1));
                    }
                } else if (subMenuStatus == SubMenuStatus.VIEWOFFS) {
                    if (input.matches(regex[6])) {
                        error = false;
                        SellerMenu.viewOff(getMatcher(input, regex[6]).group(1));
                    } else if (input.matches(regex[15])) {
                        error = false;
                        SellerMenu.editOff(getMatcher(input, regex[15]).group(1));
                    } else if (input.matches("add\\s+off")) {
                        error = false;
                        SellerMenu.addOff();
                    }
                } else if (subMenuStatus == SubMenuStatus.VIEWPERSONALINFO) {
                    if (input.matches(regex[2])) {
                        error = false;
                        LoginMenu.processEdit(getMatcher(input, regex[2]).group(1));
                    }
                }
                //
                else if (subMenuStatus.equals(SubMenuStatus.PRODUCTFIELD)) {
                    if (input.matches(regex[27])) {
                        error = false;
                        SellerMenu.productField(getMatcher(input, regex[27]).group(1));
                    }
                } else if (subMenuStatus.equals(SubMenuStatus.EDITPRODUCT)) {
                    if (input.matches(regex[27])) {
                        error = false;
                        SellerMenu.editProductField(getMatcher(input, regex[27]).group(1));
                    }
                } else if (subMenuStatus.equals(SubMenuStatus.ADDPRODUCT)) {
                    error = false;
                    if (input.matches(regex[27])) {
                        SellerMenu.addProduct(getMatcher(input, regex[27]).group(1));
                    }
                } else if (subMenuStatus.equals(SubMenuStatus.SALEFIELD)) {
                    error = false;
                    if (input.matches(regex[27])) {
                        SellerMenu.offField(getMatcher(input, regex[27]).group(1));
                    }
                } else if (subMenuStatus.equals(SubMenuStatus.EDITSALE)) {
                    if (input.matches(regex[27])) {
                        error = false;
                        SellerMenu.editOffField(getMatcher(input, regex[27]).group(1));
                    }
                } else if (subMenuStatus.equals(SubMenuStatus.FIRMFIELD)) {
                    if (input.matches(regex[27])) {
                        error = false;
                        LoginMenu.firmField(getMatcher(input, regex[27]).group(1));
                    }
                } else if (subMenuStatus.equals(SubMenuStatus.EDITFIRM)) {
                    if (input.matches(regex[27])) {
                        error = false;
                        LoginMenu.editFirm(getMatcher(input, regex[27]).group(1));
                    }
                } else if (subMenuStatus.equals(SubMenuStatus.FIRMNAME)) {
                    if (input.matches(regex[27])) {
                        error = false;
                        LoginMenu.firmName(getMatcher(input, regex[27]).group(1));
                    }
                } else if (subMenuStatus.equals(SubMenuStatus.ADDSALE)) {
                    if (input.matches(regex[27])) {
                        error = false;
                        SellerMenu.setDetailsToSale(getMatcher(input, regex[27]).group(1));
                    }
                } else if (subMenuStatus == subMenuStatus.EDITSELLERACCOUNT) {
                    if (input.matches(regex[27])) {
                        error = false;
                        LoginMenu.editSellerField(getMatcher(input, regex[27]).group(1));
                    }
                } else if (subMenuStatus == subMenuStatus.TRAIT) {
                    if (input.matches(regex[27])) {
                        error = false;
                        SellerMenu.traitValue(getMatcher(input, regex[27]).group(1));
                    }
                } else if (subMenuStatus == subMenuStatus.EDITSPECIFICATION) {
                    if (input.matches(regex[27])) {
                        error = false;
                        SellerMenu.editCategorySpecifications(getMatcher(input, regex[27]).group(1));
                    }
                }


                // } else if (menuStatus == MenuStatus.CUSTOMERMENU || ifCustomer()) {
                //CustomerMenu
                if (subMenuStatus == SubMenuStatus.MAINMENU) {
                    if (input.matches("client.view\\s+cart")) {
                        error = false;
                        CustomerMenu.processViewCart();
                    } else if (input.matches("client.view\\s+personal\\s+info")) {
                        error = false;
                        LoginMenu.viewPersonalInfo();
                    } else if (input.matches("client.view\\s+orders")) {
                        error = false;
                        CustomerMenu.processViewOrders();
                    } else if (input.matches("client.view\\s+balance")) {
                        error = false;
                        CustomerMenu.processViewBalance();
                    } else if (input.matches("client.view\\s+discount\\s+codes")) {
                        error = false;
                        CustomerMenu.processViewDiscountCodes();
                    } else if (input.matches(regex[28])) {
                        error = false;
                        CustomerMenu.sortBy(getMatcher(input, regex[28]).group(1));
                    }
                } else if (subMenuStatus == SubMenuStatus.VIEWCART) {
                    if (input.matches("show\\s+products")) {
                        error = false;
                        CustomerMenu.showProducts();
                    } else if (input.matches(regex[6])) {
                        error = false;
                        CustomerMenu.viewProduct(getMatcher(input, regex[6]).group(1));
                    } else if (input.matches(regex[19])) {
                        error = false;
                        CustomerMenu.increaseProductNumber(getMatcher(input, regex[19]).group(1));
                    } else if (input.matches(regex[20])) {
                        error = false;
                        CustomerMenu.decreaseProductNumber(getMatcher(input, regex[20]).group(1));
                    } else if (input.matches("show\\s+total\\s+price")) {
                        error = false;
                        CustomerMenu.showTotalPrice();
                    } else if (input.equalsIgnoreCase("purchase")) {
                        error = false;
                        CustomerMenu.purchase();
                    }
                } else if (subMenuStatus == SubMenuStatus.VIEWORDERS) {
                    if (input.matches(regex[21])) {
                        error = false;
                        CustomerMenu.showOrder(getMatcher(input, regex[21]).group(1));
                    } else if (input.matches(regex[22])) {
                        error = false;
                        CustomerMenu.rateProduct(getMatcher(input, regex[22]).group(1), Integer.parseInt(getMatcher(input, regex[22]).group(3)));
                    }
                } else if (subMenuStatus == SubMenuStatus.VIEWPERSONALINFO) {
                    if (input.matches(regex[2])) {
                        error = false;
                        LoginMenu.processEdit(getMatcher(input, regex[2]).group(1));
                    }
                    //SUB
                } else if (subMenuStatus == SubMenuStatus.INCREASEPRODUCTNUMBER) {
                    if (input.matches(regex[21])) {
                        error = false;
                        CustomerMenu.increaseLogProduct(getMatcher(input, regex[21]).group(1));
                    }
                } else if (subMenuStatus == SubMenuStatus.DECREASEPRODUCTNUMBER) {
                    if (input.matches(regex[21])) {
                        error = false;
                        CustomerMenu.decreaseLogProduct(getMatcher(input, regex[21]).group(1));
                    }
                } else if (subMenuStatus == subMenuStatus.EDITACCOUNT) {
                    if (input.matches(regex[27])) {
                        error = false;
                        LoginMenu.editAccount(getMatcher(input, regex[27]).group(1));
                    }
                } else if (subMenuStatus == SubMenuStatus.RECIVERINFORMATION) {
                    if (input.matches(regex[27])) {
                        error = false;
                        RegisterMenu.receiverInformation(getMatcher(input, regex[27]).group(1));
                    }
                } else if (subMenuStatus == SubMenuStatus.HAVEDISCOUNT) {
                    if (input.matches(regex[27])) {
                        error = false;
                        CustomerMenu.haveDiscount(getMatcher(input, regex[27]).group(1));
                    }
                } else if (subMenuStatus == SubMenuStatus.CHECKDISCOUNTCODE) {
                    if (input.matches(regex[27])) {
                        error = false;
                        CustomerMenu.discountCodeValidation(getMatcher(input, regex[27]).group(1));
                    }
                } else if (subMenuStatus == SubMenuStatus.PAYMENT) {
                    if (input.equalsIgnoreCase("payment")) {
                        error = false;
                        CustomerMenu.payment();
                    }
                }
            //} else if (menuStatus == MenuStatus.PRODUCTSMENU) {
                //ProductsMenu
                if (subMenuStatus == SubMenuStatus.MAINMENU) {
                    if (input.equalsIgnoreCase("products")) {
                        ProductsMenu.processProducts();
                    } else if (input.matches("client.view\\s+categories")) {
                        ProductsMenu.processViewCategories();
                    } else if (input.matches("show\\s+products")) {
                        ProductsMenu.processShowProducts();
                    }

                }
                //  } else if (menuStatus == MenuStatus.SALEMENU) {
                if (input.equalsIgnoreCase("offs")) {
                    error = false;
                    SaleMenu.processOffs();
                }
                //  } else if (menuStatus == MenuStatus.PRODUCTMENU) {
                //ProductMenu
                if (subMenuStatus == SubMenuStatus.MAINMENU) {
                    if (input.equalsIgnoreCase("digest")) {
                        error = false;
                        ProductMenu.processDigest();
                    } else if (input.equalsIgnoreCase("attributes")) {
                        error = false;
                        ProductMenu.processAttributes();
                    } else if (input.matches(regex[5])) {
                        error = false;
                        ProductMenu.processCompare(getMatcher(input, regex[5]).group(1));
                    } else if (input.equalsIgnoreCase("Comments")) {
                        error = false;
                        ProductMenu.processComments();
                    }
                } else if (subMenuStatus == SubMenuStatus.DIGEST) {
                    if (input.matches("add\\s+to\\s+cart")) {
                        error = false;
                        ProductMenu.addToCart();
                    }
                } else if (subMenuStatus == SubMenuStatus.COMMENTS) {
                    if (input.matches("add\\s+comment")) {
                        error = false;
                        ProductMenu.addComments();
                    }
                    //
                } else if (subMenuStatus == SubMenuStatus.COMMENTSTITLE) {
                    error = false;
                    if (input.matches(regex[27])) {
                        ProductMenu.titleOfComment(getMatcher(input, regex[27]).group(1));
                    }
                } else if (subMenuStatus == SubMenuStatus.COMMENTSCONTENT) {
                    error = false;
                    if (input.matches(regex[27])) {
                        error = false;
                        ProductMenu.contentOfComment(getMatcher(input, regex[27]).group(1));
                    }
                }
                // } else if (menuStatus == MenuStatus.PRODUCTSMENU || menuStatus == MenuStatus.SALEMENU ) {
                if (subMenuStatus == SubMenuStatus.MAINMENU) {
                    if (input.equalsIgnoreCase("filtering")) {
                        error = false;
                        ProductsMenu.processFiltering();
                    } else if (input.equalsIgnoreCase("sorting")) {
                        error = false;
                        ProductsMenu.processSorting();
                    } else if (input.matches(regex[4])) {
                        ProductsMenu.processShowProductsID(getMatcher(input, regex[4]).group(1));
                    }
                } else if (subMenuStatus == SubMenuStatus.FILTERING) {
                    if (input.matches("show\\s+available\\s+filters ")) {
                        error = false;
                        ProductsMenu.showAvailableFilters();
                    } else if (input.matches(regex[23])) {
                        error = false;
                        ProductsMenu.filter(getMatcher(input, regex[23]).group(1));
                    } else if (input.matches("current\\s+filters")) {
                        error = false;
                        ProductsMenu.currentFilters();
                    } else if (input.matches(regex[24])) {
                        error = false;
                        ProductsMenu.disableFilter(getMatcher(input, regex[24]).group(1));
                    }
                } else if (subMenuStatus == SubMenuStatus.SORTING) {
                    if (input.matches("show\\s+available\\s+sorts")) {
                        error = false;
                        ProductsMenu.showAvailableSorts();
                    } else if (input.matches(regex[25])) {
                        error = false;
                        ProductsMenu.sort(getMatcher(input, regex[25]).group(1));
                    } else if (input.matches("current\\s+sort")) {
                        error = false;
                        ProductsMenu.currentSorts();
                    } else if (input.matches("disable\\s+sort")) {
                        error = false;
                        ProductsMenu.disableSort();
                    }
                } else if (subMenuStatus == SubMenuStatus.PERIODFILTER) {
                    if (input.matches(regex[28])) {
                        error = false;
                        ProductsMenu.periodFilter(getMatcher(input, regex[24]).group(1), getMatcher(input, regex[24]).group(2));
                    }
                } else if (subMenuStatus == SubMenuStatus.CATEGORYNAMEFILTER) {
                    if (input.matches(regex[28])) {
                        error = false;
                        ProductsMenu.categoryFilter(getMatcher(input, regex[24]).group(1));
                    }
                } else if (subMenuStatus == SubMenuStatus.FIRMFILTER) {
                    if (input.matches(regex[28])) {
                        error = false;
                        ProductsMenu.FirmFilter(getMatcher(input, regex[24]).group(1));
                    }
                } else if (subMenuStatus == SubMenuStatus.PRODUCTNAMEFILTER) {
                    if (input.matches(regex[28])) {
                        error = false;
                        ProductsMenu.productNameFilter(getMatcher(input, regex[24]).group(1));
                    }
                }
            }
        }
            if(input.equals("exit"))

        {
            OutputMassageHandler.showOutput(1);
        }



        }

      */
    }
}
