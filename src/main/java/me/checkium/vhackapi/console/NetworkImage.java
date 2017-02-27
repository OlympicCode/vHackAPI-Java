package me.checkium.vhackapi.console;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Julian Mundhahs on 22.02.2017.
 */
public class NetworkImage extends Image{

    private String hostName = "";

    public NetworkImage(String base64String) throws IOException {
        super(base64String);

        readInHostname();
    }

    public NetworkImage(BufferedImage image)
    {
        super(image);

        readInHostname();
    }

    private boolean checkRedPixel()
    {
        int oneAnonymityPixel = image.getRGB(50, 38);
        int  red   = (oneAnonymityPixel & 0x00ff0000) >> 16;
        int  green = (oneAnonymityPixel & 0x0000ff00) >> 8;
        int  blue  =  oneAnonymityPixel & 0x000000ff;
        return red == 136 && green == 0 && blue == 0;
    }

    private void readInHostname() {
        Letters letters = Letters.getInstance();

        for(int i=0; i<7; i++)
        {
            BufferedImage subImage = image.getSubimage(9 * i + 72, 23, 8, 12);

            if(letters.getCharFor(generateHashFor(subImage)) == ' ')
            {
                throw new IllegalArgumentException("One of the characters is unkown at the moment. Plese send the base64 string to us so that we cam add it");
            }
            else
            {
                hostName += letters.getCharFor(generateHashFor(subImage));
            }
        }
    }

    @SuppressWarnings("unused")
	@Deprecated
    private boolean checkOCRString()
    {
        //return true for now because the ocr functionality still needs to be reimplemented
        return true;
       //return (ocrString.contains("Matched by the FBI") || ocrString.contains("Watched by the FBI"));
    }

    public boolean isWatchedByFBI()
    {
        return checkRedPixel();
    }

    public String getHostName()
    {
        return "XT-"+hostName+".vhack.cc";
    }

}
