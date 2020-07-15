package model.accounts;

import model.request.AccountRequest;

import java.io.IOException;
import java.util.ArrayList;

public class Supporter extends Account {
    private String online = "off";
    private static ArrayList<Supporter> allSupporters = new ArrayList<>();

    public Supporter(String username) throws IOException {
        super(username);
        role ="supporter";
        allSupporters.add(this);
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    public static ArrayList<Supporter> getAllSupporters() {
        return allSupporters;
    }

    public static void setAllSupporters(ArrayList<Supporter> allSupporters) {
        Supporter.allSupporters = allSupporters;
    }
}