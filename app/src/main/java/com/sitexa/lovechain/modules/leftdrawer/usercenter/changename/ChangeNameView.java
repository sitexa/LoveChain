package com.sitexa.lovechain.modules.leftdrawer.usercenter.changename;

import com.sitexa.lovechain.base.BaseView;
import com.sitexa.lovechain.base.BaseView;

/**
 * Created by sitexa on 2018/6/19.
 */

public interface ChangeNameView extends BaseView {

    void updateNameDataHttp();


    void getDataHttpFail(String msg);
}
