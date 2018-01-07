package net.olympiccode.vhack.api.entities.console.impl;

import net.olympiccode.vhack.api.entities.console.Target;
import net.olympiccode.vhack.api.entities.impl.vHackAPIImpl;
import net.olympiccode.vhack.api.exceptions.ScanFailException;
import net.olympiccode.vhack.api.requests.Response;
import net.olympiccode.vhack.api.requests.Route;
import org.json.JSONException;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Objects;

public class TargetImpl implements Target {
    private String hostName;
    private String imageRaw;
    private boolean watched;
    private BufferedImage image;
    private vHackAPIImpl api;

    public TargetImpl(vHackAPIImpl api, String imageRaw, String hostName) {
        this.api = api;
        this.imageRaw = imageRaw;
        this.hostName = hostName;
        try {
            this.image = ImageIO.read(new ByteArrayInputStream(DatatypeConverter.parseBase64Binary(imageRaw)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int oneAnonymityPixel = image.getRGB(50, 38);
        int red = (oneAnonymityPixel & 0x00ff0000) >> 16;
        int green = (oneAnonymityPixel & 0x0000ff00) >> 8;
        int blue = oneAnonymityPixel & 0x000000ff;
        watched = (red == 136 && green == 0 && blue == 0);
    }

    public boolean isWatched() {
        return watched;
    }

    public String getHostName() {
        return hostName;
    }

    public String getImageRaw() {
        return imageRaw;
    }

    public BufferedImage getImage() {
        return image;
    }

    public ScannedTargetImpl scan() throws ScanFailException {
        if (watched) {
            throw new ScanFailException(hostName, true);
        }
        Route.CompiledRoute cr = Route.Console.SCAN_HOST.compile(api, hostName);
        Response r = api.getRequester().getResponse(cr);
        String ip = null;
        int vuln = -1;
        try {
            JSONObject object1 = new JSONObject(r.getString());
            ip = object1.getString("ipaddress");
            vuln = Integer.parseInt(object1.getString("vuln"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (Objects.isNull(ip) || vuln < 1) {
            throw new ScanFailException(ip, false);
        }
        cr = Route.Console.LOAD_REMOTE_DATA.compile(api, ip);
        r = api.getRequester().getResponse(cr);

        int fw = -1;
        int av = -1;
        int spam = -1;
        int sdk = -1;
        int ipsp = -1;
        long money = -1;
        boolean anonymous = false;
        int winchance = -1;
        int spyware = -1;
        String username = "*";
        int eloonwin = -1;

        try {
            JSONObject object2 = new JSONObject(r.getString());
            fw = Integer.parseInt(object2.getString("fw"));
            av = Integer.parseInt(object2.getString("av"));
            spam = Integer.parseInt(object2.getString("spam"));
            sdk = Integer.parseInt(object2.getString("sdk"));
            ipsp = Integer.parseInt(object2.getString("ipsp"));
            try {
            money = Long.parseLong(object2.getString("money"));
            } catch (NumberFormatException e) {
                winchance = 0;
            }
            anonymous = object2.getString("anonymous").equalsIgnoreCase("YES");
            username = object2.getString("username");
            eloonwin = Integer.parseInt(object2.getString("winelo"));
            try {
                winchance = Integer.parseInt(object2.getString("winchance"));
            } catch (NumberFormatException e) {
                winchance = 0;
            }
            spyware = Integer.parseInt(object2.getString("spyware"));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return new ScannedTargetImpl(api, fw, av, spam, sdk, ipsp, money, anonymous, winchance, spyware, username, eloonwin, ip);
    }
}
