/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Services.ServiceArticle;
import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.io.Log;
import com.codename1.ui.Button;
import static com.codename1.ui.CN.CENTER;
import static com.codename1.ui.CN.addNetworkErrorListener;
import static com.codename1.ui.CN.updateNetworkThreadCount;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import static com.codename1.ui.FontImage.MATERIAL_INFO;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.UIBuilder;
import static com.codename1.ui.util.UIBuilder.registerCustomComponent;
import entity.Article;
import java.io.IOException;

/**
 *
 * @author Lenovo
 */
public class MesArticletst2 {
    private Form current;
    Form MesArticleForm;
    private Resources theme;
    Services.UserSession idsession= new Services.UserSession();
    ServiceArticle SA = new ServiceArticle();
    Label imgUser,UserName,ArticleType,ImageArticle;
    TextArea TitreArticleData,TextArticle;
    Menu menu = new Menu();
        
    
    public Form MesArticle(){
      
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
      registerCustomComponent("SpanLabel", com.codename1.components.SpanLabel.class);
      Container c = uIBuilder.createContainer(theme,"MesArticles");
       MesArticleForm=(Form)uIBuilder.findByName("MesArticles", c);
    UIBuilder.registerCustomComponent("imageViewer", ImageViewer.class);
   
    
      
        
        
        addItem();
       addItem();
       addItem();
       addItem();
       addItem();
       addItem();
        MesArticleForm.show();
        return MesArticleForm;
    }
    public void addItem() {
         
    
  Container cnt = new Container(BoxLayout.y());
   
    
    
          Label imgUser=new Label("imgUser");
    Label UserName=new Label("UserData");
    Label ArticleType=new Label("typeArticle");
    
    Button ModArticle=new Button("ModArticle");
    Button SuppArticle=new Button ("SuppArticle");
    
    Label TitreArticleData=new Label("TitreArticleData");
    Label ImageArticle=new Label("ImageArticle");
    SpanLabel TextArticle=new SpanLabel("TextArticle");
            
            try {
                
                Image imgUserGet = Image.createImage("file:///C:/wamp64/www/doctourna/public/assets/img/clients/000028dd04b54e69077b1ab56a683708.jpeg");
                imgUser.setIcon(imgUserGet.scaledLargerRatio(95,95));
            } catch (IOException ex) {
                
            }
            
            try {
                
               
                
                Image imgPost = Image.createImage("file:///C:/wamp64/www/doctourna/public/assets/img/clients/000028dd04b54e69077b1ab56a683708.jpeg");
                ImageArticle.setIcon(imgPost);
//                ImageArticle.setText(ListArt.getImage());
            } catch (IOException ex) {
                
            }
          
           cnt.add(UserName);
        cnt.add(ArticleType);
        cnt.add(ModArticle);
        cnt.add(SuppArticle);
        cnt.add(TitreArticleData);
        cnt.add(ImageArticle);
        cnt.add(TextArticle);
        
        MesArticleForm.add(cnt);
MesArticleForm.refreshTheme();
    }
}

    

