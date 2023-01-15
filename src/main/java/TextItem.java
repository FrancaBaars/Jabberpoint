import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>A text item.</p>
 * <p>A text item has drawing capabilities.</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class TextItem extends SlideItem {
    private String text;
    private List<TextLayout> layouts;

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
        if (text != null && !text.isEmpty()) {
            this.text = text;
        }
    }

    public List<TextLayout> getLayouts() {
        return this.layouts;
    }

    public void setLayouts(List<TextLayout> layouts) {
        this.layouts = layouts;
    }

    //Returns the AttributedString for the Item
    public AttributedString getAttributedString(Style style, float scale) {
        AttributedString attrStr = new AttributedString(getText());
        int lengthText = this.getText().length();

        attrStr.addAttribute(TextAttribute.FONT, style.getFont(scale), 0, lengthText);
        return attrStr;
    }

    //Returns the bounding box of an Item
    public Rectangle getBoundingBox(Graphics graphics, ImageObserver observer, float scale, Style myStyle) {
        this.getLayouts(graphics, myStyle, scale);

        int xsize = 0;
        int ysize = (int) (myStyle.getLeading() * scale);

        for (TextLayout layout : layouts) {
            Rectangle2D bounds = layout.getBounds();
            int width = (int) bounds.getWidth();
            int height = (int) bounds.getHeight();

            if (width > xsize) {
                xsize = width;
            }
            if (height > 0) {
                ysize += height;
            }
            ysize += layout.getLeading() + layout.getDescent();
        }
        return new Rectangle((int) (myStyle.getIndent() * scale), 0, xsize, ysize);
    }

    //Draws the item
    public void draw(int x, int y, float scale, Graphics graphics, Style myStyle, ImageObserver imageObserver) {
        if (this.text == null || this.text.isEmpty()) {
            return;
        }
        this.getLayouts(graphics, myStyle, scale);

        int xPoint = x + (int) (myStyle.getIndent() * scale);
        int yPoint = y + (int) (myStyle.getLeading() * scale);

        Graphics2D graphics2D = (Graphics2D) graphics;

        graphics2D.setColor(myStyle.getColor());
        for (TextLayout layout : layouts) {
            yPoint += layout.getAscent();
            layout.draw(graphics2D, xPoint, yPoint);
            yPoint += layout.getDescent();
        }
    }

    private void getLayouts(Graphics graphics, Style style, float scale) {
        this.layouts = new ArrayList<>();

        AttributedString attrStr = this.getAttributedString(style, scale);
        Graphics2D graphics2D = (Graphics2D) graphics;

        FontRenderContext frc = graphics2D.getFontRenderContext();
        LineBreakMeasurer measurer = new LineBreakMeasurer(attrStr.getIterator(), frc);
        float wrappingWidth = (Slide.WIDTH - style.getIndent()) * scale;

        while (measurer.getPosition() < getText().length()) {
            TextLayout layout = measurer.nextLayout(wrappingWidth);
            this.layouts.add(layout);
        }
    }

    public String toString() {
        return "TextItem[" + this.getLevel() + "," + this.getText() + "]";
    }
}
