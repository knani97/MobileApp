/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyberclan.doctourna.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.cyberclan.doctourna.models.User;
import com.cyberclan.doctourna.utils.DataSource;
import com.cyberclan.doctourna.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author mouhe
 */
public class UserService {
    private ConnectionRequest request;
    
    private boolean responseResult;
    private ArrayList<User> users;
    
    public UserService() {
        request = DataSource.getInstance().getRequest();
    }
    
    public boolean addUser(User user) {
        String url = Statics.BASE_URL + "/admin/user/add" + user.getNom() + "/" + user.getPrenom();
        
        request.setUrl(url);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent event) {
                responseResult = request.getResponseCode() == 200;
                request.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);
        
        return responseResult;
    }
    
    public ArrayList<User> getAllUsers() {
        String url = Statics.BASE_URL + "/admin/user/list";
        
        request.setUrl(url);
        request.setPost(false);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent event) {
                users = parseUsers(new String(request.getResponseData()));
                request.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);
        
        return users;
    }
    
    public ArrayList<User> getAllMedecins() {
        ArrayList<User> medecins = new ArrayList<User>();
        
        for (User u : getAllUsers()) {
            if (u.getType() == 2)
                medecins.add(u);
        }
        
        return medecins;
    }
    
    public User getUser(int id) {
        for (User u : getAllUsers()) {
            if (u.getId() == id)
                return u;
        }
        
        return null;
    }
    
    public ArrayList<User> parseUsers(String jsonText) {
        try {
            users = new ArrayList<User>();
            
            JSONParser jp = new JSONParser();
            Map<String, Object> usersListJson = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String, Object>> list = (List<Map<String, Object>>) usersListJson.get("root");
            for (Map<String, Object> obj : list) {
                int id = (int)Float.parseFloat(obj.get("id").toString());
                String image = new String();
                if (obj.get("image") != null)
                    image = obj.get("image").toString();
                int type = (int)Float.parseFloat(obj.get("type").toString());
                String nom = obj.get("nom").toString();
                String prenom = obj.get("prenom").toString();
                String email = obj.get("email").toString();
                String roles = obj.get("roles").toString();
                String password = obj.get("password").toString();
                users.add(new User(id, null, image, type, nom, prenom, email, roles, password));
            }
        }
        catch (IOException ex) {
           ex.printStackTrace();
        }
        
        return users;
    }
}
