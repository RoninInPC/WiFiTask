package com.example.wifitasksecondtime;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.wifi.ScanResult;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.widget.ScrollView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.customview.widget.Openable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.wifitasksecondtime.databinding.ActivityMainBinding;
import com.example.wifitasksecondtime.ui.home.DecoratorMainFragment;
import com.google.android.material.navigation.NavigationView;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static Context context_;

    public static DatabaseWifi databaseWifi;

    public static List<ScanResult> list;

    private ActivityMainBinding binding_;

    private AppBarConfiguration mAppBarConfiguration_;

    public static Context getContext_() {
        return context_;
    }

    public void checkPermission(String paramString, int paramInt) {
        if (ContextCompat.checkSelfPermission((Context)this, paramString) == -1)
            ActivityCompat.requestPermissions((Activity)this, new String[] { paramString }, paramInt);
    }

    @Override
    protected void onCreate(Bundle paramBundle) {
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        if (displayMetrics.widthPixels < displayMetrics.heightPixels) {
            setRequestedOrientation(1);
        } else {
            setRequestedOrientation(0);
        }
        checkPermission("android.permission.ACCESS_COARSE_LOCATION", 100);
        checkPermission("android.permission.ACCESS_FINE_LOCATION", 101);
        checkPermission("android.permission.CHANGE_WIFI_STATE", 102);
        checkPermission("android.permission.ACCESS_WIFI_STATE", 103);
        checkPermission("android.permission.BIND_ACCESSIBILITY_SERVICE", 104);
        checkPermission("android.permission.WRITE_EXTERNAL_STORAGE", 105);
        if (Build.VERSION.SDK_INT >= 30)
            checkPermission("android.permission.MANAGE_EXTERNAL_STORAGE", 106);
        super.onCreate(paramBundle);
        ActivityMainBinding activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        this.binding_ = activityMainBinding;
        setContentView((View)activityMainBinding.getRoot());
        setSupportActionBar(this.binding_.appBarMain.toolbar);

        new DecoratorMainFragment(
                findViewById(R.id.toolbar),
                findViewById(R.id.scrollView2),
                       this);
        DrawerLayout drawerLayout = this.binding_.drawerLayout;
        NavigationView navigationView = this.binding_.navView;
        this.mAppBarConfiguration_ = (new AppBarConfiguration.Builder(new int[] { R.id.nav_home, R.id.nav_gallery })).setOpenableLayout((Openable)drawerLayout).build();
        NavController navController = Navigation.findNavController((Activity)this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, this.mAppBarConfiguration_);
        NavigationUI.setupWithNavController(navigationView, navController);
        context_ = (Context)this;
        databaseWifi = new DatabaseWifi((Context)this);
        startService(new Intent(getContext_(), ScannerWiFiService.class));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu paramMenu) {
        return false;
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(getContext_(), ScannerWiFiService.class));
        super.onDestroy();
    }
    @Override
    public boolean onSupportNavigateUp() {
        return (NavigationUI.navigateUp(Navigation.findNavController((Activity)this, R.id.nav_host_fragment_content_main), this.mAppBarConfiguration_) || super.onSupportNavigateUp());
    }
}