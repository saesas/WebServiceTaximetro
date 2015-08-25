/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aquitax.domain;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "sae_usuariovehiculo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SaeUsuariovehiculo.findAll", query = "SELECT s FROM SaeUsuariovehiculo s"),
    @NamedQuery(name = "SaeUsuariovehiculo.findByUsuIntTelefono", query = "SELECT s FROM SaeUsuariovehiculo s WHERE s.usuIntTelefono = :usuIntTelefono"),
    @NamedQuery(name = "SaeUsuariovehiculo.findByUsuStrNombre", query = "SELECT s FROM SaeUsuariovehiculo s WHERE s.usuStrNombre = :usuStrNombre"),
    @NamedQuery(name = "SaeUsuariovehiculo.findByUsuBooEstado", query = "SELECT s FROM SaeUsuariovehiculo s WHERE s.usuBooEstado = :usuBooEstado")})
public class SaeUsuariovehiculo implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "usu_int_telefono")
    private BigDecimal usuIntTelefono;
    @Column(name = "usu_str_nombre")
    private String usuStrNombre;
    @Column(name = "usu_boo_estado")
    private Boolean usuBooEstado;
    @OneToMany(mappedBy = "serIntTelefono")
    private List<SaeServicio> saeServicioList;

    public SaeUsuariovehiculo() {
    }

    public SaeUsuariovehiculo(BigDecimal usuIntTelefono) {
        this.usuIntTelefono = usuIntTelefono;
    }

    public BigDecimal getUsuIntTelefono() {
        return usuIntTelefono;
    }

    public void setUsuIntTelefono(BigDecimal usuIntTelefono) {
        this.usuIntTelefono = usuIntTelefono;
    }

    public String getUsuStrNombre() {
        return usuStrNombre;
    }

    public void setUsuStrNombre(String usuStrNombre) {
        this.usuStrNombre = usuStrNombre;
    }

    public Boolean getUsuBooEstado() {
        return usuBooEstado;
    }

    public void setUsuBooEstado(Boolean usuBooEstado) {
        this.usuBooEstado = usuBooEstado;
    }

    @XmlTransient
    @JsonIgnore
    public List<SaeServicio> getSaeServicioList() {
        return saeServicioList;
    }

    public void setSaeServicioList(List<SaeServicio> saeServicioList) {
        this.saeServicioList = saeServicioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuIntTelefono != null ? usuIntTelefono.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SaeUsuariovehiculo)) {
            return false;
        }
        SaeUsuariovehiculo other = (SaeUsuariovehiculo) object;
        if ((this.usuIntTelefono == null && other.usuIntTelefono != null) || (this.usuIntTelefono != null && !this.usuIntTelefono.equals(other.usuIntTelefono))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.aquitax.domain.SaeUsuariovehiculo[ usuIntTelefono=" + usuIntTelefono + " ]";
    }
    
}
