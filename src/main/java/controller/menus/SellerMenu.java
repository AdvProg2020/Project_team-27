package controller.menus;

import controller.request.Request;
import model.firms.Firm;
import model.off.Sale;
import model.off.SaleStatus;
import model.productRelated.Product;
import view.CommandProcessor;
import view.OutputHandler;
import view.SubMenuStatus;
import model.accounts.Seller;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.text.SimpleDateFormat;
import java.util.Date;
public class SellerMenu {
    private int outputNo;
    private int inputNo;
    private Sale sale;
    private Product product;
    private Firm firm;
    private String field;
    private int detailMenu = 0;
    private CommandProcessor commandProcessor;
    private OutputHandler outputHandler = new OutputHandler();
    private LoginMenu loginMenu;
    private Product productToEdit;
    private Sale saleToEdit;
    private Seller seller;

    //?
    public void processViewCompanyInformation() {
        // outputHandler.showFirm(loginMenu.getLoginAccount().getFirm());
    }

    //array
    public void processViewSalesHistory() {

    }

    //--------------------------------------------------------------------------------------
    // manager // customer // seller
    private boolean checkProduct(String productID) {
        if (productID.matches("")) {
            if (product.isThereProductWithId(productID)) {
                return true;
            } else inputNo = 3;
        } else inputNo = 2;
        outputHandler.showAccountOutput(inputNo);
        return false;
    }

    //array
    public void processManageProducts() {
        commandProcessor.setSubMenuStatus(SubMenuStatus.MANAGEPRODUCTS);

    }

    public void viewProduct(String productID) {
        if (checkProduct(productID)) {
            outputHandler.showProduct(product.getProductById(productID));
        }
        outputHandler.showAccountOutput(inputNo);
    }

    //array
    public void viewBuyersProduct(String productID) {

    }

    public void editProduct(String productID) {
        productToEdit = product.getProductById(productID);
        commandProcessor.setSubMenuStatus(SubMenuStatus.PRODUCTFIELD);
    }

    public void editProductField(String edit) {
        if (field.equalsIgnoreCase("")) {
            if (edit.matches("")) {

            }
        }
    }

    public void processAddProduct() {
        commandProcessor.setSubMenuStatus(SubMenuStatus.ADDPRODUCT);
    }

    public void addProduct(String detail) {
        if (detailMenu == 0) {
            if (detail.matches("")) {
            }
        }
        Request newRequest = new Request("edit products " + field + " to " + detail);
    }

    public void processRemoveProduct(String productID) {
        if (checkProduct(productID)) {
            product.deleteProduct(productID);
        }
        outputHandler.showAccountOutput(inputNo);
    }
    //----------------------------------------------------------------------------------------

    //array
    public void processShowCategories() {

    }

    //---------------------------------------------------------------------------------------
    private boolean checkSale(String offID) {
        if (offID.matches("")) {
            if (sale.isThereSaleWithId(offID)) {
                return true;
            } else inputNo = 0;
        } else inputNo = 0;
        outputHandler.showAccountOutput(inputNo);
        return false;

    }

    //array
    public void processViewOffs() {

    }

    public void viewOff(String offID) {
        if (checkSale(offID)) {
            outputHandler.showSale(sale.getSaleWithId(offID));
        }

    }

    public void editOff(String offID) {
        if (checkSale(offID)) {
            saleToEdit = sale.getSaleWithId(offID);
            commandProcessor.setSubMenuStatus(SubMenuStatus.SALEFIELD);
        }
    }

    public void editOffField(String edit) {
        if (field.equalsIgnoreCase("sale status")) {
            if (edit.matches("CONFIRMED")) {
                saleToEdit.setSaleStatus(SaleStatus.CONFIRMED);
            } else if (edit.matches("UNDERREVIEWFOREDITING")) {
                saleToEdit.setSaleStatus(SaleStatus.UNDERREVIEWFOREDITING);
            } else {
                saleToEdit.setSaleStatus(SaleStatus.UNDERREVIEWFOREDITING);
            }
        } else if (field.equalsIgnoreCase("start of sale period")) {
            if (edit.matches("([0-2][0-9]|3[0-1])/([0-9]|1[0-2])/20[0-5][0-9]")) {
                Date currentDate = new Date();
                Date inputDate = new SimpleDateFormat("dd/MM/yyyy").parse(edit);
                if (inputDate.after(currentDate)) {
                    saleToEdit.setStartOfSalePeriod(inputDate);
                }
            } else if (field.equalsIgnoreCase("end of sale period")) {
                if (edit.matches("([0-2][0-9]|3[0-1])/([0-9]|1[0-2])/20[0-5][0-9]")) {
                    Date currentDate = new Date();
                    Date inputDate = new SimpleDateFormat("dd/MM/yyyy").parse(edit);
                    if (inputDate.after(currentDate)) {
                        saleToEdit.setEndOfSalePeriod(inputDate);
                    }
                }
            } else if (field.equalsIgnoreCase("sale amount")) {
                if (edit.matches("\\d+\\.?\\d*")) {
                    saleToEdit.setSaleAmount(Integer.parseInt(edit));
                }
            } else if (field.equalsIgnoreCase("seller")) {
                if (edit.matches("\\D+")) {
                    //convert seller to string!!!
                    saleToEdit.setSeller();
                }

            }
        }
    }

    public void addOff() {
        commandProcessor.setSubMenuStatus(SubMenuStatus.ADDSALE);
    }

    public void setDetailsToSale(String detail) {
        if (detailMenu == 0) {
            if (detail.matches("([0-2][0-9]|3[0-1])/([0-9]|1[0-2])/20[0-5][0-9]")) {
                Date currentDate = new Date();
                Date inputDate = new SimpleDateFormat("dd/MM/yyyy").parse(detail);
                if (inputDate.after(currentDate)) {
                    sale.setStartOfSalePeriod(inputDate);
                }
            }
        } else if (detailMenu == 1) {
            if (detail.matches("([0-2][0-9]|3[0-1])/([0-9]|1[0-2])/20[0-5][0-9]")) {
                Date currentDate = new Date();
                Date inputDate = new SimpleDateFormat("dd/MM/yyyy").parse(detail);
                if (inputDate.after(currentDate)) {
                    sale.setEndOfSalePeriod(inputDate);
                }
            }
        } else if (detailMenu == 2) {
            if (detail.matches("\\d+")) {
                sale.setSaleAmount(Integer.parseInt(detail));
            }
        } else if (detailMenu == 3) {
            if (detail.matches("\\D+")) {
                if (detail.equals(SaleStatus.CONFIRMED)) {
                    sale.setSaleStatus(SaleStatus.CONFIRMED);
                } else if (detail.equals(SaleStatus.UNDERREVIEWFORCONSTRUCTION)) {
                    sale.setSaleStatus(SaleStatus.UNDERREVIEWFORCONSTRUCTION);
                } else {
                    sale.setSaleStatus(SaleStatus.UNDERREVIEWFOREDITING);
                }
            }
        }
            /*ina lazem nemishe hala man mahze ehtiyat pak nemikonam.khasti pak kon!
        }else if (detailMenu == 4) {
            if (detail.matches("")) {
            }
        }else if (detailMenu == 5) {
            if (detail.matches("")) {
            }
        }*/
    }

    //-------------------------------------------------------------------------------
    public void processViewBalance() {

    }
}



