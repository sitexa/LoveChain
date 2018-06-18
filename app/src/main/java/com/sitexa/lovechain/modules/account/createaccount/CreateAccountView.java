package com.sitexa.lovechain.modules.account.createaccount;

import com.sitexa.lovechain.base.BaseView;
import com.sitexa.lovechain.base.BaseView;
import com.sitexa.lovechain.bean.EosRegisterBean;

/**
 * Created by sitexa on 2018/6/19.
 */

public interface CreateAccountView extends BaseView {

    void getEosRegisterhDataHttp(EosRegisterBean.DataBeanX eosRegisterBean);
    void postEosAccountDataHttp();
    void getDataHttpFail(String msg);
    void setMainAccountHttp();
}
