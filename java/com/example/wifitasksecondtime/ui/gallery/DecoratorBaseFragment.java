package com.example.wifitasksecondtime.ui.gallery;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import com.example.wifitasksecondtime.R;

public class DecoratorBaseFragment {
    private static final float button_size_ = 0.1F;

    private static final float textView_size_ = 0.5F;

    private static final float toolbar_size_ = 0.1F;

    private final AppCompatButton appCompatButton_;

    private final Context context_;

    private final ScrollView scrollView_;

    private final Toolbar toolbar_;

    public DecoratorBaseFragment(Toolbar paramToolbar, ScrollView paramScrollView, AppCompatButton paramAppCompatButton, Context paramContext) {
        int i = (Resources.getSystem().getDisplayMetrics()).heightPixels;
        this.context_ = paramContext;
        ViewGroup.LayoutParams layoutParams2 = paramToolbar.getLayoutParams();
        layoutParams2.height = (int)(i * 0.1F);
        paramToolbar.setLayoutParams(layoutParams2);
        this.toolbar_ = paramToolbar;
        ViewGroup.LayoutParams layoutParams1 = paramScrollView.getLayoutParams();
        layoutParams1.height = (int)(i * 0.79999995F);
        paramScrollView.setLayoutParams(layoutParams1);
        this.scrollView_ = paramScrollView;
        layoutParams1 = paramAppCompatButton.getLayoutParams();
        layoutParams1.height = (int)(i * 0.1F);
        paramAppCompatButton.setLayoutParams(layoutParams1);
        this.appCompatButton_ = paramAppCompatButton;
    }

    public static TextView makeTextView(Context paramContext, String paramString) {
        TextView textView = new TextView(paramContext);
        int i = (Resources.getSystem().getDisplayMetrics()).heightPixels;
        textView.setLayoutParams((ViewGroup.LayoutParams)new TableLayout.LayoutParams(-2, -2));
        textView.setBackgroundResource(R.drawable.text_view_restangle);
        i = (int)(i * 0.5F / 50.0F);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(i);
        textView.setText(paramString);
        textView.setTextColor(Color.BLACK);
        return textView;
    }
}

