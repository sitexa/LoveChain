package com.sitexa.lovechain.modules.dapp;

import com.sitexa.lovechain.base.BaseView;
import com.sitexa.lovechain.bean.DappBean;
import com.sitexa.lovechain.bean.DappCompanyBean;

import java.util.List;

/**
 * Created By sitexa on 2018/6/17
 * 获取friendslist
 */

public interface DappView extends BaseView {
    void getDappCompanyDataHttp(List<DappCompanyBean.DataBean> dappCommpanyBean);

    void getDappDataHttp(List<DappBean.DataBean> dappBean);

    void getDataHttpFail(String msg);
}
