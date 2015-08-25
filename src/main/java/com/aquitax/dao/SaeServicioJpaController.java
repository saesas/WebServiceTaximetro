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
import com.aquitax.domain.SaeUsuariovehiculo;
import com.aquitax.domain.SaeOperador;
import com.aquitax.domain.SaeConductor;
import com.aquitax.domain.SaeServicio;
import com.aquitax.util.JpaUtil;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class SaeServicioJpaController implements Serializable {

    public SaeServicioJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SaeServicio saeServicio) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SaeVehiculo serStrPlaca = saeServicio.getSerStrPlaca();
            if (serStrPlaca != null) {
                serStrPlaca = em.getReference(serStrPlaca.getClass(), serStrPlaca.getVehStrPlaca());
                saeServicio.setSerStrPlaca(serStrPlaca);
            }
            SaeUsuariovehiculo serIntTelefono = saeServicio.getSerIntTelefono();
            if (serIntTelefono != null) {
                serIntTelefono = em.getReference(serIntTelefono.getClass(), serIntTelefono.getUsuIntTelefono());
                saeServicio.setSerIntTelefono(serIntTelefono);
            }
            SaeOperador serIntOperadora = saeServicio.getSerIntOperadora();
            if (serIntOperadora != null) {
                serIntOperadora = em.getReference(serIntOperadora.getClass(), serIntOperadora.getOpeIntId());
                saeServicio.setSerIntOperadora(serIntOperadora);
            }
            SaeConductor serIntConductor = saeServicio.getSerIntConductor();
            if (serIntConductor != null) {
                serIntConductor = em.getReference(serIntConductor.getClass(), serIntConductor.getConIntCedula());
                saeServicio.setSerIntConductor(serIntConductor);
            }
            em.persist(saeServicio);
            if (serStrPlaca != null) {
                serStrPlaca.getSaeServicioList().add(saeServicio);
                serStrPlaca = em.merge(serStrPlaca);
            }
            if (serIntTelefono != null) {
                serIntTelefono.getSaeServicioList().add(saeServicio);
                serIntTelefono = em.merge(serIntTelefono);
            }
            if (serIntOperadora != null) {
                serIntOperadora.getSaeServicioList().add(saeServicio);
                serIntOperadora = em.merge(serIntOperadora);
            }
            if (serIntConductor != null) {
                serIntConductor.getSaeServicioList().add(saeServicio);
                serIntConductor = em.merge(serIntConductor);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSaeServicio(saeServicio.getSerIntId()) != null) {
                throw new PreexistingEntityException("SaeServicio " + saeServicio + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SaeServicio saeServicio) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SaeServicio persistentSaeServicio = em.find(SaeServicio.class, saeServicio.getSerIntId());
            SaeVehiculo serStrPlacaOld = persistentSaeServicio.getSerStrPlaca();
            SaeVehiculo serStrPlacaNew = saeServicio.getSerStrPlaca();
            SaeUsuariovehiculo serIntTelefonoOld = persistentSaeServicio.getSerIntTelefono();
            SaeUsuariovehiculo serIntTelefonoNew = saeServicio.getSerIntTelefono();
            SaeOperador serIntOperadoraOld = persistentSaeServicio.getSerIntOperadora();
            SaeOperador serIntOperadoraNew = saeServicio.getSerIntOperadora();
            SaeConductor serIntConductorOld = persistentSaeServicio.getSerIntConductor();
            SaeConductor serIntConductorNew = saeServicio.getSerIntConductor();
            if (serStrPlacaNew != null) {
                serStrPlacaNew = em.getReference(serStrPlacaNew.getClass(), serStrPlacaNew.getVehStrPlaca());
                saeServicio.setSerStrPlaca(serStrPlacaNew);
            }
            if (serIntTelefonoNew != null) {
                serIntTelefonoNew = em.getReference(serIntTelefonoNew.getClass(), serIntTelefonoNew.getUsuIntTelefono());
                saeServicio.setSerIntTelefono(serIntTelefonoNew);
            }
            if (serIntOperadoraNew != null) {
                serIntOperadoraNew = em.getReference(serIntOperadoraNew.getClass(), serIntOperadoraNew.getOpeIntId());
                saeServicio.setSerIntOperadora(serIntOperadoraNew);
            }
            if (serIntConductorNew != null) {
                serIntConductorNew = em.getReference(serIntConductorNew.getClass(), serIntConductorNew.getConIntCedula());
                saeServicio.setSerIntConductor(serIntConductorNew);
            }
            saeServicio = em.merge(saeServicio);
            if (serStrPlacaOld != null && !serStrPlacaOld.equals(serStrPlacaNew)) {
                serStrPlacaOld.getSaeServicioList().remove(saeServicio);
                serStrPlacaOld = em.merge(serStrPlacaOld);
            }
            if (serStrPlacaNew != null && !serStrPlacaNew.equals(serStrPlacaOld)) {
                serStrPlacaNew.getSaeServicioList().add(saeServicio);
                serStrPlacaNew = em.merge(serStrPlacaNew);
            }
            if (serIntTelefonoOld != null && !serIntTelefonoOld.equals(serIntTelefonoNew)) {
                serIntTelefonoOld.getSaeServicioList().remove(saeServicio);
                serIntTelefonoOld = em.merge(serIntTelefonoOld);
            }
            if (serIntTelefonoNew != null && !serIntTelefonoNew.equals(serIntTelefonoOld)) {
                serIntTelefonoNew.getSaeServicioList().add(saeServicio);
                serIntTelefonoNew = em.merge(serIntTelefonoNew);
            }
            if (serIntOperadoraOld != null && !serIntOperadoraOld.equals(serIntOperadoraNew)) {
                serIntOperadoraOld.getSaeServicioList().remove(saeServicio);
                serIntOperadoraOld = em.merge(serIntOperadoraOld);
            }
            if (serIntOperadoraNew != null && !serIntOperadoraNew.equals(serIntOperadoraOld)) {
                serIntOperadoraNew.getSaeServicioList().add(saeServicio);
                serIntOperadoraNew = em.merge(serIntOperadoraNew);
            }
            if (serIntConductorOld != null && !serIntConductorOld.equals(serIntConductorNew)) {
                serIntConductorOld.getSaeServicioList().remove(saeServicio);
                serIntConductorOld = em.merge(serIntConductorOld);
            }
            if (serIntConductorNew != null && !serIntConductorNew.equals(serIntConductorOld)) {
                serIntConductorNew.getSaeServicioList().add(saeServicio);
                serIntConductorNew = em.merge(serIntConductorNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = saeServicio.getSerIntId();
                if (findSaeServicio(id) == null) {
                    throw new NonexistentEntityException("The saeServicio with id " + id + " no longer exists.");
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
            SaeServicio saeServicio;
            try {
                saeServicio = em.getReference(SaeServicio.class, id);
                saeServicio.getSerIntId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The saeServicio with id " + id + " no longer exists.", enfe);
            }
            SaeVehiculo serStrPlaca = saeServicio.getSerStrPlaca();
            if (serStrPlaca != null) {
                serStrPlaca.getSaeServicioList().remove(saeServicio);
                serStrPlaca = em.merge(serStrPlaca);
            }
            SaeUsuariovehiculo serIntTelefono = saeServicio.getSerIntTelefono();
            if (serIntTelefono != null) {
                serIntTelefono.getSaeServicioList().remove(saeServicio);
                serIntTelefono = em.merge(serIntTelefono);
            }
            SaeOperador serIntOperadora = saeServicio.getSerIntOperadora();
            if (serIntOperadora != null) {
                serIntOperadora.getSaeServicioList().remove(saeServicio);
                serIntOperadora = em.merge(serIntOperadora);
            }
            SaeConductor serIntConductor = saeServicio.getSerIntConductor();
            if (serIntConductor != null) {
                serIntConductor.getSaeServicioList().remove(saeServicio);
                serIntConductor = em.merge(serIntConductor);
            }
            em.remove(saeServicio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SaeServicio> findSaeServicioEntities() {
        return findSaeServicioEntities(true, -1, -1);
    }

    public List<SaeServicio> findSaeServicioEntities(int maxResults, int firstResult) {
        return findSaeServicioEntities(false, maxResults, firstResult);
    }

    private List<SaeServicio> findSaeServicioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SaeServicio.class));
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

    public SaeServicio findSaeServicio(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SaeServicio.class, id);
        } finally {
            em.close();
        }
    }

    public int getSaeServicioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SaeServicio> rt = cq.from(SaeServicio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
