package com.sitexa.lovechain.modules.account.mapaccount;

import android.os.Bundle;
import android.widget.Button;

import com.sitexa.lovechain.base.BaseActivity;
import com.sitexa.lovechain.blockchain.cypto.ec.EosPrivateKey;
import com.sitexa.lovechain.modules.account.createaccount.CreateAccountPresenter;
import com.sitexa.lovechain.modules.account.createaccount.CreateAccountView;
import com.sitexa.lovechain.R;
import com.sitexa.lovechain.bean.EosRegisterBean;
import com.sitexa.lovechain.view.ClearEditText;

import butterknife.BindView;
import butterknife.OnClick;

public class MapAccountActivity extends BaseActivity<CreateAccountView, CreateAccountPresenter> implements CreateAccountView {


    @BindView(R.id.account_name)
    ClearEditText mAccountName;
    @BindView(R.id.owner_private_key)
    ClearEditText mOwnerPrivateKey;
    @BindView(R.id.import_number)
    Button mImportNumber;

    private String mAccount_owner_private_key, mAccount_active_private_key = null;
    private String mAccount_owner_public_key, mAccount_active_public_key = null;
    private String userPassword = null;


    private EosPrivateKey mOwnerKey;
    private EosPrivateKey mActiveKey;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_map_account;
    }

    @Override
    public CreateAccountPresenter initPresenter() {
        return new CreateAccountPresenter(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setCenterTitle(getString(R.string.map_account_title));
    }

    @Override
    protected void initData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void getEosRegisterhDataHttp(EosRegisterBean.DataBeanX eosRegisterBean) {

    }

    @Override
    public void postEosAccountDataHttp() {

    }

    @Override
    public void getDataHttpFail(String msg) {

    }

    @Override
    public void setMainAccountHttp() {

    }



    @OnClick(R.id.import_number)
    public void onViewClicked() {
        toast("EOS主网上线之后，该功能开放");
       /* if (RegexUtil.isEosName(mAccountName.getText().toString())) {
            PasswordDialog dialog = new PasswordDialog(MapAccountActivity.this, new PasswordCallback() {
                @Override
                public void sure(String password) {
                    if (MyApplication.getInstance().getUserBean().getWallet_shapwd().equals(PasswordToKeyUtils.shaCheck(password))) {
                        userPassword = password;
                        showProgress();
                        mOwnerKey = EosDataManager.getPrivateKey(2)[0];
                        mActiveKey = EosDataManager.getPrivateKey(2)[1];
                        mAccount_owner_public_key = mOwnerKey.getPublicKey().toString();
                        mAccount_active_public_key = mActiveKey.getPublicKey().toString();
                        mAccount_active_private_key = mActiveKey.toString();
                        mAccount_owner_private_key = mOwnerKey.toString();
                        presenter.getRegisterData(mAccountName.getText().toString(), mAccount_owner_public_key, mAccount_active_public_key);
                    } else {
                        toast(getResources().getString(R.string.password_error));
                    }
                }

                @Override
                public void cancle() {
                }
            });
            dialog.setCancelable(true);
            dialog.show();
        } else {
            toast(getString(R.string.eos_register_toast));
        }*/
    }
}
