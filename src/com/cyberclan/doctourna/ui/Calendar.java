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
import com.cyberclan.doctourna.forms.EditTacheForm;
import com.cyberclan.doctourna.models.Tache;
import com.cyberclan.doctourna.services.CalendrierService;
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
public class Calendar {
    
    public Form parent = new Form("Calendrier");
    public com.cyberclan.doctourna.ui.Calendar tachesList;
    
    private Form form = new Form();
    private Map<String, Object>[] data = new HashMap[5];
    private TacheService ts = new TacheService();
    private CalendrierService cs = new CalendrierService();

    private GenericListCellRenderer cellContainer;

    private com.codename1.ui.List list;
    private Container container = new Container(new BorderLayout());
    private java.util.ArrayList<Integer> deleted = new java.util.ArrayList<Integer>();

    public Button addButton = new Button();
    public ArrayList<Tache> taches = ts.getTachesByCal(cs.getCalendrierByUid(Session.getId()).getId());

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public void showForm() {
        tachesList = this;
        list = new com.codename1.ui.List(createGenericListCellRendererModelData(new Date()));
        cellContainer = new GenericListCellRenderer(createGenericRendererContainer(), createGenericRendererContainer());
        list.setRenderer(cellContainer);
        //Form form = new Form("GenericListCellRenderer", new BorderLayout());
        container.add(BorderLayout.CENTER, list);
        clearList();
        clearList();
        clearList();
        // Bouton Ajouter
        addButton = new Button("Ajouter");
        container.add(BorderLayout.SOUTH, addButton);
        // Ajout de la liste
        form.add(BorderLayout.SOUTH, container);
        //form.show();
    }

    public void refresh(Date date) {
        int i = 0;

        for (Map<String, Object> map : data) {
            map.clear();
        }

        clearList();
        clearList();
        clearList();

        for (Tache t : taches) {
            if (!deleted.contains(t.getId()) && Manipulator.toPureDate(t.getDate()).contains(Manipulator.toPureDate(date))) {
                data[i] = new HashMap<>();
                data[i].put("Id", t.getId());
                data[i].put("Name", t.getLibelle());
                data[i].put("Surname", t.getDescription());
                data[i].put("Selected", Boolean.TRUE);
                list.getModel().addItem(data[i]);
                i++;
            }
        }
        form.revalidate();
    }

    public void reinitTaches() {
        taches = ts.getTachesByCal(cs.getCalendrierByUid(Session.getId()).getId());
    }

    private void clearList() {
        for (int i = 0; i < list.getModel().getSize(); i++) {
            list.getModel().removeItem(i);
        }
    }

    private Container createGenericRendererContainer() {
        Label name = new Label();
        name.setFocusable(true);
        name.setName("Name");
        Label surname = new Label();
        surname.setFocusable(true);
        surname.setName("Surname");
        CheckBox selected = new CheckBox();
        selected.setName("Selected");
        selected.setFocusable(true);
        Label id = new Label();
        id.setName("Id");
        id.setHidden(true);
        // Boutons Modifier et Supprimer
        Container optContainer = new Container(BoxLayout.y());
        Button editButton = new Button("Modifier");
        Button deleteButton = new Button("Supprimer");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Tache tache = ts.getTache(Integer.parseInt(id.getText()));
                EditTacheForm form = new EditTacheForm(tache);
                form.mainForm = parent;
                form.tachesList = tachesList;
                form.show();
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Dialog.show("Suppression Tâche", "Voulez-vous vraiment supprimer la tâche ?", "Oui", "Non");
                Tache tache = ts.getTache(Integer.parseInt(id.getText()));
                ts.deleteTache(tache);
                deleted.add(Integer.parseInt(id.getText()));
                refresh(tache.getDate());
            }
        });
        optContainer.add(editButton);
        optContainer.add(deleteButton);
        // Container
        Container c = BorderLayout.center(name).
                add(BorderLayout.SOUTH, surname).
                add(BorderLayout.WEST, selected).
                add(BorderLayout.EAST, optContainer).
                add(BorderLayout.NORTH, id);
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
