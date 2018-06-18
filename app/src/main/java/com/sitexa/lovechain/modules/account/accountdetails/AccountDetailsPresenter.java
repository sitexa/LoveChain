package com.sitexa.lovechain.modules.account.accountdetails;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.sitexa.lovechain.app.MyApplication;
import com.sitexa.lovechain.base.BasePresenter;
import com.sitexa.lovechain.base.BaseUrl;
import com.sitexa.lovechain.net.HttpUtils;
import com.sitexa.lovechain.net.callbck.JsonCallback;
import com.sitexa.lovechain.bean.ResponseBean;

import java.util.HashMap;

/**
 * Created by sitexa on 2018/6/19.
 */

public class AccountDetailsPresenter extends BasePresenter<AccountDetailsView> {
    private Context mContext;

    public AccountDetailsPresenter(Context context) {
        this.mContext = context;
    }

    public void setMianAccountData(String eosAccountName, final int type) {//0代表直接执行设置主账号操作，1代表先删除后设置主账号
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("uid", MyApplication.getInstance().getUserBean().getWallet_uid());
        hashMap.put("eosAccountName", eosAccountName);
        HttpUtils.postRequest(BaseUrl.HTTP_set_mian_account, mContext, hashMap, new JsonCallback<ResponseBean<String>>() {
            @Override
            public void onSuccess(Response<ResponseBean<String>> response) {
                if (response.body().code == 0) {
                    view.setMainAccountHttp(type);
                } else {
                    view.getDataHttpFail(response.body().message);
                }
            }
        });
    }
}

