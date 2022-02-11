package com.rtw181204.ex50appbartablayout;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyAdapter extends FragmentPagerAdapter {

    Fragment[] frags = new Fragment[3];
    String[] titles = new String[]{"Home","Theme","Talk"};
    public MyAdapter(FragmentManager fm) {
        super(fm);

        frags[0] = new Page1Fragment();
        frags[1] = new Page2Fragment();
        frags[2] = new Page3Fragment();

    }

    @Override
    public Fragment getItem(int position) {
        return frags[position];
    }

    @Override
    public int getCount() {
        return frags.length;
    }

    //뷰페이저와 연동된 TabLayout의 Tab버튼 글씨 적용 메소드
    //뷰페이저의 페이지별 제목을 리턴해주는 메소드


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {


        return titles[position];
    }
}
