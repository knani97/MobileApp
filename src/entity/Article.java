/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.Date;

public class Article  {

  
    private Integer id;
    private String titre;
    private String text;
    private String image;
    private Date dateAjout;
    private int etatAjout;
    private int idUser;
    private int idCat;
public ArticleCat articleCat = new ArticleCat();
public Users users = new Users();
    public Article() {
    }

    public Article(Integer id) {
        this.id = id;
    }

    public Article(String titre, String text, String image, Date dateAjout, int etatAjout, int idUser) {
        this.id = id;
        this.titre = titre;
        this.text = text;
        this.image = image;
        this.dateAjout = dateAjout;
        this.etatAjout = etatAjout;
        this.idUser = idUser;
        articleCat.setId(idCat);
    }
    
    public Article(Integer id, String titre, String text, String image, Date dateAjout, int etatAjout, int idUser,int idCat) {
        this.id = id;
        this.titre = titre;
        this.text = text;
        this.image = image;
        this.dateAjout = dateAjout;
        this.etatAjout = etatAjout;
        this.idUser = idUser;
        articleCat.setId(idCat);
    }


    public Article(int id, String titre, String text, String image) {
        this.id = id;
         new ArticleCat(idCat);
        this.titre = titre;
        this.text = text;
        this.image = image;
        this.idUser = idUser;
    }

    public Article(String titre, String text, String image, int idUser,int idCat) {
        this.id = id;
        articleCat.setId(idCat);
        this.titre = titre;
        this.text = text;
        this.image = image;
        this.idUser = idUser;
    }
    
    public Article(int id, String titre, String text, String image,Date dateAjout, int etatAjout, int idUser, String prenom, String nom, String imageU,int idCat) {
        users.setNom(nom);
        users.setPrenom(prenom);
        users.setImage(imageU);
        this.id = id;
        this.titre = titre;
        this.text = text;
        this.image = image;
        this.dateAjout = dateAjout;
        this.etatAjout = etatAjout;
        this.idUser = idUser;
        articleCat.setId(idCat);
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getDateAjout() {
        return dateAjout;
    }

    public void setDateAjout(Date dateAjout) {
        this.dateAjout = dateAjout;
    }

    public int getEtatAjout() {
        return etatAjout;
    }

    public void setEtatAjout(int etatAjout) {
        this.etatAjout = etatAjout;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdCat() {
        return idCat;
    }

    public void setIdCat(int idCat) {
        this.idCat = idCat;
    }

    public ArticleCat getArticleCat() {
        return articleCat;
    }

    public void setArticleCat(ArticleCat articleCat) {
        this.articleCat = articleCat;
    }

    @Override
    public String toString() {
        return "entity.Article[ id=" + id + "- Titre"+titre+" ]";
    }
    
}
