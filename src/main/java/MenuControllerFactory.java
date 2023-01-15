import java.awt.*;

public class MenuControllerFactory {
    public static MenuItem makeMenuItem(String name) {
        return new MenuItem(name, MenuControllerFactory.makeMenuShortCut(name));
    }

    private static MenuShortcut makeMenuShortCut(String name) {
        return new MenuShortcut(name.charAt(0));
    }
}
