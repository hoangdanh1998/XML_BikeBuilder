/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danh.utils;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author apple
 */
public class DBUtil implements Serializable{
    private static EntityManagerFactory emf;
    private static final Object LOCK = new Object();
    
      public static EntityManager getEntityManager() throws SQLException, ClassNotFoundException {
        synchronized (LOCK) {
            if (emf == null)
            try {
                emf = Persistence.createEntityManagerFactory("ProjectXML_BikeMixedPU");
            } catch (Exception e) {
                Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE,null, e);
            }
        }
        return emf.createEntityManager();
      }
}
