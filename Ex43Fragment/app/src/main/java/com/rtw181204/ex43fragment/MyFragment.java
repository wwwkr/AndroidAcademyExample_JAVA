package com.rtw181204.ex43fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MyFragment extends Fragment {

    TextView tv;
    Button btn,btn2;

    //이 프레그먼트가 보여줄 View를 만들어서 리턴시키는 메소드
    //화면에 보일때 자동으로 실행되는 메소드
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //보여줄 뷰를 만들어서 리턴


        //layout폴더안에 fragment_my.xml의 모양으로
        //View객체 생성(부풀리다 inflate)해주는 객체(첫번째 파라미터)에게 요청
        View view =  inflater.inflate(R.layout.fragment_my,container,false);

        //만들어진 View안에 있는 텍스트뷰와 버튼객체를 참조하기기
        tv= view.findViewById(R.id.myfragment_tv);
        btn= view.findViewById(R.id.myfragment_btn);
        btn2= view.findViewById(R.id.myfragment_btn2);

        //버튼에 리스너 추가하기
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("Hello android");
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MainActivity의 TextView(tv)를 제어하기
                MainActivity ma = (MainActivity)getActivity();

                ma.tv.setText("Nice World");
            }
        });

        //위에서 만들어진 View객체를 액티비티에게 리턴시키기
        return view;
    }

    //프레그먼트는 버튼의 onClick속성에 반응하지 못함
    //프레그먼트안에 있는 버튼은 onClick속성으로 클릭이벤트를 처리하지 않음
    //public void clickBtn2(View v){ }

}
