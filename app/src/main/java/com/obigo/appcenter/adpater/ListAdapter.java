package com.obigo.appcenter.adpater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.obigo.appcenter.data.AppListData;
import com.obigo.appcenter.view.AppListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by obigo on 10/28/16.
 */

public class ListAdapter extends BaseAdapter {
    List<AppListData> appListDatas = new ArrayList<>();

    public void addAppdata(List<AppListData> appListDatas){
        this.appListDatas.addAll(appListDatas);
        notifyDataSetChanged();
    }

    int downloadPostion = -1;
    String message = "";
    public void setDownloadText(int downloadPostion, String message){
        this.downloadPostion = downloadPostion;
        this.message = message;
        notifyDataSetChanged();
    }

    public void setDownloading(int downloadPostion){
        AppListData appListData = appListDatas.get(downloadPostion);
        appListData.setStatus(3);
        appListDatas.set(downloadPostion, appListData);
        notifyDataSetChanged();
    }

    public void setComplete(int downloadPostion){
        appListDatas.get(downloadPostion).setStatus(4);
        notifyDataSetChanged();
    }


    public void addAppdata(AppListData appListData){
        this.appListDatas.add(appListData);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return appListDatas.size();
    }

    @Override
    public AppListData getItem(int position) {
        return appListDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AppListView view;
        if (convertView != null && convertView instanceof AppListView) {
            view = (AppListView)convertView;
        } else {
            view = new AppListView(parent.getContext());
        }
        view.setAppList(appListDatas.get(position));
        if(downloadPostion == position){
            view.setDownloading(message);
        }
        return view;
    }
}
