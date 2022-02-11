package com.rtw181204.test;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class MyAdapter extends BaseAdapter {

    ArrayList<Member> members;
    LayoutInflater inflater;
    public MyAdapter(ArrayList<Member> members, LayoutInflater inflater) {

        this.members=members;
        this.inflater=inflater;

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

            convertView= inflater.inflate(R.layout.listview_item,null);
        }


        ImageView listitem_iv= convertView.findViewById(R.id.listitem_iv);
        TextView listitem_tv01=convertView.findViewById(R.id.listitem_tv01);
        TextView listitem_tv02=convertView.findViewById(R.id.listitem_tv02);


        Member member =members.get(position);

        listitem_iv.setImageResource(member.imgId);
        listitem_tv01.setText(member.name);
        listitem_tv02.setText(member.nation);

        return convertView;
    }
}
