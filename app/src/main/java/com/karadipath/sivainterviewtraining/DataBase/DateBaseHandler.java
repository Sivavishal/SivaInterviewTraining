package com.karadipath.sivainterviewtraining.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.karadipath.sivainterviewtraining.task2.UserDetailsTable;


public class DateBaseHandler extends SQLiteOpenHelper {

    public static String DBName = "InterviewTask";
    static int DBVersion = 1;

    public DateBaseHandler(Context context) {
        super(context, DBName, null, DBVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserDetailsTable.CreateTableStatement);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(UserDetailsTable.DropTableStatement);
    }



}
