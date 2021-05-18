/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyberclan.doctourna;

import com.codename1.ui.Button;
import com.codename1.ui.Calendar;
import static com.codename1.ui.Component.CENTER;

/**
 *
 * @author mouhe
 */
public class CustomCalendar extends Calendar {

    @Override
    protected Button createDay() {
        //Customize your button here
        Button day = new Button();
        day.setAlignment(CENTER);
        day.setUIID("Calendar");
        day.setEndsWith3Points(false);
        day.setTickerEnabled(false);
        return day;
    }

    @Override
    protected void updateButtonDayDate(Button dayButton, int currentMonth, int day) {
        //Customize day values 
        dayButton.setText("" + day);
    }
}
