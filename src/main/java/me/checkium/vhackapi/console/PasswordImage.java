package me.checkium.vhackapi.console;

import java.awt.image.BufferedImage;
import java.io.IOException;

//TODO: cleanup code
public class PasswordImage extends Image {

    private String text;

    public PasswordImage(String base64String) throws IOException {
        super(base64String);
    }

    public PasswordImage(BufferedImage image) {
        super(image);
    }

    @Override
    protected void parseImageData() {
        readInPassword();
    }

    private void readInPassword() {
        text = "";
        StringBuffer readText = new StringBuffer();
        Letters letters = Letters.getInstance();
        boolean arePixelsInTenCharSpace = arePixelsInTenCharSpace();
        int offset = (arePixelsInTenCharSpace ? 0 : 4);
        int numberOfCharsDelimiter = (arePixelsInTenCharSpace ? 11 : 10);

        for (int i = 1; i < numberOfCharsDelimiter; i++) {
            BufferedImage subImage = image.getSubimage((9 * i) + offset, 15, 8, 12);

            if (letters.getCharFor(generateHashFor(subImage)) == ' ') {
                System.out.println(base64RepresentationOf(subImage));
                throw new IllegalArgumentException("One of the characters is unkown at the moment. You may send us the preceding string so that we can add it to the lookup table.");
            } else {
                readText.append(letters.getCharFor(generateHashFor(subImage)));
            }

        }
        text = readText.toString();
    }

    public String getOCRString() {
        return text;
    }

    private boolean arePixelsInTenCharSpace() {
        for (int y = 15; y < 27; y++) {
            for (int x = 9; x < 12; x++) {
                if (image.getRGB(x, y) != 16711680) {
                    return true;
                }
            }
        }
        return false;
    }
}
