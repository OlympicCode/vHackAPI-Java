package me.checkium.vhackapi.console;


import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;

/**
 * Created by Julian Mundhahs on 23.02.2017.
 */
public abstract class Image {

    BufferedImage image;

    public Image(String base64String) throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(DatatypeConverter.parseBase64Binary(base64String));
        image = ImageIO.read(byteArrayInputStream);
        byteArrayInputStream.close();
    }

    public Image(BufferedImage image)
    {
        this.image = image;
    }

    protected BigInteger generateHashFor(BufferedImage image) {
        return generateHashFor(image, 16711680);
    }

    protected BigInteger generateHashFor(BufferedImage image, int imageColorToSearchFor) {
        BigInteger two = BigInteger.valueOf(2);

        BigInteger hash = BigInteger.ONE;

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                hash = hash.multiply(two).add(BigInteger.valueOf((image.getRGB(x,y)==imageColorToSearchFor?0:1)));
            }
        }
        return hash;
    }
}
