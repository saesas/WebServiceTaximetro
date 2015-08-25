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
import com.aquitax.domain.SaeServicio;
import com.aquitax.domain.SaeUsuariovehiculo;
import com.aquitax.util.JpaUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class SaeUsuariovehiculoJpaController implements Serializable {

    public SaeUsuariovehiculoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SaeUsuariovehiculo saeUsuariovehiculo) throws PreexistingEntityException, Exception {
        if (saeUsuariovehiculo.getSaeServicioList() == null) {
            saeUsuariovehiculo.setSaeServicioList(new ArrayList<SaeServicio>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<SaeServicio> attachedSaeServicioList = new ArrayList<SaeServicio>();
            for (SaeServicio saeServicioListSaeServicioToAttach : saeUsuariovehiculo.getSaeServicioList()) {
                saeServicioListSaeServicioToAttach = em.getReference(saeServicioListSaeServicioToAttach.getClass(), saeServicioListSaeServicioToAttach.getSerIntId());
                attachedSaeServicioList.add(saeServicioListSaeServicioToAttach);
            }
            saeUsuariovehiculo.setSaeServicioList(attachedSaeServicioList);
            em.persist(saeUsuariovehiculo);
            for (SaeServicio saeServicioListSaeServicio : saeUsuariovehiculo.getSaeServicioList()) {
                SaeUsuariovehiculo oldSerIntTelefonoOfSaeServicioListSaeServicio = saeServicioListSaeServicio.getSerIntTelefono();
                saeServicioListSaeServicio.setSerIntTelefono(saeUsuariovehiculo);
                saeServicioListSaeServicio = em.merge(saeServicioListSaeServicio);
                if (oldSerIntTelefonoOfSaeServicioListSaeServicio != null) {
                    oldSerIntTelefonoOfSaeServicioListSaeServicio.getSaeServicioList().remove(saeServicioListSaeServicio);
                    oldSerIntTelefonoOfSaeServicioListSaeServicio = em.merge(oldSerIntTelefonoOfSaeServicioListSaeServicio);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSaeUsuariovehiculo(saeUsuariovehiculo.getUsuIntTelefono()) != null) {
                throw new PreexistingEntityException("SaeUsuariovehiculo " + saeUsuariovehiculo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    
    public void edit(SaeUsuariovehiculo saeUsuariovehiculo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SaeUsuariovehiculo persistentSaeUsuariovehiculo = em.find(SaeUsuariovehiculo.class, saeUsuariovehiculo.getUsuIntTelefono());
            List<SaeServicio> saeServicioListOld = persistentSaeUsuariovehiculo.getSaeServicioList();
            List<SaeServicio> saeServicioListNew = saeUsuariovehiculo.getSaeServicioList();
            List<SaeServicio> attachedSaeServicioListNew = new ArrayList<SaeServicio>();
            for (SaeServicio saeServicioListNewSaeServicioToAttach : saeServicioListNew) {
                saeServicioListNewSaeServicioToAttach = em.getReference(saeServicioListNewSaeServicioToAttach.getClass(), saeServicioListNewSaeServicioToAttach.getSerIntId());
                attachedSaeServicioListNew.add(saeServicioListNewSaeServicioToAttach);
            }
            saeServicioListNew = attachedSaeServicioListNew;
            saeUsuariovehiculo.setSaeServicioList(saeServicioListNew);
            saeUsuariovehiculo = em.merge(saeUsuariovehiculo);
            for (SaeServicio saeServicioListOldSaeServicio : saeServicioListOld) {
                if (!saeServicioListNew.contains(saeServicioListOldSaeServicio)) {
                    saeServicioListOldSaeServicio.setSerIntTelefono(null);
                    saeServicioListOldSaeServicio = em.merge(saeServicioListOldSaeServicio);
                }
            }
            for (SaeServicio saeServicioListNewSaeServicio : saeServicioListNew) {
                if (!saeServicioListOld.contains(saeServicioListNewSaeServicio)) {
                    SaeUsuariovehiculo oldSerIntTelefonoOfSaeServicioListNewSaeServicio = saeServicioListNewSaeServicio.getSerIntTelefono();
                    saeServicioListNewSaeServicio.setSerIntTelefono(saeUsuariovehiculo);
                    saeServicioListNewSaeServicio = em.merge(saeServicioListNewSaeServicio);
                    if (oldSerIntTelefonoOfSaeServicioListNewSaeServicio != null && !oldSerIntTelefonoOfSaeServicioListNewSaeServicio.equals(saeUsuariovehiculo)) {
                        oldSerIntTelefonoOfSaeServicioListNewSaeServicio.getSaeServicioList().remove(saeServicioListNewSaeServicio);
                        oldSerIntTelefonoOfSaeServicioListNewSaeServicio = em.merge(oldSerIntTelefonoOfSaeServicioListNewSaeServicio);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = saeUsuariovehiculo.getUsuIntTelefono();
                if (findSaeUsuariovehiculo(id) == null) {
                    throw new NonexistentEntityException("The saeUsuariovehiculo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    /*
    
     public void edit(SaeUsuariovehiculo saeUsuariovehiculo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            saeUsuariovehiculo= em.merge(saeUsuariovehiculo);
            em.getTransaction().commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("error.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    */

    public void destroy(BigDecimal id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SaeUsuariovehiculo saeUsuariovehiculo;
            try {
                saeUsuariovehiculo = em.getReference(SaeUsuariovehiculo.class, id);
                saeUsuariovehiculo.getUsuIntTelefono();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The saeUsuariovehiculo with id " + id + " no longer exists.", enfe);
            }
            List<SaeServicio> saeServicioList = saeUsuariovehiculo.getSaeServicioList();
            for (SaeServicio saeServicioListSaeServicio : saeServicioList) {
                saeServicioListSaeServicio.setSerIntTelefono(null);
                saeServicioListSaeServicio = em.merge(saeServicioListSaeServicio);
            }
            em.remove(saeUsuariovehiculo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SaeUsuariovehiculo> findSaeUsuariovehiculoEntities() {
        return findSaeUsuariovehiculoEntities(true, -1, -1);
    }

    public List<SaeUsuariovehiculo> findSaeUsuariovehiculoEntities(int maxResults, int firstResult) {
        return findSaeUsuariovehiculoEntities(false, maxResults, firstResult);
    }

    private List<SaeUsuariovehiculo> findSaeUsuariovehiculoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SaeUsuariovehiculo.class));
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

    public SaeUsuariovehiculo findSaeUsuariovehiculo(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            SaeUsuariovehiculo saeUsuarioVehiculo = em.find(SaeUsuariovehiculo.class, id);
            return saeUsuarioVehiculo;
        }catch(Exception e){
           return null;
        }
//        } finally {
//            em.close();
//        }
    }

    public int getSaeUsuariovehiculoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SaeUsuariovehiculo> rt = cq.from(SaeUsuariovehiculo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
