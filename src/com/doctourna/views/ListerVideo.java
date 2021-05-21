/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctourna.views;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.MultiButton;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.cyberclan.doctourna.utils.ToolbarInitializer;
import com.doctourna.models.Video;
import com.doctourna.services.VideoService;

import com.mycompany.myapp.MyApplication;

/**
 *
 * @author user
 */
public class ListerVideo extends Form {

    Resources theme = UIManager.initFirstTheme("/theme");
    //Button AjoutMedicament = new Button("Ajouter");
    Resources res;

    public ListerVideo(Form previous) {
        super("Video", BoxLayout.y());

        this.add(new InfiniteProgress());
        Display.getInstance().scheduleBackgroundTask(() -> {
            // this will take a while...

            Display.getInstance().callSerially(() -> {
                this.removeAll();
                for (Video v : new VideoService().findAll()) {

                    this.add(addItem_Video(v));
                    Label lsupprimer = new Label("");
                    lsupprimer.setUIID("NewsTopLine");
                    Style supprimerStyle = new Style(lsupprimer.getUnselectedStyle());
                    supprimerStyle.setFgColor(0xf21f1f);
                    FontImage supprimerImage = FontImage.createMaterial(FontImage.MATERIAL_DELETE, supprimerStyle);
                    lsupprimer.setIcon(supprimerImage);
                    lsupprimer.setTextPosition(RIGHT);

                    /*  Label lmodifier = new Label("");
     lmodifier.setUIID("NewsTopLine");
     Style modifierStyle = new Style(lmodifier.getUnselectedStyle());
     modifierStyle.setFgColor(0xf7ad02);
         FontImage modifierImage = FontImage.createMaterial(FontImage.MATERIAL_MODE_EDIT,modifierStyle);
     lmodifier.setIcon(modifierImage);
     lmodifier.setTextPosition(LEFT);
     
     lmodifier.addPointerPressedListener(l->{
      //System.out.println("hello update");
     new  ModifierMedicament(res,c).show();
                     */
                    Label lmodifier = new Label("");
                    lmodifier.setUIID("NewsTopLine");
                    Style modifierStyle = new Style(lmodifier.getUnselectedStyle());
                    modifierStyle.setFgColor(0xf7ad02);
                    FontImage modifierImage = FontImage.createMaterial(FontImage.MATERIAL_MODE_EDIT, modifierStyle);
                    lmodifier.setIcon(modifierImage);
                    lmodifier.setTextPosition(LEFT);

                    lmodifier.addPointerPressedListener(l -> {
                        new ModifierVideo(res, v).show();
                        System.out.println("HI");

                    });

                    lsupprimer.addPointerPressedListener(l -> {
                        Dialog dig = new Dialog("Suppression");
                        if (dig.show("suppression", "Voulez vous supprimer ce Video ?", "Annuler", "Oui")) {
                            dig.dispose();
                        } else {
                            dig.dispose();
                            if (VideoService.getInstance().SupprimerVideo(v.getId())) {
                                new ListerVideo(this);
                            }
                        }

                    });
                    Container box = BoxLayout.encloseX((lmodifier),
                            (lsupprimer));
                    this.add(box);
                    //this.add(lsupprimer);

                }

                this.revalidate();
            });
        });

        this.getToolbar().addSearchCommand(e -> {
            String text = (String) e.getSource();
            if (text == null || text.length() == 0) {
                // clear search
                for (Component cmp : this.getContentPane()) {
                    cmp.setHidden(false);
                    cmp.setVisible(true);
                }
                this.getContentPane().animateLayout(150);
            } else {
                text = text.toLowerCase();
                for (Component cmp : this.getContentPane()) {
                    if (cmp instanceof MultiButton) {
                        MultiButton mb = (MultiButton) cmp;
                        //MultiButton mb=new MultiButton(cmp);
                        String line1 = mb.getTextLine1();
                        String line2 = mb.getTextLine2();
                        mb.setUIIDLine1("libC");
                        mb.setUIIDLine2("btn");
                        boolean show = line1 != null && line1.toLowerCase().indexOf(text) > -1
                                || line2 != null && line2.toLowerCase().indexOf(text) > -1;
                        mb.setHidden(!show);
                        mb.setVisible(show);
                    }
                }
                this.getContentPane().animateLayout(150);
            }
        }, 4);

        this.getToolbar().addCommandToOverflowMenu("back", null, ev -> {
            new MyApplication().start();
        });

        ToolbarInitializer.initToolbar(this);
    }

    public MultiButton addItem_Video(Video v) {
        MultiButton m = new MultiButton();
        m.setTextLine1("Titre: " + v.getTitre());
        m.setTextLine2("Source: " + v.getSource());
        m.setTextLine3("Gratuit " + String.valueOf(v.getPaye()));
        m.setTextLine4("Prix :" + String.valueOf(v.getPrix()) + " DT");
        m.setTextLine4("Note :" + String.valueOf(v.getNote()));
        // m.setTextLine5(String.valueOf(v.getPanier_id()));

        return m;

    }
}
