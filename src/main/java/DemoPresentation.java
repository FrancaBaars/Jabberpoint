/**
 * A built-in demo presentation
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

class DemoPresentation extends Accessor {

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
        firstSlide.append(1, "The Java prestentation tool");
        firstSlide.append(2, "Copyright (c) 1996-2000: Ian Darwin");
        firstSlide.append(2, "Copyright (c) 2000-now:");
        firstSlide.append(2, "Gert Florijn and Sylvia Stuurman");
        firstSlide.append(4, "Calling Jabberpoint without a filename");
        firstSlide.append(4, "will show presentation");
        firstSlide.append(1, "Navigate:");
        firstSlide.append(3, "Next slide: PgDn or Enter");
        firstSlide.append(3, "Previous slide: PgUp or up-arrow");
        firstSlide.append(3, "Quit: q or Q");
        return firstSlide;
    }

    public Slide loadSlide2() {
        Slide secondSlide = new Slide();
        secondSlide.setTitle("Demonstration of levels and styles");
        secondSlide.append(1, "Level 1");
        secondSlide.append(2, "Level 2");
        secondSlide.append(1, "Again level 1");
        secondSlide.append(1, "Level 1 has style number 1");
        secondSlide.append(2, "Level 2 has style number 2");
        secondSlide.append(3, "This is how level 3 looks like");
        secondSlide.append(4, "And this is level 4");
        return secondSlide;
    }

    public Slide loadSlide3() {
        Slide thirdSlide = new Slide();
        thirdSlide.setTitle("The third slide");
        thirdSlide.append(1, "To open a new presentation,");
        thirdSlide.append(2, "use File->Open from the menu.");
        thirdSlide.append(1, " ");
        thirdSlide.append(1, "This is the end of the presentation.");
        thirdSlide.append(new BitmapItem(1, "JabberPoint.jpg"));
        return thirdSlide;
    }


    public void saveFile(Presentation presentation, String unusedFilename) {
        throw new IllegalStateException("Save As->Demo! called");
    }
}
