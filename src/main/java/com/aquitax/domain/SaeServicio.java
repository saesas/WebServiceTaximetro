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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author SAE2
 */
@Entity
@Table(name = "sae_servicio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SaeServicio.findAll", query = "SELECT s FROM SaeServicio s"),
    @NamedQuery(name = "SaeServicio.findBySerIntId", query = "SELECT s FROM SaeServicio s WHERE s.serIntId = :serIntId"),
    @NamedQuery(name = "SaeServicio.findBySerStrFechainicio", query = "SELECT s FROM SaeServicio s WHERE s.serStrFechainicio = :serStrFechainicio"),
    @NamedQuery(name = "SaeServicio.findBySerStrFechafin", query = "SELECT s FROM SaeServicio s WHERE s.serStrFechafin = :serStrFechafin"),
    @NamedQuery(name = "SaeServicio.findBySerDouLongitudorigen", query = "SELECT s FROM SaeServicio s WHERE s.serDouLongitudorigen = :serDouLongitudorigen"),
    @NamedQuery(name = "SaeServicio.findBySerDouLatitudorigen", query = "SELECT s FROM SaeServicio s WHERE s.serDouLatitudorigen = :serDouLatitudorigen"),
    @NamedQuery(name = "SaeServicio.findBySerDouLongituddestino", query = "SELECT s FROM SaeServicio s WHERE s.serDouLongituddestino = :serDouLongituddestino"),
    @NamedQuery(name = "SaeServicio.findBySerDouLatituddestino", query = "SELECT s FROM SaeServicio s WHERE s.serDouLatituddestino = :serDouLatituddestino"),
    @NamedQuery(name = "SaeServicio.findBySerIntTiemposervicio", query = "SELECT s FROM SaeServicio s WHERE s.serIntTiemposervicio = :serIntTiemposervicio"),
    @NamedQuery(name = "SaeServicio.findBySerIntCosto", query = "SELECT s FROM SaeServicio s WHERE s.serIntCosto = :serIntCosto"),
    @NamedQuery(name = "SaeServicio.findBySerIntRecorrido", query = "SELECT s FROM SaeServicio s WHERE s.serIntRecorrido = :serIntRecorrido"),
    @NamedQuery(name = "SaeServicio.findBySerStrEstadoservicio", query = "SELECT s FROM SaeServicio s WHERE s.serStrEstadoservicio = :serStrEstadoservicio"),
    @NamedQuery(name = "SaeServicio.findBySerBooEstado", query = "SELECT s FROM SaeServicio s WHERE s.serBooEstado = :serBooEstado")})
public class SaeServicio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ser_int_id")
    private Integer serIntId;
    @Column(name = "ser_str_fechainicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date serStrFechainicio;
    @Column(name = "ser_str_fechafin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date serStrFechafin;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ser_dou_longitudorigen")
    private Double serDouLongitudorigen;
    @Column(name = "ser_dou_latitudorigen")
    private Double serDouLatitudorigen;
    @Column(name = "ser_dou_longituddestino")
    private Double serDouLongituddestino;
    @Column(name = "ser_dou_latituddestino")
    private Double serDouLatituddestino;
    @Column(name = "ser_int_tiemposervicio")
    private Integer serIntTiemposervicio;
    @Column(name = "ser_int_costo")
    private Integer serIntCosto;
    @Column(name = "ser_int_recorrido")
    private Integer serIntRecorrido;
    @Column(name = "ser_str_estadoservicio")
    private String serStrEstadoservicio;
    @Column(name = "ser_boo_estado")
    private Boolean serBooEstado;
    @JoinColumn(name = "ser_str_placa", referencedColumnName = "veh_str_placa")
    @ManyToOne
    private SaeVehiculo serStrPlaca;
    @JoinColumn(name = "ser_int_telefono", referencedColumnName = "usu_int_telefono")
    @ManyToOne
    private SaeUsuariovehiculo serIntTelefono;
    @JoinColumn(name = "ser_int_operadora", referencedColumnName = "ope_int_id")
    @ManyToOne
    private SaeOperador serIntOperadora;
    @JoinColumn(name = "ser_int_conductor", referencedColumnName = "con_int_cedula")
    @ManyToOne
    private SaeConductor serIntConductor;

    public SaeServicio() {
    }

    public SaeServicio(Integer serIntId) {
        this.serIntId = serIntId;
    }

    public Integer getSerIntId() {
        return serIntId;
    }

    public void setSerIntId(Integer serIntId) {
        this.serIntId = serIntId;
    }

    public Date getSerStrFechainicio() {
        return serStrFechainicio;
    }

    public void setSerStrFechainicio(Date serStrFechainicio) {
        this.serStrFechainicio = serStrFechainicio;
    }

    public Date getSerStrFechafin() {
        return serStrFechafin;
    }

    public void setSerStrFechafin(Date serStrFechafin) {
        this.serStrFechafin = serStrFechafin;
    }

    public Double getSerDouLongitudorigen() {
        return serDouLongitudorigen;
    }

    public void setSerDouLongitudorigen(Double serDouLongitudorigen) {
        this.serDouLongitudorigen = serDouLongitudorigen;
    }

    public Double getSerDouLatitudorigen() {
        return serDouLatitudorigen;
    }

    public void setSerDouLatitudorigen(Double serDouLatitudorigen) {
        this.serDouLatitudorigen = serDouLatitudorigen;
    }

    public Double getSerDouLongituddestino() {
        return serDouLongituddestino;
    }

    public void setSerDouLongituddestino(Double serDouLongituddestino) {
        this.serDouLongituddestino = serDouLongituddestino;
    }

    public Double getSerDouLatituddestino() {
        return serDouLatituddestino;
    }

    public void setSerDouLatituddestino(Double serDouLatituddestino) {
        this.serDouLatituddestino = serDouLatituddestino;
    }

    public Integer getSerIntTiemposervicio() {
        return serIntTiemposervicio;
    }

    public void setSerIntTiemposervicio(Integer serIntTiemposervicio) {
        this.serIntTiemposervicio = serIntTiemposervicio;
    }

    public Integer getSerIntCosto() {
        return serIntCosto;
    }

    public void setSerIntCosto(Integer serIntCosto) {
        this.serIntCosto = serIntCosto;
    }

    public Integer getSerIntRecorrido() {
        return serIntRecorrido;
    }

    public void setSerIntRecorrido(Integer serIntRecorrido) {
        this.serIntRecorrido = serIntRecorrido;
    }

    public String getSerStrEstadoservicio() {
        return serStrEstadoservicio;
    }

    public void setSerStrEstadoservicio(String serStrEstadoservicio) {
        this.serStrEstadoservicio = serStrEstadoservicio;
    }

    public Boolean getSerBooEstado() {
        return serBooEstado;
    }

    public void setSerBooEstado(Boolean serBooEstado) {
        this.serBooEstado = serBooEstado;
    }

    public SaeVehiculo getSerStrPlaca() {
        return serStrPlaca;
    }

    public void setSerStrPlaca(SaeVehiculo serStrPlaca) {
        this.serStrPlaca = serStrPlaca;
    }

    public SaeUsuariovehiculo getSerIntTelefono() {
        return serIntTelefono;
    }

    public void setSerIntTelefono(SaeUsuariovehiculo serIntTelefono) {
        this.serIntTelefono = serIntTelefono;
    }

    public SaeOperador getSerIntOperadora() {
        return serIntOperadora;
    }

    public void setSerIntOperadora(SaeOperador serIntOperadora) {
        this.serIntOperadora = serIntOperadora;
    }

    public SaeConductor getSerIntConductor() {
        return serIntConductor;
    }

    public void setSerIntConductor(SaeConductor serIntConductor) {
        this.serIntConductor = serIntConductor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (serIntId != null ? serIntId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SaeServicio)) {
            return false;
        }
        SaeServicio other = (SaeServicio) object;
        if ((this.serIntId == null && other.serIntId != null) || (this.serIntId != null && !this.serIntId.equals(other.serIntId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.aquitax.domain.SaeServicio[ serIntId=" + serIntId + " ]";
    }
    
}
