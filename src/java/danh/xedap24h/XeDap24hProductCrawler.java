/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danh.xedap24h;

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
public class XeDap24hProductCrawler extends BaseCrawler implements Runnable {

    private String url;
    String categoryName;
//    private TblCategory category;

    public XeDap24hProductCrawler(ServletContext context, String url, String categoryName) {
        super(context);
        this.url = url;
        this.categoryName = categoryName;
    }

    @Override
    public void run() {
        BufferedReader reader;
        try {
            System.out.println("Each url: " + url);
            reader = getBufferedReaderForURL(url);
            String line = "";
            String document = "<document>";
            boolean isStart = false;

            while ((line = reader.readLine()) != null) {
                if (line.contains("product_list_category")) {
                    isStart = true;
                }
                if (isStart) {
                    if (line.contains("wrapper clearfix product-bottom")) {
                        break;
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
                Logger.getLogger(XeDap24hProductCrawler.class.getName()).log(Level.SEVERE, null, e);
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
                        if ("product-inner".equals(className)) {
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
                            event = eventReader.nextEvent();
                            name = event.asCharacters().getData();

                            while (!tagName.equals("span")) {
                                event = eventReader.nextEvent();
                                if (event.isStartElement()) {
                                    startElement = event.asStartElement();
                                    tagName = startElement.getName().getLocalPart();
                                }
                            }
                            event = eventReader.nextEvent();
                            price = Double.parseDouble(FormatData.formatPrice(event.asCharacters().getData()));

                            Accessary myAccessary = new Accessary(null, name, categoryName, price, imgLink, detailLink, true);
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
            Logger.getLogger(XeDap24hProductCrawler.class.getName()).log(Level.SEVERE, url, ex);
        }
    }

}
