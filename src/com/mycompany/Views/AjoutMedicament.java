
package com.mycompany.Views;

import com.codename1.capture.Capture;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.notifications.LocalNotification;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.cyberclan.doctourna.utils.ToolbarInitializer;
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
public class AjoutMedicament extends Form{
    String file ;
     Resources theme = UIManager.initFirstTheme("/theme");
     Form current;
     Form prev;
       public AjoutMedicament(Form previous){
             super("Medicaments",BoxLayout.y());
             prev = this;
             Toolbar tb = new Toolbar(true);
             current = this;
             initMenu();
             setToolbar(tb);
             getTitleArea().setUIID("container");
             setTitle("Ajouter Medicament");
             getContentPane().setScrollVisible(false);
             
             TextField nom = new TextField("","saisir le nom");
             nom.setUIID("TextFieldBlack");
             addStringValue("Nom",nom);
             
              TextField fournisseur = new TextField("","saisir le fournisseur");
             fournisseur.setUIID("TextFieldBlack");
             addStringValue("Fournisseur",fournisseur);
             
             TextField prix = new TextField("","saisir le prix");
             prix.setUIID("TextFieldBlack");
             addStringValue("Prix",prix);
             
             TextField poids = new TextField("","saisir le poids");
             poids.setUIID("TextFieldBlack");
             addStringValue("Poids",poids);
                 ComboBox combo_cat = new ComboBox();
            for (Categorie c : new Categorie_Service().findAll()) {

                
                    combo_cat.addItem(c.getId());
                    int i = c.getId();
                

            }
             addStringValue("Categorie",combo_cat);
              Button upload = new Button("Upload Image");
                addStringValue("",upload);
             Button btnAjout = new Button("Ajouter");
             addStringValue("",btnAjout);
             
           
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
             
            btnAjout.addActionListener((e)-> {
                 
                 try{
                     
                     if(nom.getText()==""||fournisseur.getText()==""){
                         Dialog.show("Remplir tous les champs","","Annuler","ok");
                     }else{
                         InfiniteProgress ip = new InfiniteProgress();
                         final Dialog iDialog=ip.showInfiniteBlocking();
                         
                       
                         
                         Medicament med = new Medicament();
                            med.setNomMed(nom.getText());
                            med.setFournisseur(fournisseur.getText());
                            med.setPrix_achat(Float.parseFloat(prix.getText()));
                            med.setPoids(Float.parseFloat(poids.getText()));
                            med.setId_Categorie(Integer.valueOf(combo_cat.getSelectedItem().toString()));
                            
                            med.setImage_med(file);
                            System.out.println(med.toString());
                      //   String.valueOf(nom.getText().toString()),
                        //         String.valueOf(fournisseur.getText().toString()),
                          //       Float.parseFloat(prix.getText()),
                            //     Float.parseFloat(poids.getText())
                                 System.out.println("data medicament == "+med);
                                 Medicament_Service.getInstance().ajouterMedicament(med);
                                 iDialog.dispose();
                                
                                 new Medicament_Sans_Categorie_Form(current).show();
                                 refreshTheme();
                                 
                     }
                     
                 }
                 catch(Exception ex){
               ex.printStackTrace();
             }
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
            new CategorieForm(prev).show();
        });
        btn2.addActionListener((evt) -> {
            new Medicament_Sans_Categorie_Form(prev).show();
        });
        btn3.addActionListener((evt) -> {
            new AjoutMedicament(prev).show();
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
        prev.add(menuForm);
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