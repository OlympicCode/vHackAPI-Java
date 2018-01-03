package me.checkium.vhackapi.console;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class NetworkImage extends Image {

    private String hostName = "";
    private boolean anonymous;
    private int firewallLevel;

    public NetworkImage(String base64String) throws IOException {
        super(base64String);
    }

    public NetworkImage(BufferedImage image) {
        super(image);
    }

    @Override
    protected void parseImageData() {
        readInHostname();
        parseLastLine();
    }

    private void parseLastLine() {
        anonymous = checkRedPixel();
        Letters letters = Letters.getInstance();

        if (anonymous) {
            int start = findStartOfFirewallText() + 77 + 13;

            for (int i = 0; i < 10; i++) {
                BufferedImage subImage = image.getSubimage(start + i * 9, 38, 8, 12);

                if (letters.getCharFor(generateHashFor(subImage)) == ' ') {
                    //we probably reached the end but it may also be an unknown char
                    break;
                } else {
                    firewallLevel *= 10;
                    firewallLevel += Character.getNumericValue(letters.getCharFor(generateHashFor(subImage)));
                }
            }
        }
    }


    private boolean checkRedPixel() {
        int oneAnonymityPixel = image.getRGB(50, 38);
        int red = (oneAnonymityPixel & 0x00ff0000) >> 16;
        int green = (oneAnonymityPixel & 0x0000ff00) >> 8;
        int blue = oneAnonymityPixel & 0x000000ff;
        return !(red == 136 && green == 0 && blue == 0);
    }

    private void readInHostname() {
        Letters letters = Letters.getInstance();

        for (int i = 0; i < 7; i++) {
            BufferedImage subImage = image.getSubimage(9 * i + 72, 23, 8, 12);

            if (letters.getCharFor(generateHashFor(subImage)) == ' ') {
                throw new IllegalArgumentException("One of the characters is unkown at the moment. Plese send the base64 string to us so that we cam add it");
            } else {
                hostName += letters.getCharFor(generateHashFor(subImage));
            }
        }
    }

    public boolean checkForAnonymity() {
        return anonymous;
    }

    public String getHostName() {
        return "XT-" + hostName + ".vhack.cc";
    }

    public int getFirewallLevel() {
        return firewallLevel;
    }

    private int findStartOfFirewallText() {
        for (int x = 0; x < 263; x++) {
            if (image.getRGB(x, 38) != 16711680) {
                return x;
            }
        }
        throw new IllegalArgumentException("The image seems to be malformed");
    }

}