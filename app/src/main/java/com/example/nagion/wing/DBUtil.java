package com.example.nagion.wing;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Nagion on 2016. 8. 6..
 */
public class DBUtil extends SQLiteOpenHelper {
    public DBUtil(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //DB가 없을 때 딱 한 번 실행됨
    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuffer sb = new StringBuffer();
        sb.append("CREATE TABLE USER_SETTINGS (");
            sb.append("_id INTEGER PRIMARY KEY AUTOINCREMENT, ");
            sb.append("logined INTEGER ");
        sb.append(")");

        db.execSQL(sb.toString());
    }

    //어플리케이션 버전 업으로 테이블 구조가 변경되었을 때 실행됨
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


    public boolean isLogined(){
        SQLiteDatabase db = getReadableDatabase();

        StringBuffer sb = new StringBuffer();
        sb.append("SELECT isLogined FROM USER_SETTINGS");

        try {
            Cursor cursor = db.rawQuery(sb.toString(), null);

            if (cursor.moveToNext() && (cursor.getInt(0) == 1)){
                return true;
            }
            /* before code
            if (cursor.moveToNext()) {
                if (cursor.getInt(0) == 1) {
                    return true;
                }
            }
            */
        }
        catch(Exception e){
            return false;
        }

        return false;
    }


}
