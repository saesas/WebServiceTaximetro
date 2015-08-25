/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aquitax.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
@Table(name = "sae_conductor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SaeConductor.findAll", query = "SELECT s FROM SaeConductor s"),
    @NamedQuery(name = "SaeConductor.findByConIntCedula", query = "SELECT s FROM SaeConductor s WHERE s.conIntCedula = :conIntCedula"),
    @NamedQuery(name = "SaeConductor.findByConStrNombre", query = "SELECT s FROM SaeConductor s WHERE s.conStrNombre = :conStrNombre"),
    @NamedQuery(name = "SaeConductor.findByConStrApellidos", query = "SELECT s FROM SaeConductor s WHERE s.conStrApellidos = :conStrApellidos"),
    @NamedQuery(name = "SaeConductor.findByConIntCelular", query = "SELECT s FROM SaeConductor s WHERE s.conIntCelular = :conIntCelular"),
    @NamedQuery(name = "SaeConductor.findByConStrCorreo", query = "SELECT s FROM SaeConductor s WHERE s.conStrCorreo = :conStrCorreo"),
    @NamedQuery(name = "SaeConductor.findByConStrDireccion", query = "SELECT s FROM SaeConductor s WHERE s.conStrDireccion = :conStrDireccion"),
    @NamedQuery(name = "SaeConductor.findByConBooEstado", query = "SELECT s FROM SaeConductor s WHERE s.conBooEstado = :conBooEstado"),
    @NamedQuery(name = "SaeConductor.findByConStrRutafoto", query = "SELECT s FROM SaeConductor s WHERE s.conStrRutafoto = :conStrRutafoto")})
public class SaeConductor implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "con_int_cedula")
    private BigDecimal conIntCedula;
    @Column(name = "con_str_nombre")
    private String conStrNombre;
    @Column(name = "con_str_apellidos")
    private String conStrApellidos;
    @Column(name = "con_int_celular")
    private BigInteger conIntCelular;
    @Column(name = "con_str_correo")
    private String conStrCorreo;
    @Column(name = "con_str_direccion")
    private String conStrDireccion;
    @Column(name = "con_boo_estado")
    private Boolean conBooEstado;
    @Column(name = "con_str_rutafoto")
    private String conStrRutafoto;
    @OneToMany(mappedBy = "serIntConductor")
    private List<SaeServicio> saeServicioList;
    @OneToMany(mappedBy = "prcoIntCedulaconductor")
    private List<SaePropietarioconductor> saePropietarioconductorList;
    @OneToMany(mappedBy = "turIntConductor")
    private List<SaeTurnoconductor> saeTurnoconductorList;

    public SaeConductor() {
    }

    public SaeConductor(BigDecimal conIntCedula) {
        this.conIntCedula = conIntCedula;
    }

    public BigDecimal getConIntCedula() {
        return conIntCedula;
    }

    public void setConIntCedula(BigDecimal conIntCedula) {
        this.conIntCedula = conIntCedula;
    }

    public String getConStrNombre() {
        return conStrNombre;
    }

    public void setConStrNombre(String conStrNombre) {
        this.conStrNombre = conStrNombre;
    }

    public String getConStrApellidos() {
        return conStrApellidos;
    }

    public void setConStrApellidos(String conStrApellidos) {
        this.conStrApellidos = conStrApellidos;
    }

    public BigInteger getConIntCelular() {
        return conIntCelular;
    }

    public void setConIntCelular(BigInteger conIntCelular) {
        this.conIntCelular = conIntCelular;
    }

    public String getConStrCorreo() {
        return conStrCorreo;
    }

    public void setConStrCorreo(String conStrCorreo) {
        this.conStrCorreo = conStrCorreo;
    }

    public String getConStrDireccion() {
        return conStrDireccion;
    }

    public void setConStrDireccion(String conStrDireccion) {
        this.conStrDireccion = conStrDireccion;
    }

    public Boolean getConBooEstado() {
        return conBooEstado;
    }

    public void setConBooEstado(Boolean conBooEstado) {
        this.conBooEstado = conBooEstado;
    }

    public String getConStrRutafoto() {
        return conStrRutafoto;
    }

    public void setConStrRutafoto(String conStrRutafoto) {
        this.conStrRutafoto = conStrRutafoto;
    }

    @XmlTransient
    @JsonIgnore
    public List<SaeServicio> getSaeServicioList() {
        return saeServicioList;
    }

    public void setSaeServicioList(List<SaeServicio> saeServicioList) {
        this.saeServicioList = saeServicioList;
    }

    @XmlTransient
    @JsonIgnore
    public List<SaePropietarioconductor> getSaePropietarioconductorList() {
        return saePropietarioconductorList;
    }

    public void setSaePropietarioconductorList(List<SaePropietarioconductor> saePropietarioconductorList) {
        this.saePropietarioconductorList = saePropietarioconductorList;
    }

    @XmlTransient
    @JsonIgnore
    public List<SaeTurnoconductor> getSaeTurnoconductorList() {
        return saeTurnoconductorList;
    }

    public void setSaeTurnoconductorList(List<SaeTurnoconductor> saeTurnoconductorList) {
        this.saeTurnoconductorList = saeTurnoconductorList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (conIntCedula != null ? conIntCedula.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SaeConductor)) {
            return false;
        }
        SaeConductor other = (SaeConductor) object;
        if ((this.conIntCedula == null && other.conIntCedula != null) || (this.conIntCedula != null && !this.conIntCedula.equals(other.conIntCedula))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.aquitax.domain.SaeConductor[ conIntCedula=" + conIntCedula + " ]";
    }
    
}
