package com.sitexa.lovechain.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sitexa on 2018/6/19.
 */

public class WalletDetailsBean {

    /**
     * code : 0
     * message : ok
     * data : [{"eosAccountName":"1589","isMainAccount":1}]
     */

    private String code;
    private String message;
    private List<DataBean> data;

    public String getCode() {
        return code == null ? "" : code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message == null ? "" : message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        if (data == null) {
            return new ArrayList<>();
        }
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * eosAccountName : 1589
         * isMainAccount : 1
         */

        private String eosAccountName;
        private int isMainAccount;

        public String getEosAccountName() {
            return eosAccountName == null ? "" : eosAccountName;
        }

        public void setEosAccountName(String eosAccountName) {
            this.eosAccountName = eosAccountName;
        }

        public int getIsMainAccount() {
            return isMainAccount;
        }

        public void setIsMainAccount(int isMainAccount) {
            this.isMainAccount = isMainAccount;
        }
    }
}
