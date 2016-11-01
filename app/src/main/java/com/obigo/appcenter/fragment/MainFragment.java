package com.obigo.appcenter.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.obigo.appcenter.MainActivity;
import com.obigo.appcenter.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private final String NEW_TAG = "new";
    private final String UPDATE_TAG = "update";

    public MainFragment() {
        // Required empty public constructor
    }

    RelativeLayout newView, updateView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ((MainActivity)getActivity()).setTitle(getString(R.string.app_name));
        newView = (RelativeLayout)view.findViewById(R.id.layout_new);
        updateView = (RelativeLayout)view.findViewById(R.id.layout_update);
        newView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)(getActivity())).changeFragment(new NewFragment(), NEW_TAG);
            }
        });

        updateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)(getActivity())).changeFragment(new UpdateFragment(), UPDATE_TAG);
            }
        });

        return view;
    }

}
