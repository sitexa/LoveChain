package com.sitexa.lovechain.modules.dapp;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.sitexa.lovechain.base.BasePresenter;
import com.sitexa.lovechain.base.BaseUrl;
import com.sitexa.lovechain.net.HttpUtils;
import com.sitexa.lovechain.net.callbck.JsonCallback;
import com.sitexa.lovechain.bean.DappBean;
import com.sitexa.lovechain.bean.DappCompanyBean;
import com.sitexa.lovechain.bean.ResponseBean;

import java.util.HashMap;
import java.util.List;

/**
 * Created By sitexa on 2018/6/17
 */

public class DappPresenter extends BasePresenter<DappView> {
    private Context mContext;

    public DappPresenter(Context context) {
        this.mContext = context;
    }

    public void getData() {

        HttpUtils.getRequets(BaseUrl.HTTP_dapp_commpany_list, mContext, new HashMap<String, String>(), new JsonCallback<ResponseBean<List<DappCompanyBean.DataBean>>>() {
            @Override
            public void onSuccess(Response<ResponseBean<List<DappCompanyBean.DataBean>>> response) {
                if (response.body().code == 0) {
                    view.getDappCompanyDataHttp(response.body().data);
                } else {
                    view.getDataHttpFail(response.body().message);
                }
            }
        });
        HttpUtils.getRequets(BaseUrl.HTTP_dapp_list, mContext, new HashMap<String, String>(), new JsonCallback<ResponseBean<List<DappBean.DataBean>>>() {
            @Override
            public void onSuccess(Response<ResponseBean<List<DappBean.DataBean>>> response) {
                if (response.body().code == 0) {
                    view.getDappDataHttp(response.body().data);
                } else {
                    view.getDataHttpFail(response.body().message);
                }
            }
        });
    }
}
