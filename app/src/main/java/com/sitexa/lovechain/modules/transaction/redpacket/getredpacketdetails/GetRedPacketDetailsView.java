package com.sitexa.lovechain.modules.transaction.redpacket.getredpacketdetails;

import com.sitexa.lovechain.base.BaseView;
import com.sitexa.lovechain.bean.RedPacketDetailsBean;
import com.sitexa.lovechain.base.BaseView;
import com.sitexa.lovechain.bean.RedPacketDetailsBean;

/**
 * Created By sitexa on 2018/6/17
 */
public interface GetRedPacketDetailsView extends BaseView {
    void getRedPacketDetailsDataHttp(RedPacketDetailsBean.DataBean dataBean);

    void getDataHttpFail(String msg);
}
