/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import entity.Article;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author mouhe
 */
public class TacheService {
    private ConnectionRequest request;
    
    private boolean responseResult;
    private ArrayList<Article> taches;
    
    public TacheService() {
        request = DataSource.getInstance().getRequest();
    }
   
    
    public ArrayList<Article> getAllTaches() {
        String url = Statics.BASE_URL + "/news/detailsArtJ/29";
        
        request.setUrl(url);
        request.setPost(false);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent event) {
                taches = parseTaches(new String(request.getResponseData()));
                request.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);
        
        return taches;
    }
    
    public Article getTache(int id) {
        for (Article t : getAllTaches()) {
            if (t.getId() == id)
                return t;
        }
        
        return null;
    }
    
    
    public ArrayList<Article> parseTaches(String jsonText) {
        try {
            taches = new ArrayList<Article>();
            
            JSONParser jp = new JSONParser();
            Map<String, Object> tachesListJson = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String, Object>> list = (List<Map<String, Object>>) tachesListJson.get("root");
            for (Map<String, Object> obj : list) {
                int id = (int)Float.parseFloat(obj.get("id").toString());
                String libelle = obj.get("libelle").toString();
                String description = obj.get("description").toString();
                String type = obj.get("type").toString();
                String couleur = obj.get("couleur").toString();
                
            }
        }
        catch (IOException ex) {
           ex.printStackTrace();
        }
        
        return taches;
    }
}
