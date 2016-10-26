package examples;

import java.util.ArrayList;
import java.util.List;

import me.checkium.vhackapi.vHackAPI;
import me.checkium.vhackapi.vHackAPIBuilder;
import me.checkium.vhackapi.console.ScannedNode;
import me.checkium.vhackapi.console.TransferResult;

public class ConsoleExample {
	public static void main(String[] args) {
		 vHackAPI api = new vHackAPIBuilder().password("pass").username("user").getAPI();
		   List<String> ip = api.getConsole().getIPs(1, false, false);
		   List<ScannedNode> scanned = api.getConsole().scanIPs(ip);
		   
		   if(scanned.get(0).getSuccessRate() <= 90 && scanned.get(0).isAnonymous()) {
			   ArrayList<TransferResult> transfer = api.getConsole().trTransferIPs((ArrayList<String>) ip);
			   if(transfer.get(0).getSuccess()){
				   System.out.println("Got $" + transfer.get(0).getMoneyAmount());
				   System.out.println("Gained " + transfer.get(0).getRepGained() + " rep.");
			   }
			   if(api.getAdwareManager().uploadAdware(ip.get(0))){
				   System.out.println("Adware uploaded successfully.");
			   }
			   else{
				   System.out.println("Failed to upload Adware.");
			   }
			   
		   }
		
	}
}
