import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.font.TextLayout;
import java.awt.font.TextAttribute;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;
import java.text.AttributedString;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * <p>A text item.</p>
 * <p>A text item has drawing capabilities.</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class TextItem extends SlideItem {
    private String text;

    private static final String EMPTY_TEXT = "No Text Given";

    //A textitem of int level with text string
    public TextItem(int level, String string) {
        super(level);
        setText(string);
    }

    //An empty textitem
    public TextItem() {
        this(0, EMPTY_TEXT);
    }

    //Returns the text
    public String getText() {
        return this.text == null ? "" : this.text;
    }

    public void setText(String text) {
        if(text != null && !text.isEmpty()){
            this.text = text;
        }
    }

    //Returns the AttributedString for the Item
    public AttributedString getAttributedString(Style style, float scale) {
        AttributedString attrStr = new AttributedString(getText());
        int lengthText = this.text.length();

        attrStr.addAttribute(TextAttribute.FONT, style.getFont(scale), 0, lengthText);
        return attrStr;
    }

    //Returns the bounding box of an Item
    public Rectangle getBoundingBox(Graphics g, ImageObserver observer, float scale, Style myStyle) {
        List<TextLayout> layouts = getLayouts(g, myStyle, scale);
        int xsize = 0, ysize = (int) (myStyle.getLeading() * scale);
        Iterator<TextLayout> iterator = layouts.iterator();
        while (iterator.hasNext()) {
            TextLayout layout = iterator.next();
            Rectangle2D bounds = layout.getBounds();
            if (bounds.getWidth() > xsize) {
                xsize = (int) bounds.getWidth();
            }
            if (bounds.getHeight() > 0) {
                ysize += bounds.getHeight();
            }
            ysize += layout.getLeading() + layout.getDescent();
        }
        return new Rectangle((int) (myStyle.getIndent() * scale), 0, xsize, ysize);
    }

    //Draws the item
    public void draw(int x, int y, float scale, Graphics graphics, Style myStyle, ImageObserver o) {
        if (this.text == null || !this.text.isEmpty()) {
            return;
        }
        List<TextLayout> layouts = getLayouts(graphics, myStyle, scale);
        Point pen = new Point(x + (int) (myStyle.getIndent() * scale),
                y + (int) (myStyle.getLeading() * scale));
        Graphics2D g2d = (Graphics2D) graphics;
        g2d.setColor(myStyle.getColor());
        Iterator<TextLayout> it = layouts.iterator();
        while (it.hasNext()) {
            TextLayout layout = it.next();
            pen.y += layout.getAscent();
            layout.draw(g2d, pen.x, pen.y);
            pen.y += layout.getDescent();
        }
    }

    private List<TextLayout> getLayouts(Graphics g, Style s, float scale) {
        List<TextLayout> layouts = new ArrayList<>();
        AttributedString attrStr = getAttributedString(s, scale);
        Graphics2D g2d = (Graphics2D) g;
        FontRenderContext frc = g2d.getFontRenderContext();
        LineBreakMeasurer measurer = new LineBreakMeasurer(attrStr.getIterator(), frc);
        float wrappingWidth = (Slide.WIDTH - s.getIndent()) * scale;
        while (measurer.getPosition() < getText().length()) {
            TextLayout layout = measurer.nextLayout(wrappingWidth);
            layouts.add(layout);
        }
        return layouts;
    }

    public String toString() {
        return "TextItem[" + getLevel() + "," + getText() + "]";
    }
}
