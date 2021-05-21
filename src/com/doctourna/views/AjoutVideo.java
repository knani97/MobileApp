/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctourna.views;

import com.codename1.capture.Capture;
import com.codename1.components.InfiniteProgress;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Component;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.doctourna.models.Video;
import com.doctourna.services.VideoService;
import java.io.IOException;
import java.util.Date;

/**
 *
 * @author yessi
 */
public class AjoutVideo extends Form {
     String file ;
     Resources theme = UIManager.initFirstTheme("/theme");
     Form current;
       public AjoutVideo(Form previous){
             super("Video",BoxLayout.y());
             Toolbar tb = new Toolbar(true);
             current = this;
             setToolbar(tb);
             getTitleArea().setUIID("container");
             setTitle("Ajouter Video");
             getContentPane().setScrollVisible(false);
             
             TextField titre = new TextField("","saisir le Titre");
             titre.setUIID("TextFieldBlack");
             addStringValue("Titre",titre);
             
              TextField source = new TextField("","saisir Source");
             source.setUIID("TextFieldBlack");
             addStringValue("Source",source);
             
             CheckBox paye = new CheckBox("Gratuit");
             paye.setUIID("TextFieldBlack");
             addStringValue("Paye",paye);
             
             TextField prix = new TextField("","saisir le prix");
             prix.setUIID("TextFieldBlack");
             addStringValue("Prix",prix);
             
             TextField note = new TextField("","saisir la note");
             note.setUIID("TextFieldBlack");
             addStringValue("note",note);
             
             TextField panierID = new TextField("","saisir panier_id");
             panierID.setUIID("TextFieldBlack");
             addStringValue("Panier_id",panierID);
             
                 
                 
         
           
             Button btnAjout = new Button("Ajouter");
             addStringValue("",btnAjout);
             
            btnAjout.addActionListener((e)-> {
                 
                 try{
                     
                     if(titre.getText()==""||source.getText()==""){
                         Dialog.show("Remplir tous les champs","","Annuler","ok");
                     }else{
                         InfiniteProgress ip = new InfiniteProgress();
                         final Dialog iDialog=ip.showInfiniteBlocking();
                         
                       
                         
                         Video vid = new Video();
                            vid.setTitre(titre.getText());
                            vid.setSource(source.getText());
                            vid.setPaye(paye.isSelected() ? 1 : 0);
                            vid.setPrix(Float.parseFloat(prix.getText()));
                            vid.setNote(Integer.parseInt(note.getText()));
                            vid.setPanier_id(Integer.parseInt(panierID.getText()));
                            
                           
                            System.out.println(vid.toString());
                             Dialog.show("Ajouter avec Succée","","annuler","ok");
                           
                      //   String.valueOf(nom.getText().toString()),
                        //         String.valueOf(fournisseur.getText().toString()),
                          //       Float.parseFloat(prix.getText()),
                            //     Float.parseFloat(poids.getText())
                                 System.out.println("data medicament == "+vid);
                                 VideoService.getInstance().ajouterVideo(vid);
                                 iDialog.dispose();
                                
//                                 new Medicament_Sans_Categorie_Form(current).show();
//                                 refreshTheme();
                                 
                     }
                     
                 }
                 catch(Exception ex){
               ex.printStackTrace();
             }
  });
       
       }     


public Component createLineSeparator() {
        Label separator = new Label("", "WhiteSeparator");
        separator.setShowEvenIfBlank(true);
        return separator;
    }
     public Component createLineSeparator(int color) {
        Label separator = new Label("", "WhiteSeparator");
        separator.getUnselectedStyle().setBgColor(color);
        separator.getUnselectedStyle().setBgTransparency(255);
        separator.setShowEvenIfBlank(true);
        return separator;
    }

    private void addStringValue(String s, Component v) {
      add(BorderLayout.west(new Label(s,"Faddedlabel"))
      .add(BorderLayout.CENTER,v));
      add(createLineSeparator(0xeeeeee));
      
      
    }
    
    
}
