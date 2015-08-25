/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aquitax.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author SAE2
 */
@Entity
@Table(name = "sae_turnoconductor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SaeTurnoconductor.findAll", query = "SELECT s FROM SaeTurnoconductor s"),
    @NamedQuery(name = "SaeTurnoconductor.findByTurStrHorainicio", query = "SELECT s FROM SaeTurnoconductor s WHERE s.turStrHorainicio = :turStrHorainicio"),
    @NamedQuery(name = "SaeTurnoconductor.findByTurStrHorafin", query = "SELECT s FROM SaeTurnoconductor s WHERE s.turStrHorafin = :turStrHorafin"),
    @NamedQuery(name = "SaeTurnoconductor.findByTurStrDias", query = "SELECT s FROM SaeTurnoconductor s WHERE s.turStrDias = :turStrDias"),
    @NamedQuery(name = "SaeTurnoconductor.findByTurBooEstado", query = "SELECT s FROM SaeTurnoconductor s WHERE s.turBooEstado = :turBooEstado"),
    @NamedQuery(name = "SaeTurnoconductor.findByTurIntId", query = "SELECT s FROM SaeTurnoconductor s WHERE s.turIntId = :turIntId")})
public class SaeTurnoconductor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "tur_str_horainicio")
    @Temporal(TemporalType.TIME)
    private Date turStrHorainicio;
    @Column(name = "tur_str_horafin")
    @Temporal(TemporalType.TIME)
    private Date turStrHorafin;
    @Column(name = "tur_str_dias")
    private String turStrDias;
    @Column(name = "tur_boo_estado")
    private Boolean turBooEstado;
    @Id
    @Basic(optional = false)
    @Column(name = "tur_int_id")
    private Integer turIntId;
    @JoinColumn(name = "tur_str_vehiculo", referencedColumnName = "veh_str_placa")
    @ManyToOne
    private SaeVehiculo turStrVehiculo;
    @JoinColumn(name = "tur_int_conductor", referencedColumnName = "con_int_cedula")
    @ManyToOne
    private SaeConductor turIntConductor;

    public SaeTurnoconductor() {
    }

    public SaeTurnoconductor(Integer turIntId) {
        this.turIntId = turIntId;
    }

    public Date getTurStrHorainicio() {
        return turStrHorainicio;
    }

    public void setTurStrHorainicio(Date turStrHorainicio) {
        this.turStrHorainicio = turStrHorainicio;
    }

    public Date getTurStrHorafin() {
        return turStrHorafin;
    }

    public void setTurStrHorafin(Date turStrHorafin) {
        this.turStrHorafin = turStrHorafin;
    }

    public String getTurStrDias() {
        return turStrDias;
    }

    public void setTurStrDias(String turStrDias) {
        this.turStrDias = turStrDias;
    }

    public Boolean getTurBooEstado() {
        return turBooEstado;
    }

    public void setTurBooEstado(Boolean turBooEstado) {
        this.turBooEstado = turBooEstado;
    }

    public Integer getTurIntId() {
        return turIntId;
    }

    public void setTurIntId(Integer turIntId) {
        this.turIntId = turIntId;
    }

    public SaeVehiculo getTurStrVehiculo() {
        return turStrVehiculo;
    }

    public void setTurStrVehiculo(SaeVehiculo turStrVehiculo) {
        this.turStrVehiculo = turStrVehiculo;
    }

    public SaeConductor getTurIntConductor() {
        return turIntConductor;
    }

    public void setTurIntConductor(SaeConductor turIntConductor) {
        this.turIntConductor = turIntConductor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (turIntId != null ? turIntId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SaeTurnoconductor)) {
            return false;
        }
        SaeTurnoconductor other = (SaeTurnoconductor) object;
        if ((this.turIntId == null && other.turIntId != null) || (this.turIntId != null && !this.turIntId.equals(other.turIntId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.aquitax.domain.SaeTurnoconductor[ turIntId=" + turIntId + " ]";
    }
    
}
