/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Views;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.MultiButton;
import com.codename1.ui.Component;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.mycompany.Models.Categorie;
import com.mycompany.Models.Medicament;
import com.mycompany.Services.Categorie_Service;
import com.mycompany.Services.Medicament_Service;
import com.mycompany.myapp.MyApplication;

/**
 *
 * @author user
 */
public class Medicament_Form extends Form {

    Resources theme = UIManager.initFirstTheme("/theme");
    public Medicament_Form(Form previous,int id_cat)
    {
           super("Medicaments",BoxLayout.y());
           
           
             this.add(new InfiniteProgress());
        Display.getInstance().scheduleBackgroundTask(() -> {
            // this will take a while...

            Display.getInstance().callSerially(() -> {
                this.removeAll();
             for (Medicament c : new Medicament_Service().findAll()) {
                 System.out.println(id_cat+" "+ c.getId_Categorie());
                 if (c.getId_Categorie()==id_cat)
                 {
                     this.add(addItem_Medicament(c));
                 }

            

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
    
     public MultiButton  addItem_Medicament(Medicament c) {
          MultiButton m = new MultiButton();
 m.setTextLine1(c.getNomMed());
        m.setTextLine2(c.getFournisseur());
             m.setTextLine3(String.valueOf(c.getPrix_achat())+" DT");
                  m.setTextLine4(String.valueOf(c.getPoids()));
    String url = "http://127.0.0.1:8000/photos/" + c.getImage_med();
      Image imge;
        EncodedImage enc;
       
        enc = EncodedImage.createFromImage(theme.getImage("round.png"), false);
        imge = URLImage.createToStorage(enc, url, url);
        m.setIcon(imge);
    m.setEmblem(theme.getImage("info.png"));
     m.addActionListener(l
                -> {
         if (c.getFiche_exist().equals(false))
         {
              Dialog.show("N'existe pas", "N'existe pas", "OK", null);
         }
         else
         {
             new Fiche_Form(this, c.getId()).show();
         }
         
     
     }
     ); 
        

      
        return m;

    }
      
}
