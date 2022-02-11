package com.rtw181204.test05;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    LayoutInflater inflater;
    ArrayList<Member> members;

    public MyAdapter(LayoutInflater inflater, ArrayList<Member> members) {
        this.inflater=inflater;
        this.members=members;
    }

    @Override
    public int getCount() {
        return members.size();
    }

    @Override
    public Object getItem(int position) {
        return members.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null){
            convertView=inflater.inflate(R.layout.listview_item,null);
        }

        TextView tv_name = convertView.findViewById(R.id.tv_name);
        TextView tv_nick = convertView.findViewById(R.id.tv_nick);
        TextView tv_title = convertView.findViewById(R.id.tv_title);
        TextView tv_content = convertView.findViewById(R.id.tv_content);


        Member member =members.get(position);

        tv_name.setText(member.name);
        tv_nick.setText(member.nick);
        tv_title.setText(member.title);
        tv_content.setText(member.content);



        return convertView;
    }
}
