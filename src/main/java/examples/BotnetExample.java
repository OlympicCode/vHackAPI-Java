package examples;

import me.checkium.vhackapi.vHackAPI;
import me.checkium.vhackapi.vHackAPIBuilder;
import me.checkium.vhackapi.botnet.Bot;
import me.checkium.vhackapi.botnet.BotnetManager;
import me.checkium.vhackapi.botnet.BotnetTarget;

public class BotnetExample {

	//Creating an API instance
	vHackAPI api = new vHackAPIBuilder().password("pass").username("user").getAPI();
	//We need a botnet manager
	BotnetManager mgr = api.getBotnetManager();
	//We need a target
	public void attack(BotnetTarget target){
		
		//Let's check the input
		switch(target){
		
		case KFMSolutions:
			if(mgr.getStrength() < 7){
				//We shouldn't attack this target if our strength is under 7
				System.out.println("You shouldn't attack this target");
				break;
				
			} else {
				
				//We're using a boolean that will be true if the attack is successful
				boolean success = mgr.attack(target);
				if(success){
					
					System.out.println("Attaccked KFM Solutions, got 200 NC");
					
				} else {
					
					//We verified that we have enough strength, so if we fail the target is already attacked
					System.out.println("You already attacked this target");
					
				}
				
			}
		case VHACKSERVER: //you could do it by yourself ;)
		
		case NETCOINBANK: //you could do it by yourself ;)
			
		}
		
	}
	
	
	public void getBotStats(){
	//Maybe we want to see our stats	
		//How much bots do we have?
		int count = mgr.getBotCount();
		//Let's get bots' stats
		for(int i = 1; i <= count; i++){
			
			Bot bot = mgr.getBot(i);
			//Get the botID
			System.out.println(bot.getBID());
			//Get the bot level
			System.out.println(bot.getLevel());
			//Get money needed for upgrade
			System.out.println(bot.getPrice());
			//Maybe we could upgrade our botnets?
			bot.update();
			try {
				//Let's wait half second for avoiding crashes
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
}
