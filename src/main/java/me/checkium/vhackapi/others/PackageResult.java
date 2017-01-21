package me.checkium.vhackapi.others;

public class PackageResult {

	protected PackageResultEnum typee;
	protected int amountt;
	
	public PackageResult(PackageResultEnum type, int amount) {
		typee = type;
		amountt = amount;
	}
	
	public PackageResultEnum getResultType() {
		return typee;
	}
	
	public int getResultAmount() {
		return amountt;
	}
}
