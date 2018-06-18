package com.sitexa.lovechain.modules.transaction.transferaccounts.switchfriend;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.sitexa.lovechain.app.MyApplication;
import com.sitexa.lovechain.base.BasePresenter;
import com.sitexa.lovechain.base.BaseUrl;
import com.sitexa.lovechain.net.HttpUtils;
import com.sitexa.lovechain.net.callbck.JsonCallback;
import com.sitexa.lovechain.bean.FriendsListInfoBean;
import com.sitexa.lovechain.bean.ResponseBean;

import java.util.HashMap;
import java.util.List;

/**
 * Created By sitexa on 2018/6/17
 */

public class SwitchFriendPresenter extends BasePresenter<SwitchFriendView> {
    private Context mContext;

    public SwitchFriendPresenter(Context context) {
        this.mContext = context;
    }
    public void getData() {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("uid", MyApplication.getInstance().getUserBean().getWallet_uid());
        HttpUtils.postRequest(BaseUrl.HTTP_Getfollow_list, mContext, hashMap, new JsonCallback<ResponseBean<List<FriendsListInfoBean>>>() {
            @Override
            public void onSuccess(Response<ResponseBean<List<FriendsListInfoBean>>> response) {
                if (response.body().code == 0) {
                    view.getDataHttp(response.body().data);
                } else {
                    view.getDataHttpFail(response.body().message);
                }
            }
        });
    }
}
