/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyberclan.doctourna.forms;

import com.codename1.components.MultiButton;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.spinner.Picker;
import com.cyberclan.doctourna.CustomCalendar;
import com.cyberclan.doctourna.models.Calendrier;
import com.cyberclan.doctourna.models.Tache;
import com.cyberclan.doctourna.services.CalendrierService;
import com.cyberclan.doctourna.services.TacheService;
import com.cyberclan.doctourna.utils.Session;
import com.cyberclan.doctourna.utils.ToolbarInitializer;
import static com.cyberclan.doctourna.utils.ToolbarInitializer.initToolbar;
import java.io.IOException;
import java.util.Date;

/**
 *
 * @author mouhe
 */
public class AddCalendrierForm {

    public Form mainForm = new Form();
    public Form current = new Form("Calendrier", BoxLayout.y());

    private CalendrierService cs = new CalendrierService();
    private TacheService ts = new TacheService();

    private int format;

    public void show() {
        initMenu();
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

    public void initMenu() {
        Container menuForm = new Container(new GridLayout(1, 4));
        menuForm.setScrollableX(true);
        menuForm.setScrollVisible(true);
        menuForm.setScrollableY(false);
        menuForm.setUIID("ContainerWhite");
        Button btn1 = new Button("");
        btn1.setUIID("ButtonSelect");
        Button btn2 = new Button("");
        btn2.setUIID("ButtonNonSelect");
        Button btn3 = new Button("");
        btn3.setUIID("ButtonNonSelect");

        btn1.addActionListener((evt) -> {
            AddCalendrierForm form = new AddCalendrierForm();
            form.mainForm = mainForm;
            form.show();
        });
        btn2.addActionListener((evt) -> {
            EditCalendrierForm form = new EditCalendrierForm();
            form.mainForm = mainForm;
            form.show();
        });
        btn3.addActionListener((evt) -> {
            Dialog.show("Suppression Calendrier", "Voulez-vous vraiment supprimer votre calendrier et tous ses tâches ?", "Oui", "Non");
        });

        menuForm.add(btn1);
        if (cs.getCalendrierByUid(Session.getId()) != null) {
            menuForm.add(btn2);
            menuForm.add(btn3);
        }

        try {
            Image iconNews = Image.createImage("file:///C:/Users/mouhe/Downloads/DoctournaApp/DoctournaApp/src/Images/news.png");
            btn1.setIcon(iconNews.scaledLargerRatio(95, 95));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            Image iconArticleP = Image.createImage("file:///C:/Users/mouhe/Downloads/DoctournaApp/DoctournaApp/src/Images/articleP.png");
            btn2.setIcon(iconArticleP.scaledLargerRatio(95, 95));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        current.add(menuForm);
    }

    public void init() {
        if (cs.getCalendrierByUid(Session.getId()) == null) {
            Container timezone = new Container(BoxLayout.x());
            Label lblTimezone = new Label("Timezone:");
            TextField txtTimezone = new TextField();
            timezone.add(lblTimezone).add(txtTimezone);
            Container email = new Container(BoxLayout.x());
            Label lblEmail = new Label("Option Email:");
            CheckBox chcBoxEmail = new CheckBox();
            email.add(lblEmail).add(chcBoxEmail);
            Button btnAjout = new Button("Ajouter");
            btnAjout.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    Calendrier calendrier = new Calendrier();
                    calendrier.setFormat(format);
                    calendrier.setEmail(chcBoxEmail.isSelected());
                    calendrier.setTimezone(txtTimezone.getText());
                    calendrier.setCouleur("black");
                    cs.addCalendrier(calendrier);
                    mainForm.showBack();
                }
            });
            initPicker();

            current.add(timezone).
                    add(email).
                    add(btnAjout);
        } else {
            Button btnAjout = new Button("Consulter Calendrier");
            btnAjout.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    Form calendrierForm = new Form("Calendrier", new BorderLayout());
                    initToolbar(calendrierForm);
                    com.cyberclan.doctourna.ui.Calendar tachesList = new com.cyberclan.doctourna.ui.Calendar();
                    tachesList.setForm(calendrierForm);
                    System.out.println(tachesList.taches);
                    CustomCalendar cld = new CustomCalendar(tachesList.taches);
                    //cld.addActionListener((e) -> Log.p("You picked: " + new Date(cld.getSelectedDay())));
                    cld.addActionListener((e) -> tachesList.refresh(new Date(cld.getSelectedDay())));
                    calendrierForm.add(BorderLayout.CENTER, cld);
                    tachesList.parent = calendrierForm;
                    tachesList.showForm();
                    tachesList.addButton.addActionListener((e) -> {
                        System.out.println(cld.getSelectedDay());
                        AddTacheForm addTacheForm = new AddTacheForm();
                        addTacheForm.mainForm = calendrierForm;
                        addTacheForm.tachesList = tachesList;
                        addTacheForm.currentDate = new Date(cld.getSelectedDay());
                        addTacheForm.show();
                    });
                    calendrierForm.show();
                }
            });

            current.add(btnAjout);
        }
    }

    public void initPicker() {
        String[] characters = {"Plage Horaire", "Calendrier Standard"};
        String[] actors = {"Un format d plage horaire.", "Une vue annuelle"};
        int size = Display.getInstance().convertToPixels(7);
        EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(size, size, 0xffcccccc), true);
        Image[] pictures = {
            URLImage.createToStorage(placeholder, "plagehoraire", "https://lh3.googleusercontent.com/proxy/HJTH80ynWsb6Skf0jC0_He2a9zlexiy5SqfOhBJxosWkaJGC8-_kmEeMighBJeanDYOq8yE2585r5w6bbbvMGb0J_AQW4Wq28cXHOMrkxeU-hmV2QhgP1tNS2J827VRk_VZWJfA"),
            URLImage.createToStorage(placeholder, "standard", "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAOEAAADhCAMAAAAJbSJIAAAAq1BMVEX////s8PHnTDy9w8eVpabAOSvq7u+6wMTO09a/xcnIztHnSTnnQCzqwL7s9PXpXlGptLeTqaq7yMzfW0319vbIoJ7+9/algX7BNCTX293nRjXLPjDCMiHiSTr2zcrwnZbsg3nrdGnlOCHgU0S0h4O8QDPYRTbqaV3hbGH64d/XTj+jgX7OOCfpal6ofHjDLRutcWygjIq5TEGxY12Ym5u3VEudkpGXn5++lJFR1Vg0AAAF0klEQVR4nO2daVviSBRGgZiNMOOgqCQsorTY3fZi06j9/3/ZFGuSugUOqbpwcd7zrb395M2pJamiXGo1AAAAAAAz9TSN0qBzsLxOoPLS+sHygjhc4EWHcexE3jIwDg6SV4tCb0UYHiIyCPPA6AB5teYmbx7JrxiU8prsebW0GKjgHqidclyYMufVOpog+7iJ9EDuJtW70AuZA0kedyfGnp7IOxMD3dCLWfNok3K3KRkz3INGn4bsE1GfhuwTkRp6zIYkD4aWwNA5MHQODJ0DQ+d8aMPr4e3o8fOFHhhfjc/5GF+RhfDF58fR7fDaud/w7r6VZdklMby4aiV8tK5o4KW6j9b93dCt32MraygSo2GDD6NhMq9krUd3jtejVrIMFGSobqY1cjRWv4yzdaAoQ9WP4y8uBIfZ5orSDBtJ5mCkfikIijNUita9eD0uXE+eYSMZ287FUVYMVIaxDrMhySsbNrKRneCwfPvJ5T+EP6U2cEz2hwaWDRstu6n4WL5aMm6fabQ/sRp+ooFj7Z7OHXahSEO7TrzT7l6kYXZXXfD6XgtMvtLAG1bDGxr4VTNs3Fd/nOqDtDF4oIFP3xgNvz3RwIeB9p8shumt1j0D32B405+wCU76hj588DXF7Lay4Ugz7JkN9URnDHyzYU8zHFU2/J5ogVsMtURn9LYYak2afK9seJ5ogVsMmTpRtegWw3KTWrwRy4Yq0Pdp4I++nugK1aL9HzRwfhulJnVmOFFX/qnnnbWfu+rrLIbqut1nYnj2U3299HBzZqia1O//0hPbU9KmjliMmSnJ+6XGTHnQODXs/tYTZ31Ww/5MN/zdZTX0u7O2IdDneCVOfEOTtmeLPE7DF23MLAL5+tDvavPihduw3KjtV99nNvT912LgcsgwGU6Wgf2Xt/Yys714Fy5gEFw8SxeBN5u8t5dVIM+zdN2m3e7z69tZ+2z2NF0Lsr0Pl4rTp5nKe3t97nZNY8aZ4SbR7/Yfpi9Tf+3HuKZZO/oq76G/9uNa06yH6cqy+A8Wwc0wpXnlR7c7w7wTNfj2Fma0SeHQcEsi19Zia5NqLerQsDxO+QW3KOrLC5eGJkVOQaMiWT85NaSKvIIGRbpAdGvYGPTeyXNNuU17hqeaY8OSI7/fnMlOPwZDxWDS6/UmXC+JffM4DGUBQxjKB4YwlA8MYSgfGMJQPjCEoXxguINx6zQYVzb861SobPj3qVDZsH4qwBCG8oEhDOUDQxjKB4YwlA8MYSgfGO5hGJAfWo2jTa25vVanteamFtFasL0Wb2o8hobf9ZXbk1puQWpenNvT38mWG9LAQxt6Owy9/2hIa7khqcEQhjCEIQxhCEMYwhCGMIThSRp+uP3hh9/jCwWGMJQPDGEoHxjCsEDQJKQ7avmaJtqrlq9p0h01HsPK61LyF3Gkrkv/h3sLGMIQhjCEIQxhCEMYwhCGMHRhuGt/6MLw4PtDb8e5BT1iyA13nVvQWsGQlDzmc4uAsKsWWNd2BrIYygSGMJQPDGEoHxjuYRhEhM25RX2/Wn5uke5XO+a6lJxNVDy3CHFuAUMYwhCGMIQhDGEIQxjCEIYwPLohITf0SK24A9Yp7oB18rOJiAYe/NzC/myi8pmGvaHpYhIJYLiV9P2LiyCtbBi9f3ERRJUN4/cvLoK4uuFpTMTAwvA0hmlkYWj6rgBxBJ6FYXgKnRiF1Q2bxkWgMNRSuWlh6Il/2MzX+9UN07C4PZCJ6oWw+ht/vlcKZSvOT+bC6qu2znw3GJp+lEoIweLoMexUNqzF2n5UGKs9d/VH6XIizhvJdLJ8dILV+bDFNFTkn1VIcwzyzzdsBNedOG8pL0oNG+7jkEb59wXYdeF6Jq6uJYfip1Z2grU6/XBNFmH1D2lWpLIVbceoeEUXgqIV3QjOF28yHUOL5ZpOJNAxDKt/AGWgHnmyJNXr2fohqtEJInr2cCziKLBYbe/U7NQ7x0bdAY8cAAAAAAAAAAAARPMvWqd4ise4zEIAAAAASUVORK5CYII=")
        };

        MultiButton b = new MultiButton("Selectionner un format");
        b.addActionListener(e -> {
            Dialog d = new Dialog();
            d.setLayout(BoxLayout.y());
            d.getContentPane().setScrollableY(true);
            for (int iter = 0; iter < characters.length; iter++) {
                MultiButton mb = new MultiButton(characters[iter]);
                mb.setTextLine2(actors[iter]);
                mb.setIcon(pictures[iter]);
                d.add(mb);
                mb.addActionListener(ee -> {
                    b.setTextLine1(mb.getTextLine1());
                    b.setTextLine2(mb.getTextLine2());
                    b.setIcon(mb.getIcon());
                    d.dispose();
                    b.revalidate();
                    if (b.getTextLine1().contains("Plage Horaire")) {
                        format = 1;
                    } else if (b.getTextLine1().contains("Calendrier Standard")) {
                        format = 2;
                    }
                });
            }
            d.showPopupDialog(b);
        });

        current.add(b);
    }
}
