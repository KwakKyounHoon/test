package com.obigo.appcenter.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.obigo.appcenter.R;
import com.obigo.appcenter.data.AppListData;

/**
 * Created by obigo on 11/1/16.
 */

public class AppViewHolder extends RecyclerView.ViewHolder {
    TextView nameView;
    ImageView logoView;
    public AppViewHolder(View itemView) {
        super(itemView);
        nameView = (TextView)itemView.findViewById(R.id.text_name);
        logoView = (ImageView)itemView.findViewById(R.id.image_logo);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null) listener.onItemClick(v, getAdapterPosition(), appListData);
            }
        });
    }

    AppListData appListData;
    public void setAppList(AppListData appListData){
        this.appListData = appListData;
        nameView.setText(appListData.getName());
    }

    public interface OnViewItemClickLIstener {
        public void onItemClick(View view, int position, AppListData appListData);
    }

    OnViewItemClickLIstener listener;
    public void setOnViewClickListener(OnViewItemClickLIstener listener) {
        this.listener = listener;
    }

}
