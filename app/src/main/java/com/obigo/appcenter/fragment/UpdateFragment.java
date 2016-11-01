package com.obigo.appcenter.fragment;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.Toast;

import com.obigo.appcenter.MainActivity;
import com.obigo.appcenter.R;
import com.obigo.appcenter.adpater.RecyclerAdapter;
import com.obigo.appcenter.data.AppListData;
import com.obigo.appcenter.service.DownloadService;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateFragment extends Fragment {
    private final String DETAILS_TAG = "details";
    private int downloadPosition;

    public UpdateFragment() {
        // Required empty public constructor
    }
//    ListView listView;
//    ListAdapter mAdapter;
    RecyclerView listView;
    RecyclerAdapter mAdapter;
    GridLayout gridView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new RecyclerAdapter();
        init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity)(getActivity())).setTitle(getContext().getString(R.string.app_title_update));
        View view = inflater.inflate(R.layout.fragment_update, container, false);
        listView = (RecyclerView)view.findViewById(R.id.list);
        listView.setAdapter(mAdapter);
        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(), 2);
        listView.setLayoutManager(manager);

        mAdapter.setOnAdapterItemClickListener(new RecyclerAdapter.OnAdapterItemClickLIstener() {
            @Override
            public void onItemClick(View view, int position, AppListData appListData) {
                ((MainActivity)getActivity()).changeFragment(AppDetailsFragment.newInstance(appListData, position), DETAILS_TAG);
                downloadPosition = position;
            }
        });
//        listView = (ListView)view.findViewById(R.id.list);
//        listView.setAdapter(mAdapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                ((MainActivity)getActivity()).changeFragment(AppDetailsFragment.newInstance(mAdapter.getItem(position), position), DETAILS_TAG);
//                downloadPosition = position;
//            }
//        });
        return view;
    }

    private void init() {
        for(int i = 0; i < 10; i++){
            AppListData appListData = new AppListData();
            appListData.setName("name"+i);
            appListData.setStatus(1);
            mAdapter.addAppdata(appListData);
        }
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            int resultCode = bundle.getInt(DownloadService.RESULT);
            if (bundle != null) {
                int selectPosition = ((MainActivity)getActivity()).getSelectUpdatePosition();
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(getContext(),
                            "Download complete. Download URI: ",
                            Toast.LENGTH_LONG).show();
                    ((MainActivity) getActivity()).setSelectNewPosiition(-1);
                    ((MainActivity) getActivity()).setSelectUpdatePosition(-1);
                    mAdapter.setComplete(downloadPosition);
                } else if(resultCode == 2) {
                    mAdapter.setDownloading(selectPosition);
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
