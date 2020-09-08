/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danh.dao;

import danh.db.Accessary;
import danh.utils.DBUtil;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author apple
 */
public class AccessaryDAO implements Serializable {

    private static AccessaryDAO instance;
    private static final Object LOCK = new Object();

    public static AccessaryDAO getInstance() {
        synchronized (LOCK) {
            if (instance == null) {
                instance = new AccessaryDAO();
            }
        }
        return instance;
    }

    public synchronized boolean insert(Accessary accessary) {
        EntityManager em = null;
        try {
            em = DBUtil.getEntityManager();
            Accessary myAccessary = getAccessaryByDetailLink(accessary.getDetailLink());
            if (myAccessary == null) {
                em.getTransaction().begin();
                em.persist(accessary);
                em.getTransaction().commit();
                return true;
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(AccessaryDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return false;
    }

    public Accessary getAccessaryByDetailLink(String link) {
        EntityManager em = null;
        try {
            em = DBUtil.getEntityManager();
            Query query = em.createQuery("SELECT a FROM Accessary a WHERE a.detailLink = :detailLink");
            query.setParameter("detailLink", link);
            List<Accessary> result = query.getResultList();
            if (result != null && !result.isEmpty()) {
                return result.get(0);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(AccessaryDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return null;
    }

    public String getAllAccessary() {
         EntityManager em = null;
        try {
            em = DBUtil.getEntityManager();
            Query query = em.createNativeQuery("SELECT CAST((SELECT * FROM accessary a FOR XML PATH('accessary'),ROOT('listAccessary')) AS VARCHAR(MAX))");

            String result = query.getSingleResult().toString();
            if (result != null && !result.isEmpty()) {
                
                return result;
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(AccessaryDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return null;
    }
}
