/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danh.crawler.rideplus;

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
public class RidePlusThread extends BaseThread implements Runnable {
    private ServletContext context;

    public RidePlusThread(ServletContext context) {
        this.context = context;
    }

    @Override
    public void run() {
            try {
                RidePlusCategoriesCrawler categoriesCrawler = new RidePlusCategoriesCrawler(context);
                Map<String, String> categories = categoriesCrawler.getCategories(DomainConstant.RIDEPLUS);
                System.out.println("Categories: "+categories.size());
                for (Map.Entry<String, String> entry : categories.entrySet()) {
                    String link = entry.getKey(); 
                    String categoryName = entry.getValue();
                    System.out.println("Link: " +link);
                    System.out.println("Value: " +categoryName);
                    Thread crawlingThread = new Thread(new RidePlusCrawler(context, DomainConstant.RIDEPLUS+link, categoryName));
                    crawlingThread.start();
                    synchronized (BaseThread.getInstance()) {
                        while (BaseThread.isSuspended()) {
                            BaseThread.getInstance().wait();
                        }
                    }
                }
                synchronized (BaseThread.getInstance()) {
                    while (BaseThread.isSuspended()) {
                        BaseThread.getInstance().wait();
                    }
                }
            } catch (Exception e) {
                Logger.getLogger(RidePlusThread.class.getName()).log(Level.SEVERE, null, e);
            }
        }
}
