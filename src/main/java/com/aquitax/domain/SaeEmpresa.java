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
@Table(name = "sae_empresa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SaeEmpresa.findAll", query = "SELECT s FROM SaeEmpresa s"),
    @NamedQuery(name = "SaeEmpresa.findByEmpStrNit", query = "SELECT s FROM SaeEmpresa s WHERE s.empStrNit = :empStrNit"),
    @NamedQuery(name = "SaeEmpresa.findByEmpStrNombre", query = "SELECT s FROM SaeEmpresa s WHERE s.empStrNombre = :empStrNombre"),
    @NamedQuery(name = "SaeEmpresa.findByEmpStrDireccion", query = "SELECT s FROM SaeEmpresa s WHERE s.empStrDireccion = :empStrDireccion"),
    @NamedQuery(name = "SaeEmpresa.findByEmpStrNombrecontacto", query = "SELECT s FROM SaeEmpresa s WHERE s.empStrNombrecontacto = :empStrNombrecontacto"),
    @NamedQuery(name = "SaeEmpresa.findByEmpStrCorreocontacto", query = "SELECT s FROM SaeEmpresa s WHERE s.empStrCorreocontacto = :empStrCorreocontacto"),
    @NamedQuery(name = "SaeEmpresa.findByEmpIntTelefono", query = "SELECT s FROM SaeEmpresa s WHERE s.empIntTelefono = :empIntTelefono"),
    @NamedQuery(name = "SaeEmpresa.findByEmpBooEstado", query = "SELECT s FROM SaeEmpresa s WHERE s.empBooEstado = :empBooEstado")})
public class SaeEmpresa implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "emp_str_nit")
    private String empStrNit;
    @Column(name = "emp_str_nombre")
    private String empStrNombre;
    @Column(name = "emp_str_direccion")
    private String empStrDireccion;
    @Column(name = "emp_str_nombrecontacto")
    private String empStrNombrecontacto;
    @Column(name = "emp_str_correocontacto")
    private String empStrCorreocontacto;
    @Column(name = "emp_int_telefono")
    private Integer empIntTelefono;
    @Column(name = "emp_boo_estado")
    private Boolean empBooEstado;
    @OneToMany(mappedBy = "opeStrEmpresa")
    private List<SaeOperador> saeOperadorList;
    @OneToMany(mappedBy = "proStrEmpresa")
    private List<SaePropietariovehiculo> saePropietariovehiculoList;

    public SaeEmpresa() {
    }

    public SaeEmpresa(String empStrNit) {
        this.empStrNit = empStrNit;
    }

    public String getEmpStrNit() {
        return empStrNit;
    }

    public void setEmpStrNit(String empStrNit) {
        this.empStrNit = empStrNit;
    }

    public String getEmpStrNombre() {
        return empStrNombre;
    }

    public void setEmpStrNombre(String empStrNombre) {
        this.empStrNombre = empStrNombre;
    }

    public String getEmpStrDireccion() {
        return empStrDireccion;
    }

    public void setEmpStrDireccion(String empStrDireccion) {
        this.empStrDireccion = empStrDireccion;
    }

    public String getEmpStrNombrecontacto() {
        return empStrNombrecontacto;
    }

    public void setEmpStrNombrecontacto(String empStrNombrecontacto) {
        this.empStrNombrecontacto = empStrNombrecontacto;
    }

    public String getEmpStrCorreocontacto() {
        return empStrCorreocontacto;
    }

    public void setEmpStrCorreocontacto(String empStrCorreocontacto) {
        this.empStrCorreocontacto = empStrCorreocontacto;
    }

    public Integer getEmpIntTelefono() {
        return empIntTelefono;
    }

    public void setEmpIntTelefono(Integer empIntTelefono) {
        this.empIntTelefono = empIntTelefono;
    }

    public Boolean getEmpBooEstado() {
        return empBooEstado;
    }

    public void setEmpBooEstado(Boolean empBooEstado) {
        this.empBooEstado = empBooEstado;
    }

    @XmlTransient
    @JsonIgnore
    public List<SaeOperador> getSaeOperadorList() {
        return saeOperadorList;
    }

    public void setSaeOperadorList(List<SaeOperador> saeOperadorList) {
        this.saeOperadorList = saeOperadorList;
    }

    @XmlTransient
    @JsonIgnore
    public List<SaePropietariovehiculo> getSaePropietariovehiculoList() {
        return saePropietariovehiculoList;
    }

    public void setSaePropietariovehiculoList(List<SaePropietariovehiculo> saePropietariovehiculoList) {
        this.saePropietariovehiculoList = saePropietariovehiculoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (empStrNit != null ? empStrNit.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SaeEmpresa)) {
            return false;
        }
        SaeEmpresa other = (SaeEmpresa) object;
        if ((this.empStrNit == null && other.empStrNit != null) || (this.empStrNit != null && !this.empStrNit.equals(other.empStrNit))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.aquitax.domain.SaeEmpresa[ empStrNit=" + empStrNit + " ]";
    }
    
}
