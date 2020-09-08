/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danh.xedap24h;

import danh.constants.DomainConstant;
import danh.resolver.BaseThread;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;

/**
 *
 * @author apple
 */
public class XeDap24hThread extends BaseThread implements Runnable {
    private ServletContext context;

    public XeDap24hThread(ServletContext context) {
        this.context = context;
    }

    @Override
    public void run() {
            try {
                XeDap24hCategoriesCrawler categoriesCrawler = new XeDap24hCategoriesCrawler(context);
                Map<String, String> categories = categoriesCrawler.getCategories(DomainConstant.XEDAP24H);
                System.out.println("Categories: "+categories.size());
                for (Map.Entry<String, String> entry : categories.entrySet()) {
                    String link = entry.getKey();
                    String categoryName = entry.getValue();
                    System.out.println("Link: " +link);
                    System.out.println("Value: " +categoryName);
                    Thread crawlingThread = new Thread(new XeDap24hCrawler(context, link, categoryName));
                    crawlingThread.start();
                    synchronized (BaseThread.getInstance()) {
                        while (BaseThread.isSuspended()) {
                            BaseThread.getInstance().wait();
                        }
                    }
                }
                XeDap24hThread.sleep(TimeUnit.DAYS.toMillis(300));
                synchronized (BaseThread.getInstance()) {
                    while (BaseThread.isSuspended()) {
                        BaseThread.getInstance().wait();
                    }
                }
            } catch (Exception e) {
                Logger.getLogger(XeDap24hThread.class.getName()).log(Level.SEVERE, null, e);
            }
        }
}
