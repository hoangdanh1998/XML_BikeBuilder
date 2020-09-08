/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danh.dao;

import danh.db.Category;
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
public class CategoryDAO implements Serializable {
    private static CategoryDAO instance;
    private static final Object LOCK = new Object();

    public static CategoryDAO getInstance() {
        synchronized (LOCK) {
            if (instance == null) {
                instance = new CategoryDAO();
            }
        }
        return instance;
    }

    public synchronized void insert(Category category) {
        EntityManager em = null;
        try {
            em = DBUtil.getEntityManager();
            Category myCategory = getCategoryByName(category.getCategoryName());
            if (myCategory == null) {
                em.getTransaction().begin();
                em.persist(category);
                em.getTransaction().commit();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, "CategoryDAO insert", ex);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Category getCategoryByName(String name) {
        EntityManager em = null;
        try {
            em = DBUtil.getEntityManager();
                Query query = em.createQuery("SELECT c FROM Category c WHERE c.categoryName = :categoryName");
            query.setParameter("categoryName", name);
            List<Category> result = query.getResultList();
            if (result != null && !result.isEmpty()) {
                return result.get(0);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(AccessaryDAO.class.getName()).log(Level.SEVERE, "CategoryDAO getCategory", ex);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return null;
    }
}
