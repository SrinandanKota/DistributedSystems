package ES;

import ES.end;
import ES.endHelper;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

public class Server {
    public static void main(String[] args) {
        try {
        	//long start=System.currentTimeMillis();
            ORB orb = ORB.init(args, null);
            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();
            AuctionFun auctionfun = new AuctionFun();
            auctionfun.setORB(orb);
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(auctionfun);
            end href = endHelper.narrow(ref);
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            String name = "end";
            NameComponent path[] = ncRef.to_name(name);
            ncRef.rebind(path, href);
            //long stop=System.currentTimeMillis();
            //System.out.println(stop-start);
            System.out.println("Server is ready");
            orb.run();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
