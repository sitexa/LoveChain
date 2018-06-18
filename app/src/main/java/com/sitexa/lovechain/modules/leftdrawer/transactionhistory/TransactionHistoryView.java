package com.sitexa.lovechain.modules.leftdrawer.transactionhistory;

import com.sitexa.lovechain.base.BaseView;
import com.sitexa.lovechain.base.BaseView;
import com.sitexa.lovechain.bean.TransferHistoryBean;

/**
 * Created By sitexa on 2018/6/17
 */
public interface TransactionHistoryView extends BaseView {


    void getTransferHistoryDataHttp(TransferHistoryBean.DataBeanX transferHistoryBean);


    void getDataHttpFail(String msg);
}
