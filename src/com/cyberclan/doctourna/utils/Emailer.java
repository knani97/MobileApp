/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyberclan.doctourna.utils;

import com.codename1.messaging.Message;
import com.codename1.ui.Display;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author mouhe
 */
public class Emailer {
    public static final String EMAIL = "mouhebbenabdallah@gmail.com";
    public static final String MDP = "Mounira11";
    
    public static void send(String recepient, String content) throws Exception {
        System.out.println("Préparation de l'email.");
        Properties properties = new Properties();

        //Enable authentication
        properties.put("mail.smtp.auth", "true");
        //Set TLS encryption enabled
        properties.put("mail.smtp.starttls.enable", "true");
        //Set SMTP host
        properties.put("mail.smtp.host", "smtp.gmail.com");
        //Set smtp port
        properties.put("mail.smtp.port", "587");

        //Your gmail address
        String myAccountEmail = "mouhebbenabdallah@gmail.com";
        //Your gmail password
        String password = "Mounira11";

        //Create a session with account credentials
        javax.mail.Session session = javax.mail.Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });

        //Prepare email message
        javax.mail.Message message = prepareMessage(session, myAccountEmail, recepient, content);

        //Send mail
        Transport.send(message);
        System.out.println("Message envoyé avec succés.");
    }
    
    public static void send(String recepient, String content, String subject) throws Exception {
        System.out.println("Préparation de l'email.");
        Properties properties = new Properties();

        //Enable authentication
        properties.put("mail.smtp.auth", "true");
        //Set TLS encryption enabled
        properties.put("mail.smtp.starttls.enable", "true");
        //Set SMTP host
        properties.put("mail.smtp.host", "smtp.gmail.com");
        //Set smtp port
        properties.put("mail.smtp.port", "587");

        //Your gmail address
        String myAccountEmail = "mouhebbenabdallah@gmail.com";
        //Your gmail password
        String password = "Mounira11";

        //Create a session with account credentials
        javax.mail.Session session = javax.mail.Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });

        //Prepare email message
        javax.mail.Message message = prepareMessage(session, myAccountEmail, recepient, content, subject);

        //Send mail
        Transport.send(message);
        System.out.println("Message envoyé avec succés.");
    }

    private static javax.mail.Message prepareMessage(javax.mail.Session session, String myAccountEmail, String recepient, String content) {
        try {
            javax.mail.Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject("RDV DOCTOURNA");
            String htmlCode = content;
            message.setContent(htmlCode, "text/html");
            return message;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    private static javax.mail.Message prepareMessage(javax.mail.Session session, String myAccountEmail, String recepient, String content, String subject) {
        try {
            javax.mail.Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject(subject);
            String htmlCode = content;
            message.setContent(htmlCode, "text/html");
            return message;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
