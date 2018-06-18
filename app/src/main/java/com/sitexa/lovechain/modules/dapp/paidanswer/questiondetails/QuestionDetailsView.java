package com.sitexa.lovechain.modules.dapp.paidanswer.questiondetails;

import com.sitexa.lovechain.base.BaseView;
import com.sitexa.lovechain.base.BaseView;
import com.sitexa.lovechain.bean.ChainInfoBean;
import com.sitexa.lovechain.bean.GetChainJsonBean;
import com.sitexa.lovechain.bean.GetRequiredKeysBean;

/**
 * Created By sitexa on 2018/6/17
 */
public interface QuestionDetailsView extends BaseView {


    void getChainInfoHttp(ChainInfoBean.DataBean chainInfoBean);

    void getChainJsonHttp(GetChainJsonBean.DataBean getChainJsonBean);

    void getRequiredKeysHttp(GetRequiredKeysBean.DataBean getRequiredKeysBean);

    void pushtransactionHttp();

    void getDataHttpFail(String msg);
}
