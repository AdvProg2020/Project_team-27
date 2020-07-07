package controller.menus;

import model.accounts.Manager;
import model.off.Auction;
import model.request.ProductRequest;
import model.request.Request;
import model.request.SaleRequest;
import model.accounts.Seller;
import model.off.Sale;
import model.off.SaleStatus;
import model.productRelated.Category;
import model.productRelated.Product;
import model.productRelated.ProductStatus;
import model.sort.Sort;
import view.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;


public class SellerMenu {

    public static boolean doEdit = false;
    private static int outputNo = 0;
    // private static String field = null;
    private static int detailMenu = 0;
    private static ProductRequest productRequest;
    private static SaleRequest saleRequest;
    // private static String productId;
    // private static String offId;
    //  private static String editValue;
    private static int create = 0;
    private static int edit = 0;
    private static Auction newAuction;
    // static ArrayList<String> keys = new ArrayList<String>(productRequest.getSpecialValue().keySet());

    public static int getEdit() {
        return edit;
    }

    public static void setEdit(int edit) {
        SellerMenu.edit = edit;
    }

    private static int number = 0;


    public static int getCreate() {
        return create;
    }


    public static void setCreate(int create) {
        SellerMenu.create = create;
    }


    public static int getDetailMenu() {
        return detailMenu;
    }

    public static SaleRequest getSaleRequest() {
        return saleRequest;
    }

    public static int addAuction(String id) throws IOException {
        if (!id.equals("")) {
            if (!Auction.isThereWithId(id)) {
                newAuction = new Auction(id);
                create = 1;
                outputNo = 0;
            } else outputNo = 0;
        } else outputNo = 0;
        return outputNo;
    }

    public static int setStartAuction(String detail) throws IOException {
        if (detail.matches("^\\d{2}\\/\\d{2}\\/\\d{4}$")) {
            LocalDate localDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate inputDate = LocalDate.parse(detail, formatter);
            if (inputDate.isAfter(localDate) || inputDate.isEqual(localDate)) {
                newAuction.setStartOfPeriod(inputDate);
                Seller.writeInJ();
                outputNo = 0;
            } else outputNo = 0;
        } else outputNo = 0;
        return outputNo;
    }

    public static int setEndAuction(String detail) throws IOException {
        if (detail.matches("^\\d{2}\\/\\d{2}\\/\\d{4}$")) {
            LocalDate localDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate inputDate = LocalDate.parse(detail, formatter);
            if (inputDate.isAfter(localDate) || inputDate.isEqual(localDate)) {
                newAuction.setEndOfPeriod(inputDate);
                Seller.writeInJ();
                outputNo = 0;
            } else outputNo = 0;
        } else outputNo = 0;
        return outputNo;
    }

    public static void setProduct(String detail) throws IOException {
        newAuction.setProduct(detail);
        Seller.writeInJ();
    }

  /*  public static int addProduct(Product detail) throws IOException {
       // Product p = Product.getProductById(detail);
        if (!detail.isInAuction()) {
            newAuction.getAllProducts().add(detail);
            Seller.writeInJ();
            outputNo = 0;
        } else outputNo = 0;
        return outputNo;
    }

    public static int removeProduct(Product detail) throws IOException {
       // Product p = Product.getProductById(detail);
        if (!detail.isInAuction()) {
            newAuction.getAllProducts().remove(detail);
            Seller.writeInJ();
            outputNo = 0;
        } else outputNo = 0;
        return outputNo;
    }

   */

    //product--------------------------------------------------------------------------------------
    // manager // customer // seller
    private static boolean checkProduct(String productID) {
        // if (productID.matches("((?!^ +$)^.+$)")) {
        if (Product.isThereProductWithId(productID)) {
            return true;
        } else outputNo = 1;
        //} else outputNo = 2;
        return false;
    }

    public static ProductRequest getProductRequest() {
        return productRequest;
    }


    public static int editProduct(String productID) throws IOException {
        Product product = Product.getProductById(productID);
        if (checkProduct(productID)) {
            //  if (product.getSeller() == LoginMenu.getLoginAccount()) {
            // productId = productID;
            String id = LoginMenu.getLoginAccount().getUsername() + " wants edit product " + productID;
            if (!productRequest.isThereRequestFromID(id)) {
                product.setProductStatus(ProductStatus.UNDERREVIEWFOREDITING);
                productRequest = new ProductRequest(id);
                productRequest.setLastCategory(product.getCategory().getName());
                // Seller seller = (Seller) LoginMenu.getLoginAccount();
                // productRequest.setSellerName(seller.getUsername());
                productRequest.setProductId(productID);
            } else productRequest = (ProductRequest) Request.getRequestFromID(id);
            // CommandProcessor.setSubMenuStatus(SubMenuStatus.PRODUCTFIELD);
            outputNo = 0;
            edit = 1;
            //  } else outputNo = 22;
        }
        return outputNo;
        // OutputMassageHandler.showSellerOutput(outputNo);
    }


    public static int editProductField(String edit, String field) throws IOException {
        if (field.equalsIgnoreCase("Name")) {
            if (edit.matches("^(?!\\s*$).+")) {
                productRequest.setProductName(edit);
                outputNo = 4;
            } else outputNo = 6;
        } else if (field.equalsIgnoreCase("price")) {
            if (edit.matches("[+-]?\\d*\\.?\\d+")) {
                productRequest.setPrice(Double.parseDouble(edit));
                outputNo = 5;
            } else outputNo = 7;
        } else if (field.matches("(?i)number")) {
            if (edit.matches("\\d+")) {
                productRequest.setNumberOfProduct(Integer.parseInt(edit));
                outputNo = 9;
            } else outputNo = 8;
        } else if (field.matches("additional")) {
            if (edit.matches("^(?!\\s*$).+")) {
                productRequest.setAdditionalDetail(edit);
                outputNo = 14;
            } else outputNo = 15;
        } else if (field.equalsIgnoreCase("category")) {
            if (edit.matches("^(?!\\s*$).+")) {
                if (Category.isThereCategoryWithName(edit)) {
                    productRequest.setCategoryName(edit);
                    outputNo = 5;
                } else outputNo = 21;
            } else outputNo = 20;
//        } else if (field.matches("(?i)category\\s*Specifications")) {
//            if (Product.getCategorySpecifications().containsKey(edit)) {
//              //  CommandProcessor.setSubMenuStatus(SubMenuStatus.EDITSPECIFICATION);
//                editValue = edit;
//                outputNo = 0;
//            } else outputNo = 31;
        }
        return outputNo;
    }


    public static int addProduct(String detail, int detailMen, String img) throws IOException {
        if (detailMen == 0) {
            if (detail.matches("^(?!\\s*$).+")) {
                // if (!detail.equalsIgnoreCase("finish")) {
                if (!Product.isThereProductWithId(detail)) {
                    String id = LoginMenu.getLoginAccount().getUsername() + " wants add product " + detail;
                    if (!productRequest.isThereRequestFromID(id)) {
                        Product product = new Product(detail);
                        Seller seller = (Seller) Seller.getAccountWithUsername(LoginMenu.getLoginAccount().getUsername());
                        seller.getAllProduct().add(product);
                        product.setProductStatus(ProductStatus.UNDERREVIEWFORCONSTRUCTION);
                        productRequest = new ProductRequest(id);
                        productRequest.setProductId(detail);
                        productRequest.setImg(img);
//                            productRequest.setCompanyName(LoginMenu.getFirm().getName());
                    } else if (Request.getRequestFromID(id) instanceof ProductRequest) {
                        productRequest = (ProductRequest) Request.getRequestFromID(id);
                    }
                    create = 1;
                    detailMenu = 1;
                    outputNo = 0;
                } else outputNo = 27;
                // } else outputNo = 19;
            } else outputNo = 0;
        } else if (detailMen == 1) {
            if (detail.matches("^(?!\\s*$).+")) {
                productRequest.setProductName(detail);
                detailMenu = 2;
                outputNo = 0;
            } else outputNo = 6;
        } else if (detailMen == 2) {
            if (detail.matches("[+-]?\\d*\\.?\\d+")) {
                productRequest.setPrice(Double.parseDouble(detail));
                detailMenu = 3;
                outputNo = 0;
            } else outputNo = 7;
        } else if (detailMen == 3) {
            if (detail.matches("^(?!\\s*$).+")) {
                if (Category.isThereCategoryWithName(detail)) {
                    productRequest.setCategoryName(detail);
                    //      productRequest.addKey();
                    detailMenu = 4;
                    outputNo = 0;
                } else outputNo = 25;
            } else outputNo = 24;
        } else if (detailMen == 4) {
            if (detail.matches("^(?!\\s*$).+")) {
                productRequest.setAdditionalDetail(detail);
                detailMenu = 5;
                outputNo = 0;
            } else outputNo = 15;
        } else if (detailMen == 5) {
            if (detail.matches("\\d+")) {
                productRequest.setNumberOfProduct(Integer.parseInt(detail));
                detailMenu = 6;
                outputNo = 0;
                // OutputMassageHandler.show((String) productRequest.getSpecialValue().keySet().toArray()[0]);
                //CommandProcessor.setSubMenuStatus(SubMenuStatus.TRAIT);
            } else outputNo = 8;
        }
        return outputNo;
        //OutputMassageHandler.showSellerOutput(outputNo);

    }

    public static int processRemoveProduct(String productID) throws IOException {
        if (checkProduct(productID)) {
            if (Product.getProductById(productID).getSeller().equalsIgnoreCase(LoginMenu.getLoginAccount().getUsername())) {
                if (LoginMenu.getLoginAccount() instanceof Seller) {
                    Seller seller = (Seller) LoginMenu.getLoginAccount();
                    Product p = Product.getProductById(productID);
                    seller.getAllProduct().remove(p);
                    Product.deleteProduct(productID);
                    outputNo = 18;
                    Seller.writeInJ();
                }
            }
        }
        return outputNo;
        // OutputMassageHandler.showAccountOutput(outputNo);

    }
    //----------------------------------------------------------------------------------------

    private static boolean checkSale(String offID) {
        //if (offID.matches("")) {
        if (Sale.isThereSaleWithId(offID)) {
            return true;
        } else outputNo = 1;
        // } else outputNo = 0;
        OutputMassageHandler.showAccountOutput(outputNo);
        return false;

    }

    private static boolean isOk(Seller seller, Sale sale) {
        for (Sale sale2 : seller.getAllSales()) {
            if (sale2.getOffId().equals(sale.getOffId())) {
                return true;
            }
        }
        return false;
    }

    public static int editOff(String offID) throws IOException {
        if (checkSale(offID)) {
            Sale sale = Sale.getSaleWithId(offID);
            Seller seller = (Seller) LoginMenu.getLoginAccount();
            if (isOk(seller, sale)) {
                String id = LoginMenu.getLoginAccount().getUsername() + " wants edit off " + offID;
                if (!Request.isThereRequestFromID(id)) {
                    Sale.getSaleWithId(offID).setSaleStatus(SaleStatus.UNDERREVIEWFOREDITING);
                    saleRequest = new SaleRequest(id);
//                        Seller seller = (Seller) Seller.getAccountWithUsername(LoginMenu.getLoginAccount().getUsername());
                    //  seller.addSale(sale);
                    // seller.getAllSaleRequests().add(saleRequest);
                    saleRequest.setOffId(offID);
                    doEdit = true;
                    edit = 1;
                    // saleRequest.setSeller(LoginMenu.getLoginAccount());
                } else {
                    saleRequest = (SaleRequest) Request.getRequestFromID(id);
                }
            }
            //  offId = offID;
            // CommandProcessor.setSubMenuStatus(SubMenuStatus.SALEFIELD);
            outputNo = 0;
        } else outputNo = 5;


        return outputNo;
        // OutputMassageHandler.showSaleOutput(outputNo);
    }


    public static int editOffField(String edit, String field) throws ParseException, IOException {
        if (field.matches("(?i)start")) {
            if (edit.matches("([0-2][0-9]|3[0-1])/([0-9]|1[0-2])/20[0-5][0-9]")) {
                LocalDate localDate = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate inputDate = LocalDate.parse(edit, formatter);
                if (inputDate.isAfter(localDate) || inputDate.isEqual(localDate)) {
                    saleRequest.setStartOfSalePeriod(inputDate);
                    outputNo = 11;
                } else outputNo = 12;
            } else outputNo = 9;
        } else if (field.matches("(?i)end")) {
            if (edit.matches("([0-2][0-9]|3[0-1])/([0-9]|1[0-2])/20[0-5][0-9]")) {
                LocalDate localDate = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate inputDate = LocalDate.parse(edit, formatter);
                if (inputDate.isAfter(localDate) || inputDate.isEqual(localDate)) {
                    saleRequest.setEndOfSalePeriod(inputDate);
                    outputNo = 13;
                } else outputNo = 12;
            } else outputNo = 14;
        } else if (field.matches("(?i)amount")) {
            if (edit.matches("\\d+\\.?\\d*")) {
                saleRequest.setSaleAmount(Integer.parseInt(edit));
                outputNo = 16;
            } else outputNo = 15;
        } else if (field.matches("(?i)remove\\s*product")) {
            if (edit.matches("((?!^ +$)^.+$)")) {
                //if (checkProductSale(edit)) {
                saleRequest.removeProduct(Product.getProductById(edit));
                outputNo = 17;
                // }
            } else outputNo = 19;
        } else if (field.matches("(?i)add\\s*product")) {
            if (edit.matches("((?!^ +$)^.+$)")) {
                //if (checkProductSale(edit)) {
                if (!Product.getProductById(edit).getInSale()) {
                    saleRequest.addProductToSale(Product.getProductById(edit));
                    outputNo = 18;
                } else outputNo = 20;
                //}
            } else outputNo = 19;
        }
        return outputNo;
        // OutputMassageHandler.showSaleOutput(outputNo);
    }


    private static boolean checkSaleId(String detail) throws IOException {
        if (!Sale.isThereSaleWithId(detail)) {
            String id = "add sale: " + detail;
            if (!saleRequest.isThereRequestFromID(id)) {
                Sale sale = new Sale(detail);
                if (LoginMenu.getLoginAccount() instanceof Seller) {
                    Seller seller = (Seller) LoginMenu.getLoginAccount();
                    seller.addSale(sale);
                    // seller.getAllSales().add(sale);
                    sale.setSaleStatus(SaleStatus.UNDERREVIEWFORCONSTRUCTION);
                    saleRequest = new SaleRequest(id);
                    //seller.getAllSaleRequests().add(saleRequest);
                    saleRequest.setOffId(detail);
                }
                // saleRequest.setSeller(LoginMenu.getLoginAccount());
            } else {
                saleRequest = (SaleRequest) Request.getRequestFromID(id);
            }
            return true;
        } else outputNo = 7;
        return false;
    }

    public static int setDetailsToSale(String detail, int detailMen) throws ParseException, IOException {
        if (detailMen == 0) {
            if (detail.matches("^(?!\\s*$).+")) {
                if (checkSaleId(detail)) {
                    detailMenu = 1;
                    create = 1;
                    outputNo = 0;
                }
            } else outputNo = 9;
        } else if (detailMen == 1) {
            if (detail.matches("^\\d{1,2}\\/\\d{1,2}\\/\\d{4}$")) {
                LocalDate localDate = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate inputDate = LocalDate.parse(detail, formatter);
                if (inputDate.isAfter(localDate) || inputDate.isEqual(localDate)) {
                    saleRequest.setStartOfSalePeriod(inputDate);
                    detailMenu = 2;
                    outputNo = 0;
                } else outputNo = 12;
            } else outputNo = 9;
        } else if (detailMen == 2) {
            if (detail.matches("^\\d{1,2}\\/\\d{1,2}\\/\\d{4}$")) {
                LocalDate localDate = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate inputDate = LocalDate.parse(detail, formatter);
                if (inputDate.isAfter(localDate) || inputDate.isEqual(localDate)) {
                    saleRequest.setEndOfSalePeriod(inputDate);
                    detailMenu = 3;
                    outputNo = 0;
                } else outputNo = 12;
            } else outputNo = 19;
        } else if (detailMen == 3) {
            if (detail.matches("\\d+")) {
                saleRequest.setSaleAmount(Integer.parseInt(detail));
                detailMenu = 4;
                outputNo = 0;
            } else outputNo = 15;
        } else if (detailMen == 4) {
            if (detail.matches("((?!^ +$)^.+$)")) {
                // if (!detail.equalsIgnoreCase("finish")) {
                // if (checkProductSale(detail)) {
                Product product = Product.getProductById(detail);
                if (!saleRequest.isThereProduct(product)) {
                    //  saleRequest.addProduct(product);
                    if (!product.getInSale()) {
                        saleRequest.addProductToSale(product);
                        //   outputNo = 18;
                        // }
                        // } else {
                        //  CommandProcessor.setSubMenuStatus(SubMenuStatus.MANAGEPRODUCTS);
                        //  CommandProcessor.setInternalMenu(InternalMenu.MAINMENU);
                        // detailMenu = 0;
                        outputNo = 0;
                        //  }
                    } else outputNo = 20;
                }
            } else outputNo = 19;
        }
        return outputNo;
        // OutputMassageHandler.showSaleOutput(outputNo);
    }


//    ///////////////////////////////////////
//    private static boolean checkProductSale(String detail) {
//        if (Product.isThereProductWithId(detail)) {
//           // if (Product.getProductById(detail).getSeller() == LoginMenu.getLoginAccount().getUsername()) {
//               // if (Product.getProductById(detail).getInSale()) {
//                    return true;
//               // } else outputNo = 0;
//           // } else outputNo = 5;
//        } else outputNo = 8;
//        return false;
//    }

    //-------------------------------------------------------------------------------
  /*  //gson
    public static void processViewBalance() throws FileNotFoundException {
        OutputHandler.showBalance(LoginMenu.getLoginAccount().getUsername());
    }

    //gson
    public static void processViewCompanyInformation() throws FileNotFoundException {
        OutputHandler.showFirmInformation(LoginMenu.getFirm().getName());
    }

    //gson
    public static void processViewSalesHistory() throws FileNotFoundException {
        OutputHandler.showSalesHistory(LoginMenu.getLoginAccount().getUsername());
    }

    //gson
    public static void processManageProducts() throws FileNotFoundException {
        OutputHandler.showProducts(Filter.newArrayOfProductFilter);
        CommandProcessor.setSubMenuStatus(SubMenuStatus.MANAGEPRODUCTS);

    }

    //gson
    public static void viewProduct(String productID) throws FileNotFoundException {
        if (checkProduct(productID)) {
            OutputHandler.showProduct(productID);
        } else OutputMassageHandler.showSellerOutput(outputNo);
    }



    //gson
    public static void viewBuyersProduct(String productID) throws FileNotFoundException {
        if (checkProduct(productID)) {
            OutputHandler.showProductBuyers(productID);
        } else OutputMassageHandler.showSellerOutput(outputNo);

    }

    //array
    public static void processShowCategories() throws FileNotFoundException {
        OutputHandler.showCategories();
    }

   //array
    public static void processViewOffs() throws FileNotFoundException {
        CommandProcessor.setSubMenuStatus(SubMenuStatus.VIEWOFFS);
        OutputHandler.showOffs();
    }

    public static void viewOff(String offID) throws FileNotFoundException {
        if (checkSale(offID)) {
            OutputHandler.showOff(offID);
        }

    }
        public static void addOff() {
     //   CommandProcessor.setSubMenuStatus(SubMenuStatus.ADDSALE);
     //   CommandProcessor.setInternalMenu(InternalMenu.CHANGEDETAILS);
        OutputMassageHandler.showSaleOutput(6);
    }

    public static void processAddProduct() {
      //  CommandProcessor.setSubMenuStatus(SubMenuStatus.ADDPRODUCT);
      //  CommandProcessor.setInternalMenu(InternalMenu.CHANGEDETAILS);
        OutputMassageHandler.showSellerOutput(10);
    }

     public static void productField(String field) throws IOException {
       // if (field.matches("(?i)(?:Name|price|category|additional\\s*details|number\\s*Of\\s*Product)")) {
            String id = LoginMenu.getLoginAccount().getUsername() + " wants edit product " + productId + "'s " + field;
            if (!productRequest.isThereRequestFromID(id)) {
                Product product = Product.getProductById(productId);
                product.setProductStatus(ProductStatus.UNDERREVIEWFOREDITING);
                productRequest = new ProductRequest(id);
                productRequest.setLastCategory(product.getCategory());
                Seller seller = (Seller) LoginMenu.getLoginAccount();
                productRequest.setSellerName(seller);
                productRequest.setProductId(productId);
            } else productRequest = (ProductRequest) Request.getRequestFromID(id);
           // SellerMenu.field = field;
           // CommandProcessor.setSubMenuStatus(SubMenuStatus.EDITPRODUCT);
            outputNo = 3;
      //  } else outputNo = 23;
        OutputMassageHandler.showSellerOutput(outputNo);
    }

    public static void traitValue(String detail) throws IOException {
        ArrayList<String> keys = new ArrayList<>();
        for (String s : productRequest.getSpecialValue().keySet()) {
            keys.add(s);
        }
        if (!detail.equalsIgnoreCase("finish")) {
            if (number < keys.size() - 1) {
                if (detail.matches(".+")) {
                    productRequest.addHashmapValue(keys.get(number), detail);
                    outputNo = 30;
                    number++;
                    OutputMassageHandler.show(keys.get(number));
                } else outputNo = 29;
            } else if (number == keys.size() - 1) {
                if (detail.matches(".+")) {
                    productRequest.addHashmapValue(keys.get(number), detail);
                    //CommandProcessor.setSubMenuStatus(SubMenuStatus.MAINMENU);
                    // CommandProcessor.setInternalMenu(InternalMenu.MAINMENU);
                    number = 0;
                    outputNo = 28;
                } else outputNo = 29;
            } else {
                outputNo = 28;
                number = 0;
                // CommandProcessor.setSubMenuStatus(SubMenuStatus.MAINMENU);
                // CommandProcessor.setInternalMenu(InternalMenu.MAINMENU);
            }
        } else {
            // CommandProcessor.setSubMenuStatus(SubMenuStatus.MAINMENU);
            // CommandProcessor.setInternalMenu(InternalMenu.MAINMENU);
            outputNo = 28;
        }
        OutputMassageHandler.showSellerOutput(outputNo);
    }

    public static void editCategorySpecifications(String value, String editValue) throws IOException {
        productRequest.addHashmapValue(editValue, value);
       // OutputMassageHandler.showSaleOutput(33);
    }
      public static void offField(String field) throws IOException {
        if (field.matches("(?i)(?:sale\\s*status|start\\s*of\\s*sale\\s*period|end\\s*of\\s*sale\\s*period|remove\\s*product|add\\s*product|category\\s*Specifications)")) {
            String id = LoginMenu.getLoginAccount() + " wants edit off " + offId + "'s " + field;
            if (!Request.isThereRequestFromID(id)) {
                Sale.getSaleWithId(offId).setSaleStatus(SaleStatus.UNDERREVIEWFOREDITING);
                saleRequest = new SaleRequest(id);
                saleRequest.setOffId(offId);
               // saleRequest.setSeller(LoginMenu.getLoginAccount());
            } else {
                saleRequest = (SaleRequest) Request.getRequestFromID(id);
            }
            if (field.matches("(?i)category\\s*Specifications")) {
                outputNo = 26;
            } else outputNo = 4;
           // SellerMenu.field = field;
           // CommandProcessor.setSubMenuStatus(SubMenuStatus.EDITSALE);

        }
        outputNo = 3;
        OutputMassageHandler.showSaleOutput(outputNo);
    }


    public static void sortBy(String sort) throws FileNotFoundException {
        if (sort.matches("(?i)(?:product\\s+view|product\\s+score|log\\s+date)")) {
            if (sort.matches("product\\s+view")) {
                Seller seller = (Seller) LoginMenu.getLoginAccount();
                Sort.setNewArrayOfProductSort(seller.getAllProduct());
                Sort.numberOfViewsSort();
            } else if (sort.matches("product\\s+score")) {
                Seller seller = (Seller) LoginMenu.getLoginAccount();
                Sort.setNewArrayOfProductSort(seller.getAllProduct());
                Sort.scoreSort();
            } else if (sort.matches("log\\s+date")) {
                Seller seller = (Seller) LoginMenu.getLoginAccount();
                Sort.setNewArrayOfSalelog(seller.getSaleLogsHistory());
                Sort.saleLogSortDate();
            }
        }
    }
   */


}