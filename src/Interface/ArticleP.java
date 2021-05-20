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
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.UIBuilder;
import static com.codename1.ui.util.UIBuilder.registerCustomComponent;
import entity.Article;
import entity.Reagit;
import java.io.IOException;

/**
 *
 * @author Lenovo
 */
public class ArticleP {
    private Form current;
    Form ArticlePForm;
    private Resources theme;
    Services.UserSession idsession= new Services.UserSession();
    ServiceArticle SA = new ServiceArticle();
    Label imgUser,UserName,ArticleType,ImageArticle;
    TextArea TitreArticleData,TextArticle;
    Container c;
    int idLike;
  

       
        
    public Form ArticleP(){
      
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
    
    
       ArticlePForm = new Form("Article Préféré", BoxLayout.y());
       ArticlePForm.setUIID("BtnPostAction");
    UIBuilder.registerCustomComponent("imageViewer", ImageViewer.class);
    
  Container MenuForm = new Container( new GridLayout(1,4));
    MenuForm.setScrollableX(true);
    MenuForm.setScrollVisible(true);
    MenuForm.setScrollableY(false);
    MenuForm.setUIID("ContainerWhite");
    Button News=new Button("");
    News.setUIID("ButtonNonSelect");
    Button MesArticle=new Button("");
    MesArticle.setUIID("ButtonNonSelect");
    Button ArticleP=new Button("");
    ArticleP.setUIID("ButtonNonSelect");
    Button Recom=new Button("");
    Recom.setUIID("ButtonNonSelect");
    
    News.addActionListener((evt) -> {
             News NewsF = new News();
    Form NewsForm = NewsF.News();
    
        
        NewsForm.showBack();
    });
     MesArticle.addActionListener((evt) -> {
       MesArticle MesArticleF = new MesArticle();
              Form MesArticleForm = MesArticleF.MesArticle(new Article(idsession.getIdUser()));
              MesArticleForm.show();
    });
      ArticleP.addActionListener((evt) -> {
              ArticlePForm.show();
    });
       Recom.addActionListener((evt) -> {
             Recom recf  = new Recom();
             Form recform = recf.Recom();
              recform.show();
    });
    
    MenuForm.add(News);
    MenuForm.add(MesArticle);
     MenuForm.add(Recom);
    MenuForm.add(ArticleP);
    
        
    
    try {
                
                Image iconNews = Image.createImage("file:///C:/Users/Lenovo/Documents/NetBeansProjects/DoctournaApp/src/Images/news.png");
                News.setIcon(iconNews.scaledLargerRatio(95,95));
            } catch (IOException ex) {
                
            }
    try {
                
                Image iconMesArticle = Image.createImage("file:///C:/Users/Lenovo/Documents/NetBeansProjects/DoctournaApp/src/Images/mesArticle.png");
                MesArticle.setIcon(iconMesArticle.scaledLargerRatio(95,95));
            } catch (IOException ex) {
                
            }
    try {
                
                Image iconRecom = Image.createImage("file:///C:/Users/Lenovo/Documents/NetBeansProjects/DoctournaApp/src/Images/recom.png");
                Recom.setIcon(iconRecom.scaledLargerRatio(95,95));
            } catch (IOException ex) {
                
            }
    try {
                
                Image iconArticleP = Image.createImage("file:///C:/Users/Lenovo/Documents/NetBeansProjects/DoctournaApp/src/Images/articlePS.png");
                ArticleP.setIcon(iconArticleP.scaledLargerRatio(95,95));
            } catch (IOException ex) {
                
            }
    
   ArticlePForm.add(MenuForm);
   
   for(Article ListArt : SA.ArticleP())
       {
           addItem(ListArt);
          
       }
       
    
       
     
    
    
    Toolbar tb = ArticlePForm.getToolbar();
    
    
     Form f2 = new Form(new FlowLayout(CENTER, CENTER));
        f2.add(new Label("Settings"));
        
    Image icon =theme.getImage("file:///C:/Users/Lenovo/Documents/NetBeansProjects/Doctourna/src/Images/logodocpdf.png");
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
            ArticlePForm.show();
        });

        tb.addMaterialCommandToSideMenu(" Regime / Sprt",FontImage.MATERIAL_SPORTS,(ActionListener) (ActionEvent evt) -> {
            f2.show();
        });

        tb.addMaterialCommandToSideMenu(" About",FontImage.MATERIAL_INFO,(ActionListener) (ActionEvent evt) -> {
            InterfaceAjoutArticle aj = new InterfaceAjoutArticle();
            Form ajoutArticle = aj.InterfaceAjoutArticle();
            ajoutArticle.show();
        });
        
        ArticlePForm.getToolbar().addCommandToOverflowMenu("Ajout Article",FontImage.createMaterial(FontImage.MATERIAL_POST_ADD, ArticlePForm.getUnselectedStyle(), CENTER), (ActionListener) (ActionEvent evt) -> {
            
            InterfaceAjoutArticle aj = new InterfaceAjoutArticle();
            Form ajoutArticle = aj.InterfaceAjoutArticle();
            ajoutArticle.showBack();
        });
        
      
        
        
        ArticlePForm.show();
        return ArticlePForm;
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
    
    if(ListArt.getIdUser()==idsession.getIdUser()){
      try {
                
                Image imgmod = Image.createImage("file:///C:/Users/Lenovo/Documents/NetBeansProjects/DoctournaApp/src/Images/article_white_24dp.png");
                ModArticle.setIcon(imgmod.scaledLargerRatio(80,80));
            } catch (IOException ex) {
                
            }
      
      
      
    Button SuppArticle=new Button("");
    SuppArticle.setUIID("BtnPostAction");
  
  try {
                
                Image imgSupp = Image.createImage("file:///C:/Users/Lenovo/Documents/NetBeansProjects/DoctournaApp/src/Images/delete.png");
                SuppArticle.setIcon(imgSupp.scaledLargerRatio(95,95));
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
     
     Action.add(SuppArticle);  
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

    Button DisLikeBtn=new Button("");
    DisLikeBtn.setUIID("BtnPostAction");
    
  
         
     
     if(SA.ReagitLike(new Reagit(ListArt.getId())).size()==0)
     {
         try {
                
                Image imgLike = Image.createImage("file:///C:/Users/Lenovo/Documents/NetBeansProjects/DoctournaApp/src/Images/like.png");
                LikeBtn.setIcon(imgLike.scaledLargerRatio(95,95));
            } catch (IOException ex) {
                
            }
         LikeBtn.addActionListener((ActionListener) (ActionEvent evt) -> {
          try {
                
                Image imgLike = Image.createImage("file:///C:/Users/Lenovo/Documents/NetBeansProjects/DoctournaApp/src/Images/likeS.png");
                LikeBtn.setIcon(imgLike.scaledLargerRatio(95,95));
                
                if(SA.ReagitDisLikeJ(new Reagit(ListArt.getId())).size()==0)
                {
                    for (Reagit r :  SA.ReagitDisLikeJ(new Reagit(ListArt.getId())))
                    {
                        System.out.println(r.getId());
                    }
                Reagit Ajoutreagit=new Reagit(idsession.getIdUser(),ListArt.getId(), 1);
                    if(new ServiceArticle().AjoutLike(Ajoutreagit)){
                         System.out.println("ajout like");;
                       
                    }
                    else{
                        Dialog.show("ERROR","PROB UPDATE","OK",null);
                    }
                }
                
           
                else if(SA.ReagitDisLikeJ(new Reagit(ListArt.getId())).size()>0)
                {
                    try {
                
                Image imgDisLike = Image.createImage("file:///C:/Users/Lenovo/Documents/NetBeansProjects/DoctournaApp/src/Images/dislike.png");
                DisLikeBtn.setIcon(imgDisLike.scaledLargerRatio(95,95));
            } catch (IOException ex) {
                
            }
                    for( Reagit rm :SA.ReagitDisLikeJ(new Reagit(ListArt.getId())))
                    {
                        this.idLike=rm.getId();
                        System.out.println(this.idLike);
                    }
                Reagit Modreagit=new Reagit(this.idLike,idsession.getIdUser(),ListArt.getId(), 1);
                    if(new ServiceArticle().ModLike(Modreagit)){
                        System.out.println("mod dislike");
                       
                    }
                    else{
                        Dialog.show("ERROR","PROB UPDATE","OK",null);
                    }
                }
                     
                
                
          }
          catch (IOException ex) {
          }
         });
     }
     
         if(SA.ReagitDisLikeJ(new Reagit(ListArt.getId())).size()==0)
     {
         try {
                
                Image imgLike = Image.createImage("file:///C:/Users/Lenovo/Documents/NetBeansProjects/DoctournaApp/src/Images/dislike.png");
                DisLikeBtn.setIcon(imgLike.scaledLargerRatio(95,95));
            } catch (IOException ex) {
                
            }
         DisLikeBtn.addActionListener((ActionListener) (ActionEvent evt) -> {
          try {
                
                Image imgLike = Image.createImage("file:///C:/Users/Lenovo/Documents/NetBeansProjects/DoctournaApp/src/Images/dislikeS.png");
                DisLikeBtn.setIcon(imgLike.scaledLargerRatio(95,95));
                
                if(SA.ReagitLike(new Reagit(ListArt.getId())).size()==0)
                {
                    for (Reagit r :  SA.ReagitLike(new Reagit(ListArt.getId())))
                    {
                        System.out.println(r.getId());
                    }
                Reagit Ajoutreagit=new Reagit(idsession.getIdUser(),ListArt.getId(), 0);
                    if(new ServiceArticle().AjoutLike(Ajoutreagit)){
                         System.out.println("ajout Dislike");;
                       
                    }
                    else{
                        Dialog.show("ERROR","PROB UPDATE","OK",null);
                    }
                }
                
           
                else if(SA.ReagitLike(new Reagit(ListArt.getId())).size()>0)
                {
                    try {
                
                Image imgDisLike = Image.createImage("file:///C:/Users/Lenovo/Documents/NetBeansProjects/DoctournaApp/src/Images/like.png");
                LikeBtn.setIcon(imgDisLike.scaledLargerRatio(95,95));
            } catch (IOException ex) {
                
            }
                    for( Reagit rm :SA.ReagitLike(new Reagit(ListArt.getId())))
                    {
                        this.idLike=rm.getId();
                        System.out.println(this.idLike);
                    }
                Reagit Modreagit=new Reagit(this.idLike,idsession.getIdUser(),ListArt.getId(), 0);
                    if(new ServiceArticle().ModLike(Modreagit)){
                        System.out.println("mod dislike");
                       
                    }
                    else{
                        Dialog.show("ERROR","PROB UPDATE","OK",null);
                    }
                }
                     
                
                
          }
          catch (IOException ex) {
          }
         });
     }
         
         if(SA.ReagitLike(new Reagit(ListArt.getId())).size()>0)
         {
             try {
                 
                 Image imgLikeIcon = Image.createImage("file:///C:/Users/Lenovo/Documents/NetBeansProjects/DoctournaApp/src/Images/likeS.png");
                 LikeBtn.setIcon(imgLikeIcon.scaledLargerRatio(95,95));
             } catch (IOException ex) {
                 
             }
             LikeBtn.addActionListener((ActionListener) (ActionEvent evt) -> {
             try {
                 
                 Image imgLikeIcon = Image.createImage("file:///C:/Users/Lenovo/Documents/NetBeansProjects/DoctournaApp/src/Images/like.png");
                 LikeBtn.setIcon(imgLikeIcon.scaledLargerRatio(95,95));
             } catch (IOException ex) {
                 
             }
             for( Reagit rm :SA.ReagitLike(new Reagit(ListArt.getId())))
             {
                 this.idLike=rm.getId();
                 System.out.println(this.idLike);
             }
             Reagit Delreagit=new Reagit(this.idLike);
             if(new ServiceArticle().DeleteLike(Delreagit)){
                 System.out.println("supp like");
                 
             }
             else{
                 Dialog.show("ERROR","PROB UPDATE","OK",null);
             }
              });
         }
         
         
          if(SA.ReagitDisLikeJ(new Reagit(ListArt.getId())).size()>0)
         {
             try {
                 
                 Image imgDisLikeIcon = Image.createImage("file:///C:/Users/Lenovo/Documents/NetBeansProjects/DoctournaApp/src/Images/DislikeS.png");
                 DisLikeBtn.setIcon(imgDisLikeIcon.scaledLargerRatio(95,95));
             } catch (IOException ex) {
                 
             }
             DisLikeBtn.addActionListener((ActionListener) (ActionEvent evt) -> {
             try {
                 
                 Image imgLikeIcon = Image.createImage("file:///C:/Users/Lenovo/Documents/NetBeansProjects/DoctournaApp/src/Images/dislike.png");
                 DisLikeBtn.setIcon(imgLikeIcon.scaledLargerRatio(95,95));
             } catch (IOException ex) {
                 
             }
             for( Reagit rm :SA.ReagitDisLikeJ(new Reagit(ListArt.getId())))
             {
                 this.idLike=rm.getId();
                 System.out.println(this.idLike);
             }
             Reagit Delreagit=new Reagit(this.idLike);
             if(new ServiceArticle().DeleteLike(Delreagit)){
                 System.out.println("supp Dislike");
                 
             }
             else{
                 Dialog.show("ERROR","PROB UPDATE","OK",null);
             }
              });
         }
    

    System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
       ImageContainer.add(imgUser);
       Action.add(ModArticle);
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
       
       ArticlePForm.add(BoxArticleContainer);
     }
     
     
    
}

    

