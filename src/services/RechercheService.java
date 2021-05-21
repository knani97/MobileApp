package services ;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import models.Medicament;
import models.Pharmacie;
import utils.DataSource;
import utils.Statics;



public class RechercheService {
    private ConnectionRequest request; 
    private ArrayList<Pharmacie> medicament;
    private boolean responseResult;
   
 

    public RechercheService() {
        request = DataSource.getInstance().getRequest();

    }

        public ArrayList<Pharmacie> medicament( Medicament med) {
        
        String url = Statics.BASE_URL + "/pharmacie/search/" + med.getNomMed();
        request.setUrl(url);
        request.setPost(false);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                medicament = parseMedicament(new String(request.getResponseData()));
                request.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);
               return medicament;
        }
        
        public ArrayList<Pharmacie> parseMedicament(String jsonText) {
        try {
            medicament = new ArrayList<Pharmacie>();
            JSONParser jp = new JSONParser();
            Map<String, Object> DetailsArticleJson = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String, Object>> list = (List<Map<String, Object>>) DetailsArticleJson.get("root");
            for (Map<String, Object> obj : list) {
                int id = (int) Float.parseFloat(obj.get("id").toString());
                String nom = obj.get("nom").toString();
                String adresse = obj.get("adr").toString();
                String gouvernourat = obj.get("gouv").toString();
                
                String img_patente = obj.get("img_pat").toString();
                medicament.add(new Pharmacie(id, nom, adresse, gouvernourat, img_patente));
            }
        } catch (IOException ex) {
        }

        
        return medicament;
    }
}