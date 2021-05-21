/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyberclan.doctourna.forms;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.spinner.Picker;
import com.cyberclan.doctourna.Manipulator;
import com.cyberclan.doctourna.models.Disponibilite;
import com.cyberclan.doctourna.services.CalendrierService;
import com.cyberclan.doctourna.services.TacheService;
import com.cyberclan.doctourna.services.UserService;
import com.cyberclan.doctourna.utils.Session;
import com.cyberclan.doctourna.utils.ToolbarInitializer;
import java.io.IOException;
import java.util.Date;

/**
 *
 * @author mouhe
 */
public class AjoutDispoForm {

    public Form mainForm = new Form();
    public Form current = new Form("Ajout Disponibilités", BoxLayout.y());

    private UserService us = new UserService();
    private CalendrierService cs = new CalendrierService();
    private TacheService ts = new TacheService();

    private int medId;

    public void show() {
        EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(current.getWidth(), current.getWidth() / 2, 0xffff0000), true);
        URLImage background = URLImage.createToStorage(placeholder, "background.png",
                "file:///C:/Users/mouhe/OneDrive/Documents/NetBeansProjects/DOCTOURNA/src/images/background.png");
        background.fetch();
        current.getUnselectedStyle().setBgImage(background);
        current.getUnselectedStyle().setBackgroundType(Style.BACKGROUND_IMAGE_TILE_BOTH);
        initMenu();
        ToolbarInitializer.initToolbar(current);
        init();
        current.show();
    }

    public void initMenu() {
        Container menuForm = new Container(new GridLayout(1, 4));
        menuForm.setScrollableX(true);
        menuForm.setScrollVisible(true);
        menuForm.setScrollableY(false);
        menuForm.setUIID("ContainerWhite");
        Button btn1 = new Button("Liste RDVs");
        btn1.setUIID("ButtonNonSelect");
        Button btn2 = new Button(Session.getType() == 1 ? "Prise RDV" : "Config Dispo");
        btn2.setUIID("ButtonSelect");

        btn1.addActionListener((evt) -> {
            com.cyberclan.doctourna.ui.ListeRDVs list = new com.cyberclan.doctourna.ui.ListeRDVs();
            list.parentForm = current;
            list.showForm();
        });
        btn2.addActionListener((evt) -> {
            if (Session.getType() == 1) {
                PriseRDVForm priseRDVForm = new PriseRDVForm();
                priseRDVForm.mainForm = current;
                priseRDVForm.show();
            } else {
                AjoutDispoForm ajoutDispoForm = new AjoutDispoForm();
                ajoutDispoForm.mainForm = current;
                ajoutDispoForm.show();
            }
        });

        menuForm.add(btn1);
        menuForm.add(btn2);

        try {
            Image iconNews = Image.createImage("file:///C:/Users/mouhe/Downloads/DoctournaApp/DoctournaApp/src/Images/news.png");
            btn1.setIcon(iconNews.scaledLargerRatio(95, 95));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            Image iconArticleP;
            if (Session.getType() == 1) {
                iconArticleP = Image.createImage("file:///C:/Users/mouhe/Downloads/DoctournaApp/DoctournaApp/src/Images/articleP.png");
            } else {
                iconArticleP = Image.createImage("file:///C:/Users/mouhe/Downloads/DoctournaApp/DoctournaApp/src/Images/articleP.png");
            }
            btn2.setIcon(iconArticleP.scaledLargerRatio(95, 95));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        current.add(menuForm);
    }

    public void init() {
        Container dateDeb = new Container();
        Label lblDateDeb = new Label("Date début:");
        Picker pckDateDeb = new Picker();
        pckDateDeb.setType(Display.PICKER_TYPE_DATE_AND_TIME);
        dateDeb.add(lblDateDeb).add(pckDateDeb);
        Container dateFin = new Container();
        Label lblDateFin = new Label("Date fin:");
        Picker pckDateFin = new Picker();
        pckDateFin.setType(Display.PICKER_TYPE_DATE_AND_TIME);
        dateFin.add(lblDateFin).add(pckDateFin);
        Container dureeRDV = new Container();
        Label lblDureeRDV = new Label("Durée RDV:");
        Picker pckDureeRDV = new Picker();
        pckDureeRDV.setType(Display.PICKER_TYPE_TIME);
        dureeRDV.add(lblDureeRDV).add(pckDureeRDV);
        Container dureePause = new Container();
        Label lblDureePause = new Label("Durée Pause:");
        Picker pckDureePause = new Picker();
        pckDureePause.setType(Display.PICKER_TYPE_TIME);
        dureePause.add(lblDureePause).add(pckDureePause);
        Button btnAjout = new Button("Ajouter");
        btnAjout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (pckDateDeb.getDate().getTime() > pckDateFin.getDate().getTime()) {
                    Dialog.show("Erreur", "La date début est supérieur à la date fin!", "Annuler", "OK");
                } else {
                    Disponibilite dispo = new Disponibilite();
                    dispo.setStartDate(pckDateDeb.getDate());
                    dispo.setEndDate(pckDateFin.getDate());
                    //System.out.println(Manipulator.getDateFromTime(pckDureeRDV.getTime()));
                    dispo.setDureeRdv(Manipulator.getDateFromTime(pckDureeRDV.getTime()));
                    dispo.setDureePause(Manipulator.getDateFromTime(pckDureePause.getTime()));
                    //System.out.println(dispo);
                    ts.addDispo(dispo, cs.getCalendrierByUid(Session.getId()).getId());
                    mainForm.showBack();
                }
            }
        });

        current.add(dateDeb).
                add(dateFin).
                add(dureeRDV).
                add(dureePause).
                add(btnAjout);
    }
}
