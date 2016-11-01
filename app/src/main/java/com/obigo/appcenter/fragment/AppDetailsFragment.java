package com.obigo.appcenter.fragment;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.obigo.appcenter.MainActivity;
import com.obigo.appcenter.R;
import com.obigo.appcenter.data.AppListData;
import com.obigo.appcenter.service.DownloadService;

/**
 * A simple {@link Fragment} subclass.
 */
public class AppDetailsFragment extends Fragment {


    public AppDetailsFragment() {
        // Required empty public constructor
    }

    private AppListData appListData;
    private int selectPosition;
    public static final String APPLISTDATA= "applistdata";
    public static final String POSITION = "position";

    public static AppDetailsFragment newInstance(AppListData appListData, int position) {
        AppDetailsFragment fragment = new AppDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(APPLISTDATA,appListData);
        args.putInt(POSITION,position);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.appListData = (AppListData) getArguments().getSerializable(APPLISTDATA);
            this.selectPosition = getArguments().getInt(POSITION);
        }
    }


    private final String DOWNLOAD_TAG = "download";
    Button updateView;
    AlertDialog dialog;
    int status;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity)(getActivity())).setTitle(getContext().getString(R.string.app_title_details));
        View view = inflater.inflate(R.layout.fragment_app_details, container, false);
        updateView = (Button)view.findViewById(R.id.btn_update);
        status = appListData.getStatus();
        Log.i("test",status+"");
        if(status == 1){
            updateView.setText("Update");
            updateView.setEnabled(true);
        }else if(status == 2){
            updateView.setText("Install");
            updateView.setEnabled(true);
        }else if(status == 3){
            updateView.setEnabled(false);
        }else if(status == 4){
            updateView.setText("Complete");
            updateView.setEnabled(false);
        }
        updateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(status == 1){
                    makeDialog("업데이트 하시겠습니까?");
                }else{
                    makeDialog("설치 하시겠습니까?");
                }
            }
        });
//        listView = (ListView)view.findViewById(R.id.list);
//        mAdapter = new ListAdapter();
//        listView.setAdapter(mAdapter);
//        init();
        return view;
    }

    private void makeDialog(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(message);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getContext(), "Yes click", Toast.LENGTH_SHORT).show();
                ((MainActivity)getActivity()).changeFragment(new DownloadFragment(), DOWNLOAD_TAG);
                if(status == 1) {
                    ((MainActivity) getActivity()).setSelectUpdatePosition(selectPosition);
                }else if(status == 2){
                    ((MainActivity) getActivity()).setSelectNewPosiition(selectPosition);
                }
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getContext(), "No click", Toast.LENGTH_SHORT).show();
            }
        });

        dialog = builder.create();
        dialog.show();
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                String string = bundle.getString(DownloadService.FILEPATH);
                int resultCode = bundle.getInt(DownloadService.RESULT);
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(getContext(),
                            "Download complete. Download URI: " + string,
                            Toast.LENGTH_LONG).show();
                    updateView.setText("Update");
                    updateView.setEnabled(false);
                    ((MainActivity) getActivity()).setSelectNewPosiition(-1);
                    ((MainActivity) getActivity()).setSelectUpdatePosition(-1);
                } else if(resultCode == 2) {
                    updateView.setEnabled(false);
                } else {
                    Toast.makeText(getContext(), "Download failed",
                            Toast.LENGTH_LONG).show();
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
