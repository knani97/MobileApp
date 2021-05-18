/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyberclan.doctourna.utils;

/**
 *
 * @author mouhe
 */
public class Session {
    private static int id;
    private static int type;
    private static String email;
    private static String nom;
    private static String prenom;

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        Session.id = id;
    }

    public static int getType() {
        return type;
    }

    public static void setType(int type) {
        Session.type = type;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        Session.email = email;
    }

    public static String getNom() {
        return nom;
    }

    public static void setNom(String nom) {
        Session.nom = nom;
    }

    public static String getPrenom() {
        return prenom;
    }

    public static void setPrenom(String prenom) {
        Session.prenom = prenom;
    }
}
