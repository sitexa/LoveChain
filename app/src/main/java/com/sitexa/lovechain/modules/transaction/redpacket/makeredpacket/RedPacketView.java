package com.sitexa.lovechain.modules.transaction.redpacket.makeredpacket;

import com.sitexa.lovechain.base.BaseView;
import com.sitexa.lovechain.base.BaseView;
import com.sitexa.lovechain.bean.CoinRateBean;
import com.sitexa.lovechain.bean.RedPacketHistoryBean;
import com.sitexa.lovechain.bean.SendRedPacketBean;

import java.util.List;

/**
 * Created By sitexa on 2018/6/17
 */
public interface RedPacketView extends BaseView {
    void getCoinRateDataHttp(CoinRateBean.DataBean coinRateBean);

    void getRedPacketHistoryDataHttp(List<RedPacketHistoryBean.DataBean> dataBeanList);

    void sendRedPacketDataHttp(SendRedPacketBean.DataBean sendRedPacketBean);


    void getDataHttpFail(String msg);
}
