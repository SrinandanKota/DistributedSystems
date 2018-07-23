import org.stellar.sdk.KeyPair;
import org.stellar.sdk.Server;
import org.stellar.sdk.responses.AccountResponse;

import org.stellar.sdk.*;
import org.stellar.sdk.responses.*;

import java.io.IOException;

import java.net.*;
import java.io.*;
import java.util.*;

public class SendLumen {
	public static void main(String[] args) throws IOException{
		Network.useTestNetwork();
		Server server = new Server("https://horizon-testnet.stellar.org");

		KeyPair source = KeyPair.fromSecretSeed("SDSWVLNGYAU2PNYGUI7ZXD6W76Y5ZZIHERRNMPW2KO62HWGWZXP7CIQV");
		KeyPair destination = KeyPair.fromAccountId("GA5GZXM772VATJNMANCNJPYRGCBD5XOKU3HGOJNT4FEB5PT3YFRJDIDR");

		// First, check to make sure that the destination account exists.
		// You could skip this, but if the account does not exist, you will be charged
		// the transaction fee when the transaction fails.
		// It will throw HttpResponseException if account does not exist or there was another error.
		server.accounts().account(destination);

		// If there was no error, load up-to-date information on your account.
		AccountResponse sourceAccount = server.accounts().account(source);

		// Start building the transaction.
		Transaction transaction = new Transaction.Builder(sourceAccount)
		        .addOperation(new PaymentOperation.Builder(destination, new AssetTypeNative(), "10").build())
		        // A memo allows you to add your own metadata to a transaction. It's
		        // optional and does not affect how Stellar treats the transaction.
		        .addMemo(Memo.text("Test Transaction"))
		        .build();
		// Sign the transaction to prove you are actually the person sending it.
		transaction.sign(source);

		// And finally, send it off to Stellar!
		try {
		  SubmitTransactionResponse response = server.submitTransaction(transaction);
		  System.out.println("Success!");
		  System.out.println(response);
		} catch (Exception e) {
		  System.out.println("Something went wrong!");
		  System.out.println(e.getMessage());
		  // If the result is unknown (no response body, timeout etc.) we simply resubmit
		  // already built transaction:
		  // SubmitTransactionResponse response = server.submitTransaction(transaction);
		}
	}
}
