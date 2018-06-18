package com.sitexa.lovechain.modules.leftdrawer.candyintegral;

import com.sitexa.lovechain.base.BaseView;
import com.sitexa.lovechain.bean.HotEquitiesBean;
import com.sitexa.lovechain.base.BaseView;
import com.sitexa.lovechain.bean.CandyScoreBean;
import com.sitexa.lovechain.bean.CandyUserTaskBean;
import com.sitexa.lovechain.bean.HotEquitiesBean;

import java.util.List;

/**
 * Created By sitexa on 2018/6/17
 * 获取friendslist
 */
public interface CandyIntegralView extends BaseView {

    void getCandyScoreDataHttp(CandyScoreBean.DataBean messageBean);

    void getHotEquitiesDataHttp(List<HotEquitiesBean.DataBean> mDataBeans);

    void getCandyTaskDataHttp(List<CandyUserTaskBean.DataBean> mDataBeans);

    void getDataHttpFail(String msg);
}
