package com.sitexa.lovechain.modules.home;

import android.os.Bundle;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.sitexa.lovechain.R;
import com.sitexa.lovechain.base.BaseFragment;
import com.sitexa.lovechain.view.MyScrollview;

import butterknife.BindView;

public class HomeFragment extends BaseFragment<HomeView,HomePresenter> implements HomeView {

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.recycle_lover)
    XRecyclerView mRecycleLover;


    @Override
    public HomePresenter initPresenter() {
        return new HomePresenter(getActivity());
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_home;
    }


}
