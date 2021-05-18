/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyberclan.doctourna.models;

import java.util.Collection;

/**
 *
 * @author mouhe
 */
public class User {
    
    private Integer id;
    private String image;
    private Integer type;
    private String nom;
    private String prenom;
    private String email;
    private String roles;
    private String password;
    private String activationToken;
    private String resetToken;
    private String articles;
    private Collection<Calendrier> calendrierCollection;
    private Collection<Rdv> rdvCollection;
    private Collection<Rdv> rdvCollection1;
    private Cv cvId;

    public User() {
    }

    public User(Integer id) {
        this.id = id;
    }

    public User(Integer id, Cv cvId, String image, Integer type, String nom, String prenom, String email, String roles, String password) {
        this.id = id;
        this.cvId = cvId;
        this.image = image;
        this.type = type;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.roles = roles;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getActivationToken() {
        return activationToken;
    }

    public void setActivationToken(String activationToken) {
        this.activationToken = activationToken;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public String getArticles() {
        return articles;
    }

    public void setArticles(String articles) {
        this.articles = articles;
    }

    public Collection<Calendrier> getCalendrierCollection() {
        return calendrierCollection;
    }

    public void setCalendrierCollection(Collection<Calendrier> calendrierCollection) {
        this.calendrierCollection = calendrierCollection;
    }

    public Collection<Rdv> getRdvCollection() {
        return rdvCollection;
    }

    public void setRdvCollection(Collection<Rdv> rdvCollection) {
        this.rdvCollection = rdvCollection;
    }

    public Collection<Rdv> getRdvCollection1() {
        return rdvCollection1;
    }

    public void setRdvCollection1(Collection<Rdv> rdvCollection1) {
        this.rdvCollection1 = rdvCollection1;
    }

    public Cv getCvId() {
        return cvId;
    }

    public void setCvId(Cv cvId) {
        this.cvId = cvId;
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
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return prenom + " " + nom;
    }
}
