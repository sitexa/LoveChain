package com.sitexa.lovechain.modules.leftdrawer.suggestionfeedback;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.sitexa.lovechain.app.MyApplication;
import com.sitexa.lovechain.base.BasePresenter;
import com.sitexa.lovechain.base.BaseUrl;
import com.sitexa.lovechain.net.HttpUtils;
import com.sitexa.lovechain.net.callbck.JsonCallback;
import com.sitexa.lovechain.bean.SuggestionBean;
import com.sitexa.lovechain.bean.ResponseBean;

import java.util.HashMap;
import java.util.List;

/**
 * Created By sitexa on 2018/6/17
 */

public class SuggestionFeedbackPresenter extends BasePresenter<SuggestionFeedbackView> {
    private Context mContext;

    public SuggestionFeedbackPresenter(Context context) {
        this.mContext = context;
    }

    public void postSuggestionDetails(String content) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("uid", MyApplication.getInstance().getUserBean().getWallet_uid());
        hashMap.put("content", content);
        HttpUtils.postRequest(BaseUrl.HTTP_post_suggestion, mContext, hashMap, new JsonCallback<ResponseBean<String>>() {
            @Override
            public void onSuccess(Response<ResponseBean<String>> response) {
                if (response.body().code == 0) {
                    view.postSuggestionHttp();
                } else {
                    view.getDataHttpFail(response.body().message);
                }
            }
        });
    }

    public void getSuggestionList(int offset) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("offset", offset + "");
        hashMap.put("size", "10");
        hashMap.put("uid", MyApplication.getInstance().getUserBean().getWallet_uid());
        HttpUtils.postRequest(BaseUrl.HTTP_get_suggestion_list, mContext, hashMap, new JsonCallback<ResponseBean<List<SuggestionBean.DataBean>>>() {
            @Override
            public void onSuccess(Response<ResponseBean<List<SuggestionBean.DataBean>>> response) {
                if (response.body().code == 0) {
                    view.getSuggestionListHttp(response.body().data);
                } else {
                    view.getDataHttpFail(response.body().message);
                }
            }
        });
    }
}
