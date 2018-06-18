package com.sitexa.lovechain.modules.friendslist.myfriendslist;

import android.content.Context;
import android.text.TextUtils;

import com.lzy.okgo.model.Response;
import com.sitexa.lovechain.app.MyApplication;
import com.sitexa.lovechain.base.BasePresenter;
import com.sitexa.lovechain.base.BaseUrl;
import com.sitexa.lovechain.net.HttpUtils;
import com.sitexa.lovechain.net.callbck.JsonCallback;
import com.sitexa.lovechain.R;
import com.sitexa.lovechain.bean.FriendsListInfoBean;
import com.sitexa.lovechain.view.contact.ChineseToEnglish;
import com.sitexa.lovechain.view.contact.CompareSort;
import com.sitexa.lovechain.bean.User;
import com.sitexa.lovechain.bean.ResponseBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created By sitexa on 2018/6/17
 */

public class FriendsListPresenter extends BasePresenter<FriendsListView> {
    private Context mContext;

    public FriendsListPresenter(Context context) {
        this.mContext = context;
    }

    public void getData() {
        final ArrayList<User> mDataBeanArrayList = new ArrayList<>();
        final String[] headArray = new String[2];
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("uid", MyApplication.getInstance().getUserBean().getWallet_uid());
        HttpUtils.postRequest(BaseUrl.HTTP_Getfollow_list, mContext, hashMap, new JsonCallback<ResponseBean<List<FriendsListInfoBean>>>() {
            @Override
            public void onSuccess(Response<ResponseBean<List<FriendsListInfoBean>>> response) {
                if (response.body().code == 0) {
                    for (int i = 0; i < response.body().data.size(); i++) {
                        User userinfo = new User();
                        userinfo.setName(response.body().data.get(i).getDisplayName());
                        userinfo.setAvatar(response.body().data.get(i).getAvatar());
                        userinfo.setUid(response.body().data.get(i).getUid());
                        userinfo.setFriend_type(response.body().data.get(i).getFollowType());
                        if (!TextUtils.isEmpty(response.body().data.get(i).getDisplayName())) {
                            String firstSpell = ChineseToEnglish.getFirstSpell(response.body().data.get(i).getDisplayName());
                            String substring = firstSpell.substring(0, 1).toUpperCase();
                            if (substring.matches("[A-Z]")) {
                                userinfo.setLetter(substring);
                            } else {
                                userinfo.setLetter("#");
                            }
                            mDataBeanArrayList.add(userinfo);
                        }
                    }
                    headArray[0] = mContext.getResources().getString(R.string.company_list);
                    headArray[1] = mContext.getResources().getString(R.string.pe_list);
                    //头部两个服务列表
                    for (int i = 0; i < headArray.length; i++) {
                        User userinfo = new User();
                        userinfo.setName(headArray[i]);
                        userinfo.setLetter("@");
                        mDataBeanArrayList.add(userinfo);
                    }
                    //排序
                    Collections.sort(mDataBeanArrayList, new CompareSort());
                    view.getDataHttp(mDataBeanArrayList);
                } else {
                    view.getDataHttpFail(response.body().message);
                }
            }
        });
    }
}
