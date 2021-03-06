package com.rtw181204.test07;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    ArrayList<Member> members;



    LayoutInflater inflater;

    String imgUrl;
    ImageView img;
    Bitmap bm;
    Context context;

    View view ;
    public MyAdapter(ArrayList<Member> members, LayoutInflater inflater ,Context context) {

        this.members = members;
        this.inflater = inflater;
        this.context = context;

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


        TextView titleB=convertView.findViewById(R.id.titleB);
        TextView dateB =convertView.findViewById(R.id.dateB);
        TextView praceB=convertView.findViewById(R.id.praceB);
        TextView timeB = convertView.findViewById(R.id.timeB);
        TextView rinkB=convertView.findViewById(R.id.rinkB);
        TextView numberB=convertView.findViewById(R.id.numberB);
        img = convertView.findViewById(R.id.img);


        Member member = members.get(position);

        titleB.setText(member.title);
        dateB.setText(member.date);
        praceB.setText(member.prace);
        timeB.setText(member.time);
        rinkB.setText(member.rink);
        numberB.setText(member.number);

        imgUrl = member.img;

        imgUrl.toLowerCase();
        view = convertView;

        new Thread() {


            @Override
            public void run() {

                //?????????????????? ???????????????(URL)

                //?????? ??????????????? ????????? ??????(Stream)??? ???????????? ??????
                //?????????(URL) ?????? ??????



                try {
                    URL url = new URL(imgUrl);

                    //?????????(url)??? ?????? ???????????????(Stream) ????????????
                    InputStream is = url.openStream();

                    //???????????? ?????? ???????????? ???????????? Bitmap????????? ??????
                    bm = BitmapFactory.decodeStream(is);
                    //?????? ??????????????? ??????

                    view.post(new Runnable() {
                        @Override
                        public void run() {

                            img.setImageBitmap(bm);
                        }
                    });








                } catch (MalformedURLException e) {


                } catch (IOException e) {

                }


            }//run method
        }.start();



        return convertView;
    }
}
