package com.sitexa.lovechain.modules.leftdrawer.usercenter;

import com.sitexa.lovechain.base.BaseView;
import com.sitexa.lovechain.base.BaseView;
import com.sitexa.lovechain.bean.UpdataPhotoBean;

/**
 * Created by sitexa on 2018/6/19.
 */

public interface UserCenterView extends BaseView {

    void headImgUploadaDataHttp(UpdataPhotoBean updataPhotoBean);


    void getDataHttpFail(String msg);
}
