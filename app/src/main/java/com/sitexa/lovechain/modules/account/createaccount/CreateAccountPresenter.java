package com.sitexa.lovechain.modules.account.createaccount;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.sitexa.lovechain.app.MyApplication;
import com.sitexa.lovechain.base.BasePresenter;
import com.sitexa.lovechain.base.BaseUrl;
import com.sitexa.lovechain.net.HttpUtils;
import com.sitexa.lovechain.net.callbck.JsonCallback;
import com.sitexa.lovechain.bean.EosRegisterBean;
import com.sitexa.lovechain.bean.ResponseBean;

import java.util.HashMap;

/**
 * Created by sitexa on 2018/6/19.
 */

public class CreateAccountPresenter extends BasePresenter<CreateAccountView> {

    private Context mContext;

    public CreateAccountPresenter(Context context) {
        this.mContext = context;
    }


    public void getRegisterData(String username, String owner_key, String active_key) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("account_name", username);
        hashMap.put("owner_key", owner_key);
        hashMap.put("active_key", active_key);
        HttpUtils.postRequest(BaseUrl.HTTP_eos_register, mContext, hashMap, new JsonCallback<ResponseBean<EosRegisterBean.DataBeanX>>() {
            @Override
            public void onSuccess(Response<ResponseBean<EosRegisterBean.DataBeanX>> response) {
                if (response.body().code == 0) {
                    view.getEosRegisterhDataHttp(response.body().data);
                } else {
                    view.getDataHttpFail(response.body().message);
                }
            }
        });
    }

    public void postEosAccountData(String eosAccountName) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("uid", MyApplication.getInstance().getUserBean().getWallet_uid());
        hashMap.put("eosAccountName", eosAccountName);
        HttpUtils.postRequest(BaseUrl.HTTP_add_new_eos, mContext, hashMap, new JsonCallback<ResponseBean<String>>() {
            @Override
            public void onSuccess(Response<ResponseBean<String>> response) {
                if (response.body().code == 0) {
                    view.postEosAccountDataHttp();
                } else {
                    view.getDataHttpFail(response.body().message);
                }
            }
        });
    }

    public void setMianAccountData(String eosAccountName) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("uid", MyApplication.getInstance().getUserBean().getWallet_uid());
        hashMap.put("eosAccountName", eosAccountName);
        HttpUtils.postRequest(BaseUrl.HTTP_set_mian_account, mContext, hashMap, new JsonCallback<ResponseBean<String>>() {
            @Override
            public void onSuccess(Response<ResponseBean<String>> response) {
                if (response.body().code == 0) {
                    view.setMainAccountHttp();
                } else {
                    view.getDataHttpFail(response.body().message);
                }
            }
        });
    }
}

