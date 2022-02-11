package com.rtw181204.ex82jsonhttprequest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter {

    ArrayList<ItemVO> items;
    Context context;

    public ItemAdapter(ArrayList<ItemVO> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.recycler_item, viewGroup,false);

        VH holder = new VH(itemView);


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        VH vh = (VH)viewHolder;

        //값들 연결
        //현재번째(position)번째
        ItemVO item = items.get(position);

        vh.tvName.setText(item.getName());
        vh.tvMsg.setText(item.getMsg());
        vh.tvDate.setText(item.getDate());

        Glide.with(context).load(item.getFilePath()).into(vh.iv);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    //ViewHolder클래스 설계
    class VH extends RecyclerView.ViewHolder{

        TextView tvName;
        TextView tvMsg;
        TextView tvDate;
        ImageView iv;

        public VH(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name);
            tvMsg = itemView.findViewById(R.id.tv_msg);
            tvDate = itemView.findViewById(R.id.tv_date);
            iv = itemView.findViewById(R.id.iv);
        }
    }
}
