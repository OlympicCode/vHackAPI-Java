/*package examples;

import me.checkium.vhackapi.vHackAPI;
import me.checkium.vhackapi.vHackAPIBuilder;
import me.checkium.vhackapi.console.ScannedNode;
import me.checkium.vhackapi.console.TransferResult;

public class ConsoleExample {
	public static void main(String[] args) {
		//Create your API instance
		 vHackAPI api = new vHackAPIBuilder().password("pass").username("user").getAPI();
		 //Get an IP trougth console
		 String ip = api.getConsole().getIP(false, false);
		//Scan the IP
		 ScannedNode scanned = api.getConsole().scanIP(ip);
		   
		 if(scanned.getSuccessRate() >= 70) {
			 //if the success rate is bigger or equals 70 then transfer trojan
			 TransferResult transfer = api.getConsole().transferTrojanTo(scanned);
			 if(transfer.getSuccess()){
				 // if transfer is successfull
				 System.out.println("Got $" + transfer.getMoneyAmount());
				 System.out.println("Gained " + transfer.getRepGained() + " rep.");
			 }			   
		 }
	}
}*/
