/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import com.codename1.io.rest.Response;
import com.codename1.io.rest.Rest;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author Lenovo
 */
public class Sms {
   String accountSID = "SKe835563b461395e0526f6ec324c8951b";
//   AC9fc6bc3ba5bdeaa55caf0faa015cc916	
String authToken = "3b938cbec9e4ecdc8c7a9d81eee3f58d";
String fromPhone = "+15024148020";
String val = "";


public Sms(String num){


    

    val = "ya bagra ya feriel";
    System.out.println("------"+val);
Response<Map> result = Rest.post("https://api.twilio.com/2010-04-01/Accounts/" + accountSID + "/Messages.json").
        queryParam("To", num).
        queryParam("From", fromPhone).
        queryParam("Body", val).
        basicAuth(accountSID, authToken).
        getAsJsonMap();

}

}
