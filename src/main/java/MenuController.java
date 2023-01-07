import java.awt.MenuBar;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.JOptionPane;

/**
 * <p>The controller for the menu</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */
public class MenuController extends MenuBar {

    private Frame parent; //The frame, only used as parent for the Dialogs
    private Presentation presentation; //Commands are given to the presentation

    private static final long serialVersionUID = 227L;

    protected static final String ABOUT = "About";
    protected static final String FILE = "File";
    protected static final String EXIT = "Exit";
    protected static final String GOTO = "Go to";
    protected static final String HELP = "Help";
    protected static final String NEW = "New";
    protected static final String NEXT = "Next";
    protected static final String OPEN = "Open";
    protected static final String PAGENR = "Page number?";
    protected static final String PREV = "Prev";
    protected static final String SAVE = "Save";
    protected static final String VIEW = "View";

    protected static final String TESTFILE = "testPresentation.xml";
    protected static final String SAVEFILE = "savedPresentation.xml";

    protected static final String IOEX = "IO Exception: ";
    protected static final String LOADERR = "Load Error";
    protected static final String SAVEERR = "Save Error";

    public MenuController(Frame frame, Presentation pres) {
        parent = frame;
        presentation = pres;
        MenuItem menuItem;

        //file tab
        Menu fileMenu = new Menu(FILE);
        //press open
        fileMenu.add(menuItem = mkMenuItem(OPEN));
        menuItem.addActionListener(actionEvent -> this.pressOpen());

        //press new
        fileMenu.add(menuItem = mkMenuItem(NEW));
        menuItem.addActionListener(actionEvent -> this.pressNew());

        //press save
        fileMenu.add(menuItem = mkMenuItem(SAVE));
        menuItem.addActionListener(actionEvent -> this.pressSave());

        //press exit
        fileMenu.addSeparator();
        fileMenu.add(menuItem = mkMenuItem(EXIT));
        menuItem.addActionListener(actionEvent -> this.pressExit());

        //new tab
        add(fileMenu);
        Menu viewMenu = new Menu(VIEW);

        //press next
        viewMenu.add(menuItem = mkMenuItem(NEXT));
        menuItem.addActionListener(actionEvent -> this.pressNext());

        viewMenu.add(menuItem = mkMenuItem(PREV));
        menuItem.addActionListener(actionEvent -> this.pressPrevious());

        //go to page number
        viewMenu.add(menuItem = mkMenuItem(GOTO));
        menuItem.addActionListener(actionEvent -> this.goToPage());


        add(viewMenu);
        Menu helpMenu = new Menu(HELP);

        //press about
        helpMenu.add(menuItem = mkMenuItem(ABOUT));
        menuItem.addActionListener(actionEvent -> this.openAboutBox());

        setHelpMenu(helpMenu);        //Needed for portability (Motif, etc.).
    }

    public void pressOpen() {
        presentation.clear();
        Accessor xmlAccessor = new XMLAccessor();
        try {
            xmlAccessor.loadFile(presentation, TESTFILE);
            presentation.setSlideNumber(0);
        } catch (IOException exc) {
            JOptionPane.showMessageDialog(parent, IOEX + exc,
                    LOADERR, JOptionPane.ERROR_MESSAGE);
        }
        parent.repaint();
    }

    public void pressNew() {
        presentation.clear();
        parent.repaint();
    }

    public void pressExit() {
        presentation.exit(0);
    }

    public void pressSave() {
        Accessor xmlAccessor = new XMLAccessor();
        try {
            xmlAccessor.saveFile(presentation, SAVEFILE);
        } catch (IOException exc) {
            JOptionPane.showMessageDialog(parent, IOEX + exc,
                    SAVEERR, JOptionPane.ERROR_MESSAGE);
        }
    }

    public void pressNext() {
        this.presentation.nextSlide();
    }

    public void pressPrevious() {
        presentation.prevSlide();
    }

    public void goToPage() {
        String pageNumberStr = JOptionPane.showInputDialog((Object) PAGENR);
        int pageNumber = Integer.parseInt(pageNumberStr);
        presentation.setSlideNumber(pageNumber - 1);
    }

    public void openAboutBox() {
        AboutBox.show(parent);
    }


    //Creating a menu-item
    public MenuItem mkMenuItem(String name) {
        return new MenuItem(name, new MenuShortcut(name.charAt(0)));
    }
}
