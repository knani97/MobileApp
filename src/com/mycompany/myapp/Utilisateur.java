/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

/**
 *
 * @author LENOVO
 */
public class Utilisateur {
    private int id ; 
    private String email ; 
    private String passorwd ;
    private String nom ;
    private String prenom ; 

    public Utilisateur(String email, String passorwd, String nom, String prenom) {
        this.email = email;
        this.passorwd = passorwd;
        this.nom = nom;
        this.prenom = prenom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassorwd() {
        return passorwd;
    }

    public void setPassorwd(String passorwd) {
        this.passorwd = passorwd;
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
     public Utilisateur() {
    }
     
    
     @Override
    public String toString() {
        return "Utilisateur{" + "id=" + id + ", nom=" + nom + ", adresse=" + email + ", prenom=" + prenom + '}';
    }

    public Utilisateur(int id,  String email,  String nom, String prenom) {
        this.id = id;
       
        this.email = email;
        
        this.nom = nom;
        this.prenom = prenom;
    }

    public Utilisateur( String email,  String nom, String prenom) {
       
        this.email = email;
        
        this.nom = nom;
        this.prenom = prenom;
    }
    
    
    
}
