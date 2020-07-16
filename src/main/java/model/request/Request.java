package model.request;

import server.menus.LoginMenu;
import model.accounts.Customer;
import model.accounts.Seller;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;

public abstract class Request {
    private String requestText;
    private String requestDate;
    private String seller;
    private static ArrayList<Request> allRequests;
    LocalDateTime now;
//    public static Type RequestType = new TypeToken<ArrayList<Request>>() {
//    }.getType();

    public Request(String requestID) throws IOException {
        this.requestText = requestID;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        now = LocalDateTime.now();
        requestDate = dtf.format(now);
        if(LoginMenu.getLoginAccount() instanceof Seller) {
            seller =  LoginMenu.getLoginAccount().getUsername();
        }else if(LoginMenu.getLoginAccount() instanceof Customer) {
            seller =  LoginMenu.getLoginAccount().getUsername();
        }
        allRequests.add(this);
    }


    public String getSeller() {
        return seller;
    }

    public LocalDateTime getNow() {
        return now;
    }

    public static void setAllRequests(ArrayList<Request> allRequests) {
        Request.allRequests = allRequests;
    }

    public static ArrayList<Request> getAllRequests() {
        return allRequests;
    }

    public String getRequestText() {
        return requestText;
    }

    public abstract void declineRequest() throws IOException;

    public abstract void acceptRequest() throws IOException;

    public static Request getRequestFromID(String requestID){
        for(Request request : allRequests){
            if (request.requestText.equalsIgnoreCase(requestID)) return request;
        }
        return null;
    }

    public static boolean isThereRequestFromID(String requestID){
        for(Request request : allRequests){
            if (request.requestText.equalsIgnoreCase(requestID)) return true;
        }
        return false;
    }

    public static Comparator<Request> productComparatorForScore = new Comparator<Request>() {

        public int compare(Request o1, Request o2) {
            return o1.getNow().compareTo(o2.getNow());
        }
    };


    @Override
    public String toString() {
        return "Request{" +
                "requestText='" + requestText + '\'' +
                ", requestDate='" + requestDate + '\'' +
                ", seller=" + seller +
                '}';
    }

}
