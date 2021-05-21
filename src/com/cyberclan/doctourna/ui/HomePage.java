/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyberclan.doctourna.ui;

import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.cyberclan.doctourna.utils.Session;
import com.cyberclan.doctourna.utils.ToolbarInitializer;
import java.io.IOException;

/**
 *
 * @author mouhe
 */
public class HomePage {

    public Form current = new Form("DOCTOURNA", BoxLayout.y());

    public void show() {
        ToolbarInitializer.initToolbar(current);
        init();
        EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(current.getWidth(), current.getWidth() / 2, 0xffff0000), true);
            URLImage background = URLImage.createToStorage(placeholder, "background.png",
                    "file:///C:/Users/mouhe/OneDrive/Documents/NetBeansProjects/DOCTOURNA/src/images/background.png");
            background.fetch();
            current.getUnselectedStyle().setBgImage(background);
            current.getUnselectedStyle().setBackgroundType(Style.BACKGROUND_IMAGE_TILE_BOTH);
        current.show();
    }

    public void init() {
        Label body = new Label("WELCOME TO DOCTOURNA");
        try {
            Container container = new Container(BoxLayout.y());
            Image iconNews = Image.createImage("file:///C:/Users/mouhe/OneDrive/Documents/NetBeansProjects/DOCTOURNA-mobile/src/Images/logodoc.png");
            Image footer = Image.createImage("file:///C:/Users/mouhe/OneDrive/Documents/NetBeansProjects/DOCTOURNA/src/images/about.jpg");
            container.add(iconNews).add(footer);
            body.setAlignment(TextArea.CENTER);
            current.add(body);
            current.add(container);
            //current.add(new Label(Session.getPrenom() + " " + Session.getNom()));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
