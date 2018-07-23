import org.stellar.sdk.responses.Page;
import org.stellar.sdk.responses.TransactionResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import javax.script.ScriptException;

import org.stellar.sdk.*;
import org.stellar.sdk.requests.*;
import org.stellar.sdk.responses.operations.OperationResponse;
import org.stellar.sdk.responses.operations.PaymentOperationResponse;


public class ReceiveLumen {
	public static void main(String[] args) throws ScriptException, IOException, NoSuchMethodException, URISyntaxException{
		Server server = new Server("https://horizon-testnet.stellar.org");
		KeyPair account = KeyPair.fromAccountId("GA5GZXM772VATJNMANCNJPYRGCBD5XOKU3HGOJNT4FEB5PT3YFRJDIDR");

		// Create an API call to query payments involving the account.
		PaymentsRequestBuilder paymentsRequest = server.payments().forAccount(account);	
		paymentsRequest.cursor(null);
		//for this account
			Page<OperationResponse> nextPayments = paymentsRequest.execute();
		ArrayList<OperationResponse> opresplist=(nextPayments.getRecords());
		for (OperationResponse operationResponse : opresplist) {	
			
		    System.out.println("ID : "+operationResponse.getId());			
			System.out.println("Source Account: "+operationResponse.getSourceAccount().getAccountId());

			
			if(operationResponse.getClass().equals(PaymentOperationResponse.class)){
				
				String amount=((PaymentOperationResponse) operationResponse).getAmount();
				
				Asset asset = ((PaymentOperationResponse) operationResponse).getAsset();
			    String assetName;
			    if (asset.equals(new AssetTypeNative())) {
			        assetName = "lumens";
			      }
			    else {
			        StringBuilder assetNameBuilder = new StringBuilder();
			        assetNameBuilder.append(((AssetTypeCreditAlphaNum) asset).getCode());
			        //assetNameBuilder.append(":");
			        assetNameBuilder.append("->");
			        assetNameBuilder.append(((AssetTypeCreditAlphaNum) asset).getIssuer().getAccountId());
			        assetName = assetNameBuilder.toString();
			      }
			    
			    System.out.println("Amount received: "+amount+" "+assetName);
			    
			}
			else{
				System.out.println("This payment is in account creation stage");
			}
		}
		}
		
	}


