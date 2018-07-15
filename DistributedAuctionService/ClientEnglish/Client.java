package ES;

import ES.end;
import ES.endHelper;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StringHolder;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import java.util.Scanner;


public class Client {
    static end endImp;
    private static String role = null;
    private static Scanner in = new Scanner(System.in);
    private static String username;
    public static void main(String[] args) {
        try {
        	//long start=System.currentTimeMillis();
            ORB orb = ORB.init(args, null);
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            String name = "end";
            endImp = endHelper.narrow(ncRef.resolve_str(name));
            //long stop=System.currentTimeMillis();
            //System.out.println(stop-start);           
            System.out.println("Please enter user name: ");
            username = in.next();
            boolean run = true;
            while (run) {
                selectRoles();
                if (role.equals("seller")){
                    seller(endImp);
                }
                if (role.equals("bidder")){
                    bidder(endImp);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private static void seller(end endImp){
        boolean run = true;
        while (run) {
            sellerMainMenu();
            String input = in.next();
            switch (input){
                case "1" :{
                    System.out.println("Please enter item name:");
                    String itemname = in.next();
                    System.out.println("Please enter price:");
                    String price = in.next();
                    StringHolder s = new StringHolder();
                    //long start=System.currentTimeMillis();
                    endImp.offer_item(username, itemname, Integer.valueOf(price), s);
                    //long stop=System.currentTimeMillis();
                    //System.out.println(stop-start);                     
                    System.out.println(s.value);
                    continue;
                }
                case "2" :{
                    StringHolder name = new StringHolder();;
                    StringHolder price = new StringHolder();;
                    //long start=System.currentTimeMillis();
                    endImp.view_high_bidder(username,name, price);
                    //long stop=System.currentTimeMillis();
                    //System.out.println(stop-start); 
                    if (price.value.equals("-1"))
                        System.out.println(name.value);
                    else
                        System.out.println("Current highest bidder is " + name.value+" and price is " + price.value);
                    continue;
                }
                case "3":{
                    StringHolder s = new StringHolder();;
                    //long start=System.currentTimeMillis();
                    endImp.sell(username,"",0,s);
                    //long stop=System.currentTimeMillis();
                    //System.out.println(stop-start);
                    System.out.println(s.value);
                    continue;
                }
                case "4":{
                    StringHolder s = new StringHolder();;
                    endImp.view_auction(username, s);
                    System.out.println(s.value);
                    continue;
                }
                case "5" :{
                    return;
                }
                default: continue;
            }
        }
    }
    private static void bidder(end endImp){
        boolean run = true;
        while (run) {
            bidderMainMenu();
            String input = in.next();
            switch (input){
                case "1" :{
                    System.out.println("Please enter price:");
                    String price = in.next();
                    StringHolder s = new StringHolder();;
                    //long start=System.currentTimeMillis();
                    endImp.bid(username, Integer.valueOf(price), s);
                    //long stop=System.currentTimeMillis();
                    //System.out.println(stop-start);                   
                    System.out.println(s.value);
                    continue;
                }
                case "2" :{
                    StringHolder s = new StringHolder();;
                    //StringHolder price = new StringHolder();;
                    //long start=System.currentTimeMillis();
                    endImp.view_bid(username,s); //view_high_bidder(un,n,h) was here
                    //long stop=System.currentTimeMillis();
                    //System.out.println(stop-start); 
                    System.out.println(s.value);
                    /*if (price.value.equals("-1"))
                        System.out.println(name.value);
                    else
                        System.out.println("Current highest bidder is " + name.value+"  and price is " + price.value);*/
                    continue;
                }
                case "3":{
                    StringHolder s = new StringHolder();;
                    //long start=System.currentTimeMillis();                                       
                    endImp.view_auction(username, s);
                    //long stop=System.currentTimeMillis();
                    //System.out.println(stop-start); 
                    System.out.println(s.value);
                    continue;
                }
                case "4" :{   
                    return;
                }
                default: continue;
            }
        }
    }
    private static void sellerMainMenu(){
        System.out.println("");
        System.out.println("1. Offer Item");
        System.out.println("2. View Highest Bidder");
        System.out.println("3. Sell");
        System.out.println("4. View Auction Status");
        System.out.println("5. Exit");
    }
    private static void bidderMainMenu(){
        System.out.println("");
        System.out.println("1. Bid");
        System.out.println("2. View Bid Status");
        System.out.println("3. View Auction Status");
        System.out.println("4. Exit");
    }
    private static void selectRoles(){
        boolean run = true;
        while (run) {
            System.out.println("Welcome to Auction System");
            System.out.println("Please select your role:");
            printRoles();
            String input = in.next();
            switch (input){
                case "1" : {
                    role = "seller";
                    run = !run;
                    break;
                }
                case "2" : {
                    role = "bidder";
                    run = !run;
                    break;
                }
                case "3" :{
                    break;
                }
            }
        }
    }
    private static void printRoles(){
        System.out.println("");
        System.out.println("1. Seller");
        System.out.println("2. Bidder");
        System.out.println("3. Exit");
    }
}

