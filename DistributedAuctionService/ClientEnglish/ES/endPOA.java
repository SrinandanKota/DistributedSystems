package ES;


/**
* ES/endPOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from AuctionEnd.idl
* Tuesday, March 6, 2018 2:13:50 PM PST
*/

public abstract class endPOA extends org.omg.PortableServer.Servant
 implements ES.endOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("view_auction", new java.lang.Integer (0));
    _methods.put ("sell", new java.lang.Integer (1));
    _methods.put ("bid", new java.lang.Integer (2));
    _methods.put ("view_high_bidder", new java.lang.Integer (3));
    _methods.put ("view_bid", new java.lang.Integer (4));
    _methods.put ("offer_item", new java.lang.Integer (5));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // ES/end/view_auction
       {
         String username = in.read_string ();
         org.omg.CORBA.StringHolder c = new org.omg.CORBA.StringHolder ();
         this.view_auction (username, c);
         out = $rh.createReply();
         out.write_string (c.value);
         break;
       }

       case 1:  // ES/end/sell
       {
         String username = in.read_string ();
         String itemname = in.read_string ();
         int price = in.read_long ();
         org.omg.CORBA.StringHolder c = new org.omg.CORBA.StringHolder ();
         this.sell (username, itemname, price, c);
         out = $rh.createReply();
         out.write_string (c.value);
         break;
       }

       case 2:  // ES/end/bid
       {
         String username = in.read_string ();
         int price = in.read_long ();
         org.omg.CORBA.StringHolder c = new org.omg.CORBA.StringHolder ();
         this.bid (username, price, c);
         out = $rh.createReply();
         out.write_string (c.value);
         break;
       }

       case 3:  // ES/end/view_high_bidder
       {
         String username = in.read_string ();
         org.omg.CORBA.StringHolder name = new org.omg.CORBA.StringHolder ();
         org.omg.CORBA.StringHolder c = new org.omg.CORBA.StringHolder ();
         this.view_high_bidder (username, name, c);
         out = $rh.createReply();
         out.write_string (name.value);
         out.write_string (c.value);
         break;
       }

       case 4:  // ES/end/view_bid
       {
         String username = in.read_string ();
         org.omg.CORBA.StringHolder output = new org.omg.CORBA.StringHolder ();
         this.view_bid (username, output);
         out = $rh.createReply();
         out.write_string (output.value);
         break;
       }

       case 5:  // ES/end/offer_item
       {
         String username = in.read_string ();
         String itemname = in.read_string ();
         int price = in.read_long ();
         org.omg.CORBA.StringHolder c = new org.omg.CORBA.StringHolder ();
         this.offer_item (username, itemname, price, c);
         out = $rh.createReply();
         out.write_string (c.value);
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:ES/end:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public end _this() 
  {
    return endHelper.narrow(
    super._this_object());
  }

  public end _this(org.omg.CORBA.ORB orb) 
  {
    return endHelper.narrow(
    super._this_object(orb));
  }


} // class endPOA