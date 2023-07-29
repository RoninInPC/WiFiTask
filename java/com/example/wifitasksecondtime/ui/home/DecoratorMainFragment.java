package com.example.wifitasksecondtime.ui.home;

import android.content.Context;
import android.content.res.Resources;
import android.view.ViewGroup;
import android.widget.ScrollView;
import androidx.appcompat.widget.Toolbar;

public class DecoratorMainFragment {
    private final Context context_;

    private final ScrollView scrollView_;

    private final Toolbar toolbar_;

    private float toolbar_size_ = 0.1F;

    public DecoratorMainFragment(Toolbar paramToolbar, ScrollView paramScrollView, Context paramContext) {
        int height = (Resources.getSystem().getDisplayMetrics()).heightPixels;
        
        ViewGroup.LayoutParams layoutParams2 = paramToolbar.getLayoutParams();
        layoutParams2.height = (int)(height * toolbar_size_);
        paramToolbar.setLayoutParams(layoutParams2);
        toolbar_ = paramToolbar;
        
        
        ViewGroup.LayoutParams layoutParams1 = paramScrollView.getLayoutParams();
        layoutParams1.height = (int)(height * (1.0F - toolbar_size_));
        paramScrollView.setLayoutParams(layoutParams1);
        scrollView_ = paramScrollView;
        context_ = paramContext;
    }

 
}