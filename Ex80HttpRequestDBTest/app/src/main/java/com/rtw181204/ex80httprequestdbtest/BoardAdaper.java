package com.rtw181204.ex80httprequestdbtest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class BoardAdaper extends BaseAdapter {

    LayoutInflater inflater;
    ArrayList<BoardItem> boardItems;

    public BoardAdaper(LayoutInflater inflater, ArrayList<BoardItem> boardItems) {
        this.inflater = inflater;
        this.boardItems = boardItems;
    }

    @Override
    public int getCount() {
        return boardItems.size();
    }

    @Override
    public Object getItem(int position) {
        return boardItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        //1. create view
        if(convertView==null){//재활용할 뷰가 있는지

            convertView = inflater.inflate(R.layout.layout_item, parent,false);

        }


        //2. bind view
        TextView tvNum = convertView.findViewById(R.id.tv_num);
        TextView tvName =convertView.findViewById(R.id.tv_name);
        TextView tvMsg = convertView.findViewById(R.id.tv_msg);
        ImageView iv =convertView.findViewById(R.id.iv);
        TextView tvDate = convertView.findViewById(R.id.tv_date);

        //설정할 데이터
        BoardItem boardItem = boardItems.get(position);
        tvNum.setText(boardItem.num);
        tvName.setText(boardItem.name);
        tvMsg.setText(boardItem.message);
        tvDate.setText(boardItem.date);

        Glide.with(convertView).load(boardItem.filePath).into(iv);


        return convertView;
    }
}
