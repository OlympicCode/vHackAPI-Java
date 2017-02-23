package me.checkium.vhackapi.console;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Created by Julian Mundhahs on 22.02.2017.
 */
public class NetworkImage extends Image{

    public NetworkImage(String base64String) throws IOException, TesseractException {
        super(base64String);
    }

    public NetworkImage(BufferedImage image)
    {
        super(image);
    }

    private boolean checkRedPixel()
    {
        int oneAnonymityPixel = image.getRGB(50, 38);
        int  red   = (oneAnonymityPixel & 0x00ff0000) >> 16;
        int  green = (oneAnonymityPixel & 0x0000ff00) >> 8;
        int  blue  =  oneAnonymityPixel & 0x000000ff;
        return red == 136 && green == 0 && blue == 0;
    }

    private boolean checkOCRString()
    {
       return (ocrString.contains("Matched by the FBI") || ocrString.contains("Watched by the FBI"));
    }
    public boolean checkForAnonymity()
    {
        return checkOCRString() && checkRedPixel();
    }

}
