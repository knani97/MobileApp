/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctourna.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.processing.Result;
import com.codename1.ui.events.ActionListener;
import com.doctourna.models.Video;
import com.doctourna.utils.Statics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author user
 */
public class VideoService {
        public ArrayList<Video> Videos;
    public static VideoService instance = null;
    public boolean resultOK;
    private ConnectionRequest req;
     public VideoService() {
        req = new ConnectionRequest();
    }

    public static VideoService getInstance() {
        if (instance == null) {
            instance = new VideoService();
        }
        return instance;
    }
        public ArrayList<Video> parseVideo(String jsonText) {
        try {
            Videos = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> ReclamationListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) ReclamationListJson.get("root");

            for (Map<String, Object> obj : list) {
                Video cat = new Video();

                float id = Float.parseFloat(obj.get("id").toString());
                cat.setId((int) id);

               

              cat.setTitre(obj.get("titre").toString());
                 cat.setSource(obj.get("source").toString());
                cat.setPaye(obj.get("paye").toString().contains("true") ? 1 : 0);
                   cat.setPrix(((float)Float.parseFloat(obj.get("prix").toString())));
                   cat.setNote(((int)Float.parseFloat(obj.get("note").toString())));
                     cat.setPanier_id(Result.fromContent(obj).getAsInteger("panier/id"));
                   
             
           
        
             
              
                
                // questionnaire q =new questionnaire();
                // q.setDescription_cat_qst((String) map.get("description_cat_qst"));
             
                Videos.add(cat);
            }

        } catch (IOException ex) {
            System.out.println("Exception in parsing reclamations ");
        }

        return Videos;
    }

    public ArrayList<Video> findAll() {
        String url = Statics.BASE_URL + "/video/liste";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                Videos = parseVideo(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return Videos;
    }
    
    public void ajouterVideo(Video v){
       String url = Statics.BASE_URL + "/addV1?titre=" + v.getTitre() + "&source=" + v.getSource() +"&paye=" + v.getPaye() + "&prix="  + v.getPrix() + "&note="  + v.getNote() + "&panier_id="  + v.getPanier_id(); //création de l'URL
        req.setUrl(url);// Insertion de l'URL de notre demande de connexion
        req.addResponseListener((e)->{
                String str = new String(req.getResponseData());
                System.out.println("data == "+str);
           
        });
        
         NetworkManager.getInstance().addToQueueAndWait(req);
        
    }
    
    public boolean SupprimerVideo(int id){
      String url = Statics.BASE_URL +"/video/deleteVide1?id="+id;
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
//    
//    public boolean ModifierVideo(Video v){
//        String url = Statics.BASE_URL +"updateVideo?id="+v.getId()+"&nomMed="+m.getNomMed()+"&fournisseur="+m.getFournisseur()+"&prix_achat="+m.getPrix_achat()+
//                "&poids="+m.getPoids()+"&idCat="+m.getId_Categorie()+"&img_med="+m.getImage_med();
//        
//        req.setUrl(url);
//      req.addResponseListener(new ActionListener<NetworkEvent>(){
//            @Override
//            public void actionPerformed(NetworkEvent evt) {
//            // resultOK  =req.getResponseCode()==200;
//             req.removeResponseListener(this);
//                
//            }
//      }
//      );
//      NetworkManager.getInstance().addToQueueAndWait(req);
//      return  resultOK;
//    }
     
//   public void ajouterVideo(Video v){
//        String url=Statics.BASE_URL+"medicament/addMedicament?nomMed="+m.getNomMed()+"&fournisseur="+m.getFournisseur()+"&prix_achat="+m.getPrix_achat()+
//                "&poids="+m.getPoids()+"&idCat="+m.getId_Categorie()+"&img_med="+m.getImage_med();
//        req.setUrl(url);
//        req.addResponseListener((e)->{
//                String str = new String(req.getResponseData());
//                System.out.println("data == "+str);
//           
//        });
//        
//         NetworkManager.getInstance().addToQueueAndWait(req);
//        
//    }
    
//    public boolean SupprimerAbsence(int id){
//      String url = Statics.BASE_URL +"deleteMedicament?id="+id;
//       req.setUrl(url);
//        req.addResponseListener(new ActionListener<NetworkEvent>() {
//          @Override
//          public void actionPerformed(NetworkEvent evt) {
//               
//                req.removeResponseListener(this);
//          }
//            
//        });
//         NetworkManager.getInstance().addToQueueAndWait(req);
//       
//        
//          return resultOK;
//}
    
    public boolean ModifierVideo(Video v){
        String url = Statics.BASE_URL +"/video/modifVideo?id="+ v.getTitre() + "&source=" + v.getSource() +"&paye=" + v.getPaye() + "&prix="  + v.getPrix() + "&note="  + v.getNote() + "&panier_id="  + v.getPanier_id(); //création de l'URL
        
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