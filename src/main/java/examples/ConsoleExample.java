package examples;

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
		 //Request the Passwords 
		 //As result you will get 4 images in base64 format. Which you have to compare with the secret string
		 api.getConsole().requestPassword(ip);
		 //Enter the correct number (In this example number 2)
		 api.getConsole().enterPassword("2");
		 //Scan the User (You dont have to enter the ip)
		 ScannedNode scanned = api.getConsole().scanUser();
		 //Get The Money using the IP
		 
		   
		 if(scanned.getSuccessRate() >= 70) {
			 //if the success rate is bigger or equals 70 then transfer trojan
			 TransferResult transfer = api.getConsole().transferMoney(ip);
			 //Clear the Log
			 api.getConsole().clearLog(ip);
			 if(transfer.getSuccess()){
				 // if transfer is successfull
				 System.out.println("Got $" + transfer.getMoneyAmount());
				 System.out.println("Gained " + transfer.getRepGained() + " rep.");
			 }			   
		 }
	}
}
