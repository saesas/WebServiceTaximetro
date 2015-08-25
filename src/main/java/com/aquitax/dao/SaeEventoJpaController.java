/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aquitax.dao;

import com.aquitax.dao.exceptions.NonexistentEntityException;
import com.aquitax.dao.exceptions.PreexistingEntityException;
import com.aquitax.domain.SaeEvento;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.aquitax.domain.SaeHistorialvehiculo;
import com.aquitax.util.JpaUtil;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class SaeEventoJpaController implements Serializable {

    public SaeEventoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SaeEvento saeEvento) throws PreexistingEntityException, Exception {
        if (saeEvento.getSaeHistorialvehiculoList() == null) {
            saeEvento.setSaeHistorialvehiculoList(new ArrayList<SaeHistorialvehiculo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<SaeHistorialvehiculo> attachedSaeHistorialvehiculoList = new ArrayList<SaeHistorialvehiculo>();
            for (SaeHistorialvehiculo saeHistorialvehiculoListSaeHistorialvehiculoToAttach : saeEvento.getSaeHistorialvehiculoList()) {
                saeHistorialvehiculoListSaeHistorialvehiculoToAttach = em.getReference(saeHistorialvehiculoListSaeHistorialvehiculoToAttach.getClass(), saeHistorialvehiculoListSaeHistorialvehiculoToAttach.getHisIntId());
                attachedSaeHistorialvehiculoList.add(saeHistorialvehiculoListSaeHistorialvehiculoToAttach);
            }
            saeEvento.setSaeHistorialvehiculoList(attachedSaeHistorialvehiculoList);
            em.persist(saeEvento);
            for (SaeHistorialvehiculo saeHistorialvehiculoListSaeHistorialvehiculo : saeEvento.getSaeHistorialvehiculoList()) {
                SaeEvento oldHisIntEventoOfSaeHistorialvehiculoListSaeHistorialvehiculo = saeHistorialvehiculoListSaeHistorialvehiculo.getHisIntEvento();
                saeHistorialvehiculoListSaeHistorialvehiculo.setHisIntEvento(saeEvento);
                saeHistorialvehiculoListSaeHistorialvehiculo = em.merge(saeHistorialvehiculoListSaeHistorialvehiculo);
                if (oldHisIntEventoOfSaeHistorialvehiculoListSaeHistorialvehiculo != null) {
                    oldHisIntEventoOfSaeHistorialvehiculoListSaeHistorialvehiculo.getSaeHistorialvehiculoList().remove(saeHistorialvehiculoListSaeHistorialvehiculo);
                    oldHisIntEventoOfSaeHistorialvehiculoListSaeHistorialvehiculo = em.merge(oldHisIntEventoOfSaeHistorialvehiculoListSaeHistorialvehiculo);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSaeEvento(saeEvento.getEveIntId()) != null) {
                throw new PreexistingEntityException("SaeEvento " + saeEvento + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SaeEvento saeEvento) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SaeEvento persistentSaeEvento = em.find(SaeEvento.class, saeEvento.getEveIntId());
            List<SaeHistorialvehiculo> saeHistorialvehiculoListOld = persistentSaeEvento.getSaeHistorialvehiculoList();
            List<SaeHistorialvehiculo> saeHistorialvehiculoListNew = saeEvento.getSaeHistorialvehiculoList();
            List<SaeHistorialvehiculo> attachedSaeHistorialvehiculoListNew = new ArrayList<SaeHistorialvehiculo>();
            for (SaeHistorialvehiculo saeHistorialvehiculoListNewSaeHistorialvehiculoToAttach : saeHistorialvehiculoListNew) {
                saeHistorialvehiculoListNewSaeHistorialvehiculoToAttach = em.getReference(saeHistorialvehiculoListNewSaeHistorialvehiculoToAttach.getClass(), saeHistorialvehiculoListNewSaeHistorialvehiculoToAttach.getHisIntId());
                attachedSaeHistorialvehiculoListNew.add(saeHistorialvehiculoListNewSaeHistorialvehiculoToAttach);
            }
            saeHistorialvehiculoListNew = attachedSaeHistorialvehiculoListNew;
            saeEvento.setSaeHistorialvehiculoList(saeHistorialvehiculoListNew);
            saeEvento = em.merge(saeEvento);
            for (SaeHistorialvehiculo saeHistorialvehiculoListOldSaeHistorialvehiculo : saeHistorialvehiculoListOld) {
                if (!saeHistorialvehiculoListNew.contains(saeHistorialvehiculoListOldSaeHistorialvehiculo)) {
                    saeHistorialvehiculoListOldSaeHistorialvehiculo.setHisIntEvento(null);
                    saeHistorialvehiculoListOldSaeHistorialvehiculo = em.merge(saeHistorialvehiculoListOldSaeHistorialvehiculo);
                }
            }
            for (SaeHistorialvehiculo saeHistorialvehiculoListNewSaeHistorialvehiculo : saeHistorialvehiculoListNew) {
                if (!saeHistorialvehiculoListOld.contains(saeHistorialvehiculoListNewSaeHistorialvehiculo)) {
                    SaeEvento oldHisIntEventoOfSaeHistorialvehiculoListNewSaeHistorialvehiculo = saeHistorialvehiculoListNewSaeHistorialvehiculo.getHisIntEvento();
                    saeHistorialvehiculoListNewSaeHistorialvehiculo.setHisIntEvento(saeEvento);
                    saeHistorialvehiculoListNewSaeHistorialvehiculo = em.merge(saeHistorialvehiculoListNewSaeHistorialvehiculo);
                    if (oldHisIntEventoOfSaeHistorialvehiculoListNewSaeHistorialvehiculo != null && !oldHisIntEventoOfSaeHistorialvehiculoListNewSaeHistorialvehiculo.equals(saeEvento)) {
                        oldHisIntEventoOfSaeHistorialvehiculoListNewSaeHistorialvehiculo.getSaeHistorialvehiculoList().remove(saeHistorialvehiculoListNewSaeHistorialvehiculo);
                        oldHisIntEventoOfSaeHistorialvehiculoListNewSaeHistorialvehiculo = em.merge(oldHisIntEventoOfSaeHistorialvehiculoListNewSaeHistorialvehiculo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = saeEvento.getEveIntId();
                if (findSaeEvento(id) == null) {
                    throw new NonexistentEntityException("The saeEvento with id " + id + " no longer exists.");
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
            SaeEvento saeEvento;
            try {
                saeEvento = em.getReference(SaeEvento.class, id);
                saeEvento.getEveIntId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The saeEvento with id " + id + " no longer exists.", enfe);
            }
            List<SaeHistorialvehiculo> saeHistorialvehiculoList = saeEvento.getSaeHistorialvehiculoList();
            for (SaeHistorialvehiculo saeHistorialvehiculoListSaeHistorialvehiculo : saeHistorialvehiculoList) {
                saeHistorialvehiculoListSaeHistorialvehiculo.setHisIntEvento(null);
                saeHistorialvehiculoListSaeHistorialvehiculo = em.merge(saeHistorialvehiculoListSaeHistorialvehiculo);
            }
            em.remove(saeEvento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SaeEvento> findSaeEventoEntities() {
        return findSaeEventoEntities(true, -1, -1);
    }

    public List<SaeEvento> findSaeEventoEntities(int maxResults, int firstResult) {
        return findSaeEventoEntities(false, maxResults, firstResult);
    }

    private List<SaeEvento> findSaeEventoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SaeEvento.class));
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

    public SaeEvento findSaeEvento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SaeEvento.class, id);
        } finally {
            em.close();
        }
    }

    public int getSaeEventoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SaeEvento> rt = cq.from(SaeEvento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
