package com.rtw181204.test09;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_my,container,false);

        tv= view.findViewById(R.id.Myfragment_tv);
        btn = view.findViewById(R.id.Myfragment_btn);
        btn2= view.findViewById(R.id.Myfragment_btn2);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("Hello world!!!!");
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity ma=(MainActivity)getActivity();

                ma.tv.setText("BBBBBBBBBB");
            }
        });




        return view;
    }
}
