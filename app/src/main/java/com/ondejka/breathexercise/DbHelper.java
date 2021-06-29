package com.ondejka.breathexercise;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "breath_exercise.db";
    public static final String TABLE_NAME = "score_table";
    public static final int DATABASE_VERSION = 2;
    public static final String COL_1 = "ID";
    public static final String COL_2 = "userName";
    public static final String COL_3 = "dateTime";
    public static final String COL_4 = "time1";
    public static final String COL_5 = "time2";
    public static final String COL_6 = "time3";
    public static final String COL_7 = "time4";
    public static final String COL_8 = "timeMax";
    public static final String COL_9 = "timeAvg";
    public static final String COL_10 = "timeMax2";
    public static final String COL_11 = "timeMax3";
    public static final String COL_12 = "synchro";
    public static final String COL_13 = "trophy1";
    public static final String COL_14 = "trophy2";
    public static final String COL_15 = "trophy3";
    public static final String COL_16 = "trophy4";


    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        openDB();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        db.execSQL("create table " + TABLE_NAME +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "USERNAME TEXT, " +
                "DATETIME BIGINT, " +
                "TIME1 INTEGER, " +
                "TIME2 INTEGER, " +
                "TIME3 INTEGER, " +
                "TIME4 INTEGER, " +
                "TIMEMAX INTEGER, " +
                "TIMEAVG FLOAT, " +
                "TIMEMAX2 FLOAT, " +
                "TIMEMAX3 FLOAT, " +
                "SYNCHRO BOOLEAN, " +
                "TROPHY1 INTEGER, " +
                "TROPHY2 INTEGER, " +
                "TROPHY3 INTEGER, " +
                "TROPHY4 INTEGER" +
                ")");

    }

    public void updateDB() {
        SQLiteDatabase db = this.getWritableDatabase();
        onCreate(db);
    }

    public void openDB() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("create table if not exists " + TABLE_NAME +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "USERNAME TEXT, " +
                "DATETIME BIGINT, " +
                "TIME1 INTEGER, " +
                "TIME2 INTEGER, " +
                "TIME3 INTEGER, " +
                "TIME4 INTEGER, " +
                "TIMEMAX INTEGER, " +
                "TIMEAVG FLOAT, " +
                "TIMEMAX2 FLOAT, " +
                "TIMEMAX3 FLOAT, " +
                "SYNCHRO BOOLEAN, " +
                "TROPHY1 INTEGER, " +
                "TROPHY2 INTEGER, " +
                "TROPHY3 INTEGER, " +
                "TROPHY4 INTEGER" +
                ")");
    }


    public void deleteDBtable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public boolean insertData(String userName, long dateTime,
                              int time1, int time2, int time3, int time4,
                              int timeMax, float timeAvg, float timeMax2, float timeMax3, boolean synchro) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, userName);
        contentValues.put(COL_3, dateTime);
        contentValues.put(COL_4, time1);
        contentValues.put(COL_5, time2);
        contentValues.put(COL_6, time3);
        contentValues.put(COL_7, time4);
        contentValues.put(COL_8, timeMax);
        contentValues.put(COL_9, timeAvg);
        contentValues.put(COL_10, timeMax2);
        contentValues.put(COL_11, timeMax3);
        contentValues.put(COL_12, synchro);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;

    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }

    public Cursor getAllDataDESC() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME + " order by ID DESC", null);
        return res;
    }


    public void updateTrophy(int id, int trophyValue, int trophyId) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("trophy" + trophyId, trophyValue);
//        myDB.update(TABLE_NAME, "(Field1, Field2, Field3)" + " VALUES ('Bob', 19, 'Male')", "where _id = 1", null);
        myDB.update(TABLE_NAME, contentValues, " ID = " + id, null);
        Log.i("updateTrophy", String.valueOf(trophyId + trophyValue));

    }


    // Clear all trophy --------------------------------------------------------------------------------
    public void clearAllTrophy() {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues dataToUpdate = new ContentValues();
        dataToUpdate.put("trophy1", 0);
        dataToUpdate.put("trophy2", 0);
        dataToUpdate.put("trophy3", 0);
        dataToUpdate.put("trophy4", 0);
        String where = " trophy1 <> 0 OR trophy2 <> 0 OR trophy3 <> 0 OR trophy4 <> 0";

        //        myDB.execSQL("UPDATE " + TABLE_NAME + " SET TROPHY1 = 0, TROPHY2 = 0, TROPHY3 = 0, TROPHY4 = 0 WHERE TROPHY1 <> 0 OR TROPHY2 <> 0 OR TROPHY3 <> 0 OR TROPHY4 <> 0", null);
        myDB.update(TABLE_NAME, dataToUpdate, where, null);

    }


    // DEMO DATA --------------------------------------------------------------------------------
    public void insertDemoData1() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        long milis = 0;
        SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        Date d = null;
        String string_date;
        int i = 0;

        List<DemoData> demoDataList = new ArrayList<DemoData>();
        demoDataList.add(new DemoData("ondejka@hotmail.com", "25.11.2020 18:00", 110, 135, 145, 170, 170, 140, 158, 150));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "26.11.2020 18:00", 120, 135, 150, 140, 150, 136, 145, 142));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "27.11.2020 18:00", 120, 135, 150, 180, 180, 146, 165, 155));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "29.11.2020 18:31", 75, 90, 105, 123, 123, 98, 114, 106));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "30.11.2020 18:50", 78, 95, 110, 130, 130, 103, 120, 112));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "1.12.2020 19:06", 90, 110, 120, 130, 130, 112, 125, 120));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "5.12.2020 8:41", 94, 120, 150, 190, 190, 139, 170, 153));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "6.12.2020 8:12", 99, 120, 144, 165, 165, 132, 155, 143));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "7.12.2020 10:38", 85, 108, 147, 170, 170, 127, 158, 142));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "8.12.2020 9:41", 104, 120, 0, 0, 120, 112, 112, 112));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "12.12.2020 8:11", 98, 120, 136, 166, 166, 130, 151, 141));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "13.12.2020 8:46", 106, 120, 103, 143, 143, 118, 123, 122));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "17.12.2020 11:29", 99, 126, 140, 155, 155, 130, 147, 140));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "18.12.2020 8:20", 98, 126, 140, 164, 164, 132, 152, 143));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "19.12.2020 13:06", 91, 131, 151, 165, 165, 135, 158, 149));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "20.12.2020 8:20", 80, 95, 125, 151, 151, 113, 138, 124));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "21.12.2020 8:20", 80, 95, 125, 135, 135, 109, 130, 118));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "22.12.2020 8:20", 91, 105, 121, 135, 135, 113, 128, 120));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "23.12.2020 8:20", 93, 122, 140, 0, 140, 118, 140, 131));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "24.12.2020 8:20", 85, 123, 139, 155, 155, 126, 147, 139));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "26.12.2020 8:20", 65, 86, 121, 130, 130, 100, 125, 112));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "27.12.2020 8:20", 110, 135, 157, 172, 172, 143, 165, 155));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "28.12.2020 8:14", 95, 122, 124, 139, 139, 120, 132, 128));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "30.12.2020 8:03", 70, 90, 111, 136, 136, 102, 123, 112));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "31.12.2020 8:03", 99, 110, 135, 156, 156, 125, 145, 134));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "1.1.2021 8:03", 70, 101, 121, 135, 135, 107, 128, 119));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "2.1.2021 8:03", 90, 110, 127, 142, 142, 117, 135, 126));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "3.1.2021 8:30", 85, 87, 76, 134, 134, 95, 105, 99));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "4.1.2021 6:45", 78, 105, 120, 120, 120, 106, 120, 115));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "5.1.2021 6:45", 120, 142, 164, 180, 180, 151, 172, 162));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "7.1.2021 13:28", 76, 100, 121, 130, 130, 107, 125, 117));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "9.1.2021 7:37", 90, 100, 121, 130, 130, 110, 125, 117));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "10.1.2021 8:16", 70, 100, 121, 141, 141, 108, 131, 121));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "11.1.2021 9:36", 92, 120, 121, 140, 140, 118, 130, 127));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "12.1.2021 7:10", 90, 100, 110, 138, 138, 109, 124, 116));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "13.1.2021 7:06", 95, 121, 114, 125, 125, 114, 120, 120));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "18.1.2021 11:21", 90, 83, 122, 136, 136, 108, 129, 114));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "24.1.2021 7:45", 75, 121, 135, 147, 147, 120, 141, 134));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "25.1.2021 7:00", 70, 83, 91, 121, 121, 91, 106, 98));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "7.2.2021 8:00", 62, 122, 141, 160, 160, 121, 150, 141));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "8.2.2021 7:00", 62, 75, 108, 121, 121, 91, 114, 101));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "13.2.2021 7:00", 101, 151, 156, 155, 156, 141, 156, 154));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "20.2.2021 7:00", 79, 114, 131, 142, 142, 116, 136, 129));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "21.2.2021 8:58", 63, 105, 135, 154, 154, 114, 144, 131));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "22.2.2021 10:00", 91, 121, 125, 140, 140, 119, 133, 129));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "27.2.2021 7:38", 88, 102, 125, 153, 153, 117, 139, 127));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "28.2.2021 8:07", 91, 121, 138, 144, 144, 123, 141, 134));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "2.3.2021 7:06", 101, 122, 151, 181, 181, 139, 166, 151));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "6.3.2021 7:35", 78, 127, 133, 156, 156, 123, 145, 139));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "7.3.2021 8:51", 100, 134, 151, 166, 166, 138, 158, 150));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "8.3.2021 9:03", 86, 111, 127, 130, 130, 113, 128, 123));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "13.3.2021 6:34", 105, 130, 135, 140, 140, 127, 137, 135));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "14.3.2021 7:18", 90, 121, 135, 151, 151, 124, 143, 136));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "15.3.2021 15:45", 81, 95, 108, 122, 122, 102, 115, 108));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "20.3.2021 7:06", 107, 130, 141, 160, 160, 135, 150, 144));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "21.3.2021 7:06", 90, 130, 130, 165, 165, 129, 147, 142));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "27.3.2021 7:17", 122, 159, 182, 205, 205, 167, 194, 182));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "28.3.2021 7:43", 72, 117, 135, 161, 161, 121, 148, 138));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "29.3.2021 19:06", 121, 126, 152, 181, 181, 145, 166, 153));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "2.4.2021 8:32", 111, 135, 160, 181, 181, 147, 171, 159));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "3.4.2021 8:22", 120, 121, 120, 151, 151, 128, 135, 131));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "4.4.2021 7:48", 93, 135, 151, 160, 160, 135, 156, 149));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "5.4.2021 8:21", 77, 122, 133, 150, 150, 120, 142, 135));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "8.4.2021 7:02", 70, 90, 90, 140, 140, 97, 115, 107));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "10.4.2021 7:25", 105, 136, 151, 165, 165, 139, 158, 151));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "11.4.2021 8:08", 111, 138, 150, 171, 171, 143, 160, 153));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "12.4.2021 7:05", 81, 110, 75, 120, 120, 97, 115, 104));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "13.4.2021 7:09", 80, 99, 124, 140, 140, 111, 132, 121));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "14.4.2021 7:23", 85, 121, 140, 160, 160, 127, 150, 140));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "15.4.2021 7:08", 94, 120, 124, 150, 150, 122, 137, 131));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "15.4.2021 7:00", 80, 99, 123, 140, 140, 110, 132, 121));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "17.4.2021 7:59", 85, 135, 139, 140, 140, 125, 140, 138));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "18.4.2021 7:42", 95, 130, 146, 157, 157, 132, 151, 144));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "19.4.2021 7:03", 78, 98, 102, 120, 120, 99, 111, 107));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "20.4.2021 6:26", 106, 108, 124, 134, 134, 118, 129, 122));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "21.4.2021 6:44", 79, 105, 130, 137, 137, 113, 133, 124));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "22.4.2021 6:55", 92, 92, 130, 122, 130, 109, 126, 115));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "23.4.2021 6:56", 103, 108, 111, 120, 120, 111, 116, 113));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "24.4.2021 7:04", 97, 130, 137, 170, 170, 133, 153, 146));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "26.4.2021 6:47", 69, 97, 109, 135, 135, 102, 122, 114));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "27.4.2021 6:45", 81, 103, 129, 130, 130, 111, 129, 121));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "28.4.2021 6:56", 51, 82, 106, 130, 130, 92, 118, 106));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "29.4.2021 6:53", 52, 75, 86, 135, 135, 87, 110, 99));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "30.4.2021 7:19", 105, 124, 135, 150, 150, 128, 142, 136));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "1.5.2021 7:30", 114, 124, 135, 144, 144, 129, 140, 134));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "2.5.2021 8:39", 108, 123, 135, 167, 167, 133, 151, 142));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "7.5.2021 7:14", 96, 139, 142, 162, 162, 135, 152, 148));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "8.5.2021 6:55", 90, 120, 138, 158, 158, 127, 148, 139));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "9.5.2021 8:07", 86, 123, 133, 150, 150, 123, 142, 135));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "15.5.2021 7:57", 75, 108, 123, 151, 151, 114, 137, 127));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "22.5.2021 7:57", 91, 121, 133, 155, 155, 125, 144, 136));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "28.5.2021 6:54", 108, 122, 133, 147, 147, 128, 140, 134));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "2.6.2021 7:13", 95, 122, 137, 153, 153, 127, 145, 137));
        demoDataList.add(new DemoData("ondejka@hotmail.com", "4.6.2021 7:04", 75, 95, 129, 155, 155, 114, 142, 126));


        for (DemoData demodata : demoDataList) {
            i++;
            string_date = demodata.getDateTime();
            try {
                d = f.parse(string_date);
                milis = d.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            contentValues.put(COL_2, demodata.getUserName());
            contentValues.put(COL_3, milis);
            contentValues.put(COL_4, demodata.getTime1());
            contentValues.put(COL_5, demodata.getTime2());
            contentValues.put(COL_6, demodata.getTime3());
            contentValues.put(COL_7, demodata.getTime4());
            contentValues.put(COL_8, demodata.getTimeMax());
            contentValues.put(COL_9, demodata.getTimeAvg());
            contentValues.put(COL_10, demodata.getTimeMax2());
            contentValues.put(COL_11, demodata.getTimeMax3());
            contentValues.put(COL_12, false);
            db.insert(TABLE_NAME, null, contentValues);


        }
    }
}
