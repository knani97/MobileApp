/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Interface.ModArticle;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.events.ActionListener;
import entity.Article;
import entity.ArticleCat;
import entity.Reagit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Lenovo
 */
public class ServiceArticle {

    private ConnectionRequest request;
    private ArrayList<Reagit> reagit;        
    private ArrayList<ArticleCat> articleCat;    
    private ArrayList<Article> article;
    private boolean responseResult;
    Services.UserSession idsession= new Services.UserSession();
    int idUser=idsession.idUser;

    public ServiceArticle() {
        request = DataSource.getInstance().getRequest();

    }

    public boolean AjoutArticle(Article article) {
        String url=Statics.BASE_URL+"/news/ajoutArticleJ?titre="+article.getTitre()+"&text="+article.getText()+"&image="+article.getImage().toString()+"&idUser="+idUser+"&idCat="+article.articleCat.getId();
        request.setUrl(url);
        System.out.println(article.getImage());
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
               responseResult=request.getResponseCode()==200;
               request.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);
        return responseResult;      
    }

    public ArrayList<Article> MesArticles() {
        
        String url = Statics.BASE_URL + "/news/mes-articlesJ/"+idsession.getIdUser();
        request.setUrl(url);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                article = parseMesArticle(new String(request.getResponseData()));
                request.removeResponseListener(this);
            }
        });
        
        

        NetworkManager.getInstance().addToQueueAndWait(request);
        return article;
    }
    
    
    public ArrayList<Article> ArticleP() {
        
        String url = Statics.BASE_URL + "/news/ArticlePrefereJ/"+idsession.getIdUser();
        request.setUrl(url);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                article = parseMesArticle(new String(request.getResponseData()));
                request.removeResponseListener(this);
            }
        });
        
        

        NetworkManager.getInstance().addToQueueAndWait(request);
        return article;
    }
        

    
    
    public ArrayList<Article> RecomA() {
        
        String url = Statics.BASE_URL +"/news/ArticlePrefereJ/"+idsession.getIdUser();
        request.setUrl(url);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                article = parseMesArticle(new String(request.getResponseData()));
                request.removeResponseListener(this);
            }
        });
        
        

        NetworkManager.getInstance().addToQueueAndWait(request);
        return article;
    }        
    
 public ArrayList<Article> NewsJ() {
        
        String url = Statics.BASE_URL + "/newsJ";
        request.setUrl(url);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                article = parseMesArticle(new String(request.getResponseData()));
                request.removeResponseListener(this);
            }
        });
        
        

        NetworkManager.getInstance().addToQueueAndWait(request);
        return article;
    }
    
    
    public ArrayList<Article> DetailsArticle(Article detailsarticles) {
        String url = Statics.BASE_URL + "/news/detailsArtJ/"+detailsarticles.getId();
        request.setUrl(url);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                article = parseArticle(new String(request.getResponseData()));
                
                request.removeResponseListener(this);
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(request);
        return article;
    }
    
         public ArrayList<Article> parseMesArticle(String jsonText) {
        try {
            article = new ArrayList<Article>();
            JSONParser jp = new JSONParser();
            Map<String, Object> DetailsArticleJson = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) DetailsArticleJson.get("root");
            for (Map<String, Object> obj : list) {
                int id = (int) Float.parseFloat(obj.get("id").toString());
                String titre = obj.get("titre").toString();
                String text = obj.get("text").toString();
                String image = obj.get("image").toString();
                int etatAjout = (int) Float.parseFloat(obj.get("etat_ajout").toString());
                int idUsers = (int) Float.parseFloat(obj.get("id_user").toString());
                int idCatId = (int) Float.parseFloat(obj.get("id_cat_id").toString());
                long time = System.currentTimeMillis();
               Date dateAjout=(Date) obj.get("Date_ajout");
               String prenom = obj.get("prenom").toString();
               String nom = obj.get("nom").toString();
               String imageU = obj.get("imageuser").toString();
                article.add(new Article(id, titre, text, image, dateAjout, etatAjout, idUsers,prenom,nom,imageU,idCatId));
            }
        } catch (IOException ex) {
        }

        
        return article;
    }
         
    public ArrayList<Article> parseArticle(String jsonText) {
        try {
            article = new ArrayList<Article>();
            JSONParser jp = new JSONParser();
            Map<String, Object> DetailsArticleJson = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) DetailsArticleJson.get("root");
            for (Map<String, Object> obj : list) {
                int id = (int) Float.parseFloat(obj.get("id").toString());
                String titre = obj.get("titre").toString();
                String text = obj.get("text").toString();
                String image = obj.get("image").toString();
                int etatAjout = (int) Float.parseFloat(obj.get("etat_ajout").toString());
                int idUsers = (int) Float.parseFloat(obj.get("id_user").toString());
                int idCatId = (int) Float.parseFloat(obj.get("id_cat_id").toString());
                long time = System.currentTimeMillis();
               Date dateAjout=(Date) obj.get("Date_ajout");
               
                article.add(new Article(id, titre, text, image, dateAjout, etatAjout, idUsers,idCatId));
            }
        } catch (IOException ex) {
        }

        
        return article;
    }
    
    public boolean UpdateArticle(Article article){
        
        String url = Statics.BASE_URL+"/news/modArtJ/"+article.getId()+"?titre="+article.getTitre()+"&text="+article.getText()+"&image="+article.getImage();
        System.out.println("------------"+article.getImage());
               
        request.setUrl(url);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
               responseResult=request.getResponseCode()==200;
               request.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);
        return responseResult;       
    }
    
     public boolean DeleteArticle(Article article){
        String url = Statics.BASE_URL+"/news/suppJ/"+article.getId();
        request.setUrl(url);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
               responseResult=request.getResponseCode()==200;
               request.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);
        return responseResult;       
    }

          
          public boolean AjoutLike(Reagit reagitAjout){
        String url = Statics.BASE_URL+"/news/AjoutLikeJ?idUserId="+reagitAjout.getIdUserId()+"&typeReact="+reagitAjout.getTypeReact()+"&idArtId="+reagitAjout.getIdArtId();
        request.setUrl(url);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
               responseResult=request.getResponseCode()==200;
               request.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);
        return responseResult;       
    }
          public boolean DeleteLike(Reagit reagitAjout){
        String url = Statics.BASE_URL+"/news/DeleteLikeJ/"+reagitAjout.getId();
        request.setUrl(url);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
               responseResult=request.getResponseCode()==200;
               request.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);
        return responseResult;       
    }
          
          
          public ArrayList<Reagit> ReagitLike(Reagit reagitid) {
        String url = Statics.BASE_URL + "/news/ReagitLikeJ/"+reagitid.getId()+"&"+idsession.getIdUser();
        request.setUrl(url);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                reagit = parsePostLike(new String(request.getResponseData()));
                
                request.removeResponseListener(this);
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(request);
        return reagit;
    }
          
          public ArrayList<Reagit> ReagitDisLikeJ(Reagit reagitid) {
        String url = Statics.BASE_URL + "/news/ReagitDisLikeJ/"+reagitid.getId()+"&"+idsession.getIdUser();
        request.setUrl(url);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                reagit = parsePostLike(new String(request.getResponseData()));
                
                request.removeResponseListener(this);
            }
        });


        NetworkManager.getInstance().addToQueueAndWait(request);
        return reagit;
    }
          
          public boolean ModLike(Reagit ragitmod){
              
        String url = Statics.BASE_URL+"/news/ModLikeJ/"+ragitmod.getId()+"?&typeReact="+ragitmod.getTypeReact();
        request.setUrl(url);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
               responseResult=request.getResponseCode()==200;
               request.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);
        return responseResult;       
    }

         public ArrayList<Reagit> parsePostLike(String jsonText) {
        try {
            reagit = new ArrayList<Reagit>();
            JSONParser jp = new JSONParser();
            Map<String, Object> DetailsArticleJson = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) DetailsArticleJson.get("root");
            for (Map<String, Object> obj : list) {
                int id = (int) Float.parseFloat(obj.get("id").toString());
                int idUser = (int) Float.parseFloat(obj.get("id_user_id").toString());
                int idArt = (int) Float.parseFloat(obj.get("id_art_id").toString());
                int TypeReact = (int) Float.parseFloat(obj.get("type_react").toString());
                reagit.add(new Reagit(id, idUser,idArt,TypeReact));;
            }
        } catch (IOException ex) {
        }

        
        return reagit;
    }
         
         
         public ArrayList<ArticleCat> ArticleCat() {
        String url = Statics.BASE_URL + "/news/ArticleCat";
        request.setUrl(url);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                articleCat = parseArticleCat(new String(request.getResponseData()));
                
                request.removeResponseListener(this);
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(request);
        return  articleCat;
    }
      
          public ArrayList<ArticleCat> parseArticleCat(String jsonText) {
        try {
            articleCat = new ArrayList<ArticleCat>();
            JSONParser jp = new JSONParser();
            Map<String, Object> DetailsArticleJson = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) DetailsArticleJson.get("root");
            for (Map<String, Object> obj : list) {
                int id = (int) Float.parseFloat(obj.get("id").toString());
                String categorie = obj.get("categorie").toString();
                articleCat.add(new ArticleCat(id, categorie));
            }
        } catch (IOException ex) {
        }

        
        return articleCat;
    }

}
