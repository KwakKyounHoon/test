package com.obigo.appcenter.fragment;


import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.obigo.appcenter.MainActivity;
import com.obigo.appcenter.R;
import com.obigo.appcenter.service.DownloadService;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 */
public class DownloadFragment extends Fragment {


    public DownloadFragment() {
        // Required empty public constructor
    }

    ProgressBar downloadView;
    TextView percentView;
    TextView text_message;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_download, container, false);
        downloadView = (ProgressBar)view.findViewById(R.id.progressBar);
        percentView = (TextView)view.findViewById(R.id.text_percent);
        text_message = (TextView)view.findViewById(R.id.text_message);
//        mHandler.post(progressRunnable);
        Intent intent = new Intent(getContext() , DownloadService.class);
        // add infos for the service which file to download and where to store
        intent.putExtra(DownloadService.FILENAME, "index.jpg");
        intent.putExtra(DownloadService.URL,
                "http://192.168.0.61:3000/download");
        getContext().startService(intent);
        return view;
    }

    Handler mHandler = new Handler(Looper.getMainLooper());

    int progress = 0;
    Runnable progressRunnable = new Runnable() {
        @Override
        public void run() {
            if (progress <= 100) {
                downloadView.setProgress(progress);
                percentView.setText(progress+"%");
                progress += 5;
                mHandler.postDelayed(this, 500);
            } else {
                downloadView.setProgress(100);
                percentView.setText("progress done");
            }
        }
    };


    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                String string = bundle.getString(DownloadService.FILEPATH);
                int resultCode = bundle.getInt(DownloadService.RESULT);
                int progress = bundle.getInt(DownloadService.PROGRESS);
                if (resultCode == Activity.RESULT_OK) {
                    downloadView.setProgress(100);
                    Toast.makeText(getContext(),
                            "Download complete. Download URI: " + string,
                            Toast.LENGTH_LONG).show();
                    ((MainActivity) getActivity()).setSelectNewPosiition(-1);
                    ((MainActivity) getActivity()).setSelectUpdatePosition(-1);
                    text_message.setText("Download done");
//                    my_image.setImageDrawable(Drawable.createFromPath(string));
                    Uri uri = Uri.fromFile(new File(string));
                    Intent viewIntent = new Intent(Intent.ACTION_VIEW);
                    viewIntent.setDataAndType(uri, "image/*");
//                    Intent viewIntent = new Intent(getContext(), Main2Activity.class);
                    PendingIntent pi = PendingIntent.getActivity(getContext(), 0, viewIntent, PendingIntent.FLAG_ONE_SHOT);
                    try {
                        pi.send();
                    } catch (PendingIntent.CanceledException e) {
                        e.printStackTrace();
                    }
//                    viewIntent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
//
//                    getContext().startActivity(viewIntent);

                } else if(resultCode == 2) {
                    text_message.setText("Downloading...");
                    downloadView.setProgress(progress);
                    percentView.setText(progress+"%");

                } else {
                    Toast.makeText(getContext(), "Download failed",
                            Toast.LENGTH_LONG).show();
                    text_message.setText("Download failed");
                }
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        getContext().registerReceiver(receiver, new IntentFilter(
                DownloadService.NOTIFICATION));
    }
    @Override
    public void onPause() {
        super.onPause();
        getContext().unregisterReceiver(receiver);
    }

}
