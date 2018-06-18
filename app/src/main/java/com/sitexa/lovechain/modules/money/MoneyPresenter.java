package com.sitexa.lovechain.modules.money;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.sitexa.lovechain.base.BasePresenter;
import com.sitexa.lovechain.base.BaseUrl;
import com.sitexa.lovechain.bean.AccountWithCoinBean;
import com.sitexa.lovechain.net.HttpUtils;
import com.sitexa.lovechain.net.callbck.JsonCallback;
import com.sitexa.lovechain.bean.AccountDetailsBean;
import com.sitexa.lovechain.bean.ResponseBean;
import com.sitexa.lovechain.utils.RegexUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sitexa on 2018/6/19.
 */

public class MoneyPresenter extends BasePresenter<MoneyView> {
    private Context mContext;

    public MoneyPresenter(Context context) {
        this.mContext = context;
    }
    public void getAccountDetailsData(final String name ) {

        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("name", name);
        HttpUtils.postRequest(BaseUrl.HTTP_eos_get_account, mContext, hashMap, new JsonCallback<ResponseBean<AccountDetailsBean>>() {
            @Override
            public void onSuccess(Response<ResponseBean<AccountDetailsBean>> response) {
                if (response.body().code == 0) {
                    if (response.body().data.getAccount_name().equals(name)) {
                        List<AccountWithCoinBean> accountWithCoinBeens = new ArrayList<>();
                        AccountWithCoinBean  eos = new AccountWithCoinBean();
                        eos.setCoinName("EOS");
                        eos.setCoinForCny(RegexUtil.subZeroAndDot(response.body().data.getEos_balance_cny()));
                        eos.setCoinNumber(RegexUtil.subZeroAndDot(response.body().data.getEos_balance()));
                        eos.setCoinImg(response.body().data.getAccount_icon());
                        eos.setEos_market_cap_usd(response.body().data.getEos_market_cap_usd());
                        eos.setEos_market_cap_cny(response.body().data.getEos_market_cap_cny());
                        eos.setEos_price_cny(response.body().data.getEos_price_cny());
                        if (response.body().data.getEos_price_change_in_24h().contains("-")) {
                            eos.setCoinUpsAndDowns(response.body().data.getEos_price_change_in_24h() + "%");
                        } else {
                            eos.setCoinUpsAndDowns("+" + response.body().data.getEos_price_change_in_24h() + "%");
                        }
                        accountWithCoinBeens.add(eos);
                        AccountWithCoinBean oct = new AccountWithCoinBean();
                        oct.setCoinName("OCT");
                        oct.setCoinForCny(RegexUtil.subZeroAndDot(response.body().data.getOct_balance_cny()));
                        oct.setCoinNumber(RegexUtil.subZeroAndDot(response.body().data.getOct_balance()));
                        oct.setCoinImg(response.body().data.getAccount_icon());
                        oct.setOct_market_cap_cny(response.body().data.getOct_market_cap_cny());
                        oct.setOct_market_cap_usd(response.body().data.getOct_market_cap_usd());
                        oct.setOct_price_cny(response.body().data.getOct_price_cny());
                        if (response.body().data.getOct_price_change_in_24h().contains("-")) {
                            oct.setCoinUpsAndDowns(response.body().data.getOct_price_change_in_24h() + "%");
                        } else {
                            oct.setCoinUpsAndDowns("+" +response.body().data.getOct_price_change_in_24h() + "%");
                        }
                        accountWithCoinBeens.add(oct);
                        view.getAccountDetailsDataHttp(accountWithCoinBeens);
                    }
                } else {
                    view.getDataHttpFail(response.body().message);
                }
            }
        });
    }
}

