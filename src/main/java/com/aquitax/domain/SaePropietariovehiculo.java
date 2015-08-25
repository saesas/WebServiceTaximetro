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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "sae_propietariovehiculo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SaePropietariovehiculo.findAll", query = "SELECT s FROM SaePropietariovehiculo s"),
    @NamedQuery(name = "SaePropietariovehiculo.findByProIntCedula", query = "SELECT s FROM SaePropietariovehiculo s WHERE s.proIntCedula = :proIntCedula"),
    @NamedQuery(name = "SaePropietariovehiculo.findByProStrNombre", query = "SELECT s FROM SaePropietariovehiculo s WHERE s.proStrNombre = :proStrNombre"),
    @NamedQuery(name = "SaePropietariovehiculo.findByProStrApellidos", query = "SELECT s FROM SaePropietariovehiculo s WHERE s.proStrApellidos = :proStrApellidos"),
    @NamedQuery(name = "SaePropietariovehiculo.findByProIntCelular", query = "SELECT s FROM SaePropietariovehiculo s WHERE s.proIntCelular = :proIntCelular"),
    @NamedQuery(name = "SaePropietariovehiculo.findByProStrCorreo", query = "SELECT s FROM SaePropietariovehiculo s WHERE s.proStrCorreo = :proStrCorreo"),
    @NamedQuery(name = "SaePropietariovehiculo.findByProStrDireccion", query = "SELECT s FROM SaePropietariovehiculo s WHERE s.proStrDireccion = :proStrDireccion"),
    @NamedQuery(name = "SaePropietariovehiculo.findByProBooEstado", query = "SELECT s FROM SaePropietariovehiculo s WHERE s.proBooEstado = :proBooEstado")})
public class SaePropietariovehiculo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "pro_int_cedula")
    private Integer proIntCedula;
    @Column(name = "pro_str_nombre")
    private String proStrNombre;
    @Column(name = "pro_str_apellidos")
    private String proStrApellidos;
    @Column(name = "pro_int_celular")
    private Integer proIntCelular;
    @Column(name = "pro_str_correo")
    private String proStrCorreo;
    @Column(name = "pro_str_direccion")
    private String proStrDireccion;
    @Column(name = "pro_boo_estado")
    private Boolean proBooEstado;
    @JoinColumn(name = "pro_str_empresa", referencedColumnName = "emp_str_nit")
    @ManyToOne
    private SaeEmpresa proStrEmpresa;
    @OneToMany(mappedBy = "prcoIntCedulapropietario")
    private List<SaePropietarioconductor> saePropietarioconductorList;
    @OneToMany(mappedBy = "vehIntPropietario")
    private List<SaeVehiculo> saeVehiculoList;

    public SaePropietariovehiculo() {
    }

    public SaePropietariovehiculo(Integer proIntCedula) {
        this.proIntCedula = proIntCedula;
    }

    public Integer getProIntCedula() {
        return proIntCedula;
    }

    public void setProIntCedula(Integer proIntCedula) {
        this.proIntCedula = proIntCedula;
    }

    public String getProStrNombre() {
        return proStrNombre;
    }

    public void setProStrNombre(String proStrNombre) {
        this.proStrNombre = proStrNombre;
    }

    public String getProStrApellidos() {
        return proStrApellidos;
    }

    public void setProStrApellidos(String proStrApellidos) {
        this.proStrApellidos = proStrApellidos;
    }

    public Integer getProIntCelular() {
        return proIntCelular;
    }

    public void setProIntCelular(Integer proIntCelular) {
        this.proIntCelular = proIntCelular;
    }

    public String getProStrCorreo() {
        return proStrCorreo;
    }

    public void setProStrCorreo(String proStrCorreo) {
        this.proStrCorreo = proStrCorreo;
    }

    public String getProStrDireccion() {
        return proStrDireccion;
    }

    public void setProStrDireccion(String proStrDireccion) {
        this.proStrDireccion = proStrDireccion;
    }

    public Boolean getProBooEstado() {
        return proBooEstado;
    }

    public void setProBooEstado(Boolean proBooEstado) {
        this.proBooEstado = proBooEstado;
    }

    public SaeEmpresa getProStrEmpresa() {
        return proStrEmpresa;
    }

    public void setProStrEmpresa(SaeEmpresa proStrEmpresa) {
        this.proStrEmpresa = proStrEmpresa;
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
    public List<SaeVehiculo> getSaeVehiculoList() {
        return saeVehiculoList;
    }

    public void setSaeVehiculoList(List<SaeVehiculo> saeVehiculoList) {
        this.saeVehiculoList = saeVehiculoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (proIntCedula != null ? proIntCedula.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SaePropietariovehiculo)) {
            return false;
        }
        SaePropietariovehiculo other = (SaePropietariovehiculo) object;
        if ((this.proIntCedula == null && other.proIntCedula != null) || (this.proIntCedula != null && !this.proIntCedula.equals(other.proIntCedula))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.aquitax.domain.SaePropietariovehiculo[ proIntCedula=" + proIntCedula + " ]";
    }
    
}
