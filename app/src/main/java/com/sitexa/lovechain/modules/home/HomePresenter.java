package com.sitexa.lovechain.modules.home;

import android.content.Context;

import com.sitexa.lovechain.base.BasePresenter;

public class HomePresenter extends BasePresenter<HomeView> {
    private Context mContext;

    public HomePresenter(Context context){
        this.mContext = context;
    }

}
