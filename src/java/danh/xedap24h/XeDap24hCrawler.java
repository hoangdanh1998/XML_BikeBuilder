/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danh.xedap24h;

import danh.resolver.BaseCrawler;
import danh.resolver.BaseThread;
import java.io.BufferedReader;
import java.io.UnsupportedEncodingException;
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
public class XeDap24hCrawler extends BaseCrawler implements Runnable  {
    private String url;
    private String categoryName;
//    protected TblCategory category = null;

    public XeDap24hCrawler(ServletContext context, String url, String categoryName) {
        super(context);
        this.url = url;
        this.categoryName = categoryName;
    }

    @Override
    public void run() {

        BufferedReader reader = null;
        try {
            reader = getBufferedReaderForURL(url);
            String line = "";
            String document = "";
            boolean isStart = false;

            while ((line = reader.readLine()) != null) {
                if (line.contains("page-numbers")) {
                    isStart = true;
                }
                if (isStart) {
                    document += line;
                    if (line.contains("</ul>")) {
                        break;
                    }
                }
            }
            try {
                synchronized (BaseThread.getInstance()) {
                    while (BaseThread.isSuspended()) {
                        BaseThread.getInstance().wait();
                    }
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(XeDap24hCrawler.class.getName()).log(Level.SEVERE, null, ex);
            }
            int lastPage = getLastPage("<document>" + document + "</document>");
            for (int i = 0; i < lastPage; i++) {
                String pageUrl = url + "page/" + (i + 1);

                XeDap24hProductCrawler xedap = new XeDap24hProductCrawler(this.getContext(), pageUrl,categoryName);
                Thread pageCrawlingThread = new Thread(xedap);
                pageCrawlingThread.start();
                

                try {
                    synchronized (BaseThread.getInstance()) {
                        while (BaseThread.isSuspended()) {
                            BaseThread.getInstance().wait();
                        }
                    }

                } catch (InterruptedException e) {
                    Logger.getLogger(XeDap24hCrawler.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(XeDap24hCrawler.class.getName()).log(Level.SEVERE, null, e);

        }
    }

    private int getLastPage(String document) throws UnsupportedEncodingException, XMLStreamException {
        document = document.trim();
        XMLEventReader eventReader = parseStringToXMLEventReader(document);
        String tagName = "";
        Attribute attrPage = null;
        int pageCount = 1;
        while (eventReader.hasNext()) {
            XMLEvent event = eventReader.nextEvent();
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                tagName = startElement.getName().getLocalPart();
                if ("a".equals(tagName)) {
                    attrPage = startElement.getAttributeByName(new QName("href"));
                    if (attrPage != null) {
                        String countString = attrPage.getValue();

                        int newCount = Integer.parseInt(countString.charAt(countString.length() - 2) + "");
                        if (newCount > pageCount) {
                            pageCount = newCount;
                        }
                    }
                }
            }
        }

        return pageCount;
    }
}
