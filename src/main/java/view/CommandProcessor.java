package view;

import controller.menus.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandProcessor {
    private static MenuStatus menuStatus = MenuStatus.MAINMENU;
    private static SubMenuStatus subMenuStatus = SubMenuStatus.MAINMENU;
    private static InternalMenu internalMenu = InternalMenu.MAINMENU;
    private boolean error = true;
    //
    //


    public static MenuStatus getMenuStatus() {
        return menuStatus;
    }

    public static void setMenuStatus(MenuStatus menuStatus) {
        CommandProcessor.menuStatus = menuStatus;
    }

    public static SubMenuStatus getSubMenuStatus() {
        return subMenuStatus;
    }

    public static void setSubMenuStatus(SubMenuStatus subMenuStatus) {
        CommandProcessor.subMenuStatus = subMenuStatus;
    }

    public static InternalMenu getInternalMenu() {
        return internalMenu;
    }

    public static void setInternalMenu(InternalMenu internalMenu) {
        CommandProcessor.internalMenu = internalMenu;
    }

    private String[] regex = {"(?i)create\\s+account\\s+(.+)\\s+(.+)",
            "(?i)login\\s+(.+)",//1
            "(?i)edit\\s+(.+)",//2
            "(?i)remove\\s+product\\s+(.+)",//3
            "(?i)show\\s+product\\s+(.+)",//4
            "(?i)compare\\s+(.+)",//5
            "(?i)view\\s+(.+)",//6
            "(?i)delete\\s+user(.+)",//7
            "(?i)remove\\s+(.+)",//8
            "(?i)view\\s+discount\\s+code(.+)",//9
            "(?i)edit\\s+discount\\s+code(.+)",//10
            "(?i)remove\\s+discount\\s+code(.+)",//11
            "(?i)details\\s+(.+)",//12
            "(?i)accept\\s+(.+)",//13
            "(?i)decline\\s+(.+)",//14
            "(?i)edit\\s+(.+)",//15
            "(?i)add\\s+(.+)",//16
            "(?i)remove\\s+(.+)",//17
            "(?i)view\\s+buyers\\s+(.+)",//18
            "(?i)increase(.+)",//19
            "(?i)decrease(.+)",//20
            "(?i)show\\s+order(.+)",//21
            "(?i)rate(.+) (\\d+)",//22
            "(?i)filter(.+)",//23
            "(?i)disable\\s+filter",//24
            "(?i)sort(.+)",//25
            "(?i)select seller",//26
            "(.+)",//27
            ""


    };

    private Matcher getMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        matcher.find();
        return matcher;
    }

    public void run() throws IOException, ParseException {
        Scanner scanner = new Scanner(System.in);
        String input;
        while (!(input = scanner.nextLine()).trim().equalsIgnoreCase("exit")) {
           if (input.equalsIgnoreCase("back")) {
                error = false;
                MenuSituation.processBack();
            } else if (input.equalsIgnoreCase("help")) {
                error = false;
                MenuSituation.processHelp();
            } else if (subMenuStatus == subMenuStatus.PASSWORD) {
                if (input.matches(regex[27])) {
                    error = false;
                    LoginMenu.checkPassword(getMatcher(input, regex[27]).group(1));
                }
            } else if (subMenuStatus == subMenuStatus.REGISTERATIONDETAILS) {
                if (input.matches(regex[27])) {
                    error = false;
                    RegisterMenu.completeRegisterProcess(getMatcher(input, regex[27]).group(1));
                }
            } else if (menuStatus == MenuStatus.MAINMENU) {
                //SaleMenu
                if (input.equalsIgnoreCase("offs")) {
                    error = false;
                    SaleMenu.processOffs();
                } else if (input.equalsIgnoreCase("products")) {
                    error = false;
                    ProductsMenu.processProducts();
                }  else if (internalMenu == InternalMenu.MAINMENU) {
                   if (input.matches(regex[1])) {
                       error = false;
                       LoginMenu.processLogin(getMatcher(input, regex[1]).group(1));
                   } else if (input.equalsIgnoreCase("logout")) {
                       error = false;
                       LoginMenu.processLogout();
                   } else if (input.matches(regex[0])) {
                       error = false;
                       RegisterMenu.processRegister(getMatcher(input, regex[0]).group(1), getMatcher(input, regex[0]).group(2));
                   }
               }
            } else if (menuStatus == MenuStatus.MANAGERMENU) {
                //ManagerMenu
                if (subMenuStatus == SubMenuStatus.MAINMENU) {
                    if (input.equalsIgnoreCase("manage users")) {
                        error = false;
                        ManagerMenu.processManageUsers();
                    } else if (input.equalsIgnoreCase("view personal info")) {
                        error = false;
                        LoginMenu.viewPersonalInfo();
                    } else if (input.equalsIgnoreCase("manage all products")) {
                        error = false;
                        ManagerMenu.processManageAllProducts();
                    } else if (input.equalsIgnoreCase("create discount code")) {
                        error = false;
                        ManagerMenu.processCreateDiscountCode();
                    } else if (input.equalsIgnoreCase("view discount codes")) {
                        error = false;
                        ManagerMenu.processViewDiscountCodes();
                    } else if (input.equalsIgnoreCase("manage requests")) {
                        error = false;
                        ManagerMenu.processManageRequests();
                    } else if (input.equalsIgnoreCase("manage categories")) {
                        error = false;
                        ManagerMenu.processManageCategories();
                    } else if (subMenuStatus == SubMenuStatus.VIEWPERSONALINFO) {
                        if (input.matches(regex[2])) {
                            error = false;
                            LoginMenu.processEdit(getMatcher(input, regex[2]).group(1));
                        }
                    }
                } else if (subMenuStatus == SubMenuStatus.MANAGEUSERS) {
                    if (input.matches(regex[6])) {
                        error = false;
                        ManagerMenu.view(getMatcher(input, regex[6]).group(1));
                    } else if (input.matches(regex[7])) {
                        error = false;
                        ManagerMenu.deleteUser(getMatcher(input, regex[7]).group(1));
                    } else if (input.equalsIgnoreCase(" create manager profile ")) {
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
                    if (input.matches(regex[15])) {
                        error = false;
                        ManagerMenu.createNewDiscountCode(getMatcher(input, regex[16]).group(1));
                    }
                } else if (subMenuStatus.equals(SubMenuStatus.DETAILDESCOUNTCODE)) {
                    if (input.matches(regex[15])) {
                        error = false;
                        ManagerMenu.setDetailToDiscountCode(getMatcher(input, regex[16]).group(1));
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

            } else if (menuStatus == MenuStatus.SELLERMENU) {
                // SellerMenu
                if (subMenuStatus == SubMenuStatus.MAINMENU) {
                    if (input.equalsIgnoreCase("view company information")) {
                        error = false;
                        SellerMenu.processViewCompanyInformation();
                    } else if (input.equalsIgnoreCase("view personal info")) {
                        error = false;
                        LoginMenu.viewPersonalInfo();
                    } else if (input.equalsIgnoreCase("view sales history")) {
                        error = false;
                        SellerMenu.processViewSalesHistory();
                    } else if (input.equalsIgnoreCase("manage products")) {
                        error = false;
                        SellerMenu.processManageProducts();
                    } else if (input.equalsIgnoreCase("add product")) {
                        error = false;
                        SellerMenu.processAddProduct();
                    } else if (input.matches(regex[3])) {
                        error = false;
                        SellerMenu.processRemoveProduct(getMatcher(input, regex[3]).group(1));
                    } else if (input.equalsIgnoreCase("show categories")) {
                        error = false;
                        SellerMenu.processShowCategories();
                    } else if (input.equalsIgnoreCase("view offs")) {
                        error = false;
                        SellerMenu.processViewOffs();
                    } else if (input.equalsIgnoreCase("view balance")) {
                        error = false;
                        SellerMenu.processViewBalance();
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
                    } else if (input.equalsIgnoreCase("add off")) {
                        error = false;
                        SellerMenu.addOff();
                    }
                } else if (subMenuStatus == SubMenuStatus.VIEWPERSONALINFO) {
                    if (input.matches(regex[2])) {
                        error = false;
                        LoginMenu.processEdit(getMatcher(input, regex[2]).group(1));
                    }
                }
                ///$$$$$$$$$$$$$
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
                }
                if (subMenuStatus == subMenuStatus.ADDFIRM) {
                    if (input.matches(regex[27])) {
                        error = false;
                        RegisterMenu.createFirm(getMatcher(input, regex[27]).group(1));
                    }
                }


            } else if (menuStatus == MenuStatus.CUSTOMERMENU) {
                //CustomerMenu
                if (subMenuStatus == SubMenuStatus.MAINMENU) {
                    if (input.equalsIgnoreCase("view cart")) {
                        error = false;
                        CustomerMenu.processViewCart();
                    } else if (input.equalsIgnoreCase("view personal info")) {
                        error = false;
                        LoginMenu.viewPersonalInfo();
                    } else if (input.equalsIgnoreCase("purchase")) {
                        error = false;
                        CustomerMenu.processPurchase();
                    } else if (input.equalsIgnoreCase("view orders")) {
                        error = false;
                        CustomerMenu.processViewOrders();
                    } else if (input.equalsIgnoreCase("view balance")) {
                        error = false;
                        CustomerMenu.processViewBalance();
                    } else if (input.equalsIgnoreCase("view discount codes")) {
                        error = false;
                        CustomerMenu.processViewDiscountCodes();
                    }
                } else if (subMenuStatus == SubMenuStatus.VIEWCART) {
                    if (input.equalsIgnoreCase("show products")) {
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
                    } else if (input.equalsIgnoreCase("show total price")) {
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
                        CustomerMenu.rateProduct(getMatcher(input, regex[22]).group(1), Integer.parseInt(getMatcher(input, regex[22]).group(2)));
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
                }
            }
            //ReceiverInformation
            else if (menuStatus == MenuStatus.PURCHASE) {
                if (subMenuStatus == SubMenuStatus.RECIVERINFORMATION) {
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
                    if (input.matches(regex[27])) {
                        error = false;
                        //   CustomerMenu.payment(getMatcher(input, regex[27]).group(1));
                    }
                }
            }
                /*if (menuStatus == MenuStatus.PRODUCTSMENU || menuStatus == MenuStatus.MAINMENU) {
                    //ProductsMenu
                    if (subMenuStatus == SubMenuStatus.MAINMENU) {
                        if (input.equalsIgnoreCase("products")) {
                            productsMenu.processProducts();
                        } else if (input.equalsIgnoreCase("view categories")) {
                            productsMenu.processViewCategories();
                        } else if (input.equalsIgnoreCase("filtering")) {
                            productsMenu.processFiltering();
                        } else if (input.equalsIgnoreCase("sorting")) {
                            productsMenu.processSorting();
                        } else if (input.equalsIgnoreCase("show products")) {
                            productsMenu.processShowProducts();
                        } else if (input.matches(regex[4])) {
                            productsMenu.processShowProductsID(getMatcher(input, regex[4]).group(1));
                        }
                    } else if (subMenuStatus == SubMenuStatus.FILTERING) {
                        if (input.equalsIgnoreCase("show available filters ")) {
                            productsMenu.showAvailableFilters();
                        } else if (input.matches(regex[23])) {
                            productsMenu.filter(getMatcher(input, regex[23]).group(1));
                        } else if (input.equalsIgnoreCase("current filters")) {
                            productsMenu.currentFilters();
                        } else if (input.matches(regex[24])) {
                            productsMenu.disableFilter(getMatcher(input, regex[24]).group(1));
                        }
                    } else if (subMenuStatus == SubMenuStatus.SORTING) {
                        if (input.equalsIgnoreCase("show available sorts")) {
                            productsMenu.showAvailableSorts();
                        } else if (input.matches(regex[25])) {
                            productsMenu.sort(getMatcher(input, regex[25]).group(1));
                        } else if (input.equalsIgnoreCase("current sort")) {
                            productsMenu.currentSorts();
                        } else if (input.equalsIgnoreCase("disable sort")) {
                            productsMenu.disableSort();
                        }
                    } /*else if (input.equalsIgnoreCase("purchase")) {
                        customerMenu.processPurchase();
                    }
                    */

            else if (menuStatus == MenuStatus.PRODUCTMENU) {
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
                    if (input.equalsIgnoreCase("add to cart")) {
                        error = false;
                        ProductMenu.addToCart();
                    } else if (input.matches(regex[25])) {
                        error = false;
                        ProductMenu.selectSeller(getMatcher(input, regex[25]).group(1));
                    }
                } else if (subMenuStatus == SubMenuStatus.COMMENTS) {
                    if (input.equalsIgnoreCase("add comment")) {
                        error = false;
                        ProductMenu.addComments();
                    }
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
            } else if (menuStatus == MenuStatus.PRODUCTSMENU || menuStatus == MenuStatus.SALEMENU) {
                if (input.equalsIgnoreCase("filtering")) {
                    error = false;
                    ProductsMenu.processFiltering();
                } else if (input.equalsIgnoreCase("sorting")) {
                    error = false;
                    ProductsMenu.processSorting();
                } else if (subMenuStatus == SubMenuStatus.FILTERING) {
                    if (input.equalsIgnoreCase("show available filters ")) {
                        error = false;
                        ProductsMenu.showAvailableFilters();
                    } else if (input.matches(regex[23])) {
                        error = false;
                        ProductsMenu.filter(getMatcher(input, regex[23]).group(1));
                    } else if (input.equalsIgnoreCase("current filters")) {
                        error = false;
                        ProductsMenu.currentFilters();
                    } else if (input.matches(regex[24])) {
                        error = false;
                        ProductsMenu.disableFilter(getMatcher(input, regex[24]).group(1));
                    }
                } else if (subMenuStatus == SubMenuStatus.SORTING) {
                    if (input.equalsIgnoreCase("show available sorts")) {
                        error = false;
                        ProductsMenu.showAvailableSorts();
                    } else if (input.matches(regex[25])) {
                        error = false;
                        ProductsMenu.sort(getMatcher(input, regex[25]).group(1));
                    } else if (input.equalsIgnoreCase("current sort")) {
                        error = false;
                        ProductsMenu.currentSorts();
                    } else if (input.equalsIgnoreCase("disable sort")) {
                        error = false;
                        ProductsMenu.disableSort();
                    }
                }
            } else if (error == true) {
                OutputMassageHandler.showOutput(0);
            }
        }
        if (input.equals("exit")) {
            OutputMassageHandler.showOutput(1);
        }


    }
}

