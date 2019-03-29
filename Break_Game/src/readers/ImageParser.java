package readers;

import java.awt.Image;

import java.io.IOException;
import java.io.InputStream;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

/**
 * parse the Image from the text to a Image.
 *
 * @author Daniel Kaganovich
 * @version 1.0
 * @since 2018-06-08
 */
public class ImageParser {

    /**
     * parse Image definition and return the specified Image.
     *
     * @param s the color string from a pattern of image(path)
     * @return the specified color
     */
    public Image imgFromString(String s) {

        if (!s.startsWith("image")) {
            return null;
        }
        Image img = null;
        String path = "";
        Pattern rgbPattern = Pattern.compile("[(].*[)]");
        Matcher matchImg = rgbPattern.matcher(s);

        matchImg.find();
        path += s.substring(matchImg.start() + 1, matchImg.end() - 1);
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(path);
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException("Problem with loading the the image");
        }

        return img;
    }

}
