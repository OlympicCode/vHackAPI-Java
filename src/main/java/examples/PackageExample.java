package examples;

import me.checkium.vhackapi.vHackAPI;
import me.checkium.vhackapi.vHackAPIBuilder;
import me.checkium.vhackapi.packages.PackageResult;

public class PackageExample {

	vHackAPI api = new vHackAPIBuilder().password("pass").username("user").getAPI();
	
	public void openPackage() {
		PackageResult openresult = api.getPackageManager().openPackage();
		System.out.println("Got " + openresult.getResultAmount() + api.humanizeString(openresult.getResultType().toString()));
	}
}
