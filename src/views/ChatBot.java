/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.io.IOException;
import java.util.ArrayList;


/**
 *
 * @author Meriem
 */
public class ChatBot extends Form {
    public  ChatBot()
     {
     
        try {
            setTitle("ChatBot");
            setLayout(BoxLayout.y());
            ArrayList<String> liste3;
            Button ok = new Button("ok");
            
            
            TextArea t = new TextArea();
            
           // String  ch2=t.getText().replace(' ',"%20");
           // System.out.println(ch2);
            HttpResponse<String> response = Unirest.get("https://acobot-brainshop-ai-v1.p.rapidapi.com/get?bid=155900&key=CZainXOvM6BlGVtY&uid=pidev&msg="+"je%20veux%20consulter%20")
                    .header("x-rapidapi-key", "acd422d7b6msh682a6b9121a4397p1f6684jsnfd86ac745d6b")
                    .header("x-rapidapi-host", "acobot-brainshop-ai-v1.p.rapidapi.com")
                    .asString();
             String myJSON = response.getBody();
            System.out.println("***************************"+response.getBody()+"*****************");
            
            Label l = new Label(myJSON);
            Container details = new Container(BoxLayout.y());
            details.add(l);
            
            
            this.addAll(details,t,ok);
        } catch (UnirestException ex) {
                   }

     
     
     }
    
    
    
    
}
