import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;

import javax.imageio.ImageIO;

import java.io.IOException;


/**
 * <p>The class for a Bitmap item</p>
 * <p>Bitmap items are responsible for drawing themselves.</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class BitmapItem extends SlideItem {
    private BufferedImage bufferedImage;
    private String imageName;

    private static final String FILE = "File ";
    private static final String NOTFOUND = " not found";


    //level indicates the item-level; name indicates the name of the file with the image
    public BitmapItem(int level, String name) {
        super(level);
        imageName = name;
        try {
            bufferedImage = ImageIO.read(new File(imageName));
        } catch (IOException e) {
            System.err.println(FILE + imageName + NOTFOUND);
        }
    }

    //An empty bitmap item
    public BitmapItem() {
        this(0, null);
    }

    //Returns the filename of the image
    public String getName() {
        return imageName;
    }

    //Returns the bounding box of the image
    public Rectangle getBoundingBox(Graphics g, ImageObserver observer, float scale, Style myStyle) {
        int x = (int) (myStyle.getIndent() * scale);
        int width = (int) (bufferedImage.getWidth(observer) * scale);
        int height = ((int) (myStyle.getLeading() * scale)) + (int) (bufferedImage.getHeight(observer) * scale);
        return new Rectangle(x, 0, width, height);
    }

    //Draws the image
    public void draw(int x, int y, float scale, Graphics g, Style myStyle, ImageObserver observer) {
        int xDrawImage = x + (int) (myStyle.getIndent() * scale);
        int yDrawImage = y + (int) (myStyle.getLeading() * scale);
        int width = (int) (bufferedImage.getWidth(observer) * scale);
        int height = (int) (bufferedImage.getHeight(observer) * scale);

        g.drawImage(bufferedImage, xDrawImage, yDrawImage, width ,height, observer);
    }

    public String toString() {
        return "BitmapItem[" + getLevel() + "," + imageName + "]";
    }
}
