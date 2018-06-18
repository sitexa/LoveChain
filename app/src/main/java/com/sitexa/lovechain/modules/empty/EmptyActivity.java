package com.sitexa.lovechain.modules.empty;

import android.os.Bundle;

import com.sitexa.lovechain.base.BaseActivity;
import com.sitexa.lovechain.modules.normalvp.NormalPresenter;
import com.sitexa.lovechain.modules.normalvp.NormalView;
import com.sitexa.lovechain.R;

public class EmptyActivity extends BaseActivity<NormalView, NormalPresenter> implements NormalView {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_empty;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setCenterTitle("红包");
    }

    @Override
    protected void initData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public NormalPresenter initPresenter() {
        return new NormalPresenter();
    }
}
