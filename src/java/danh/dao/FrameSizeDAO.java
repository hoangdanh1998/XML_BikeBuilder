/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danh.dao;

import danh.db.Framesize;
import danh.utils.DBUtil;
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
public class FrameSizeDAO {

    private static FrameSizeDAO instance;
    private static final Object LOCK = new Object();

    public static FrameSizeDAO getInstance() {
        synchronized (LOCK) {
            if (instance == null) {
                instance = new FrameSizeDAO();
            }
        }
        return instance;
    }

    public synchronized void insert(Framesize framesize) {
        EntityManager em = null;
        try {
            em = DBUtil.getEntityManager();
            if (getFramesizeBySize(framesize.getFramesizeNumber()) == null) {
                em.getTransaction().begin();
                em.persist(framesize);
                em.getTransaction().commit();
            }

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(AccessaryDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Framesize getFramesizeBySize(int size) {
        EntityManager em = null;
        try {
            em = DBUtil.getEntityManager();
            Query query = em.createQuery("SELECT f FROM Framesize f WHERE f.framesizeNumber = :framesizeNumber");
            query.setParameter("framesizeNumber", size);
            List<Framesize> result = query.getResultList();

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
}
