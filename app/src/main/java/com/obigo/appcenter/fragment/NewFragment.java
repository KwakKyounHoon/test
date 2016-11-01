package com.obigo.appcenter.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.obigo.appcenter.MainActivity;
import com.obigo.appcenter.R;
import com.obigo.appcenter.adpater.ListAdapter;
import com.obigo.appcenter.data.AppListData;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewFragment extends Fragment {
    private final String DETAILS_TAG = "details";

    public NewFragment() {
        // Required empty public constructor
    }

    ListView listView;
    ListAdapter mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity)(getActivity())).setTitle(getContext().getString(R.string.app_title_new));
        View view = inflater.inflate(R.layout.fragment_new, container, false);
        listView = (ListView)view.findViewById(R.id.list);
        mAdapter = new ListAdapter();
        listView.setAdapter(mAdapter);
        init();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((MainActivity)getActivity()).changeFragment(AppDetailsFragment.newInstance(mAdapter.getItem(position),position), DETAILS_TAG);
            }
        });
        return view;
    }

    private void init() {
        for(int i = 0; i < 7; i++){
            AppListData appListData = new AppListData();
            appListData.setName("name"+i);
            appListData.setStatus(2);
            mAdapter.addAppdata(appListData);
        }
    }


}
