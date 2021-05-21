/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Services.ServiceArticle;
import com.codename1.capture.Capture;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Log;
import com.codename1.ui.Button;
import static com.codename1.ui.CN.CENTER;
import static com.codename1.ui.CN.addNetworkErrorListener;
import static com.codename1.ui.CN.updateNetworkThreadCount;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import static com.codename1.ui.TextArea.URL;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.ImageIO;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.UIBuilder;
import com.cyberclan.doctourna.utils.Session;
import entity.Article;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
//import javafx.stage.FileChooser;

/**
 *
 * @author Lenovo
 */
public class ModArticle {
  private Form current;
  private Resources theme;
  Article article = new Article();
  Services.UserSession idsession= new Services.UserSession(Session.getId(), Session.getNom(), Session.getPrenom());
  String titre,text,image,path;
  ServiceArticle SA = new ServiceArticle();
  int  idArt=1,idUser=0;
  Image imgPost=null;

  public Form ModArticle(Article article){
      
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
      Container c = uIBuilder.createContainer(theme,"ModArticle");
       
        Form ModArticle=(Form)uIBuilder.findByName("ModArticle", c);
    // 
    
  
    
    TextArea titre=(TextArea) uIBuilder.findByName("titre",c);
    TextArea Text=(TextArea) uIBuilder.findByName("text",c);
    Label image=(Label) uIBuilder.findByName("image",c);
    Button BtnImage=(Button) uIBuilder.findByName("BtnImage", c);
    Button Modifier=(Button) uIBuilder.findByName("Modifier", c);
    CheckBox multiSelect = new CheckBox("Multi-select");

    BtnImage.addActionListener((ActionEvent e) -> {
   
            if (com.codename1.ext.filechooser.FileChooser.isAvailable()) {
                com.codename1.ext.filechooser.FileChooser.setOpenFilesInPlace(true);
                com.codename1.ext.filechooser.FileChooser.showOpenDialog(multiSelect.isSelected(), ".jpg, .jpeg, .png/plain", (ActionEvent e2) -> {
                    String file = (String) e2.getSource();
                    if (e2 == null || e2.getSource() == null) {
                        ModArticle.add("No file was selected");
                        ModArticle.revalidate();
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
                            ModArticle.revalidate();
                        }
                    }

                });
            }
        });
    
    this.idArt=article.getId();
      System.out.println("id article "+idArt);
    Article detailsarticles=new Article(idArt);
    for(Article ListArt : SA.DetailsArticle(detailsarticles))
       {
           this.idUser=ListArt.getIdUser();
           titre.setText(ListArt.getTitre());
           Text.setText(ListArt.getText());
            try {
                this.path=ListArt.getImage();
                Image imgPost = Image.createImage("file:///C:/wamp64/www/doctourna/public/assets/img/article/"+ListArt.getImage());
                image.setIcon(imgPost);
            } catch (IOException ex) {
                
            }
           
       }
    
  ModArticle.getToolbar().addCommandToOverflowMenu("Back",FontImage.createMaterial(FontImage.MATERIAL_POST_ADD,
          ModArticle.getUnselectedStyle(), CENTER), (ActionListener) (ActionEvent evt) -> {
            Dialog.show("Quitter","Voulez vous quittez la modification","Continuer","Quitter");
            Dialog.getDefaultBlurBackgroundRadius();
            Interface.MesArticle ma = new Interface.MesArticle();
            Form MesArticles = ma.MesArticle(article);
            MesArticles.show();
        });
  
  if(idsession.getIdUser() == idUser)
        {
        ModArticle.getToolbar().addCommandToOverflowMenu("Supp Article",FontImage.createMaterial(FontImage.MATERIAL_DELETE_FOREVER, ModArticle.getUnselectedStyle(), CENTER), (ActionListener) (ActionEvent evt) -> {
         Dialog.show("ATTENTION","Votre article doit etre supprimé définitivement","Continuer","Annuler");
         Dialog.getDefaultBlurBackgroundRadius();
         try{
             
                    
                    if(new ServiceArticle().DeleteArticle(article)){
                        Dialog.show("SUCCESS","Votre Article à été Supprumé","OK",null);
                        Interface.MesArticle ma = new Interface.MesArticle();
                        Form MesArticles = ma.MesArticle(article);
                        MesArticles.show();
                    }
                    else{
                        Dialog.show("ERROR","PROB UPDATE","OK",null);
                       
                    }
                    
                }
                catch(NumberFormatException e){
                }
        });
        }
       
    

//    
//    ServiceArticle SA = new ServiceArticle();
//    ArrayList<Article> ArtiList= SA.ServiceDetailsArticle();
//    
//    for(Article art : ArtiList){
//        
//    }
        
       
        for(Article array_L : SA.DetailsArticle(detailsarticles))
       {
           titre.setText(array_L.getTitre());
           Text.setText(array_L.getText());
            try {
                Image imgPost = Image.createImage("file:///C:/wamp64/www/doctourna/public/assets/img/article/"+array_L.getImage());
                image.setIcon(imgPost);
                image.setIcon(imgPost);
            } catch (IOException ex) {
                
            }
       }
        
        Modifier.addActionListener((evt)->{
            if((titre.getText().length()==0)||(Text.getText().length()==0)){
                Dialog.show("ALERT","Remplir les champs","OK",null);
        }
            else{
                try{
                    
                    Article articlea=new Article(idArt,titre.getText(),Text.getText(),this.path);
                    System.out.println("-----------"+idArt);
                    if(new ServiceArticle().UpdateArticle(articlea)){
                        Dialog.show("SUCCESS","Votre Article à été bien modifié","OK",null);
                    }
                    else{
                        Dialog.show("ERROR","PROB UPDATE","OK",null);
                    }
                    
                }
                catch(NumberFormatException e){
                }
            }
            MesArticle mes = new MesArticle();
            mes.MesArticleForm.showBack();
        });
       
//        
//        
    
      return ModArticle;
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
