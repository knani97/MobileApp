/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author Lenovo
 */
public class ArticleCat {
    private int id;
    private String  Categorie,image;

public ArticleCat(){
}

public ArticleCat(int id,String Categorie,String image){
    this.id=id;
    this.Categorie=Categorie;
    this.image=image;
    
}

    ArticleCat(int idCat) {
        this.id=id;
    }

    public ArticleCat(int id, String categorie) {
        this.id=id;
    this.Categorie=categorie;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategorie() {
        return Categorie;
    }

    public void setCategorie(String Categorie) {
        this.Categorie = Categorie;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}