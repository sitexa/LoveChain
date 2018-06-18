package com.sitexa.lovechain.modules.dapp.paidanswer.paidanswerhome.fragment;

import android.content.Context;

import com.google.gson.Gson;
import com.lzy.okgo.model.Response;
import com.sitexa.lovechain.base.BasePresenter;
import com.sitexa.lovechain.base.BaseUrl;
import com.sitexa.lovechain.net.HttpUtils;
import com.sitexa.lovechain.net.callbck.JsonCallback;
import com.sitexa.lovechain.bean.GetAnswerListBean;
import com.sitexa.lovechain.bean.PaidAnswerBean;
import com.sitexa.lovechain.bean.ResponseBean;

/**
 * Created By sitexa on 2018/6/17
 */

public class PaidAnswerPresenter extends BasePresenter<PaidAnswerView> {
    private Context mContext;

    public PaidAnswerPresenter(Context context) {
        this.mContext = context;
    }
    public void getData(int page , String releasedLable ,String askid ) {
        HttpUtils.postRequest(BaseUrl.HTTP_GetAsks, mContext, new Gson().toJson(new GetAnswerListBean(askid,new GetAnswerListBean.PageBean(page,10),releasedLable)), new JsonCallback<ResponseBean<PaidAnswerBean.DataBeanX>>() {
            @Override
            public void onSuccess(Response<ResponseBean<PaidAnswerBean.DataBeanX>> response) {
                if (response.body().code == 0) {
                    view.getQuestionListDataHttp(response.body().data);
                } else {
                    view.getDataHttpFail(response.body().message);
                }
            }
        });
    }

}
