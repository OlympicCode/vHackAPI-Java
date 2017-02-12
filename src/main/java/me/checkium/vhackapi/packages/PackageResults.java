package me.checkium.vhackapi.packages;

public enum PackageResults {

	money("money"),
	netcoins("netcoins"), 
	av("av"), 
	fw("fw"), 
	ipsp("ipsp"), 
	btntpc("pcs"), 
	sdk("sdk"), 
	spam("spam"), 
	scan("scan"), 
	adw("adw"), 
	boost("boost");
	
	String toString;
	PackageResults(String toString){
		
		this.toString = toString;
		
	}
	
	public String toString(){
		
		return this.toString;
		
	}
	
}
