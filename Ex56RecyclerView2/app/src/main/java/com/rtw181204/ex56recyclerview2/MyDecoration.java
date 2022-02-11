package com.rtw181204.ex56recyclerview2;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class MyDecoration extends RecyclerView.ItemDecoration {


    //리사이클러뷰의 배경그림 그리기
    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    //리사이클러뷰의 전경그림(아이템뷰 위에 그려짐)
    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);


        if(parent.getLayoutManager() instanceof GridLayoutManager){

            //현재 아이템의 인덱스번호 얻어오기
            int index = parent.getChildAdapterPosition(view);

            if(index%2==0){//짝수번째

                outRect.set(0,0,30,0);

            }
        }
    }
}
