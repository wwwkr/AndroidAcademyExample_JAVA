package com.rtw181204.test10;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Page3Fragment extends Fragment {


    ArrayList<String> datas= new ArrayList<>();
    ListView listView;
    ArrayAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        datas.add("AAA");
        datas.add("BBB");
        datas.add("CCC");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


       View view = inflater.inflate(R.layout.fragment_page3,container,false);


       listView = view.findViewById(R.id.listview);
       adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,datas);

       listView.setAdapter(adapter);
        return view;
    }
}
