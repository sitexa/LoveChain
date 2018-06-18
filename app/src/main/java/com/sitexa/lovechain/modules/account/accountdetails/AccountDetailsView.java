package com.sitexa.lovechain.modules.account.accountdetails;

import com.sitexa.lovechain.base.BaseView;
import com.sitexa.lovechain.base.BaseView;

/**
 * Created by sitexa on 2018/6/19.
 */

public interface AccountDetailsView extends BaseView {

    void setMainAccountHttp(int type);


    void getDataHttpFail(String msg);


}
