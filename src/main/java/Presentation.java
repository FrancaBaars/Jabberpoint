import java.util.ArrayList;


/**
 * <p>Presentations keeps track of the slides in a presentation.</p>
 * <p>Only one instance of this class is available.</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class Presentation {
    private String title; //The title of the presentation
    private ArrayList<Slide> showList = null; //An ArrayList with slides
    private int currentSlideNumber = 0; //The number of the current slide
    private SlideViewerComponent slideViewComponent; //The view component of the slides

    //constructors
    public Presentation() {
        setSlideViewComponent(null);
        clear();
    }

    public Presentation(SlideViewerComponent slideViewerComponent) {
        setSlideViewComponent(slideViewerComponent);
        clear();
    }

    //getters and setters
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String newTitle) {
        if (newTitle != null && !newTitle.isEmpty()) {
            this.title = newTitle;
        }
    }

    public ArrayList<Slide> getShowList() {
        return this.showList;
    }

    //Returns the number of the current slide
    public int getCurrentSlideNumber() {
        return this.currentSlideNumber;
    }

    public void setCurrentSlideNumber(int currentSlideNumber) {
        if(currentSlideNumber >= 0) {
            this.currentSlideNumber = currentSlideNumber;
        }
    }

    public SlideViewerComponent getSlideViewComponent() {
        return this.slideViewComponent;
    }

    public void setSlideViewComponent(SlideViewerComponent slideViewComponent) {
        this.slideViewComponent = slideViewComponent;
    }

    public int getSize() {
        return this.getShowList().size();
    }

    //Change the current slide number and report it the window
    public void setSlideNumber(int number) {
        this.setCurrentSlideNumber(number);
        if (this.getSlideViewComponent() != null) {
            this.getSlideViewComponent().update(this, getCurrentSlide());
        }
    }

    //Navigate to the previous slide unless we are at the first slide
    public void prevSlide() {
        if (this.getCurrentSlideNumber() > 0) {
            this.setSlideNumber(this.getCurrentSlideNumber() - 1);
        }
    }

    //Navigate to the next slide unless we are at the last slide
    public void nextSlide() {
        if (this.getCurrentSlideNumber() < (this.getShowList().size() - 1)) {
            this.setSlideNumber(this.getCurrentSlideNumber() + 1);
        }
    }

    //Remove the presentation
    void clear() {
        this.showList = new ArrayList<>();
        this.setSlideNumber(-1);
    }

    //Add a slide to the presentation
    public void append(Slide slide) {
        this.getShowList().add(slide);
    }

    //Return a slide with a specific number
    public Slide getSlide(int number) {
        if (number < 0 || number >= this.getSize()) {
            return null;
        }
        return this.getShowList().get(number);
    }

    //Return the current slide
    public Slide getCurrentSlide() {
        return this.getSlide(this.getCurrentSlideNumber());
    }

    public void exit(int n) {
        System.exit(n);
    }
}
