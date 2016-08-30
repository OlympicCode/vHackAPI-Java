package examples;

import java.util.ArrayList;

import me.checkium.vhackapi.vHackAPI;
import me.checkium.vhackapi.vHackAPIBuilder;
import me.checkium.vhackapi.console.ScanResult;
import me.checkium.vhackapi.console.TransferResult;

public class ConsoleExample {
	public void hackExample(){
		   vHackAPI api = new vHackAPIBuilder().password("pass").username("user").getAPI();
		   ArrayList<String> ip = api.getConsole().getIPs(1, false, false);
		   ArrayList<ScanResult> stats = api.getConsole().scanIPs(ip);
		   
		   if(stats.get(0).getSuccessRate() <= 90 && stats.get(0).anonymous()) {
			   ArrayList<TransferResult> transfer = api.getConsole().trTransferIPs(ip);
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
