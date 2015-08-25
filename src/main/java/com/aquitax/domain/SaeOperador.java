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
@Table(name = "sae_operador")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SaeOperador.findAll", query = "SELECT s FROM SaeOperador s"),
    @NamedQuery(name = "SaeOperador.findByOpeIntId", query = "SELECT s FROM SaeOperador s WHERE s.opeIntId = :opeIntId"),
    @NamedQuery(name = "SaeOperador.findByOpeIntCedula", query = "SELECT s FROM SaeOperador s WHERE s.opeIntCedula = :opeIntCedula"),
    @NamedQuery(name = "SaeOperador.findByOpeStrNombre", query = "SELECT s FROM SaeOperador s WHERE s.opeStrNombre = :opeStrNombre"),
    @NamedQuery(name = "SaeOperador.findByOpeStrApellidos", query = "SELECT s FROM SaeOperador s WHERE s.opeStrApellidos = :opeStrApellidos"),
    @NamedQuery(name = "SaeOperador.findByOpeStrUsuario", query = "SELECT s FROM SaeOperador s WHERE s.opeStrUsuario = :opeStrUsuario"),
    @NamedQuery(name = "SaeOperador.findByOpeStrClave", query = "SELECT s FROM SaeOperador s WHERE s.opeStrClave = :opeStrClave"),
    @NamedQuery(name = "SaeOperador.findByOpeBooEstado", query = "SELECT s FROM SaeOperador s WHERE s.opeBooEstado = :opeBooEstado")})
public class SaeOperador implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ope_int_id")
    private Integer opeIntId;
    @Column(name = "ope_int_cedula")
    private Integer opeIntCedula;
    @Column(name = "ope_str_nombre")
    private String opeStrNombre;
    @Column(name = "ope_str_apellidos")
    private String opeStrApellidos;
    @Column(name = "ope_str_usuario")
    private String opeStrUsuario;
    @Column(name = "ope_str_clave")
    private String opeStrClave;
    @Column(name = "ope_boo_estado")
    private Boolean opeBooEstado;
    @OneToMany(mappedBy = "serIntOperadora")
    private List<SaeServicio> saeServicioList;
    @JoinColumn(name = "ope_str_empresa", referencedColumnName = "emp_str_nit")
    @ManyToOne
    private SaeEmpresa opeStrEmpresa;

    public SaeOperador() {
    }

    public SaeOperador(Integer opeIntId) {
        this.opeIntId = opeIntId;
    }

    public Integer getOpeIntId() {
        return opeIntId;
    }

    public void setOpeIntId(Integer opeIntId) {
        this.opeIntId = opeIntId;
    }

    public Integer getOpeIntCedula() {
        return opeIntCedula;
    }

    public void setOpeIntCedula(Integer opeIntCedula) {
        this.opeIntCedula = opeIntCedula;
    }

    public String getOpeStrNombre() {
        return opeStrNombre;
    }

    public void setOpeStrNombre(String opeStrNombre) {
        this.opeStrNombre = opeStrNombre;
    }

    public String getOpeStrApellidos() {
        return opeStrApellidos;
    }

    public void setOpeStrApellidos(String opeStrApellidos) {
        this.opeStrApellidos = opeStrApellidos;
    }

    public String getOpeStrUsuario() {
        return opeStrUsuario;
    }

    public void setOpeStrUsuario(String opeStrUsuario) {
        this.opeStrUsuario = opeStrUsuario;
    }

    public String getOpeStrClave() {
        return opeStrClave;
    }

    public void setOpeStrClave(String opeStrClave) {
        this.opeStrClave = opeStrClave;
    }

    public Boolean getOpeBooEstado() {
        return opeBooEstado;
    }

    public void setOpeBooEstado(Boolean opeBooEstado) {
        this.opeBooEstado = opeBooEstado;
    }

    @XmlTransient
    @JsonIgnore
    public List<SaeServicio> getSaeServicioList() {
        return saeServicioList;
    }

    public void setSaeServicioList(List<SaeServicio> saeServicioList) {
        this.saeServicioList = saeServicioList;
    }

    public SaeEmpresa getOpeStrEmpresa() {
        return opeStrEmpresa;
    }

    public void setOpeStrEmpresa(SaeEmpresa opeStrEmpresa) {
        this.opeStrEmpresa = opeStrEmpresa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (opeIntId != null ? opeIntId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SaeOperador)) {
            return false;
        }
        SaeOperador other = (SaeOperador) object;
        if ((this.opeIntId == null && other.opeIntId != null) || (this.opeIntId != null && !this.opeIntId.equals(other.opeIntId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.aquitax.domain.SaeOperador[ opeIntId=" + opeIntId + " ]";
    }
    
}
