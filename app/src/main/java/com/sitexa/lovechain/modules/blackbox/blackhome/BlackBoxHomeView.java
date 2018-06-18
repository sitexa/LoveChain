package com.sitexa.lovechain.modules.blackbox.blackhome;

import com.sitexa.lovechain.base.BaseView;
import com.sitexa.lovechain.bean.AccountWithCoinBean;
import com.sitexa.lovechain.base.BaseView;
import com.sitexa.lovechain.bean.AccountWithCoinBean;

import java.util.List;

/**
 * Created by sitexa on 2018/6/19.
 */

public interface BlackBoxHomeView extends BaseView {

    void getAccountDetailsDataHttp(List<AccountWithCoinBean> mAccountWithCoinBeen);

    void getDataHttpFail(String msg);
}
