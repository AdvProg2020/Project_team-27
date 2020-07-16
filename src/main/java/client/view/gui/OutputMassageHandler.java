package client.view.gui;


import javafx.scene.Parent;

import java.util.ArrayList;

public class OutputMassageHandler {
    private static String text;
    private static ArrayList<Parent> allRoots = new ArrayList<>();

    public static void addRoot(Parent root) {
        allRoots.add(root);
    }

    public static ArrayList<Parent> getAllRoots() {
        return allRoots;
    }

    public static void setAllRoots(ArrayList<Parent> allRoots) {
        OutputMassageHandler.allRoots = allRoots;
    }

    public static String showProductsOutput(int output) {
        if (output == 0) {
            System.out.println("");
        } else if (output == 1) {
            text = ("there is no filter with this id");
        } else if (output == 2) {
            text = ("filter disabled");
        } else if (output == 3) {
            text = ("there is no sort with this id");
        } else if (output == 4) {
            text = ("sort disabled");
        } else if (output == 5) {
            text = ("there is no product with this id");
        } else if (output == 6) {
            text = ("this filter isn't available");
        } else if (output == 7) {
            text = ("this sort isn't available");
        } else if (output == 8) {
            text = ("please enter 2 number");
        } else if (output == 9) {
            text = ("sort format is in valid");
        } else if (output == 10) {
            text = ("please enter category name to filter");
        } else if (output == 11) {
            text = ("please enter firm name to filter");
        } else if (output == 12) {
            text = ("please enter product name to filter");
        } else if (output == 13) {
            text = ("there is no category with this id");
        } else if (output == 14) {
            text = ("there is no firm with this id");
        } else if (output == 15) {
            text = ("there is no product with this id");
        }
        return text;
    }

    public static String showProductOutput(int output) {
        if (output == 0) {
            text = ("");
        } else if (output == 1) {
            text = ("comment content format is invalid");
        } else if (output == 2) {
            text = ("comment title format is invalid");
        } else if (output == 3) {
            text = ("you already commented on this product");
        }
        return text;
    }


    public static String showManagerOutput(int output) {
        if (output == 0) {
            text = ("");
        } else if (output == 1) {
            text = ("product removed");
        } else if (output == 2) {
            text = ("product Id format is invalid");
        } else if (output == 3) {
            text = ("no product exist with this Id");
        } else if (output == 4) {
            text = ("discount code Id format is inavlid");
        } else if (output == 5) {
            text = ("there is no discount code with this Id");
        } else if (output == 6) {
            text = ("discount code removed");
        } else if (output == 8) {
            text = ("start of discount period format is invalid");
        } else if (output == 10) {
            text = ("end of discount period format is invalid");
        } else if (output == 12) {
            text = ("maximum discount amount format is invalid");
        } else if (output == 13) {
            text = ("category removed");
        } else if (output == 14) {
            text = ("total time of use format is invalid");
        } else if (output == 16) {
            text = ("start of discount period changed");
        } else if (output == 17) {
            text = ("end of discount period changed");
        } else if (output == 18) {
            text = ("maximum discount amount changed");
        } else if (output == 19) {
            text = ("total time of use changed");
        } else if (output == 20) {
            text = ("request format is invalid");
        } else if (output == 21) {
            text = ("no request exist with this ID");
        } else if (output == 22) {
            text = ("category format is invalid");
        } else if (output == 23) {
            text = ("no category exist with this name");
        } else if (output == 24) {
            text = ("total time of use format is invalid");
        } else if (output == 25) {
            text = ("there is discount code with this id");
        } else if (output == 26) {
            text = ("date must be after current date");
        } else if (output == 27) {
            text = ("changed");
        } else if (output == 28) {
            text = ("discount amount format is invalid");
        } else if (output == 29) {
            text = ("account added to list");
        } else if (output == 30) {
            text = ("username format is invalid");
        } else if (output == 31) {
            text = ("there is no account with this username");
        } else if (output == 32) {
            text = ("account removed from list");
        } else if (output == 34) {
            text = ("there is a category with this name");
        } else if (output == 36) {
            text = ("please enter username you want to add to discount code list (if you enter finish process end)");
        } else if (output == 37) {
            text = ("discount code add");
        } else if (output == 39) {
            text = ("product add to category");
        } else if (output == 40) {
            text = ("product removed from category");
        } else if (output == 41) {
            text = ("trait removed");
        } else if (output == 42) {
            text = ("trait format is invalid");
        } else if (output == 43) {
            text = ("trait add");
        } else if (output == 45) {
            text = ("category add");
        } else if (output == 46) {
            text = ("there is no product with this id");
        } else if (output == 47) {
            text = ("there is no trait with this id");
        } else if (output == 48) {
            text = ("there is a trait with this id");
        }
        return text;
    }

    public static void showCategoryOutput(int output) {
        if (output == 0) {
            System.out.println("");
        } else if (output == 1) {
            System.out.println("trait removed");
        } else if (output == 2) {
            System.out.println("trait format is invalid");
        } else if (output == 3) {
            System.out.println("trait add");
        } else if (output == 9) {
            System.out.println("you aren't a customer");
        } else if (output == 10) {
            System.out.println("your discount is incorrect");
        } else if (output == 11) {
            System.out.println("number must be between 1 to 5");
        } else if (output == 12) {
            System.out.println("there is no log yet");
        } else if (output == 13) {
            System.out.println("no user exist with this username");
        } else if (output == 14) {
            System.out.println("your password is wrong");
        } else if (output == 15) {
            System.out.println("login successful");
        } else if (output == 16) {
            System.out.println("no field matches with this");
        } else if (output == 17) {
            System.out.println("password changed");
        } else if (output == 18) {
            System.out.println("name changed");
        } else if (output == 19) {
            System.out.println("last name changed");
        } else if (output == 20) {
            System.out.println("Email changed");
        } else if (output == 21) {
            System.out.println("phone number changed");
        } else if (output == 22) {
            System.out.println("logout");
        } else if (output == 23) {
            System.out.println("you cannot create manager profile");
        } else if (output == 24) {
            System.out.println("You've logged in before");
        } else if (output == 25) {
            System.out.println("you have to login first");
        } else if (output == 26) {
            System.out.println("no role matches with this");
        } else if (output == 27) {
            System.out.println("you have already requested for new account");
        } else if (output == 28) {
            text = ("you don't have permission for new account");
        } else if (output == 29) {
            text = ("Your request has not been reviewed");
        }
    }


    public static String showCustomerOutput(int output) {
        if (output == 0) {
            System.out.println("");
        } else if (output == 1) {
            text = ("there is no product with this productId ");
        } else if (output == 4) {
            text = ("number format is invalid");
        } else if (output == 7) {
            text = ("there is no discount code with this id");
        } else if (output == 8) {
            text = ("there is no log with this id");
        } else if (output == 10) {
            text = ("product number increase");
        } else if (output == 11) {
            text = ("number must be between 1 to 5");
        } else if (output == 12) {
            text = ("there is no log yet");
        } else if (output == 13) {
            text = ("you didn't buy this product");
        } else if (output == 14) {
            text = ("score add to product");
        } else if (output == 15) {
            text = ("product number decrese");
        }
        return text;
    }

    public static String showPurchaseOutput(int output) {
        if (output == 0) {
            text = ("please enter discount id");
        } else if (output == 1) {
            text = ("there is no discount code wiyh yhis id");
        } else if (output == 2) {
            text = ("discount ok");
        } else if (output == 3) {
            text = ("you dont have this discount id");
        } else if (output == 4) {
            text = ("date is invalid");
        } else if (output == 5) {
            text = ("you can't use it anymore");
        } else if (output == 6) {
            text = ("you have to login first");
        } else if (output == 7) {
            text = ("please enter your phone number");
        } else if (output == 8) {
            text = ("you aren't a customer");
        } else if (output == 9) {
            text = ("you don't have enough credit");
        } else if (output == 10) {
            text = ("payment successful!");
        }
        return text;
    }

    public static String showSellerOutput(int output) {
        if (output == 0) {
            System.out.println("number");
        } else if (output == 1) {
            text = ("there is no product with this productId ");
        } else if (output == 4) {
            text = ("product name change request sent");
        } else if (output == 5) {
            text = ("product price change request sent");
        } else if (output == 6) {
            text = ("product name format is invalid");
        } else if (output == 7) {
            text = ("product price format is invalid");
        } else if (output == 8) {
            text = ("number of product format is invalid");
        } else if (output == 9) {
            text = ("number Of Product change request sent");
        } else if (output == 14) {
            text = ("additional detail change request sent");
        } else if (output == 15) {
            text = ("additional detail format is invalid");
        } else if (output == 18) {
            text = ("product removed");
        } else if (output == 19) {
            text = ("product id must not be finish");
        } else if (output == 20) {
            text = ("category field format is invalid");
        } else if (output == 21) {
            text = ("there is no category with this id");
        } else if (output == 22) {
            text = ("you can't change this product");
        } else if (output == 23) {
            text = ("field format is invalid (Name|price|category|additional details|number Of Product)");
        } else if (output == 24) {
            text = ("there is no category with this name");
        } else if (output == 25) {
            text = ("category format is invalid");
        } else if (output == 27) {
            text = ("there is no product with this id");
        } else if (output == 28) {
            text = ("sale request sent");
        } else if (output == 29) {
            text = ("value format is invalid");
        } else if (output == 31) {
            text = ("there is no trait in category");
        } else if (output == 33) {
            text = ("new value request sent");
        }
        return text;
    }

    public static String showSaleOutput(int output) {
        if (output == 0) {
            text = ("");
        } else if (output == 1) {
            text = ("there is no sale with this name");
        } else if (output == 3) {
            text = ("off field format is invalid");
        } else if (output == 5) {
            text = ("you cant't edit this off");
        } else if (output == 7) {
            text = ("there is an off with this id");
        } else if (output == 8) {
            text = ("there is no product with this id");
        } else if (output == 9) {
            text = ("start of sale period format is invalid");
        } else if (output == 11) {
            text = ("start of sale period request sent");
        } else if (output == 12) {
            text = ("date is invalid");
        } else if (output == 13) {
            text = ("end of sale period request sent");
        } else if (output == 14) {
            text = ("end of sale period format is invalid");
        } else if (output == 15) {
            text = ("sale amount format is invalid");
        } else if (output == 16) {
            text = ("sale amount request sent");
        } else if (output == 17) {
            text = ("product removed");
        } else if (output == 18) {
            text = ("product add");
        } else if (output == 19) {
            text = ("product id format is invalid");
        }else if (output == 20) {
            text = ("has sale");
        }
        return text;

    }


    public static String showAccountOutput(int output) {
        if (output == 0) {
            return ("");
        } else if (output == 1) {
            text = ("a user exists with this username");
        } else if (output == 2) {
            text = ("wage format is invalid");
        } else if (output == 3) {
            text = ("password format is invalid");
        } else if (output == 5) {
            text = ("name format is invalid");
        } else if (output == 7) {
            text = ("last name format is invalid");
        } else if (output == 9) {
            text = ("email format is invalid");
        } else if (output == 11) {
            text = ("phone number format is invalid");
        } else if (output == 12) {
            text = ("register successful");
        } else if (output == 13) {
            text = ("no user exist with this username");
        } else if (output == 14) {
            text = ("your password is wrong");
        } else if (output == 16) {
            text = ("no account field matches with this (username|password|last name|email|phone number|firm)");
        } else if (output == 22) {
            text = ("logout");
        } else if (output == 23) {
            text = ("you cannot create manager profile");
        } else if (output == 24) {
            text = ("You've logged in before");
        } else if (output == 25) {
            text = ("you have to login first");
        } else if (output == 26) {
            text = ("no role matches with this");
        } else if (output == 27) {
            text = ("you can't change firm information");
        } else if (output == 30) {
            text = ("birthday Date format is invalid");
        } else if (output == 32) {
            text = ("username format is invalid");
        } else if (output == 33) {
            text = ("you can't login");
        } else if (output == 35) {
            text = ("there is no account with this user name");
        } else if (output == 36) {
            text = ("username format is invalid");
        } else if (output == 37) {
            text = ("account deleted");
        } else if (output == 18) {
            text = ("product removed");
        }
        return text;
    }

    public static String showFirmOutput(int output) {
        if (output == 0) {
            text = ("");
        } else if (output == 1) {
            text = ("no firm field matches with this (name|address|email|phone number)");
        } else if (output == 3) {
            text = ("firm name format is invalid");
        } else if (output == 6) {
            text = ("firm's phone number format is invalid");
        } else if (output == 7) {
            text = ("phone number request for new value sent");
        } else if (output == 8) {
            text = ("firm's address format is invalid");
        } else if (output == 9) {
            text = ("address request for new value sent");
        } else if (output == 10) {
            text = ("firm's Email format is invalid");
        } else if (output == 11) {
            text = ("Email number request for new value sent");
        } else if (output == 12) {
            text = ("you dont have firm with this name");
        } else if (output == 17) {
            text = ("register account request sent to manager");
        } else if (output == 18) {
            text = ("firm's type format is invalid (company|factory|workshop)");
        }
        return text;
    }

    public static String showReceiverInfo(int output) {
        if (output == 0) {
            text = ("");
        } else if (output == 1) {
            text = ("current phone number format is invalid");
        } else if (output == 3) {
            text = ("address format is invalid");
        } else if (output == 4) {
            text = ("do you want fast post? (yes|no)");
        } else if (output == 5) {
            text = ("answer must be yes or no");
        } else if (output == 6) {
            text = ("do yo have discount code? (yes|no)");
        }
        return text;
    }

    public static String showOutputWithString(int output) {
        if (output == 1) {
            text = ("account deleted");
        } else if (output == 2) {
            text = ("product  removed");
        } else if (output == 4) {
            text = ("discount code removed");
        } else if (output == 5) {
            text = ("request  accepted");
        } else if (output == 6) {
            text = ("request declined");
        } else if (output == 7) {
            text = ("category  removed");
        } else if (output == 8) {
            text = ("your credit ");
        }
        return text;
    }


 /*   public static String showOutputWithString(String string, int output) {
        if (output == 1) {
            text =("username " + string + " deleted");
        } else if (output == 2) {
            text =("product " + string + " removed");
        } else if (output == 3) {
            text =("please enter your new  " + string);
        } else if (output == 4) {
            text =("discount code " + string + " removed");
        } else if (output == 5) {
            text =("request " + string + " accepted");
        } else if (output == 6) {
            text =("request " + string + " declined");
        } else if (output == 7) {
            text =("category " + string + " removed");
        } else if (output == 8) {
            text =("your credit " + string);
        }
        return text;
    }

  */

    public static void show(String show) {
        System.out.println(show);
    }


}