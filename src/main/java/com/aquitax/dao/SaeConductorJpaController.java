/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aquitax.dao;

import com.aquitax.dao.exceptions.NonexistentEntityException;
import com.aquitax.dao.exceptions.PreexistingEntityException;
import com.aquitax.domain.SaeConductor;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.aquitax.domain.SaeServicio;
import java.util.ArrayList;
import java.util.List;
import com.aquitax.domain.SaePropietarioconductor;
import com.aquitax.domain.SaeTurnoconductor;
import com.aquitax.util.JpaUtil;
import java.math.BigDecimal;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class SaeConductorJpaController implements Serializable {

    public SaeConductorJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SaeConductor saeConductor) throws PreexistingEntityException, Exception {
        if (saeConductor.getSaeServicioList() == null) {
            saeConductor.setSaeServicioList(new ArrayList<SaeServicio>());
        }
        if (saeConductor.getSaePropietarioconductorList() == null) {
            saeConductor.setSaePropietarioconductorList(new ArrayList<SaePropietarioconductor>());
        }
        if (saeConductor.getSaeTurnoconductorList() == null) {
            saeConductor.setSaeTurnoconductorList(new ArrayList<SaeTurnoconductor>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<SaeServicio> attachedSaeServicioList = new ArrayList<SaeServicio>();
            for (SaeServicio saeServicioListSaeServicioToAttach : saeConductor.getSaeServicioList()) {
                saeServicioListSaeServicioToAttach = em.getReference(saeServicioListSaeServicioToAttach.getClass(), saeServicioListSaeServicioToAttach.getSerIntId());
                attachedSaeServicioList.add(saeServicioListSaeServicioToAttach);
            }
            saeConductor.setSaeServicioList(attachedSaeServicioList);
            List<SaePropietarioconductor> attachedSaePropietarioconductorList = new ArrayList<SaePropietarioconductor>();
            for (SaePropietarioconductor saePropietarioconductorListSaePropietarioconductorToAttach : saeConductor.getSaePropietarioconductorList()) {
                saePropietarioconductorListSaePropietarioconductorToAttach = em.getReference(saePropietarioconductorListSaePropietarioconductorToAttach.getClass(), saePropietarioconductorListSaePropietarioconductorToAttach.getPrcoIntId());
                attachedSaePropietarioconductorList.add(saePropietarioconductorListSaePropietarioconductorToAttach);
            }
            saeConductor.setSaePropietarioconductorList(attachedSaePropietarioconductorList);
            List<SaeTurnoconductor> attachedSaeTurnoconductorList = new ArrayList<SaeTurnoconductor>();
            for (SaeTurnoconductor saeTurnoconductorListSaeTurnoconductorToAttach : saeConductor.getSaeTurnoconductorList()) {
                saeTurnoconductorListSaeTurnoconductorToAttach = em.getReference(saeTurnoconductorListSaeTurnoconductorToAttach.getClass(), saeTurnoconductorListSaeTurnoconductorToAttach.getTurIntId());
                attachedSaeTurnoconductorList.add(saeTurnoconductorListSaeTurnoconductorToAttach);
            }
            saeConductor.setSaeTurnoconductorList(attachedSaeTurnoconductorList);
            em.persist(saeConductor);
            for (SaeServicio saeServicioListSaeServicio : saeConductor.getSaeServicioList()) {
                SaeConductor oldSerIntConductorOfSaeServicioListSaeServicio = saeServicioListSaeServicio.getSerIntConductor();
                saeServicioListSaeServicio.setSerIntConductor(saeConductor);
                saeServicioListSaeServicio = em.merge(saeServicioListSaeServicio);
                if (oldSerIntConductorOfSaeServicioListSaeServicio != null) {
                    oldSerIntConductorOfSaeServicioListSaeServicio.getSaeServicioList().remove(saeServicioListSaeServicio);
                    oldSerIntConductorOfSaeServicioListSaeServicio = em.merge(oldSerIntConductorOfSaeServicioListSaeServicio);
                }
            }
            for (SaePropietarioconductor saePropietarioconductorListSaePropietarioconductor : saeConductor.getSaePropietarioconductorList()) {
                SaeConductor oldPrcoIntCedulaconductorOfSaePropietarioconductorListSaePropietarioconductor = saePropietarioconductorListSaePropietarioconductor.getPrcoIntCedulaconductor();
                saePropietarioconductorListSaePropietarioconductor.setPrcoIntCedulaconductor(saeConductor);
                saePropietarioconductorListSaePropietarioconductor = em.merge(saePropietarioconductorListSaePropietarioconductor);
                if (oldPrcoIntCedulaconductorOfSaePropietarioconductorListSaePropietarioconductor != null) {
                    oldPrcoIntCedulaconductorOfSaePropietarioconductorListSaePropietarioconductor.getSaePropietarioconductorList().remove(saePropietarioconductorListSaePropietarioconductor);
                    oldPrcoIntCedulaconductorOfSaePropietarioconductorListSaePropietarioconductor = em.merge(oldPrcoIntCedulaconductorOfSaePropietarioconductorListSaePropietarioconductor);
                }
            }
            for (SaeTurnoconductor saeTurnoconductorListSaeTurnoconductor : saeConductor.getSaeTurnoconductorList()) {
                SaeConductor oldTurIntConductorOfSaeTurnoconductorListSaeTurnoconductor = saeTurnoconductorListSaeTurnoconductor.getTurIntConductor();
                saeTurnoconductorListSaeTurnoconductor.setTurIntConductor(saeConductor);
                saeTurnoconductorListSaeTurnoconductor = em.merge(saeTurnoconductorListSaeTurnoconductor);
                if (oldTurIntConductorOfSaeTurnoconductorListSaeTurnoconductor != null) {
                    oldTurIntConductorOfSaeTurnoconductorListSaeTurnoconductor.getSaeTurnoconductorList().remove(saeTurnoconductorListSaeTurnoconductor);
                    oldTurIntConductorOfSaeTurnoconductorListSaeTurnoconductor = em.merge(oldTurIntConductorOfSaeTurnoconductorListSaeTurnoconductor);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSaeConductor(saeConductor.getConIntCedula()) != null) {
                throw new PreexistingEntityException("SaeConductor " + saeConductor + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SaeConductor saeConductor) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SaeConductor persistentSaeConductor = em.find(SaeConductor.class, saeConductor.getConIntCedula());
            List<SaeServicio> saeServicioListOld = persistentSaeConductor.getSaeServicioList();
            List<SaeServicio> saeServicioListNew = saeConductor.getSaeServicioList();
            List<SaePropietarioconductor> saePropietarioconductorListOld = persistentSaeConductor.getSaePropietarioconductorList();
            List<SaePropietarioconductor> saePropietarioconductorListNew = saeConductor.getSaePropietarioconductorList();
            List<SaeTurnoconductor> saeTurnoconductorListOld = persistentSaeConductor.getSaeTurnoconductorList();
            List<SaeTurnoconductor> saeTurnoconductorListNew = saeConductor.getSaeTurnoconductorList();
            List<SaeServicio> attachedSaeServicioListNew = new ArrayList<SaeServicio>();
            for (SaeServicio saeServicioListNewSaeServicioToAttach : saeServicioListNew) {
                saeServicioListNewSaeServicioToAttach = em.getReference(saeServicioListNewSaeServicioToAttach.getClass(), saeServicioListNewSaeServicioToAttach.getSerIntId());
                attachedSaeServicioListNew.add(saeServicioListNewSaeServicioToAttach);
            }
            saeServicioListNew = attachedSaeServicioListNew;
            saeConductor.setSaeServicioList(saeServicioListNew);
            List<SaePropietarioconductor> attachedSaePropietarioconductorListNew = new ArrayList<SaePropietarioconductor>();
            for (SaePropietarioconductor saePropietarioconductorListNewSaePropietarioconductorToAttach : saePropietarioconductorListNew) {
                saePropietarioconductorListNewSaePropietarioconductorToAttach = em.getReference(saePropietarioconductorListNewSaePropietarioconductorToAttach.getClass(), saePropietarioconductorListNewSaePropietarioconductorToAttach.getPrcoIntId());
                attachedSaePropietarioconductorListNew.add(saePropietarioconductorListNewSaePropietarioconductorToAttach);
            }
            saePropietarioconductorListNew = attachedSaePropietarioconductorListNew;
            saeConductor.setSaePropietarioconductorList(saePropietarioconductorListNew);
            List<SaeTurnoconductor> attachedSaeTurnoconductorListNew = new ArrayList<SaeTurnoconductor>();
            for (SaeTurnoconductor saeTurnoconductorListNewSaeTurnoconductorToAttach : saeTurnoconductorListNew) {
                saeTurnoconductorListNewSaeTurnoconductorToAttach = em.getReference(saeTurnoconductorListNewSaeTurnoconductorToAttach.getClass(), saeTurnoconductorListNewSaeTurnoconductorToAttach.getTurIntId());
                attachedSaeTurnoconductorListNew.add(saeTurnoconductorListNewSaeTurnoconductorToAttach);
            }
            saeTurnoconductorListNew = attachedSaeTurnoconductorListNew;
            saeConductor.setSaeTurnoconductorList(saeTurnoconductorListNew);
            saeConductor = em.merge(saeConductor);
            for (SaeServicio saeServicioListOldSaeServicio : saeServicioListOld) {
                if (!saeServicioListNew.contains(saeServicioListOldSaeServicio)) {
                    saeServicioListOldSaeServicio.setSerIntConductor(null);
                    saeServicioListOldSaeServicio = em.merge(saeServicioListOldSaeServicio);
                }
            }
            for (SaeServicio saeServicioListNewSaeServicio : saeServicioListNew) {
                if (!saeServicioListOld.contains(saeServicioListNewSaeServicio)) {
                    SaeConductor oldSerIntConductorOfSaeServicioListNewSaeServicio = saeServicioListNewSaeServicio.getSerIntConductor();
                    saeServicioListNewSaeServicio.setSerIntConductor(saeConductor);
                    saeServicioListNewSaeServicio = em.merge(saeServicioListNewSaeServicio);
                    if (oldSerIntConductorOfSaeServicioListNewSaeServicio != null && !oldSerIntConductorOfSaeServicioListNewSaeServicio.equals(saeConductor)) {
                        oldSerIntConductorOfSaeServicioListNewSaeServicio.getSaeServicioList().remove(saeServicioListNewSaeServicio);
                        oldSerIntConductorOfSaeServicioListNewSaeServicio = em.merge(oldSerIntConductorOfSaeServicioListNewSaeServicio);
                    }
                }
            }
            for (SaePropietarioconductor saePropietarioconductorListOldSaePropietarioconductor : saePropietarioconductorListOld) {
                if (!saePropietarioconductorListNew.contains(saePropietarioconductorListOldSaePropietarioconductor)) {
                    saePropietarioconductorListOldSaePropietarioconductor.setPrcoIntCedulaconductor(null);
                    saePropietarioconductorListOldSaePropietarioconductor = em.merge(saePropietarioconductorListOldSaePropietarioconductor);
                }
            }
            for (SaePropietarioconductor saePropietarioconductorListNewSaePropietarioconductor : saePropietarioconductorListNew) {
                if (!saePropietarioconductorListOld.contains(saePropietarioconductorListNewSaePropietarioconductor)) {
                    SaeConductor oldPrcoIntCedulaconductorOfSaePropietarioconductorListNewSaePropietarioconductor = saePropietarioconductorListNewSaePropietarioconductor.getPrcoIntCedulaconductor();
                    saePropietarioconductorListNewSaePropietarioconductor.setPrcoIntCedulaconductor(saeConductor);
                    saePropietarioconductorListNewSaePropietarioconductor = em.merge(saePropietarioconductorListNewSaePropietarioconductor);
                    if (oldPrcoIntCedulaconductorOfSaePropietarioconductorListNewSaePropietarioconductor != null && !oldPrcoIntCedulaconductorOfSaePropietarioconductorListNewSaePropietarioconductor.equals(saeConductor)) {
                        oldPrcoIntCedulaconductorOfSaePropietarioconductorListNewSaePropietarioconductor.getSaePropietarioconductorList().remove(saePropietarioconductorListNewSaePropietarioconductor);
                        oldPrcoIntCedulaconductorOfSaePropietarioconductorListNewSaePropietarioconductor = em.merge(oldPrcoIntCedulaconductorOfSaePropietarioconductorListNewSaePropietarioconductor);
                    }
                }
            }
            for (SaeTurnoconductor saeTurnoconductorListOldSaeTurnoconductor : saeTurnoconductorListOld) {
                if (!saeTurnoconductorListNew.contains(saeTurnoconductorListOldSaeTurnoconductor)) {
                    saeTurnoconductorListOldSaeTurnoconductor.setTurIntConductor(null);
                    saeTurnoconductorListOldSaeTurnoconductor = em.merge(saeTurnoconductorListOldSaeTurnoconductor);
                }
            }
            for (SaeTurnoconductor saeTurnoconductorListNewSaeTurnoconductor : saeTurnoconductorListNew) {
                if (!saeTurnoconductorListOld.contains(saeTurnoconductorListNewSaeTurnoconductor)) {
                    SaeConductor oldTurIntConductorOfSaeTurnoconductorListNewSaeTurnoconductor = saeTurnoconductorListNewSaeTurnoconductor.getTurIntConductor();
                    saeTurnoconductorListNewSaeTurnoconductor.setTurIntConductor(saeConductor);
                    saeTurnoconductorListNewSaeTurnoconductor = em.merge(saeTurnoconductorListNewSaeTurnoconductor);
                    if (oldTurIntConductorOfSaeTurnoconductorListNewSaeTurnoconductor != null && !oldTurIntConductorOfSaeTurnoconductorListNewSaeTurnoconductor.equals(saeConductor)) {
                        oldTurIntConductorOfSaeTurnoconductorListNewSaeTurnoconductor.getSaeTurnoconductorList().remove(saeTurnoconductorListNewSaeTurnoconductor);
                        oldTurIntConductorOfSaeTurnoconductorListNewSaeTurnoconductor = em.merge(oldTurIntConductorOfSaeTurnoconductorListNewSaeTurnoconductor);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = saeConductor.getConIntCedula();
                if (findSaeConductor(id) == null) {
                    throw new NonexistentEntityException("The saeConductor with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(BigDecimal id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SaeConductor saeConductor;
            try {
                saeConductor = em.getReference(SaeConductor.class, id);
                saeConductor.getConIntCedula();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The saeConductor with id " + id + " no longer exists.", enfe);
            }
            List<SaeServicio> saeServicioList = saeConductor.getSaeServicioList();
            for (SaeServicio saeServicioListSaeServicio : saeServicioList) {
                saeServicioListSaeServicio.setSerIntConductor(null);
                saeServicioListSaeServicio = em.merge(saeServicioListSaeServicio);
            }
            List<SaePropietarioconductor> saePropietarioconductorList = saeConductor.getSaePropietarioconductorList();
            for (SaePropietarioconductor saePropietarioconductorListSaePropietarioconductor : saePropietarioconductorList) {
                saePropietarioconductorListSaePropietarioconductor.setPrcoIntCedulaconductor(null);
                saePropietarioconductorListSaePropietarioconductor = em.merge(saePropietarioconductorListSaePropietarioconductor);
            }
            List<SaeTurnoconductor> saeTurnoconductorList = saeConductor.getSaeTurnoconductorList();
            for (SaeTurnoconductor saeTurnoconductorListSaeTurnoconductor : saeTurnoconductorList) {
                saeTurnoconductorListSaeTurnoconductor.setTurIntConductor(null);
                saeTurnoconductorListSaeTurnoconductor = em.merge(saeTurnoconductorListSaeTurnoconductor);
            }
            em.remove(saeConductor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SaeConductor> findSaeConductorEntities() {
        return findSaeConductorEntities(true, -1, -1);
    }

    public List<SaeConductor> findSaeConductorEntities(int maxResults, int firstResult) {
        return findSaeConductorEntities(false, maxResults, firstResult);
    }

    private List<SaeConductor> findSaeConductorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SaeConductor.class));
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

    public SaeConductor findSaeConductor(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SaeConductor.class, id);
        } finally {
            em.close();
        }
    }

    public int getSaeConductorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SaeConductor> rt = cq.from(SaeConductor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
