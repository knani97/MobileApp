/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyberclan.doctourna;

import com.codename1.l10n.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author mouhe
 */
public class Manipulator {

    public static Date dateFromFormat(String date, String format) {
        try {
            return new SimpleDateFormat(format).parse(date.replace('T', '|'));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return new Date();
    }

    public static Date dateFrom(String date) {
        return dateFromFormat(date, "yyyy-MM-dd|HH:mm:ss");
    }

    public static Date timeFrom(String date) {
        return dateFromFormat(date.substring(11), "HH:mm:ss");
    }

    public static String toDateString(Date date) {
        return date.toString();
    }

    public static String toTimeString(Date time) {
        return time.toString().substring(11).substring(0, 8);
    }

    public static String toPureDate(Date date) {
        return date.toString().substring(0, 10) + date.toString().substring(23);
    }

    public static int getEtat(String etat) {
        if (etat.contains("RDV")) {
            return 1;
        } else if (etat.contains("Prise Médicament")) {
            return 2;
        } else if (etat.contains("Personnelle")) {
            return 3;
        } else if (etat.contains("Disponibilité")) {
            return 4;
        } else if (etat.contains("RDV Perso")) {
            return 5;
        }

        return -1;
    }

    public static String getEtatString(int etat) {
        switch (etat) {
            case 1:
                return "RDV";
            case 2:
                return "Prise Médicament";
            case 3:
                return "Personnelle";
            case 4:
                return "Disponibilité";
            case 5:
                return "RDV Perso";
            default:
                return null;
        }
    }

    public static String getEtatRDVString(int etat) {
        switch (etat) {
            case 1:
                return "Disponible";
            case 2:
                return "Reporté";
            case 3:
                return "Annulé";
            case 4:
                return "Terminé";
            default:
                return null;
        }
    }
    
    public static String symfonyDateTime(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date);
    }
    
    public static String symfonyTime(Date date) {
        return new SimpleDateFormat("hh:mm:ss").format(date);
    }
    
    public static Date getDateFromTime(int minutes) {
        Date date = new Date();
        date.setTime((minutes - 60) * 60 * 1000);
        return date;
    }
    
    public static int getMonthFromDate(Date date) {
        return Integer.parseInt(new SimpleDateFormat("MM").format(date));
    }
    
    public static int getDayFromDate(Date date) {
        return Integer.parseInt(new SimpleDateFormat("dd").format(date));
    }
}
