package me.checkium.vhackapi.packages;

import java.util.HashMap;

public class MultiplePackageResult {

	private int money,netcoins,av,fw,ipsp,pcs,sdk,spam,scan,adw,boost;
	private HashMap<PackageResults, Integer> results = new HashMap<>();

	public MultiplePackageResult(PackageResult[] res) {
		
		for(PackageResult r:res){
			
			PackageResults rE = r.getResultType();
			int n = r.getResultAmount();
			if(rE == PackageResults.money){
				
				money += n;
				
			} else if (rE == PackageResults.netcoins){
				
				netcoins += n;
				
			} else if (rE == PackageResults.av){
				
				av += n;
				
			} else if (rE == PackageResults.fw){
				
				fw += n;
				
			} else if (rE == PackageResults.ipsp){
				
				ipsp += n;
				
			} else if (rE == PackageResults.btntpc){
				
				pcs += n;
				
			} else if (rE == PackageResults.sdk){
				
				sdk += n;
				
			} else if (rE == PackageResults.spam){
				
				spam += n;
				
			} else if (rE == PackageResults.scan){
				
				scan += n;
				
			} else if (rE == PackageResults.adw){
				
				adw += n;
				
			} else if (rE == PackageResults.boost){
				
				boost += n;
				
			}
			
		}
		
	}
	
	public HashMap<PackageResults, Integer> getResults(){
		
		for(PackageResults rE:PackageResults.values()){
			
			if(rE == PackageResults.money){
				
				results.put(rE, money);
				
			} else if (rE == PackageResults.netcoins){
				
				results.put(rE, netcoins);
				
			} else if (rE == PackageResults.av){
				
				results.put(rE, av);
				
			} else if (rE == PackageResults.fw){
				
				results.put(rE, fw);
				
			} else if (rE == PackageResults.ipsp){
				
				results.put(rE, ipsp);
				
			} else if (rE == PackageResults.btntpc){
				
				results.put(rE, pcs);
				
			} else if (rE == PackageResults.sdk){
				
				results.put(rE, sdk);
				
			} else if (rE == PackageResults.spam){
				
				results.put(rE, spam);
				
			} else if (rE == PackageResults.scan){
				
				results.put(rE, scan);
				
			} else if (rE == PackageResults.adw){
				
				results.put(rE, adw);
				
			} else if (rE == PackageResults.boost){
				
				results.put(rE, boost);
				
			}
			
		}
		
		return results;
		
	}
		
}
