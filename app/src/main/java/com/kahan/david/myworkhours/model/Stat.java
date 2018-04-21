package com.kahan.david.myworkhours.model;

/**
 * Created by david on 21/04/2018.
 */
public class Stat {

    public static final String TABLE_NAME = "notes";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_DURATION = "duration";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    private int id;
    private String duration;
    private String timestamp;


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_DURATION + " TEXT,"
                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public Stat() {
    }

    public Stat(int id, String duration, String timestamp) {
        this.id = id;
        this.duration = duration;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
