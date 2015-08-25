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
@Table(name = "sae_vehiculo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SaeVehiculo.findAll", query = "SELECT s FROM SaeVehiculo s"),
    @NamedQuery(name = "SaeVehiculo.findByVehStrPlaca", query = "SELECT s FROM SaeVehiculo s WHERE s.vehStrPlaca = :vehStrPlaca"),
    @NamedQuery(name = "SaeVehiculo.findByVehIntModelo", query = "SELECT s FROM SaeVehiculo s WHERE s.vehIntModelo = :vehIntModelo"),
    @NamedQuery(name = "SaeVehiculo.findByVehStrDispositivo", query = "SELECT s FROM SaeVehiculo s WHERE s.vehStrDispositivo = :vehStrDispositivo"),
    @NamedQuery(name = "SaeVehiculo.findByVehStrColor", query = "SELECT s FROM SaeVehiculo s WHERE s.vehStrColor = :vehStrColor"),
    @NamedQuery(name = "SaeVehiculo.findByVehStrMarca", query = "SELECT s FROM SaeVehiculo s WHERE s.vehStrMarca = :vehStrMarca"),
    @NamedQuery(name = "SaeVehiculo.findByVehBooEstado", query = "SELECT s FROM SaeVehiculo s WHERE s.vehBooEstado = :vehBooEstado"),
    @NamedQuery(name = "SaeVehiculo.findByVehStrLateral", query = "SELECT s FROM SaeVehiculo s WHERE s.vehStrLateral = :vehStrLateral")})
public class SaeVehiculo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "veh_str_placa")
    private String vehStrPlaca;
    @Column(name = "veh_int_modelo")
    private Integer vehIntModelo;
    @Column(name = "veh_str_dispositivo")
    private String vehStrDispositivo;
    @Column(name = "veh_str_color")
    private String vehStrColor;
    @Column(name = "veh_str_marca")
    private String vehStrMarca;
    @Column(name = "veh_boo_estado")
    private Boolean vehBooEstado;
    @Column(name = "veh_str_lateral")
    private String vehStrLateral;
    @OneToMany(mappedBy = "serStrPlaca")
    private List<SaeServicio> saeServicioList;
    @OneToMany(mappedBy = "hisStrPlaca")
    private List<SaeHistorialvehiculo> saeHistorialvehiculoList;
    @OneToMany(mappedBy = "turStrVehiculo")
    private List<SaeTurnoconductor> saeTurnoconductorList;
    @JoinColumn(name = "veh_int_propietario", referencedColumnName = "pro_int_cedula")
    @ManyToOne
    private SaePropietariovehiculo vehIntPropietario;

    public SaeVehiculo() {
    }

    public SaeVehiculo(String vehStrPlaca) {
        this.vehStrPlaca = vehStrPlaca;
    }

    public String getVehStrPlaca() {
        return vehStrPlaca;
    }

    public void setVehStrPlaca(String vehStrPlaca) {
        this.vehStrPlaca = vehStrPlaca;
    }

    public Integer getVehIntModelo() {
        return vehIntModelo;
    }

    public void setVehIntModelo(Integer vehIntModelo) {
        this.vehIntModelo = vehIntModelo;
    }

    public String getVehStrDispositivo() {
        return vehStrDispositivo;
    }

    public void setVehStrDispositivo(String vehStrDispositivo) {
        this.vehStrDispositivo = vehStrDispositivo;
    }

    public String getVehStrColor() {
        return vehStrColor;
    }

    public void setVehStrColor(String vehStrColor) {
        this.vehStrColor = vehStrColor;
    }

    public String getVehStrMarca() {
        return vehStrMarca;
    }

    public void setVehStrMarca(String vehStrMarca) {
        this.vehStrMarca = vehStrMarca;
    }

    public Boolean getVehBooEstado() {
        return vehBooEstado;
    }

    public void setVehBooEstado(Boolean vehBooEstado) {
        this.vehBooEstado = vehBooEstado;
    }

    public String getVehStrLateral() {
        return vehStrLateral;
    }

    public void setVehStrLateral(String vehStrLateral) {
        this.vehStrLateral = vehStrLateral;
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
    public List<SaeHistorialvehiculo> getSaeHistorialvehiculoList() {
        return saeHistorialvehiculoList;
    }

    public void setSaeHistorialvehiculoList(List<SaeHistorialvehiculo> saeHistorialvehiculoList) {
        this.saeHistorialvehiculoList = saeHistorialvehiculoList;
    }

    @XmlTransient
    @JsonIgnore
    public List<SaeTurnoconductor> getSaeTurnoconductorList() {
        return saeTurnoconductorList;
    }

    public void setSaeTurnoconductorList(List<SaeTurnoconductor> saeTurnoconductorList) {
        this.saeTurnoconductorList = saeTurnoconductorList;
    }

    public SaePropietariovehiculo getVehIntPropietario() {
        return vehIntPropietario;
    }

    public void setVehIntPropietario(SaePropietariovehiculo vehIntPropietario) {
        this.vehIntPropietario = vehIntPropietario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (vehStrPlaca != null ? vehStrPlaca.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SaeVehiculo)) {
            return false;
        }
        SaeVehiculo other = (SaeVehiculo) object;
        if ((this.vehStrPlaca == null && other.vehStrPlaca != null) || (this.vehStrPlaca != null && !this.vehStrPlaca.equals(other.vehStrPlaca))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.aquitax.domain.SaeVehiculo[ vehStrPlaca=" + vehStrPlaca + " ]";
    }
    
}
