package controller.menus;

import controller.request.Request;
import model.accounts.Account;
import model.filtar.Filter;
import model.off.DiscountCode;
import model.productRelated.Category;
import model.productRelated.Product;
import view.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class ManagerMenu {

    private static int outputNo;
    private static DiscountCode editableDiscountCode;
    private static Category editableCategory;
    private static String field;
    private static int detailMenu = 0;
    private static DiscountCode newDiscountCode;
    private static Category newCategory;

    public static String getField() {
        return field;
    }

    public static int getDetailMenu() {
        return detailMenu;
    }

    //gson
    public static void processManageUsers() throws FileNotFoundException {
        OutputHandler.showAccounts();
        CommandProcessor.setSubMenuStatus(SubMenuStatus.MANAGEUSERS);
    }

    private static boolean checkUsername(String username) {
        if (username.matches("^(?i)(?=.[a-z])(?=.[0-9])[a-z0-9#.!@$*&_]{5,12}$")) {
            if (Account.isThereAccountWithUsername(username)) {
                return true;
            } else outputNo = 13;
        } else outputNo = 0;
        return false;
    }

    //gson
    public static void view(String username) throws FileNotFoundException {
        if (checkUsername(username)) {
            OutputHandler.showAccountInformation(username);
        } else OutputMassageHandler.showAccountOutput(outputNo);
    }

    public static void deleteUser(String username) {
        if (checkUsername(username)) {
            Account.deleteAccount(username);
            OutputMassageHandler.showOutputWithString(username, 1);
        }
        OutputMassageHandler.showAccountOutput(outputNo);
    }

    public static void createManagerProfile() {
        CommandProcessor.setSubMenuStatus(SubMenuStatus.CREATEMANAGERACCOUNT);
        RegisterMenu.setManagerWant(true);
        OutputMassageHandler.showManageOutput(1);
    }
    //--------------------------------------------------------------------

    //gson
    public static void processManageAllProducts() throws FileNotFoundException {
        OutputHandler.showProducts(Filter.getNewArrayOfProductFilter());
        CommandProcessor.setSubMenuStatus(SubMenuStatus.MANAGEALLPRODUCTS);
    }

    private static boolean checkProduct(String productID) {
        // if (productID.matches("")) {
        if (Product.isThereProductWithId(productID)) {
            return true;
        } else outputNo = 3;
        // } else outputNo = 2;
        OutputMassageHandler.showAccountOutput(outputNo);
        return false;
    }

    public static void removeProduct(String productID) {
        if (checkProduct(productID)) {
            Product.deleteProduct(productID);
            OutputMassageHandler.showOutputWithString(productID, 2);
        }
    }
    //------------------------------------------------

    private static boolean checkDiscountCode(String discountCodeID) {
        //if (discountCodeID.matches("")) {
        if (DiscountCode.isThereDiscountWithId(discountCodeID)) {
            return true;
        } else outputNo = 5;
        // } else outputNo = 4;
        OutputMassageHandler.showAccountOutput(outputNo);
        return false;
    }

    public static void processCreateDiscountCode() {
        CommandProcessor.setSubMenuStatus(SubMenuStatus.ADDDISCOUNTCODE);
        OutputMassageHandler.showAccountOutput(6);
    }

    public static void createNewDiscountCode(String discountCodeId) throws IOException {
        if (!DiscountCode.isThereDiscountWithId(discountCodeId)) {
            newDiscountCode = new DiscountCode(discountCodeId);
            CommandProcessor.setInternalMenu(InternalMenu.CHANGEDETAILS);
            CommandProcessor.setSubMenuStatus(SubMenuStatus.DETAILDESCOUNTCODE);
            OutputMassageHandler.showAccountOutput(7);
        }else outputNo=0;
    }

    //exception for parse!!
    public static void setDetailToDiscountCode(String detail) throws ParseException {
        if (detailMenu == 0) {
            if (detail.matches("([0-2][0-9]|3[0-1])/([0-9]|1[0-2])/20[0-5][0-9]")) {
                Date currentDate=new Date();
                Date inputDate=new SimpleDateFormat("dd/MM/yyyy").parse(detail);
                if (inputDate.after(currentDate)) {
                    newDiscountCode.setStartOfDiscountPeriod(inputDate);
                    outputNo = 9;
                    detailMenu = 1;
                }
            } else outputNo = 8;
            //END???
        } else if (detailMenu == 1) {
            if (detail.matches("([0-2][0-9]|3[0-1])/([0-9]|1[0-2])/20[0-5][0-9]")) {
                Date currentDate=new Date();
                Date inputDate=new SimpleDateFormat("dd/MM/yyyy").parse(detail);
                if (inputDate.after(currentDate)) {
                    newDiscountCode.setStartOfDiscountPeriod(inputDate);
                    outputNo = 11;
                    detailMenu = 1;
                }
            } else outputNo = 10;
        } else if (detailMenu == 2) {
            if (detail.matches("\\d+")) {
                newDiscountCode.setMaxDiscountAmount(Double.parseDouble(detail));
                outputNo = 13;
                detailMenu = 1;
            } else outputNo = 12;
        } else if (detailMenu == 3) {
            if (detail.matches("\\d+")) {
                newDiscountCode.setTotalTimesOfUse(Integer.parseInt(detail));
                //  inputNo = 15;
                detailMenu = 0;
                CommandProcessor.setSubMenuStatus(SubMenuStatus.MAINMENU);
                CommandProcessor.setInternalMenu(InternalMenu.MAINMENU);

            } else outputNo = 14;
            //
        }else if (detailMenu == 4) {
            if (detail.matches("\\d+")) {
                newDiscountCode.setDiscountAmount(Integer.parseInt(detail));
                //  inputNo = 15;
                detailMenu = 0;
                CommandProcessor.setSubMenuStatus(SubMenuStatus.MAINMENU);
                CommandProcessor.setInternalMenu(InternalMenu.MAINMENU);

            } else outputNo = 14;
        }else if (detailMenu == 5) {
            if(detail.equalsIgnoreCase("")) {
                if (detail.matches("\\d+")) {
                    if (Account.isThereAccountWithUsername(detail)) {
                        newDiscountCode.addAccount(Account.getAccountWithUsername(detail));
                        //  inputNo = 15;
                        detailMenu = 0;
                        CommandProcessor.setSubMenuStatus(SubMenuStatus.MAINMENU);
                        CommandProcessor.setInternalMenu(InternalMenu.MAINMENU);
                    } else outputNo = 14;
                } else outputNo = 14;
            }
        }



    }

    // array
    public static void processViewDiscountCodes() throws FileNotFoundException {
        OutputHandler.showDiscountCodes();
        CommandProcessor.setSubMenuStatus(SubMenuStatus.VIEWDISCOUNTCODES);
    }

    //moshkel dare
    public static void viewDiscountCode(String discountCodeID) throws FileNotFoundException {
        OutputHandler.showDiscountCode(discountCodeID);
        if (checkDiscountCode(discountCodeID)) {
            OutputHandler.showDiscountCode(discountCodeID);
        }
    }

    public static void editDiscountCode(String discountCodeID) {
        if (checkDiscountCode(discountCodeID)) {
            editableDiscountCode = DiscountCode.getDiscountWithId(discountCodeID);
            CommandProcessor.setSubMenuStatus(SubMenuStatus.DISCOUNTCODEFIELD);
            OutputMassageHandler.showAccountOutput(15);
        }
    }

    public static void discountCodeField(String field) {
        if (field.matches("(?i)(?:||||)")) {
            ManagerMenu.field = field;
            CommandProcessor.setSubMenuStatus(SubMenuStatus.EDITDISCOUNTCODE);
            OutputMassageHandler.showOutputWithString(field, 3);
        }
    }

    ///????????ParseException
    public static void editDiscountCodeField(String edit) throws ParseException {
        if (field.matches("(?i)start\\s+Of\\s+Discount\\s+Period")) {
            if (edit.matches("([0-2][0-9]|3[0-1])/([0-9]|1[0-2])/20[0-5][0-9]")) {
                Date currentDate=new Date();
                Date inputDate=new SimpleDateFormat("dd/MM/yyyy").parse(edit);
                if (inputDate.after(currentDate)){
                    editableDiscountCode.setStartOfDiscountPeriod(inputDate) ;
                    outputNo = 16;
                }
            } else outputNo = 8;
        } else if (field.matches("(?i)end\\s+Of\\s+Discount\\s+Period")) {
            if (edit.matches("([0-2][0-9]|3[0-1])/([0-9]|1[0-2])/20[0-5][0-9]")) {
                Date currentDate=new Date();
                Date inputDate=new SimpleDateFormat("dd/MM/yyyy").parse(edit);
                if (inputDate.after(currentDate)){
                    editableDiscountCode.setEndOfDiscountPeriod(inputDate);
                    outputNo = 17;
                }
            } else outputNo = 10;
        } else if (field.matches("(?i)max\\s+Discount\\s+Amount")) {
            if (edit.matches("\\d+\\.\\d*")) {
                editableDiscountCode.setMaxDiscountAmount(Double.parseDouble(edit));
                outputNo = 18;
            } else outputNo = 12;
        } else if (field.matches("(?i)total\\s+Times\\s+Of\\s+Use")) {
            if (edit.matches("\\d+")) {
                editableDiscountCode.setTotalTimesOfUse(Integer.parseInt(edit));
                outputNo = 19;
            } else outputNo = 14;
            //
        } else if (field.matches("(?i)discount\\s+amount")) {
            if (edit.matches("\\d+")) {
                editableDiscountCode.setDiscountAmount(Integer.parseInt(edit));
                outputNo = 19;
            } else outputNo = 14;
        } else if (field.matches("(?i)add\\s+account")) {
            if (edit.matches("\\d+")) {
                if(Account.isThereAccountWithUsername(edit)) {
                    editableDiscountCode.addAccount(Account.getAccountWithUsername(edit));
                    outputNo = 19;
                }
            } else outputNo = 14;
        } else if (field.matches("(?i)remove\\s+account")) {
            if (edit.matches("\\d+")) {
                if(Account.isThereAccountWithUsername(edit)) {
                    editableDiscountCode.removeAccount(Account.getAccountWithUsername(edit));
                    outputNo = 19;
                }
            } else outputNo = 14;
        }



    }


    public static void removeDiscountCode(String discountCodeID) {
        if (checkDiscountCode(discountCodeID)) {
            DiscountCode.deleteDiscount(discountCodeID);
            OutputMassageHandler.showOutputWithString(discountCodeID, 4);
        }
    }
    //-------------------------------------------------------------------------------------

    private static boolean checkRequest(String requestID) {
        // if (requestID.matches("")) {
        if (Request.isThereRequestFromID(requestID)) {
            return true;
        } else outputNo = 21;
        // } else outputNo = 20;
        OutputMassageHandler.showAccountOutput(outputNo);
        return false;
    }

    /// array
    public static void processManageRequests() throws FileNotFoundException {
        CommandProcessor.setSubMenuStatus(SubMenuStatus.MANAGEREQUESTS);
        OutputHandler.showRequests();
    }

    //moshkel dare
    public static void detailsRequest(String requestID) throws FileNotFoundException {
        //OutputHandler.showRequest();
        if (checkRequest(requestID)) {
            OutputHandler.showRequest(requestID);
        }
    }

    //moshkel dare
    public static void acceptRequest(String requestID) {
        if (checkRequest(requestID)) {
            //Request.acceptRequest(requestID);
            OutputMassageHandler.showOutputWithString(requestID, 4);
        }
    }

    public static void declineRequest(String requestID) {
        if (checkRequest(requestID)) {
            Request.declineRequest(requestID);
            OutputMassageHandler.showOutputWithString(requestID, 4);
        }
    }
    //------------------------------------------------------------------------------

    private static boolean checkCategory(String category) {
        if (category.matches("")) {
            //if (category.isThereCategoryWithName(category)) {
            return true;
            // } else inputNo =23;
        } else outputNo = 22;
        return false;
    }

    // array
    public static void processManageCategories() throws FileNotFoundException {
        OutputHandler.showCategories();
        CommandProcessor.setSubMenuStatus(SubMenuStatus.MANAGECATEGORIES);

    }

    public static void editCategory(String category) {
        if (checkCategory(category)) {
            editableCategory = Category.getCategoryWithName(category);
            CommandProcessor.setSubMenuStatus(SubMenuStatus.CATEGORYFIELD);
            outputNo= 0;

        }
    }

    public static void categoryField(String field) {
        if (field.matches("(?i)(?:||||)")) {
            ManagerMenu.field = field;
            CommandProcessor.setSubMenuStatus(SubMenuStatus.EDITCATEGORY);
            OutputMassageHandler.showOutputWithString(field, 3);
        }
    }

    public static void editCategoryField(String edit) {
       /* if (field.equalsIgnoreCase("trait")) {
            if (field.matches("\\D")) {
            editableCategory.setTraits(edit);
            }
        }else if (field.equalsIgnoreCase("product")) {
            if (edit.matches("")) {
                ArrayList<Product> products = category.getAllProducts();
                products.add(product.getProductWithName(edit));
                category.setAllProducts(products);
            }
        }
        */

    }

    public static void addCategory(String category) throws IOException {
        if(!(newCategory.isThereCategoryWithName(category))) {
            newCategory = new Category(category);
            CommandProcessor.setSubMenuStatus(SubMenuStatus.DETAILCATEGORY);
            CommandProcessor.setInternalMenu(InternalMenu.CHANGEDETAILS);
            outputNo = 0;
        }else outputNo=0;

    }

    public static void setDetailToCategory(String detail) {
        //oon bala dade!
       /* if (field.equalsIgnoreCase("name")) {
            if (detail.matches("\\D+")) {
                if(newCategory.isThereCategoryWithName(detail)) {
                    newCategory = new Category(detail);
                }else outputNo=0;
            }
        }  */
        if (field.equalsIgnoreCase("traits")) {
            if (detail.matches("\\D+")) {
                newCategory.setTraits(detail);
            }
        } else if (field.equalsIgnoreCase("product")) {
            if (detail.matches("\\D+")) {
                ArrayList<Product> products = newCategory.getAllProducts();
                products.add(Product.getProductWithName(detail));
                // category.setAllProducts(products);
                detailMenu = 0;
                CommandProcessor.setInternalMenu(InternalMenu.MAINMENU);
                CommandProcessor.setSubMenuStatus(SubMenuStatus.MANAGECATEGORIES);
            }
        }


    }


    public static void removeCategory(String category) {
        if (checkCategory(category)) {
            // category.deleteCategory(category);
            OutputMassageHandler.showOutputWithString(category, 7);
        }
    }

}