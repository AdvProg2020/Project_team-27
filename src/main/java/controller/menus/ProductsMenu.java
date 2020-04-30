package controller.menus;

import model.productRelated.Category;
import model.productRelated.Product;
import model.filtar.Filter;
import model.sort.Sort;
import view.CommandProcessor;
import view.SubMenuStatus;

import java.util.ArrayList;

public class ProductsMenu {
    private int outputNo;
    private int inputNo;
    private Product product;
    private Category category;
    private CommandProcessor commandProcessor;
    private Filter filter;
    private Sort sort;

    //a
    public void processProducts() {

    }

    //a
    public void processViewCategories() {

    }

    public void processFiltering() {
        commandProcessor.setSubMenuStatus(SubMenuStatus.FILTERING);
    }

    //finish
    public ArrayList<String> showAvailableFilters() {
        commandProcessor.setSubMenuStatus(SubMenuStatus.FILTERING);
        return filter.showAvailableFilters();
    }

    //finish
    public ArrayList<Product> filter(String filterID) {
        if (filter.ifFilterAvailable(filterID)){
            if (filterID.equals("category")){
               return filter.categoryFilter(product.getCategory());
            }
            else if (filterID.equals("companyName")){
                return filter.companiesFilter(product.getCompaniesName());
            }
            else if (filterID.equals("discount")){
                if (product.getHasDiscount()){
                    return filter.discountFilter(product.getDiscountCode());
                }
            }
            else if (filterID.equals("productName")){
                return filter.productNameFilter(product.getProductName());
            }
        }
        return null;
    }

    //finish
    public ArrayList<String> currentFilters() {
        return filter.currentFilters();
    }

    //finish
    public void disableFilter(String filterID) {
        filter.disableFilter(filterID);
    }


    public void processSorting() {
        commandProcessor.setSubMenuStatus(SubMenuStatus.SORTING);

    }

    //finish
    public ArrayList<String> showAvailableSorts() {
        return sort.showAvailableSort();
    }

    //finish
    public ArrayList<Product> sort(String sortID) {
        if (sort.ifAvailable(sortID)){
            if (sortID.equals("numberOfView")){
                return sort.scoreSort();
            }
            else if (sortID.equals("score")){
                return sort.numberOfViewsSort();
            }
        }
        return null;
    }

    //finish
    public ArrayList<String> currentSorts() {
        return sort.currentSorts();
    }

    //finish
    public void disableSort(String sortName) {
        sort.disableSort(sortName);
    }

    //finish
    public ArrayList<Product> processShowProducts() {
        return Filter.newArrayOfProductFilter;
    }

    //finish
    public ArrayList<String> processShowProductsID(String id) {
        return Product.listOfId;
    }

}
