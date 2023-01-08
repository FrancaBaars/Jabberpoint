/**
 * A built-in demo presentation
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

class DemoPresentation extends Accessor {
    private Slide firstSlide;
    private Slide secondSlide;
    private Slide thirdSlide;

    public void loadFile(Presentation presentation, String unusedFilename) {
        presentation.setTitle(DEMO_NAME);

        this.loadSlide1();
        presentation.append(this.firstSlide);

        this.loadSlide2();
        presentation.append(this.secondSlide);

        this.loadSlide3();
        presentation.append(this.thirdSlide);
    }

    public void loadSlide1() {
        this.firstSlide = new Slide();
        this.firstSlide.setTitle("JabberPoint");
        this.firstSlide.append(1, "The Java prestentation tool");
        this.firstSlide.append(2, "Copyright (c) 1996-2000: Ian Darwin");
        this.firstSlide.append(2, "Copyright (c) 2000-now:");
        this.firstSlide.append(2, "Gert Florijn and Sylvia Stuurman");
        this.firstSlide.append(4, "Calling Jabberpoint without a filename");
        this.firstSlide.append(4, "will show this presentation");
        this.firstSlide.append(1, "Navigate:");
        this.firstSlide.append(3, "Next slide: PgDn or Enter");
        this.firstSlide.append(3, "Previous slide: PgUp or up-arrow");
        this.firstSlide.append(3, "Quit: q or Q");
    }

    public void loadSlide2() {
        this.secondSlide = new Slide();
        this.secondSlide.setTitle("Demonstration of levels and styles");
        this.secondSlide.append(1, "Level 1");
        this.secondSlide.append(2, "Level 2");
        this.secondSlide.append(1, "Again level 1");
        this.secondSlide.append(1, "Level 1 has style number 1");
        this.secondSlide.append(2, "Level 2 has style number 2");
        this.secondSlide.append(3, "This is how level 3 looks like");
        this.secondSlide.append(4, "And this is level 4");
    }

    public void loadSlide3() {
        this.thirdSlide = new Slide();
        this.thirdSlide.setTitle("The third slide");
        this.thirdSlide.append(1, "To open a new presentation,");
        this.thirdSlide.append(2, "use File->Open from the menu.");
        this.thirdSlide.append(1, " ");
        this.thirdSlide.append(1, "This is the end of the presentation.");
        this.thirdSlide.append(new BitmapItem(1, "JabberPoint.jpg"));
    }


    public void saveFile(Presentation presentation, String unusedFilename) {
        throw new IllegalStateException("Save As->Demo! called");
    }
}
