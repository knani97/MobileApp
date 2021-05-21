/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyberclan.doctourna.utils;

import static com.codename1.ui.CN.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.UIManager;
import com.cyberclan.doctourna.CustomCalendar;
import com.cyberclan.doctourna.forms.AddCalendrierForm;
import com.cyberclan.doctourna.forms.AddTacheForm;
import com.cyberclan.doctourna.forms.AjoutDispoForm;
import com.cyberclan.doctourna.services.CalendrierService;
import com.cyberclan.doctourna.ui.HomePage;
import com.doctourna.views.ListerVideo;
import com.mycompany.Views.Medicament_Sans_Categorie_Form;
import com.mycompany.gui.SignUpForm;
import entity.Article;
import java.util.Date;
import views.ChatBot;
import views.Corona;
import views.ListerPharmacie;

/**
 *
 * @author mouhe
 */
public class ToolbarInitializer {

    public static void initToolbar(Form form) {
        Toolbar tb = form.getToolbar();

        Form f2 = new Form(new FlowLayout(CENTER, CENTER));
        f2.add(new Label("Settings"));

        Image icon = UIManager.initFirstTheme("/theme").getImage("file:///C:/Users/mouhe/OneDrive/Documents/NetBeansProjects/DOCTOURNA/src/images/logodocpdf.png");
        Container topBar = BorderLayout.center(new Label(icon));
        topBar.setUIID("SideCOmmand");

        tb.addComponentToSideMenu(topBar);
        tb.addMaterialCommandToSideMenu(" Home", FontImage.MATERIAL_HOME, e -> {
            HomePage homePage = new HomePage();
        });
        tb.addMaterialCommandToSideMenu(" Médicaments", FontImage.MATERIAL_MEDICAL_SERVICES, (ActionListener) (ActionEvent evt) -> {
            new Medicament_Sans_Categorie_Form(form).show();
        });
        tb.addMaterialCommandToSideMenu(" Pharmacies", FontImage.MATERIAL_MEDICAL_SERVICES, (ActionListener) (ActionEvent evt) -> {
            new ListerPharmacie(form).show();
        });
        tb.addMaterialCommandToSideMenu(" Calendrier", FontImage.MATERIAL_CALENDAR_TODAY, (ActionListener) (ActionEvent evt) -> {
            AddCalendrierForm addCalForm = new AddCalendrierForm();
            addCalForm.mainForm = form;
            addCalForm.show();
        });

        tb.addMaterialCommandToSideMenu(" Prise RDVs", FontImage.MATERIAL_ARTICLE, (ActionListener) (ActionEvent evt) -> {
            com.cyberclan.doctourna.ui.ListeRDVs list = new com.cyberclan.doctourna.ui.ListeRDVs();
            list.parentForm = form;
            list.showForm();
        });

        tb.addMaterialCommandToSideMenu(" News / Articles", FontImage.MATERIAL_ARTICLE, (ActionListener) (ActionEvent evt) -> {
            Interface.MesArticle mod = new Interface.MesArticle();
            mod.MesArticle(new Article(1)).show();
        });

        tb.addMaterialCommandToSideMenu(" Regime / Sport", FontImage.MATERIAL_SPORTS, (ActionListener) (ActionEvent evt) -> {
            new ListerVideo(form).show();
        });

        tb.addMaterialCommandToSideMenu(" Corona", FontImage.MATERIAL_MEDICAL_SERVICES, (ActionListener) (ActionEvent evt) -> {
            new Corona().show();
        });

        tb.addMaterialCommandToSideMenu(" About", FontImage.MATERIAL_INFO, (ActionListener) (ActionEvent evt) -> {
            System.out.println("REDIRECTION");
        });

        tb.addMaterialCommandToSideMenu(" Logout", FontImage.MATERIAL_INFO, (ActionListener) (ActionEvent evt) -> {
            new SignUpForm(UIManager.initFirstTheme("/start")).show();
        });
    }
}
