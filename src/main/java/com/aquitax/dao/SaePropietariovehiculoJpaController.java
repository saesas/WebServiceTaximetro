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
import com.aquitax.domain.SaeEmpresa;
import com.aquitax.domain.SaePropietarioconductor;
import com.aquitax.domain.SaePropietariovehiculo;
import java.util.ArrayList;
import java.util.List;
import com.aquitax.domain.SaeVehiculo;
import com.aquitax.util.JpaUtil;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class SaePropietariovehiculoJpaController implements Serializable {

    public SaePropietariovehiculoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SaePropietariovehiculo saePropietariovehiculo) throws PreexistingEntityException, Exception {
        if (saePropietariovehiculo.getSaePropietarioconductorList() == null) {
            saePropietariovehiculo.setSaePropietarioconductorList(new ArrayList<SaePropietarioconductor>());
        }
        if (saePropietariovehiculo.getSaeVehiculoList() == null) {
            saePropietariovehiculo.setSaeVehiculoList(new ArrayList<SaeVehiculo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SaeEmpresa proStrEmpresa = saePropietariovehiculo.getProStrEmpresa();
            if (proStrEmpresa != null) {
                proStrEmpresa = em.getReference(proStrEmpresa.getClass(), proStrEmpresa.getEmpStrNit());
                saePropietariovehiculo.setProStrEmpresa(proStrEmpresa);
            }
            List<SaePropietarioconductor> attachedSaePropietarioconductorList = new ArrayList<SaePropietarioconductor>();
            for (SaePropietarioconductor saePropietarioconductorListSaePropietarioconductorToAttach : saePropietariovehiculo.getSaePropietarioconductorList()) {
                saePropietarioconductorListSaePropietarioconductorToAttach = em.getReference(saePropietarioconductorListSaePropietarioconductorToAttach.getClass(), saePropietarioconductorListSaePropietarioconductorToAttach.getPrcoIntId());
                attachedSaePropietarioconductorList.add(saePropietarioconductorListSaePropietarioconductorToAttach);
            }
            saePropietariovehiculo.setSaePropietarioconductorList(attachedSaePropietarioconductorList);
            List<SaeVehiculo> attachedSaeVehiculoList = new ArrayList<SaeVehiculo>();
            for (SaeVehiculo saeVehiculoListSaeVehiculoToAttach : saePropietariovehiculo.getSaeVehiculoList()) {
                saeVehiculoListSaeVehiculoToAttach = em.getReference(saeVehiculoListSaeVehiculoToAttach.getClass(), saeVehiculoListSaeVehiculoToAttach.getVehStrPlaca());
                attachedSaeVehiculoList.add(saeVehiculoListSaeVehiculoToAttach);
            }
            saePropietariovehiculo.setSaeVehiculoList(attachedSaeVehiculoList);
            em.persist(saePropietariovehiculo);
            if (proStrEmpresa != null) {
                proStrEmpresa.getSaePropietariovehiculoList().add(saePropietariovehiculo);
                proStrEmpresa = em.merge(proStrEmpresa);
            }
            for (SaePropietarioconductor saePropietarioconductorListSaePropietarioconductor : saePropietariovehiculo.getSaePropietarioconductorList()) {
                SaePropietariovehiculo oldPrcoIntCedulapropietarioOfSaePropietarioconductorListSaePropietarioconductor = saePropietarioconductorListSaePropietarioconductor.getPrcoIntCedulapropietario();
                saePropietarioconductorListSaePropietarioconductor.setPrcoIntCedulapropietario(saePropietariovehiculo);
                saePropietarioconductorListSaePropietarioconductor = em.merge(saePropietarioconductorListSaePropietarioconductor);
                if (oldPrcoIntCedulapropietarioOfSaePropietarioconductorListSaePropietarioconductor != null) {
                    oldPrcoIntCedulapropietarioOfSaePropietarioconductorListSaePropietarioconductor.getSaePropietarioconductorList().remove(saePropietarioconductorListSaePropietarioconductor);
                    oldPrcoIntCedulapropietarioOfSaePropietarioconductorListSaePropietarioconductor = em.merge(oldPrcoIntCedulapropietarioOfSaePropietarioconductorListSaePropietarioconductor);
                }
            }
            for (SaeVehiculo saeVehiculoListSaeVehiculo : saePropietariovehiculo.getSaeVehiculoList()) {
                SaePropietariovehiculo oldVehIntPropietarioOfSaeVehiculoListSaeVehiculo = saeVehiculoListSaeVehiculo.getVehIntPropietario();
                saeVehiculoListSaeVehiculo.setVehIntPropietario(saePropietariovehiculo);
                saeVehiculoListSaeVehiculo = em.merge(saeVehiculoListSaeVehiculo);
                if (oldVehIntPropietarioOfSaeVehiculoListSaeVehiculo != null) {
                    oldVehIntPropietarioOfSaeVehiculoListSaeVehiculo.getSaeVehiculoList().remove(saeVehiculoListSaeVehiculo);
                    oldVehIntPropietarioOfSaeVehiculoListSaeVehiculo = em.merge(oldVehIntPropietarioOfSaeVehiculoListSaeVehiculo);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSaePropietariovehiculo(saePropietariovehiculo.getProIntCedula()) != null) {
                throw new PreexistingEntityException("SaePropietariovehiculo " + saePropietariovehiculo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SaePropietariovehiculo saePropietariovehiculo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SaePropietariovehiculo persistentSaePropietariovehiculo = em.find(SaePropietariovehiculo.class, saePropietariovehiculo.getProIntCedula());
            SaeEmpresa proStrEmpresaOld = persistentSaePropietariovehiculo.getProStrEmpresa();
            SaeEmpresa proStrEmpresaNew = saePropietariovehiculo.getProStrEmpresa();
            List<SaePropietarioconductor> saePropietarioconductorListOld = persistentSaePropietariovehiculo.getSaePropietarioconductorList();
            List<SaePropietarioconductor> saePropietarioconductorListNew = saePropietariovehiculo.getSaePropietarioconductorList();
            List<SaeVehiculo> saeVehiculoListOld = persistentSaePropietariovehiculo.getSaeVehiculoList();
            List<SaeVehiculo> saeVehiculoListNew = saePropietariovehiculo.getSaeVehiculoList();
            if (proStrEmpresaNew != null) {
                proStrEmpresaNew = em.getReference(proStrEmpresaNew.getClass(), proStrEmpresaNew.getEmpStrNit());
                saePropietariovehiculo.setProStrEmpresa(proStrEmpresaNew);
            }
            List<SaePropietarioconductor> attachedSaePropietarioconductorListNew = new ArrayList<SaePropietarioconductor>();
            for (SaePropietarioconductor saePropietarioconductorListNewSaePropietarioconductorToAttach : saePropietarioconductorListNew) {
                saePropietarioconductorListNewSaePropietarioconductorToAttach = em.getReference(saePropietarioconductorListNewSaePropietarioconductorToAttach.getClass(), saePropietarioconductorListNewSaePropietarioconductorToAttach.getPrcoIntId());
                attachedSaePropietarioconductorListNew.add(saePropietarioconductorListNewSaePropietarioconductorToAttach);
            }
            saePropietarioconductorListNew = attachedSaePropietarioconductorListNew;
            saePropietariovehiculo.setSaePropietarioconductorList(saePropietarioconductorListNew);
            List<SaeVehiculo> attachedSaeVehiculoListNew = new ArrayList<SaeVehiculo>();
            for (SaeVehiculo saeVehiculoListNewSaeVehiculoToAttach : saeVehiculoListNew) {
                saeVehiculoListNewSaeVehiculoToAttach = em.getReference(saeVehiculoListNewSaeVehiculoToAttach.getClass(), saeVehiculoListNewSaeVehiculoToAttach.getVehStrPlaca());
                attachedSaeVehiculoListNew.add(saeVehiculoListNewSaeVehiculoToAttach);
            }
            saeVehiculoListNew = attachedSaeVehiculoListNew;
            saePropietariovehiculo.setSaeVehiculoList(saeVehiculoListNew);
            saePropietariovehiculo = em.merge(saePropietariovehiculo);
            if (proStrEmpresaOld != null && !proStrEmpresaOld.equals(proStrEmpresaNew)) {
                proStrEmpresaOld.getSaePropietariovehiculoList().remove(saePropietariovehiculo);
                proStrEmpresaOld = em.merge(proStrEmpresaOld);
            }
            if (proStrEmpresaNew != null && !proStrEmpresaNew.equals(proStrEmpresaOld)) {
                proStrEmpresaNew.getSaePropietariovehiculoList().add(saePropietariovehiculo);
                proStrEmpresaNew = em.merge(proStrEmpresaNew);
            }
            for (SaePropietarioconductor saePropietarioconductorListOldSaePropietarioconductor : saePropietarioconductorListOld) {
                if (!saePropietarioconductorListNew.contains(saePropietarioconductorListOldSaePropietarioconductor)) {
                    saePropietarioconductorListOldSaePropietarioconductor.setPrcoIntCedulapropietario(null);
                    saePropietarioconductorListOldSaePropietarioconductor = em.merge(saePropietarioconductorListOldSaePropietarioconductor);
                }
            }
            for (SaePropietarioconductor saePropietarioconductorListNewSaePropietarioconductor : saePropietarioconductorListNew) {
                if (!saePropietarioconductorListOld.contains(saePropietarioconductorListNewSaePropietarioconductor)) {
                    SaePropietariovehiculo oldPrcoIntCedulapropietarioOfSaePropietarioconductorListNewSaePropietarioconductor = saePropietarioconductorListNewSaePropietarioconductor.getPrcoIntCedulapropietario();
                    saePropietarioconductorListNewSaePropietarioconductor.setPrcoIntCedulapropietario(saePropietariovehiculo);
                    saePropietarioconductorListNewSaePropietarioconductor = em.merge(saePropietarioconductorListNewSaePropietarioconductor);
                    if (oldPrcoIntCedulapropietarioOfSaePropietarioconductorListNewSaePropietarioconductor != null && !oldPrcoIntCedulapropietarioOfSaePropietarioconductorListNewSaePropietarioconductor.equals(saePropietariovehiculo)) {
                        oldPrcoIntCedulapropietarioOfSaePropietarioconductorListNewSaePropietarioconductor.getSaePropietarioconductorList().remove(saePropietarioconductorListNewSaePropietarioconductor);
                        oldPrcoIntCedulapropietarioOfSaePropietarioconductorListNewSaePropietarioconductor = em.merge(oldPrcoIntCedulapropietarioOfSaePropietarioconductorListNewSaePropietarioconductor);
                    }
                }
            }
            for (SaeVehiculo saeVehiculoListOldSaeVehiculo : saeVehiculoListOld) {
                if (!saeVehiculoListNew.contains(saeVehiculoListOldSaeVehiculo)) {
                    saeVehiculoListOldSaeVehiculo.setVehIntPropietario(null);
                    saeVehiculoListOldSaeVehiculo = em.merge(saeVehiculoListOldSaeVehiculo);
                }
            }
            for (SaeVehiculo saeVehiculoListNewSaeVehiculo : saeVehiculoListNew) {
                if (!saeVehiculoListOld.contains(saeVehiculoListNewSaeVehiculo)) {
                    SaePropietariovehiculo oldVehIntPropietarioOfSaeVehiculoListNewSaeVehiculo = saeVehiculoListNewSaeVehiculo.getVehIntPropietario();
                    saeVehiculoListNewSaeVehiculo.setVehIntPropietario(saePropietariovehiculo);
                    saeVehiculoListNewSaeVehiculo = em.merge(saeVehiculoListNewSaeVehiculo);
                    if (oldVehIntPropietarioOfSaeVehiculoListNewSaeVehiculo != null && !oldVehIntPropietarioOfSaeVehiculoListNewSaeVehiculo.equals(saePropietariovehiculo)) {
                        oldVehIntPropietarioOfSaeVehiculoListNewSaeVehiculo.getSaeVehiculoList().remove(saeVehiculoListNewSaeVehiculo);
                        oldVehIntPropietarioOfSaeVehiculoListNewSaeVehiculo = em.merge(oldVehIntPropietarioOfSaeVehiculoListNewSaeVehiculo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = saePropietariovehiculo.getProIntCedula();
                if (findSaePropietariovehiculo(id) == null) {
                    throw new NonexistentEntityException("The saePropietariovehiculo with id " + id + " no longer exists.");
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
            SaePropietariovehiculo saePropietariovehiculo;
            try {
                saePropietariovehiculo = em.getReference(SaePropietariovehiculo.class, id);
                saePropietariovehiculo.getProIntCedula();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The saePropietariovehiculo with id " + id + " no longer exists.", enfe);
            }
            SaeEmpresa proStrEmpresa = saePropietariovehiculo.getProStrEmpresa();
            if (proStrEmpresa != null) {
                proStrEmpresa.getSaePropietariovehiculoList().remove(saePropietariovehiculo);
                proStrEmpresa = em.merge(proStrEmpresa);
            }
            List<SaePropietarioconductor> saePropietarioconductorList = saePropietariovehiculo.getSaePropietarioconductorList();
            for (SaePropietarioconductor saePropietarioconductorListSaePropietarioconductor : saePropietarioconductorList) {
                saePropietarioconductorListSaePropietarioconductor.setPrcoIntCedulapropietario(null);
                saePropietarioconductorListSaePropietarioconductor = em.merge(saePropietarioconductorListSaePropietarioconductor);
            }
            List<SaeVehiculo> saeVehiculoList = saePropietariovehiculo.getSaeVehiculoList();
            for (SaeVehiculo saeVehiculoListSaeVehiculo : saeVehiculoList) {
                saeVehiculoListSaeVehiculo.setVehIntPropietario(null);
                saeVehiculoListSaeVehiculo = em.merge(saeVehiculoListSaeVehiculo);
            }
            em.remove(saePropietariovehiculo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SaePropietariovehiculo> findSaePropietariovehiculoEntities() {
        return findSaePropietariovehiculoEntities(true, -1, -1);
    }

    public List<SaePropietariovehiculo> findSaePropietariovehiculoEntities(int maxResults, int firstResult) {
        return findSaePropietariovehiculoEntities(false, maxResults, firstResult);
    }

    private List<SaePropietariovehiculo> findSaePropietariovehiculoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SaePropietariovehiculo.class));
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

    public SaePropietariovehiculo findSaePropietariovehiculo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SaePropietariovehiculo.class, id);
        } finally {
            em.close();
        }
    }

    public int getSaePropietariovehiculoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SaePropietariovehiculo> rt = cq.from(SaePropietariovehiculo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
