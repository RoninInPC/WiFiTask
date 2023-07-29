package com.example.wifitasksecondtime;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WiFiDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "wifiDB";

    public static final int DATABASE_VERSION = 1;

    public static final String KEY_BSSID = "BSSID";

    public static final String KEY_CHANNEL = "channel";

    public static final String KEY_ID = "_id";

    public static final String KEY_LEVEL = "level";

    public static final String KEY_OPERATOR = "operator";

    public static final String KEY_PASSWORD = "password";

    public static final String KEY_SECURITY = "security";

    public static final String KEY_SSID = "SSID";

    public static final String KEY_VENUE = "venue";

    public static final String TABLE_NAME = "WiFiInfo";

    public WiFiDBHelper(Context paramContext) {
        super(paramContext, "wifiDB", null, 1);
    }

    public void onCreate(SQLiteDatabase paramSQLiteDatabase) {
        paramSQLiteDatabase.execSQL("create table WiFiInfo(_id integer primary key,SSID text,BSSID text,channel integer,level integer,security text,operator text,venue text,password text)");
    }

    public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2) {
        paramSQLiteDatabase.execSQL("drop table if exists WiFiInfo");
        onCreate(paramSQLiteDatabase);
    }
}
