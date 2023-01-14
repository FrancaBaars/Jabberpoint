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
        this.setPresentation(presentation);
        this.setLabelFont(new Font(FONTNAME, FONTSTYLE, FONTHEIGHT));
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

    public Presentation getPresentation() {
        return this.presentation;
    }

    public void setPresentation(Presentation presentation) {
        if (presentation != null) {
            this.presentation = presentation;
        }
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

    public void update(Presentation presentation, Slide data) {
        repaint();
        if (data == null) {
            return;
        }
        this.setPresentation(presentation);
        this.setSlide(data);
        this.getFrame().setTitle(presentation.getTitle());
    }

    //Draw the slide
    public void paintComponent(Graphics graphics) {
        graphics.setColor(BGCOLOR);
        graphics.fillRect(0, 0, getSize().width, getSize().height);
        if (this.getPresentation().getCurrentSlideNumber() < 0 || this.getSlide() == null) {
            return;
        }
        graphics.setFont(this.getLabelFont());
        graphics.setColor(COLOR);
        graphics.drawString("Slide " + (1 + this.getPresentation().getCurrentSlideNumber()) + " of " + this.getPresentation().getSize(), XPOS, YPOS);
        Rectangle area = new Rectangle(0, YPOS, getWidth(), (getHeight() - YPOS));
        this.getSlide().draw(graphics, area, this);
    }
}
