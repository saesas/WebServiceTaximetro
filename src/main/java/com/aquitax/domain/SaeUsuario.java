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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author SAE2
 */
@Entity
@Table(name = "sae_usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SaeUsuario.findAll", query = "SELECT s FROM SaeUsuario s"),
    @NamedQuery(name = "SaeUsuario.findByUsuStrNombre", query = "SELECT s FROM SaeUsuario s WHERE s.usuStrNombre = :usuStrNombre"),
    @NamedQuery(name = "SaeUsuario.findByUsuStrClave", query = "SELECT s FROM SaeUsuario s WHERE s.usuStrClave = :usuStrClave"),
    @NamedQuery(name = "SaeUsuario.findByUsuStrPerfil", query = "SELECT s FROM SaeUsuario s WHERE s.usuStrPerfil = :usuStrPerfil")})
public class SaeUsuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "usu_str_nombre")
    private String usuStrNombre;
    @Column(name = "usu_str_clave")
    private String usuStrClave;
    @Column(name = "usu_str_perfil")
    private String usuStrPerfil;

    public SaeUsuario() {
    }

    public SaeUsuario(String usuStrNombre) {
        this.usuStrNombre = usuStrNombre;
    }

    public String getUsuStrNombre() {
        return usuStrNombre;
    }

    public void setUsuStrNombre(String usuStrNombre) {
        this.usuStrNombre = usuStrNombre;
    }

    public String getUsuStrClave() {
        return usuStrClave;
    }

    public void setUsuStrClave(String usuStrClave) {
        this.usuStrClave = usuStrClave;
    }

    public String getUsuStrPerfil() {
        return usuStrPerfil;
    }

    public void setUsuStrPerfil(String usuStrPerfil) {
        this.usuStrPerfil = usuStrPerfil;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuStrNombre != null ? usuStrNombre.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SaeUsuario)) {
            return false;
        }
        SaeUsuario other = (SaeUsuario) object;
        if ((this.usuStrNombre == null && other.usuStrNombre != null) || (this.usuStrNombre != null && !this.usuStrNombre.equals(other.usuStrNombre))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.aquitax.domain.SaeUsuario[ usuStrNombre=" + usuStrNombre + " ]";
    }
    
}
