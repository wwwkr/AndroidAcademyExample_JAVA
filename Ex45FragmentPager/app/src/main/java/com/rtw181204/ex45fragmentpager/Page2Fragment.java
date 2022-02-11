package com.rtw181204.ex45fragmentpager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class Page2Fragment extends Fragment {


    Button btn;
    ImageView iv;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         return inflater.inflate(R.layout.fragment_page2,container,false);
    }

    //onCreateView()메소드가 종료도니 후에 자동으로 실행되는 메소드


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //첫번째 매개변수 : 이 프레그먼트가 보여줄 View(위에서 리턴시킨 View)
        btn= view.findViewById(R.id.btn);
        iv = view.findViewById(R.id.iv);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv.setImageResource(R.mipmap.ic_launcher_round);
            }
        });
    }
}
