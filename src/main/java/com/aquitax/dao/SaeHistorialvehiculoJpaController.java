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
import com.aquitax.domain.SaeEvento;
import com.aquitax.domain.SaeDatoevento;
import com.aquitax.domain.SaeHistorialvehiculo;
import com.aquitax.util.JpaUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import org.hibernate.SessionFactory;

/**
 *
 * @author SAE2
 */
public class SaeHistorialvehiculoJpaController implements Serializable {

    public SaeHistorialvehiculoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SaeHistorialvehiculo saeHistorialvehiculo) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SaeVehiculo hisStrPlaca = saeHistorialvehiculo.getHisStrPlaca();
            if (hisStrPlaca != null) {
                hisStrPlaca = em.getReference(hisStrPlaca.getClass(), hisStrPlaca.getVehStrPlaca());
                saeHistorialvehiculo.setHisStrPlaca(hisStrPlaca);
            }
            SaeEvento hisIntEvento = saeHistorialvehiculo.getHisIntEvento();
            if (hisIntEvento != null) {
                hisIntEvento = em.getReference(hisIntEvento.getClass(), hisIntEvento.getEveIntId());
                saeHistorialvehiculo.setHisIntEvento(hisIntEvento);
            }
            SaeDatoevento hisIntDatoevento = saeHistorialvehiculo.getHisIntDatoevento();
            if (hisIntDatoevento != null) {
                hisIntDatoevento = em.getReference(hisIntDatoevento.getClass(), hisIntDatoevento.getDatIntId());
                saeHistorialvehiculo.setHisIntDatoevento(hisIntDatoevento);
            }
            em.persist(saeHistorialvehiculo);
            if (hisStrPlaca != null) {
                hisStrPlaca.getSaeHistorialvehiculoList().add(saeHistorialvehiculo);
                hisStrPlaca = em.merge(hisStrPlaca);
            }
            if (hisIntEvento != null) {
                hisIntEvento.getSaeHistorialvehiculoList().add(saeHistorialvehiculo);
                hisIntEvento = em.merge(hisIntEvento);
            }
            if (hisIntDatoevento != null) {
                hisIntDatoevento.getSaeHistorialvehiculoList().add(saeHistorialvehiculo);
                hisIntDatoevento = em.merge(hisIntDatoevento);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSaeHistorialvehiculo(saeHistorialvehiculo.getHisIntId()) != null) {
                throw new PreexistingEntityException("SaeHistorialvehiculo " + saeHistorialvehiculo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SaeHistorialvehiculo saeHistorialvehiculo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SaeHistorialvehiculo persistentSaeHistorialvehiculo = em.find(SaeHistorialvehiculo.class, saeHistorialvehiculo.getHisIntId());
            SaeVehiculo hisStrPlacaOld = persistentSaeHistorialvehiculo.getHisStrPlaca();
            SaeVehiculo hisStrPlacaNew = saeHistorialvehiculo.getHisStrPlaca();
            SaeEvento hisIntEventoOld = persistentSaeHistorialvehiculo.getHisIntEvento();
            SaeEvento hisIntEventoNew = saeHistorialvehiculo.getHisIntEvento();
            SaeDatoevento hisIntDatoeventoOld = persistentSaeHistorialvehiculo.getHisIntDatoevento();
            SaeDatoevento hisIntDatoeventoNew = saeHistorialvehiculo.getHisIntDatoevento();
            if (hisStrPlacaNew != null) {
                hisStrPlacaNew = em.getReference(hisStrPlacaNew.getClass(), hisStrPlacaNew.getVehStrPlaca());
                saeHistorialvehiculo.setHisStrPlaca(hisStrPlacaNew);
            }
            if (hisIntEventoNew != null) {
                hisIntEventoNew = em.getReference(hisIntEventoNew.getClass(), hisIntEventoNew.getEveIntId());
                saeHistorialvehiculo.setHisIntEvento(hisIntEventoNew);
            }
            if (hisIntDatoeventoNew != null) {
                hisIntDatoeventoNew = em.getReference(hisIntDatoeventoNew.getClass(), hisIntDatoeventoNew.getDatIntId());
                saeHistorialvehiculo.setHisIntDatoevento(hisIntDatoeventoNew);
            }
            saeHistorialvehiculo = em.merge(saeHistorialvehiculo);
            if (hisStrPlacaOld != null && !hisStrPlacaOld.equals(hisStrPlacaNew)) {
                hisStrPlacaOld.getSaeHistorialvehiculoList().remove(saeHistorialvehiculo);
                hisStrPlacaOld = em.merge(hisStrPlacaOld);
            }
            if (hisStrPlacaNew != null && !hisStrPlacaNew.equals(hisStrPlacaOld)) {
                hisStrPlacaNew.getSaeHistorialvehiculoList().add(saeHistorialvehiculo);
                hisStrPlacaNew = em.merge(hisStrPlacaNew);
            }
            if (hisIntEventoOld != null && !hisIntEventoOld.equals(hisIntEventoNew)) {
                hisIntEventoOld.getSaeHistorialvehiculoList().remove(saeHistorialvehiculo);
                hisIntEventoOld = em.merge(hisIntEventoOld);
            }
            if (hisIntEventoNew != null && !hisIntEventoNew.equals(hisIntEventoOld)) {
                hisIntEventoNew.getSaeHistorialvehiculoList().add(saeHistorialvehiculo);
                hisIntEventoNew = em.merge(hisIntEventoNew);
            }
            if (hisIntDatoeventoOld != null && !hisIntDatoeventoOld.equals(hisIntDatoeventoNew)) {
                hisIntDatoeventoOld.getSaeHistorialvehiculoList().remove(saeHistorialvehiculo);
                hisIntDatoeventoOld = em.merge(hisIntDatoeventoOld);
            }
            if (hisIntDatoeventoNew != null && !hisIntDatoeventoNew.equals(hisIntDatoeventoOld)) {
                hisIntDatoeventoNew.getSaeHistorialvehiculoList().add(saeHistorialvehiculo);
                hisIntDatoeventoNew = em.merge(hisIntDatoeventoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = saeHistorialvehiculo.getHisIntId();
                if (findSaeHistorialvehiculo(id) == null) {
                    throw new NonexistentEntityException("The saeHistorialvehiculo with id " + id + " no longer exists.");
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
            SaeHistorialvehiculo saeHistorialvehiculo;
            try {
                saeHistorialvehiculo = em.getReference(SaeHistorialvehiculo.class, id);
                saeHistorialvehiculo.getHisIntId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The saeHistorialvehiculo with id " + id + " no longer exists.", enfe);
            }
            SaeVehiculo hisStrPlaca = saeHistorialvehiculo.getHisStrPlaca();
            if (hisStrPlaca != null) {
                hisStrPlaca.getSaeHistorialvehiculoList().remove(saeHistorialvehiculo);
                hisStrPlaca = em.merge(hisStrPlaca);
            }
            SaeEvento hisIntEvento = saeHistorialvehiculo.getHisIntEvento();
            if (hisIntEvento != null) {
                hisIntEvento.getSaeHistorialvehiculoList().remove(saeHistorialvehiculo);
                hisIntEvento = em.merge(hisIntEvento);
            }
            SaeDatoevento hisIntDatoevento = saeHistorialvehiculo.getHisIntDatoevento();
            if (hisIntDatoevento != null) {
                hisIntDatoevento.getSaeHistorialvehiculoList().remove(saeHistorialvehiculo);
                hisIntDatoevento = em.merge(hisIntDatoevento);
            }
            em.remove(saeHistorialvehiculo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SaeHistorialvehiculo> findSaeHistorialvehiculoEntities() {
        return findSaeHistorialvehiculoEntities(true, -1, -1);
    }

    public List<SaeHistorialvehiculo> findSaeHistorialvehiculoEntities(int maxResults, int firstResult) {
        return findSaeHistorialvehiculoEntities(false, maxResults, firstResult);
    }

    private List<SaeHistorialvehiculo> findSaeHistorialvehiculoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SaeHistorialvehiculo.class));
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

    public SaeHistorialvehiculo findSaeHistorialvehiculo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SaeHistorialvehiculo.class, id);
        } finally {
            em.close();
        }
    }

    public int getSaeHistorialvehiculoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SaeHistorialvehiculo> rt = cq.from(SaeHistorialvehiculo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<SaeHistorialvehiculo> obtenerVehiculosCercanosParaTaxi(String latitud, String longitud) {
        
        EntityManager em = getEntityManager();
        
       Query query = em.createNamedQuery("SaeHistorialvehiculo.generateNew");
        query.setParameter("latitud",Double.parseDouble(latitud));
        query.setParameter("longitud",Double.parseDouble(longitud));

        List<SaeHistorialvehiculo> l = query.getResultList();
        
        
        //List<SaeHistorialvehiculo> query = em.createQuery("SELECT NEW com.aquitax.domain.SaeHistorialvehiculo(s.hisIntId,s.hisStrPlaca, s.hisBooEstado) FROM SaeHistorialvehiculo s").getResultList();
//       List<SaeHistorialvehiculo> query = em.createQuery(" SELECT consulta  FROM (SELECT DISTINCT(his.hisIntId), his.hisStrPlaca, his.hisDouLongitud/1000000 longitud, his.hisDouLatitud/1000000 latitud,  " +
//"\"      his.hisStrFecha, his.hisIntRecorrido, his.hisIntEstadogps, \\n\" +\n" +
//"\"      his.hisIntDatoevento, his.hisIntEvento, his.hisBooEstado, SQRT(POWER(1 - his.hisDouLatitud/1000000, 2) + POWER(1 - his.hisDouLongitud/1000000, 2)) distancia\\n\" +\n" +
//"\"        FROM SaeHistorialvehiculo his\\n\" +\n" +
//"\"        WHERE\\n\" +\n" +
//"\"        his.hisBooEstado='TRUE'\\n\" +\n" +
//"\"        AND\\n\" +\n" +
//"\"        his.hisStrFecha=(\\n\" +\n" +
//"\"                SELECT max(hisStrFecha)\\n\" +\n" +
//"\"                FROM SaeHistorialvehiculo\\n\" +\n" +
//"\"                WHERE his.hisStrPlaca = hisStrPlaca)\\n\" +\n" +
//"\"        ) consulta\\n\" +\n" +
//"\"        ORDER BY consulta.distancia ASC\\n\" +\n" +
//"\"        limit 5\")").getResultList(); 
//                .setParameter("latitud", latitud)
//                .setParameter("longitud", longitud);
//        HashMap <Integer,Boolean> resul= new HashMap<Integer,Boolean>();
//        
//        for (SaeHistorialvehiculo entry : query.getResultList()) {
//            resul.put(entry.getHisIntId(), entry.getHisBooEstado());
//            
//        }
        
        
        for (SaeHistorialvehiculo customer : l) {
            System.out.println("" + customer.getHisIntId()+""+customer.getHisBooEstado());
            
        }
       
        return null;
    }
    /* SELECT consulta.his_int_id, consulta.his_str_placa, consulta.longitud, consulta.latitud, consulta.his_str_fecha, consulta.his_int_recorrido, consulta.his_int_estadogps, consulta.his_int_datoevento, consulta.his_int_evento, consulta.his_boo_estado
     FROM (SELECT DISTINCT(his.his_int_id), his.his_str_placa, his.his_dou_longitud/1000000 longitud, his.his_dou_latitud/1000000 latitud, his.his_str_fecha, his.his_int_recorrido, his.his_int_estadogps, his.his_int_datoevento, his.his_int_evento, his.his_boo_estado, SQRT(POWER($1-his.his_dou_latitud/1000000, 2) + POWER($2-his.his_dou_longitud/1000000, 2)) distancia
     FROM sae_historialvehiculo his
     WHERE
     his.his_boo_estado='TRUE'
     AND
     his.his_str_fecha=(
     SELECT max(his_str_fecha)
     FROM sae_historialvehiculo
     WHERE his.his_str_placa = his_str_placa)
     ) consulta
     ORDER BY consulta.distancia ASC
     limit 5
     */
}
