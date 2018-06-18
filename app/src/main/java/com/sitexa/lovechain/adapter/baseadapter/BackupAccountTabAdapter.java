package com.sitexa.lovechain.adapter.baseadapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sitexa.lovechain.modules.account.backupaccount.fragment.BackupAccountFragment;

import java.util.List;


public class BackupAccountTabAdapter extends FragmentPagerAdapter {

    private List<BackupAccountFragment> list_fragment;                         //fragment列表
    private List<String> list_Title;                              //tab名的列表


    public BackupAccountTabAdapter(FragmentManager fm, List<BackupAccountFragment> list_fragment, List<String> list_Title ) {
        super(fm);
        this.list_fragment = list_fragment;
        this.list_Title = list_Title;
    }

    @Override
    public Fragment getItem(int position) {
        return list_fragment.get(position);
    }

    @Override
    public int getCount() {
        return list_Title.size();
    }

    //此方法用来显示tab上的名字
    @Override
    public CharSequence getPageTitle(int position) {

        return list_Title.get(position % list_Title.size());
    }
}