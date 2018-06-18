package com.sitexa.lovechain.modules.friendslist.friendsdetails;

import com.sitexa.lovechain.base.BaseView;
import com.sitexa.lovechain.bean.AccountWithCoinBean;
import com.sitexa.lovechain.base.BaseView;
import com.sitexa.lovechain.bean.AccountWithCoinBean;
import com.sitexa.lovechain.bean.WalletDetailsBean;

import java.util.List;

/**
 * Created by sitexa on 2018/6/19.
 */

public interface FriendsDetailsView extends BaseView {

    void getWalletDetailsDataHttp(List<WalletDetailsBean.DataBean> walletDetailsBean);

    void getAccountDetailsDataHttp(List<AccountWithCoinBean> mAccountWithCoinBeen);

    void getAddFollowsDataHttp();

    void getCancelFollow();

    void isfollow(Boolean isFollows);

    void getDataHttpFail(String msg);
}
