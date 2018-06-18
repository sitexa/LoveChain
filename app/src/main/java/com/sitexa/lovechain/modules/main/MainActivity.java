package com.sitexa.lovechain.modules.main;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sitexa.lovechain.app.ActivityUtils;
import com.sitexa.lovechain.app.AppManager;
import com.sitexa.lovechain.app.MyApplication;
import com.sitexa.lovechain.base.BaseActivity;
import com.sitexa.lovechain.gen.UserBeanDao;
import com.sitexa.lovechain.modules.friendslist.myfriendslist.FriendsListFragment;
import com.sitexa.lovechain.modules.home.HomeFragment;
import com.sitexa.lovechain.modules.money.MoneyFragment;
import com.sitexa.lovechain.modules.leftdrawer.appupdate.AppUpdateActivity;
import com.sitexa.lovechain.modules.leftdrawer.candyintegral.CandyIntegralActivity;
import com.sitexa.lovechain.modules.leftdrawer.messagecenter.MessageCenterActivity;
import com.sitexa.lovechain.modules.leftdrawer.suggestionfeedback.SuggestionFeedbackActivity;
import com.sitexa.lovechain.modules.leftdrawer.systemsetting.SystemSettingActivity;
import com.sitexa.lovechain.modules.leftdrawer.transactionhistory.TransactionHistoryActivity;
import com.sitexa.lovechain.modules.leftdrawer.usercenter.UserCenterActivity;
import com.sitexa.lovechain.modules.news.NewsFragment;
import com.sitexa.lovechain.modules.normalvp.NormalPresenter;
import com.sitexa.lovechain.modules.normalvp.NormalView;
import com.sitexa.lovechain.modules.otherloginorshare.BaseUIListener;
import com.sitexa.lovechain.modules.otherloginorshare.WxShareAndLoginUtils;
import com.sitexa.lovechain.modules.wallet.createwallet.login.LoginActivity;
import com.sitexa.lovechain.modules.wallet.walletmanagement.WalletManagementActivity;
import com.sitexa.lovechain.R;
import com.sitexa.lovechain.bean.QrCodeWalletBean;
import com.sitexa.lovechain.modules.dapp.DappFragment;
import com.sitexa.lovechain.utils.FilesUtils;
import com.sitexa.lovechain.utils.ToastUtils;
import com.sitexa.lovechain.utils.UpdateUtils;
import com.sitexa.lovechain.utils.Utils;
import com.sitexa.lovechain.view.CircleImageView;
import com.sitexa.lovechain.view.dialog.walletcodedialog.WalletCodeCallback;
import com.sitexa.lovechain.view.dialog.walletcodedialog.WalletCodeDialog;
import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzonePublish;
import com.tencent.tauth.Tencent;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.sitexa.lovechain.R.id.drawer;

public class MainActivity extends BaseActivity<NormalView, NormalPresenter> implements View.OnClickListener, NormalView, MoneyFragment.OpenLeft {

    FrameLayout mContent;
    LinearLayout mLlHome;
    LinearLayout mLlMoney;
    //LinearLayout mLlFriendsList;
    LinearLayout mLlNews;
    //LinearLayout mLlApplication;
    @BindView(R.id.activity_main)
    LinearLayout mActivityMain;
    @BindView(drawer)
    DrawerLayout mDrawer;
    @BindView(R.id.user_img)
    CircleImageView mUserImg;
    @BindView(R.id.user_name)
    TextView mUserName;
    @BindView(R.id.wallet_management)
    TextView mWalletManagement;
    @BindView(R.id.transaction_history)
    TextView mTransactionHistory;
    @BindView(R.id.message_center)
    TextView mMessageCenter;
    @BindView(R.id.suggestion_feedback)
    TextView mSuggestionFeedback;
    @BindView(R.id.system_settings)
    TextView mSystemSettings;
    @BindView(R.id.app_update)
    TextView mAppUpdate;
    @BindView(R.id.navigation_view)
    LinearLayout mNavigationView;
    @BindView(R.id.logout_wallet)
    TextView mLogoutWallet;
    @BindView(R.id.user_wallet_code)
    ImageView mUserWalletCode;
    WalletCodeDialog dialog = null;
    @BindView(R.id.candy_integral)
    TextView mCandyIntegral;

    private HomeFragment homeFragment;
    private MoneyFragment moneyFragment;
    //private FriendsListFragment friendsListFragment;
    private NewsFragment newsFragment;
    //private DappFragment applicationFragment;
    private long exitTime = 0;

    @Override
    public void open(int tag) {
        mDrawer.openDrawer(Gravity.START);
    }

    @OnClick({R.id.user_img, R.id.wallet_management, R.id.transaction_history, R.id.candy_integral, R.id.message_center, R.id.suggestion_feedback, R.id.system_settings, R.id.app_update})
    public void onViewClicked(View view) {
        //  mDrawer.closeDrawers();
        switch (view.getId()) {
            case R.id.user_img:
                ActivityUtils.next(MainActivity.this, UserCenterActivity.class);
                break;
            case R.id.wallet_management:
                ActivityUtils.next(MainActivity.this, WalletManagementActivity.class);
                break;
            case R.id.transaction_history:
                ActivityUtils.next(MainActivity.this, TransactionHistoryActivity.class);
                break;
            case R.id.candy_integral:
                ActivityUtils.next(MainActivity.this, CandyIntegralActivity.class);
                break;
            case R.id.message_center:
                ActivityUtils.next(MainActivity.this, MessageCenterActivity.class);
                break;
            case R.id.suggestion_feedback:
                ActivityUtils.next(MainActivity.this, SuggestionFeedbackActivity.class);
                break;
            case R.id.system_settings:
                ActivityUtils.next(MainActivity.this, SystemSettingActivity.class);
                break;
            case R.id.app_update:
                ActivityUtils.next(MainActivity.this, AppUpdateActivity.class);
                break;
        }
    }

    /**
     * 二次退出确认 结束软件
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO 按两次返回键退出应用程序
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            // 判断间隔时间 大于2秒就退出应用
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                String msg1 = getString(R.string.drop_two_to_home);
                ToastUtils.showLongToast(msg1);
                // 计算两次返回键按下的时间差
                exitTime = System.currentTimeMillis();
            } else {
                // 返回桌面操作
                Intent home = new Intent(Intent.ACTION_MAIN);
                home.addCategory(Intent.CATEGORY_HOME);
                startActivity(home);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Fragment fragment = homeFragment;
        fragment.onActivityResult(requestCode, resultCode, data);
        hideProgress();
        if (requestCode == Constants.REQUEST_QQ_SHARE || requestCode == Constants.REQUEST_QZONE_SHARE || requestCode == Constants.REQUEST_OLD_SHARE) {
            Tencent.handleResultData(data, new BaseUIListener(MainActivity.this, true));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public NormalPresenter initPresenter() {
        return new NormalPresenter();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        mLlHome = getId(R.id.ll_home);
        mLlMoney = getId(R.id.ll_money);
        //mLlFriendsList = getId(R.id.ll_friends_list);
        mLlNews = getId(R.id.ll_news);
        //mLlApplication = getId(R.id.ll_application);
        mContent = getId(R.id.content);

        selectedFragment(0);
        tabSelected(mLlHome);

        UpdateUtils.updateApp(this, 0);
    }

    @Override
    protected void initData() {
        MyApplication.getInstance().setUserBean(MyApplication.getDaoInstant().getUserBeanDao().queryBuilder().where(UserBeanDao.Properties.Wallet_phone.eq(Utils.getSpUtils().getString("firstUser"))).build().unique());
        mUserName.setText(MyApplication.getInstance().getUserBean().getWallet_name() + getString(R.string.wallet));
        MyApplication.getInstance().showCirImage(MyApplication.getInstance().getUserBean().getWallet_img(), mUserImg);
    }

    @Override
    public void initEvent() {
        mLlHome.setOnClickListener(this);
        mLlMoney.setOnClickListener(this);
        //mLlFriendsList.setOnClickListener(this);
        mLlNews.setOnClickListener(this);
        //mLlApplication.setOnClickListener(this);
        mLogoutWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManager.getAppManager().finishAllActivity();
                ActivityUtils.next(MainActivity.this, LoginActivity.class, true);
            }
        });
        mUserWalletCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QrCodeWalletBean walletCodeBean = new QrCodeWalletBean();
                walletCodeBean.setType("wallet_QRCode");
                walletCodeBean.setWallet_img(MyApplication.getInstance().getUserBean().getWallet_img());
                walletCodeBean.setWallet_name(MyApplication.getInstance().getUserBean().getWallet_name());
                walletCodeBean.setWallet_uid(MyApplication.getInstance().getUserBean().getWallet_uid());
                //生成的账号二维码包含的信息：
                if (dialog == null) {
                    dialog = new WalletCodeDialog(MainActivity.this, new WalletCodeCallback() {
                        @Override
                        public void goWeixinFriend(Bitmap bitmap) {
                            WxShareAndLoginUtils.WxBitmapShare(MainActivity.this, bitmap, WxShareAndLoginUtils.WECHAT_FRIEND);
                        }

                        @Override
                        public void goWeixinCircle(Bitmap bitmap) {
                            WxShareAndLoginUtils.WxBitmapShare(MainActivity.this, bitmap, WxShareAndLoginUtils.WECHAT_MOMENT);
                        }

                        @Override
                        public void goQQFriend(Bitmap bitmap) {
                            Bundle params = new Bundle();
                            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
                            params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, FilesUtils.savePhoto(bitmap, Environment
                                    .getExternalStorageDirectory().getAbsolutePath() + "/pocketEos/walletCode", String
                                    .valueOf(System.currentTimeMillis())));
                            params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "pocketEos");
                            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
                            MyApplication.getInstance().getTencent().shareToQQ(MainActivity.this, params, new BaseUIListener(MainActivity.this, true));
                        }

                        @Override
                        public void goQzone(Bitmap bitmap) {
                            Bundle params = new Bundle();
                            params.putInt(QzonePublish.PUBLISH_TO_QZONE_KEY_TYPE, QzonePublish.PUBLISH_TO_QZONE_TYPE_PUBLISHMOOD);
                            ArrayList<String> imgUrlList = new ArrayList<>();
                            imgUrlList.add(FilesUtils.savePhoto(bitmap, Environment
                                    .getExternalStorageDirectory().getAbsolutePath() + "/pocketEos/walletCode", String
                                    .valueOf(System.currentTimeMillis())));// 图片地址
                            params.putStringArrayList(QzonePublish.PUBLISH_TO_QZONE_IMAGE_URL,
                                    imgUrlList);// 图片地址ArrayList
                            MyApplication.getInstance().getTencent().publishToQzone(MainActivity.this, params, new BaseUIListener(MainActivity.this, true));
                        }
                    });
                    dialog.setContent(new Gson().toJson(walletCodeBean));
                    dialog.setCancelable(true);
                    dialog.show();
                } else {
                    dialog.show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mUserName.setText(MyApplication.getInstance().getUserBean().getWallet_name() + getString(R.string.wallet));
        MyApplication.getInstance().showCirImage(MyApplication.getInstance().getUserBean().getWallet_img(), mUserImg);
    }

    private void selectedFragment(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideFragment(transaction);
        switch (position) {
            case 0:
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    transaction.add(R.id.content, homeFragment);
                } else {
                    transaction.show(homeFragment);
                }
                break;
            case 1:
                if (moneyFragment == null) {
                    moneyFragment = new MoneyFragment();
                    transaction.add(R.id.content, moneyFragment);
                } else {
                    transaction.show(moneyFragment);
                }
                break;
            //case 2:
            //    if (friendsListFragment == null) {
            //        friendsListFragment = new FriendsListFragment();
            //        transaction.add(R.id.content, friendsListFragment);
            //    } else {
            //        transaction.show(friendsListFragment);
            //    }
            //    break;
            case 2:
                if (newsFragment == null) {
                    newsFragment = new NewsFragment();
                    transaction.add(R.id.content, newsFragment);
                } else {
                    transaction.show(newsFragment);
                }
                break;
            //case 3:
            //    if (applicationFragment == null) {
            //        applicationFragment = new DappFragment();
            //        transaction.add(R.id.content, applicationFragment);
            //    } else {
            //        transaction.show(applicationFragment);
            //    }
            //    break;
        }
        transaction.commit();
    }

    private void tabSelected(LinearLayout linearLayout) {
        mLlHome.setSelected(false);
        mLlMoney.setSelected(false);
        //mLlFriendsList.setSelected(false);
        mLlNews.setSelected(false);
        //mLlApplication.setSelected(false);
        linearLayout.setSelected(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_home:
                selectedFragment(0);
                tabSelected(mLlHome);
                break;
            case R.id.ll_money:
                selectedFragment(1);
                tabSelected(mLlMoney);
                break;
            //case R.id.ll_friends_list:
            //    selectedFragment(2);
            //    tabSelected(mLlFriendsList);
            //    break;
            case R.id.ll_news:
                selectedFragment(2);
                tabSelected(mLlNews);
                break;
            //case R.id.ll_application:
            //    selectedFragment(3);
            //    tabSelected(mLlApplication);
            //    break;
        }
    }

    private void hideFragment(FragmentTransaction transaction) {

        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }
        if (moneyFragment != null) {
            transaction.hide(moneyFragment);
        }
        //if (friendsListFragment != null) {
        //    transaction.hide(friendsListFragment);
        //}
        if (newsFragment != null) {
            transaction.hide(newsFragment);
        }
        //if (applicationFragment != null) {
        //    transaction.hide(applicationFragment);
        //}
    }



}