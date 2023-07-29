package com.example.wifitasksecondtime;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import java.util.List;

public class ConnectToWiFi {
    @SuppressLint("MissingPermission")
    public static boolean connectOpen(WiFiInfo WiFiInfo) {
        try {
            WifiConfiguration wifiConfiguration = new WifiConfiguration();
            wifiConfiguration.SSID = "\"" + WiFiInfo.SSID + "\"";
            wifiConfiguration.status = 2;
            wifiConfiguration.priority = 40;


            wifiConfiguration.allowedKeyManagement.set(0);
            wifiConfiguration.allowedProtocols.set(1);
            wifiConfiguration.allowedProtocols.set(0);
            wifiConfiguration.allowedAuthAlgorithms.clear();
            wifiConfiguration.allowedPairwiseCiphers.set(2);
            wifiConfiguration.allowedPairwiseCiphers.set(1);
            wifiConfiguration.allowedGroupCiphers.set(0);
            wifiConfiguration.allowedGroupCiphers.set(1);
            wifiConfiguration.allowedGroupCiphers.set(3);
            wifiConfiguration.allowedGroupCiphers.set(2);

            WifiManager wifiManager = (WifiManager)MainActivity.getContext_()
                    .getApplicationContext()
                    .getSystemService(Context.WIFI_SERVICE);
            return (wifiManager.addNetwork(wifiConfiguration) == -1) ?
                    false :
                    findConnect(wifiManager.getConfiguredNetworks(), wifiConfiguration.SSID, wifiManager);
        } catch (Exception exception) {
            return false;
        }
    }

    @SuppressLint("MissingPermission")
    public static boolean connectWEP(WiFiInfo WiFiInfo) {
        try {
            WifiConfiguration wifiConfiguration = new WifiConfiguration();
            wifiConfiguration.SSID = "\"" + WiFiInfo.SSID + "\"";
            wifiConfiguration.status = 2;
            wifiConfiguration.priority = 40;
            wifiConfiguration.allowedKeyManagement.set(0);
            wifiConfiguration.allowedProtocols.set(1);
            wifiConfiguration.allowedProtocols.set(0);
            wifiConfiguration.allowedAuthAlgorithms.set(0);
            wifiConfiguration.allowedAuthAlgorithms.set(1);
            wifiConfiguration.allowedPairwiseCiphers.set(2);
            wifiConfiguration.allowedPairwiseCiphers.set(1);
            wifiConfiguration.allowedGroupCiphers.set(0);
            wifiConfiguration.allowedGroupCiphers.set(1);
            if (WiFiInfo.Password.matches("^[0-9a-fA-F]+$")) {
                wifiConfiguration.wepKeys[0] = WiFiInfo.Password;
            } else {
                wifiConfiguration.wepKeys[0] = "\"".concat(WiFiInfo.Password).concat("\"");
            }
            wifiConfiguration.wepTxKeyIndex = 0;
            WifiManager wifiManager = (WifiManager)MainActivity.getContext_()
                    .getApplicationContext()
                    .getSystemService(Context.WIFI_SERVICE);
            return (wifiManager.addNetwork(wifiConfiguration) == -1) ?
                    false :
                    findConnect(wifiManager.getConfiguredNetworks(), wifiConfiguration.SSID, wifiManager);
        } catch (Exception exception) {
            return false;
        }
    }

    @SuppressLint("MissingPermission")
    public static boolean connectWPA(WiFiInfo WiFiInfo) {
        try {
            WifiConfiguration wifiConfiguration = new WifiConfiguration();
            wifiConfiguration.SSID = "\"" + WiFiInfo.SSID + "\"";
            wifiConfiguration.status = 2;
            wifiConfiguration.priority = 40;
            wifiConfiguration.allowedProtocols.set(1);
            wifiConfiguration.allowedProtocols.set(0);
            wifiConfiguration.allowedKeyManagement.set(1);
            wifiConfiguration.allowedPairwiseCiphers.set(2);
            wifiConfiguration.allowedPairwiseCiphers.set(1);
            wifiConfiguration.allowedGroupCiphers.set(0);
            wifiConfiguration.allowedGroupCiphers.set(1);
            wifiConfiguration.allowedGroupCiphers.set(3);
            wifiConfiguration.allowedGroupCiphers.set(2);
            wifiConfiguration.preSharedKey = "\"" + WiFiInfo.Password + "\"";
            WifiManager wifiManager = (WifiManager)MainActivity.getContext_()
                    .getApplicationContext()
                    .getSystemService(Context.WIFI_SERVICE);
            return (wifiManager.addNetwork(wifiConfiguration) == -1) ?
                    false :
                    findConnect(wifiManager.getConfiguredNetworks(), wifiConfiguration.SSID, wifiManager);
        } catch (Exception exception) {
            return false;
        }
    }

    public static boolean connectWiFi(WiFiInfo WiFiInfo) {
        switch (WiFiInfo.Security) {
            case "WPA":
            case "WPA2":
                return connectWPA(WiFiInfo);
            case "WEP":
                return connectWEP(WiFiInfo);
            default:
                return connectOpen(WiFiInfo);
        }
    }

    public static boolean findConnect(List<WifiConfiguration> List, String String, WifiManager WifiManager) {
        for (WifiConfiguration wifiConfiguration : List) {
            if (wifiConfiguration.SSID != null && wifiConfiguration.SSID.equals(String)) {
                String str= wifiConfiguration.toString();
                if (str.contains("NETWORK_SELECTION_DISABLED_BY_WRONG_PASSWORD"))
                    return false;
                WifiManager.disconnect();
                WifiManager.enableNetwork(wifiConfiguration.networkId, true);
                return WifiManager.reconnect();
            }
        }
        return false;
    }

    public static boolean tryConnectWiFi(WiFiInfo WiFiInfo, String String) {
        WiFiInfo.Password = String;
        boolean bool = connectWiFi(WiFiInfo);
        if (!bool)
            WiFiInfo.Password = "";
        return bool;
    }
}
