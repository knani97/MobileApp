/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.cyberclan.doctourna.utils.Statics;
import com.mycompany.Models.Medicament;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author user
 */
public class Medicament_Service  {
        public ArrayList<Medicament> Medicaments;
    public static Medicament_Service instance = null;
    public boolean resultOK=true;
    private ConnectionRequest req;
     public Medicament_Service() {
        req = new ConnectionRequest();
    }

    public static Medicament_Service getInstance() {
        if (instance == null) {
            instance = new Medicament_Service();
        }
        return instance;
    }
        public ArrayList<Medicament> parseMedicament(String jsonText) {
        try {
            Medicaments = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> ReclamationListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) ReclamationListJson.get("root");

            for (Map<String, Object> obj : list) {
                Medicament m = new Medicament();

                float id = Float.parseFloat(obj.get("id").toString());
                m.setId((int) id);

               

                m.setNomMed(obj.get("nom").toString());
                m.setFournisseur(obj.get("fournisseur").toString());
                float prix_achat = Float.parseFloat(obj.get("prix_achat").toString());
                m.setPrix_achat(prix_achat);
            float poids = Float.parseFloat(obj.get("poid").toString());
                m.setPoids((int) poids);
        
       Boolean fiche_exist = Boolean.parseBoolean(obj.get("fiche_exist").toString());
                m.setFiche_exist(fiche_exist);

 m.setImage_med(obj.get("img").toString());
 
Map<String, Object> map1 = ((Map<String, Object>) obj.get("idCat"));
                for (Entry<String, Object> entry : map1.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    
                    if(key.equals("id"))
                    {
                       float id_c = Float.parseFloat(value.toString());
                m.setId_Categorie((int) id_c);  
                    }
                  
                    
                }
 

                Medicaments.add(m);
            }

        } catch (IOException ex) {
            System.out.println("Exception in parsing reclamations ");
        }

        return Medicaments;
    }

    public ArrayList<Medicament> findAll() {
        String url = Statics.BASE_URL + "/medicament/ListMedicament_Mobile";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                Medicaments = parseMedicament(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return Medicaments;
    }
    
    public void ajouterMedicament(Medicament m){
        String url=Statics.BASE_URL+"/medicament/addMedicament?nomMed="+m.getNomMed()+"&fournisseur="+m.getFournisseur()+"&prix_achat="+m.getPrix_achat()+
                "&poids="+m.getPoids()+"&idCat="+m.getId_Categorie()+"&img_med="+m.getImage_med();
        req.setUrl(url);
        req.addResponseListener((e)->{
                String str = new String(req.getResponseData());
                System.out.println("data == "+str);
           
        });
        
         NetworkManager.getInstance().addToQueueAndWait(req);
        
    }
    
    public boolean SupprimerAbsence(int id){
      String url = Statics.BASE_URL +"deleteMedicament?id="+id;
       req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
          @Override
          public void actionPerformed(NetworkEvent evt) {
               
                req.removeResponseListener(this);
          }
            
        });
         NetworkManager.getInstance().addToQueueAndWait(req);
       
        
          return resultOK;
}
    
    public boolean ModifierMedicament(Medicament m){
        String url = Statics.BASE_URL +"/updateMedicament?id="+m.getId()+"&nomMed="+m.getNomMed()+"&fournisseur="+m.getFournisseur()+"&prix_achat="+m.getPrix_achat()+
                "&poids="+m.getPoids()+"&idCat="+m.getId_Categorie()+"&img_med="+m.getImage_med();
        
        req.setUrl(url);
      req.addResponseListener(new ActionListener<NetworkEvent>(){
            @Override
            public void actionPerformed(NetworkEvent evt) {
            // resultOK  =req.getResponseCode()==200;
             req.removeResponseListener(this);
                
            }
      }
      );
      NetworkManager.getInstance().addToQueueAndWait(req);
      return  resultOK;
    }
     


}

