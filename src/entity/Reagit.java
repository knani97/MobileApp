/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;


public class Reagit {

     private Integer id;
    private int typeReact;
    private int idUserId;
    private int idArtId;

    public Reagit() {
    }

    public Reagit(Integer id) {
        this.id = id;
    }
   

    public Reagit(Integer id, int typeReact) {
        this.id = id;
        this.typeReact = typeReact;
    }

    public Reagit(int id, int idUserId, int idArtId,int typeReact) {
        this.id = id;
        this.idUserId = idUserId;
        this.idArtId = idArtId;
        this.typeReact = typeReact;
    }
    
    public Reagit(int idUserId, int idArtId,int typeReact) {
        this.idUserId = idUserId;
        this.idArtId = idArtId;
        this.typeReact = typeReact;
    }
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getTypeReact() {
        return typeReact;
    }

    public void setTypeReact(int typeReact) {
        this.typeReact = typeReact;
    }

    public int getIdUserId() {
        return idUserId;
    }

    public void setIdUserId(int idUserId) {
        this.idUserId = idUserId;
    }

    public int getIdArtId() {
        return idArtId;
    }

    public void setIdArtId(int idArtId) {
        this.idArtId = idArtId;
    }

    @Override
    public String toString() {
        return "entity.Reagit[ id=" + id + " ]";
    }
    
}
