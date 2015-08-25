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
import com.aquitax.domain.SaeOperador;
import com.aquitax.domain.SaeServicio;
import com.aquitax.util.JpaUtil;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class SaeOperadorJpaController implements Serializable {

    public SaeOperadorJpaController() {
       this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SaeOperador saeOperador) throws PreexistingEntityException, Exception {
        if (saeOperador.getSaeServicioList() == null) {
            saeOperador.setSaeServicioList(new ArrayList<SaeServicio>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SaeEmpresa opeStrEmpresa = saeOperador.getOpeStrEmpresa();
            if (opeStrEmpresa != null) {
                opeStrEmpresa = em.getReference(opeStrEmpresa.getClass(), opeStrEmpresa.getEmpStrNit());
                saeOperador.setOpeStrEmpresa(opeStrEmpresa);
            }
            List<SaeServicio> attachedSaeServicioList = new ArrayList<SaeServicio>();
            for (SaeServicio saeServicioListSaeServicioToAttach : saeOperador.getSaeServicioList()) {
                saeServicioListSaeServicioToAttach = em.getReference(saeServicioListSaeServicioToAttach.getClass(), saeServicioListSaeServicioToAttach.getSerIntId());
                attachedSaeServicioList.add(saeServicioListSaeServicioToAttach);
            }
            saeOperador.setSaeServicioList(attachedSaeServicioList);
            em.persist(saeOperador);
            if (opeStrEmpresa != null) {
                opeStrEmpresa.getSaeOperadorList().add(saeOperador);
                opeStrEmpresa = em.merge(opeStrEmpresa);
            }
            for (SaeServicio saeServicioListSaeServicio : saeOperador.getSaeServicioList()) {
                SaeOperador oldSerIntOperadoraOfSaeServicioListSaeServicio = saeServicioListSaeServicio.getSerIntOperadora();
                saeServicioListSaeServicio.setSerIntOperadora(saeOperador);
                saeServicioListSaeServicio = em.merge(saeServicioListSaeServicio);
                if (oldSerIntOperadoraOfSaeServicioListSaeServicio != null) {
                    oldSerIntOperadoraOfSaeServicioListSaeServicio.getSaeServicioList().remove(saeServicioListSaeServicio);
                    oldSerIntOperadoraOfSaeServicioListSaeServicio = em.merge(oldSerIntOperadoraOfSaeServicioListSaeServicio);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSaeOperador(saeOperador.getOpeIntId()) != null) {
                throw new PreexistingEntityException("SaeOperador " + saeOperador + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SaeOperador saeOperador) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SaeOperador persistentSaeOperador = em.find(SaeOperador.class, saeOperador.getOpeIntId());
            SaeEmpresa opeStrEmpresaOld = persistentSaeOperador.getOpeStrEmpresa();
            SaeEmpresa opeStrEmpresaNew = saeOperador.getOpeStrEmpresa();
            List<SaeServicio> saeServicioListOld = persistentSaeOperador.getSaeServicioList();
            List<SaeServicio> saeServicioListNew = saeOperador.getSaeServicioList();
            if (opeStrEmpresaNew != null) {
                opeStrEmpresaNew = em.getReference(opeStrEmpresaNew.getClass(), opeStrEmpresaNew.getEmpStrNit());
                saeOperador.setOpeStrEmpresa(opeStrEmpresaNew);
            }
            List<SaeServicio> attachedSaeServicioListNew = new ArrayList<SaeServicio>();
            for (SaeServicio saeServicioListNewSaeServicioToAttach : saeServicioListNew) {
                saeServicioListNewSaeServicioToAttach = em.getReference(saeServicioListNewSaeServicioToAttach.getClass(), saeServicioListNewSaeServicioToAttach.getSerIntId());
                attachedSaeServicioListNew.add(saeServicioListNewSaeServicioToAttach);
            }
            saeServicioListNew = attachedSaeServicioListNew;
            saeOperador.setSaeServicioList(saeServicioListNew);
            saeOperador = em.merge(saeOperador);
            if (opeStrEmpresaOld != null && !opeStrEmpresaOld.equals(opeStrEmpresaNew)) {
                opeStrEmpresaOld.getSaeOperadorList().remove(saeOperador);
                opeStrEmpresaOld = em.merge(opeStrEmpresaOld);
            }
            if (opeStrEmpresaNew != null && !opeStrEmpresaNew.equals(opeStrEmpresaOld)) {
                opeStrEmpresaNew.getSaeOperadorList().add(saeOperador);
                opeStrEmpresaNew = em.merge(opeStrEmpresaNew);
            }
            for (SaeServicio saeServicioListOldSaeServicio : saeServicioListOld) {
                if (!saeServicioListNew.contains(saeServicioListOldSaeServicio)) {
                    saeServicioListOldSaeServicio.setSerIntOperadora(null);
                    saeServicioListOldSaeServicio = em.merge(saeServicioListOldSaeServicio);
                }
            }
            for (SaeServicio saeServicioListNewSaeServicio : saeServicioListNew) {
                if (!saeServicioListOld.contains(saeServicioListNewSaeServicio)) {
                    SaeOperador oldSerIntOperadoraOfSaeServicioListNewSaeServicio = saeServicioListNewSaeServicio.getSerIntOperadora();
                    saeServicioListNewSaeServicio.setSerIntOperadora(saeOperador);
                    saeServicioListNewSaeServicio = em.merge(saeServicioListNewSaeServicio);
                    if (oldSerIntOperadoraOfSaeServicioListNewSaeServicio != null && !oldSerIntOperadoraOfSaeServicioListNewSaeServicio.equals(saeOperador)) {
                        oldSerIntOperadoraOfSaeServicioListNewSaeServicio.getSaeServicioList().remove(saeServicioListNewSaeServicio);
                        oldSerIntOperadoraOfSaeServicioListNewSaeServicio = em.merge(oldSerIntOperadoraOfSaeServicioListNewSaeServicio);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = saeOperador.getOpeIntId();
                if (findSaeOperador(id) == null) {
                    throw new NonexistentEntityException("The saeOperador with id " + id + " no longer exists.");
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
            SaeOperador saeOperador;
            try {
                saeOperador = em.getReference(SaeOperador.class, id);
                saeOperador.getOpeIntId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The saeOperador with id " + id + " no longer exists.", enfe);
            }
            SaeEmpresa opeStrEmpresa = saeOperador.getOpeStrEmpresa();
            if (opeStrEmpresa != null) {
                opeStrEmpresa.getSaeOperadorList().remove(saeOperador);
                opeStrEmpresa = em.merge(opeStrEmpresa);
            }
            List<SaeServicio> saeServicioList = saeOperador.getSaeServicioList();
            for (SaeServicio saeServicioListSaeServicio : saeServicioList) {
                saeServicioListSaeServicio.setSerIntOperadora(null);
                saeServicioListSaeServicio = em.merge(saeServicioListSaeServicio);
            }
            em.remove(saeOperador);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SaeOperador> findSaeOperadorEntities() {
        return findSaeOperadorEntities(true, -1, -1);
    }

    public List<SaeOperador> findSaeOperadorEntities(int maxResults, int firstResult) {
        return findSaeOperadorEntities(false, maxResults, firstResult);
    }

    private List<SaeOperador> findSaeOperadorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SaeOperador.class));
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

    public SaeOperador findSaeOperador(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SaeOperador.class, id);
        } finally {
            em.close();
        }
    }

    public int getSaeOperadorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SaeOperador> rt = cq.from(SaeOperador.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
