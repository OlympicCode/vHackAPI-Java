package examples;

import me.checkium.vhackapi.VHackAPI;
import me.checkium.vhackapi.packages.PackageResult;
import me.checkium.vhackapi.VHackAPIBuilder;

public class PackageExample {

    VHackAPI api = new VHackAPIBuilder().password("pass").username("user").getAPI();

    public void openPackage() {
        PackageResult openresult = api.getPackageManager().openPackage();
        System.out.println("Got " + openresult.getResultAmount() + api.humanizeString(openresult.getResultType().toString()));
    }
}
