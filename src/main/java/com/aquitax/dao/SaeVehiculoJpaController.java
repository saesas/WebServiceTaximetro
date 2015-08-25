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
import com.aquitax.domain.SaeServicio;
import java.util.ArrayList;
import java.util.List;
import com.aquitax.domain.SaeHistorialvehiculo;
import com.aquitax.domain.SaeTurnoconductor;
import com.aquitax.domain.SaeVehiculo;
import com.aquitax.util.JpaUtil;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class SaeVehiculoJpaController implements Serializable {

    public SaeVehiculoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SaeVehiculo saeVehiculo) throws PreexistingEntityException, Exception {
        if (saeVehiculo.getSaeServicioList() == null) {
            saeVehiculo.setSaeServicioList(new ArrayList<SaeServicio>());
        }
        if (saeVehiculo.getSaeHistorialvehiculoList() == null) {
            saeVehiculo.setSaeHistorialvehiculoList(new ArrayList<SaeHistorialvehiculo>());
        }
        if (saeVehiculo.getSaeTurnoconductorList() == null) {
            saeVehiculo.setSaeTurnoconductorList(new ArrayList<SaeTurnoconductor>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SaePropietariovehiculo vehIntPropietario = saeVehiculo.getVehIntPropietario();
            if (vehIntPropietario != null) {
                vehIntPropietario = em.getReference(vehIntPropietario.getClass(), vehIntPropietario.getProIntCedula());
                saeVehiculo.setVehIntPropietario(vehIntPropietario);
            }
            List<SaeServicio> attachedSaeServicioList = new ArrayList<SaeServicio>();
            for (SaeServicio saeServicioListSaeServicioToAttach : saeVehiculo.getSaeServicioList()) {
                saeServicioListSaeServicioToAttach = em.getReference(saeServicioListSaeServicioToAttach.getClass(), saeServicioListSaeServicioToAttach.getSerIntId());
                attachedSaeServicioList.add(saeServicioListSaeServicioToAttach);
            }
            saeVehiculo.setSaeServicioList(attachedSaeServicioList);
            List<SaeHistorialvehiculo> attachedSaeHistorialvehiculoList = new ArrayList<SaeHistorialvehiculo>();
            for (SaeHistorialvehiculo saeHistorialvehiculoListSaeHistorialvehiculoToAttach : saeVehiculo.getSaeHistorialvehiculoList()) {
                saeHistorialvehiculoListSaeHistorialvehiculoToAttach = em.getReference(saeHistorialvehiculoListSaeHistorialvehiculoToAttach.getClass(), saeHistorialvehiculoListSaeHistorialvehiculoToAttach.getHisIntId());
                attachedSaeHistorialvehiculoList.add(saeHistorialvehiculoListSaeHistorialvehiculoToAttach);
            }
            saeVehiculo.setSaeHistorialvehiculoList(attachedSaeHistorialvehiculoList);
            List<SaeTurnoconductor> attachedSaeTurnoconductorList = new ArrayList<SaeTurnoconductor>();
            for (SaeTurnoconductor saeTurnoconductorListSaeTurnoconductorToAttach : saeVehiculo.getSaeTurnoconductorList()) {
                saeTurnoconductorListSaeTurnoconductorToAttach = em.getReference(saeTurnoconductorListSaeTurnoconductorToAttach.getClass(), saeTurnoconductorListSaeTurnoconductorToAttach.getTurIntId());
                attachedSaeTurnoconductorList.add(saeTurnoconductorListSaeTurnoconductorToAttach);
            }
            saeVehiculo.setSaeTurnoconductorList(attachedSaeTurnoconductorList);
            em.persist(saeVehiculo);
            if (vehIntPropietario != null) {
                vehIntPropietario.getSaeVehiculoList().add(saeVehiculo);
                vehIntPropietario = em.merge(vehIntPropietario);
            }
            for (SaeServicio saeServicioListSaeServicio : saeVehiculo.getSaeServicioList()) {
                SaeVehiculo oldSerStrPlacaOfSaeServicioListSaeServicio = saeServicioListSaeServicio.getSerStrPlaca();
                saeServicioListSaeServicio.setSerStrPlaca(saeVehiculo);
                saeServicioListSaeServicio = em.merge(saeServicioListSaeServicio);
                if (oldSerStrPlacaOfSaeServicioListSaeServicio != null) {
                    oldSerStrPlacaOfSaeServicioListSaeServicio.getSaeServicioList().remove(saeServicioListSaeServicio);
                    oldSerStrPlacaOfSaeServicioListSaeServicio = em.merge(oldSerStrPlacaOfSaeServicioListSaeServicio);
                }
            }
            for (SaeHistorialvehiculo saeHistorialvehiculoListSaeHistorialvehiculo : saeVehiculo.getSaeHistorialvehiculoList()) {
                SaeVehiculo oldHisStrPlacaOfSaeHistorialvehiculoListSaeHistorialvehiculo = saeHistorialvehiculoListSaeHistorialvehiculo.getHisStrPlaca();
                saeHistorialvehiculoListSaeHistorialvehiculo.setHisStrPlaca(saeVehiculo);
                saeHistorialvehiculoListSaeHistorialvehiculo = em.merge(saeHistorialvehiculoListSaeHistorialvehiculo);
                if (oldHisStrPlacaOfSaeHistorialvehiculoListSaeHistorialvehiculo != null) {
                    oldHisStrPlacaOfSaeHistorialvehiculoListSaeHistorialvehiculo.getSaeHistorialvehiculoList().remove(saeHistorialvehiculoListSaeHistorialvehiculo);
                    oldHisStrPlacaOfSaeHistorialvehiculoListSaeHistorialvehiculo = em.merge(oldHisStrPlacaOfSaeHistorialvehiculoListSaeHistorialvehiculo);
                }
            }
            for (SaeTurnoconductor saeTurnoconductorListSaeTurnoconductor : saeVehiculo.getSaeTurnoconductorList()) {
                SaeVehiculo oldTurStrVehiculoOfSaeTurnoconductorListSaeTurnoconductor = saeTurnoconductorListSaeTurnoconductor.getTurStrVehiculo();
                saeTurnoconductorListSaeTurnoconductor.setTurStrVehiculo(saeVehiculo);
                saeTurnoconductorListSaeTurnoconductor = em.merge(saeTurnoconductorListSaeTurnoconductor);
                if (oldTurStrVehiculoOfSaeTurnoconductorListSaeTurnoconductor != null) {
                    oldTurStrVehiculoOfSaeTurnoconductorListSaeTurnoconductor.getSaeTurnoconductorList().remove(saeTurnoconductorListSaeTurnoconductor);
                    oldTurStrVehiculoOfSaeTurnoconductorListSaeTurnoconductor = em.merge(oldTurStrVehiculoOfSaeTurnoconductorListSaeTurnoconductor);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSaeVehiculo(saeVehiculo.getVehStrPlaca()) != null) {
                throw new PreexistingEntityException("SaeVehiculo " + saeVehiculo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SaeVehiculo saeVehiculo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SaeVehiculo persistentSaeVehiculo = em.find(SaeVehiculo.class, saeVehiculo.getVehStrPlaca());
            SaePropietariovehiculo vehIntPropietarioOld = persistentSaeVehiculo.getVehIntPropietario();
            SaePropietariovehiculo vehIntPropietarioNew = saeVehiculo.getVehIntPropietario();
            List<SaeServicio> saeServicioListOld = persistentSaeVehiculo.getSaeServicioList();
            List<SaeServicio> saeServicioListNew = saeVehiculo.getSaeServicioList();
            List<SaeHistorialvehiculo> saeHistorialvehiculoListOld = persistentSaeVehiculo.getSaeHistorialvehiculoList();
            List<SaeHistorialvehiculo> saeHistorialvehiculoListNew = saeVehiculo.getSaeHistorialvehiculoList();
            List<SaeTurnoconductor> saeTurnoconductorListOld = persistentSaeVehiculo.getSaeTurnoconductorList();
            List<SaeTurnoconductor> saeTurnoconductorListNew = saeVehiculo.getSaeTurnoconductorList();
            if (vehIntPropietarioNew != null) {
                vehIntPropietarioNew = em.getReference(vehIntPropietarioNew.getClass(), vehIntPropietarioNew.getProIntCedula());
                saeVehiculo.setVehIntPropietario(vehIntPropietarioNew);
            }
            List<SaeServicio> attachedSaeServicioListNew = new ArrayList<SaeServicio>();
            for (SaeServicio saeServicioListNewSaeServicioToAttach : saeServicioListNew) {
                saeServicioListNewSaeServicioToAttach = em.getReference(saeServicioListNewSaeServicioToAttach.getClass(), saeServicioListNewSaeServicioToAttach.getSerIntId());
                attachedSaeServicioListNew.add(saeServicioListNewSaeServicioToAttach);
            }
            saeServicioListNew = attachedSaeServicioListNew;
            saeVehiculo.setSaeServicioList(saeServicioListNew);
            List<SaeHistorialvehiculo> attachedSaeHistorialvehiculoListNew = new ArrayList<SaeHistorialvehiculo>();
            for (SaeHistorialvehiculo saeHistorialvehiculoListNewSaeHistorialvehiculoToAttach : saeHistorialvehiculoListNew) {
                saeHistorialvehiculoListNewSaeHistorialvehiculoToAttach = em.getReference(saeHistorialvehiculoListNewSaeHistorialvehiculoToAttach.getClass(), saeHistorialvehiculoListNewSaeHistorialvehiculoToAttach.getHisIntId());
                attachedSaeHistorialvehiculoListNew.add(saeHistorialvehiculoListNewSaeHistorialvehiculoToAttach);
            }
            saeHistorialvehiculoListNew = attachedSaeHistorialvehiculoListNew;
            saeVehiculo.setSaeHistorialvehiculoList(saeHistorialvehiculoListNew);
            List<SaeTurnoconductor> attachedSaeTurnoconductorListNew = new ArrayList<SaeTurnoconductor>();
            for (SaeTurnoconductor saeTurnoconductorListNewSaeTurnoconductorToAttach : saeTurnoconductorListNew) {
                saeTurnoconductorListNewSaeTurnoconductorToAttach = em.getReference(saeTurnoconductorListNewSaeTurnoconductorToAttach.getClass(), saeTurnoconductorListNewSaeTurnoconductorToAttach.getTurIntId());
                attachedSaeTurnoconductorListNew.add(saeTurnoconductorListNewSaeTurnoconductorToAttach);
            }
            saeTurnoconductorListNew = attachedSaeTurnoconductorListNew;
            saeVehiculo.setSaeTurnoconductorList(saeTurnoconductorListNew);
            saeVehiculo = em.merge(saeVehiculo);
            if (vehIntPropietarioOld != null && !vehIntPropietarioOld.equals(vehIntPropietarioNew)) {
                vehIntPropietarioOld.getSaeVehiculoList().remove(saeVehiculo);
                vehIntPropietarioOld = em.merge(vehIntPropietarioOld);
            }
            if (vehIntPropietarioNew != null && !vehIntPropietarioNew.equals(vehIntPropietarioOld)) {
                vehIntPropietarioNew.getSaeVehiculoList().add(saeVehiculo);
                vehIntPropietarioNew = em.merge(vehIntPropietarioNew);
            }
            for (SaeServicio saeServicioListOldSaeServicio : saeServicioListOld) {
                if (!saeServicioListNew.contains(saeServicioListOldSaeServicio)) {
                    saeServicioListOldSaeServicio.setSerStrPlaca(null);
                    saeServicioListOldSaeServicio = em.merge(saeServicioListOldSaeServicio);
                }
            }
            for (SaeServicio saeServicioListNewSaeServicio : saeServicioListNew) {
                if (!saeServicioListOld.contains(saeServicioListNewSaeServicio)) {
                    SaeVehiculo oldSerStrPlacaOfSaeServicioListNewSaeServicio = saeServicioListNewSaeServicio.getSerStrPlaca();
                    saeServicioListNewSaeServicio.setSerStrPlaca(saeVehiculo);
                    saeServicioListNewSaeServicio = em.merge(saeServicioListNewSaeServicio);
                    if (oldSerStrPlacaOfSaeServicioListNewSaeServicio != null && !oldSerStrPlacaOfSaeServicioListNewSaeServicio.equals(saeVehiculo)) {
                        oldSerStrPlacaOfSaeServicioListNewSaeServicio.getSaeServicioList().remove(saeServicioListNewSaeServicio);
                        oldSerStrPlacaOfSaeServicioListNewSaeServicio = em.merge(oldSerStrPlacaOfSaeServicioListNewSaeServicio);
                    }
                }
            }
            for (SaeHistorialvehiculo saeHistorialvehiculoListOldSaeHistorialvehiculo : saeHistorialvehiculoListOld) {
                if (!saeHistorialvehiculoListNew.contains(saeHistorialvehiculoListOldSaeHistorialvehiculo)) {
                    saeHistorialvehiculoListOldSaeHistorialvehiculo.setHisStrPlaca(null);
                    saeHistorialvehiculoListOldSaeHistorialvehiculo = em.merge(saeHistorialvehiculoListOldSaeHistorialvehiculo);
                }
            }
            for (SaeHistorialvehiculo saeHistorialvehiculoListNewSaeHistorialvehiculo : saeHistorialvehiculoListNew) {
                if (!saeHistorialvehiculoListOld.contains(saeHistorialvehiculoListNewSaeHistorialvehiculo)) {
                    SaeVehiculo oldHisStrPlacaOfSaeHistorialvehiculoListNewSaeHistorialvehiculo = saeHistorialvehiculoListNewSaeHistorialvehiculo.getHisStrPlaca();
                    saeHistorialvehiculoListNewSaeHistorialvehiculo.setHisStrPlaca(saeVehiculo);
                    saeHistorialvehiculoListNewSaeHistorialvehiculo = em.merge(saeHistorialvehiculoListNewSaeHistorialvehiculo);
                    if (oldHisStrPlacaOfSaeHistorialvehiculoListNewSaeHistorialvehiculo != null && !oldHisStrPlacaOfSaeHistorialvehiculoListNewSaeHistorialvehiculo.equals(saeVehiculo)) {
                        oldHisStrPlacaOfSaeHistorialvehiculoListNewSaeHistorialvehiculo.getSaeHistorialvehiculoList().remove(saeHistorialvehiculoListNewSaeHistorialvehiculo);
                        oldHisStrPlacaOfSaeHistorialvehiculoListNewSaeHistorialvehiculo = em.merge(oldHisStrPlacaOfSaeHistorialvehiculoListNewSaeHistorialvehiculo);
                    }
                }
            }
            for (SaeTurnoconductor saeTurnoconductorListOldSaeTurnoconductor : saeTurnoconductorListOld) {
                if (!saeTurnoconductorListNew.contains(saeTurnoconductorListOldSaeTurnoconductor)) {
                    saeTurnoconductorListOldSaeTurnoconductor.setTurStrVehiculo(null);
                    saeTurnoconductorListOldSaeTurnoconductor = em.merge(saeTurnoconductorListOldSaeTurnoconductor);
                }
            }
            for (SaeTurnoconductor saeTurnoconductorListNewSaeTurnoconductor : saeTurnoconductorListNew) {
                if (!saeTurnoconductorListOld.contains(saeTurnoconductorListNewSaeTurnoconductor)) {
                    SaeVehiculo oldTurStrVehiculoOfSaeTurnoconductorListNewSaeTurnoconductor = saeTurnoconductorListNewSaeTurnoconductor.getTurStrVehiculo();
                    saeTurnoconductorListNewSaeTurnoconductor.setTurStrVehiculo(saeVehiculo);
                    saeTurnoconductorListNewSaeTurnoconductor = em.merge(saeTurnoconductorListNewSaeTurnoconductor);
                    if (oldTurStrVehiculoOfSaeTurnoconductorListNewSaeTurnoconductor != null && !oldTurStrVehiculoOfSaeTurnoconductorListNewSaeTurnoconductor.equals(saeVehiculo)) {
                        oldTurStrVehiculoOfSaeTurnoconductorListNewSaeTurnoconductor.getSaeTurnoconductorList().remove(saeTurnoconductorListNewSaeTurnoconductor);
                        oldTurStrVehiculoOfSaeTurnoconductorListNewSaeTurnoconductor = em.merge(oldTurStrVehiculoOfSaeTurnoconductorListNewSaeTurnoconductor);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = saeVehiculo.getVehStrPlaca();
                if (findSaeVehiculo(id) == null) {
                    throw new NonexistentEntityException("The saeVehiculo with id " + id + " no longer exists.");
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
            SaeVehiculo saeVehiculo;
            try {
                saeVehiculo = em.getReference(SaeVehiculo.class, id);
                saeVehiculo.getVehStrPlaca();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The saeVehiculo with id " + id + " no longer exists.", enfe);
            }
            SaePropietariovehiculo vehIntPropietario = saeVehiculo.getVehIntPropietario();
            if (vehIntPropietario != null) {
                vehIntPropietario.getSaeVehiculoList().remove(saeVehiculo);
                vehIntPropietario = em.merge(vehIntPropietario);
            }
            List<SaeServicio> saeServicioList = saeVehiculo.getSaeServicioList();
            for (SaeServicio saeServicioListSaeServicio : saeServicioList) {
                saeServicioListSaeServicio.setSerStrPlaca(null);
                saeServicioListSaeServicio = em.merge(saeServicioListSaeServicio);
            }
            List<SaeHistorialvehiculo> saeHistorialvehiculoList = saeVehiculo.getSaeHistorialvehiculoList();
            for (SaeHistorialvehiculo saeHistorialvehiculoListSaeHistorialvehiculo : saeHistorialvehiculoList) {
                saeHistorialvehiculoListSaeHistorialvehiculo.setHisStrPlaca(null);
                saeHistorialvehiculoListSaeHistorialvehiculo = em.merge(saeHistorialvehiculoListSaeHistorialvehiculo);
            }
            List<SaeTurnoconductor> saeTurnoconductorList = saeVehiculo.getSaeTurnoconductorList();
            for (SaeTurnoconductor saeTurnoconductorListSaeTurnoconductor : saeTurnoconductorList) {
                saeTurnoconductorListSaeTurnoconductor.setTurStrVehiculo(null);
                saeTurnoconductorListSaeTurnoconductor = em.merge(saeTurnoconductorListSaeTurnoconductor);
            }
            em.remove(saeVehiculo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SaeVehiculo> findSaeVehiculoEntities() {
        return findSaeVehiculoEntities(true, -1, -1);
    }

    public List<SaeVehiculo> findSaeVehiculoEntities(int maxResults, int firstResult) {
        return findSaeVehiculoEntities(false, maxResults, firstResult);
    }

    private List<SaeVehiculo> findSaeVehiculoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SaeVehiculo.class));
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

    public SaeVehiculo findSaeVehiculo(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SaeVehiculo.class, id);
        } finally {
            em.close();
        }
    }

    public int getSaeVehiculoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SaeVehiculo> rt = cq.from(SaeVehiculo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
