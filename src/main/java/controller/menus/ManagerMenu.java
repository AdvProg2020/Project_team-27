package controller.menus;

import controller.request.Request;
import model.accounts.Account;
import model.off.DiscountCode;
import model.productRelated.Category;
import model.productRelated.Product;
import view.InternalMenu;
import view.OutputMassageHandler;
import view.SubMenuStatus;

import java.util.ArrayList;


public class ManagerMenu {

    private int outputNo;
    private Product product;
    private DiscountCode discountCode;
    private Category category;
    private Request request;
    private DiscountCode editableDiscountCode;
    private Category editableCategory;
    private String field;
    private OutputMassageHandler outputMassageHandler = new OutputMassageHandler();
    private int detailMenu = 0;
    private DiscountCode newDiscountCode;
    private view.commandProcessor commandProcessor;

    //----------------------------------------------------------------------
    //array
    public void processManageUsers() {
        view.commandProcessor.setSubMenuStatus(SubMenuStatus.MANAGEUSERS);
    }

    private boolean checkUsername(String username) {
        if (username.matches("^(?i)(?=.[a-z])(?=.[0-9])[a-z0-9#.!@$*&_]{5,12}$")) {
            if (Account.isThereAccountWithUsername(username)) {
                return true;
            } else outputNo = 13;
        } else outputNo = 0;
        return false;
    }

    public void view(String username) {
        if (checkUsername(username)) {
            outputMassageHandler.showAccount(Account.getAccountWithUsername(username));
        } else outputMassageHandler.showAccountOutput(outputNo);
    }

    public void deleteUser(String username) {
        if (checkUsername(username)) {
            Account.deleteAccount(username);
            outputMassageHandler.showOutputWithString(username, 1);
        }
        outputMassageHandler.showAccountOutput(outputNo);
    }

    public void createManagerProfile() {
        view.commandProcessor.setSubMenuStatus(SubMenuStatus.CREATEMANAGERACCOUNT);
        RegisterMenu.setManagerWant(true);
        outputMassageHandler.showManagerMenuOutput(1);
    }
    //--------------------------------------------------------------------

    // array
    public void processManageAllProducts() {
        view.commandProcessor.setSubMenuStatus(SubMenuStatus.MANAGEALLPRODUCTS);
    }

    private boolean checkProduct(String productID) {
        if (productID.matches("")) {
            if (product.isThereProductWithId(productID)) {
                return true;
            } else outputNo = 3;
        } else outputNo = 2;
        outputMassageHandler.showAccountOutput(outputNo);
        return false;
    }

    public void removeProduct(String productID) {
        if (checkProduct(productID)) {
            product.deleteProduct(productID);
            outputMassageHandler.showOutputWithString(productID, 2);
        }
    }
    //------------------------------------------------

    private boolean checkDiscountCode(String discountCodeID) {
        if (discountCodeID.matches("")) {
            if (discountCode.isThereDiscountWithId(discountCodeID)) {
                return true;
            } else outputNo = 5;
        } else outputNo = 4;
        outputMassageHandler.showAccountOutput(outputNo);
        return false;
    }

    public void processCreateDiscountCode() {
        view.commandProcessor.setSubMenuStatus(SubMenuStatus.ADDDISCOUNTCODE);
        outputMassageHandler.showAccountOutput(6);
    }

    public void createNewDiscountCode(String discountCodeId) {
        if (checkDiscountCode(discountCodeId)) {
            DiscountCode newDiscountCode = new DiscountCode(discountCodeId);
            commandProcessor.setInternalMenu(InternalMenu.CHANGEDETAILS);
            view.commandProcessor.setSubMenuStatus(SubMenuStatus.DETAILDESCOUNTCODE);
            outputMassageHandler.showAccountOutput(7);
        }
    }

    //exception for parse!!
    public void setDetailToDiscountCode(String detail) {
        /*
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
                newDiscountCode.setMaxDiscountAmount(Integer.parseInt(detail));
                outputNo = 13;
                detailMenu = 1;
            } else outputNo = 12;
        } else if (detailMenu == 3) {
            if (detail.matches("\\d+")) {
                newDiscountCode.setTotalTimesOfUse(Integer.parseInt(detail));
              //  inputNo = 15;
                detailMenu = 0;
                CommandProcessor.setSubMenuStatus(SubMenuStatus.MAINMENU);
                 commandProcessor.setInternalMenu(InternalMenu.MAINMENU);

            } else outputNo = 14;
        }

         */

    }

    // array
    public void processViewDiscountCodes() {
        view.commandProcessor.setSubMenuStatus(SubMenuStatus.VIEWDISCOUNTCODES);
    }

    public void viewDiscountCode(String discountCodeID) {
        if (checkDiscountCode(discountCodeID)) {
            outputMassageHandler.showDiscountCode(discountCode.getDiscountWithId(discountCodeID));
        }
    }

    public void editDiscountCode(String discountCodeID) {
        if (checkDiscountCode(discountCodeID)) {
            editableDiscountCode = discountCode.getDiscountWithId(discountCodeID);
            view.commandProcessor.setSubMenuStatus(SubMenuStatus.DISCOUNTCODEFIELD);
            outputMassageHandler.showAccountOutput(15);
        }
    }

    public void discountCodeField(String field) {
        if (field.matches("(?i)(?:||||)")) {
            this.field = field;
            view.commandProcessor.setSubMenuStatus(SubMenuStatus.EDITDISCOUNTCODE);
            outputMassageHandler.showOutputWithString(field, 3);
        }
    }

    ///????????ParseException
    public void editDiscountCodeField(String edit) {
        /*
        if (field.equalsIgnoreCase("start Of Discount Period")) {
            if (edit.matches("([0-2][0-9]|3[0-1])/([0-9]|1[0-2])/20[0-5][0-9]")) {
                Date currentDate=new Date();
                Date inputDate=new SimpleDateFormat("dd/MM/yyyy").parse(edit);
                if (inputDate.after(currentDate)){
                    editableDiscountCode.setStartOfDiscountPeriod(inputDate) ;
                    outputNo = 16;
                }
            } else outputNo = 8;
        } else if (field.equalsIgnoreCase("end Of Discount Period")) {
            if (edit.matches("([0-2][0-9]|3[0-1])/([0-9]|1[0-2])/20[0-5][0-9]")) {
                Date currentDate=new Date();
                Date inputDate=new SimpleDateFormat("dd/MM/yyyy").parse(edit);
                if (inputDate.after(currentDate)){
                    editableDiscountCode.setEndOfDiscountPeriod(inputDate);
                    outputNo = 17;
                }
            } else outputNo = 10;
        } else if (field.equalsIgnoreCase("max Discount Amount")) {
            if (edit.matches("\\d+\\.\\d*")) {
                editableDiscountCode.setMaxDiscountAmount(Double.parseDouble(edit));
                outputNo = 18;
            } else outputNo = 12;
        } else if (field.equalsIgnoreCase("total Times Of Use")) {
            if (edit.matches("\\d+")) {
                editableDiscountCode.setTotalTimesOfUse(Integer.parseInt(edit));
                outputNo = 19;
            } else outputNo = 14;
        }


         */
    }


    public void removeDiscountCode(String discountCodeID) {
        if (checkDiscountCode(discountCodeID)) {
            discountCode.deleteDiscount(discountCodeID);
            outputMassageHandler.showOutputWithString(discountCodeID, 4);
        }
    }
    //-------------------------------------------------------------------------------------

    private boolean checkRequest(String requestID) {
        if (requestID.matches("")) {
            if (request.isThereRequestFromID(requestID)) {
                return true;
            } else outputNo = 21;
        } else outputNo = 20;
        outputMassageHandler.showAccountOutput(outputNo);
        return false;
    }

    /// array
    public void processManageRequests() {
        view.commandProcessor.setSubMenuStatus(SubMenuStatus.MANAGEREQUESTS);
    }

    public void detailsRequest(String requestID) {
        if (checkRequest(requestID)) {
            outputMassageHandler.showRequest(request.getRequestFromID(requestID));
        }
    }

    public void acceptRequest(String requestID) {
        if (checkRequest(requestID)) {
            request.acceptRequest(requestID);
            outputMassageHandler.showOutputWithString(requestID, 4);
        }
    }

    public void declineRequest(String requestID) {
        if (checkRequest(requestID)) {
            request.declineRequest(requestID);
            outputMassageHandler.showOutputWithString(requestID, 4);
        }
    }
    //------------------------------------------------------------------------------

    private boolean checkCategory(String category) {
        if (category.matches("")) {
            //if (category.isThereCategoryWithName(category)) {
            return true;
            // } else inputNo =23;
        } else outputNo = 22;
        return false;
    }

    // array
    public void processManageCategories() {
        view.commandProcessor.setSubMenuStatus(SubMenuStatus.MANAGECATEGORIES);

    }

    public void editCategory(String category) {
        if (checkCategory(category)) {
           // editableCategory = category.getCa
            view.commandProcessor.setSubMenuStatus(SubMenuStatus.CATEGORYFIELD);

        }
    }

    public void categoryField(String field) {
        if (field.matches("(?i)(?:||||)")) {
            this.field = field;
            view.commandProcessor.setSubMenuStatus(SubMenuStatus.EDITCATEGORY);
            outputMassageHandler.showOutputWithString(field, 3);
        }
    }

    public void editCategoryField(String edit) {
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

    public void addCategory(String category) {
        if (checkCategory(category)) {
            // Category newCategory = new Category(category);
            view.commandProcessor.setSubMenuStatus(SubMenuStatus.DETAILCATEGORY);
            commandProcessor.setInternalMenu(InternalMenu.CHANGEDETAILS);
            // outputHandler.showAccountOutput();
        }
    }

    public void setDetailToCategory(String detail) {

        if (field.equalsIgnoreCase("name")) {
            if (detail.matches("\\D+")) {
                category.setName(detail);
            }
        } else if (field.equalsIgnoreCase("traits")) {
            if (detail.matches("\\D+")) {
                category.setTraits(detail);
            }
        } else if (field.equalsIgnoreCase("product")) {
            if (detail.matches("\\D+")) {
                ArrayList<Product> products = category.getAllProducts();
                products.add(product.getProductWithName(detail));
                // category.setAllProducts(products);
                detailMenu = 0;
                commandProcessor.setInternalMenu(InternalMenu.MAINMENU);
                commandProcessor.setSubMenuStatus(SubMenuStatus.MANAGECATEGORIES);
            }
        }


    }


    public void removeCategory(String category) {
        if (checkCategory(category)) {
            // category.deleteCategory(category);
            outputMassageHandler.showOutputWithString(category, 7);
        }
    }

}