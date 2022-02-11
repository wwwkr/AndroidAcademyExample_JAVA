package com.rtw181204.test10;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Page1Fragment extends Fragment {


    TextView tv;
    Button btn;
    String savedData =null;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


       View view = inflater.inflate(R.layout.fragment_page1,container,false);

       tv = view.findViewById(R.id.tv);
       if(savedData!=null){
           tv.setText(savedData);
       }
       btn = view.findViewById(R.id.btn);

       btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               savedData="AAAAAAAAA";
               tv.setText(savedData);
           }
       });
        return view;
    }
}
