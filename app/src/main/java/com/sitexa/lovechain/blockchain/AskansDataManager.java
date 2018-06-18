package com.sitexa.lovechain.blockchain;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lzy.okgo.model.Response;
import com.sitexa.lovechain.base.BaseUrl;
import com.sitexa.lovechain.base.Constants;
import com.sitexa.lovechain.blockchain.api.EosChainInfo;
import com.sitexa.lovechain.blockchain.bean.GetRequiredKeys;
import com.sitexa.lovechain.blockchain.bean.JsonToBeanResultBean;
import com.sitexa.lovechain.blockchain.bean.JsonToBinRequest;
import com.sitexa.lovechain.blockchain.bean.PushSuccessBean;
import com.sitexa.lovechain.blockchain.bean.RequireKeyResult;
import com.sitexa.lovechain.blockchain.chain.Action;
import com.sitexa.lovechain.blockchain.chain.PackedTransaction;
import com.sitexa.lovechain.blockchain.chain.SignedTransaction;
import com.sitexa.lovechain.blockchain.cypto.ec.EosPrivateKey;
import com.sitexa.lovechain.blockchain.types.TypeChainId;
import com.sitexa.lovechain.blockchain.util.GsonEosTypeAdapterFactory;
import com.sitexa.lovechain.net.HttpUtils;
import com.sitexa.lovechain.net.callbck.JsonCallback;
import com.sitexa.lovechain.bean.ResponseBean;
import com.sitexa.lovechain.utils.PublicAndPrivateKeyUtils;
import com.sitexa.lovechain.utils.ShowDialog;
import com.sitexa.lovechain.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sitexa on 2018/6/19.
 * 币答测试管理
 */

public class AskansDataManager {
    public  static String OCTCONTRACT =  Constants.OCTCONTRACT;//erctoken
    public  static String OCTASKANSCONTRACT = Constants.OCTASKANSCONTRACT;//erctoken
    public  static String ACTIONAPPROVE =  Constants.ACTIONAPPROVE;//授权可转走（押币）
    public  static String ACTIONASK =  Constants.ACTIONASK;//押币后提问
    public  static String ACTIONANSWER =  Constants.ACTIONANSWER;//押币后回答问题
    public  static String PERMISSONION =  Constants.PERMISSONION;

    Callback mCallback;
    Context mContext;
    EosChainInfo mChainInfoBean = new EosChainInfo();
    JsonToBeanResultBean mJsonToBeanResultBean = new JsonToBeanResultBean();
    String[] permissions;
    SignedTransaction txnBeforeSign;
    Gson mGson = new GsonBuilder()
            .registerTypeAdapterFactory(new GsonEosTypeAdapterFactory())
            .excludeFieldsWithoutExposeAnnotation().create();

    String contract, action, message, userpassword;

    public AskansDataManager(Context context, String userpassword, Callback callback) {
        mContext = context;
        mCallback = callback;
        this.userpassword = userpassword;
    }

    public void pushAction(String contract, String action, String message, String permissionAccount) {
        this.message = message;
        this.contract = contract;
        this.action = action;
        permissions = new String[]{permissionAccount + "@" + PERMISSONION};
        getChainInfo();
    }

    public void getChainInfo() {
        HttpUtils.getRequets(BaseUrl.HTTP_get_chain_info, this, new HashMap<String, String>(), new JsonCallback<ResponseBean<EosChainInfo>>() {
            @Override
            public void onSuccess(Response<ResponseBean<EosChainInfo>> response) {
                if (response.body().code == 0) {
                    mChainInfoBean = response.body().data;
                    getabi_json_to_bin();
                } else {
                    if (ShowDialog.dialog != null) {
                        ShowDialog.dissmiss();
                    }
                    ToastUtils.showLongToast(response.body().message);
                }
            }
        });
    }

    public void getabi_json_to_bin() {
        JsonToBinRequest jsonToBinRequest = new JsonToBinRequest(contract, action, message.replaceAll("\\r|\\n", ""));
        HttpUtils.postRequest(BaseUrl.HTTP_get_abi_json_to_bin, this, mGson.toJson(jsonToBinRequest), new JsonCallback<ResponseBean<JsonToBeanResultBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<JsonToBeanResultBean>> response) {
                if (response.body().code == 0) {
                    mJsonToBeanResultBean = response.body().data;
                    txnBeforeSign = createTransaction(contract, action, mJsonToBeanResultBean.getBinargs(), permissions, mChainInfoBean);
                    //扫描钱包列出所有可用账号的公钥
                    List<String> pubKey = PublicAndPrivateKeyUtils.getActivePublicKey();

                    getRequreKey(new GetRequiredKeys(txnBeforeSign, pubKey));
                } else {
                    if (ShowDialog.dialog != null) {
                        ShowDialog.dissmiss();
                    }
                    ToastUtils.showLongToast(response.body().message);
                }
            }
        });
    }

    private SignedTransaction createTransaction(String contract, String actionName, String dataAsHex,
                                                String[] permissions, EosChainInfo chainInfo) {

        Action action = new Action(contract, actionName);
        action.setAuthorization(permissions);
        action.setData(dataAsHex);


        SignedTransaction txn = new SignedTransaction();
        txn.addAction(action);
        txn.putSignatures(new ArrayList<String>());


        if (null != chainInfo) {
            txn.setReferenceBlock(chainInfo.getHeadBlockId());
            txn.setExpiration(chainInfo.getTimeAfterHeadBlockTime(30000));
        }
        return txn;
    }

    public void getRequreKey(GetRequiredKeys getRequiredKeys) {
        HttpUtils.postRequest(BaseUrl.HTTP_get_required_keys, this, mGson.toJson(getRequiredKeys), new JsonCallback<ResponseBean<RequireKeyResult>>() {
            @Override
            public void onSuccess(Response<ResponseBean<RequireKeyResult>> response) {
                if (response.body().code == 0) {
                    EosPrivateKey eosPrivateKey = new EosPrivateKey(PublicAndPrivateKeyUtils.getPrivateKey(response.body().data.getRequired_keys().get(0), userpassword));
                    txnBeforeSign.sign(eosPrivateKey, new TypeChainId(mChainInfoBean.getChain_id()));
                    pushTransactionRetJson(new PackedTransaction(txnBeforeSign));
                } else {
                    if (ShowDialog.dialog != null) {
                        ShowDialog.dissmiss();
                    }
                    ToastUtils.showLongToast(response.body().message);
                }
            }
        });
    }



    public void pushTransactionRetJson(PackedTransaction body) {
        HttpUtils.postRequest(BaseUrl.HTTP_push_transaction, this, mGson.toJson(body), new JsonCallback<ResponseBean<PushSuccessBean.DataBeanX>>() {
            @Override
            public void onSuccess(final Response<ResponseBean<PushSuccessBean.DataBeanX>> response) {
                if (ShowDialog.dialog != null) {
                    ShowDialog.dissmiss();
                }
                if (response.body().code == 0) {
                    mCallback.isApprove(action);
                } else {
                    ToastUtils.showLongToast(response.body().message);
                }
            }
        });
    }

    public interface Callback {
        void isApprove(String action);
    }
}
