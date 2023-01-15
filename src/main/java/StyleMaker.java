import java.awt.*;

public class StyleMaker {
    private static Style[] styles; // de styles

    public static void createStyles() {
        StyleMaker.makeStyleArray(5);

        // De styles zijn vast ingecodeerd.
        StyleMaker.styles[0] = StyleMaker.makeNewStyle(0, Color.red, 48, 20);    // style voor item-level 0
        StyleMaker.styles[1] = StyleMaker.makeNewStyle(20, Color.blue, 40, 10);    // style voor item-level 1
        StyleMaker.styles[2] = StyleMaker.makeNewStyle(50, Color.black, 36, 10);    // style voor item-level 2
        StyleMaker.styles[3] = StyleMaker.makeNewStyle(70, Color.black, 30, 10);    // style voor item-level 3
        StyleMaker.styles[4] = StyleMaker.makeNewStyle(90, Color.black, 24, 10);    // style voor item-level 4
    }

    private static void makeStyleArray(int number) {
        StyleMaker.styles = new Style[number];
    }

    private static Style makeNewStyle(int indent, Color color, int points, int leading) {
        return new Style(indent, color, points, leading);
    }


    public static Style getStyle(int level) {
        if (level >= StyleMaker.styles.length) {
            level = StyleMaker.styles.length - 1;
        }
        return StyleMaker.styles[level];
    }
}
