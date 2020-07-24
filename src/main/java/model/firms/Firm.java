package model.firms;

import java.io.IOException;
import java.util.ArrayList;

public  class Firm {

    private String name;
    private double phoneNO;
    private String address;
    private String Email;
    String type;
    private static ArrayList<Firm> allFirms = new ArrayList<>();

    public Firm(String name) throws IOException {
        this.name = name;
        allFirms.add(this);
    //    writeInJ();
    }

    public void setDetailToFirm( Double phoneNO, String address, String email){

        if( phoneNO != null){
            this.phoneNO=phoneNO;
        }
        if (address != null){
            this.address= address;
        }
        if(email != null){
            this.Email= email;
        }

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static ArrayList<Firm> getAllFirms() {
        return allFirms;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPhoneNO() {
        return phoneNO;
    }

    public void setPhoneNO(double phoneNO) {
        this.phoneNO = phoneNO;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public static Firm getFirmWithID(String ID){
        for(Firm firm : allFirms){
            if(firm.name.equalsIgnoreCase(ID))return firm;
        }
        return null;
    }

    public static boolean isThereFirmWithID(String ID){
        for(Firm firm : allFirms){
            if(firm.name.equalsIgnoreCase(ID))return true;
        }
        return false;
    }

    public void deleteFirm(String ID){
        allFirms.remove(getFirmWithID(ID));
    }

//    public static void writeInJ() throws IOException {
//        Type collectionType = new TypeToken<ArrayList<Firm>>(){}.getType();
//        String json= FileHandling.getGson().toJson(Firm.allFirms,collectionType);
//        FileHandling.turnToArray(json+" "+"firm.json");
//    }

    @Override
    public String toString() {
        return "Firm{" +
                "name='" + name + '\'' +
                ", phoneNO=" + phoneNO +
                ", address='" + address + '\'' +
                ", Email='" + Email + '\'' +
                '}';
    }
}