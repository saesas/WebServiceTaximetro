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
import com.aquitax.domain.SaeVehiculo;
import com.aquitax.domain.SaeConductor;
import com.aquitax.domain.SaeTurnoconductor;
import com.aquitax.util.JpaUtil;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class SaeTurnoconductorJpaController implements Serializable {

    public SaeTurnoconductorJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SaeTurnoconductor saeTurnoconductor) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SaeVehiculo turStrVehiculo = saeTurnoconductor.getTurStrVehiculo();
            if (turStrVehiculo != null) {
                turStrVehiculo = em.getReference(turStrVehiculo.getClass(), turStrVehiculo.getVehStrPlaca());
                saeTurnoconductor.setTurStrVehiculo(turStrVehiculo);
            }
            SaeConductor turIntConductor = saeTurnoconductor.getTurIntConductor();
            if (turIntConductor != null) {
                turIntConductor = em.getReference(turIntConductor.getClass(), turIntConductor.getConIntCedula());
                saeTurnoconductor.setTurIntConductor(turIntConductor);
            }
            em.persist(saeTurnoconductor);
            if (turStrVehiculo != null) {
                turStrVehiculo.getSaeTurnoconductorList().add(saeTurnoconductor);
                turStrVehiculo = em.merge(turStrVehiculo);
            }
            if (turIntConductor != null) {
                turIntConductor.getSaeTurnoconductorList().add(saeTurnoconductor);
                turIntConductor = em.merge(turIntConductor);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSaeTurnoconductor(saeTurnoconductor.getTurIntId()) != null) {
                throw new PreexistingEntityException("SaeTurnoconductor " + saeTurnoconductor + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SaeTurnoconductor saeTurnoconductor) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SaeTurnoconductor persistentSaeTurnoconductor = em.find(SaeTurnoconductor.class, saeTurnoconductor.getTurIntId());
            SaeVehiculo turStrVehiculoOld = persistentSaeTurnoconductor.getTurStrVehiculo();
            SaeVehiculo turStrVehiculoNew = saeTurnoconductor.getTurStrVehiculo();
            SaeConductor turIntConductorOld = persistentSaeTurnoconductor.getTurIntConductor();
            SaeConductor turIntConductorNew = saeTurnoconductor.getTurIntConductor();
            if (turStrVehiculoNew != null) {
                turStrVehiculoNew = em.getReference(turStrVehiculoNew.getClass(), turStrVehiculoNew.getVehStrPlaca());
                saeTurnoconductor.setTurStrVehiculo(turStrVehiculoNew);
            }
            if (turIntConductorNew != null) {
                turIntConductorNew = em.getReference(turIntConductorNew.getClass(), turIntConductorNew.getConIntCedula());
                saeTurnoconductor.setTurIntConductor(turIntConductorNew);
            }
            saeTurnoconductor = em.merge(saeTurnoconductor);
            if (turStrVehiculoOld != null && !turStrVehiculoOld.equals(turStrVehiculoNew)) {
                turStrVehiculoOld.getSaeTurnoconductorList().remove(saeTurnoconductor);
                turStrVehiculoOld = em.merge(turStrVehiculoOld);
            }
            if (turStrVehiculoNew != null && !turStrVehiculoNew.equals(turStrVehiculoOld)) {
                turStrVehiculoNew.getSaeTurnoconductorList().add(saeTurnoconductor);
                turStrVehiculoNew = em.merge(turStrVehiculoNew);
            }
            if (turIntConductorOld != null && !turIntConductorOld.equals(turIntConductorNew)) {
                turIntConductorOld.getSaeTurnoconductorList().remove(saeTurnoconductor);
                turIntConductorOld = em.merge(turIntConductorOld);
            }
            if (turIntConductorNew != null && !turIntConductorNew.equals(turIntConductorOld)) {
                turIntConductorNew.getSaeTurnoconductorList().add(saeTurnoconductor);
                turIntConductorNew = em.merge(turIntConductorNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = saeTurnoconductor.getTurIntId();
                if (findSaeTurnoconductor(id) == null) {
                    throw new NonexistentEntityException("The saeTurnoconductor with id " + id + " no longer exists.");
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
            SaeTurnoconductor saeTurnoconductor;
            try {
                saeTurnoconductor = em.getReference(SaeTurnoconductor.class, id);
                saeTurnoconductor.getTurIntId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The saeTurnoconductor with id " + id + " no longer exists.", enfe);
            }
            SaeVehiculo turStrVehiculo = saeTurnoconductor.getTurStrVehiculo();
            if (turStrVehiculo != null) {
                turStrVehiculo.getSaeTurnoconductorList().remove(saeTurnoconductor);
                turStrVehiculo = em.merge(turStrVehiculo);
            }
            SaeConductor turIntConductor = saeTurnoconductor.getTurIntConductor();
            if (turIntConductor != null) {
                turIntConductor.getSaeTurnoconductorList().remove(saeTurnoconductor);
                turIntConductor = em.merge(turIntConductor);
            }
            em.remove(saeTurnoconductor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SaeTurnoconductor> findSaeTurnoconductorEntities() {
        return findSaeTurnoconductorEntities(true, -1, -1);
    }

    public List<SaeTurnoconductor> findSaeTurnoconductorEntities(int maxResults, int firstResult) {
        return findSaeTurnoconductorEntities(false, maxResults, firstResult);
    }

    private List<SaeTurnoconductor> findSaeTurnoconductorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SaeTurnoconductor.class));
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

    public SaeTurnoconductor findSaeTurnoconductor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SaeTurnoconductor.class, id);
        } finally {
            em.close();
        }
    }

    public int getSaeTurnoconductorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SaeTurnoconductor> rt = cq.from(SaeTurnoconductor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
