package readers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * parse the color from the text to a java.awt Color.
 *
 * @author Daniel Kaganovich
 * @version 1.0
 * @since 2018-06-08
 */
public class ColorsParser {

    /**
     * parse color definition and return the specified color.
     *
     * @param s the color string from a pattern of color(...)
     * @return the specified color
     */
    public java.awt.Color colorFromString(String s) {

        if (!s.startsWith("color")) {
            return null;
        }

        if (s.indexOf("RGB") != -1) {
            Pattern rgbPattern = Pattern.compile("[0-9]+");
            Matcher matchColor = rgbPattern.matcher(s);

            matchColor.find();
            int r = Integer.parseInt(s.substring(matchColor.start(), matchColor.end()));

            matchColor.find();
            int b = Integer.parseInt(s.substring(matchColor.start(), matchColor.end()));

            matchColor.find();
            int g = Integer.parseInt(s.substring(matchColor.start(), matchColor.end()));

            return new java.awt.Color(r, b, g);
        }

        java.awt.Color color = null;

        if (s.indexOf("black") != -1) {
            color = java.awt.Color.BLACK;

        } else if (s.indexOf("blue") != -1) {
            color = java.awt.Color.BLUE;

        } else if (s.indexOf("cyan") != -1) {
            color = java.awt.Color.CYAN;

        } else if (s.indexOf("gray") != -1) {
            color = java.awt.Color.GRAY;

        } else if (s.indexOf("lightGray") != -1) {
            color = java.awt.Color.LIGHT_GRAY;

        } else if (s.indexOf("green") != -1) {
            color = java.awt.Color.GREEN;

        } else if (s.indexOf("orange") != -1) {
            color = java.awt.Color.ORANGE;

        } else if (s.indexOf("pink") != -1) {
            color = java.awt.Color.PINK;

        } else if (s.indexOf("red") != -1) {
            color = java.awt.Color.RED;

        } else if (s.indexOf("white") != -1) {
            color = java.awt.Color.WHITE;

        } else if (s.indexOf("yellow") != -1) {
            color = java.awt.Color.YELLOW;

        }

        return color;
    }

}
