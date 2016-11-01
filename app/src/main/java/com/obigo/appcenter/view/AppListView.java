package com.obigo.appcenter.view;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.obigo.appcenter.R;
import com.obigo.appcenter.data.AppListData;

/**
 * Created by obigo on 10/28/16.
 */

public class AppListView extends RelativeLayout {
    TextView nameView, statusView;
    ImageView logoView;

    public AppListView(Context context) {
        super(context);
        inflate(getContext(), R.layout.view_app_list, this);
        nameView = (TextView)findViewById(R.id.text_name);
//        statusView = (TextView)findViewById(R.id.text_status);
        logoView = (ImageView)findViewById(R.id.image_logo);
    }

    AppListData appListData;
    public void setAppList(AppListData appListData){
        this.appListData = appListData;
        nameView.setText(appListData.getName());
//        if(appListData.getStatus() == 1){
//            statusView.setText("Update");
//        }else if(appListData.getStatus() == 2){
//            statusView.setText("Not Installed");
//        }else if(appListData.getStatus() == 3){
//            statusView.setText("donwloading...");
//        }else{
//            statusView.setText("");
//        }
    }

    public void setDownloading(String message) {
        statusView.setText(message);
    }
}
