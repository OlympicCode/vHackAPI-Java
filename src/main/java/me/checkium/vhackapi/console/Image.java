package me.checkium.vhackapi.console;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Created by Julian Mundhahs on 23.02.2017.
 */
public abstract class Image {

    BufferedImage image;
    String ocrString;

    public Image(String base64String) throws IOException, TesseractException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(DatatypeConverter.parseBase64Binary(base64String));
        image = ImageIO.read(byteArrayInputStream);
        byteArrayInputStream.close();
        ocrString = (new Tesseract()).doOCR(image);
    }

    public Image(BufferedImage image)
    {
        this.image = image;
    }

    public String getOCRString()
    {
        return ocrString;
    }
}
