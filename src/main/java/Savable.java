import java.io.IOException;

public interface Savable {
    void saveFile(Presentation presentation, String unusedFilename) throws IOException;
}
