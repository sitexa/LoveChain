package com.sitexa.lovechain.modules.friendslist.pelist;

import com.sitexa.lovechain.base.BaseView;
import com.sitexa.lovechain.base.BaseView;
import com.sitexa.lovechain.bean.PelistBean;

import java.util.List;

/**
 * Created By sitexa on 2018/6/17
 * 获取friendslist
 */
public interface PelistView extends BaseView {

    void getListDataHttp(List<PelistBean.DataBean> pelistBean);

    void getDataHttpFail(String msg);
}
