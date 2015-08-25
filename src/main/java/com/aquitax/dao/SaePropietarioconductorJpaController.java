/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aquitax.dao;

import com.aquitax.dao.exceptions.NonexistentEntityException;
import com.aquitax.dao.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.aquitax.domain.SaePropietariovehiculo;
import com.aquitax.domain.SaeConductor;
import com.aquitax.domain.SaePropietarioconductor;
import com.aquitax.util.JpaUtil;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class SaePropietarioconductorJpaController implements Serializable {

    public SaePropietarioconductorJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SaePropietarioconductor saePropietarioconductor) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SaePropietariovehiculo prcoIntCedulapropietario = saePropietarioconductor.getPrcoIntCedulapropietario();
            if (prcoIntCedulapropietario != null) {
                prcoIntCedulapropietario = em.getReference(prcoIntCedulapropietario.getClass(), prcoIntCedulapropietario.getProIntCedula());
                saePropietarioconductor.setPrcoIntCedulapropietario(prcoIntCedulapropietario);
            }
            SaeConductor prcoIntCedulaconductor = saePropietarioconductor.getPrcoIntCedulaconductor();
            if (prcoIntCedulaconductor != null) {
                prcoIntCedulaconductor = em.getReference(prcoIntCedulaconductor.getClass(), prcoIntCedulaconductor.getConIntCedula());
                saePropietarioconductor.setPrcoIntCedulaconductor(prcoIntCedulaconductor);
            }
            em.persist(saePropietarioconductor);
            if (prcoIntCedulapropietario != null) {
                prcoIntCedulapropietario.getSaePropietarioconductorList().add(saePropietarioconductor);
                prcoIntCedulapropietario = em.merge(prcoIntCedulapropietario);
            }
            if (prcoIntCedulaconductor != null) {
                prcoIntCedulaconductor.getSaePropietarioconductorList().add(saePropietarioconductor);
                prcoIntCedulaconductor = em.merge(prcoIntCedulaconductor);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSaePropietarioconductor(saePropietarioconductor.getPrcoIntId()) != null) {
                throw new PreexistingEntityException("SaePropietarioconductor " + saePropietarioconductor + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SaePropietarioconductor saePropietarioconductor) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SaePropietarioconductor persistentSaePropietarioconductor = em.find(SaePropietarioconductor.class, saePropietarioconductor.getPrcoIntId());
            SaePropietariovehiculo prcoIntCedulapropietarioOld = persistentSaePropietarioconductor.getPrcoIntCedulapropietario();
            SaePropietariovehiculo prcoIntCedulapropietarioNew = saePropietarioconductor.getPrcoIntCedulapropietario();
            SaeConductor prcoIntCedulaconductorOld = persistentSaePropietarioconductor.getPrcoIntCedulaconductor();
            SaeConductor prcoIntCedulaconductorNew = saePropietarioconductor.getPrcoIntCedulaconductor();
            if (prcoIntCedulapropietarioNew != null) {
                prcoIntCedulapropietarioNew = em.getReference(prcoIntCedulapropietarioNew.getClass(), prcoIntCedulapropietarioNew.getProIntCedula());
                saePropietarioconductor.setPrcoIntCedulapropietario(prcoIntCedulapropietarioNew);
            }
            if (prcoIntCedulaconductorNew != null) {
                prcoIntCedulaconductorNew = em.getReference(prcoIntCedulaconductorNew.getClass(), prcoIntCedulaconductorNew.getConIntCedula());
                saePropietarioconductor.setPrcoIntCedulaconductor(prcoIntCedulaconductorNew);
            }
            saePropietarioconductor = em.merge(saePropietarioconductor);
            if (prcoIntCedulapropietarioOld != null && !prcoIntCedulapropietarioOld.equals(prcoIntCedulapropietarioNew)) {
                prcoIntCedulapropietarioOld.getSaePropietarioconductorList().remove(saePropietarioconductor);
                prcoIntCedulapropietarioOld = em.merge(prcoIntCedulapropietarioOld);
            }
            if (prcoIntCedulapropietarioNew != null && !prcoIntCedulapropietarioNew.equals(prcoIntCedulapropietarioOld)) {
                prcoIntCedulapropietarioNew.getSaePropietarioconductorList().add(saePropietarioconductor);
                prcoIntCedulapropietarioNew = em.merge(prcoIntCedulapropietarioNew);
            }
            if (prcoIntCedulaconductorOld != null && !prcoIntCedulaconductorOld.equals(prcoIntCedulaconductorNew)) {
                prcoIntCedulaconductorOld.getSaePropietarioconductorList().remove(saePropietarioconductor);
                prcoIntCedulaconductorOld = em.merge(prcoIntCedulaconductorOld);
            }
            if (prcoIntCedulaconductorNew != null && !prcoIntCedulaconductorNew.equals(prcoIntCedulaconductorOld)) {
                prcoIntCedulaconductorNew.getSaePropietarioconductorList().add(saePropietarioconductor);
                prcoIntCedulaconductorNew = em.merge(prcoIntCedulaconductorNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = saePropietarioconductor.getPrcoIntId();
                if (findSaePropietarioconductor(id) == null) {
                    throw new NonexistentEntityException("The saePropietarioconductor with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SaePropietarioconductor saePropietarioconductor;
            try {
                saePropietarioconductor = em.getReference(SaePropietarioconductor.class, id);
                saePropietarioconductor.getPrcoIntId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The saePropietarioconductor with id " + id + " no longer exists.", enfe);
            }
            SaePropietariovehiculo prcoIntCedulapropietario = saePropietarioconductor.getPrcoIntCedulapropietario();
            if (prcoIntCedulapropietario != null) {
                prcoIntCedulapropietario.getSaePropietarioconductorList().remove(saePropietarioconductor);
                prcoIntCedulapropietario = em.merge(prcoIntCedulapropietario);
            }
            SaeConductor prcoIntCedulaconductor = saePropietarioconductor.getPrcoIntCedulaconductor();
            if (prcoIntCedulaconductor != null) {
                prcoIntCedulaconductor.getSaePropietarioconductorList().remove(saePropietarioconductor);
                prcoIntCedulaconductor = em.merge(prcoIntCedulaconductor);
            }
            em.remove(saePropietarioconductor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SaePropietarioconductor> findSaePropietarioconductorEntities() {
        return findSaePropietarioconductorEntities(true, -1, -1);
    }

    public List<SaePropietarioconductor> findSaePropietarioconductorEntities(int maxResults, int firstResult) {
        return findSaePropietarioconductorEntities(false, maxResults, firstResult);
    }

    private List<SaePropietarioconductor> findSaePropietarioconductorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SaePropietarioconductor.class));
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

    public SaePropietarioconductor findSaePropietarioconductor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SaePropietarioconductor.class, id);
        } finally {
            em.close();
        }
    }

    public int getSaePropietarioconductorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SaePropietarioconductor> rt = cq.from(SaePropietarioconductor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
