/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danh.crawler.rideplus;

import danh.constants.PathConstant;
import danh.dao.CategoryDAO;
import danh.db.Category;
import danh.resolver.BaseCrawler;
import danh.utils.JAXBUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author apple
 */
public class RidePlusCategoriesCrawler extends BaseCrawler {

    public RidePlusCategoriesCrawler(ServletContext context) {
        super(context);
    }

    public Map<String, String> getCategories(String url) {

        BufferedReader reader = null;

        try {
            reader = getBufferedReaderForURL(url);
            String line = "";
            String document = "";
            boolean isStart = false;
            boolean isFound = false;
            while ((line = reader.readLine()) != null) {
                
                if (line.contains("<ul") && isFound) {
                    
                    isStart = true;
                }
                if (isStart) {
                    document += line.trim();
                    if (line.contains("</ul>")) {
                        break;
                    }
                }
                if (line.contains("href=\"/collections/phu-tung\"")) {
                    System.out.println("Line found " + line);
                    isFound = true;
                }
            }
            document = document.replaceAll("Khung xe &", "");
                    
            Map<String, String> categories = new HashMap<>();
            categories = stAXparserForCategories(document);
            return categories;
        } catch (IOException ex) {
            Logger.getLogger(RidePlusCategoriesCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public Map<String, String> stAXparserForCategories(String document) {
        document = "<document>" + document.trim() + "</document>";
        XMLEventReader eventReader;
        Map<String, String> categories = new HashMap<>();
        try {
            eventReader = parseStringToXMLEventReader(document);
            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();
                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    String tagName = startElement.getName().getLocalPart();
                    if ("a".equals(tagName)) {
                        Attribute attrHref = startElement.getAttributeByName(new QName("href"));
                        String link = attrHref.getValue();
                        event = (XMLEvent) eventReader.next();
                        Characters character = event.asCharacters();
                        categories.put(link, character.getData());
                        Category category = new Category();
                        category.setCategoryName(character.getData());
                        boolean validate = JAXBUtils.validateXml(PathConstant.FILE_PATH_CATEGORY_XSD, category);
                        if (validate) {
                            CategoryDAO categoryDAO = CategoryDAO.getInstance();
                            categoryDAO.insert(category);
                        }
                    }
                }
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(RidePlusCategoriesCrawler.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        } catch (XMLStreamException ex) {
            Logger.getLogger(RidePlusCategoriesCrawler.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

        return categories;
    }
}
