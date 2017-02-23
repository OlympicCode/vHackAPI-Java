package me.checkium.vhackapi.console;

import net.sourceforge.tess4j.TesseractException;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Julian Mundhahs on 23.02.2017.
 */
public class PasswordImage extends Image {

    public PasswordImage(String base64String) throws IOException, TesseractException {
        super(base64String);
    }

    public PasswordImage(BufferedImage image)
    {
        super(image);
    }
}
