import java.awt.MenuBar;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
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

    private static final long SERIAL_VERSION_UID = 227L;

    private static final String ABOUT = "About";
    private static final String FILE = "File";
    private static final String EXIT = "Exit";
    private static final String GOTO = "Go to";
    private static final String HELP = "Help";
    private static final String NEW = "New";
    private static final String NEXT = "Next";
    private static final String OPEN = "Open";
    private static final String PAGE_NUMBER = "Page number?";
    private static final String PREV = "Prev";
    private static final String SAVE = "Save";
    private static final String VIEW = "View";

    private static final String TEST_FILE = "testPresentation.xml";
    private static final String SAVED_FILE = "savedPresentation.xml";

    private static final String IO_EXCEPTION = "IO Exception: ";
    private static final String LOAD_ERROR = "Load Error";
    private static final String SAVE_ERROR = "Save Error";

    public MenuController(Frame frame, Presentation pres) {
        setParent(frame);
        setPresentation(pres);
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

    @Override
    public Frame getParent() {
        return this.parent;
    }

    public void setParent(Frame parent) {
        if(parent != null) {
            this.parent = parent;
        }
    }

    public Presentation getPresentation() {
        return this.presentation;
    }

    public void setPresentation(Presentation presentation) {
        if(presentation != null) {
            this.presentation = presentation;
        }
    }

    public void pressOpen() {
        this.getPresentation().clear();
        Accessor xmlAccessor = new XMLAccessor();
        try {
            xmlAccessor.loadFile(this.getPresentation(), TEST_FILE);
            this.getPresentation().setSlideNumber(0);
        } catch (IOException exc) {
            JOptionPane.showMessageDialog(this.getParent(), IO_EXCEPTION + exc,
                    LOAD_ERROR, JOptionPane.ERROR_MESSAGE);
        }
        this.getParent().repaint();
    }

    public void pressNew() {
        this.getPresentation().clear();
        this.getParent().repaint();
    }

    public void pressExit() {
        this.getPresentation().exit(0);
    }

    public void pressSave() {
        Accessor xmlAccessor = new XMLAccessor();
        try {
            xmlAccessor.saveFile(this.getPresentation(), SAVED_FILE);
        } catch (IOException exc) {
            JOptionPane.showMessageDialog(this.getParent(), IO_EXCEPTION + exc,
                    SAVE_ERROR, JOptionPane.ERROR_MESSAGE);
        }
    }

    public void pressNext() {
        this.getPresentation().nextSlide();
    }

    public void pressPrevious() {
        this.getPresentation().prevSlide();
    }

    public void goToPage() {
        String pageNumberStr = JOptionPane.showInputDialog(PAGE_NUMBER);
        int pageNumber = Integer.parseInt(pageNumberStr);
        this.getPresentation().setSlideNumber(pageNumber - 1);
    }

    public void openAboutBox() {
        AboutBox.show(this.getParent());
    }


    //Creating a menu-item
    public MenuItem mkMenuItem(String name) {
        return new MenuItem(name, new MenuShortcut(name.charAt(0)));
    }
}
