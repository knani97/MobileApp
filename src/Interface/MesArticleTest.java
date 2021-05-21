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
import com.cyberclan.doctourna.utils.Session;
import com.cyberclan.doctourna.utils.ToolbarInitializer;
import entity.Article;
import java.io.IOException;

/**
 *
 * @author Lenovo
 */
public class MesArticleTest {
    private Form current;
    Form MesArticleForm;
    private Resources theme;
    Services.UserSession idsession= new Services.UserSession(Session.getId(), Session.getNom(), Session.getPrenom());
    ServiceArticle SA = new ServiceArticle();
    Label imgUser,UserName,ArticleType,ImageArticle;
    TextArea TitreArticleData,TextArticle;
    Container c;
        
    public Form MesArticle(Article article){
      
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
        
  
      registerCustomComponent("SpanLabel", com.codename1.components.SpanLabel.class);
     
    UIBuilder.registerCustomComponent("imageViewer", ImageViewer.class);
    
    
       MesArticleForm = new Form("Mes Article", BoxLayout.y());
       MesArticleForm.setUIID("BtnPostAction");
    UIBuilder.registerCustomComponent("imageViewer", ImageViewer.class);
    
    Container MenuForm = new Container(BoxLayout.x());
    MenuForm.setScrollableX(true);
    MenuForm.setScrollableY(false);
    MenuForm.setUIID("ContainerBgColor");
    Button News=new Button("News");
    News.setUIID("ButtonNonSelect");
    Button MesArticle=new Button("MesArticle");
    MesArticle.setUIID("Button");
    Button ArticleP=new Button("ArticleP");
    ArticleP.setUIID("ButtonNonSelect");
    Button Recom=new Button("Recom");
    Recom.setUIID("ButtonNonSelect");
    
    MenuForm.add(News);
    MenuForm.add(MesArticle);
     MenuForm.add(Recom);
    MenuForm.add(ArticleP);
    
   MesArticleForm.add(MenuForm);
   
   for(Article ListArt : SA.MesArticles())
       {
           addItem(ListArt);
          
       }
       
    
       
     
    
    
    /*Toolbar tb = MesArticleForm.getToolbar();
    
    
     Form f2 = new Form(new FlowLayout(CENTER, CENTER));
        f2.add(new Label("Settings"));
        
    Image icon =theme.getImage("file:///C:/Users/mouhe/OneDrive/Documents/NetBeansProjects/DOCTOURNA-mobile/src/Images/logodocpdf.png");
        Container topBar = BorderLayout.center(new Label(icon));
        topBar.setUIID("SideCOmmand");
        
        tb.addComponentToSideMenu(topBar);
        tb.addMaterialCommandToSideMenu(" Home",FontImage.MATERIAL_HOME,e->{});
        tb.addMaterialCommandToSideMenu(" Pharmacie",FontImage.MATERIAL_MEDICAL_SERVICES, (ActionListener) (ActionEvent evt) -> {
            f2.show();
        });
        tb.addMaterialCommandToSideMenu(" Calendrier",FontImage.MATERIAL_CALENDAR_TODAY,(ActionListener) (ActionEvent evt) -> {
            f2.show();
        });

        tb.addMaterialCommandToSideMenu(" News / Articles",FontImage.MATERIAL_ARTICLE,(ActionListener) (ActionEvent evt) -> {
            MesArticleForm.show();
        });

        tb.addMaterialCommandToSideMenu(" Regime / Sprt",FontImage.MATERIAL_SPORTS,(ActionListener) (ActionEvent evt) -> {
            f2.show();
        });

        tb.addMaterialCommandToSideMenu(" About",FontImage.MATERIAL_INFO,(ActionListener) (ActionEvent evt) -> {
            InterfaceAjoutArticle aj = new InterfaceAjoutArticle();
            Form ajoutArticle = aj.InterfaceAjoutArticle();
            ajoutArticle.show();
        });
        
        MesArticleForm.getToolbar().addCommandToOverflowMenu("Ajout Article",FontImage.createMaterial(FontImage.MATERIAL_POST_ADD, MesArticleForm.getUnselectedStyle(), CENTER), (ActionListener) (ActionEvent evt) -> {
            
            InterfaceAjoutArticle aj = new InterfaceAjoutArticle();
            Form ajoutArticle = aj.InterfaceAjoutArticle();
            ajoutArticle.showBack();
        });*/
        ToolbarInitializer.initToolbar(MesArticleForm);
      
        
        
        MesArticleForm.show();
        return MesArticleForm;
    }
    
     public void addItem(Article ListArt) {
         Container BoxArticleContainer = new Container(BoxLayout.y());
          BoxArticleContainer.setUIID("BtnPostAction");
          
          Container BoxArticle = new Container(BoxLayout.y());
           BoxArticle.setUIID("BgBoxArticle");
          
          Container TOPBoX = new Container(BoxLayout.x());
           TOPBoX.setUIID("Container"); 
           
           Container ImageContainer = new Container();
           ImageContainer.setUIID("Container"); 
           
           Container DataUser = new Container(BoxLayout.y());
           ImageContainer.setUIID("Container"); 
           
           Container Action = new Container(BoxLayout.xRight());
           Action.setUIID("Container"); 
           
           Container DATA = new Container(BoxLayout.y());
           Action.setUIID("Container"); 
           
           Container LikeDislike = new Container(BoxLayout.x());
           Action.setUIID("Container"); 
           
           
          
    Label imgUser=new Label("");
     
    try {
                
                Image imgPost = Image.createImage("file:///C:/wamp64/www/doctourna/public/assets/img/clients/"+ListArt.users.getImage());
                imgUser.setIcon(imgPost.scaledLargerRatio(95,95));
            } catch (IOException ex) {
                
            }
    
    
    Label UserName=new Label(ListArt.users.getNom()+" "+ListArt.users.getPrenom());
    UserName.setUIID("LabelUserName");
    
    Label ArticleType=new Label ("type");
    ArticleType.setUIID("LabelCategorieType");
     
    Button ModArticle=new Button("");
    ModArticle.setUIID("BtnPostAction");
    
    
      try {
                
                Image imgmod = Image.createImage("file:///C:/Users/mouhe/OneDrive/Documents/NetBeansProjects/DOCTOURNA-mobile/src/Images/article_white_24dp.png");
                ModArticle.setIcon(imgmod.scaledLargerRatio(80,80));
            } catch (IOException ex) {
                
            }
      
      
      
    Button SuppArticle=new Button("");
    SuppArticle.setUIID("BtnPostAction");
  
  try {
                
                Image imgSupp = Image.createImage("file:///C:/Users/mouhe/OneDrive/Documents/NetBeansProjects/DOCTOURNA-mobile/src/Images/delete.png");
                SuppArticle.setIcon(imgSupp.scaledLargerRatio(95,95));
            } catch (IOException ex) {
                
            }
  
    
    SpanLabel TitreArticleData=new SpanLabel(ListArt.getTitre());
    TitreArticleData.setUIID("LabelTitreArticle");
   
    Label imgArticle=new Label("");
    
     try {
                
                Image imgArt = Image.createImage("file:///C:/wamp64/www/doctourna/public/assets/img/article/"+ListArt.getImage());
                imgArticle.setIcon(imgArt);
                imgArticle.setUIID("Label");
                imgArticle.setGap(0);
            } catch (IOException ex) {
                
            }
     
     
    SpanLabel TextArticle= new SpanLabel(ListArt.getText());
    TextArticle.setUIID("LabelTextArticle");
    
    Button LikeBtn=new Button("");
    LikeBtn.setUIID("BtnPostAction");
    
    try {
                
                Image imgLike = Image.createImage("file:///C:/Users/mouhe/OneDrive/Documents/NetBeansProjects/DOCTOURNA-mobile/src/Images/article_white_24dp.png");
                LikeBtn.setIcon(imgLike.scaledLargerRatio(95,95));
            } catch (IOException ex) {
                
            }
    
    LikeBtn.addActionListener((ActionListener) (ActionEvent evt) -> {
          try {
                
                Image imgLike = Image.createImage("file:///C:/Users/mouhe/OneDrive/Documents/NetBeansProjects/DOCTOURNA-mobile/src/Images/delete.png");
                LikeBtn.setIcon(imgLike.scaledLargerRatio(95,95));
                
            } catch (IOException ex) {
                
            }
        });;
    Button DisLikeBtn=new Button("");
    
    
      
    
    
    DisLikeBtn.setUIID("BtnPostAction");
    
     try {
                
                Image imgDisLike = Image.createImage("file:///C:/Users/mouhe/OneDrive/Documents/NetBeansProjects/DOCTOURNA-mobile/src/Images/article_white_24dp.png");
                DisLikeBtn.setIcon(imgDisLike.scaledLargerRatio(95,95));
            } catch (IOException ex) {
                
            }
     SuppArticle.addActionListener((evt) -> {
         Dialog.show("ATTENTION","Votre article doit etre supprimé définitivement","Continuer","Annuler");
         Dialog.getDefaultBlurBackgroundRadius();
         try{
             
                    Article article=new Article(ListArt.getId());
                    if(new ServiceArticle().DeleteArticle(article)){
                        Dialog.show("SUCCESS","Votre Article à été Supprumé","OK",null);
                        
                    }
                    else{
                        Dialog.show("ERROR","PROB UPDATE","OK",null);
                       
                    }
                    
                }
                catch(NumberFormatException e){
                }
     });
         

    System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
       ImageContainer.add(imgUser);
       Action.add(ModArticle);    
       Action.add(SuppArticle);  
       DataUser.add(UserName);
       DataUser.add(ArticleType);

       LikeDislike.add(LikeBtn);
       LikeDislike.add(DisLikeBtn);
       
       TOPBoX.add(ImageContainer);
       TOPBoX.add(DataUser);
       TOPBoX.add(Action);
      
       BoxArticle.add(TOPBoX);
       BoxArticle.add(TitreArticleData);   
       BoxArticle.add(imgArticle);   
       BoxArticle.add(TextArticle);   
       BoxArticle.add(LikeDislike); 
       BoxArticleContainer.add(BoxArticle);
       
       MesArticleForm.add(BoxArticleContainer);
     }
     
     
    
}

    

