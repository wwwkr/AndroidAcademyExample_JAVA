package com.rtw181204.ex45fragmentpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyAdapter extends FragmentPagerAdapter {

    Fragment[] frags = new Fragment[3];


    //생성자 메소드
    public MyAdapter(FragmentManager fm) {

        super(fm);

        frags[0] = new Page1Fragment();
        frags[1] = new Page2Fragment();
        frags[2] = new Page3Fragment();
    }



    //뷰페이저가 보여줄 Fragment를 만들어서 리턴시켜주는 메소드드
   @Override
    public Fragment getItem(int position) {


     /*   Fragment frag=null;

        switch(position){
            case 0:
                frag = new Page1Fragment();
                break;
            case 1:
                frag = new Page2Fragment();
                break;
            case 2:
                frag = new Page3Fragment();
                break;
        }
        return frag;*/
     return frags[position];
    }

    //뷰페이저가 보여줄 총 페이지수
    @Override
    public int getCount() {
        return 3;
    }
}
