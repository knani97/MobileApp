/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyberclan.doctourna;

import com.cyberclan.doctourna.models.User;
import com.cyberclan.doctourna.services.CalendrierService;
import com.cyberclan.doctourna.services.TacheService;
import com.cyberclan.doctourna.services.UserService;
import com.cyberclan.doctourna.utils.Session;
import com.cyberclan.doctourna.Manipulator;
import com.cyberclan.doctourna.models.Disponibilite;
import com.cyberclan.doctourna.models.Rdv;
import com.cyberclan.doctourna.models.Tache;
import com.cyberclan.doctourna.services.RdvService;
import java.util.Date;

/**
 *
 * @author mouhe
 */
public class Console {

    public static void start() {
        TacheService ts = new TacheService();
        CalendrierService cs = new CalendrierService();
        UserService us = new UserService();
        RdvService rs = new RdvService();
        int cal = cs.getCalendrierByUid(Session.getId()).getId();
        
        //System.out.println(ts.getTachesByCal(cs.getCalendrierByUid(Session.getId()).getId()));
        //System.out.println(cs.getCalendrierByUid(Session.getId()));
        //System.out.println(us.getUser(Session.getId()));
        //System.out.println(rs.getRdvsByUid(Session.getId()));
        /*Date date1 = new Date();
        Date date2 = Manipulator.dateFrom("2021-05-16|02:05:00");
        Date duree1 = Manipulator.timeFrom("2021-05-16|01:05:00");
        Date duree2 = Manipulator.timeFrom("2021-05-16|00:05:00");
        ts.addDispo(new Disponibilite(null, date1, date2, duree1, duree2), cal);
        System.out.println(date1 + " " + date2 + " " + duree1 + " " + duree2 + " " + cal);*/
        /*User patient = us.getUser(1);
        User medecin = us.getUser(2);
        Tache tacheDispo = ts.getTache(151);
        rs.addRdv(new Rdv(null, "test", "jointure", tacheDispo, medecin, patient), cal);*/
        /*Rdv rdv = rs.getRdv(15);
        rs.cancelRdv(rdv);*/
    }
}
