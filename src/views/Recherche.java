/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

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
import com.mycompany.myapp.MyApplication;
import models.Medicament;
import models.Pharmacie;
import services.PharmacieService;
import services.RechercheService;

/**
 *
 * @author Meriem
 */
public class Recherche extends Form {
      Resources theme = UIManager.initFirstTheme("/theme");
     //Button AjoutMedicament = new Button("Ajouter");
    Resources res;
    public Recherche(Form previous)
    {
           super("Pharmacie",BoxLayout.y());
          
         
             this.add(new InfiniteProgress());
        Display.getInstance().scheduleBackgroundTask(() -> {
            // this will take a while...

            Display.getInstance().callSerially(() -> {
                this.removeAll();
               PharmacieService rs = new PharmacieService ();
                Medicament medicament=new Medicament("test");
             for(Pharmacie Listmed : rs.findAll())
             {
                
                     this.add(addItem_Pharmacie(Listmed));
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
     //new  ModifierMedicament(res,c).show();
      
      
     } );
     
     
      lsupprimer.addPointerPressedListener(l->{
          Dialog dig =new Dialog("Suppression");
          if(dig.show("suppression","Voulez vous supprimer ce medicament ?","Annuler","Oui")){
              dig.dispose();
          }
          else{
              dig.dispose();
//              if(Medicament_Service.getInstance().SupprimerAbsence(c.getId())){
//                  new Medicament_Sans_Categorie_Form(this);
//              }
          }
          
      } );
      Container box = BoxLayout.encloseX((lmodifier), 
        (lsupprimer));
     // this.add(lmodifier);
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
                this.removeAll();
                RechercheService rs = new RechercheService();
                Medicament med = new Medicament();
                med.setNomMed(text);
                for (Pharmacie Listmed : rs.medicament(med)) {
                    MultiButton mb = new MultiButton();
                    //MultiButton mb=new MultiButton(cmp);
                    mb.setTextLine1(Listmed.getNom());
                    mb.setTextLine2(Listmed.getAdresse());
                    mb.setUIIDLine1("libC");
                    mb.setUIIDLine2("btn");
                    /*String line1 = mb.getTextLine1();
                    String line2 = mb.getTextLine2();
                    mb.setUIIDLine1("libC");
                    mb.setUIIDLine2("btn");
                    boolean show = line1 != null && line1.toLowerCase().indexOf(text) > -1
                            || line2 != null && line2.toLowerCase().indexOf(text) > -1;
                    mb.setHidden(!show);
                    mb.setVisible(show);*/
                    this.add(mb);
                }
                this.getContentPane().animateLayout(150);
            }
        }, 4);
        
            
             
               this.getToolbar().addCommandToOverflowMenu("back", null, ev -> {
           new MyApplication().start();
        });
    }
    
     public MultiButton  addItem_Pharmacie(Pharmacie p) {
          MultiButton m = new MultiButton();
 m.setTextLine1("Nom "+p.getNom());
        m.setTextLine2("Adresse "+p.getAdresse());
         m.setTextLine3("Gouvernourat "+p.getGouvernourat());
      
                  
    
        
       
        
  
    
     

      
        return m;

    }
    
}
