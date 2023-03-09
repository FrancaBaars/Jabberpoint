import java.util.Vector;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;


/**
 * XMLAccessor, reads and writes XML files
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class XMLAccessor implements Savable, Loadable {
    /**
     * Names of xml tags of attributes
     */
    private static final String SHOW_TITLE = "showtitle";
    private static final String SLIDE_TITLE = "title";
    private static final String SLIDE = "slide";
    private static final String ITEM = "item";
    private static final String LEVEL = "level";
    private static final String KIND = "kind";
    private static final String TEXT = "text";
    private static final String IMAGE = "image";

    /**
     * Text of messages
     */
    private static final String PCE = "Parser Configuration Exception";
    private static final String UNKNOWN_TYPE = "Unknown Element type";
    private static final String NFE = "Number Format Exception";
    private static final int DEFAULT_LEVEL = 1;


    private String getTitle(Element element, String tagName) {
        NodeList titles = element.getElementsByTagName(tagName);
        return titles.item(0).getTextContent();
        
    }

    public void loadFile(Presentation presentation, String fileName) throws IOException {
        try {
            Element doc = buildDocument(fileName);
            presentation.setTitle(getTitle(doc, SHOW_TITLE));

            this.addSlidesToPresentation(doc, presentation);
        } catch (IOException iox) {
            System.err.println(iox.toString());
        } catch (SAXException sax) {
            System.err.println(sax.getMessage());
        } catch (ParserConfigurationException pcx) {
            System.err.println(PCE);
        }
    }

    private void addSlidesToPresentation(Element doc, Presentation presentation) {
        int max;

        NodeList slides = doc.getElementsByTagName(SLIDE);
        max = slides.getLength();

        for (int slideNumber = 0; slideNumber < max; slideNumber++) {
            Element xmlSlide = (Element) slides.item(slideNumber);
            Slide slide = new Slide();
            slide.setTitle(getTitle(xmlSlide, SLIDE_TITLE));
            presentation.append(slide);

            this.addItemsToSlide(xmlSlide, slide);
        }
    }

    private void addItemsToSlide(Element xmlSlide, Slide slide) {
        NodeList slideItems = xmlSlide.getElementsByTagName(ITEM);
        int maxItems = slideItems.getLength();
        for (int itemNumber = 0; itemNumber < maxItems; itemNumber++) {
            Element item = (Element) slideItems.item(itemNumber);
            this.loadSlideItem(slide, item);
        }
    }

    protected void loadSlideItem(Slide slide, Element item) {
        int level = DEFAULT_LEVEL; // default
        NamedNodeMap attributes = item.getAttributes();
        String levelText = attributes.getNamedItem(LEVEL).getTextContent();
        if (levelText != null) {
            try {
                level = Integer.parseInt(levelText);
            } catch (NumberFormatException x) {
                System.err.println(NFE);
            }
        }
        String type = attributes.getNamedItem(KIND).getTextContent();
        if (TEXT.equals(type)) {
            slide.addSlideItem(new TextItem(level, item.getTextContent()));
        } else {
            if (IMAGE.equals(type)) {
                slide.addSlideItem(new BitmapItem(level, item.getTextContent()));
            } else {
                System.err.println(UNKNOWN_TYPE);
            }
        }
    }

    public void saveFile(Presentation presentation, String filename) throws IOException {
        PrintWriter out = new PrintWriter(new FileWriter(filename));
        this.saveStartXMLFile(out);
        this.saveTitlePresentation(out, presentation);

        for (int slideNumber = 0; slideNumber < presentation.getSize(); slideNumber++) {
            Slide slide = presentation.getSlide(slideNumber);

            out.println("<slide>");

            this.saveTitleSlide(slide, out);

            //deze loop misschien in een aparte methode
            Vector<SlideItem> slideItems = slide.getSlideItems();
            for (int itemNumber = 0; itemNumber < slideItems.size(); itemNumber++) {

                SlideItem slideItem = slideItems.elementAt(itemNumber);
                out.print("<item kind=");
                if (slideItem instanceof TextItem) {
                    this.saveTextItem(out, slideItem);
                } else {
                    if (slideItem instanceof BitmapItem) {
                        saveBitmapItem(out, slideItem);
                    } else {
                        System.out.println("Ignoring " + slideItem);
                    }
                }
                out.println("</item>");
            }
            out.println("</slide>");
        }
        out.println("</presentation>");
        out.close();
    }

    public Element buildDocument(String fileName) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = builder.parse(new File(fileName)); //Create a JDOM document
        return document.getDocumentElement();
    }

    private void saveTitleSlide(Slide slide, PrintWriter out) {
        out.println("<title>" + slide.getTitle() + "</title>");
    }

    private void saveTextItem(PrintWriter out, SlideItem slideItem) {
        out.print("\"text\" level=\"" + slideItem.getLevel() + "\">");
        out.print(((TextItem) slideItem).getText());
    }

    private void saveBitmapItem(PrintWriter out, SlideItem slideItem) {
        out.print("\"image\" level=\"" + slideItem.getLevel() + "\">");
        out.print(((BitmapItem) slideItem).getImageName());
    }

    private void saveStartXMLFile(PrintWriter out) {
        out.println("<?xml version=\"1.0\"?>");
        out.println("<!DOCTYPE presentation SYSTEM \"jabberpoint.dtd\">");
        out.println("<presentation>");
    }

    private void saveTitlePresentation(PrintWriter out, Presentation presentation) {
        out.print("<showtitle>");
        out.print(presentation.getTitle());
        out.println("</showtitle>");
    }
}
