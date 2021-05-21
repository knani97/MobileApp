/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import models.Pharmacie;
import utils.Statics;

/**
 *
 * @author Meriem
 */
public class PharmacieService {
     public ArrayList<Pharmacie> Pharmacies;
    public static PharmacieService instance = null;
    public boolean resultOK;
    private ConnectionRequest req;
     public PharmacieService() {
        req = new ConnectionRequest();
    }

    public static PharmacieService getInstance() {
        if (instance == null) {
            instance = new PharmacieService();
        }
        return instance;
    }
        public ArrayList<Pharmacie> parseVideo(String jsonText) {
        try {
            Pharmacies = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> ReclamationListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) ReclamationListJson.get("root");

            for (Map<String, Object> obj : list) {
                Pharmacie cat = new Pharmacie();

                float id = Float.parseFloat(obj.get("id").toString());
                cat.setId((int) id);

               

              cat.setNom(obj.get("nom").toString());
                 cat.setAdresse(obj.get("adr").toString());
                cat.setGouvernourat(obj.get("gouv").toString());
                  
                   
             
           
        
             
              
                
                // questionnaire q =new questionnaire();
                // q.setDescription_cat_qst((String) map.get("description_cat_qst"));
             
                Pharmacies.add(cat);
            }

        } catch (IOException ex) {
            System.out.println("Exception in parsing reclamations ");
        }

        return Pharmacies;
    }

    public ArrayList<Pharmacie> findAll() {
        String url = Statics.BASE_URL + "/pharmacie/liste";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                Pharmacies = parseVideo(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return Pharmacies;
    
    }
}
