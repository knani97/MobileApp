/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

/**
 *
 * @author Meriem
 */


import com.codename1.io.CharArrayReader;
import com.codename1.io.JSONParser;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.cyberclan.doctourna.utils.ToolbarInitializer;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 *
 * @author ASUS
 */
public class Corona extends Form {
    public ArrayList<String> c=new ArrayList<>();
    
    public Corona(){
        try {
            EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(getWidth(), getWidth() / 2, 0xffff0000), true);
            URLImage background = URLImage.createToStorage(placeholder,"68712301-2-0-6584368-v-000001000000.jpg?$p=hi-w795",
        "https://s1.lmcdn.fr/multimedia/501506889775/180066075d630/produits/peinture-mur-creme-de-couleur-dulux-valentine-bleu-caraibes-satine-2-5-l/68712301-2-0-6584368-v-000001000000.jpg?$p=hi-w795");
background.fetch();
            getUnselectedStyle().setBgImage(background );
           // final Form f = new Form("Camera Demo");
            setLayout(BoxLayout.y());
             Container holder = new Container(BoxLayout.y());
            // public  ArrayList<String> getCoronaService() throws UnirestException, IOException{
            HttpResponse<String> response = Unirest.get("https://covid-19-data.p.rapidapi.com/totals")
                    .header("x-rapidapi-key", "acd422d7b6msh682a6b9121a4397p1f6684jsnfd86ac745d6b")
                    .header("x-rapidapi-host", "covid-19-data.p.rapidapi.com")
                    .asString();
            String myJSON = response.getBody();
                JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json
            Map<String,Object> tasksListJson;
            tasksListJson = j.parseJSON(new CharArrayReader(myJSON.toCharArray()));
            List <Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            System.out.println(list);
            for(Map<String,Object> obj : list){
                //Création des tâches et récupération de leurs données
                
                float confirmed = Float.parseFloat(obj.get("confirmed").toString());
                float recovered = Float.parseFloat(obj.get("recovered").toString());
                float critical = Float.parseFloat(obj.get("critical").toString());
                float deaths = Float.parseFloat(obj.get("deaths").toString());
                String lastChange = obj.get("lastChange").toString();
                String lastUpdate = obj.get("lastUpdate").toString();
                System.out.println("confirmé"+ confirmed);
                System.out.println("recoveré"+ recovered);
                c.add(String.valueOf(confirmed));
                c.add(String.valueOf(recovered));
                c.add(String.valueOf(critical));
                c.add(String.valueOf(deaths));
                c.add(lastUpdate);
                
                
                
            }
            System.out.println(c);
            //final Form f = new Form("Camera Demo");
            //  f.setLayout(BoxLayout.y());
            // f.setTitle("donate");
            //  Form hi = new Form("Donate", BoxLayout.y());
             Label l = new Label("le resultat du statistique de  "+c.get(4));
            Label l1 = new Label("les cas confirmées ");
            Label l11= new Label(c.get(0));
            Label l2= new Label("les cas rétablie ");
            Label l12= new Label(c.get(1));
            Label l3= new Label("les cas critique ");
            Label l13= new Label(c.get(2));
            Label l4= new Label("nombre des mortes ");
            Label l14= new Label(c.get(3));
            holder.addAll(l , l1,l11,l2,l12,l3,l13,l4,l14);
           add(holder);
             } catch (IOException ex) {
               
            } catch (UnirestException ex) {
           
        }
        
    ToolbarInitializer.initToolbar(this);
}
    
    
    




}


