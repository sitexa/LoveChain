package com.sitexa.lovechain.modules.leftdrawer.usercenter.otherlogintype;

import com.sitexa.lovechain.base.BaseView;
import com.sitexa.lovechain.base.BaseView;

/**
 * Created by sitexa on 2018/6/19.
 */

public interface OtherLoginTypeView extends BaseView {

    void unBindOtherLoginDataHttp();

    void bindOtherLoginDataHttp();

    void getDataHttpFail(String msg);
}
