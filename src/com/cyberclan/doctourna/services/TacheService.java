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
import com.cyberclan.doctourna.models.Disponibilite;
import com.cyberclan.doctourna.models.Tache;
import com.cyberclan.doctourna.utils.DataSource;
import com.cyberclan.doctourna.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author mouhe
 */
public class TacheService {

    private ConnectionRequest request;

    private boolean responseResult;
    private ArrayList<Tache> taches;

    public TacheService() {
        request = DataSource.getInstance().getRequest();
    }

    public boolean addTache(Tache tache) {
        String url = Statics.BASE_URL + "/taches/add?" +
                "libelle=" + tache.getLibelle() +
                "&description=" + tache.getDescription() +
                "&type=" + tache.getType() +
                "&cal=" + tache.getCalendrier().getId() +
                "&date=" + Manipulator.symfonyDateTime(tache.getDate()) +
                "&duree=" + Manipulator.symfonyTime(tache.getDuree()) +
                "&couleur=" + tache.getCouleur();
        System.out.println(Manipulator.symfonyDateTime(tache.getDate()));
        System.out.println(Manipulator.symfonyTime(tache.getDuree()));

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
    
    public boolean editTache(Tache tache) {
        String url = Statics.BASE_URL + "/taches/edit?" +
                "id=" + tache.getId() +
                "&libelle=" + tache.getLibelle() +
                "&description=" + tache.getDescription() +
                "&type=" + tache.getType() +
                "&cal=" + tache.getCalendrier().getId() +
                "&date=" + Manipulator.symfonyDateTime(tache.getDate()) +
                "&duree=" + Manipulator.symfonyTime(tache.getDuree()) +
                "&couleur=" + tache.getCouleur();

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
    
    public boolean deleteTache(Tache tache) {
        String url = Statics.BASE_URL + "/taches/delete?" +
                "id=" + tache.getId();

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
    
    public boolean addDispo(Disponibilite dispo, int cal) {
        String url = Statics.BASE_URL + "/dispos/add?" +
                "cal=" + cal +
                "&start_date=" + Manipulator.symfonyDateTime(dispo.getStartDate()) +
                "&end_date=" + Manipulator.symfonyDateTime(dispo.getEndDate()) +
                "&duree_rdv=" + Manipulator.symfonyTime(dispo.getDureeRdv()) +
                "&duree_pause=" + Manipulator.symfonyTime(dispo.getDureePause());

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

    public ArrayList<Tache> getAllTaches() {
        String url = Statics.BASE_URL + "/taches/list";

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

    public Tache getTache(int id) {
        for (Tache t : getAllTaches()) {
            if (t.getId() == id) {
                return t;
            }
        }

        return null;
    }

    public ArrayList<Tache> getTachesByCal(int cal) {
        ArrayList<Tache> list = new ArrayList<Tache>();

        for (Tache t : getAllTaches()) {
            if (t.getCalendrier().getId() == cal) {
                t.update();
                list.add(t);
            }
        }

        return list;
    }
    
    public ArrayList<Tache> getDisposByCal(int cal) {
        ArrayList<Tache> list = new ArrayList<Tache>();

        for (Tache t : getTachesByCal(cal)) {
            t.update();
            if (t.getType().contains("4")) {
                list.add(t);
            }
        }

        return list;
    }

    public ArrayList<Tache> parseTaches(String jsonText) {
        try {
            taches = new ArrayList<Tache>();

            JSONParser jp = new JSONParser();
            Map<String, Object> tachesListJson = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) tachesListJson.get("root");
            for (Map<String, Object> obj : list) {
                int id = (int) Float.parseFloat(obj.get("id").toString());
                String libelle = obj.get("libelle").toString();
                String description = obj.get("description").toString();
                String type = obj.get("type").toString();
                String couleur = obj.get("couleur").toString();
                Date date = Manipulator.dateFrom(obj.get("date").toString());
                Date duree = Manipulator.timeFrom(obj.get("duree").toString());
                taches.add(new Tache(id, new Calendrier(Result.fromContent(obj).getAsInteger("calendrier/id")), libelle, description, type, couleur, date, duree));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return taches;
    }
}
