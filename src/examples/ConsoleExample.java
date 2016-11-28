package examples;

//import java.util.ArrayList;
//import java.util.List;

import me.checkium.vhackapi.vHackAPI;
import me.checkium.vhackapi.vHackAPIBuilder;
import me.checkium.vhackapi.console.ScannedNode;
import me.checkium.vhackapi.console.TransferResult;

public class ConsoleExample {
	public static void main(String[] args) {
		 vHackAPI api = new vHackAPIBuilder().password("pass").username("user").getAPI();
		 String ip = api.getConsole().getIP(false, false);
		 ScannedNode scanned = api.getConsole().scanIP(ip);
		   
		 if(scanned.getSuccessRate() >= 70) {
			 TransferResult transfer = api.getConsole().transferTrojanTo(scanned);
			 if(transfer.getSuccess()){
				 System.out.println("Got $" + transfer.getMoneyAmount());
				 System.out.println("Gained " + transfer.getRepGained() + " rep.");
			 }			   
		 }
	}
}
