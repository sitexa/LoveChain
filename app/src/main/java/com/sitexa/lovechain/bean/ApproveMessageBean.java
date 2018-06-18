package com.sitexa.lovechain.bean;

/**
 * Created by sitexa on 2018/6/19.
 */

public class ApproveMessageBean {

    /**
     * owner : askera
     * spender : ocaskans
     * quantity : 2.0000 OCT
     */

    private String owner;
    private String spender;
    private String quantity;

    public ApproveMessageBean(String owner, String spender, String quantity) {
        this.owner = owner;
        this.spender = spender;
        this.quantity = quantity;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getSpender() {
        return spender;
    }

    public void setSpender(String spender) {
        this.spender = spender;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
