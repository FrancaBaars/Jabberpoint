import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.Vector;

/**
 * <p>A slide. This class has drawing functionality.</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class Slide {
    public final static int WIDTH = 1200;
    public final static int HEIGHT = 800;
    private String title; //The title is kept separately
    private Vector<SlideItem> slideItems; //The SlideItems are kept in a vector

    //constructor
    public Slide() {
        this.slideItems = new Vector<>();
    }

    //getters and setters
    //Return the title of a slide
    public String getTitle() {
        return title;
    }

    //Change the title of a slide
    public void setTitle(String newTitle) {
        this.title = newTitle;
    }

    //Return all the SlideItems in a vector
    public Vector<SlideItem> getSlideItems() {
        return this.slideItems;
    }

    public void setSlideItems(Vector<SlideItem> slideItems) {
        this.slideItems = slideItems;
    }

    //Add a SlideItem
    public void append(SlideItem anItem) {
        this.getSlideItems().addElement(anItem);
    }

    //Create a TextItem out of a String and add the TextItem
    public void append(int level, String message) {
        append(new TextItem(level, message));
    }

    //Returns the SlideItem
    public SlideItem getSlideItem(int number) {
        return this.getSlideItems().elementAt(number);
    }


    //Returns the size of a slide
    public int getSize() {
        return this.getSlideItems().size();
    }

    //Draws the slide
    public void draw(Graphics graphics, Rectangle area, ImageObserver view) {
        float scale = getScale(area);
        int y = area.y;

        //The title is treated separately
        SlideItem slideItem = new TextItem(0, getTitle());
        Style style = StyleMaker.getStyle(slideItem.getLevel());
        slideItem.draw(area.x, y, scale, graphics, style, view);
        y += slideItem.getBoundingBox(graphics, view, scale, style).height;

        for (int number = 0; number < this.getSize(); number++) {

            slideItem = getSlideItems().elementAt(number);
            style = StyleMaker.getStyle(slideItem.getLevel());

            slideItem.draw(area.x, y, scale, graphics, style, view);
            y += slideItem.getBoundingBox(graphics, view, scale, style).height;
        }
    }

    //Returns the scale to draw a slide
    private float getScale(Rectangle area) {
        return Math.min(((float) area.width) / ((float) WIDTH), ((float) area.height) / ((float) HEIGHT));
    }
}
