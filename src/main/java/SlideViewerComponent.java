import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JComponent;
import javax.swing.JFrame;


/**
 * <p>SlideViewerComponent is a graphical component that ca display Slides.</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class SlideViewerComponent extends JComponent {

    private Slide slide; //The current slide
    private Font labelFont; //The font for labels
    private int slideCount;
    private int currentSlideNumber;
    private JFrame frame;

    private static final Color BG_COLOR = Color.white;
    private static final Color COLOR = Color.black;
    private static final String FONT_NAME = "Dialog";
    private static final int FONT_STYLE = Font.BOLD;
    private static final int FONT_HEIGHT = 10;
    private static final int X_POS = 1100;
    private static final int Y_POS = 20;

    public SlideViewerComponent(Presentation presentation, JFrame frame) {
        this.setBackground(BG_COLOR);
        this.setSlideCount(presentation.getSize());
        this.setLabelFont(new Font(FONT_NAME, FONT_STYLE, FONT_HEIGHT));
        this.setFrame(frame);
    }

    //getters and setters
    public Slide getSlide() {
        return this.slide;
    }

    public void setSlide(Slide slide) {
        if (slide != null) {
            this.slide = slide;
        }
    }

    public Font getLabelFont() {
        return this.labelFont;
    }

    public void setLabelFont(Font labelFont) {
        if (labelFont != null) {
            this.labelFont = labelFont;
        }
    }

    public int getSlideCount() {
        return this.slideCount;
    }

    public void setSlideCount(int slideCount) {
        this.slideCount = slideCount;
    }

    public int getCurrentSlideNumber() {
        return this.currentSlideNumber;
    }

    public void setCurrentSlideNumber(int currentSlideNumber) {
        this.currentSlideNumber = currentSlideNumber;
    }

    public JFrame getFrame() {
        return this.frame;
    }

    public void setFrame(JFrame frame) {
        if (frame != null) {
            this.frame = frame;
        }
    }

    public Dimension getPreferredSize() {
        return new Dimension(Slide.WIDTH, Slide.HEIGHT);
    }

    public void update(Slide data, String title, int slideCount, int currentSlideNumber) {
        repaint();
        if (data == null) {
            return;
        }
        if (title == null || title.isEmpty()) {
            return;
        }
        this.setCurrentSlideNumber(currentSlideNumber);
        this.setSlideCount(slideCount);
        this.setSlide(data);
        this.getFrame().setTitle(title);
    }

    //Draw the slide
    public void paintComponent(Graphics graphics) {
        graphics.setColor(BG_COLOR);
        graphics.fillRect(0, 0, getSize().width, getSize().height);
        if (this.getCurrentSlideNumber() < 0 || this.getSlide() == null) {
            return;
        }
        graphics.setFont(this.getLabelFont());
        graphics.setColor(COLOR);
        graphics.drawString("Slide " + (1 + this.getCurrentSlideNumber()) + " of " + this.getSlideCount(), X_POS, Y_POS);
        Rectangle area = new Rectangle(0, Y_POS, getWidth(), (getHeight() - Y_POS));
        this.getSlide().draw(graphics, area, this);
    }
}
