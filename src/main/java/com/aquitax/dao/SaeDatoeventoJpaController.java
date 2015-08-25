/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aquitax.dao;

import com.aquitax.dao.exceptions.NonexistentEntityException;
import com.aquitax.dao.exceptions.PreexistingEntityException;
import com.aquitax.domain.SaeDatoevento;
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
public class SaeDatoeventoJpaController implements Serializable {

    public SaeDatoeventoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SaeDatoevento saeDatoevento) throws PreexistingEntityException, Exception {
        if (saeDatoevento.getSaeHistorialvehiculoList() == null) {
            saeDatoevento.setSaeHistorialvehiculoList(new ArrayList<SaeHistorialvehiculo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<SaeHistorialvehiculo> attachedSaeHistorialvehiculoList = new ArrayList<SaeHistorialvehiculo>();
            for (SaeHistorialvehiculo saeHistorialvehiculoListSaeHistorialvehiculoToAttach : saeDatoevento.getSaeHistorialvehiculoList()) {
                saeHistorialvehiculoListSaeHistorialvehiculoToAttach = em.getReference(saeHistorialvehiculoListSaeHistorialvehiculoToAttach.getClass(), saeHistorialvehiculoListSaeHistorialvehiculoToAttach.getHisIntId());
                attachedSaeHistorialvehiculoList.add(saeHistorialvehiculoListSaeHistorialvehiculoToAttach);
            }
            saeDatoevento.setSaeHistorialvehiculoList(attachedSaeHistorialvehiculoList);
            em.persist(saeDatoevento);
            for (SaeHistorialvehiculo saeHistorialvehiculoListSaeHistorialvehiculo : saeDatoevento.getSaeHistorialvehiculoList()) {
                SaeDatoevento oldHisIntDatoeventoOfSaeHistorialvehiculoListSaeHistorialvehiculo = saeHistorialvehiculoListSaeHistorialvehiculo.getHisIntDatoevento();
                saeHistorialvehiculoListSaeHistorialvehiculo.setHisIntDatoevento(saeDatoevento);
                saeHistorialvehiculoListSaeHistorialvehiculo = em.merge(saeHistorialvehiculoListSaeHistorialvehiculo);
                if (oldHisIntDatoeventoOfSaeHistorialvehiculoListSaeHistorialvehiculo != null) {
                    oldHisIntDatoeventoOfSaeHistorialvehiculoListSaeHistorialvehiculo.getSaeHistorialvehiculoList().remove(saeHistorialvehiculoListSaeHistorialvehiculo);
                    oldHisIntDatoeventoOfSaeHistorialvehiculoListSaeHistorialvehiculo = em.merge(oldHisIntDatoeventoOfSaeHistorialvehiculoListSaeHistorialvehiculo);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSaeDatoevento(saeDatoevento.getDatIntId()) != null) {
                throw new PreexistingEntityException("SaeDatoevento " + saeDatoevento + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SaeDatoevento saeDatoevento) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SaeDatoevento persistentSaeDatoevento = em.find(SaeDatoevento.class, saeDatoevento.getDatIntId());
            List<SaeHistorialvehiculo> saeHistorialvehiculoListOld = persistentSaeDatoevento.getSaeHistorialvehiculoList();
            List<SaeHistorialvehiculo> saeHistorialvehiculoListNew = saeDatoevento.getSaeHistorialvehiculoList();
            List<SaeHistorialvehiculo> attachedSaeHistorialvehiculoListNew = new ArrayList<SaeHistorialvehiculo>();
            for (SaeHistorialvehiculo saeHistorialvehiculoListNewSaeHistorialvehiculoToAttach : saeHistorialvehiculoListNew) {
                saeHistorialvehiculoListNewSaeHistorialvehiculoToAttach = em.getReference(saeHistorialvehiculoListNewSaeHistorialvehiculoToAttach.getClass(), saeHistorialvehiculoListNewSaeHistorialvehiculoToAttach.getHisIntId());
                attachedSaeHistorialvehiculoListNew.add(saeHistorialvehiculoListNewSaeHistorialvehiculoToAttach);
            }
            saeHistorialvehiculoListNew = attachedSaeHistorialvehiculoListNew;
            saeDatoevento.setSaeHistorialvehiculoList(saeHistorialvehiculoListNew);
            saeDatoevento = em.merge(saeDatoevento);
            for (SaeHistorialvehiculo saeHistorialvehiculoListOldSaeHistorialvehiculo : saeHistorialvehiculoListOld) {
                if (!saeHistorialvehiculoListNew.contains(saeHistorialvehiculoListOldSaeHistorialvehiculo)) {
                    saeHistorialvehiculoListOldSaeHistorialvehiculo.setHisIntDatoevento(null);
                    saeHistorialvehiculoListOldSaeHistorialvehiculo = em.merge(saeHistorialvehiculoListOldSaeHistorialvehiculo);
                }
            }
            for (SaeHistorialvehiculo saeHistorialvehiculoListNewSaeHistorialvehiculo : saeHistorialvehiculoListNew) {
                if (!saeHistorialvehiculoListOld.contains(saeHistorialvehiculoListNewSaeHistorialvehiculo)) {
                    SaeDatoevento oldHisIntDatoeventoOfSaeHistorialvehiculoListNewSaeHistorialvehiculo = saeHistorialvehiculoListNewSaeHistorialvehiculo.getHisIntDatoevento();
                    saeHistorialvehiculoListNewSaeHistorialvehiculo.setHisIntDatoevento(saeDatoevento);
                    saeHistorialvehiculoListNewSaeHistorialvehiculo = em.merge(saeHistorialvehiculoListNewSaeHistorialvehiculo);
                    if (oldHisIntDatoeventoOfSaeHistorialvehiculoListNewSaeHistorialvehiculo != null && !oldHisIntDatoeventoOfSaeHistorialvehiculoListNewSaeHistorialvehiculo.equals(saeDatoevento)) {
                        oldHisIntDatoeventoOfSaeHistorialvehiculoListNewSaeHistorialvehiculo.getSaeHistorialvehiculoList().remove(saeHistorialvehiculoListNewSaeHistorialvehiculo);
                        oldHisIntDatoeventoOfSaeHistorialvehiculoListNewSaeHistorialvehiculo = em.merge(oldHisIntDatoeventoOfSaeHistorialvehiculoListNewSaeHistorialvehiculo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = saeDatoevento.getDatIntId();
                if (findSaeDatoevento(id) == null) {
                    throw new NonexistentEntityException("The saeDatoevento with id " + id + " no longer exists.");
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
            SaeDatoevento saeDatoevento;
            try {
                saeDatoevento = em.getReference(SaeDatoevento.class, id);
                saeDatoevento.getDatIntId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The saeDatoevento with id " + id + " no longer exists.", enfe);
            }
            List<SaeHistorialvehiculo> saeHistorialvehiculoList = saeDatoevento.getSaeHistorialvehiculoList();
            for (SaeHistorialvehiculo saeHistorialvehiculoListSaeHistorialvehiculo : saeHistorialvehiculoList) {
                saeHistorialvehiculoListSaeHistorialvehiculo.setHisIntDatoevento(null);
                saeHistorialvehiculoListSaeHistorialvehiculo = em.merge(saeHistorialvehiculoListSaeHistorialvehiculo);
            }
            em.remove(saeDatoevento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SaeDatoevento> findSaeDatoeventoEntities() {
        return findSaeDatoeventoEntities(true, -1, -1);
    }

    public List<SaeDatoevento> findSaeDatoeventoEntities(int maxResults, int firstResult) {
        return findSaeDatoeventoEntities(false, maxResults, firstResult);
    }

    private List<SaeDatoevento> findSaeDatoeventoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SaeDatoevento.class));
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

    public SaeDatoevento findSaeDatoevento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SaeDatoevento.class, id);
        } finally {
            em.close();
        }
    }

    public int getSaeDatoeventoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SaeDatoevento> rt = cq.from(SaeDatoevento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
