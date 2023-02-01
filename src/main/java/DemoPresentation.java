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
        Slide firstSlide = new Slide();
        firstSlide.setTitle("JabberPoint");
        firstSlide.addSlideItem(1, "The Java prestentation tool");
        firstSlide.addSlideItem(2, "Copyright (c) 1996-2000: Ian Darwin");
        firstSlide.addSlideItem(2, "Copyright (c) 2000-now:");
        firstSlide.addSlideItem(2, "Gert Florijn and Sylvia Stuurman");
        firstSlide.addSlideItem(4, "Calling Jabberpoint without a filename");
        firstSlide.addSlideItem(4, "will show presentation");
        firstSlide.addSlideItem(1, "Navigate:");
        firstSlide.addSlideItem(3, "Next slide: PgDn or Enter");
        firstSlide.addSlideItem(3, "Previous slide: PgUp or up-arrow");
        firstSlide.addSlideItem(3, "Quit: q or Q");
        return firstSlide;
    }

    public Slide loadSlide2() {
        Slide secondSlide = new Slide();
        secondSlide.setTitle("Demonstration of levels and styles");
        secondSlide.addSlideItem(1, "Level 1");
        secondSlide.addSlideItem(2, "Level 2");
        secondSlide.addSlideItem(1, "Again level 1");
        secondSlide.addSlideItem(1, "Level 1 has style number 1");
        secondSlide.addSlideItem(2, "Level 2 has style number 2");
        secondSlide.addSlideItem(3, "This is how level 3 looks like");
        secondSlide.addSlideItem(4, "And this is level 4");
        return secondSlide;
    }

    public Slide loadSlide3() {
        Slide thirdSlide = new Slide();
        thirdSlide.setTitle("The third slide");
        thirdSlide.addSlideItem(1, "To open a new presentation,");
        thirdSlide.addSlideItem(2, "use File->Open from the menu.");
        thirdSlide.addSlideItem(1, " ");
        thirdSlide.addSlideItem(1, "This is the end of the presentation.");
        thirdSlide.addSlideItem(new BitmapItem(1, "JabberPoint.jpg"));
        return thirdSlide;
    }
}
