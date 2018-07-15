package ES;

import ES.endPOA;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StringHolder;

public class AuctionFun extends endPOA{
    private Auction curr = null;
    private Auction prev = null;
    class Auction{
        private long price;
        private String creater;
        private String highest_bidder;
        private String itemname;
        private boolean done = false;
        private Auction(){}

        public Auction(long price, String creater, String highest_bidder, String itemname){
            this.price = price;
            this.creater = creater;
            this.highest_bidder = highest_bidder;
            this.itemname = itemname;
        }
    }
    private ORB orb;

    public void setORB(ORB orb_val) {
        orb = orb_val;
    }
    public void shutdown() {
        orb.shutdown(false);
    }

    @Override
    public void view_auction(String username, StringHolder c) {
       /* if (curr == null && prev != null && !prev.done && username.equals(prev.highest_bidder)) {
            c.value = "CONGRATULATIONS!! YOU WON " + prev.itemname;
            prev.done =true;
            return;
        }*/
        if (curr == null)
            c.value = "There is no current bidding!";
        else {
            if (!curr.highest_bidder.equals(username))
                c.value = "Current Item is: " + curr.itemname + " which is created by " + curr.creater + " with the price as " + curr.price + " and you are NOT the highest bidder" ;
            else{
                c.value = "Current Item is: " + curr.itemname + " which is created by " + curr.creater + " with the price as " + curr.price + " and you are the highest bidder";
            }

        }
    }

    @Override
    public void sell(String username, String itemname, int price, StringHolder c) {
        if (curr == null){
            //curr = new Auction(price,username,username,itemname);
            //c.value = "Your Auction has been Created!";
        	c.value = "There is no current bidding!";
        }
        else {
        	
        if(curr.creater.equals(username)) {
        	c.value = "Your item is sold!";
            prev = curr;
            curr = null;
        }
        
        }// sell items
            
    }

    @Override
    public void bid(String username, int price, StringHolder c) {
        if (curr == null){
            c.value = "There is no item in bidding!";
        }
        else{
            if (price <= curr.price)
                c.value = "Your price is less than current price";
            else{
                curr.price = price;
                curr.highest_bidder = username;
                c.value = "Your bid has been placed";
            }

        }
    }

    @Override
    public void view_high_bidder(String username, StringHolder name, StringHolder c) {
        if (curr == null){
            name.value = "There is no current auction item!";
            c.value = "-1";
        }else {
            if (username.equals(curr.highest_bidder)) {
                name.value = curr.highest_bidder;
                c.value = String.valueOf(curr.price);
            }
            else{
                name.value = "You are NOT the highest bidder";
                c.value = "-1";
            }

        }
    }
    @Override
    public void view_bid(String username, StringHolder output) {
    	//view bid and view high bidders
        if (curr == null){
            output.value = "There is no current auction item!";
            return;
        }
        if (username.equals(curr.creater)){
            output.value = "Your item sells to "+curr.highest_bidder;
            prev = curr;
            curr = null;
        }
        else
            output.value = "You have not created the currrent auction item";
    }
    
    public void offer_item(String username, String itemname, int price, StringHolder c) {
        if (curr == null){
            curr = new Auction(price,username,username,itemname);
            c.value = "Current Item is: " + curr.itemname + " which is created by " + curr.creater + " with the price as " + curr.price + " and you have offered the item as"+ curr.creater;
        }
        else
            c.value = "Current Auction is still in bidding!";
    }
}
