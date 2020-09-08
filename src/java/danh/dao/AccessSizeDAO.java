/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danh.dao;

import danh.db.AccessSize;
import danh.db.Framesize;
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
public class AccessSizeDAO implements Serializable {

    private static AccessSizeDAO instance;
    private static final Object LOCK = new Object();

    public static AccessSizeDAO getInstance() {
        synchronized (LOCK) {
            if (instance == null) {
                instance = new AccessSizeDAO();
            }
        }
        return instance;
    }

    public synchronized void insert(AccessSize accessSize) {
        EntityManager em = null;
        try {
            em = DBUtil.getEntityManager();
            Query query = em.createQuery("SELECT a FROM AccessSize a WHERE a.accessaryId = :accessaryId and a.framesizeId = :framesizeId");
            query.setParameter("accessaryId", accessSize.getAccessaryId());
            query.setParameter("framesizeId", accessSize.getFramesizeId());

            List<AccessSize> result = query.getResultList();
            if (result != null && !result.isEmpty()) {
                return;
            }
            em = DBUtil.getEntityManager();
            em.getTransaction().begin();
            em.persist(accessSize);
            em.getTransaction().commit();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(AccessaryDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
