/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aquitax.dao;

import com.aquitax.dao.exceptions.NonexistentEntityException;
import com.aquitax.dao.exceptions.PreexistingEntityException;
import com.aquitax.domain.SaeEmpresa;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.aquitax.domain.SaeOperador;
import java.util.ArrayList;
import java.util.List;
import com.aquitax.domain.SaePropietariovehiculo;
import com.aquitax.util.JpaUtil;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class SaeEmpresaJpaController implements Serializable {

    public SaeEmpresaJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SaeEmpresa saeEmpresa) throws PreexistingEntityException, Exception {
        if (saeEmpresa.getSaeOperadorList() == null) {
            saeEmpresa.setSaeOperadorList(new ArrayList<SaeOperador>());
        }
        if (saeEmpresa.getSaePropietariovehiculoList() == null) {
            saeEmpresa.setSaePropietariovehiculoList(new ArrayList<SaePropietariovehiculo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<SaeOperador> attachedSaeOperadorList = new ArrayList<SaeOperador>();
            for (SaeOperador saeOperadorListSaeOperadorToAttach : saeEmpresa.getSaeOperadorList()) {
                saeOperadorListSaeOperadorToAttach = em.getReference(saeOperadorListSaeOperadorToAttach.getClass(), saeOperadorListSaeOperadorToAttach.getOpeIntId());
                attachedSaeOperadorList.add(saeOperadorListSaeOperadorToAttach);
            }
            saeEmpresa.setSaeOperadorList(attachedSaeOperadorList);
            List<SaePropietariovehiculo> attachedSaePropietariovehiculoList = new ArrayList<SaePropietariovehiculo>();
            for (SaePropietariovehiculo saePropietariovehiculoListSaePropietariovehiculoToAttach : saeEmpresa.getSaePropietariovehiculoList()) {
                saePropietariovehiculoListSaePropietariovehiculoToAttach = em.getReference(saePropietariovehiculoListSaePropietariovehiculoToAttach.getClass(), saePropietariovehiculoListSaePropietariovehiculoToAttach.getProIntCedula());
                attachedSaePropietariovehiculoList.add(saePropietariovehiculoListSaePropietariovehiculoToAttach);
            }
            saeEmpresa.setSaePropietariovehiculoList(attachedSaePropietariovehiculoList);
            em.persist(saeEmpresa);
            for (SaeOperador saeOperadorListSaeOperador : saeEmpresa.getSaeOperadorList()) {
                SaeEmpresa oldOpeStrEmpresaOfSaeOperadorListSaeOperador = saeOperadorListSaeOperador.getOpeStrEmpresa();
                saeOperadorListSaeOperador.setOpeStrEmpresa(saeEmpresa);
                saeOperadorListSaeOperador = em.merge(saeOperadorListSaeOperador);
                if (oldOpeStrEmpresaOfSaeOperadorListSaeOperador != null) {
                    oldOpeStrEmpresaOfSaeOperadorListSaeOperador.getSaeOperadorList().remove(saeOperadorListSaeOperador);
                    oldOpeStrEmpresaOfSaeOperadorListSaeOperador = em.merge(oldOpeStrEmpresaOfSaeOperadorListSaeOperador);
                }
            }
            for (SaePropietariovehiculo saePropietariovehiculoListSaePropietariovehiculo : saeEmpresa.getSaePropietariovehiculoList()) {
                SaeEmpresa oldProStrEmpresaOfSaePropietariovehiculoListSaePropietariovehiculo = saePropietariovehiculoListSaePropietariovehiculo.getProStrEmpresa();
                saePropietariovehiculoListSaePropietariovehiculo.setProStrEmpresa(saeEmpresa);
                saePropietariovehiculoListSaePropietariovehiculo = em.merge(saePropietariovehiculoListSaePropietariovehiculo);
                if (oldProStrEmpresaOfSaePropietariovehiculoListSaePropietariovehiculo != null) {
                    oldProStrEmpresaOfSaePropietariovehiculoListSaePropietariovehiculo.getSaePropietariovehiculoList().remove(saePropietariovehiculoListSaePropietariovehiculo);
                    oldProStrEmpresaOfSaePropietariovehiculoListSaePropietariovehiculo = em.merge(oldProStrEmpresaOfSaePropietariovehiculoListSaePropietariovehiculo);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSaeEmpresa(saeEmpresa.getEmpStrNit()) != null) {
                throw new PreexistingEntityException("SaeEmpresa " + saeEmpresa + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SaeEmpresa saeEmpresa) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SaeEmpresa persistentSaeEmpresa = em.find(SaeEmpresa.class, saeEmpresa.getEmpStrNit());
            List<SaeOperador> saeOperadorListOld = persistentSaeEmpresa.getSaeOperadorList();
            List<SaeOperador> saeOperadorListNew = saeEmpresa.getSaeOperadorList();
            List<SaePropietariovehiculo> saePropietariovehiculoListOld = persistentSaeEmpresa.getSaePropietariovehiculoList();
            List<SaePropietariovehiculo> saePropietariovehiculoListNew = saeEmpresa.getSaePropietariovehiculoList();
            List<SaeOperador> attachedSaeOperadorListNew = new ArrayList<SaeOperador>();
            for (SaeOperador saeOperadorListNewSaeOperadorToAttach : saeOperadorListNew) {
                saeOperadorListNewSaeOperadorToAttach = em.getReference(saeOperadorListNewSaeOperadorToAttach.getClass(), saeOperadorListNewSaeOperadorToAttach.getOpeIntId());
                attachedSaeOperadorListNew.add(saeOperadorListNewSaeOperadorToAttach);
            }
            saeOperadorListNew = attachedSaeOperadorListNew;
            saeEmpresa.setSaeOperadorList(saeOperadorListNew);
            List<SaePropietariovehiculo> attachedSaePropietariovehiculoListNew = new ArrayList<SaePropietariovehiculo>();
            for (SaePropietariovehiculo saePropietariovehiculoListNewSaePropietariovehiculoToAttach : saePropietariovehiculoListNew) {
                saePropietariovehiculoListNewSaePropietariovehiculoToAttach = em.getReference(saePropietariovehiculoListNewSaePropietariovehiculoToAttach.getClass(), saePropietariovehiculoListNewSaePropietariovehiculoToAttach.getProIntCedula());
                attachedSaePropietariovehiculoListNew.add(saePropietariovehiculoListNewSaePropietariovehiculoToAttach);
            }
            saePropietariovehiculoListNew = attachedSaePropietariovehiculoListNew;
            saeEmpresa.setSaePropietariovehiculoList(saePropietariovehiculoListNew);
            saeEmpresa = em.merge(saeEmpresa);
            for (SaeOperador saeOperadorListOldSaeOperador : saeOperadorListOld) {
                if (!saeOperadorListNew.contains(saeOperadorListOldSaeOperador)) {
                    saeOperadorListOldSaeOperador.setOpeStrEmpresa(null);
                    saeOperadorListOldSaeOperador = em.merge(saeOperadorListOldSaeOperador);
                }
            }
            for (SaeOperador saeOperadorListNewSaeOperador : saeOperadorListNew) {
                if (!saeOperadorListOld.contains(saeOperadorListNewSaeOperador)) {
                    SaeEmpresa oldOpeStrEmpresaOfSaeOperadorListNewSaeOperador = saeOperadorListNewSaeOperador.getOpeStrEmpresa();
                    saeOperadorListNewSaeOperador.setOpeStrEmpresa(saeEmpresa);
                    saeOperadorListNewSaeOperador = em.merge(saeOperadorListNewSaeOperador);
                    if (oldOpeStrEmpresaOfSaeOperadorListNewSaeOperador != null && !oldOpeStrEmpresaOfSaeOperadorListNewSaeOperador.equals(saeEmpresa)) {
                        oldOpeStrEmpresaOfSaeOperadorListNewSaeOperador.getSaeOperadorList().remove(saeOperadorListNewSaeOperador);
                        oldOpeStrEmpresaOfSaeOperadorListNewSaeOperador = em.merge(oldOpeStrEmpresaOfSaeOperadorListNewSaeOperador);
                    }
                }
            }
            for (SaePropietariovehiculo saePropietariovehiculoListOldSaePropietariovehiculo : saePropietariovehiculoListOld) {
                if (!saePropietariovehiculoListNew.contains(saePropietariovehiculoListOldSaePropietariovehiculo)) {
                    saePropietariovehiculoListOldSaePropietariovehiculo.setProStrEmpresa(null);
                    saePropietariovehiculoListOldSaePropietariovehiculo = em.merge(saePropietariovehiculoListOldSaePropietariovehiculo);
                }
            }
            for (SaePropietariovehiculo saePropietariovehiculoListNewSaePropietariovehiculo : saePropietariovehiculoListNew) {
                if (!saePropietariovehiculoListOld.contains(saePropietariovehiculoListNewSaePropietariovehiculo)) {
                    SaeEmpresa oldProStrEmpresaOfSaePropietariovehiculoListNewSaePropietariovehiculo = saePropietariovehiculoListNewSaePropietariovehiculo.getProStrEmpresa();
                    saePropietariovehiculoListNewSaePropietariovehiculo.setProStrEmpresa(saeEmpresa);
                    saePropietariovehiculoListNewSaePropietariovehiculo = em.merge(saePropietariovehiculoListNewSaePropietariovehiculo);
                    if (oldProStrEmpresaOfSaePropietariovehiculoListNewSaePropietariovehiculo != null && !oldProStrEmpresaOfSaePropietariovehiculoListNewSaePropietariovehiculo.equals(saeEmpresa)) {
                        oldProStrEmpresaOfSaePropietariovehiculoListNewSaePropietariovehiculo.getSaePropietariovehiculoList().remove(saePropietariovehiculoListNewSaePropietariovehiculo);
                        oldProStrEmpresaOfSaePropietariovehiculoListNewSaePropietariovehiculo = em.merge(oldProStrEmpresaOfSaePropietariovehiculoListNewSaePropietariovehiculo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = saeEmpresa.getEmpStrNit();
                if (findSaeEmpresa(id) == null) {
                    throw new NonexistentEntityException("The saeEmpresa with id " + id + " no longer exists.");
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
            SaeEmpresa saeEmpresa;
            try {
                saeEmpresa = em.getReference(SaeEmpresa.class, id);
                saeEmpresa.getEmpStrNit();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The saeEmpresa with id " + id + " no longer exists.", enfe);
            }
            List<SaeOperador> saeOperadorList = saeEmpresa.getSaeOperadorList();
            for (SaeOperador saeOperadorListSaeOperador : saeOperadorList) {
                saeOperadorListSaeOperador.setOpeStrEmpresa(null);
                saeOperadorListSaeOperador = em.merge(saeOperadorListSaeOperador);
            }
            List<SaePropietariovehiculo> saePropietariovehiculoList = saeEmpresa.getSaePropietariovehiculoList();
            for (SaePropietariovehiculo saePropietariovehiculoListSaePropietariovehiculo : saePropietariovehiculoList) {
                saePropietariovehiculoListSaePropietariovehiculo.setProStrEmpresa(null);
                saePropietariovehiculoListSaePropietariovehiculo = em.merge(saePropietariovehiculoListSaePropietariovehiculo);
            }
            em.remove(saeEmpresa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SaeEmpresa> findSaeEmpresaEntities() {
        return findSaeEmpresaEntities(true, -1, -1);
    }

    public List<SaeEmpresa> findSaeEmpresaEntities(int maxResults, int firstResult) {
        return findSaeEmpresaEntities(false, maxResults, firstResult);
    }

    private List<SaeEmpresa> findSaeEmpresaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SaeEmpresa.class));
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

    public SaeEmpresa findSaeEmpresa(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SaeEmpresa.class, id);
        } finally {
            em.close();
        }
    }

    public int getSaeEmpresaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SaeEmpresa> rt = cq.from(SaeEmpresa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
