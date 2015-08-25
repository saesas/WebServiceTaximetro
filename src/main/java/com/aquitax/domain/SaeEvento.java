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
@Table(name = "sae_evento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SaeEvento.findAll", query = "SELECT s FROM SaeEvento s"),
    @NamedQuery(name = "SaeEvento.findByEveIntId", query = "SELECT s FROM SaeEvento s WHERE s.eveIntId = :eveIntId"),
    @NamedQuery(name = "SaeEvento.findByEveSrtDescripcion", query = "SELECT s FROM SaeEvento s WHERE s.eveSrtDescripcion = :eveSrtDescripcion"),
    @NamedQuery(name = "SaeEvento.findByEveBooEstado", query = "SELECT s FROM SaeEvento s WHERE s.eveBooEstado = :eveBooEstado")})
public class SaeEvento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "eve_int_id")
    private Integer eveIntId;
    @Column(name = "eve_srt_descripcion")
    private String eveSrtDescripcion;
    @Column(name = "eve_boo_estado")
    private Boolean eveBooEstado;
    @OneToMany(mappedBy = "hisIntEvento")
    private List<SaeHistorialvehiculo> saeHistorialvehiculoList;

    public SaeEvento() {
    }

    public SaeEvento(Integer eveIntId) {
        this.eveIntId = eveIntId;
    }

    public Integer getEveIntId() {
        return eveIntId;
    }

    public void setEveIntId(Integer eveIntId) {
        this.eveIntId = eveIntId;
    }

    public String getEveSrtDescripcion() {
        return eveSrtDescripcion;
    }

    public void setEveSrtDescripcion(String eveSrtDescripcion) {
        this.eveSrtDescripcion = eveSrtDescripcion;
    }

    public Boolean getEveBooEstado() {
        return eveBooEstado;
    }

    public void setEveBooEstado(Boolean eveBooEstado) {
        this.eveBooEstado = eveBooEstado;
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
        hash += (eveIntId != null ? eveIntId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SaeEvento)) {
            return false;
        }
        SaeEvento other = (SaeEvento) object;
        if ((this.eveIntId == null && other.eveIntId != null) || (this.eveIntId != null && !this.eveIntId.equals(other.eveIntId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.aquitax.domain.SaeEvento[ eveIntId=" + eveIntId + " ]";
    }
    
}
