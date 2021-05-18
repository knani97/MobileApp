/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyberclan.doctourna.forms;

import com.codename1.components.MultiButton;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import com.cyberclan.doctourna.models.Tache;
import com.cyberclan.doctourna.services.CalendrierService;
import com.cyberclan.doctourna.services.TacheService;
import com.cyberclan.doctourna.utils.Session;
import java.util.Date;

/**
 *
 * @author mouhe
 */
public class EditTacheForm {
    
    public Form mainForm = new Form();
    public Form current = new Form();
    public Tache tacheOrig = new Tache();
    public Date currentDate;
    public com.cyberclan.doctourna.ui.Calendar tachesList;
    
    private CalendrierService cs = new CalendrierService();
    private TacheService ts = new TacheService();
    
    private int type;
    
    public EditTacheForm(Tache tache) {
        this.tacheOrig = tache;
    }

    public void show() {
        current = new Form("Ajout Tâche", BoxLayout.y());
        init();
        current.show();
    }

    public void init() {
        Container libelle = new Container(BoxLayout.x());
        Label lblLibelle = new Label("Libelle:");
        TextField txtLibelle = new TextField();
        txtLibelle.setText(tacheOrig.getLibelle());
        libelle.add(lblLibelle).add(txtLibelle);
        Container description = new Container(BoxLayout.x());
        Label lblDescription = new Label("Description:");
        TextField txtDescription = new TextField();
        txtDescription.setText(tacheOrig.getDescription());
        description.add(lblDescription).add(txtDescription);
        Container date = new Container(BoxLayout.x());
        Label lblDate = new Label("Date:");
        Picker pckDate = new Picker();
        pckDate.setType(Display.PICKER_TYPE_DATE_AND_TIME);
        pckDate.setDate(currentDate);
        date.add(lblDate).add(pckDate);
        Container duree = new Container(BoxLayout.x());
        Label lblDuree = new Label("Duree:");
        Picker pckDuree = new Picker();
        pckDuree.setType(Display.PICKER_TYPE_TIME);
        duree.add(lblDuree).add(pckDuree);
        Button btnAjout = new Button("Ajouter");
        btnAjout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Tache tache = new Tache();
                tache.setId(tacheOrig.getId());
                tache.setCalendrier(cs.getCalendrierByUid(Session.getId()));
                tache.setLibelle(txtLibelle.getText());
                tache.setDescription(txtDescription.getText());
                tache.setDate(pckDate.getDate());
                tache.setDuree(new Date());
                //tache.setDuree(pckDuree.getDate());
                tache.setType(String.valueOf(type));
                tache.setCouleur("black");
                ts.editTache(tache);
                tachesList.reinitTaches();
                tachesList.refresh(currentDate);
                mainForm.showBack();
            }
        });
        initPicker();

        current.add(libelle).
                add(description).
                add(date).
                add(duree).
                add(btnAjout);
    }

    public void initPicker() {
        String[] characters = {"RDV Perso", "Prise Médicament", "Personnelle", "Disponibilité"};
        String[] actors = {"Un rendez-vous personnel", "Une prise de médicament", "Autres...", "Une date disponible"};
        int size = Display.getInstance().convertToPixels(7);
        EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(size, size, 0xffcccccc), true);
        Image[] pictures = {
            URLImage.createToStorage(placeholder, "rdvperso", "https://www.francealzheimer.org/vendee/wp-content/uploads/sites/96/2020/10/rdv.jpg"),
            URLImage.createToStorage(placeholder, "prisemed", "https://clinique-iris-lyon.ramsaygds.fr/sites/default/files/biblio/domaines/clinique_iris_marcy/medicament.jpg"),
            URLImage.createToStorage(placeholder, "perso", "https://www.linksoft.fr/wp-content/uploads/2017/05/ww.png"),
            URLImage.createToStorage(placeholder, "dispo", "https://image.pitchbook.com/WIN4E8aKYw2zaFjYH7Ui0azp49K1602090850490_200x200")
        };

        MultiButton b = new MultiButton("Selectionner un type");
        b.addActionListener(e -> {
            Dialog d = new Dialog();
            d.setLayout(BoxLayout.y());
            d.getContentPane().setScrollableY(true);
            for (int iter = 0; iter < characters.length; iter++) {
                MultiButton mb = new MultiButton(characters[iter]);
                mb.setTextLine2(actors[iter]);
                mb.setIcon(pictures[iter]);
                d.add(mb);
                mb.addActionListener(ee -> {
                    b.setTextLine1(mb.getTextLine1());
                    b.setTextLine2(mb.getTextLine2());
                    b.setIcon(mb.getIcon());
                    d.dispose();
                    b.revalidate();
                    if (b.getTextLine1().contains("RDV Perso"))
                        type = 5;
                    else if (b.getTextLine1().contains("Prise Médicament"))
                        type = 2;
                    else if (b.getTextLine1().contains("Personnelle"))
                        type = 3;
                    else if (b.getTextLine1().contains("Disponibilité"))
                        type = 4;
                });
            }
            d.showPopupDialog(b);
        });
        
        current.add(b);
    }
}
