package model.accounts;

import com.google.gson.reflect.TypeToken;
import model.request.AccountRequest;
import client.view.FileHandling;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Supporter extends Account {

    private static ArrayList<Supporter> allSupporters = new ArrayList<>();
    public static Type SupporterType = new TypeToken<ArrayList<Supporter>>() {
    }.getType();


    public Supporter(String username) throws IOException {
        super(username);
        role ="supporter";
        allSupporters.add(this);
        writeInJ();
    }



    public static ArrayList<Supporter> getAllSupporters() {
        return allSupporters;
    }

    public static void setAllSupporters(ArrayList<Supporter> allSupporters) {
        Supporter.allSupporters = allSupporters;
    }

    public static void writeInJ() throws IOException {

        String json = FileHandling.getGson().toJson(Supporter.allSupporters, SupporterType);
        FileHandling.writeInFile(json, "supporter.json");

    }

    @Override
    public String toString() {
        return super.toString();
    }
}
