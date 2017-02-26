package me.checkium.vhackapi.console;

import net.sourceforge.tess4j.TesseractException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.MalformedParametersException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Julian Mundhahs on 23.02.2017.
 */
public class PasswordImageHelper {

    String secret;
    Pattern patternSecret;
    PasswordImage[] passwordImages = new PasswordImage[4];

    public PasswordImageHelper(String vulnScanResultString) throws IOException {
        this(new JSONObject(vulnScanResultString));
    }

    public PasswordImageHelper(JSONObject result) throws IOException {
        secret = result.getString("secret");
        patternSecret = Pattern.compile(secret.replace("*", ".{1}"));

        passwordImages[0] = new PasswordImage(result.getString("img_0"));
        passwordImages[1] = new PasswordImage(result.getString("img_1"));
        passwordImages[2] = new PasswordImage(result.getString("img_2"));
        passwordImages[3] = new PasswordImage(result.getString("img_3"));
    }

    public PasswordImage[] getPasswordImages()
    {
        return passwordImages;
    }

    public String getSecret()
    {
        return secret;
    }

    public int getIDOfRightImage()
    {
        for (PasswordImage passwordImage : passwordImages)
        {
            Matcher matcher = patternSecret.matcher(passwordImage.getOCRString());
            if(matcher.find())
            {
                return Arrays.asList(passwordImages).indexOf(passwordImage);
            }
        }
        throw new MalformedParametersException("no matches");
    }
}
