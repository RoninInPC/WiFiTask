package com.example.wifitasksecondtime;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class DatabaseWifi {
    private static SQLiteDatabase sqLiteDatabase_;

    private static WiFiDBHelper wiFiDBHelper_;

    public DatabaseWifi(Context paramContext) {
        WiFiDBHelper wiFiDBHelper = new WiFiDBHelper(paramContext);
        wiFiDBHelper_ = wiFiDBHelper;
        sqLiteDatabase_ = wiFiDBHelper.getReadableDatabase();
    }

    public static void cleverInsert(WiFiInfo paramWiFiInfo) {
        if (contains("SSID", paramWiFiInfo.SSID)) {
            update(paramWiFiInfo);
            return;
        }
        insert(paramWiFiInfo);
    }

    public static boolean contains(String paramString1, String paramString2) {
        SQLiteDatabase sQLiteDatabase = sqLiteDatabase_;
        String str = paramString1 + " = ?";
        Cursor cursor = sQLiteDatabase.query("WiFiInfo", new String[] { paramString1 }, str, new String[] { paramString2 }, null, null, null);
        boolean bool = cursor.moveToFirst();
        cursor.close();
        return bool;
    }

    public static String getPassword(String paramString) {
        Cursor cursor = sqLiteDatabase_.query("WiFiInfo", null, "SSID = ?", new String[] { paramString }, null, null, null);
        if (cursor.moveToFirst()) {
            int index =cursor.getColumnIndex("password");
            String str = cursor.getString(index);
            cursor.close();
            return str;
        }
        cursor.close();
        return "";
    }

    public static String getPathDatabase() {
        return sqLiteDatabase_.getPath();
    }

    public static void insert(WiFiInfo paramWiFiInfo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("SSID", paramWiFiInfo.SSID);
        contentValues.put("BSSID", paramWiFiInfo.BSSID);
        contentValues.put("channel", Integer.valueOf(paramWiFiInfo.Channel));
        contentValues.put("level", Integer.valueOf(paramWiFiInfo.Level));
        contentValues.put("security", paramWiFiInfo.Security);
        contentValues.put("operator", paramWiFiInfo.OperatorFrendlyName);
        contentValues.put("venue", paramWiFiInfo.VenueName);
        contentValues.put("password", paramWiFiInfo.Password);
        sqLiteDatabase_.insert("WiFiInfo", null, contentValues);
    }

    public static List<WiFiInfo> toList() {
        Cursor cursor = sqLiteDatabase_.query("WiFiInfo", null, null, null, null, null, null);
        ArrayList<WiFiInfo> arrayList = new ArrayList();
        if (cursor.moveToFirst()) {
            int i = cursor.getColumnIndex("SSID");
            int j = cursor.getColumnIndex("BSSID");
            int k = cursor.getColumnIndex("channel");
            int m = cursor.getColumnIndex("level");
            int n = cursor.getColumnIndex("security");
            int i1 = cursor.getColumnIndex("operator");
            int i2 = cursor.getColumnIndex("venue");
            int i3 = cursor.getColumnIndex("password");
            do {
                arrayList.add((new WiFiInfo.Builder()).
                        SSID(cursor.getString(i)).
                        BSSID(cursor.getString(j)).
                        Channel(cursor.getInt(k)).
                        Level(cursor.getInt(m)).
                        Security(cursor.getString(n)).
                        Operator(cursor.getString(i1)).
                        VenueName(cursor.getString(i2)).
                        Password(cursor.getString(i3)).
                        build());
            } while (cursor.moveToNext());
        }
        cursor.close();
        return arrayList;
    }

    public static void update(WiFiInfo paramWiFiInfo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("BSSID", paramWiFiInfo.BSSID);
        contentValues.put("channel", Integer.valueOf(paramWiFiInfo.Channel));
        contentValues.put("level", Integer.valueOf(paramWiFiInfo.Level));
        contentValues.put("security", paramWiFiInfo.Security);
        contentValues.put("operator", paramWiFiInfo.OperatorFrendlyName);
        contentValues.put("venue", paramWiFiInfo.VenueName);
        contentValues.put("password", paramWiFiInfo.Password);
        sqLiteDatabase_.update("WiFiInfo",
                contentValues,
                String.format("%s = ?","SSID"),
                new String[] { paramWiFiInfo.SSID });
    }
}
