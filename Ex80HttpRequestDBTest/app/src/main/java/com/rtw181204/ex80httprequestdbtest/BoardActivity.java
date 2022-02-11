package com.rtw181204.ex80httprequestdbtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class BoardActivity extends AppCompatActivity {

    ListView lv;

    ArrayList<BoardItem> boardItems = new ArrayList<>();

    BoardAdaper boardAdaper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        //대량의 데이터 .. 서버로부터 읽어오기
        loadDataFromServer();

        lv = findViewById(R.id.lv);
        boardAdaper = new BoardAdaper(getLayoutInflater(),boardItems);
        lv.setAdapter(boardAdaper);


    }

    void loadDataFromServer(){
        //서버로부터 데이터 읽어오기
        String serverUrl = "http://wwwkr.dothome.co.kr/Android/loadDB.php";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, serverUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //매개변수 response가 echo된 문자열 결과

                //받은 데이터에서 각각의 값들 분리해내기
                String[] rows =response.split(";");

                for(String row :rows){
                    //한줄에서 각 칸들 분리
                    String[] datas = row.split("&");

                    if(datas.length!=5) continue;

                    //분리된 데이터들 얻어오기
                    String num = datas[0];
                    String name = datas[1];
                    String msg = datas[2];
                    String filePath = "http://wwwkr.dothome.co.kr/Android/"+datas[3];
                    String date = datas[4];

                    //어레이리스트에 추가
                    boardItems.add(new BoardItem(num, name, msg, filePath, date));

                    //리스트뷰갱신
                    boardAdaper.notifyDataSetChanged();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(BoardActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }
}
