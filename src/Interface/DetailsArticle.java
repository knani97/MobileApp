/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

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
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.UIBuilder;
import static com.codename1.ui.util.UIBuilder.registerCustomComponent;
import com.cyberclan.doctourna.utils.Session;

/**
 *
 * @author Lenovo
 */
public class DetailsArticle {
    private Form current;
    private Resources theme;
    Services.UserSession idsession= new Services.UserSession(Session.getId(), Session.getNom(), Session.getPrenom());
    
    
    public Form DetailsArticle(){
      
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
       Form MesArticleForm=(Form)uIBuilder.findByName("MesArticles", c);
    UIBuilder.registerCustomComponent("imageViewer", ImageViewer.class);
    
    
 
    Label imgUser=(Label) uIBuilder.findByName("imgUser",c);
    Label UserName=(Label) uIBuilder.findByName("UserData",c);
    Label ArticleType=(Label) uIBuilder.findByName("TypeArticle",c);
    
    Button ModArticle=(Button) uIBuilder.findByName("ModArticle", c);
    Button SuppArticle=(Button) uIBuilder.findByName("SuppArticle", c);
    
    Label TitreArticle=(Label) uIBuilder.findByName("TitreArticleData",c);
    Label ImageArticle=(Label) uIBuilder.findByName("ImageArticle",c);
    SpanLabel TextArticle=(SpanLabel) uIBuilder.findByName("TextArticle",c);
    MesArticleForm.show();
        
        
        
        return MesArticleForm;
    }
    
}

    

