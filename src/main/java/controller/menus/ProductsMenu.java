package controller.menus;

import model.productRelated.Category;
import model.productRelated.Product;
import model.productRelated.filtar.Filter;
import view.CommandProcessor;
import view.OutputHandler;
import view.SubMenuStatus;

public class ProductsMenu {
    private int outputNo;
    private int inputNo;
    private Product product;
    private Category category;
    private CommandProcessor commandProcessor;
    private Filter filter;

    //a
    public void processProducts() {

    }

    //a
    public void processViewCategories() {

    }

    public void processFiltering() {
        commandProcessor.setSubMenuStatus(SubMenuStatus.FILTERING);
    }

    //a
    public void showAvailableFilters() {

    }

    public void filter(String filterID) {

    }

    public void currentFilters() {

    }

    public void disableFilter(String filterID) {

    }


    public void processSorting() {

    }

    public void showAvailableSorts() {

    }

    public void sort(String sortID) {

    }


    public void currentSorts() {

    }

    public void disableSort() {

    }

    //ARRAY
    public void processShowProducts() {

    }

    public void processShowProductsID(String id) {
    }
}
