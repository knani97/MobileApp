/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author Meriem
 */
public class Inventaire {
    private int pharmacie_id ;
    private int medicaments_id	;
    private int quantite ;
    private String qr ;

    public Inventaire(int pharmacie_id, int medicaments_id, int quantite, String qr) {
        this.pharmacie_id = pharmacie_id;
        this.medicaments_id = medicaments_id;
        this.quantite = quantite;
        this.qr = qr;
    }

    public String getQr() {
        return qr;
    }

    public void setQr(String qr) {
        this.qr = qr;
    }
    

    public Inventaire(int pharmacie_id, int medicaments_id, int quantite) {
        this.pharmacie_id = pharmacie_id;
        this.medicaments_id = medicaments_id;
        this.quantite = quantite;
    }

    public Inventaire(int medicaments_id, int quantite) {
        this.medicaments_id = medicaments_id;
        this.quantite = quantite;
    }
    

    public int getPharmacie_id() {
        return pharmacie_id;
    }

    public void setPharmacie_id(int pharmacie_id) {
        this.pharmacie_id = pharmacie_id;
    }

    public int getMedicaments_id() {
        return medicaments_id;
    }

    public void setMedicaments_id(int medicaments_id) {
        this.medicaments_id = medicaments_id;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    @Override
    public String toString() {
        return "Inventaire{" + "pharmacie_id=" + pharmacie_id
                + ", medicaments_id=" + medicaments_id + ", quantite=" + quantite + "} \n";
    }
    
    
    
    
    
    
    
}
