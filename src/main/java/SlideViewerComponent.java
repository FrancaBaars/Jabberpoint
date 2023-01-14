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
    private Presentation presentation; //The presentation
    private JFrame frame;

    private static final long serialVersionUID = 227L;
    private static final Color BGCOLOR = Color.white;
    private static final Color COLOR = Color.black;
    private static final String FONTNAME = "Dialog";
    private static final int FONTSTYLE = Font.BOLD;
    private static final int FONTHEIGHT = 10;
    private static final int XPOS = 1100;
    private static final int YPOS = 20;

    public SlideViewerComponent(Presentation presentation, JFrame frame) {
        this.setBackground(BGCOLOR);
        this.presentation = presentation;
        this.labelFont = new Font(FONTNAME, FONTSTYLE, FONTHEIGHT);
        this.frame = frame;
    }

    public Dimension getPreferredSize() {
        return new Dimension(Slide.WIDTH, Slide.HEIGHT);
    }

    public void update(Presentation presentation, Slide data) {
        repaint();
        if (data == null) {
            return;
        }
        this.presentation = presentation;
        this.slide = data;
        this.frame.setTitle(presentation.getTitle());
    }

    //Draw the slide
    public void paintComponent(Graphics graphics) {
        graphics.setColor(BGCOLOR);
        graphics.fillRect(0, 0, getSize().width, getSize().height);
        if (presentation.getCurrentSlideNumber() < 0 || slide == null) {
            return;
        }
        graphics.setFont(labelFont);
        graphics.setColor(COLOR);
        graphics.drawString("Slide " + (1 + presentation.getCurrentSlideNumber()) + " of " +
                presentation.getSize(), XPOS, YPOS);
        Rectangle area = new Rectangle(0, YPOS, getWidth(), (getHeight() - YPOS));
        slide.draw(graphics, area, this);
    }
}
