package com.sitexa.lovechain.modules.friendslist.myfriendslist;

import com.sitexa.lovechain.base.BaseView;
import com.sitexa.lovechain.base.BaseView;
import com.sitexa.lovechain.bean.User;

import java.util.ArrayList;

/**
 * Created By sitexa on 2018/6/17
 * 获取friendslist
 */

public interface FriendsListView extends BaseView {

    void getDataHttp(ArrayList<User> mDataBeanArrayList);


    void getDataHttpFail(String msg);
}
