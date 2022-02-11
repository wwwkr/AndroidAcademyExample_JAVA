package com.rtw181204.test12;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    ArrayList<String> items;
    LayoutInflater inflater;

    public MyAdapter(ArrayList<String> items, LayoutInflater inflater) {
        this.items = items;
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null){
            convertView = inflater.inflate(R.layout.list_item,parent,false);

            ViewHolder holder = new ViewHolder(convertView);

            convertView.setTag(holder);
        }

        String data = items.get(position);

        ViewHolder holder = (ViewHolder)convertView.getTag();
        holder.tv.setText(data);


        return convertView;
    }

    class ViewHolder{

        TextView tv;

        public ViewHolder(View itemView) {

            tv = itemView.findViewById(R.id.tv);
        }
    }
}
