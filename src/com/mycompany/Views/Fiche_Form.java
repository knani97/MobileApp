/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Views;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.MultiButton;
import com.codename1.ui.Component;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.mycompany.Models.Categorie;
import com.mycompany.Models.Fiche;
import com.mycompany.Services.Categorie_Service;
import com.mycompany.Services.Fiche_Service;
import com.mycompany.myapp.MyApplication;

/**
 *
 * @author user
 */
public class Fiche_Form  extends Form {

    Resources theme = UIManager.initFirstTheme("/theme");
    public Fiche_Form(Form previous, int id_m)
    {
           super("Fiche",BoxLayout.y());
           
           
             this.add(new InfiniteProgress());
        Display.getInstance().scheduleBackgroundTask(() -> {
            // this will take a while...

            Display.getInstance().callSerially(() -> {
                this.removeAll();
             for (Fiche c : new Fiche_Service().findAll(id_m)) {

            this.add(addItem_Fiche(c));

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
                this.getContentPane().animateLayout(150);
            }
        }, 4);
        
             
             
             
               this.getToolbar().addCommandToOverflowMenu("back", null, ev -> {
           new MyApplication().start();
        });
    }
    
     public MultiButton  addItem_Fiche(Fiche c) {
         
     
          MultiButton m = new MultiButton();
 m.setTextLine2(c.getUtilisation());
        m.setTextLine1(String.valueOf(c.getQuantite()));
   

      
     
    Fiche_Service fs = new Fiche_Service();
         fs.send8mail_Fiche(c.getId(), 1);
      
        return m;

    }
      
}
