package com.example.wifitasksecondtime;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import java.io.Serializable;
import java.util.List;

public class ScannerWiFiService extends Service {
    private MyBinder binder_ = new MyBinder();

    private List<ScanResult> list_;

    public List<ScanResult> getList() {
        return this.list_;
    }

    @Override
    public IBinder onBind(Intent Intent) {
        return (IBinder)this.binder_;
    }
    @Override
    public void onCreate() {
        final WifiManager wifiManager = (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);
        if (!wifiManager.isWifiEnabled())
            wifiManager.setWifiEnabled(true);
        if (wifiManager.getWifiState() > 0) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.net.wifi.SCAN_RESULTS");
            registerReceiver(new BroadcastReceiver() {
                @SuppressLint("MissingPermission")
                public void onReceive(Context Context, Intent Intent) {
                    list_ =  wifiManager.getScanResults();
                    
                    Log.e("SERVICE", ScannerWiFiService.this.list_.toString());
                    Intent intent = new Intent("SERVICE_FIND");
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("LIST", (Serializable)ScannerWiFiService.this.list_);
                    intent.putExtra("BUNDLE", bundle);
                    ScannerWiFiService.this.sendBroadcast(intent);
                }
            },intentFilter);
            wifiManager.startScan();
        }
    }
    @Override
    public void onDestroy() {
        Log.e("SERVICE", "Служба запущена");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent Intent, int Int1, int Int2) {
        Log.e("SERVICE", "Служба распущена");
        return super.onStartCommand(Intent, Int1, Int2);
    }

    public class MyBinder extends Binder {
        public ScannerWiFiService getService() {
            return ScannerWiFiService.this;
        }
    }
}
