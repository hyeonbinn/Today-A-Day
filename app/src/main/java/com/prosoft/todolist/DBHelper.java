package com.prosoft.todolist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MyDatabase";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 데이터베이스 테이블 생성 SQL 문장을 작성
        String createTableQuery = "CREATE TABLE MyTable (_id INTEGER PRIMARY KEY, name TEXT, age INTEGER);";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 데이터베이스 업그레이드 시 실행되는 코드를 작성합니다.
        // 예를 들어, 기존 테이블을 삭제하고 새로운 구조로 생성할 수 있습니다.
        db.execSQL("DROP TABLE IF EXISTS MyTable");
    }
}