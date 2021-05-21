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
public class Commande {
    
    private float prix;
    private String date;

    public Commande() {
    }

    public Commande(float prix, String date) {
        this.prix = prix;
        this.date = date;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    
}
