package model.firms;

import com.google.gson.reflect.TypeToken;
import model.productRelated.Product;
import view.FileHandling;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Workshop extends Firm {
    private static ArrayList<Workshop> allWorkshops;

    public Workshop(String ID) {
        super(ID);
        allWorkshops.add(this);
    }
    public static void writeInJ() throws IOException {
        Type collectionType = new TypeToken<ArrayList<Workshop>>(){}.getType();
        String json= FileHandling.getGson().toJson(Workshop.allWorkshops,collectionType);
        FileHandling.turnToArray(json+" "+"workshop.json");
    }

}
