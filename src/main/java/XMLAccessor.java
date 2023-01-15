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

public class XMLAccessor extends Accessor {

    /**
     * Default API to use.
     */
    protected static final String DEFAULT_API_TO_USE = "dom";

    /**
     * Names of xml tags of attributes
     */
    protected static final String SHOWTITLE = "showtitle";
    protected static final String SLIDETITLE = "title";
    protected static final String SLIDE = "slide";
    protected static final String ITEM = "item";
    protected static final String LEVEL = "level";
    protected static final String KIND = "kind";
    protected static final String TEXT = "text";
    protected static final String IMAGE = "image";

    /**
     * Text of messages
     */
    protected static final String PCE = "Parser Configuration Exception";
    protected static final String UNKNOWNTYPE = "Unknown Element type";
    protected static final String NFE = "Number Format Exception";
    public static final int DEFAULT_LEVEL = 1;


    private String getTitle(Element element, String tagName) {
        NodeList titles = element.getElementsByTagName(tagName);
        return titles.item(0).getTextContent();
        
    }

    public void loadFile(Presentation presentation, String fileName) throws IOException {
        int slideNumber, itemNumber, max, maxItems;
        try {
            Element doc = buildDocument(fileName);
            presentation.setTitle(getTitle(doc, SHOWTITLE));

            NodeList slides = doc.getElementsByTagName(SLIDE);
            max = slides.getLength();
            for (slideNumber = 0; slideNumber < max; slideNumber++) {
                Element xmlSlide = (Element) slides.item(slideNumber);
                Slide slide = new Slide();
                slide.setTitle(getTitle(xmlSlide, SLIDETITLE));
                presentation.append(slide);

                NodeList slideItems = xmlSlide.getElementsByTagName(ITEM);
                maxItems = slideItems.getLength();
                for (itemNumber = 0; itemNumber < maxItems; itemNumber++) {
                    Element item = (Element) slideItems.item(itemNumber);
                    loadSlideItem(slide, item);
                }
            }
        } catch (IOException iox) {
            System.err.println(iox.toString());
        } catch (SAXException sax) {
            System.err.println(sax.getMessage());
        } catch (ParserConfigurationException pcx) {
            System.err.println(PCE);
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
                System.err.println(UNKNOWNTYPE);
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
