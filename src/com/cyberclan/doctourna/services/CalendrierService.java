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
import com.cyberclan.doctourna.Console;
import com.cyberclan.doctourna.models.Calendrier;
import com.cyberclan.doctourna.models.User;
import com.cyberclan.doctourna.utils.DataSource;
import com.cyberclan.doctourna.utils.Session;
import com.cyberclan.doctourna.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author mouhe
 */
public class CalendrierService {

    private ConnectionRequest request;

    private boolean responseResult;
    private ArrayList<Calendrier> calendriers;

    public CalendrierService() {
        request = DataSource.getInstance().getRequest();
    }

    public boolean addCalendrier(Calendrier calendrier) {
        String url = Statics.BASE_URL + "/calendriers/add?" +
                "uid=" + Session.getId() +
                "&type=" + calendrier.getType() +
                "&email=" + (calendrier.getEmail() ? 1 : 0) +
                "&couleur=" + calendrier.getCouleur() +
                "&timezone=" + calendrier.getTimezone() +
                "&format=" + calendrier.getFormat();

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
    
    public boolean editCalendrier(Calendrier calendrier) {
        String url = Statics.BASE_URL + "/calendriers/edit?" +
                "id=" + calendrier.getId() +
                "&uid=" + Session.getId() +
                "&type=" + calendrier.getType() +
                "&email=" + (calendrier.getEmail() ? 1 : 0) +
                "&couleur=" + calendrier.getCouleur() +
                "&timezone=" + calendrier.getTimezone() +
                "&format=" + calendrier.getFormat();

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
    
    public boolean deleteCalendrier(Calendrier calendrier) {
        String url = Statics.BASE_URL + "/calendriers/delete?" +
                "id=" + calendrier.getId();

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

    public ArrayList<Calendrier> getAllCalendriers() {
        String url = Statics.BASE_URL + "/calendriers/list";

        request.setUrl(url);
        request.setPost(false);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent event) {
                calendriers = parseCalendriers(new String(request.getResponseData()));
                request.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return calendriers;
    }

    public Calendrier getCalendrier(int id) {
        for (Calendrier c : getAllCalendriers()) {
            if (c.getId() == id) {
                c.update();
                return c;
            }
        }

        return null;
    }

    public Calendrier getCalendrierByUid(int uid) {
        for (Calendrier c : getAllCalendriers()) {
            if (c.getUidId().getId() == uid) {
                c.update();
                return c;
            }
        }

        return null;
    }

    public ArrayList<Calendrier> parseCalendriers(String jsonText) {
        try {
            calendriers = new ArrayList<Calendrier>();

            JSONParser jp = new JSONParser();
            Map<String, Object> calendriersListJson = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) calendriersListJson.get("root");
            for (Map<String, Object> obj : list) {
                int id = (int) Float.parseFloat(obj.get("id").toString());
                int type = (int) Float.parseFloat(obj.get("type").toString());
                Boolean email = Boolean.valueOf(obj.get("email").toString());
                String couleur = obj.get("couleur").toString();
                String timezone = obj.get("timezone").toString();
                int format = (int) Float.parseFloat(obj.get("format").toString());
                calendriers.add(new Calendrier(id, new User(Result.fromContent(obj).getAsInteger("uid/id")), type, email, couleur, timezone, format));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return calendriers;
    }
}
