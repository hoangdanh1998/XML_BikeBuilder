/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danh.crawler.rideplus;

import danh.constants.DomainConstant;
import danh.constants.PathConstant;
import danh.dao.AccessSizeDAO;
import danh.dao.AccessaryDAO;
import danh.dao.FrameSizeDAO;
import danh.db.AccessSize;
import danh.db.Accessary;
import danh.db.Framesize;
import danh.resolver.BaseCrawler;
import danh.resolver.BaseThread;
import danh.utils.FormatData;
import danh.utils.JAXBUtils;
import java.io.BufferedReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author apple
 */
public class RidePlusProductCrawler extends BaseCrawler implements Runnable {

    private String url;
    String categoryName;
//    private TblCategory category;

    public RidePlusProductCrawler(ServletContext context, String url, String categoryName) {
        super(context);
        this.url = url;
        this.categoryName = categoryName;
    }

    @Override
    public void run() {
        BufferedReader reader;
        try {
            System.out.println("Each page url: " + url);
            reader = getBufferedReaderForURL(url);
            String line = "";
            String document = "<document> <div>";
            boolean isStart = false;

            while ((line = reader.readLine()) != null) {
                if (line.contains("class=\"grid__item medium--two-thirds large--three-quarters product-list\"")) {
                    isStart = true;
                }
                if (isStart) {
                    if (line.contains("<script>")) {
                        break;
                    }
                    if (line.contains("<img")) {
                        String oldImgLine = line.substring(line.indexOf("<img"), line.indexOf(">", line.indexOf("<img")) + 1);
                        String newImgLine = oldImgLine.replaceAll(">", "/>");
                        line = line.replaceAll(oldImgLine, newImgLine);
                    }
                    document += line.trim();
                }
            }
            document += "</document>";
            try {
                synchronized (BaseThread.getInstance()) {
                    while (BaseThread.isSuspended()) {
                        BaseThread.getInstance().wait();
                    }
                }
            } catch (InterruptedException e) {
                Logger.getLogger(RidePlusProductCrawler.class.getName()).log(Level.SEVERE, null, e);
            }
            stAXParserForEachPage(document);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private synchronized void stAXParserForEachPage(String document) throws UnsupportedEncodingException {
        document = document.trim().replaceAll("itemscope", "");

        String tagName = "", imgLink = "", name = "", detailLink;
        double price;

        StartElement startElement;
        XMLEventReader eventReader;
        try {
            eventReader = parseStringToXMLEventReader(document);

            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();
                if (event.isStartElement()) {
                    startElement = event.asStartElement();
                    tagName = startElement.getName().getLocalPart();
                    if (tagName.equals("div")) {
                        Attribute attributeClass = startElement.getAttributeByName(new QName("class"));
                        String className = (attributeClass == null) ? "" : attributeClass.getValue();
                        if ("grid-product__wrapper".equals(className)) {
                            while (!tagName.equals("img")) {
                                event = eventReader.nextEvent();
                                if (event.isStartElement()) {
                                    startElement = event.asStartElement();
                                    tagName = startElement.getName().getLocalPart();
                                }
                            }
                            imgLink = startElement.getAttributeByName(new QName("src")).getValue();

                            while (!tagName.equals("a")) {
                                event = eventReader.nextEvent();
                                if (event.isStartElement()) {
                                    startElement = event.asStartElement();
                                    tagName = startElement.getName().getLocalPart();
                                }
                            }
                            detailLink = startElement.getAttributeByName(new QName("href")).getValue();

                            while (!tagName.equals("h4")) {
                                event = eventReader.nextEvent();
                                if (event.isStartElement()) {
                                    startElement = event.asStartElement();
                                    tagName = startElement.getName().getLocalPart();
                                }
                            }
                            while (!event.isCharacters()) {
                                event = eventReader.nextEvent();
                            }
                            name = event.asCharacters().getData();

                            while (!tagName.equals("span")) {
                                event = eventReader.nextEvent();
                                if (event.isStartElement()) {
                                    startElement = event.asStartElement();
                                    tagName = startElement.getName().getLocalPart();
                                }

                            }
                            while (!event.isCharacters()) {
                                event = eventReader.nextEvent();
                            }
                            price = Double.parseDouble(FormatData.formatPrice(event.asCharacters().getData()));

                            String categoryNameCustom = (name.toLowerCase().contains("khung")) ? "Khung" : categoryName;
                            Accessary myAccessary = new Accessary(null, name, categoryNameCustom, price, imgLink.substring(2), DomainConstant.RIDEPLUS + detailLink, true);

                            boolean validate = JAXBUtils.validateXml(PathConstant.FILE_PATH_ACCESSARY_XSD, myAccessary);
                            if (validate) {
                                AccessaryDAO accessaryDAO = AccessaryDAO.getInstance();
                                accessaryDAO.insert(myAccessary);
                                if (myAccessary.getAccessaryName().toLowerCase().contains("khung")) {
                                    myAccessary = accessaryDAO.getAccessaryByDetailLink(myAccessary.getDetailLink());
                                    List<Integer> listSize = FormatData.getFrameSize(63, 44);
                                    FrameSizeDAO framesizeDAO = FrameSizeDAO.getInstance();
                                    AccessSizeDAO accessSizeDAO = AccessSizeDAO.getInstance();
                                    for (Integer integer : listSize) {
                                        Framesize framesize = framesizeDAO.getFramesizeBySize(integer);
                                        AccessSize accessSize = new AccessSize(myAccessary, framesize);
                                        accessSizeDAO.insert(accessSize);
                                    }
                                }
                            }

                        }
                    }
                }
            }
        } catch (XMLStreamException ex) {
            Logger.getLogger(RidePlusProductCrawler.class.getName()).log(Level.SEVERE, url, ex);
        }
    }

}
