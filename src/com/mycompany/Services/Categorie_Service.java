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
import com.mycompany.Models.Categorie;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author user
 */
public class Categorie_Service {
        public ArrayList<Categorie> Categories;
    public static Categorie_Service instance = null;
    public boolean resultOK;
    private ConnectionRequest req;
     public Categorie_Service() {
        req = new ConnectionRequest();
    }

    public static Categorie_Service getInstance() {
        if (instance == null) {
            instance = new Categorie_Service();
        }
        return instance;
    }
        public ArrayList<Categorie> parseCategorie(String jsonText) {
        try {
            Categories = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> ReclamationListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) ReclamationListJson.get("root");

            for (Map<String, Object> obj : list) {
                Categorie cat = new Categorie();

                float id = Float.parseFloat(obj.get("id").toString());
                cat.setId((int) id);

               

                cat.setNom(obj.get("nom").toString());
                cat.setDescription(obj.get("description").toString());
           
        
             
              
                
                // questionnaire q =new questionnaire();
                // q.setDescription_cat_qst((String) map.get("description_cat_qst"));
             
                Categories.add(cat);
            }

        } catch (IOException ex) {
            System.out.println("Exception in parsing reclamations ");
        }

        return Categories;
    }

    public ArrayList<Categorie> findAll() {
        String url = Statics.BASE_URL + "/categorie/List_Categorie_Mobile";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                Categories = parseCategorie(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return Categories;
    }


}
