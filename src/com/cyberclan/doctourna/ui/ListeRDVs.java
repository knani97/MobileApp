/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyberclan.doctourna.ui;

import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.list.GenericListCellRenderer;
import com.cyberclan.doctourna.Manipulator;
import com.cyberclan.doctourna.models.Rdv;
import com.cyberclan.doctourna.models.Tache;
import com.cyberclan.doctourna.services.CalendrierService;
import com.cyberclan.doctourna.services.RdvService;
import com.cyberclan.doctourna.services.TacheService;
import com.cyberclan.doctourna.utils.Session;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author mouhe
 */
public class ListeRDVs {

    private Form form = new Form();
    private Map<String, Object>[] data = new HashMap[5];
    private RdvService rs = new RdvService();

    private GenericListCellRenderer cellContainer;
    private ArrayList<Rdv> rdvs = rs.getRdvsByUid(Session.getId());

    private com.codename1.ui.List list;
    private Container container = new Container(new BorderLayout());

    public Button addButton;

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public void showForm() {
        list = new com.codename1.ui.List(createGenericListCellRendererModelData(new Date()));
        cellContainer = new GenericListCellRenderer(createGenericRendererContainer(), createGenericRendererContainer());
        list.setRenderer(cellContainer);
        //Form form = new Form("GenericListCellRenderer", new BorderLayout());
        clearList();
        clearList();
        clearList();
        container.add(BorderLayout.CENTER, list);
        refresh();
        // Ajout de la liste
        form.add(BorderLayout.NORTH, container);
        //form.show();
    }

    public void refresh() {
        int i = 0;

        for (Map<String, Object> map : data) {
            map.clear();
        }

        clearList();
        clearList();
        clearList();

        for (Rdv r : rdvs) {
            data[i] = new HashMap<>();
            data[i].put("Id", r.getId());
            data[i].put("Date", r.getDate());
            if (Session.getType() == 1) {
                data[i].put("Avec", "Avec Dr. " + r.getMedecinId().getNom() + r.getMedecinId().getPrenom());
            } else {
                data[i].put("Avec", "Avec " + r.getPatientId().getNom() + r.getPatientId().getPrenom());
            }
            data[i].put("Etat", Manipulator.getEtatRDVString(r.getEtat()));
            list.getModel().addItem(data[i]);
            i++;
        }
        form.revalidate();
    }
    
    public void refreshEtats(int id) {
        int i = 0;

        for (Map<String, Object> map : data) {
            map.clear();
        }

        clearList();
        clearList();
        clearList();

        for (Rdv r : rdvs) {
            data[i] = new HashMap<>();
            data[i].put("Id", r.getId());
            data[i].put("Date", r.getDate());
            if (Session.getType() == 1) {
                data[i].put("Avec", "Avec Dr. " + r.getMedecinId().getNom() + " " + r.getMedecinId().getPrenom());
            } else {
                data[i].put("Avec", "Avec " + r.getPatientId().getNom() + " " + r.getPatientId().getPrenom());
            }
            if (id == r.getId())
                data[i].put("Etat", "Annulé");
            else
                data[i].put("Etat", Manipulator.getEtatRDVString(r.getEtat()));
            list.getModel().addItem(data[i]);
            i++;
        }
        form.revalidate();
    }

    private void clearList() {
        for (int i = 0; i < list.getModel().getSize(); i++) {
            list.getModel().removeItem(i);
        }
    }

    private Container createGenericRendererContainer() {
        Label date = new Label();
        date.setFocusable(true);
        date.setName("Date");
        Label avec = new Label();
        avec.setFocusable(true);
        avec.setName("Avec");
        Label etat = new Label();
        etat.setFocusable(true);
        etat.setName("Etat");
        // Boutons Modifier et Supprimer
        Container optContainer = new Container(BoxLayout.y());
        Button reportButton = new Button("Reporter");
        Button cancelButton = new Button("Annuler");
        Button endButton = new Button("Terminer");
        reportButton.setFocusable(true);
        cancelButton.setFocusable(true);
        endButton.setFocusable(true);
        Label id = new Label();
        id.setName("Id");
        id.setFocusable(true);
        id.setHidden(true);
        reportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                form.getContentPane().animateLayout(200);
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Dialog.show("Annulation RDV", "Voulez-vous vraiment annuler le RDV?", "Oui", "Non");
                rs.cancelRdv(new Rdv(Integer.parseInt(id.getText())));
                refreshEtats(Integer.parseInt(id.getText()));
            }
        });
        endButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Rdv rdv = rs.getRdv(Integer.parseInt(id.getText()));
                Dialog.show("Terminer RDV", "Voulez-vous vraiment terminer le RDV?", "Oui", "Non");
                rs.endRdv(rdv);
                refresh();
            }
        });
        if (!etat.getText().contains("Annulé")) {
            optContainer.add(cancelButton);
            if (Session.getType() == 2) {
                optContainer.add(reportButton);
                optContainer.add(endButton);
            }
        }
        // Container
        Container c = BorderLayout.center(date).
                add(BorderLayout.NORTH, avec).
                add(BorderLayout.SOUTH, etat).
                add(BorderLayout.EAST, optContainer).
                add(BorderLayout.WEST, id);
        c.setUIID("ListRenderer");
        return c;
    }

    private Object[] createGenericListCellRendererModelData(Date date) {
        int i = 0;

        data[0] = new HashMap<>();
        data[1] = new HashMap<>();
        data[2] = new HashMap<>();
        data[3] = new HashMap<>();
        data[4] = new HashMap<>();

        return data;
    }
}
