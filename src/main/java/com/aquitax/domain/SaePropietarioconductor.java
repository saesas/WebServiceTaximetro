/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aquitax.domain;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author SAE2
 */
@Entity
@Table(name = "sae_propietarioconductor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SaePropietarioconductor.findAll", query = "SELECT s FROM SaePropietarioconductor s"),
    @NamedQuery(name = "SaePropietarioconductor.findByPrcoBooEstado", query = "SELECT s FROM SaePropietarioconductor s WHERE s.prcoBooEstado = :prcoBooEstado"),
    @NamedQuery(name = "SaePropietarioconductor.findByPrcoIntId", query = "SELECT s FROM SaePropietarioconductor s WHERE s.prcoIntId = :prcoIntId")})
public class SaePropietarioconductor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "prco_boo_estado")
    private Boolean prcoBooEstado;
    @Id
    @Basic(optional = false)
    @Column(name = "prco_int_id")
    private Integer prcoIntId;
    @JoinColumn(name = "prco_int_cedulapropietario", referencedColumnName = "pro_int_cedula")
    @ManyToOne
    private SaePropietariovehiculo prcoIntCedulapropietario;
    @JoinColumn(name = "prco_int_cedulaconductor", referencedColumnName = "con_int_cedula")
    @ManyToOne
    private SaeConductor prcoIntCedulaconductor;

    public SaePropietarioconductor() {
    }

    public SaePropietarioconductor(Integer prcoIntId) {
        this.prcoIntId = prcoIntId;
    }

    public Boolean getPrcoBooEstado() {
        return prcoBooEstado;
    }

    public void setPrcoBooEstado(Boolean prcoBooEstado) {
        this.prcoBooEstado = prcoBooEstado;
    }

    public Integer getPrcoIntId() {
        return prcoIntId;
    }

    public void setPrcoIntId(Integer prcoIntId) {
        this.prcoIntId = prcoIntId;
    }

    public SaePropietariovehiculo getPrcoIntCedulapropietario() {
        return prcoIntCedulapropietario;
    }

    public void setPrcoIntCedulapropietario(SaePropietariovehiculo prcoIntCedulapropietario) {
        this.prcoIntCedulapropietario = prcoIntCedulapropietario;
    }

    public SaeConductor getPrcoIntCedulaconductor() {
        return prcoIntCedulaconductor;
    }

    public void setPrcoIntCedulaconductor(SaeConductor prcoIntCedulaconductor) {
        this.prcoIntCedulaconductor = prcoIntCedulaconductor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (prcoIntId != null ? prcoIntId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SaePropietarioconductor)) {
            return false;
        }
        SaePropietarioconductor other = (SaePropietarioconductor) object;
        if ((this.prcoIntId == null && other.prcoIntId != null) || (this.prcoIntId != null && !this.prcoIntId.equals(other.prcoIntId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.aquitax.domain.SaePropietarioconductor[ prcoIntId=" + prcoIntId + " ]";
    }
    
}
