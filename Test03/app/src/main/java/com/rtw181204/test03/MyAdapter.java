package com.rtw181204.test03;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    ArrayList<Member> members;
    LayoutInflater inflater;

    public MyAdapter(ArrayList<Member> members,LayoutInflater inflater) {
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
            convertView = inflater.inflate(R.layout.listitem,null);
        }

        ImageView image = convertView.findViewById(R.id.imageId);
        TextView title = convertView.findViewById(R.id.title);
        TextView rating = convertView.findViewById(R.id.rating);
        TextView genre = convertView.findViewById(R.id.genre);
        TextView releaseYea = convertView.findViewById(R.id.releaseYear);

        Member member =members.get(position);

        image.setImageResource(member.imageId);
        title.setText(member.title);
        rating.setText(member.rating);
        genre.setText(member.genre);
        releaseYea.setText(member.releaseYear);
        return convertView;
    }
}
