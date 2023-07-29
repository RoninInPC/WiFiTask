package com.example.wifitasksecondtime.ui.gallery;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.wifitasksecondtime.DatabaseWifi;
import com.example.wifitasksecondtime.R;
import com.example.wifitasksecondtime.WiFiInfo;
import com.example.wifitasksecondtime.databinding.FragmentGalleryBinding;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class BaseFragment extends Fragment {
    private FragmentGalleryBinding binding;

    private Context context_;

    private LinearLayout linearLayout_;
    @Override
    public void onAttach(Context paramContext) {
        super.onAttach(paramContext);
        this.context_ = paramContext;
    }
    @Override
    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
        binding = FragmentGalleryBinding.inflate(paramLayoutInflater, paramViewGroup, false);
        new DecoratorBaseFragment(
                getActivity().findViewById(R.id.toolbar),
                binding.scrollView33,
                binding.loadBase,
                context_);
        linearLayout_ = binding.containerBase;
        binding.loadBase.setOnClickListener(new View.OnClickListener() {
            public void onClick(View param1View) {
                (new BaseFragment.MoveFileTask(context_, DatabaseWifi.getPathDatabase())).execute();
            }
        });
        ConstraintLayout constraintLayout = binding.getRoot();
        setListOnView(DatabaseWifi.toList());
        return (View)constraintLayout;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void setListOnView(List<WiFiInfo> paramList) {
        if (paramList == null)
            return;
        this.linearLayout_.removeAllViews();
        Log.e("RESULT", paramList.toString());
        for (WiFiInfo wiFiInfo : paramList)
            linearLayout_.addView((View)DecoratorBaseFragment.makeTextView(context_, wiFiInfo.toString()));
    }

    public class MoveFileTask extends AsyncTask<Void, Void, Boolean> {
        private static final String TAG_ = "MoveFileTask";

        private final Context mContext_;

        private final String mFilePath_;

        public MoveFileTask(Context param1Context, String param1String) {
            this.mContext_ = param1Context;
            this.mFilePath_ = param1String;
        }

        protected Boolean doInBackground(Void... param1VarArgs) {
            try {
                File file1 = new File(this.mFilePath_);
                File file2 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "database.db");
                FileInputStream fileInputStream = new FileInputStream(file1);
                FileOutputStream fileOutputStream = new FileOutputStream(file2);
                byte[] arrayOfByte = new byte[1024];
                while (true) {
                    int i = fileInputStream.read(arrayOfByte);
                    if (i > 0) {
                        fileOutputStream.write(arrayOfByte, 0, i);
                        continue;
                    }
                    fileInputStream.close();
                    fileOutputStream.close();
                    return true;
                }
            } catch (IOException iOException) {
                Log.e("MoveFileTask", "", iOException);
                return false;
            }
        }

        protected void onPostExecute(Boolean param1Boolean) {
            (new AlertDialog.Builder(this.mContext_)).setMessage("Выгружено в Download/database.db").
                    setPositiveButton("Принято", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface param2DialogInterface, int param2Int) {
                            param2DialogInterface.cancel();
                        }
                    }).create().show();
        }
    }
}

