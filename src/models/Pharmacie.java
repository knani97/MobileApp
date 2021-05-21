/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import javafx.scene.Scene;
import javafx.scene.image.ImageView;

/**
 *
 * @author imen
 */
public class Pharmacie {

   

   
  private  int id;
  private String  nom;
  private String adresse;
  private String gouvernourat;
  private String img_patente;




    public Pharmacie() {
    }
     public Pharmacie(String nom) {
             this.nom = nom;
     }
    public Pharmacie(int id, String nom, String adresse, String gouvernourat, String img_patente) {
        this.id = id;
        this.nom = nom;
        this.adresse = adresse;
        this.gouvernourat = gouvernourat;
        this.img_patente = img_patente;
    }
    

    public Pharmacie(String nom, String adresse, String gouvernourat, String img_patente) {
        this.nom = nom;
        this.adresse = adresse;
        this.gouvernourat = gouvernourat;
        this.img_patente = img_patente;
    }

    public Pharmacie(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getGouvernourat() {
        return gouvernourat;
    }

    public void setGouvernourat(String gouvernourat) {
        this.gouvernourat = gouvernourat;
    }

    public String getImg_patente() {
        return img_patente;
    }

    public void setImg_patente(String img_patente) {
        this.img_patente = img_patente;
    }

    @Override
    public String toString() {
        return "Pharmacie{" + "id=" + id + ", nom=" + nom + ", adresse=" + adresse + 
                ", gouvernourat=" + gouvernourat + ", img_patente=" + img_patente + "} \n";
    }
  
   
    
}
