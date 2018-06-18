package com.sitexa.lovechain.modules.coindetails;

import com.sitexa.lovechain.base.BaseView;
import com.sitexa.lovechain.base.BaseView;
import com.sitexa.lovechain.bean.SparkLinesBean;
import com.sitexa.lovechain.bean.TransferHistoryBean;

/**
 * Created By sitexa on 2018/6/17
 */
public interface CoinDetailsView extends BaseView {


    void getTransferHistoryDataHttp(TransferHistoryBean.DataBeanX transferHistoryBean);

    void getSparklinesData(SparkLinesBean.DataBean dataBean);


    void getDataHttpFail(String msg);
}
