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
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.cyberclan.doctourna.utils.ToolbarInitializer;
import com.mycompany.Models.Medicament;
import com.mycompany.Services.Medicament_Service;
import com.mycompany.myapp.MyApplication;
import java.io.IOException;

/**
 *
 * @author user
 */
public class Medicament_Sans_Categorie_Form  extends Form {

    Resources theme = UIManager.initFirstTheme("/theme");
     //Button AjoutMedicament = new Button("Ajouter");
    Resources res;
    
    public Form current = new Form();
    
    public Medicament_Sans_Categorie_Form(Form previous)
    {
           super("Medicaments",BoxLayout.y());
           current = this;
          
          // this.add(AjoutMedicament);
          // this.addComponent(AjoutMedicament);
          /* AjoutMedicament.addActionListener(lll->{
           
           new AjoutMedicament(this).show();
           } );   
           */
             this.add(new InfiniteProgress());
        Display.getInstance().scheduleBackgroundTask(() -> {
            // this will take a while...

            Display.getInstance().callSerially(() -> {
                this.removeAll();
                initMenu();
             for (Medicament c : new Medicament_Service().findAll()) {
                
                     this.add(addItem_Medicament(c));
                 Label lsupprimer = new Label("");
     lsupprimer.setUIID("NewsTopLine");
     Style supprimerStyle = new Style(lsupprimer.getUnselectedStyle());
     supprimerStyle.setFgColor(0xf21f1f);
     FontImage supprimerImage = FontImage.createMaterial(FontImage.MATERIAL_DELETE,supprimerStyle);
      lsupprimer.setIcon(supprimerImage);
      lsupprimer.setTextPosition(RIGHT);
      
      
       Label lmodifier = new Label("");
     lmodifier.setUIID("NewsTopLine");
     Style modifierStyle = new Style(lmodifier.getUnselectedStyle());
     modifierStyle.setFgColor(0xf7ad02);
         FontImage modifierImage = FontImage.createMaterial(FontImage.MATERIAL_MODE_EDIT,modifierStyle);
     lmodifier.setIcon(modifierImage);
     lmodifier.setTextPosition(LEFT);
     
     lmodifier.addPointerPressedListener(l->{
      //System.out.println("hello update");
     new  ModifierMedicament(res,c).show();
      
      
     } );
     
     
      lsupprimer.addPointerPressedListener(l->{
          Dialog dig =new Dialog("Suppression");
          if(dig.show("suppression","Voulez vous supprimer ce medicament ?","Annuler","Oui")){
              dig.dispose();
          }
          else{
              dig.dispose();
              if(Medicament_Service.getInstance().SupprimerAbsence(c.getId())){
                  new Medicament_Sans_Categorie_Form(this);
              }
          }
          
      } );
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
           //new MyApplication().start();
           
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
        btn1.setUIID("ButtonNonSelect");
        Button btn2 = new Button("");
        btn2.setUIID("ButtonSelect");
        Button btn3 = new Button("");
        btn3.setUIID("ButtonNonSelect");

        btn1.addActionListener((evt) -> {
            new CategorieForm(current).show();
        });
        btn2.addActionListener((evt) -> {
            /*current.removeAll();
            current.add(new Medicament_Sans_Categorie_Form(current));
            current.revalidate();*/
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
