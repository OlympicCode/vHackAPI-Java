package examples;

import me.checkium.vhackapi.vHackAPI;
import me.checkium.vhackapi.vHackAPIBuilder;
import me.checkium.vhackapi.others.PackageResult;

public class PackageExample {

	vHackAPI api = new vHackAPIBuilder().password("pass").username("user").getAPI();
	
	public void openPackage() {
		PackageResult openresult = api.getOthers().openPackage();
		System.out.println("Got " + openresult.getResultAmount() + api.humanizeString(openresult.getResultType().toString()));
	}
}
