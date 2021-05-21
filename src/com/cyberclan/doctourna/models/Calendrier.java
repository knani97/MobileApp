/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyberclan.doctourna.models;

import com.cyberclan.doctourna.services.UserService;

/**
 *
 * @author mouhe
 */
public class Calendrier {
    private Integer id;

    private int type;

    private boolean email;

    private String couleur;
 
    private String timezone;

    private int format;

    private User uidId;

    public Calendrier() {
    }

    public Calendrier(Integer id) {
        this.id = id;
    }

    public Calendrier(Integer id, int type, boolean email, String couleur, String timezone, int format) {
        this.id = id;
        this.type = type;
        this.email = email;
        this.couleur = couleur;
        this.timezone = timezone;
        this.format = format;
    }
    
    public Calendrier(Integer id, User uidId, int type, boolean email, String couleur, String timezone, int format) {
        this.id = id;
        this.uidId = uidId;
        this.type = type;
        this.email = email;
        this.couleur = couleur;
        this.timezone = timezone;
        this.format = format;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean getEmail() {
        return email;
    }

    public void setEmail(boolean email) {
        this.email = email;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public int getFormat() {
        return format;
    }

    public void setFormat(int format) {
        this.format = format;
    }

    public User getUidId() {
        return uidId;
    }

    public void setUidId(User uidId) {
        this.uidId = uidId;
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
        if (!(object instanceof Calendrier)) {
            return false;
        }
        Calendrier other = (Calendrier) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "doctourna.models.Calendrier_1[ id=" + id + " ]";
    }
    
    public void update() {
        //this.uidId = new UserService().getUser(this.uidId.getId());
    }
}
