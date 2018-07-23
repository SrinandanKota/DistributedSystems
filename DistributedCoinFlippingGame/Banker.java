import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.stellar.sdk.AssetTypeNative;
import org.stellar.sdk.KeyPair;
import org.stellar.sdk.Memo;
import org.stellar.sdk.Network;
import org.stellar.sdk.PaymentOperation;
import org.stellar.sdk.Server;
import org.stellar.sdk.Transaction;
import org.stellar.sdk.responses.AccountResponse;
import org.stellar.sdk.responses.SubmitTransactionResponse;

public class Banker {
	
	String aHash;			//Alice Hash
	String bHash;			//Bob Hash
	String x;			//Alice random number
	String y;			//Bob  random number
	String aBetAmount;		//Alice bet amount
	String bBetAmount;		//Bob bet amount
	

	public void setaHash(String aHash) {
		this.aHash = aHash;
		//store the x value for round 2
		FileWriter f_w = null;
		BufferedWriter b_w = null;
		PrintWriter out = null;
		
		try {
		    f_w = new FileWriter("aHash.txt");
		    b_w = new BufferedWriter(f_w);
		    out = new PrintWriter(b_w);
		    out.println(aHash);
		    out.close();
		} catch (IOException e) {
			// System.err.println("Caught IOException: " + e.getMessage());
		}
		
	}

	public void setbHash(String bHash) {
		this.bHash = bHash;
		
		//store the y value for round 2
				FileWriter f_w = null;
				BufferedWriter b_w = null;
				PrintWriter out = null;
				try {
				    f_w = new FileWriter("bHash.txt");
				    b_w = new BufferedWriter(f_w);
				    out = new PrintWriter(b_w);
				    out.println(bHash);
				    out.close();
				} catch (IOException e) {
					// System.err.println("Caught IOException: " + e.getMessage());
   				}
		
	}

	public void setX(String x) {
		this.x = x;
	}

	public void setY(String y) {
		this.y = y;
		
	}

		
	
	public String getaHash() {
		return aHash;
	}

	public String getbHash() {
		return bHash;
	}

	public String getX() {
		return x;
	}

	public String getY() {
		return y;
	}

	

	public String getaBetAmount() {
		return aBetAmount;
	}

	public String getbBetAmount() {
		return bBetAmount;
	}

	public void setaBetAmount(String aBetAmount) {
		this.aBetAmount = aBetAmount;
	}

	public void setbBetAmount(String bBetAmount) {
		this.bBetAmount = bBetAmount;
	}

	public String decision() throws NoSuchAlgorithmException, IOException{
		
		String xValue=this.x; 
		String yValue=this.y;
		
		FileInputStream fstream = new FileInputStream("aHash.txt");
        	BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
        	String aHash=br.readLine();
        
        	FileInputStream fystream = new FileInputStream("bHash.txt");
        	BufferedReader btr = new BufferedReader(new InputStreamReader(fystream));
        	String bHash=btr.readLine();
			
		
		//check if hashes are consistent
		if(aHash.equals(hash(xValue))&&bHash.equals(hash(yValue))){
			int x=Integer.parseInt(xValue);
			int y=Integer.parseInt(yValue);
			if(((x+y)%2)==0){
				return "Alice";
			}
			else {
				return "Bob";
			}
		}
		else{
			return "values are not consistent";
		}
		
	}
	
	private String hash(String value) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(value.getBytes("UTF-8")); // Change this to "UTF-16" if needed
		byte[] digest = md.digest();
		
		StringBuffer hexString = new StringBuffer();

        for (int i = 0; i < digest.length; i++) {
            String hex = Integer.toHexString(0xff & digest[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }

       String hash=hexString.toString();
			
		return hash;
	}

	public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
		
		Banker banker=new Banker();
		
		//get x and y values from Alice and Bob
		FileInputStream f1stream = new FileInputStream("Alicefile.txt");
        	BufferedReader br = new BufferedReader(new InputStreamReader(f1stream));
        	String line=br.readLine();
        	banker.setX(line);
		
		FileInputStream fastream = new FileInputStream("Bobfile.txt");
        	BufferedReader bbr = new BufferedReader(new InputStreamReader(fastream));
        	String bline=bbr.readLine();
        	banker.setY(bline);
		
		
		//Operations of Banker		
	
		String winner=banker.decision();
		System.out.println("winner is: "+winner);
	
		//sending 95% of funds to winner
		KeyPair destination = null;
		if(winner.equals("Alice")){
			destination = KeyPair.fromAccountId("GDQHH74E6UAZLYB7PNQ2MEQTZBTH2BJ74XTKGF3ULIBQIIZM7VSUUMZB");

		}
		else if(winner.equals("Bob")){
			destination = KeyPair.fromAccountId("GCVZN4CMS7MXA2SRW7WG2SBI3GQSDO5VS76EU3CUCL4FS37DQN5HJ6LP");

		}
		
		Network.useTestNetwork();
		Server server = new Server("https://horizon-testnet.stellar.org");

		KeyPair source = KeyPair.fromSecretSeed("SAY3UWQPAM7OQIV7D2ULXW2BXV5LI4VOOXXHTIHH4YMONEYCJRU2GPRT");
		
		server.accounts().account(destination);

		// If there was no error, load up-to-date information on your account.
		AccountResponse sourceAccount = server.accounts().account(source);

		// Start building the transaction.
		Transaction transaction = new Transaction.Builder(sourceAccount)
		        .addOperation(new PaymentOperation.Builder(destination, new AssetTypeNative(), "95").build())
		        .addMemo(Memo.text("You are the Winner!!"))
		        .build();
		// Sign the transaction to prove you are actually the person sending it.
		transaction.sign(source);
		
		// And finally, send it off to Stellar!
		try {
		  SubmitTransactionResponse response = server.submitTransaction(transaction);
		  System.out.println("Success! amount sent to winner");
		  
		} catch (Exception e) {
		  System.out.println("Something went wrong!");
		  System.out.println(e.getMessage());
		}
	}

}
