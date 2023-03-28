import dataobject.SlideItemData;

import java.util.ArrayList;
import java.util.List;

/**
 * A built-in demo presentation
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

class DemoPresentation implements Loadable {
    public static final String DEMO_NAME = "Demo presentation";

    public DemoPresentation() {
    }

    public void loadFile(Presentation presentation, String unusedFilename) {
        presentation.setTitle(DEMO_NAME);

        Slide firstSlide = this.loadSlide1();
        presentation.append(firstSlide);

        Slide secondSlide = this.loadSlide2();
        presentation.append(secondSlide);

        Slide thirdSlide = this.loadSlide3();
        presentation.append(thirdSlide);
    }

    public Slide loadSlide1() {
        List<SlideItemData> slideItemListSlide1 = new ArrayList<>();
        slideItemListSlide1.add(new SlideItemData(1, "The Java prestentation tool"));
        slideItemListSlide1.add(new SlideItemData(2, "Copyright (c) 1996-2000: Ian Darwin"));
        slideItemListSlide1.add(new SlideItemData(2, "Copyright (c) 2000-now:"));
        slideItemListSlide1.add(new SlideItemData(2, "Gert Florijn and Sylvia Stuurman"));
        slideItemListSlide1.add(new SlideItemData(4, "Calling Jabberpoint without a filename"));
        slideItemListSlide1.add(new SlideItemData(4, "will show presentation"));
        slideItemListSlide1.add(new SlideItemData(1, "Navigate:"));
        slideItemListSlide1.add(new SlideItemData(3, "Next slide: PgDn or Enter"));
        slideItemListSlide1.add(new SlideItemData(3, "Previous slide: PgUp or up-arrow"));
        slideItemListSlide1.add(new SlideItemData(3, "Quit: q or Q"));
        //factory
        Slide firstSlide = SlideFactory.makeSlide();
        firstSlide.setTitle("JabberPoint");
        SlideFactory.addSlideItemsToSlide(firstSlide, slideItemListSlide1);
        return firstSlide;
    }

    public Slide loadSlide2() {
        List<SlideItemData> slideItemDataSlide2 = new ArrayList<>();
        slideItemDataSlide2.add(new SlideItemData(1, "Level 1"));
        slideItemDataSlide2.add(new SlideItemData(2, "Level 2"));
        slideItemDataSlide2.add(new SlideItemData(1, "Again level 1"));
        slideItemDataSlide2.add(new SlideItemData(1, "Level 1 has style number 1"));
        slideItemDataSlide2.add(new SlideItemData(2, "Level 2 has style number 2"));
        slideItemDataSlide2.add(new SlideItemData(3, "This is how level 3 looks like"));
        slideItemDataSlide2.add(new SlideItemData(4, "And this is level 4"));

        Slide secondSlide = SlideFactory.makeSlide();
        secondSlide.setTitle("Demonstration of levels and styles");
        SlideFactory.addSlideItemsToSlide(secondSlide, slideItemDataSlide2);
        return secondSlide;
    }

    public Slide loadSlide3() {
        List<SlideItemData> slideItemDataSlide3 = new ArrayList<>();
        slideItemDataSlide3.add(new SlideItemData(1, "To open a new presentation,"));
        slideItemDataSlide3.add(new SlideItemData(2, "use File->Open from the menu."));
        slideItemDataSlide3.add(new SlideItemData(1, " "));
        slideItemDataSlide3.add(new SlideItemData(1, "This is the end of the presentation."));
        Slide thirdSlide = SlideFactory.makeSlide();
        thirdSlide.setTitle("The third slide");
        SlideFactory.addSlideItemsToSlide(thirdSlide, slideItemDataSlide3);
        thirdSlide.addSlideItem(new BitmapItem(1, "JabberPoint.jpg"));
        return thirdSlide;
    }
}
