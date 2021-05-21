/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Services.ServiceArticle;
import Services.Sms;
import com.codename1.capture.Capture;
import com.codename1.ext.filechooser.FileChooser;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Log;
import com.codename1.ui.Button;
import static com.codename1.ui.CN.CENTER;
import static com.codename1.ui.CN.addNetworkErrorListener;
import static com.codename1.ui.CN.updateNetworkThreadCount;
import com.codename1.ui.CheckBox;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.ImageIO;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.UIBuilder;
import com.cyberclan.doctourna.utils.Session;
import com.cyberclan.doctourna.utils.ToolbarInitializer;
import entity.Article;
import entity.ArticleCat;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author Lenovo
 */
public class InterfaceAjoutArticle {
  private Form current;
  private Resources theme;
  String path;
  Services.UserSession idsession= new Services.UserSession(Session.getId(), Session.getNom(), Session.getPrenom());
  int idCat=0;
  

  public Form InterfaceAjoutArticle(){
      
        // use two network threads instead of one
        updateNetworkThreadCount(2);

        theme = UIManager.initFirstTheme("/theme");

        // Enable Toolbar on all Forms by default
        Toolbar.setGlobalToolbar(true);

        // Pro only feature
        Log.bindCrashProtection(true);

        addNetworkErrorListener(err -> {
            // prevent the event from propagating
            err.consume();
            if(err.getError() != null) {
                Log.e(err.getError());
            }
            Log.sendLogAsync();
            Dialog.show("Connection Error", "There was a networking error in the connection to " + err.getConnectionRequest().getUrl(), "OK", null);
        });        
  
      UIBuilder uIBuilder = new UIBuilder();
        Container c = uIBuilder.createContainer(theme,"AjoutArticle");
        Form AjoutArticle=(Form)uIBuilder.findByName("AjoutArticle", c);
    
  Button News=(Button) uIBuilder.findByName("News", c);
    Button MesArticle=(Button) uIBuilder.findByName("MesArticle", c);
    Button ArticleP=(Button) uIBuilder.findByName("ArticleP", c);
    Button Recom=(Button) uIBuilder.findByName("Recom", c);
    
    MesArticle.addActionListener((ActionEvent e) -> {
           Interface.MesArticle ma = new Interface.MesArticle();
            Form MesArticles = ma.MesArticle(new Article());
            MesArticles.show();
       });
    
    TextField Titre=(TextField) uIBuilder.findByName("Titre",c);
    TextArea Text=(TextArea) uIBuilder.findByName("Text",c);
    ComboBox Categorie=(ComboBox) uIBuilder.findByName("Categorie",c);
    ServiceArticle sa = new ServiceArticle();
      System.out.println(sa.ArticleCat());
    for ( ArticleCat arc : sa.ArticleCat()){
        String cat= arc.getCategorie();
        Categorie.addItem(cat);
        System.out.println(cat+"---------");
  }
    

     
    Label image=(Label) uIBuilder.findByName("image",c);
    Button BtnImage=(Button) uIBuilder.findByName("BtnImage", c);
    Button BtnAjoutArticle=(Button) uIBuilder.findByName("AjoutArticleBtn", c);
    
    BtnImage.addActionListener((ActionEvent e) -> {
   
            if (FileChooser.isAvailable()) {
                CheckBox multiSelect = new CheckBox("Multi-select");
                FileChooser.setOpenFilesInPlace(true);
                FileChooser.showOpenDialog(multiSelect.isSelected(), ".jpg, .jpeg, .png/plain", (ActionEvent e2) -> {
                    String file = (String) e2.getSource();
                    if (e2 == null || e2.getSource() == null) {
                        AjoutArticle.add("No file was selected");
                        AjoutArticle.revalidate();
                    } else {
                        try {
                            Image logo = Image.createImage(file);
                            image.setIcon(logo);
                            String imageFile = FileSystemStorage.getInstance().getAppHomePath() + "photo.png";

                            try (OutputStream os = FileSystemStorage.getInstance().openOutputStream(imageFile)) {
                                System.out.println(imageFile);
                                ImageIO.getImageIO().save(logo, os, ImageIO.FORMAT_PNG, 1);
                            } catch (IOException err) {
                            }
                        } catch (IOException ex) {
                        }
                        String extension = null;
                        if (file.lastIndexOf(".") > 0) {
                            extension = file.substring(file.lastIndexOf(".") + 1);
                            StringBuilder hi = new StringBuilder(file);
                            if (file.startsWith("file://")) {
                                hi.delete(0, 7);
                            }
                            int lastIndexPeriod = hi.toString().lastIndexOf(".");
                            Log.p(hi.toString());
                            String ext = hi.toString().substring(lastIndexPeriod);
                            String hmore = hi.toString().substring(0, lastIndexPeriod - 1);
                            try {
                                String namePic = saveFileToDevice(file, ext);
                                int index = namePic.lastIndexOf("/");

                                System.out.println("namePicture:" + namePic.substring(index+1));
                                path = namePic.substring(index+1);

                            } catch (IOException ex) {
                            }
                            AjoutArticle.revalidate();
                        }
                    }

                });
            }
        });
         
//        this.path = Capture.capturePhoto(Display.getInstance().getDisplayWidth(), -1);
//        if(this.path!=null){
//            try {
//                Image img=Image.createImage(path);
//                image.setIcon(img);
//                AjoutArticle.revalidate();
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        }
    
    
    BtnAjoutArticle.addActionListener((evt)->{
        
        for ( ArticleCat arc : sa.ArticleCat()){
            
            System.out.println("+++"+arc.getCategorie());
            if(arc.getCategorie().toString().equals(Categorie.getSelectedItem().toString()))
            { this.idCat=arc.getId();
            }
        }
        System.out.println(Categorie.getSelectedItem().toString()+"---- id = "+idCat);
            if((Titre.getText().length()==0)||(Text.getText().length()==0)){
                Dialog.show("ALERT","Remplir les champs","OK",null);
        }
            else{
                try{
                   
                    Article article=new Article(Titre.getText(),Text.getText(),path,idsession.getIdUser(),idCat);
                    System.out.println("-+"+idCat);
                    if(new ServiceArticle().AjoutArticle(article)){
                        Dialog.show("SUCCESS","Votre Article à été bien ajouter","OK",null);
                        Sms smm = new Sms("+21624299878");
                       
                    }
                    else{
                        Dialog.show("ERROR","PROB UPDATE","OK",null);
                        
                    }
                    
                }
                catch(NumberFormatException e){
                }
            }
        });
    
    /*Toolbar tb = AjoutArticle.getToolbar();
    
    Form f2 = new Form(new FlowLayout(CENTER, CENTER));
    f2.add(new Label("Settings"));
        
    Image icon =theme.getImage("file:///C:/Users/mouhe/OneDrive/Documents/NetBeansProjects/DOCTOURNA-mobile/src/Images/logodocpdf.png");
        Container topBar = BorderLayout.center(new Label(icon));
        topBar.setUIID("SideCOmmand");
        
        tb.addComponentToSideMenu(topBar);
        tb.addMaterialCommandToSideMenu(" Home",FontImage.MATERIAL_HOME,e->{});
        tb.addMaterialCommandToSideMenu(" Pharmacie",FontImage.MATERIAL_MEDICAL_SERVICES, (ActionListener) (ActionEvent evt) -> {
            AjoutArticle.show();
        });
        tb.addMaterialCommandToSideMenu(" Calendrier",FontImage.MATERIAL_CALENDAR_TODAY,(ActionListener) (ActionEvent evt) -> {
            AjoutArticle.show();
        });

        tb.addMaterialCommandToSideMenu(" News / Articles",FontImage.MATERIAL_ARTICLE,(ActionListener) (ActionEvent evt) -> {
            Interface.MesArticle ma = new Interface.MesArticle();
            Form MesArticles = ma.MesArticle(new Article());
            MesArticles.show();
        });

        tb.addMaterialCommandToSideMenu(" Regime / Sprt",FontImage.MATERIAL_SPORTS,(ActionListener) (ActionEvent evt) -> {
            AjoutArticle.show();
        });

        tb.addMaterialCommandToSideMenu(" About",FontImage.MATERIAL_INFO,(ActionListener) (ActionEvent evt) -> {
            AjoutArticle.show();
        });*/
       ToolbarInitializer.initToolbar(AjoutArticle);
    

    
    
     

        
    AjoutArticle.show();
      return AjoutArticle;
    }

  protected String saveFileToDevice(String hi, String ext) throws IOException {
        URI uri;
        try {
            uri = new URI(hi);
            String path = uri.getPath();
            int index = hi.lastIndexOf("/");
            hi = hi.substring(index + 1);
            return hi;
        } catch (URISyntaxException ex) {
        }
        return "hh";
    }
  
}
