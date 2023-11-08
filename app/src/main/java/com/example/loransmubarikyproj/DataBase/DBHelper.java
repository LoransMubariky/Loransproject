package com.example.loransmubarikyproj.DataBase;

import static com.example.loransmubarikyproj.DataBase.QueryString.*;


import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public  class DBHelper {
    private static final String DATABASE_NAME = "MyProject.db";
    private static final int DATABASE_VERSION = 1;


    private Context mContext;
    private DataBaseHelper dbhelper;
    private SQLiteDatabase db;

    public SQLiteDatabase getDb() {
        return db;
    }

    private class DataBaseHelper extends SQLiteOpenHelper {
        DataBaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            //sqLiteDatabase.execSQL(SQL_CREATE_PRODUCT);

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL(SQL_DELETE_PRODUCT);
            onCreate(sqLiteDatabase);
        }

    }
    public void Reset(){

        dbhelper.onUpgrade(db,1,1);
    }
    public DBHelper(Context context){
        mContext = context;
        dbhelper = new DataBaseHelper(mContext);
    }
    public DBHelper OpenWriteAble() throws SQLException{
        db = dbhelper.getWritableDatabase();
        return this;
    }
    public DBHelper OpenReadAble() throws SQLException{
        db = dbhelper.getReadableDatabase();
        return this;
    }

    public void Close(){
        dbhelper.close();
    }

}
