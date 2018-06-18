package com.sitexa.lovechain.modules.news;

import com.sitexa.lovechain.base.BaseView;
import com.sitexa.lovechain.bean.NewsBean;
import com.sitexa.lovechain.base.BaseView;
import com.sitexa.lovechain.bean.CoinBean;
import com.sitexa.lovechain.bean.NewsBean;

import java.util.List;

/**
 * Created By sitexa on 2018/6/17
 * 获取friendslist
 */

public interface NewsView extends BaseView {

    void getNewsDataHttp(List<NewsBean.DataBean> newsBean);

    void getBannerDataHttp(List<NewsBean.DataBean> newsBean);

    void getCoinTypeDataHttp(List<CoinBean.DataBean> coinBeen);

    void getDataHttpFail(String msg);
}
