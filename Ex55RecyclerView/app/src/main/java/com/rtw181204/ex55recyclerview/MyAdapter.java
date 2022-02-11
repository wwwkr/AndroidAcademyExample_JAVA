package com.rtw181204.ex55recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter {

    ArrayList<String> datas;
    LayoutInflater inflater;

    public MyAdapter(ArrayList<String> datas, LayoutInflater inflater) {
        this.datas = datas;
        this.inflater = inflater;
    }

    //재활용할 뷰가 없으면 자동으로 호출되는 메소드
    //이 메소드 안에서 뷰를 만들어내는 작업 코딩딩
   @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {


        //항목하나의 뷰객체 생성
       View itemView = inflater.inflate(R.layout.list_item,viewGroup,false);

       //뷰홀더객체 생성
       VH holder = new VH(itemView);

       //홀더객체를 리턴
       return holder;
    }

    //현재 뷰에 아이템값들 연결하는 메소드
    //아이템 마다마다 호출됨
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        //데이터와 ViewHolder객체 멤버변수로 있는 참조변수들에 값 연결

        String data = datas.get(position);
        VH vh = (VH)viewHolder;

        vh.tv.setText(data);

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    class VH extends RecyclerView.ViewHolder {

        TextView tv;

        public VH(@NonNull View itemView) {


            super(itemView);

            tv = itemView.findViewById(R.id.tv);
        }
    }

}
