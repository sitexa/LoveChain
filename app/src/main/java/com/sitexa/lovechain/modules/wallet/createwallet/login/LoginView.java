package com.sitexa.lovechain.modules.wallet.createwallet.login;

import com.sitexa.lovechain.base.BaseView;
import com.sitexa.lovechain.base.BaseView;
import com.sitexa.lovechain.bean.CodeAuthBean;

/**
 * Created by sitexa on 2018/6/19.
 */

public interface LoginView extends BaseView {

    void getCodeDataHttp(String message);

    void getCodeAuthDataHttp(CodeAuthBean.DataBean codeAuthBean);

    void getDataHttpFail(String msg);
}
