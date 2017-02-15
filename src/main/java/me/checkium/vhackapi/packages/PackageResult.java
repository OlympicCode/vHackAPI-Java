package me.checkium.vhackapi.packages;

public class PackageResult {

	private PackageResults type;
	private int amount;
	
	public PackageResult(PackageResults type, int amount) {
		this.type = type;
		this.amount = amount;
	}
	
	public PackageResults getResultType() {
		return type;
	}
	
	public int getResultAmount() {
		return amount;
	}
}
