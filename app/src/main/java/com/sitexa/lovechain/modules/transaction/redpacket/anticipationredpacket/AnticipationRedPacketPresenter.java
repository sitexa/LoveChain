package com.sitexa.lovechain.modules.transaction.redpacket.anticipationredpacket;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.sitexa.lovechain.base.BasePresenter;
import com.sitexa.lovechain.base.BaseUrl;
import com.sitexa.lovechain.net.HttpUtils;
import com.sitexa.lovechain.net.callbck.JsonCallback;
import com.sitexa.lovechain.bean.AuthRedPacketBean;
import com.sitexa.lovechain.bean.ResponseBean;

import java.util.HashMap;

/**
 * Created By sitexa on 2018/6/17
 */

public class AnticipationRedPacketPresenter extends BasePresenter<AnticipationRedPacketView> {
    private Context mContext;

    public AnticipationRedPacketPresenter(Context context) {
        this.mContext = context;
    }

    public void getAuthRedPacketData(String id, String transactionId) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("id", id);
        hashMap.put("transactionId", transactionId);
        HttpUtils.postRequest(BaseUrl.getHTTP_get_red_packet_auth_message, mContext, hashMap, new JsonCallback<ResponseBean<AuthRedPacketBean.DataBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<AuthRedPacketBean.DataBean>> response) {
                if (response.body().code == 0) {
                    view.getAuthRedPacketDataHttp(response.body().data);
                } else {
                    view.getDataHttpFail(response.body().message);
                }
            }
        });
    }
}
