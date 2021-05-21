/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import com.codename1.components.FloatingHint;
import com.codename1.components.ImageViewer;
import com.codename1.components.MultiButton;
import com.codename1.io.Log;
import com.codename1.ui.Button;
import static com.codename1.ui.CN.addNetworkErrorListener;
import static com.codename1.ui.CN.updateNetworkThreadCount;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.UIBuilder;
import static com.codename1.ui.util.UIBuilder.registerCustomComponent;

/**
 *
 * @author Lenovo
 */
public class Menu {
    private Form current;
  private Resources theme;
  InterfaceAjoutArticle aj = new InterfaceAjoutArticle();
     Form ajoutArticle = aj.InterfaceAjoutArticle();
    public  Container Menu(){
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
      Container c = uIBuilder.createContainer(theme,"Menu");
       Form MenuForm=(Form)uIBuilder.findByName("Menu", c);
      
    UIBuilder.registerCustomComponent("imageViewer", ImageViewer.class);
    Button News=(Button) uIBuilder.findByName("News", c);
    Button MesArticle=(Button) uIBuilder.findByName("MesArticle", c);
    Button ArticleP=(Button) uIBuilder.findByName("ArticleP", c);
    Button Recom=(Button) uIBuilder.findByName("Recom", c);
    
     
     
        return MenuForm;
        }
}
