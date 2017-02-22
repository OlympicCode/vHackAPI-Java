package me.checkium.vhackapi;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Created by Julian Mundhahs on 22.02.2017.
 */
public class ScannedImage {

    private BufferedImage image;

    public ScannedImage(String base64String) throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(DatatypeConverter.parseBase64Binary(base64String));
        image = ImageIO.read(byteArrayInputStream);
        byteArrayInputStream.close();
    }

    public ScannedImage(BufferedImage image)
    {
        this.image = image;
    }

    public boolean checkForAnonymity()
    {
        int oneAnonymityPixel = image.getRGB(50, 38);
        int  red   = (oneAnonymityPixel & 0x00ff0000) >> 16;
        int  green = (oneAnonymityPixel & 0x0000ff00) >> 8;
        int  blue  =  oneAnonymityPixel & 0x000000ff;
        return red == 136 && green == 0 && blue == 0;
    }

}
