import dataobject.SlideItemData;

import java.util.List;

public class SlideFactory {
    public static Slide makeSlide() {
        return new Slide();
    }

    public static void addSlideItemsToSlide(Slide slide, List<SlideItemData> slideItems) {
        for (SlideItemData slideItemData : slideItems) {
            slide.addSlideItem(slideItemData.getLevel(), slideItemData.getMessage());
        }
    }
}
