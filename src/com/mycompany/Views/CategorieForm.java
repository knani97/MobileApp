/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Views;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.MultiButton;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.cyberclan.doctourna.utils.ToolbarInitializer;
import com.mycompany.Models.Categorie;
import com.mycompany.Services.Categorie_Service;
import com.mycompany.myapp.MyApplication;
import java.io.IOException;

/**
 *
 * @author user
 */
public class CategorieForm extends Form {

    Resources theme = UIManager.initFirstTheme("/theme");

    public Form current = new Form();

    public CategorieForm(Form previous) {
        super("Categories", BoxLayout.y());
        current = this;

        this.add(new InfiniteProgress());
        Display.getInstance().scheduleBackgroundTask(() -> {
            // this will take a while...

            Display.getInstance().callSerially(() -> {
                this.removeAll();
                initMenu();
                for (Categorie c : new Categorie_Service().findAll()) {

                    this.add(addItem_Categorie(c));

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

        this.getToolbar().addCommandToOverflowMenu("Retour", null, ev -> {
            new MyApplication().start();
        });
        ToolbarInitializer.initToolbar(this);
    }

    public void initMenu() {
        Container menuForm = new Container(new GridLayout(1, 4));
        menuForm.setScrollableX(true);
        menuForm.setScrollVisible(true);
        menuForm.setScrollableY(false);
        menuForm.setUIID("ContainerWhite");
        Button btn1 = new Button("");
        btn1.setUIID("ButtonSelect");
        Button btn2 = new Button("");
        btn2.setUIID("ButtonNonSelect");
        Button btn3 = new Button("");
        btn3.setUIID("ButtonNonSelect");

        btn1.addActionListener((evt) -> {
            new CategorieForm(current).show();
        });
        btn2.addActionListener((evt) -> {
            new Medicament_Sans_Categorie_Form(current).show();
        });
        btn3.addActionListener((evt) -> {
            new AjoutMedicament(current).show();
        });

        menuForm.add(btn1);
        menuForm.add(btn2);
        menuForm.add(btn3);

        try {
            Image iconNews = Image.createImage("file:///C:/Users/mouhe/Downloads/DoctournaApp/DoctournaApp/src/Images/news.png");
            btn1.setIcon(iconNews.scaledLargerRatio(95, 95));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            Image iconArticleP = Image.createImage("file:///C:/Users/mouhe/Downloads/DoctournaApp/DoctournaApp/src/Images/articleP.png");
            btn2.setIcon(iconArticleP.scaledLargerRatio(95, 95));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            Image iconArticleP = Image.createImage("file:///C:/Users/mouhe/Downloads/DoctournaApp/DoctournaApp/src/Images/articleP.png");
            btn3.setIcon(iconArticleP.scaledLargerRatio(95, 95));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        current.add(menuForm);
    }

    public MultiButton addItem_Categorie(Categorie c) {
        MultiButton m = new MultiButton();
        m.setTextLine1(c.getNom());
        m.setTextLine2(c.getDescription());

        m.setEmblem(theme.getImage("info.png"));

        m.addActionListener(l
                -> {
            new Medicament_Form(this, c.getId()).show();

        });

        return m;

    }

}
