

package com.mycompany.gui;

import com.codename1.components.FloatingHint;
import com.codename1.components.InfiniteProgress;


import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.cyberclan.doctourna.utils.Emailer;
import com.mycompany.Services.ServiceUtilisateur;
import com.sun.mail.smtp.SMTPSSLTransport;
import com.sun.mail.smtp.SMTPTransport;


import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Account activation UI
 *
 * @author Shai Almog
 */
public class ActivateForm extends BaseForm {
    TextField email ; 

    public ActivateForm(Resources res) {
        super(new BorderLayout());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        tb.setUIID("IMGLogin");
        getTitleArea().setUIID("Container");
        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());
       setUIID("Activate");
        
        add(BorderLayout.NORTH, 
                BoxLayout.encloseY(
                        new Label(res.getImage("oublier.png"), "LogoLabel"),
                        new Label("Awsome Thanks!", "LogoLabel")
                )
        );
        
         email = new TextField("","saisier votre email",20,TextField.ANY);
        email.setSingleLineTextArea(false);
        
        Button valider = new Button ("Valider"); 
        Label haveAnAcount = new Label ("Retour se connecter?");
        Button signIn = new Button("Renouveller votre motpasse");
        signIn.addActionListener(e -> previous.showBack()); //yarja3 ll window il 9balha
        signIn.setUIID("CenterLink");
        
        Container content = BoxLayout.encloseY(
        
                new FloatingHint(email),
                createLineSeparator(),
                valider,
                FlowLayout.encloseCenter(haveAnAcount),
                signIn
        
        ) ;
        content.setScrollableY(true);
        add(BorderLayout.CENTER,content);
        valider.requestFocus();
        valider.addActionListener(e -> {
            InfiniteProgress ip = new InfiniteProgress () ; 
            final Dialog ipDialog = ip.showInfiniteBlocking();
            sendMail(res);
ipDialog.dispose();
Dialog.show("Mot de passe", "Nous avons envoye le mot de passe a votre e-mail. Veuillez verifier votre boite de reception",new Command("OK"));
new SignInForm(res).show();
refreshTheme();
        });
                
                
        
    }
    public void sendMail (Resources res) {
        try {
            String mp = ServiceUtilisateur.getInstance().getPasswordByEmail(email.getText().toString(), res); 
            String txt = "Bienvenu sur Doctourna : Tapez ce mot de passse : "+mp+"dane le champs requis et appuis sur confirmer";
            Emailer.send(email.getText().toString(), "Bienvenu sur Doctourna : Tapez ce mot de passse : "+mp+"dane le champs requis et appuis sur confirmer", "DOCTOURNA - MDP Oublié");
            /*Properties props = new Properties();
props.put("mail.transport.protocol","smtp");
props.put("mail.smtp.host", "smtp.gmail.com");  

props.put("mail.smtp.auth", "auth");

         Session session = Session.getInstance(props, null);
         
            MimeMessage msg  = new MimeMessage (session);
            msg.setFrom(new InternetAddress("Reintialisation mot de passe<monEmail@domaine.com>"));
            msg.setRecipients(Message.RecipientType.TO, email.getText().toString());
            msg.setSubject("Application nom : Confirmation du ");
            msg.setSentDate(new Date(System.currentTimeMillis()));
            String mp = ServiceUtilisateur.getInstance().getPasswordByEmail(email.getText().toString(), res); 
            String txt = "Bienvenu sur Doctourna : Tapez ce mot de passse : "+mp+"dane le champs requis et appuis sur confirmer";
            msg.setText(txt);
            SMTPTransport st = (SMTPSSLTransport)session.getTransport("smtps") ;
            st.connect("smtp.gmail",465,"docpidev@gmail.com","pidev123456");
            st.sendMessage(msg, msg.getAllRecipients());
            System.out.println("server response"+st.getLastServerResponse());*/
         
        } catch (Exception e) {
        }
    
    }
    
}