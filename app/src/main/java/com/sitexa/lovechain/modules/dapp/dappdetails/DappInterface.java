package com.sitexa.lovechain.modules.dapp.dappdetails;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.sitexa.lovechain.app.MyApplication;
import com.sitexa.lovechain.blockchain.DappDataManager;
import com.sitexa.lovechain.R;
import com.sitexa.lovechain.utils.PasswordToKeyUtils;
import com.sitexa.lovechain.utils.ShowDialog;
import com.sitexa.lovechain.utils.ToastUtils;
import com.sitexa.lovechain.view.dialog.passworddialog.PasswordCallback;
import com.sitexa.lovechain.view.dialog.passworddialog.PasswordDialog;
import com.sitexa.lovechain.view.webview.BaseWebView;

import static com.lzy.okgo.utils.HttpUtils.runOnUiThread;

/**
 * Created by sitexa on 2018/6/19.
 */
public class DappInterface {

    private Context mContext;
    private BaseWebView mBaseWebView;

    /**
     * Instantiates a new Dapp interface.
     */
    public DappInterface(BaseWebView baseWebView, Context context) {
        this.mBaseWebView = baseWebView;
        mContext = context;
    }

    /**
     * Push action string.
     *
     * @param message           the message
     * @param permissionAccount the permission account
     * @return the string
     */
    @JavascriptInterface
    public void pushAction(String serialNumber, String message, String permissionAccount) {

        PasswordDialog mPasswordDialog = new PasswordDialog(mContext, new PasswordCallback() {
            @Override
            public void sure(String password) {
                if (MyApplication.getInstance().getUserBean().getWallet_shapwd().equals(PasswordToKeyUtils.shaCheck(password))) {
                    ShowDialog.showDialog(mContext, "", true, null).show();
                    new DappDataManager(mContext, password, new DappDataManager.Callback() {
                        @Override
                        public void getTxid(String txId) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mBaseWebView.loadUrl("javascript:pushActionResult('" + serialNumber + "','" + txId + "')");
                                }
                            });
                        }

                        @Override
                        public void erroMsg(String msg) {
                            msg += "ERROR:";
                            String finalMsg = msg;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mBaseWebView.loadUrl("javascript:pushActionResult('" + serialNumber + "','" + finalMsg + "')");
                                }
                            });
                        }
                    }).pushAction(message, permissionAccount);
                } else {
                    ToastUtils.showLongToast(mContext.getResources().getString(R.string.password_error));
                }
            }

            @Override
            public void cancle() {
            }
        });
        mPasswordDialog.setCancelable(true);
        mPasswordDialog.show();
    }
}
