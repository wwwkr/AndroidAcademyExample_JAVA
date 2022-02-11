package com.rtw181204.ex42viewpager;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MyAdapter extends PagerAdapter {

    int[] datas;
    LayoutInflater inflater;

    public MyAdapter(int[] datas, LayoutInflater inflater) {
        this.datas = datas;
        this.inflater = inflater;
    }

    //ViewPager에 보여줄 View를 만들어내는 작업 메소드
    //ViewPager에 의해서 자동으로 호출되는 메소드
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        //첫번째 파라미터(container) : ViewPager참조변수
        //두번째 파라미터(position) : 페이지번호(0부터)

        //layout폴더에 있는 page.xml라는 설계도면으로
        //View객체를 생성
        //xml문서를 View객체로 만들어주는 (부풀리는 inflate) 객체에게 요청
        View view = inflater.inflate(R.layout.page,null);

        //xml도면대로 만들어진 페이지(view)의 Contents 연결하기
        ImageView iv = view.findViewById(R.id.iv);
        iv.setImageResource(datas[position]);


        //만들어진 페이지(view)를 ViewPager에 추가하기
        container.addView(view);

        //만들어진 뷰가 ViewPager의 뷰에 맞는지 검증하도록 리턴
        return view;
    }

    //뷰페이저가 보여줄 View와 instantiateItem()메소드의 리턴된 뷰가 같은지 검증하는 메소드
   @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object obj) {
        return view ==obj;
    }

    //ViewPager가 필요없다고 생각하는 뷰를 없애는 메소드
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        //세번째 파라미터 : 제거대상이 된 View객체(object)
        container.removeView((View)object);
    }

    @Override
    public int getCount() {
        return datas.length;
    }





}
