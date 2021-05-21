/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Views;

import com.codename1.capture.Capture;
import com.codename1.components.FloatingHint;
import com.codename1.components.InfiniteProgress;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.mycompany.Models.Categorie;
import com.mycompany.Models.Medicament;
import com.mycompany.Services.Categorie_Service;
import com.mycompany.Services.Medicament_Service;
import java.io.IOException;
import java.util.Date;

/**
 *
 * @author user
 */
public class ModifierMedicament extends Form{
    String file ;
     Resources theme = UIManager.initFirstTheme("/theme");
     Form current;
    public ModifierMedicament(Resources res,Medicament m){
          super("Medicaments",BoxLayout.y());
             Toolbar tb = new Toolbar(true);
             current = this;
             setToolbar(tb);
             getTitleArea().setUIID("container");
             setTitle("Modifier Medicament");
             getContentPane().setScrollVisible(false);
             
     TextField nom = new TextField(m.getNomMed(),"Nom",20,TextField.ANY);
             nom.setUIID("TextFieldBlack");
             addStringValue("Nom",nom);
             
              TextField fournisseur = new TextField(m.getFournisseur(),"Fournisseur",20,TextField.ANY);
             fournisseur.setUIID("TextFieldBlack");
             addStringValue("Fournisseur",fournisseur);
             
             TextField prix = new TextField(String.valueOf(m.getPrix_achat()),"Prix",20,TextField.ANY);
             prix.setUIID("TextFieldBlack");
             addStringValue("Prix",prix);
             
             TextField poids = new TextField(String.valueOf(m.getPoids()),"Poids",20,TextField.ANY);
             poids.setUIID("TextFieldBlack");
             addStringValue("Poids",poids);
                 ComboBox combo_cat = new ComboBox();
            for (Categorie c : new Categorie_Service().findAll()) {

                
                    combo_cat.addItem(c.getId());
                   
                

            }
             addStringValue("Categorie",combo_cat);
              Button upload = new Button("Upload Image");
                addStringValue("",upload);
             Button btnModifier = new Button("Modifier");
             addStringValue("",btnModifier);
             
           
              upload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    String fileNameInServer = "";
                    MultipartRequest cr = new MultipartRequest();
                    String filepath = Capture.capturePhoto(-1, -1);
                    cr.setUrl("http://localhost/upload/uploadimage.php");
                    cr.setPost(true);
                    String mime = "image/jpeg";
                    cr.addData("file", filepath, mime);
                    String out = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
                    cr.setFilename("file", out + ".jpg");//any unique name you want
                    
                    fileNameInServer += out + ".jpg";
                    System.err.println("path2 =" + fileNameInServer);
                    file=fileNameInServer;
                    InfiniteProgress prog = new InfiniteProgress();
                    Dialog dlg = prog.showInifiniteBlocking();
                    cr.setDisposeOnCompletion(dlg);
                    NetworkManager.getInstance().addToQueueAndWait(cr);
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
                                        
            }
        });
             
            btnModifier.addActionListener((e)-> {
                 
                 try{
                     
                     if(nom.getText()==""||fournisseur.getText()==""){
                         Dialog.show("Remplir tous les champs","","Annuler","ok");
                     }else{
                         InfiniteProgress ip = new InfiniteProgress();
                         final Dialog iDialog=ip.showInfiniteBlocking();
                         
                       
                         
                         Medicament med = new Medicament();
                         med.setId(m.getId());
                            med.setNomMed(nom.getText());
                            med.setFournisseur(fournisseur.getText());
                            med.setPrix_achat(Float.parseFloat(prix.getText()));
                            med.setPoids(Float.parseFloat(poids.getText()));
                            med.setId_Categorie(Integer.valueOf(combo_cat.getSelectedItem().toString()));
                            med.setFiche_exist(m.getFiche_exist());
                            med.setImage_med(file);
                            System.out.println(med.toString());
                      //   String.valueOf(nom.getText().toString()),
                        //         String.valueOf(fournisseur.getText().toString()),
                          //       Float.parseFloat(prix.getText()),
                            //     Float.parseFloat(poids.getText())
                                 System.out.println("data medicament == "+med);
                                 Medicament_Service.getInstance().ModifierMedicament(med);
                                 iDialog.dispose();
                                
                                 new Medicament_Sans_Categorie_Form(current).show();
                                 refreshTheme();
                                 
                     }
                     
                 }
                 catch(Exception ex){
               ex.printStackTrace();
             }
  });
    
    
    
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
