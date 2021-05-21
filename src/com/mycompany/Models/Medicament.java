/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Models;

/**
 *
 * @author user
 */
public class Medicament {
    private int id;
    private String nomMed;
    private String fournisseur;
    private float prix_achat;
    private float poids;
    private Boolean fiche_exist;
    private String image_med;
    private int id_Categorie;

    public Medicament(String nomMed, String fournisseur, float prix_achat, float poids) {
        this.nomMed = nomMed;
        this.fournisseur = fournisseur;
        this.prix_achat = prix_achat;
        this.poids = poids;
    }
    
    

    public Medicament() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomMed() {
        return nomMed;
    }

    public void setNomMed(String nomMed) {
        this.nomMed = nomMed;
    }

    public String getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(String fournisseur) {
        this.fournisseur = fournisseur;
    }

    public float getPrix_achat() {
        return prix_achat;
    }

    public void setPrix_achat(float prix_achat) {
        this.prix_achat = prix_achat;
    }

    public float getPoids() {
        return poids;
    }

    public void setPoids(float poids) {
        this.poids = poids;
    }

    public Boolean getFiche_exist() {
        return fiche_exist;
    }

    public void setFiche_exist(Boolean fiche_exist) {
        this.fiche_exist = fiche_exist;
    }

    public String getImage_med() {
        return image_med;
    }

    public void setImage_med(String image_med) {
        this.image_med = image_med;
    }

    public int getId_Categorie() {
        return id_Categorie;
    }

    public void setId_Categorie(int id_Categorie) {
        this.id_Categorie = id_Categorie;
    }

    @Override
    public String toString() {
        return "Medicament{" + "id=" + id 
                + ", nomMed=" + nomMed 
                + ", fournisseur=" + fournisseur 
                + ", prix_achat=" + prix_achat
                + ", poids=" + poids 
                + ", fiche_exist=" + fiche_exist 
                + ", image_med="
                + image_med 
                + ", id_Categorie=" + id_Categorie + '}';
    }
    

    
    
}
