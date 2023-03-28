package dataobject;

public class SlideItemData {
    private int level;
    private String message;

    public SlideItemData(int level, String message) {
        this.level = level;
        this.message = message;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
