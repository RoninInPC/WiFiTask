package com.example.wifitasksecondtime;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import java.util.ArrayList;
import java.util.Arrays;

public class WiFiInfo {
    private static final ArrayList<Integer> channelsFrequency = new ArrayList<Integer>(Arrays.asList(new Integer[] {
            Integer.valueOf(0), Integer.valueOf(2412), Integer.valueOf(2417), Integer.valueOf(2422), Integer.valueOf(2427), Integer.valueOf(2432), Integer.valueOf(2437), Integer.valueOf(2442), Integer.valueOf(2447), Integer.valueOf(2452),
            Integer.valueOf(2457), Integer.valueOf(2462), Integer.valueOf(2467), Integer.valueOf(2472), Integer.valueOf(2484) }));

    public String BSSID;

    public int Channel;

    public int Level;

    public String OperatorFrendlyName;

    public String Password = "";

    public String SSID;

    public String Security;

    public String VenueName;

    public WiFiInfo() {}

    public WiFiInfo(ScanResult ScanResult) {
        SSID = ScanResult.SSID;
        BSSID = ScanResult.BSSID;
        Channel = getChannelFromFrequency(ScanResult.frequency);
        Level = WifiManager.calculateSignalLevel(ScanResult.level, 5);
        Security = getSecurity(ScanResult.capabilities);
        OperatorFrendlyName = (String)ScanResult.operatorFriendlyName;
        VenueName = (String)ScanResult.venueName;
    }

    public static int getChannelFromFrequency(int Int) {
        return channelsFrequency.indexOf(Integer.valueOf(Int));
    }

    public static String getSecurity(String String) {
        String[] arrayOfString = new String[4];
        arrayOfString[0] = "WEP";
        arrayOfString[1] = "WPA";
        arrayOfString[2] = "WPA2";
        arrayOfString[3] = "IEEE8021X";
        for (int i = arrayOfString.length - 1; i >= 0; i--) {
            if (String.contains(arrayOfString[i]))
                return arrayOfString[i];
        }
        return "OPEN";
    }

    public String toString() {
        String string = "";
        String nope = "Не известно";
        if (SSID == null || SSID.equals("")) {
            string += "Имя сети: " + nope+ "\n";
        } else {
            string += "Имя сети: " + SSID + "\n";
        }
        if (BSSID == null || BSSID.equals("")) {
            string += "Адрес: " + nope+ "\n";
        } else {
            string += "Адрес: " + BSSID + "\n";
        }
        if (Channel >= 0) {
            string += "Канал: " + String.valueOf(channelsFrequency.get(Channel))+ "\n";

        } else {
            string += "Канал: " + nope+ "\n";
        }
        string +="Уровень: " + String.valueOf(Level) + "\n";
        string += "Тип безопасности: " + Security + "\n";
        if (OperatorFrendlyName == null || OperatorFrendlyName.equals("")) {
            string += "Имя оператора точки доступа: " + nope + "\n";
        } else {
            string += "Имя оператора точки доступа:"  + OperatorFrendlyName + "\n";
        }
        if (VenueName == null || VenueName.equals("")) {
            string += "Место: " + nope + "\n";
        } else {
            string += "Место: " + VenueName + "\n";
        }
        if (Password == null || Password.equals("")) {
            string += "Пароль: " + nope + "\n";
        } else {
            string += "Пароль: " + Password + "\n";
        }
        return string;
    }

    static class Builder {
        private WiFiInfo result_ = new WiFiInfo();

        public Builder BSSID(String String) {
            result_.BSSID = String;
            return this;
        }

        public Builder Channel(int Int) {
            result_.Channel = Int;
            return this;
        }

        public Builder Level(int Int) {
            result_.Level = Int;
            return this;
        }

        public Builder Operator(String String) {
            result_.OperatorFrendlyName = String;
            return this;
        }

        public Builder Password(String String) {
            result_.Password = String;
            return this;
        }

        public Builder SSID(String String) {
            result_.SSID = String;
            return this;
        }

        public Builder Security(String String) {
            result_.Security = String;
            return this;
        }

        public Builder VenueName(String String) {
            result_.VenueName = String;
            return this;
        }

        public WiFiInfo build() {
            return result_;
        }
    }
}
