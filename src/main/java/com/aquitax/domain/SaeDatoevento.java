/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aquitax.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author SAE2
 */
@Entity
@Table(name = "sae_datoevento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SaeDatoevento.findAll", query = "SELECT s FROM SaeDatoevento s"),
    @NamedQuery(name = "SaeDatoevento.findByDatIntId", query = "SELECT s FROM SaeDatoevento s WHERE s.datIntId = :datIntId"),
    @NamedQuery(name = "SaeDatoevento.findByDatStrDescripcion", query = "SELECT s FROM SaeDatoevento s WHERE s.datStrDescripcion = :datStrDescripcion"),
    @NamedQuery(name = "SaeDatoevento.findByDatBooEstado", query = "SELECT s FROM SaeDatoevento s WHERE s.datBooEstado = :datBooEstado")})
public class SaeDatoevento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "dat_int_id")
    private Integer datIntId;
    @Column(name = "dat_str_descripcion")
    private String datStrDescripcion;
    @Column(name = "dat_boo_estado")
    private Boolean datBooEstado;
    @OneToMany(mappedBy = "hisIntDatoevento")
    private List<SaeHistorialvehiculo> saeHistorialvehiculoList;

    public SaeDatoevento() {
    }

    public SaeDatoevento(Integer datIntId) {
        this.datIntId = datIntId;
    }

    public Integer getDatIntId() {
        return datIntId;
    }

    public void setDatIntId(Integer datIntId) {
        this.datIntId = datIntId;
    }

    public String getDatStrDescripcion() {
        return datStrDescripcion;
    }

    public void setDatStrDescripcion(String datStrDescripcion) {
        this.datStrDescripcion = datStrDescripcion;
    }

    public Boolean getDatBooEstado() {
        return datBooEstado;
    }

    public void setDatBooEstado(Boolean datBooEstado) {
        this.datBooEstado = datBooEstado;
    }

    @XmlTransient
    @JsonIgnore
    public List<SaeHistorialvehiculo> getSaeHistorialvehiculoList() {
        return saeHistorialvehiculoList;
    }

    public void setSaeHistorialvehiculoList(List<SaeHistorialvehiculo> saeHistorialvehiculoList) {
        this.saeHistorialvehiculoList = saeHistorialvehiculoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (datIntId != null ? datIntId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SaeDatoevento)) {
            return false;
        }
        SaeDatoevento other = (SaeDatoevento) object;
        if ((this.datIntId == null && other.datIntId != null) || (this.datIntId != null && !this.datIntId.equals(other.datIntId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.aquitax.domain.SaeDatoevento[ datIntId=" + datIntId + " ]";
    }
    
}
