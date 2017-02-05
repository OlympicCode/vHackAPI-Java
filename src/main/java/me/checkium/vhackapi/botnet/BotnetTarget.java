package me.checkium.vhackapi.botnet;

public enum BotnetTarget {

	KFMSolutions (1),
	VHACKSERVER  (2),
	NETCOINBANK  (3);
	private final int companyN;
	
	BotnetTarget(int companyN){
		
		this.companyN = companyN;
		
	}
	
	int getCompanyN(){
		
		return companyN;
		
	}
}
