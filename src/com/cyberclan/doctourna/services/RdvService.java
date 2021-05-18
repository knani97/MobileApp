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
import com.codename1.processing.Result;
import com.codename1.ui.events.ActionListener;
import com.cyberclan.doctourna.Manipulator;
import com.cyberclan.doctourna.models.Calendrier;
import com.cyberclan.doctourna.models.Rdv;
import com.cyberclan.doctourna.models.Tache;
import com.cyberclan.doctourna.models.User;
import com.cyberclan.doctourna.utils.DataSource;
import com.cyberclan.doctourna.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author mouhe
 */
public class RdvService {
    private ConnectionRequest request;
    
    private boolean responseResult;
    private ArrayList<Rdv> rdvs;
    
    public RdvService() {
        request = DataSource.getInstance().getRequest();
    }
    
    public boolean addRdv(Rdv rdv, int cal) {
        String url = Statics.BASE_URL + "/rdvs/add?" +
                "cal=" + cal +
                "&patient_id=" + rdv.getPatientId().getId() +
                "&medecin_id=" + rdv.getMedecinId().getId() +
                "&tache_dispo=" + rdv.getTacheDispoId().getId() +
                "&description=" + rdv.getDescription() +
                "&jointure=" + rdv.getJointure();

        request.setUrl(url);
        request.setPost(false);
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
    
    public boolean editRdv(Rdv rdv) {
        String url = Statics.BASE_URL + "/rdvs/add" + rdv.getDescription() + "/" + rdv.getJointure();
        
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
    
    public boolean cancelRdv(Rdv rdv) {
        String url = Statics.BASE_URL + "/rdvs/cancel?" +
                "id=" + rdv.getId();

        request.setUrl(url);
        request.setPost(false);
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
    
    public boolean endRdv(Rdv rdv) {
        String url = Statics.BASE_URL + "/rdvs/end?" +
                "id=" + rdv.getId();

        request.setUrl(url);
        request.setPost(false);
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
    
    public ArrayList<Rdv> getAllRdvs() {
        String url = Statics.BASE_URL + "/rdvs/list";
        
        request.setUrl(url);
        request.setPost(false);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent event) {
                rdvs = parseRdvs(new String(request.getResponseData()));
                request.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);
        
        return rdvs;
    }
    
    public Rdv getRdv(int id) {
        for (Rdv r : getAllRdvs()) {
            if (r.getId() == id) {
                r.update();
                return r;
            }
        }

        return null;
    }
    
    public ArrayList<Rdv> getRdvsByUid(int uid) {
        ArrayList<Rdv> list = new ArrayList<Rdv>();
        
        for (Rdv r : getAllRdvs()) {
            if (r.getPatientId().getId() == uid || r.getMedecinId().getId() == uid) {
                r.update();
                list.add(r);
            }
        }

        return list;
    }
    
    public void annulerRdv(Rdv rdv) {
        rdv.setEtat(3);
        // add
        editRdv(rdv);
    }
    
    public ArrayList<Rdv> parseRdvs(String jsonText) {
        try {
            rdvs = new ArrayList<Rdv>();
            
            JSONParser jp = new JSONParser();
            Map<String, Object> rdvsListJson = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String, Object>> list = (List<Map<String, Object>>) rdvsListJson.get("root");
            for (Map<String, Object> obj : list) {
                int id = (int)Float.parseFloat(obj.get("id").toString());
                Date date = Manipulator.dateFrom(obj.get("date").toString());
                int etat = (int)Float.parseFloat(obj.get("etat").toString());
                String description = obj.get("description").toString();
                String jointure = obj.get("jointure").toString();
                rdvs.add(new Rdv(
                        id,
                        date,
                        etat,
                        description,
                        jointure,
                        new Tache(Result.fromContent(obj).getAsInteger("tacheDispo/id")),
                        new User(Result.fromContent(obj).getAsInteger("medecin/id")),
                        new User(Result.fromContent(obj).getAsInteger("patient/id")),
                        new Tache(Result.fromContent(obj).getAsInteger("tacheUser/id"))
                ));
            }
        }
        catch (IOException ex) {
           ex.printStackTrace();
        }
        
        return rdvs;
    }
}
