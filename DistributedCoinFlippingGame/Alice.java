import java.io.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import org.stellar.sdk.AssetTypeNative;
import org.stellar.sdk.KeyPair;
import org.stellar.sdk.Memo;
import org.stellar.sdk.Network;
import org.stellar.sdk.PaymentOperation;
import org.stellar.sdk.Server;
import org.stellar.sdk.Transaction;
import org.stellar.sdk.responses.AccountResponse;
import org.stellar.sdk.responses.SubmitTransactionResponse;

public class Alice {
	
	public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
		
		
		// Generation of random number 
		Random ranNum=new Random();		
		//converting to string for hashing
		String ranVal= Integer.toString(ranNum.nextInt()); 
		
		FileWriter f_w = null;
		BufferedWriter b_w = null;
		PrintWriter out = null;
		
	    f_w = new FileWriter("AliceFile.txt");
	    b_w = new BufferedWriter(f_w);
		out = new PrintWriter(b_w);
		out.println(ranVal);
		out.close();
		
		FileInputStream fstream = new FileInputStream("AliceFile.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
        String line=br.readLine();
		
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(ranVal.getBytes("UTF-8")); // Change this to "UTF-16" if needed
		byte[] digest = md.digest();
		StringBuffer hexString = new StringBuffer();

        for (int i = 0; i < digest.length; i++) {
            String hex = Integer.toHexString(0xff & digest[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }

       String aHash=hexString.toString();
		
		//send Hash of x and bet money to banker
		Network.useTestNetwork();
		Server server = new Server("https://horizon-testnet.stellar.org");

		//Bob Secret Seed
		KeyPair source = KeyPair.fromSecretSeed("SBNCR3MIE4MRY7FXYNDLSRERYPVDTSF62Y4M2RF6H24GERF4GFN6VEHA");
		
		KeyPair destination = KeyPair.fromAccountId("GB66572QD52WIYN3O5SIHU64SIXCMR563E4IAJGQO7AUXJYWHJFZNQIR");
		
		server.accounts().account(destination);
		AccountResponse sourceAccount = server.accounts().account(source);
		
		//for transaction time
		Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("Y-MM-d HH:mm:ss");
        String transactionTime= sdf.format(cal.getTime()) ;
		
		Transaction transaction = new Transaction.Builder(sourceAccount)
		        .addOperation(new PaymentOperation.Builder(destination, new AssetTypeNative(), "20").build())
		        .addMemo(Memo.text("alice\n"+transactionTime))
		        .build();
		
		transaction.sign(source);
		
		try {
		  SubmitTransactionResponse response = server.submitTransaction(transaction);
		  Banker banker=new Banker();
		  banker.setaHash(aHash);
		  System.out.println("Success!");
		  
		} catch (Exception e) {
		  System.out.println("Something went wrong!");
		  System.out.println(e.getMessage());
		}	
		

	}

}
