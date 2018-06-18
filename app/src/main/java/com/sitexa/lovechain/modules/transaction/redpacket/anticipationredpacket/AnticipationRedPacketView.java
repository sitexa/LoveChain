package com.sitexa.lovechain.modules.transaction.redpacket.anticipationredpacket;

import com.sitexa.lovechain.base.BaseView;
import com.sitexa.lovechain.base.BaseView;
import com.sitexa.lovechain.bean.AuthRedPacketBean;

/**
 * Created By sitexa on 2018/6/17
 */
public interface AnticipationRedPacketView extends BaseView {
    void getAuthRedPacketDataHttp(AuthRedPacketBean.DataBean dataBean);

    void getDataHttpFail(String msg);
}
