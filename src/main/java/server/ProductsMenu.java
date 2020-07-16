package server;



public class ProductsMenu {
/*
   // private static int outputNo;
    private static String productId;

    public static void setProductId(String productId) {
        ProductsMenu.productId = productId;
    }


    //filter-------------------------------------------------------

    private static boolean checkFilter(String filterName) {
        //if (filterName.matches("(?i)(?:)")) {
        if (Filter.isThereFilterWithName(filterName)) {
            return true;
        } else outputNo = 1;
        // } else inputNo = 0;
        return false;
    }


    public static void filter(String filterID) throws FileNotFoundException {
        if (checkFilter(filterID)) {
            if (Filter.ifFilterAvailable(filterID)) {
                if (filterID.equals("category")) {
                  //  CommandProcessor.setSubMenuStatus(SubMenuStatus.CATEGORYNAMEFILTER);
                    OutputMassageHandler.showProductsOutput(10);
                } else if (filterID.matches("firm\\s*name")) {
                   // CommandProcessor.setSubMenuStatus(SubMenuStatus.FIRMFILTER);
                    OutputMassageHandler.showProductsOutput(11);
                } else if (filterID.matches("product\\s*name")) {
                  //  CommandProcessor.setSubMenuStatus(SubMenuStatus.PRODUCTNAMEFILTER);
                    OutputMassageHandler.showProductsOutput(12);
                } else if (filterID.matches("period\\s*nilter")) {
                  //  CommandProcessor.setSubMenuStatus(SubMenuStatus.PERIODFILTER);
                    OutputMassageHandler.showProductsOutput(8);
                } else if (filterID.matches("is\\s*available")) {
                    Filter.isAvailable();
                   // OutputHandler.showAllProductAfterFilter();
                }
            } else OutputMassageHandler.showProductsOutput(9);
        }

    }

    public static void categoryFilter(String category) throws FileNotFoundException {
        if(Category.isThereCategoryWithName(category)) {
            Filter.categoryFilter(Category.getCategoryWithName(category));
           // OutputHandler.showAllProductAfterFilter();
        }else OutputMassageHandler.showProductsOutput(13);
    }

    public static void FirmFilter(String category) throws FileNotFoundException {
        if(Firm.isThereFirmWithID(category)) {
            Filter.companiesFilter(category);
          //  OutputHandler.showAllProductAfterFilter();
        }else OutputMassageHandler.showProductsOutput(14);
    }

    public static void productNameFilter(String product) throws IOException {
        if(Product.isThereProductWithId(product)) {
            Filter.productNameFilter(product);
           // OutputHandler.showAllProductAfterFilter();
        }else OutputMassageHandler.showProductsOutput(15);
    }

    public static void periodFilter(String first, String second) {
        Filter.periodFilter(Integer.parseInt(first), Integer.parseInt(second));
     //   OutputHandler.showAllProductAfterFilter();
    }


    public static void currentFilters() {
       // OutputHandler.showCurrentFilter(Filter.currentFilters());
    }


    public static void disableFilter(String filterID) {
        if (checkFilter(filterID)) {
            Filter.disableFilter(filterID);
            outputNo = 2;
        }
        OutputMassageHandler.showProductsOutput(outputNo);
    }


    //sort-----------------------------------------------------------------


    private static boolean checkSort(String sortName) {
        //if (sortName.matches("")) {
        if (Sort.isThereSortWithName(sortName)) {
            return true;
        } else outputNo = 3;
        //} else inputNo = 0;
        return false;
    }


    public static void sort(String sortID) {
        ArrayList<Product> sortedList = null;
        if (checkSort(sortID)) {
            if (Sort.ifAvailable(sortID)) {
                if (sortID.matches("number\\s*Of\\s*View")) {
                    Sort.setNewArrayOfProductSort(Product.getProductList());
                    sortedList = Sort.scoreSort();
                  //  OutputHandler.showAllProductAfterSort(sortedList);
                } else if (sortID.equalsIgnoreCase("score")) {
                    Sort.setNewArrayOfProductSort(Product.getProductList());
                    sortedList = Sort.numberOfViewsSort();
                  //  OutputHandler.showAllProductAfterSort(sortedList);
                } else outputNo = 9;
            } else outputNo = 7;
        }
        if (sortedList != null) {
          //  OutputHandler.showAllProductAfterSort(sortedList);
        }
    }




    public static void disableSort() {
        Sort.disableSort();
        OutputMassageHandler.showProductsOutput(4);
    }

    //product--------------------------------------------------




    public static String getProductId() {
        return productId;
    }

    /*
    //array
    public static void processViewCategories() throws FileNotFoundException {
        OutputHandler.showCategories();
    }

    public static void processShowProducts() throws FileNotFoundException {
        OutputHandler.showProducts(Filter.getNewArrayOfProductFilter());
    }

    public static void processProducts() {
        CommandProcessor.setMenuStatus(MenuStatus.PRODUCTSMENU);
    }


    public static void processFiltering() {
        CommandProcessor.setSubMenuStatus(SubMenuStatus.FILTERING);
    }


    public static void showAvailableFilters() {
        OutputHandler.showAvailableFilters(Filter.getAvailableFilters());
    }

    public static void processSorting() {
        CommandProcessor.setSubMenuStatus(SubMenuStatus.SORTING);
    }


    public static void showAvailableSorts() {
        OutputHandler.showAvailableSorts(Sort.getAvailableSorts());
    }

    public static void currentSorts() {
        OutputHandler.showCurrentSorts(Sort.currentSorts());
    }


    public static void processShowProductsID(String id) throws FileNotFoundException {
        if (Product.isThereProductWithId(id)) {
            Product.getProductById(id).setNumberOfViews();
            OutputHandler.showProduct(id);
            productId = id;
          //  CommandProcessor.setMenuStatus(MenuStatus.PRODUCTMENU);
        } else OutputMassageHandler.showProductsOutput(5);
    }

     */


}