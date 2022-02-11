package com.rtw181204.test10;

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


       View view = inflater.inflate(R.layout.fragment_page2,container,false);

       btn = view.findViewById(R.id.btn);
       iv = view.findViewById(R.id.iv);

       btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               iv.setImageResource(R.mipmap.ic_launcher_round);
           }
       });
        return view;
    }
}
