package com.sitexa.lovechain.modules.scancode;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;
import com.mylhyl.zxing.scanner.OnScannerCompletionListener;
import com.mylhyl.zxing.scanner.ScannerView;
import com.mylhyl.zxing.scanner.decode.QRDecode;
import com.sitexa.lovechain.app.ActivityUtils;
import com.sitexa.lovechain.app.MyApplication;
import com.sitexa.lovechain.base.BaseActivity;
import com.sitexa.lovechain.bean.QrCodeMakeCollectionBean;
import com.sitexa.lovechain.modules.friendslist.friendsdetails.FriendsDetailsActivity;
import com.sitexa.lovechain.modules.normalvp.NormalPresenter;
import com.sitexa.lovechain.modules.transaction.transferaccounts.TransferAccountsActivity;
import com.sitexa.lovechain.R;
import com.sitexa.lovechain.bean.QrCodeAccountBean;
import com.sitexa.lovechain.bean.QrCodeAccountPrivateKeyBean;
import com.sitexa.lovechain.bean.QrCodeWalletBean;
import com.sitexa.lovechain.modules.account.importaccount.ImportAccountActivity;
import com.sitexa.lovechain.modules.normalvp.NormalView;
import com.sitexa.lovechain.utils.FilesUtils;
import com.sitexa.lovechain.utils.JsonUtil;
import com.sitexa.lovechain.utils.ShowDialog;
import com.sitexa.lovechain.utils.ToastUtils;
import com.sitexa.lovechain.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.ljp.permission.PermissionItem;

public class ScanCodeActivity extends BaseActivity<NormalView, NormalPresenter> implements NormalView {

    @BindView(R.id.scanner_view)
    ScannerView mScannerView;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.ll)
    RelativeLayout mLl;
    @BindView(R.id.tv_right)
    TextView mTvRight;
    private static final int CHOOSE_PICTURE = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_scan;
    }


    @Override
    protected void initViews(Bundle savedInstanceState) {


        mScannerView.setMediaResId(R.raw.beep);//设置扫描成功的声音
        mScannerView.setLaserFrameBoundColor(getResources().getColor(R.color.theme_color));
        mScannerView.setLaserColor(getResources().getColor(R.color.theme_color));
        //mScannerView.toggleLight(true);//切换闪光灯
    }

    @Override
    protected void initData() {

    }

    @Override
    public void initEvent() {
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mScannerView.setOnScannerCompletionListener(new OnScannerCompletionListener() {
            @Override
            public void onScannerCompletion(Result result, ParsedResult parsedResult, Bitmap bitmap) {
                pareCode(parsedResult.toString());
            }

        });
        mTvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<PermissionItem> permissonItems = new ArrayList<PermissionItem>();
                permissonItems.add(new PermissionItem(Manifest.permission.WRITE_EXTERNAL_STORAGE, getString(R.string.WRITE_STORAGE), R.drawable.permission_card1));
                permissonItems.add(new PermissionItem(Manifest.permission.READ_EXTERNAL_STORAGE, getString(R.string.READ_STORAGE), R.drawable.permission_card1));
                if(Utils.getPermissions(permissonItems , getString(R.string.SCAN_EXTERNAL_STORAGE))){
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, CHOOSE_PICTURE);
                }
            }
        });
    }

    @Override
    public NormalPresenter initPresenter() {
        return new NormalPresenter();
    }

    @Override
    protected void onResume() {
        mScannerView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mScannerView.onPause();
        super.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ScanCodeActivity.RESULT_OK && requestCode == CHOOSE_PICTURE) {
            //获取uri拿bitmap要做压缩处理，防止oom
            ShowDialog.showDialog(this, "识别中...", true, null);
            Uri originalUri = null;
            File file = null;
            if (null != data) {
                originalUri = data.getData();
                file = FilesUtils.getFileFromMediaUri(ScanCodeActivity.this, originalUri);
            }
            Bitmap photoBmp = null;
            try {
                photoBmp = FilesUtils.getBitmapFormUri(ScanCodeActivity.this, Uri.fromFile(file));
            } catch (IOException e) {
                e.printStackTrace();
            }
            int degree = FilesUtils.getBitmapDegree(file.getAbsolutePath());
            //把图片旋转为正的方向
            Bitmap newbitmap = FilesUtils.rotateBitmapByDegree(photoBmp, degree);
            QRDecode.decodeQR(newbitmap, new OnScannerCompletionListener() {
                @Override
                public void onScannerCompletion(Result result, ParsedResult parsedResult, Bitmap bitmap) {
                    ShowDialog.dissmiss();
                    if (parsedResult != null) {
                        pareCode(parsedResult.toString());

                    } else {
                        toast(getString(R.string.scanner_error_toast));
                    }
                }
            });
        }
    }

    public void pareCode(String data) {
        Bundle bundle = new Bundle();
        //钱包二维码包含:wallet_name:test2&wallet_uid:456
        if (data.toString().contains("wallet_QRCode")) {
            QrCodeWalletBean walletCodeBean = (QrCodeWalletBean) JsonUtil.parseStringToBean(data.toString(), QrCodeWalletBean.class);
            bundle.putString("name", walletCodeBean.getWallet_name());
            bundle.putString("friend_type", "1");//属于钱包级别
            bundle.putString("avatar", walletCodeBean.getWallet_img());
            bundle.putString("uid", walletCodeBean.getWallet_uid());
            bundle.putString("from", "code");
            ActivityUtils.next(ScanCodeActivity.this, FriendsDetailsActivity.class, bundle, true);
        } else if (data.toString().contains("account_QRCode")) {
            QrCodeAccountBean qrCodeAccountBean = (QrCodeAccountBean) JsonUtil.parseStringToBean(data.toString(), QrCodeAccountBean.class);
            bundle.putString("name", qrCodeAccountBean.getAccount_name());
            bundle.putString("friend_type", "2");//属于账号级别
            bundle.putString("avatar", qrCodeAccountBean.getAccount_img());
            bundle.putString("uid", "");
            bundle.putString("from", "code");
            ActivityUtils.next(ScanCodeActivity.this, FriendsDetailsActivity.class, bundle, true);
        } else if (data.toString().contains("account_priviate_key_QRCode")) {
            if (!getIntent().getStringExtra("from").equals("home")) {
                QrCodeAccountPrivateKeyBean qrCodeAccountPrivateKeyBean = (QrCodeAccountPrivateKeyBean) JsonUtil.parseStringToBean(data.toString(), QrCodeAccountPrivateKeyBean.class);
                bundle.putString("account_name", qrCodeAccountPrivateKeyBean.getAccount_name());
                bundle.putString("owner_private_key", qrCodeAccountPrivateKeyBean.getOwner_private_key());
                bundle.putString("active_private_key", qrCodeAccountPrivateKeyBean.getActive_private_key());
                ActivityUtils.goBackWithResult(ScanCodeActivity.this, 200, bundle);
            } else {
                QrCodeAccountPrivateKeyBean qrCodeAccountPrivateKeyBean = (QrCodeAccountPrivateKeyBean) JsonUtil.parseStringToBean(data.toString(), QrCodeAccountPrivateKeyBean.class);
                bundle.putString("account_name", qrCodeAccountPrivateKeyBean.getAccount_name());
                bundle.putString("owner_private_key", qrCodeAccountPrivateKeyBean.getOwner_private_key());
                bundle.putString("active_private_key", qrCodeAccountPrivateKeyBean.getActive_private_key());
                ActivityUtils.next(ScanCodeActivity.this, ImportAccountActivity.class, bundle, true);
            }
        } else if (data.toString().contains("make_collections_QRCode")) {
            if (!getIntent().getStringExtra("from").equals("home")) {
                QrCodeMakeCollectionBean qrCodeMakeCollectionBean = (QrCodeMakeCollectionBean) JsonUtil.parseStringToBean(data.toString(), QrCodeMakeCollectionBean.class);
                bundle.putString("account", qrCodeMakeCollectionBean.getAccount_name());
                bundle.putString("money", qrCodeMakeCollectionBean.getMoney());
                bundle.putString("coin", qrCodeMakeCollectionBean.getCoin());
                ActivityUtils.goBackWithResult(ScanCodeActivity.this, 300, bundle);
            } else {
                QrCodeMakeCollectionBean qrCodeMakeCollectionBean = (QrCodeMakeCollectionBean) JsonUtil.parseStringToBean(data.toString(), QrCodeMakeCollectionBean.class);
                bundle.putString("account", MyApplication.getInstance().getUserBean().getWallet_main_account());
                bundle.putString("coin", "EOS");
                bundle.putParcelable("info", qrCodeMakeCollectionBean);
                bundle.putString("from", "qrcode");
                ActivityUtils.next(ScanCodeActivity.this, TransferAccountsActivity.class, bundle, true);
            }
        } else {
            ToastUtils.showShortToast(R.string.scan_code_notice);
        }
    }
}
