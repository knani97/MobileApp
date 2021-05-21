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
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Component;
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
import com.doctourna.models.Video;
import com.doctourna.services.VideoService;
import java.io.IOException;
import java.util.Date;

/**
 *
 * @author yessi
 */
public class ModifierVideo extends Form {
     String file ;
     Resources theme = UIManager.initFirstTheme("/theme");
     Form current;
    public ModifierVideo(Resources res,Video v){
          super("Videos",BoxLayout.y());
             Toolbar tb = new Toolbar(true);
             current = this;
             setToolbar(tb);
             getTitleArea().setUIID("container");
             setTitle("Modifier Video");
             getContentPane().setScrollVisible(false);
             
     TextField titre = new TextField(v.getTitre(),"Titre",20,TextField.ANY);
             titre.setUIID("TextFieldBlack");
             addStringValue("titre",titre);
             
              TextField source = new TextField(v.getSource(),"Source",20,TextField.ANY);
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
             
            
         
                 ComboBox combo_cat = new ComboBox();
            for (Video x : new VideoService().findAll()) {

                
                    combo_cat.addItem(x.getId());
                   
                

            }
            
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
                     
                     if(titre.getText()==""||source.getText()==""){
                         Dialog.show("Remplir tous les champs","","Annuler","ok");
                     }else{
                         InfiniteProgress ip = new InfiniteProgress();
                         final Dialog iDialog=ip.showInfiniteBlocking();
                         
                       
                         
                         Video vid = new Video();
                         vid.setId(v.getId());
                                vid.setTitre(titre.getText());
                            vid.setSource(source.getText());
                            vid.setPaye(paye.isSelected() ? 1 : 0);
                            vid.setPrix(Float.parseFloat(prix.getText()));
                            vid.setNote(Integer.parseInt(note.getText()));
                            vid.setPanier_id(Integer.parseInt(panierID.getText()));
                            System.out.println(vid.toString());
                 
                                 System.out.println("data video == "+vid);
                                 VideoService.getInstance().ModifierVideo(vid);
                                 iDialog.dispose();
                                
                                new ListerVideo(current).show();
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
