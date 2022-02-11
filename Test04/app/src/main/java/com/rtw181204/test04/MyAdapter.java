package com.rtw181204.test04;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    ArrayList<Member> member;
    LayoutInflater inflater;

    public MyAdapter(ArrayList<Member> member,LayoutInflater inflater) {

        this.member=member;
        this.inflater= inflater;

    }

    @Override
    public int getCount() {
        return member.size();
    }

    @Override
    public Object getItem(int position) {
        return member.get(position);
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


        TextView listitem_tv01= convertView.findViewById(R.id.listitem_tv01);
        TextView listitem_tv02=convertView.findViewById(R.id.listitem_tv02);
        ImageView nationId =convertView.findViewById(R.id.nationId);
        ImageView imgId = convertView.findViewById(R.id.imgId);


        Member members =member.get(position);

        listitem_tv01.setText(members.name);
        listitem_tv02.setText(members.nation);
        nationId.setImageResource(members.nationId);
        imgId.setImageResource(members.imgId);



        return convertView;
    }
}
