package readers;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import interfaces.BlockCreator;
import interfaces.FillableForBlock;
import objects.BlockTemplateCreator;
import objects.BlocksFromSymbolsFactory;
import objects.FillBlockWithColor;
import objects.FillBlockWithImg;

/**
 * Specification of a block definition from file.
 *
 * @author Daniel Kaganovich
 * @version 1.0
 * @since 2018-06-08
 */
public class BlocksDefinitionReader {

    /**
     * reading a block definitions file
     * and returning a BlocksFromSymbolsFactory object.
     *
     * @param reader a file reader
     * @return a BlocksFromSymbolsFactory object
     */
    public static BlocksFromSymbolsFactory fromReader(java.io.Reader reader) {
        BufferedReader buffReader = new BufferedReader(reader);
        String line = null;

        Map<String, String> defaults = new TreeMap<String, String>();
        Map<String, Integer> spacerWidths = new TreeMap<String, Integer>();
        Map<String, BlockCreator> blockCreators = new TreeMap<String, BlockCreator>();

        try {
            line = buffReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("Problem with reading the text of the block");
        }

        while (line != null) {

            if (line.length() > 0) {

                if (line.charAt(0) != '\n' && line.charAt(0) != '#') {

                    Map<Integer, FillableForBlock> fillMap = new TreeMap<Integer, FillableForBlock>();

                    if (line.startsWith("default ")) {
                        makeDefultsMap(line, defaults, fillMap);
                    } else if (line.startsWith("bdef")) {
                        addBlockCreator(line, blockCreators, defaults, fillMap);
                    } else if (line.startsWith("sdef")) {
                        addSpacers(line, spacerWidths, defaults);
                    }
                }
            }

            try {
                line = buffReader.readLine();
            } catch (IOException e) {
                throw new RuntimeException("Problem with reading the text of the block");
            }
        }
        return new BlocksFromSymbolsFactory(spacerWidths, blockCreators);
    }

    /**
     * add the Spacers from the file.
     *
     * @param line the current line
     * @param spacerWidths the space map
     * @param defaults the defaults map
     */
    private static void addSpacers(String line, Map<String, Integer> spacerWidths, Map<String, String> defaults) {
        Pattern pattern = Pattern.compile("\\b[_.,a-zA-Z0-9-]+:(([0-9]+)|.)");
        Matcher matchLine = pattern.matcher(line);

        String symbol = null;
        int width = 0;

        if (defaults.containsKey("width")) {
            width = Integer.parseInt(defaults.get("width"));
        }

        while (matchLine.find()) {

            String[] keyVal = new String[2];
            keyVal = line.substring(matchLine.start(), matchLine.end()).split(":");

            if (keyVal[0].equals("symbol")) {
                if (keyVal[1].length() > 1 || keyVal[1].length() == 0) {
                    throw new RuntimeException("illegal seperator");
                }
                symbol = "" + keyVal[1].charAt(0);
            }

            if (keyVal[0].equals("width")) {
                width = Integer.parseInt(keyVal[1]);
            }
        }

        spacerWidths.put(symbol, width);
    }

    /**
     * add the block creators from the files.
     *
     * @param line the current line
     * @param blockCreators the block creators map
     * @param defaults the defaults map
     * @param fillMap the fill map of the block
     */
    private static void addBlockCreator(String line, Map<String, BlockCreator> blockCreators,
                                        Map<String, String> defaults, Map<Integer, FillableForBlock> fillMap) {
        Pattern pattern = Pattern.compile("\\b[_.,a-zA-Z0-9-]+:[,_\\/.()a-zA-Z0-9-]+");
        Matcher matchLine = pattern.matcher(line);
        ColorsParser colorParse = new ColorsParser();
        ImageParser imageParse = new ImageParser();
        String symbol = null;
        int height = 0;
        int width = 0;
        int hitPoints = 0;
        Color stroke = null;
        List<FillableForBlock> fillK = new ArrayList<FillableForBlock>();
        // defaults
        if (defaults.containsKey("height")) {
            height = Integer.parseInt(defaults.get("height"));
        }
        if (defaults.containsKey("symbol")) {
            symbol = defaults.get("symbol");
        }
        if (defaults.containsKey("width")) {
            width = Integer.parseInt(defaults.get("width"));
        }
        if (defaults.containsKey("hit_points")) {
            hitPoints = Integer.parseInt(defaults.get("hit_points"));
        }
        if (defaults.containsKey("fill") || defaults.containsKey("fill-1")) {
            Color fill = colorParse.colorFromString(defaults.get("fill"));
            if (fill == null) {
                fillMap.put(0, new FillBlockWithImg(imageParse.imgFromString(defaults.get("fill"))));
            } else {
                fillMap.put(0, new FillBlockWithColor(fill));
            }

            for (int i = 1; i <= hitPoints; i++) {
                if (defaults.containsKey("fill-" + i)) {
                    String s = defaults.get("fill-" + i);
                    Color fill1 = colorParse.colorFromString(s);

                    if (fill1 == null) {
                        fillMap.put(i - 1, new FillBlockWithImg(imageParse.imgFromString(s)));
                    } else {
                        fillMap.put(i - 1, new FillBlockWithColor(fill1));
                    }
                } else {
                    fillMap.put(i - 1, fillMap.get(0));
                }
            }
        }
        if (defaults.containsKey("stroke")) {
            stroke = colorParse.colorFromString(defaults.get("stroke"));
        }
        String[] keyHitFill = new String[2];

        // hit points
        Pattern patternHit = Pattern.compile("\\bhit_points:[,_\\/.()a-zA-Z0-9-]+");
        Matcher matchHit = patternHit.matcher(line);
        if (matchHit.find()) {
            keyHitFill = line.substring(matchHit.start(), matchHit.end()).split(":");
            hitPoints = Integer.parseInt(keyHitFill[1]);
        }
        // fill
        Pattern patternFill = Pattern.compile("\\b((fill:[,_\\/.()a-zA-Z0-9-]+)|(fill-1:[,_\\\\/.()a-zA-Z0-9-]+))");
        Matcher matchFill = patternFill.matcher(line);

        if (matchFill.find()) {
            keyHitFill = line.substring(matchFill.start(), matchFill.end()).split(":");
            fillMap.clear();
            Color fill = colorParse.colorFromString(keyHitFill[1]);

            if (fill == null) {
                fillMap.put(0, new FillBlockWithImg(imageParse.imgFromString(keyHitFill[1])));
            } else {
                fillMap.put(0, new FillBlockWithColor(fill));
            }
        }
        for (int i = 1; i <= hitPoints; i++) {
            if (!defaults.containsKey("fill-" + i)) {
                fillMap.put(i - 1, fillMap.get(0));
            } else {
                Color fill1 = colorParse.colorFromString(defaults.get("fill-" + i));
                if (fill1 == null) {
                    fillMap.put(i - 1, new FillBlockWithImg(imageParse.imgFromString(defaults.get("fill-" + i))));
                } else {
                    fillMap.put(i - 1, new FillBlockWithColor(fill1));
                }
            }
        }
        while (matchLine.find()) {
            String[] keyVal = new String[2];
            keyVal = line.substring(matchLine.start(), matchLine.end()).split(":");
            // from the line
            if (keyVal[0].equals("symbol")) {
                if (keyVal[1].length() > 1 || keyVal[1].length() == 0) {
                    throw new RuntimeException("illegal symbol");
                }
                symbol = "" + keyVal[1].charAt(0);
            }
            if (keyVal[0].equals("height")) {
                height = Integer.parseInt(keyVal[1]);
            }
            if (keyVal[0].equals("width")) {
                width = Integer.parseInt(keyVal[1]);
            }
            // fill-k
            for (int i = 1; i <= hitPoints; i++) {

                if (keyVal[0].equals("fill-" + i)) {
                    Color fill = colorParse.colorFromString(keyVal[1]);
                    if (fill == null) {
                        fillMap.put(i - 1, new FillBlockWithImg(imageParse.imgFromString(keyVal[1])));
                    } else {
                        fillMap.put(i - 1, new FillBlockWithColor(fill));
                    }
                }
                if (!fillMap.containsKey(i - 1)) {
                    fillMap.put(i - 1, fillMap.get(0));
                }
            }
            if (keyVal[0].equals("stroke")) {
                stroke = colorParse.colorFromString(keyVal[1]);
            }
        }
        // fill-k default values
        for (int i = 2; i <= hitPoints; i++) {

            if (!fillMap.containsKey(i - 1) && defaults.containsKey("fill-" + i)) {
                Color fill = colorParse.colorFromString(defaults.get("fill-" + i));
                if (fill == null) {
                    fillMap.put(i - 1, new FillBlockWithImg(imageParse.imgFromString(defaults.get("fill-" + i))));
                } else {
                    fillMap.put(i - 1, new FillBlockWithColor(fill));
                }
            }
        }

        for (int i = 0; i < hitPoints; i++) {
            fillK.add(fillMap.get(i));
        }

        if (symbol == null) {
            throw new RuntimeException("must have symbol");
        } else if (width <= 0) {
            throw new RuntimeException("illegal width");
        } else if (height <= 0) {
            throw new RuntimeException("illegal height");
        } else if (hitPoints <= 0) {
            throw new RuntimeException("illegal hit Points");
        }
        blockCreators.put(symbol, new BlockTemplateCreator(width, height, hitPoints, fillK, stroke));
    }

    /**
     * make the defaults map.
     *
     * @param line the current line
     * @param defaults the defaults map
     * @param fillMap the fill of the block
     */
    private static void makeDefultsMap(String line, Map<String, String> defaults,
                                       Map<Integer, FillableForBlock> fillMap) {
        Pattern pattern = Pattern.compile("\\b[_.,a-zA-Z0-9-]+:[,/.()a-zA-Z0-9-]+");
        Matcher matchLine = pattern.matcher(line);

        while (matchLine.find()) {

            String[] keyVal = new String[2];
            keyVal = line.substring(matchLine.start(), matchLine.end()).split(":");
            defaults.put(keyVal[0], keyVal[1]);

            if (keyVal[0].contains("fill-")) {
                Pattern p = Pattern.compile("[0-9]+");
                Matcher match = p.matcher(keyVal[0]);
                match.find();
                int i = Integer.parseInt(keyVal[0].substring(match.start(), match.end()));
                ColorsParser colorParse = new ColorsParser();
                ImageParser imageParse = new ImageParser();

                Color fill = colorParse.colorFromString(defaults.get("fill-" + i));

                if (fill == null) {
                    fillMap.put(i - 1, new FillBlockWithImg(imageParse.imgFromString(defaults.get("fill-" + i))));
                } else {
                    fillMap.put(i - 1, new FillBlockWithColor(fill));
                }
            }
        }
    }
}
