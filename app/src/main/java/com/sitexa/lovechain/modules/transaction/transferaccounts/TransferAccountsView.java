package com.sitexa.lovechain.modules.transaction.transferaccounts;

import com.sitexa.lovechain.base.BaseView;
import com.sitexa.lovechain.base.BaseView;
import com.sitexa.lovechain.bean.AccountDetailsBean;
import com.sitexa.lovechain.bean.CoinRateBean;
import com.sitexa.lovechain.bean.TransferHistoryBean;

/**
 * Created By sitexa on 2018/6/17
 */
public interface TransferAccountsView extends BaseView {
    void getCoinRateDataHttp(CoinRateBean.DataBean coinRateBean);

    void getAccountDetailsDataHttp(AccountDetailsBean accountDetailsBean);


    void getTransferHistoryDataHttp(TransferHistoryBean.DataBeanX transferHistoryBean);

    void getDataHttpFail(String msg);
}
