package com.obigo.appcenter.adpater;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.obigo.appcenter.R;
import com.obigo.appcenter.data.AppListData;
import com.obigo.appcenter.view.AppViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by obigo on 11/1/16.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<AppViewHolder> implements AppViewHolder.OnViewItemClickLIstener{

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
    public AppViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_app_list, parent, false);
        AppViewHolder viewHolder = new AppViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AppViewHolder holder, int position) {
        AppViewHolder avh = (AppViewHolder)holder;
        avh.setAppList(appListDatas.get(position));
        avh.setOnViewClickListener(this);
    }

    @Override
    public int getItemCount() {
        return appListDatas.size();
    }

    @Override
    public void onItemClick(View view, int position, AppListData appListData) {
        if(listener != null) listener.onItemClick(view,position,appListData);
    }

    public interface OnAdapterItemClickLIstener {
        public void onItemClick(View view, int position, AppListData appListData);
    }

    OnAdapterItemClickLIstener listener;
    public void setOnAdapterItemClickListener(OnAdapterItemClickLIstener listener) {
        this.listener = listener;
    }
}
