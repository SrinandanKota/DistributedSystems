import org.stellar.sdk.KeyPair;
import org.stellar.sdk.Server;
import org.stellar.sdk.responses.AccountResponse;

import java.net.*;
import java.io.*;
import java.util.*;

public class Account {
	
	public static void main(String[] args) throws MalformedURLException, IOException{
		
		//seed
		KeyPair pair = KeyPair.random();
		
		System.out.println(new String(pair.getSecretSeed()));
		
		System.out.println(pair.getAccountId());
		
		//account setup
		String friendbotUrl = String.format(
				  "https://friendbot.stellar.org/?addr=%s",
				  pair.getAccountId());
		InputStream response = new URL(friendbotUrl).openStream();
		String body = new Scanner(response, "UTF-8").useDelimiter("\\A").next();
		System.out.println("SUCCESS! You have a new account :)\n" + body);
		
		System.out.println("View Account id and its balance. Press Y for yes and N for NO");
		Scanner sc=new Scanner(System.in);
		String ans=(String) sc.next();
		
		//check balance
		if(ans.equals("Y")) {
			Server server = new Server("https://horizon-testnet.stellar.org");
			AccountResponse account = server.accounts().account(pair);
			System.out.println("Balances for account " + pair.getAccountId());
			for (AccountResponse.Balance balance : account.getBalances()) {
			  System.out.println(String.format(
			    "Type: %s, Code: %s, Balance: %s",
			    balance.getAssetType(),
			    balance.getAssetCode(),
			    balance.getBalance()));
			}
		}

		
	}
}
