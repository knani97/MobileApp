/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctourna.models;

/**
 *
 * @author yass
 */
public class Video {
    int id;
    String titre;
    String source;
    int paye;
    float prix;
    int note;
    int panier_id;

    public Video(int id, String titre, String source, int paye, float prix, int note, int panier_id) {
        this.id = id;
        this.titre = titre;
        this.source = source;
        this.paye = paye;
        this.prix = prix;
        this.note = note;
        this.panier_id = panier_id;
    }

    public Video() {
    }
    

    
    public Video(String titre, String source, int paye, float prix, int note, int panier_id) {
        this.titre = titre;
        this.source = source;
        this.paye = paye;
        this.prix = prix;
        this.note = note;
        this.panier_id = panier_id;
    }

   
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getPaye() {
        return paye;
    }

    public void setPaye(int paye) {
        this.paye = paye;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public int getPanier_id() {
        return panier_id;
    }

    public void setPanier_id(int panier_id) {
        this.panier_id = panier_id;
    }
    
}
