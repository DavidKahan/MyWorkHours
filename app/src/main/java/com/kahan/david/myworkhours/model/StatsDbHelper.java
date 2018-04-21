package com.kahan.david.myworkhours.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 21/04/2018.
 */
public class StatsDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Stats.db";

    //region Singleton
    private static StatsDbHelper instance;
    public static StatsDbHelper getInstance(Context context) {
        if (instance == null){
            instance = new StatsDbHelper(context);
        }
        return instance;
    }
    private StatsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    //endregion

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Stat.CREATE_TABLE);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Stat.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


    //region dao
    public long insertStat(String duration) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Stat.COLUMN_DURATION, duration);

        // insert row
        long id = db.insert(Stat.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public List<StatItem> getAllStats() {
        List<StatItem> stats = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Stat.TABLE_NAME + " ORDER BY " +
                Stat.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                StatItem statItem = new StatItem(cursor.getString(cursor.getColumnIndex(Stat.COLUMN_TIMESTAMP)),cursor.getString(cursor.getColumnIndex(Stat.COLUMN_DURATION)) );
                stats.add(statItem);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return stat list
        return stats;
    }

    public Stat getStat(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Stat.TABLE_NAME,
                new String[]{Stat.COLUMN_ID, Stat.COLUMN_DURATION, Stat.COLUMN_TIMESTAMP},
                Stat.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare stat object
        Stat stat = new Stat(
                cursor.getInt(cursor.getColumnIndex(Stat.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Stat.COLUMN_DURATION)),
                cursor.getString(cursor.getColumnIndex(Stat.COLUMN_TIMESTAMP)));

        // close the db connection
        cursor.close();

        return stat;
    }

    public int getStatsCount() {
        String countQuery = "SELECT  * FROM " + Stat.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public void deleteStat(Stat stat) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Stat.TABLE_NAME, Stat.COLUMN_ID + " = ?",
                new String[]{String.valueOf(stat.getId())});
        db.close();
    }
    //endregion

}
