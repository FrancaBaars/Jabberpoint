import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
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
        this.setImageName(name);
        try {
            this.setBufferedImage(ImageIO.read(new File(this.getImageName())));
        } catch (IOException e) {
            System.err.println(FILE + this.getImageName() + NOTFOUND);
        }
    }

    //An empty bitmap item
    public BitmapItem() {
        this(0, null);
    }

    //Returns the filename of the image
    public String getImageName() {
        return this.imageName;
    }

    public void setImageName(String imageName) {
        if (imageName != null && !imageName.isEmpty()) {
            this.imageName = imageName;
        }
    }

    public BufferedImage getBufferedImage() {
        return this.bufferedImage;
    }

    public void setBufferedImage(BufferedImage bufferedImage) {
        if (bufferedImage != null) {
            this.bufferedImage = bufferedImage;
        }
    }

    //Returns the bounding box of the image
    public Rectangle getBoundingBox(Graphics g, ImageObserver observer, float scale, Style myStyle) {
        int x = (int) (myStyle.getIndent() * scale);
        int width = (int) (this.getBufferedImage().getWidth(observer) * scale);
        int height = ((int) (myStyle.getLeading() * scale)) + (int) (this.getBufferedImage().getHeight(observer) * scale);
        return new Rectangle(x, 0, width, height);
    }

    //Draws the image
    public void draw(int x, int y, float scale, Graphics g, Style myStyle, ImageObserver observer) {
        int xDrawImage = x + (int) (myStyle.getIndent() * scale);
        int yDrawImage = y + (int) (myStyle.getLeading() * scale);
        int width = (int) (this.getBufferedImage().getWidth(observer) * scale);
        int height = (int) (this.getBufferedImage().getHeight(observer) * scale);

        g.drawImage(this.getBufferedImage(), xDrawImage, yDrawImage, width, height, observer);
    }

    public String toString() {
        return "BitmapItem[" + getLevel() + "," + this.getImageName() + "]";
    }
}
