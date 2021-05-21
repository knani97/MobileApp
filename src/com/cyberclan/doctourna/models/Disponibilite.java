/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyberclan.doctourna.models;

import java.util.Date;

/**
 *
 * @author mouhe
 */
public class Disponibilite {
    
    private Integer id;
    private Date startDate;
    private Date endDate;
    private Date dureeRdv;
    private Date dureePause;

    public Disponibilite() {
        this.startDate = new Date();
        this.endDate = new Date();
        this.dureeRdv = new Date();
        this.dureePause = new Date();
    }

    public Disponibilite(Integer id) {
        this.id = id;
        this.startDate = new Date();
        this.endDate = new Date();
        this.dureeRdv = new Date();
        this.dureePause = new Date();
    }

    public Disponibilite(Integer id, Date startDate, Date endDate, Date dureeRdv, Date dureePause) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dureeRdv = dureeRdv;
        this.dureePause = dureePause;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getDureeRdv() {
        return dureeRdv;
    }

    public void setDureeRdv(Date dureeRdv) {
        this.dureeRdv = dureeRdv;
    }

    public Date getDureePause() {
        return dureePause;
    }

    public void setDureePause(Date dureePause) {
        this.dureePause = dureePause;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Disponibilite)) {
            return false;
        }
        Disponibilite other = (Disponibilite) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Disponibilite{" + "id=" + id + ", startDate=" + startDate + ", endDate=" + endDate + ", dureeRdv=" + dureeRdv + ", dureePause=" + dureePause + '}';
    }
}
