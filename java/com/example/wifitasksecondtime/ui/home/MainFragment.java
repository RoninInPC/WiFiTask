package com.example.wifitasksecondtime.ui.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.wifitasksecondtime.DatabaseWifi;
import com.example.wifitasksecondtime.MainActivity;
import com.example.wifitasksecondtime.SimpleElementWiFi;
import com.example.wifitasksecondtime.WiFiInfo;
import com.example.wifitasksecondtime.databinding.FragmentHomeBinding;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

public class MainFragment extends Fragment {
    private static boolean update_ = true;

    private FragmentHomeBinding binding_;

    private BroadcastReceiver broadcastReceiver_;

    private Context context_;

    private LinearLayout linearLayout_;

    private List<ScanResult> list_;

    public static void setUpdate(boolean paramBoolean) {
        update_ = paramBoolean;
    }
    @Override
    public void onAttach(Context paramContext) {
        super.onAttach(paramContext);
        this.context_ = paramContext;
    }
    @Override
    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
        FragmentHomeBinding fragmentHomeBinding = FragmentHomeBinding.inflate(paramLayoutInflater, paramViewGroup, false);
        this.binding_ = fragmentHomeBinding;
        this.linearLayout_ = fragmentHomeBinding.container;
        update_ = true;
        setListOnView(MainActivity.list);
        this.broadcastReceiver_ = new BroadcastReceiver() {
            public void onReceive(Context param1Context, Intent param1Intent) {
                Log.e("RECIVE_SERVICE", "START");
                if (!MainFragment.update_)
                    return;
                MainActivity.list = (List)param1Intent.getBundleExtra("BUNDLE").getSerializable("LIST");
                Log.e("RECIVE_SERVICE", MainActivity.list.toString());
                MainFragment.this.setListOnView(MainActivity.list);
            }
        };
        IntentFilter intentFilter = new IntentFilter("SERVICE_FIND");
        ConstraintLayout constraintLayout = this.binding_.getRoot();
        this.context_.registerReceiver(this.broadcastReceiver_, intentFilter);
        return (View)constraintLayout;
    }
    @Override
    public void onDestroy() {
        this.context_.unregisterReceiver(this.broadcastReceiver_);
        super.onDestroy();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onSaveInstanceState(Bundle paramBundle) {
        super.onSaveInstanceState(paramBundle);
        paramBundle.putSerializable("list", (Serializable)this.list_);
    }

    public void setListOnView(List<ScanResult> paramList) {
        if (paramList == null)
            return;
        this.linearLayout_.removeAllViews();
        Log.e("RESULT", paramList.toString());
        Iterator<ScanResult> iterator = paramList.iterator();
        while (iterator.hasNext()) {
            WiFiInfo wiFiInfo = new WiFiInfo(iterator.next());
            wiFiInfo.Password = DatabaseWifi.getPassword(wiFiInfo.SSID);
            DatabaseWifi.cleverInsert(wiFiInfo);
            SimpleElementWiFi simpleElementWifi = new SimpleElementWiFi(this.context_);
            simpleElementWifi.setWiFiInfo(wiFiInfo);
            this.linearLayout_.addView((View)simpleElementWifi);
        }
    }
}
