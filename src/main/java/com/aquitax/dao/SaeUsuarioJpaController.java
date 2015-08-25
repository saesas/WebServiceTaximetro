/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aquitax.dao;

import com.aquitax.dao.exceptions.NonexistentEntityException;
import com.aquitax.dao.exceptions.PreexistingEntityException;
import com.aquitax.domain.SaeUsuario;
import com.aquitax.util.JpaUtil;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author SAE2
 */
public class SaeUsuarioJpaController implements Serializable {

    public SaeUsuarioJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SaeUsuario saeUsuario) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(saeUsuario);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSaeUsuario(saeUsuario.getUsuStrNombre()) != null) {
                throw new PreexistingEntityException("SaeUsuario " + saeUsuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SaeUsuario saeUsuario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            saeUsuario = em.merge(saeUsuario);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = saeUsuario.getUsuStrNombre();
                if (findSaeUsuario(id) == null) {
                    throw new NonexistentEntityException("The saeUsuario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SaeUsuario saeUsuario;
            try {
                saeUsuario = em.getReference(SaeUsuario.class, id);
                saeUsuario.getUsuStrNombre();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The saeUsuario with id " + id + " no longer exists.", enfe);
            }
            em.remove(saeUsuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SaeUsuario> findSaeUsuarioEntities() {
        return findSaeUsuarioEntities(true, -1, -1);
    }

    public List<SaeUsuario> findSaeUsuarioEntities(int maxResults, int firstResult) {
        return findSaeUsuarioEntities(false, maxResults, firstResult);
    }

    private List<SaeUsuario> findSaeUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SaeUsuario.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public SaeUsuario findSaeUsuario(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SaeUsuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getSaeUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SaeUsuario> rt = cq.from(SaeUsuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
