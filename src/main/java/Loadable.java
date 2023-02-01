import java.io.IOException;

public interface Loadable {
    void loadFile(Presentation presentation, String unusedFilename) throws IOException;
}
