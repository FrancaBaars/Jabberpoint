import java.awt.Color;
import java.awt.Font;

/**
 * <p>Style stands for Indent, Color, Font and Leading.</p>
 * <p>The link between a style number and a item level is hard-linked:
 * in Slide the style is grabbed for an item
 * with a style number the same as the item level.</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class Style {
    private static final String FONT_NAME = "Helvetica";
    private int indent;
    private Color color;
    private Font font;
    private int fontSize;
    private int leading;

    //constructor
    public Style(int indent, Color color, int points, int leading) {
        this.setIndent(indent);
        this.setColor(color);
        this.setFontSize(points);
        this.setFont(new Font(FONT_NAME, Font.BOLD, points));
        this.setLeading(leading);
    }

    //methods
    public String toString() {
        return "[" + this.getIndent() + "," + this.getColor() + "; " + this.getFontSize() + " on " + this.getLeading() + "]";
    }

    //getters and setters
    public Font getFont(float scale) {
        return font.deriveFont(this.getFontSize() * scale);
    }

    public int getIndent() {
        return this.indent;
    }

    public void setIndent(int indent) {
        this.indent = indent;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        if (color != null) {
            this.color = color;
        }
    }

    public void setFont(Font font) {
        if (font != null) {
            this.font = font;
        }
    }

    public int getFontSize() {
        return this.fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public int getLeading() {
        return this.leading;
    }

    public void setLeading(int leading) {
        this.leading = leading;
    }
}
