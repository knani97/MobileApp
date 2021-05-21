/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.codename1.processing.Result;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Dialog;
import com.codename1.ui.TextField;
import com.codename1.ui.util.Resources;
import com.company.utils.Statics;
import com.cyberclan.doctourna.Manipulator;
import com.cyberclan.doctourna.models.Calendrier;
import com.cyberclan.doctourna.models.Tache;
import com.cyberclan.doctourna.ui.HomePage;
import com.cyberclan.doctourna.utils.Session;
import com.mycompany.gui.NewsfeedForm;
import com.mycompany.gui.SignUpForm;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.Map;

/**
 *
 * @author LENOVO
 */
public class ServiceUtilisateur {

    public static ServiceUtilisateur insatnce = null;

    public static boolean resultOK = true;
    String json;

    private ConnectionRequest req;

    public static ServiceUtilisateur getInstance() {
        if (insatnce == null) {
            insatnce = new ServiceUtilisateur();
        }
        return insatnce;
    }

    public ServiceUtilisateur() {

        req = new ConnectionRequest();

    }

    //Signup
    public void signup(TextField Nom, TextField Prenom, TextField email, TextField Password, TextField ConfirmPassword, ComboBox<String> roles, Resources res) {

        //Role 
        String type = roles.getSelectedItem().toString().contains("Patient") ? "1" : "2";
        String url = Statics.BASE_URL + "/user/signup?email=" + email.getText().toString() + "&roles=" + roles.getSelectedItem().toString() + "&password=" + Password.getText().toString() + "&nom=" + Nom.getText().toString() + "&prenom=" + Prenom.getText().toString() + "&type=" + type;

        req.setUrl(url);
        // control saisir
        if (email.getText().equals("") && Password.getText().equals("") && Nom.getText().equals("") && Prenom.getText().equals("")) {
            Dialog.show("Erreur", "Veuillez repmlir les champs", "OK", null);

        }
        //URL Exuction
        req.addResponseListener((e) -> {

            byte[] data = (byte[]) e.getMetaData();
            String responseData = new String(data);

            System.out.println("data===>" + responseData);

        }
        );
        NetworkManager.getInstance().addToQueueAndWait(req);

    }

    public void signin(TextField email, TextField Password, Resources rs) {
        String url = Statics.BASE_URL + "/user/signin?email=" + email.getText().toString() + "&password=" + Password.getText().toString();
        req.setUrl(url);
        req.addResponseListener((e) -> {
            JSONParser j = new JSONParser();
            String json = new String(req.getResponseData()) + "";
            try {
                if (json.equals("failed")) {
                    Dialog.show("Echec d'authentification", "Email or Password are wrong", "OK", null);

                } else {
                    System.out.println("data===" + json);
                    Map<String, Object> user = j.parseJSON(new CharArrayReader(json.toCharArray()));

                    if (user.size() > 0) {
                        int id = (int) Float.parseFloat(user.get("id").toString());
                        int type = (int) Float.parseFloat(user.get("type").toString());
                        String nom = user.get("nom").toString();
                        String prenom = user.get("prenom").toString();
                        String userEmail = user.get("email").toString();

                        Session.setId(id);
                        Session.setType(type);
                        Session.setEmail(userEmail);
                        Session.setNom(nom);
                        Session.setPrenom(prenom);
                        
                        new HomePage().show();
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        });

        NetworkManager.getInstance().addToQueueAndWait(req);
    }

    public String getPasswordByEmail(String email, Resources rs) {

        String url = Statics.BASE_URL + "/user/getPasswordByEmail?email=" + email;

        req = new ConnectionRequest(url, false);
        req.setUrl(url);
        req.addResponseListener((e) -> {
            JSONParser j = new JSONParser();
            json = new String(req.getResponseData()) + "";
            try {
                System.out.println("data==" + json);
                Map<String, Object> password = j.parseJSON(new CharArrayReader(json.toCharArray()));

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return json;

    }

}
