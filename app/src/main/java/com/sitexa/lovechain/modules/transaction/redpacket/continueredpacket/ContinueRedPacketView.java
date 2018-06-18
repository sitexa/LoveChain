package com.sitexa.lovechain.modules.transaction.redpacket.continueredpacket;

import com.sitexa.lovechain.base.BaseView;
import com.sitexa.lovechain.bean.RedPacketDetailsBean;
import com.sitexa.lovechain.base.BaseView;
import com.sitexa.lovechain.bean.CoinRateBean;
import com.sitexa.lovechain.bean.RedPacketDetailsBean;

/**
 * Created By sitexa on 2018/6/17
 */
public interface ContinueRedPacketView extends BaseView {
    void getCoinRateDataHttp(CoinRateBean.DataBean coinRateBean);

    void getRedPacketDetailsDataHttp(RedPacketDetailsBean.DataBean dataBean);

    void getDataHttpFail(String msg);
}
