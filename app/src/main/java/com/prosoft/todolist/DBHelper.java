package com.prosoft.todolist;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;


public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MyDatabase";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Members");

        sqLiteDatabase.execSQL("create table Todolist (mID integer primary key autoincrement, todolist text);");

        sqLiteDatabase.execSQL("INSERT INTO Members VALUES (1,'모바일프로그래밍 과제');");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public ArrayList<String> getTodolist (){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT todolist FROM Todolist",null);
        ArrayList<String> result = new ArrayList<>();
        while (cursor.moveToNext()) {
            result.add(cursor.getString(0));
        }
        cursor.close();
        return result;
    }

}