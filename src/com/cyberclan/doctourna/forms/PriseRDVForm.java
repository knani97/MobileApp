/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyberclan.doctourna.forms;

import com.codename1.components.MultiButton;
import com.codename1.ui.Button;
import static com.codename1.ui.CN.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.cyberclan.doctourna.Manipulator;
import com.cyberclan.doctourna.models.Calendrier;
import com.cyberclan.doctourna.models.Rdv;
import com.cyberclan.doctourna.models.Tache;
import com.cyberclan.doctourna.models.User;
import com.cyberclan.doctourna.services.CalendrierService;
import com.cyberclan.doctourna.services.RdvService;
import com.cyberclan.doctourna.services.TacheService;
import com.cyberclan.doctourna.services.UserService;
import com.cyberclan.doctourna.ui.ListeRDVs;
import com.cyberclan.doctourna.utils.Emailer;
import com.cyberclan.doctourna.utils.Session;
import com.cyberclan.doctourna.utils.ToolbarInitializer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author mouhe
 */
public class PriseRDVForm {

    public Form mainForm = new Form();
    public Form current = new Form();

    private UserService us = new UserService();
    private CalendrierService cs = new CalendrierService();
    private TacheService ts = new TacheService();
    private RdvService rs = new RdvService();

    private ArrayList<User> medecins = us.getAllMedecins();
    private ArrayList<Calendrier> cals = cs.getAllCalendriers();
    private MultiButton disposList = new MultiButton();

    private int medId;
    private int tacheId;

    private Resources theme;

    public void show() {
        current = new Form("Prise RDV", BoxLayout.y());
        EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(current.getWidth(), current.getWidth() / 2, 0xffff0000), true);
        URLImage background = URLImage.createToStorage(placeholder, "background.png",
                "file:///C:/Users/mouhe/OneDrive/Documents/NetBeansProjects/DOCTOURNA/src/images/background.png");
        background.fetch();
        current.getUnselectedStyle().setBgImage(background);
        current.getUnselectedStyle().setBackgroundType(Style.BACKGROUND_IMAGE_TILE_BOTH);
        current.setTintColor(50);
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
        Button btn1 = new Button("");
        btn1.setUIID("ButtonNonSelect");
        Button btn2 = new Button("");
        btn2.setUIID("ButtonSelect");

        btn1.addActionListener((evt) -> {
            com.cyberclan.doctourna.ui.ListeRDVs list = new com.cyberclan.doctourna.ui.ListeRDVs();
            list.parentForm = mainForm;
            list.showForm();
        });
        btn2.addActionListener((evt) -> {
            PriseRDVForm form = new PriseRDVForm();
            form.mainForm = mainForm;
            form.show();
        });

        menuForm.add(btn1);
        menuForm.add(btn2);

        try {

            Image iconNews = Image.createImage("file:///C:/Users/mouhe/Downloads/DoctournaApp/DoctournaApp/src/Images/news.png");
            btn1.setIcon(iconNews.scaledLargerRatio(95, 95));
        } catch (IOException ex) {

        }
        try {

            Image iconArticleP = Image.createImage("file:///C:/Users/mouhe/Downloads/DoctournaApp/DoctournaApp/src/Images/articleP.png");
            btn2.setIcon(iconArticleP.scaledLargerRatio(95, 95));
        } catch (IOException ex) {

        }
        current.add(menuForm);
    }

    public void init() {
        Container desc = new Container(BoxLayout.x());
        Label lblDesc = new Label("Description:");
        TextArea txtAreaDesc = new TextArea();
        desc.add(lblDesc).add(txtAreaDesc);
        Button btnAjout = new Button("Ajouter");
        btnAjout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Rdv rdv = new Rdv();
                rdv.setPatientId(us.getUser(Session.getId()));
                rdv.setMedecinId(us.getUser(medId));
                rdv.setTacheDispoId(ts.getTache(tacheId));
                rdv.setDescription(txtAreaDesc.getText());
                rs.addRdv(rdv, cs.getCalendrierByUid(Session.getId()).getId());
                Dialog.show("Rappel", "Voulez-vous reçevoir un email ?", "Oui", "Non");
                try {
                    Emailer.send(Session.getEmail(), "Vous avez pris un RDV avec Dr. "
                            + rdv.getMedecinId().getNom() + " " + rdv.getMedecinId().getPrenom()
                            + " le " + Manipulator.toPureDate(rdv.getTacheDispoId().getDate())
                            + " à " + Manipulator.toTimeString(rdv.getTacheDispoId().getDate()));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                mainForm.showBack();
            }
        });
        medId = 2;
        initPicker1();
        initPicker2();

        current.add(desc).
                add(btnAjout);
    }

    public void initPicker1() {
        ArrayList<String> characters = new ArrayList<String>();
        ArrayList<String> actors = new ArrayList<String>();
        ArrayList<String> ids = new ArrayList<String>();
        int size = Display.getInstance().convertToPixels(7);
        EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(size, size, 0xffcccccc), true);
        ArrayList<Image> pictures = new ArrayList<Image>();

        for (User u : medecins) {
            characters.add(u.getNom() + " " + u.getPrenom());
            actors.add(u.getRoles());
            pictures.add(URLImage.createToStorage(placeholder, u.getId().toString(), "https://www.francealzheimer.org/vendee/wp-content/uploads/sites/96/2020/10/rdv.jpg"));
            ids.add(u.getId().toString());
        }

        MultiButton b = new MultiButton("Selectionner un médecin");
        b.addActionListener(e -> {
            Dialog d = new Dialog();
            d.setLayout(BoxLayout.y());
            d.getContentPane().setScrollableY(true);
            for (int iter = 0; iter < characters.toArray().length; iter++) {
                MultiButton mb = new MultiButton(characters.get(iter));
                mb.setTextLine2(actors.get(iter));
                mb.setUIIDLine3(ids.get(iter));
                mb.setIcon(pictures.get(iter));
                d.add(mb);
                mb.addActionListener(ee -> {
                    b.setTextLine1(mb.getTextLine1());
                    b.setTextLine2(mb.getTextLine2());
                    b.setIcon(mb.getIcon());
                    d.dispose();
                    b.revalidate();
                    medId = Integer.parseInt(mb.getUIIDLine3());
                    System.out.println(medId);
                    refreshPicker2();
                });
            }
            d.showPopupDialog(b);
        });

        current.add(b);
    }

    public void initPicker2() {
        ArrayList<String> characters = new ArrayList<String>();
        ArrayList<String> actors = new ArrayList<String>();
        ArrayList<String> ids = new ArrayList<String>();
        int size = Display.getInstance().convertToPixels(7);
        EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(size, size, 0xffcccccc), true);
        ArrayList<Image> pictures = new ArrayList<Image>();
        Calendrier cal = null;
        String msg = new String();

        if (medId != 0) {
            for (Calendrier c : cals) {
                if (c.getUidId().getId() == medId) {
                    cal = c;
                }
            }
            if (cal != null) {
                ArrayList<Tache> dispos = ts.getDisposByCal(cal.getId());
                if (dispos.isEmpty()) {
                    msg = "Pas de disponibiltiés trouvés";
                } else {
                    for (Tache t : dispos) {
                        characters.add(Manipulator.toPureDate(t.getDate()));
                        actors.add(Manipulator.toTimeString(t.getDate()));
                        pictures.add(URLImage.createToStorage(placeholder, t.getId().toString(), "https://static.thenounproject.com/png/2263783-200.png"));
                        ids.add(t.getId().toString());
                    }
                    msg = "Selectionner une date";
                }
            }
        }

        MultiButton b = new MultiButton(msg);
        b.addActionListener(e -> {
            Dialog d = new Dialog();
            d.setLayout(BoxLayout.y());
            d.getContentPane().setScrollableY(true);
            for (int iter = 0; iter < characters.toArray().length; iter++) {
                MultiButton mb = new MultiButton(characters.get(iter));
                mb.setTextLine2(actors.get(iter));
                mb.setUIIDLine3(ids.get(iter));
                mb.setIcon(pictures.get(iter));
                d.add(mb);
                mb.addActionListener(ee -> {
                    b.setTextLine1(mb.getTextLine1());
                    b.setTextLine2(mb.getTextLine2());
                    b.setIcon(mb.getIcon());
                    d.dispose();
                    b.revalidate();
                    tacheId = Integer.parseInt(mb.getUIIDLine3());
                });
            }
            d.showPopupDialog(b);
        });

        disposList = b;
        current.add(b);
    }

    public void refreshPicker2() {
        ArrayList<String> characters = new ArrayList<String>();
        ArrayList<String> actors = new ArrayList<String>();
        ArrayList<String> ids = new ArrayList<String>();
        int size = Display.getInstance().convertToPixels(7);
        EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(size, size, 0xffcccccc), true);
        ArrayList<Image> pictures = new ArrayList<Image>();
        Calendrier cal = null;
        String msg = new String();

        if (medId != 0) {
            for (Calendrier c : cals) {
                if (c.getUidId().getId() == medId) {
                    cal = c;
                }
            }
            if (cal != null) {
                ArrayList<Tache> dispos = ts.getDisposByCal(cal.getId());
                if (dispos.isEmpty()) {
                    msg = "Pas de disponibiltiés trouvés";
                } else {
                    for (Tache t : dispos) {
                        characters.add(Manipulator.toPureDate(t.getDate()));
                        actors.add(Manipulator.toTimeString(t.getDate()));
                        pictures.add(URLImage.createToStorage(placeholder, t.getId().toString(), "https://static.thenounproject.com/png/2263783-200.png"));
                        ids.add(t.getId().toString());
                    }
                    msg = "Selectionner une date";
                }
            } else {
                msg = "Pas de disponibiltiés trouvés";
            }
        }

        disposList = new MultiButton(msg);
        disposList.addActionListener(e -> {
            Dialog d = new Dialog();
            d.setLayout(BoxLayout.y());
            d.getContentPane().setScrollableY(true);
            for (int iter = 0; iter < characters.toArray().length; iter++) {
                MultiButton mb = new MultiButton(characters.get(iter));
                mb.setTextLine2(actors.get(iter));
                mb.setUIIDLine3(ids.get(iter));
                mb.setIcon(pictures.get(iter));
                d.add(mb);
                mb.addActionListener(ee -> {
                    disposList.setTextLine1(mb.getTextLine1());
                    disposList.setTextLine2(mb.getTextLine2());
                    disposList.setIcon(mb.getIcon());
                    d.dispose();
                    disposList.revalidate();
                    tacheId = Integer.parseInt(mb.getUIIDLine3());
                });
            }
            d.showPopupDialog(disposList);
        });
        current.revalidate();
    }
}
