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
import com.mycompany.Models.Fiche;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author user
 */
public class Fiche_Service  {
        public ArrayList<Fiche> Fiches;
    public static Fiche_Service instance = null;
    public boolean resultOK;
    private ConnectionRequest req;
     public Fiche_Service() {
        req = new ConnectionRequest();
    }

    public static Fiche_Service getInstance() {
        if (instance == null) {
            instance = new Fiche_Service();
        }
        return instance;
    }
        public ArrayList<Fiche> parseFiche(String jsonText) {
        try {
            Fiches = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> ReclamationListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) ReclamationListJson.get("root");

            for (Map<String, Object> obj : list) {
                Fiche f = new Fiche();

                float id = Float.parseFloat(obj.get("id").toString());
                f.setId((int) id);

                 float quantite = Float.parseFloat(obj.get("quantite").toString());
                f.setQuantite((int) quantite);

                
                f.setUtilisation(obj.get("utilisation").toString());
           
        
              
                
                // questionnaire q =new questionnaire();
                // q.setDescription_cat_qst((String) map.get("description_cat_qst"));
             
                Fiches.add(f);
            }

        } catch (IOException ex) {
            System.out.println("Exception in parsing reclamations ");
        }

        return Fiches;
    }

    public ArrayList<Fiche> findAll(int id_m) {
        String url = Statics.BASE_URL + "/fiche/List_Fiche_Mobile/"+id_m;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                Fiches = parseFiche(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return Fiches;
    }

     public void send8mail_Fiche(int id_f,int id_u) {
        String url = Statics.BASE_URL + "/fiche/fiche_mail_Mobile/"+id_f+"/"+id_u;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
            
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
      
    }

}
