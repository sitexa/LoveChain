package com.sitexa.lovechain.modules.transaction.redpacket.getredpacketdetails;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.sitexa.lovechain.app.MyApplication;
import com.sitexa.lovechain.base.BaseActivity;
import com.sitexa.lovechain.bean.RedPacketDetailsBean;
import com.sitexa.lovechain.R;
import com.sitexa.lovechain.adapter.AdapterManger;
import com.sitexa.lovechain.adapter.baseadapter.wrapper.EmptyWrapper;
import com.sitexa.lovechain.bean.RedPacketHistoryBean;
import com.sitexa.lovechain.utils.RegexUtil;
import com.sitexa.lovechain.view.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class GetRedPacketDetailsActivity extends BaseActivity<GetRedPacketDetailsView, GetRedPacketDetailsPresenter> implements GetRedPacketDetailsView {


    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.user_img)
    CircleImageView mUserImg;
    @BindView(R.id.user_number)
    TextView mUserNumber;
    @BindView(R.id.user_leave_message)
    TextView mUserLeaveMessage;
    @BindView(R.id.take_rmb_property)
    TextView mTakeRmbProperty;
    @BindView(R.id.red_packet_desc)
    TextView mRedPacketDesc;
    @BindView(R.id.red_packet_status)
    TextView mRedPacketStatus;
    @BindView(R.id.recycle_redpacket_history)
    RecyclerView mRecycleRedpacketHistory;

    private RedPacketHistoryBean.DataBean mDataBean = new RedPacketHistoryBean.DataBean();

    private List<RedPacketDetailsBean.DataBean.RedPacketOrderRedisDtosBean> mRedPacketOrderRedisDtosBeanList = new ArrayList<>();
    private EmptyWrapper mHistoryAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_get_red_packet_details;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mDataBean = getIntent().getParcelableExtra("data");
        mUserNumber.setText(getIntent().getStringExtra("account"));
        MyApplication.getInstance().showCirImage(MyApplication.getInstance().getUserBean().getWallet_img(), mUserImg);

        mTakeRmbProperty.setText(Html.fromHtml("<big>" + mDataBean.getAmount() + " " + "</big>" + "<small>" + mDataBean.getType() + "</small>"));
        mRedPacketDesc.setText(getString(R.string.received) + mDataBean.getType() + getString(R.string.send_to_main_account));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        layoutManager.setSmoothScrollbarEnabled(true);
        mRecycleRedpacketHistory.setLayoutManager(layoutManager);
    }

    @Override
    protected void initData() {
        presenter.getRedPacketDetailsData(mDataBean.getId());

        mHistoryAdapter = new EmptyWrapper(AdapterManger.getRedPacketDetailsAdapter(this, mRedPacketOrderRedisDtosBeanList));
        mHistoryAdapter.setEmptyView(R.layout.empty_project);
        mRecycleRedpacketHistory.setAdapter(mHistoryAdapter);
    }

    @Override
    public void initEvent() {

    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.fitsSystemWindows(true).statusBarDarkFont(false, 0f).statusBarColor(R.color.red_packet_color).init();
    }

    @Override
    public GetRedPacketDetailsPresenter initPresenter() {
        return new GetRedPacketDetailsPresenter(this);
    }

    @Override
    public void getRedPacketDetailsDataHttp(RedPacketDetailsBean.DataBean dataBean) {
        if (dataBean.getStatistics() != null) {
            mRedPacketStatus.setText(getString(R.string.already_received)+ (dataBean.getStatistics().getPacketCount() - dataBean.getStatistics().getResidueCount()) + "/" + dataBean.getStatistics().getPacketCount() + "个,剩余" + (RegexUtil.subZeroAndDot(dataBean.getStatistics().getResidueAmount()) + " " + mDataBean.getType()));
            for (RedPacketDetailsBean.DataBean.RedPacketOrderRedisDtosBean redPacketOrderRedisDtosBean : dataBean.getRedPacketOrderRedisDtos()) {
                mRedPacketOrderRedisDtosBeanList.add(redPacketOrderRedisDtosBean);
            }
            mHistoryAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void getDataHttpFail(String msg) {
        hideProgress();
        toast(msg);
    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
