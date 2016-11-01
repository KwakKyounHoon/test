package com.obigo.appcenter.data;

import java.io.Serializable;

/**
 * Created by obigo on 10/28/16.
 */

public class AppListData implements Serializable{
    private String name;
    private int status;
    private String logoUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
}
