package com.kahan.david.myworkhours.model;

import java.util.Date;

/**
 * Created by david on 19/04/2018.
 */
public class StatItem {

    String date;

    String duration;

    public StatItem(String date, String duration) {
        this.date = date;
        this.duration = duration;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }


}
