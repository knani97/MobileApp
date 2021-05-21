/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctourna.views;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.MultiButton;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.LEFT;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.doctourna.models.Video;
import com.doctourna.services.VideoService;
import com.mycompany.myapp.MyApplication;

/**
 *
 * @author yessi
 */
public class FormHome extends Form {
    
     Resources theme = UIManager.initFirstTheme("/theme");
     //Button AjoutMedicament = new Button("Ajouter");
    Resources res;
    public FormHome(Form previous)
    {
           super("Video",BoxLayout.y());
          
         
             this.add(new InfiniteProgress());
        Display.getInstance().scheduleBackgroundTask(() -> {
            // this will take a while...

            Display.getInstance().callSerially(() -> {
                this.removeAll();
             for (Video v : new VideoService().findAll()) {
                
                     this.add(addItem_Video(v));
           

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
                this.getContentPane().animateLayout(150);
            }
        }, 4);
        
             
              
             
               this.getToolbar().addCommandToOverflowMenu("back", null, ev -> {
           new MyApplication().start();
        });
    }
    
     public MultiButton  addItem_Video(Video v) {
          MultiButton m = new MultiButton();
 m.setTextLine1("Titre: " + v.getTitre());
        m.setTextLine2("Source: "+v.getSource());
        m.setTextLine3("Gratuit "+String.valueOf(v.getPaye()));
             m.setTextLine4("Prix :"+String.valueOf(v.getPrix())+" DT");
                  m.setTextLine4("Note :"+String.valueOf(v.getNote()));
                  // m.setTextLine5(String.valueOf(v.getPanier_id()));
                  
    
        
       
        
  
    
     

      
        return m;

    }
    
}
