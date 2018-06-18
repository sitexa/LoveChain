package com.sitexa.lovechain.modules.leftdrawer.systemsetting;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.sitexa.lovechain.base.BasePresenter;
import com.sitexa.lovechain.base.BaseUrl;
import com.sitexa.lovechain.net.HttpUtils;
import com.sitexa.lovechain.net.callbck.JsonCallback;
import com.sitexa.lovechain.bean.SystemInfoBean;
import com.sitexa.lovechain.bean.ResponseBean;

import java.util.HashMap;

/**
 * Created by sitexa on 2018/6/19.
 */

public class SystemSettingPresenter extends BasePresenter<SystemSettingView> {
    private Context mContext;

    public SystemSettingPresenter(Context context) {
        this.mContext = context;
    }

    public void getSystemInfo(final String id) {//1：法律条款和隐私政策，2：关于Pocket EOS的内容
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("id", id);
        HttpUtils.postRequest(BaseUrl.HTTP_get_system_info, mContext, hashMap, new JsonCallback<ResponseBean<SystemInfoBean.DataBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<SystemInfoBean.DataBean>> response) {
                if (response.body().code == 0) {
                    view.getSystemInfoHttp(response.body().data, id);
                } else {
                    view.getDataHttpFail(response.body().message);
                }
            }
        });
    }

}

