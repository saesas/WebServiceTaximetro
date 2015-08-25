/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aquitax.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author SAE2
 */
@Entity
@Table(name = "sae_historialvehiculo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SaeHistorialvehiculo.findAll", query = "SELECT s FROM SaeHistorialvehiculo s"),
    @NamedQuery(name = "SaeHistorialvehiculo.findByHisIntId", query = "SELECT s FROM SaeHistorialvehiculo s WHERE s.hisIntId = :hisIntId"),
    @NamedQuery(name = "SaeHistorialvehiculo.findByHisDouLongitud", query = "SELECT s FROM SaeHistorialvehiculo s WHERE s.hisDouLongitud = :hisDouLongitud"),
    @NamedQuery(name = "SaeHistorialvehiculo.findByHisDouLatitud", query = "SELECT s FROM SaeHistorialvehiculo s WHERE s.hisDouLatitud = :hisDouLatitud"),
    @NamedQuery(name = "SaeHistorialvehiculo.findByHisStrFecha", query = "SELECT s FROM SaeHistorialvehiculo s WHERE s.hisStrFecha = :hisStrFecha"),
    @NamedQuery(name = "SaeHistorialvehiculo.findByHisIntRecorrido", query = "SELECT s FROM SaeHistorialvehiculo s WHERE s.hisIntRecorrido = :hisIntRecorrido"),
    @NamedQuery(name = "SaeHistorialvehiculo.findByHisIntEstadogps", query = "SELECT s FROM SaeHistorialvehiculo s WHERE s.hisIntEstadogps = :hisIntEstadogps"),
    @NamedQuery(name = "SaeHistorialvehiculo.findByHisBooEstado", query = "SELECT s FROM SaeHistorialvehiculo s WHERE s.hisBooEstado = :hisBooEstado")
    //@NamedQuery(name = "findAllVehiculosCercanosParaTaxi", query = "SELECT NEW com.aquitax.domain.SaeHistorialvehiculo(s.hisIntId, s.hisBooEstado) FROM SaeHistorialvehiculo s")    
    //@NamedQuery(name = "findAllVehiculosCercanosParaTaxi", query = "SELECT NEW com.aquitax.domain.SaeHistorialvehiculo(s.hisIntId, s.hisBooEstado) FROM SaeHistorialvehiculo s")    
})

//result-type mapping used by native-query call
@SqlResultSetMapping(name = "SaeHistorialvehiculo.implicit", entities = {
        @EntityResult(entityClass = SaeHistorialvehiculo.class)
    }
)
@NamedNativeQueries({
        @NamedNativeQuery(name = "SaeHistorialvehiculo.generateNew",
                query = "select * from obtener_vehiculos_cercanos_para_taxi (:latitud, :longitud)",
                resultSetMapping = "SaeHistorialvehiculo.implicit")
    }
)

public class SaeHistorialvehiculo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "his_int_id")
    private Integer hisIntId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "his_dou_longitud")
    private Double hisDouLongitud;
    @Column(name = "his_dou_latitud")
    private Double hisDouLatitud;
    @Column(name = "his_str_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date hisStrFecha;
    @Column(name = "his_int_recorrido")
    private Integer hisIntRecorrido;
    @Column(name = "his_int_estadogps")
    private Integer hisIntEstadogps;
    @Column(name = "his_boo_estado")
    private Boolean hisBooEstado;
    @JoinColumn(name = "his_str_placa", referencedColumnName = "veh_str_placa")
    @ManyToOne
    private SaeVehiculo hisStrPlaca;
    @JoinColumn(name = "his_int_evento", referencedColumnName = "eve_int_id")
    @ManyToOne
    private SaeEvento hisIntEvento;
    @JoinColumn(name = "his_int_datoevento", referencedColumnName = "dat_int_id")
    @ManyToOne
    private SaeDatoevento hisIntDatoevento;

    public SaeHistorialvehiculo() {
    }
    
   
    public SaeHistorialvehiculo(int id,SaeVehiculo placa, Boolean estado) {
        this.hisIntId = id;
        this.hisStrPlaca = placa;
        this.hisBooEstado = estado;
    }

    public SaeHistorialvehiculo(Integer hisIntId) {
        this.hisIntId = hisIntId;
    }

    public Integer getHisIntId() {
        return hisIntId;
    }

    public void setHisIntId(Integer hisIntId) {
        this.hisIntId = hisIntId;
    }

    public Double getHisDouLongitud() {
        return hisDouLongitud;
    }

    public void setHisDouLongitud(Double hisDouLongitud) {
        this.hisDouLongitud = hisDouLongitud;
    }

    public Double getHisDouLatitud() {
        return hisDouLatitud;
    }

    public void setHisDouLatitud(Double hisDouLatitud) {
        this.hisDouLatitud = hisDouLatitud;
    }

    public Date getHisStrFecha() {
        return hisStrFecha;
    }

    public void setHisStrFecha(Date hisStrFecha) {
        this.hisStrFecha = hisStrFecha;
    }

    public Integer getHisIntRecorrido() {
        return hisIntRecorrido;
    }

    public void setHisIntRecorrido(Integer hisIntRecorrido) {
        this.hisIntRecorrido = hisIntRecorrido;
    }

    public Integer getHisIntEstadogps() {
        return hisIntEstadogps;
    }

    public void setHisIntEstadogps(Integer hisIntEstadogps) {
        this.hisIntEstadogps = hisIntEstadogps;
    }

    public Boolean getHisBooEstado() {
        return hisBooEstado;
    }

    public void setHisBooEstado(Boolean hisBooEstado) {
        this.hisBooEstado = hisBooEstado;
    }

    public SaeVehiculo getHisStrPlaca() {
        return hisStrPlaca;
    }

    public void setHisStrPlaca(SaeVehiculo hisStrPlaca) {
        this.hisStrPlaca = hisStrPlaca;
    }

    public SaeEvento getHisIntEvento() {
        return hisIntEvento;
    }

    public void setHisIntEvento(SaeEvento hisIntEvento) {
        this.hisIntEvento = hisIntEvento;
    }

    public SaeDatoevento getHisIntDatoevento() {
        return hisIntDatoevento;
    }

    public void setHisIntDatoevento(SaeDatoevento hisIntDatoevento) {
        this.hisIntDatoevento = hisIntDatoevento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hisIntId != null ? hisIntId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SaeHistorialvehiculo)) {
            return false;
        }
        SaeHistorialvehiculo other = (SaeHistorialvehiculo) object;
        if ((this.hisIntId == null && other.hisIntId != null) || (this.hisIntId != null && !this.hisIntId.equals(other.hisIntId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.aquitax.domain.SaeHistorialvehiculo[ hisIntId=" + hisIntId + " ]";
    }
    
}
