package com.rtw181204.ex44fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AnalogClock;
import android.widget.Toast;

public class MyFragment extends Fragment {
    //이 프레그먼트가 보여줄 뷰를 만들어서 리턴시키는 작업 메소드
    //프레그먼트가 보여질 때 자동으로 실행되는 메소드 : lifecycle 메소드

    AnalogClock ac;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        //혹시 setArguments로 전달된 값이 있다면
        Bundle bundle = getArguments();

        if(bundle!=null){
            String name = bundle.getString("Name","no name");
            int age = bundle.getInt("Age",0);

            Toast.makeText(getActivity(),name+" , "+age,Toast.LENGTH_SHORT).show();
        }
        //layout폴더에 있는 fragment_my.xml문서를 읽어서
        //View객체로 만들어주는(부풀려주는) 객체에게 만들어달라고 요청
        View view = inflater.inflate(R.layout.fragment_my,container,false);



        ac= view.findViewById(R.id.ac);


        //만들어진 VIew객체를 리턴하면 Activity에서 보여줌
        return view;


    }
}
