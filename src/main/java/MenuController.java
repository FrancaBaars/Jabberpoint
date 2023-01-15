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

    private MenuItem menuItem;

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

    public MenuController(Frame parentFrame, Presentation presentation) {
        this.setParent(parentFrame);
        this.setPresentation(presentation);
        this.makeMenu();
    }

    @Override
    public Frame getParent() {
        return this.parent;
    }

    public void setParent(Frame parent) {
        if (parent != null) {
            this.parent = parent;
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

    public MenuItem getMenuItem() {
        return this.menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        if (menuItem != null) {
            this.menuItem = menuItem;
        }
    }

    private void makeMenu() {
        this.makeFileTab();
        this.makeViewTab();
        this.makeHelpTab();

    }

    private void makeFileTab() {
        //file tab
        Menu fileMenu = new Menu(FILE);
        //press open
        this.addToMenu(fileMenu, OPEN);
        this.getMenuItem().addActionListener(actionEvent -> this.pressOpen());

        //press new
        this.addToMenu(fileMenu, NEW);
        this.getMenuItem().addActionListener(actionEvent -> this.pressNew());

        //press save
        this.addToMenu(fileMenu, SAVE);
        this.getMenuItem().addActionListener(actionEvent -> this.pressSave());

        //press exit
        fileMenu.addSeparator();
        this.addToMenu(fileMenu, EXIT);
        this.getMenuItem().addActionListener(actionEvent -> this.pressExit());

        //new tab
        add(fileMenu);
    }

    private void makeViewTab() {
        Menu viewMenu = new Menu(VIEW);
        //press next
        this.addToMenu(viewMenu, NEXT);
        this.getMenuItem().addActionListener(actionEvent -> this.pressNext());

        this.addToMenu(viewMenu, PREV);
        this.getMenuItem().addActionListener(actionEvent -> this.pressPrevious());

        //go to page number
        this.addToMenu(viewMenu, GOTO);
        this.getMenuItem().addActionListener(actionEvent -> this.goToPage());

        add(viewMenu);
    }

    private void makeHelpTab() {
        Menu helpMenu = new Menu(HELP);

        //press about
        this.addToMenu(helpMenu, ABOUT);
        this.getMenuItem().addActionListener(actionEvent -> this.openAboutBox());

        setHelpMenu(helpMenu);        //Needed for portability (Motif, etc.).
    }

    private void addToMenu(Menu menu, String name) {
        this.setMenuItem(mkMenuItem(name));
        menu.add(this.getMenuItem());
    }


    private void pressOpen() {
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

    private void pressNew() {
        this.getPresentation().clear();
        this.getParent().repaint();
    }

    private void pressExit() {
        this.getPresentation().exit(0);
    }

    private void pressSave() {
        Accessor xmlAccessor = new XMLAccessor();
        try {
            xmlAccessor.saveFile(this.getPresentation(), SAVED_FILE);
        } catch (IOException exc) {
            JOptionPane.showMessageDialog(this.getParent(), IO_EXCEPTION + exc,
                    SAVE_ERROR, JOptionPane.ERROR_MESSAGE);
        }
    }

    private void pressNext() {
        this.getPresentation().nextSlide();
    }

    private void pressPrevious() {
        this.getPresentation().prevSlide();
    }

    private void goToPage() {
        String pageNumberStr = JOptionPane.showInputDialog(PAGE_NUMBER);
        if (pageNumberStr == null || pageNumberStr.isEmpty()) {
            return;
        }

        int pageNumber = Integer.parseInt(pageNumberStr);
        if (pageNumber > this.getPresentation().getSize()) {
            return;
        }
        this.getPresentation().setSlideNumber(pageNumber - 1);
    }

    private void openAboutBox() {
        AboutBox.show(this.getParent());
    }


    //Creating a menu-item
    //dit moet misschien in een factory of zoiets niet echt te verantwoordelijkheid van dit bestand
    public MenuItem mkMenuItem(String name) {
        return new MenuItem(name, new MenuShortcut(name.charAt(0)));
    }
}
