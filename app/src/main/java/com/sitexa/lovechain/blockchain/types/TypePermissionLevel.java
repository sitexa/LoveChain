package com.sitexa.lovechain.blockchain.types;

import com.google.gson.annotations.Expose;


/**
 * Created by sitexa on 2018/6/19.
 */


public class TypePermissionLevel implements EosType.Packer {

    @Expose
    private TypeAccountName actor;

    @Expose
    private TypePermissionName permission;

    public TypePermissionLevel(String accountName, String permissionName) {
        actor = new TypeAccountName(accountName);
        permission = new TypePermissionName(permissionName);
    }

    public String getAccount(){
        return actor.toString();
    }

    public void setAccount(String accountName ){
        actor = new TypeAccountName(accountName);
    }

    public String getPermission(){
        return permission.toString();
    }

    public void setPermission(String permissionName ){
        permission = new TypePermissionName(permissionName);
    }

    @Override
    public void pack(EosType.Writer writer) {

        actor.pack(writer);
        permission.pack(writer);
    }
}
