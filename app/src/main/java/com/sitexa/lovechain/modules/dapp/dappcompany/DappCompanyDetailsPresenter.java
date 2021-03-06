package com.sitexa.lovechain.modules.dapp.dappcompany;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.sitexa.lovechain.base.BasePresenter;
import com.sitexa.lovechain.base.BaseUrl;
import com.sitexa.lovechain.net.HttpUtils;
import com.sitexa.lovechain.net.callbck.JsonCallback;
import com.sitexa.lovechain.bean.DappBean;
import com.sitexa.lovechain.bean.ResponseBean;

import java.util.HashMap;
import java.util.List;

/**
 * Created By sitexa on 2018/6/17
 */

public class DappCompanyDetailsPresenter extends BasePresenter<DappCompanyDetailsView> {
    private Context mContext;

    public DappCompanyDetailsPresenter(Context context) {
        this.mContext = context;
    }

    public void getData(int id) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("id", id + "");
        HttpUtils.postRequest(BaseUrl.HTTP_commpany_dapp_list, mContext, hashMap, new JsonCallback<ResponseBean<List<DappBean.DataBean>>>() {
            @Override
            public void onSuccess(Response<ResponseBean<List<DappBean.DataBean>>> response) {
                if (response.body().code == 0) {
                    view.getDappCompanyDataHttp(response.body().data);
                } else {
                    view.getDataHttpFail(response.body().message);
                }
            }
        });
    }

}
