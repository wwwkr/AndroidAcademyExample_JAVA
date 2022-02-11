package com.mrhi2018.ex60rssfeed;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //대량의 데이터들
    ArrayList<Item> items= new ArrayList<>();

    RecyclerView recyclerView;
    MyAdapter adapter;

    SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //네트워크를 통해서 데이터들을 읽어오기!!
        readRss();

        recyclerView= findViewById(R.id.recycler);
        adapter= new MyAdapter(items, this);
        recyclerView.setAdapter(adapter);

        //리프레시레이아웃 갱신하도록!!
        refreshLayout= findViewById(R.id.layout_refresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            //스크롤을 맨 위로 당겨서..뱅글뱅글 도는 아이콘 나올때
            //자동 호출
            @Override
            public void onRefresh() {
                items.clear();
                readRss();
            }
        });

    }

    //데이터 읽어오는 작업 메소드
    void readRss(){
        //네트워크작업은 퍼미션 필요함.

        try {
            URL url= new URL("http://rss.hankyung.com/new/news_main.xml");
            url= new URL("https://rss.blog.naver.com/syokolla.xml");

            //네트워크작업은 오래걸리는 작업으로 생각함..
            //반드시 네트워크작업은 별도의 Thread만이 해야함.!!!
            RssFeedTask task= new RssFeedTask();
            //doInBackground()를 실행하는 명령
            task.execute(url); //Thread의 start()같은 역할



        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }//readRss() Method...

    //RSS XML문서를 네트워크로 읽어오는 작업 스레드 이너클래스
    class RssFeedTask extends AsyncTask<URL, Void, String>{

        //이 메소드가 Thread의 run()메소드 같은 역할!!
        //이 메소드안에서만 네트워크 작업을 해야 함.
        //이 메소드안에서는 UI변경작업을 할 수 없음.
        @Override
        protected String doInBackground(URL... urls) {

            //전달받은 URL객체 참조
            URL url= urls[0];

            //무지개로드(Stream)
            try {
                InputStream is= url.openStream();

                //XML을 파싱해주는 객체 생성
                XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
                XmlPullParser xpp= factory.newPullParser();

                //무지개로드를 통해서 XML문서 읽어오기
                //한글도 읽기위한 "utf-8" 인코딩방식 적용
                xpp.setInput(is, "utf-8");

                String tagName= null;
                Item item= null;

                int eventType= xpp.next();
                while ( eventType != XmlPullParser.END_DOCUMENT ){

                    switch (eventType){
                        case XmlPullParser.START_TAG:
                            tagName= xpp.getName();

                            if( tagName.equals("item")){
                                item= new Item();
                            }else if( tagName.equals("title")){
                                xpp.next();
                                if(item!=null) item.setTitle(xpp.getText());
                            }else if( tagName.equals("link")){
                                xpp.next();
                                if(item!=null) item.setLink(xpp.getText());
                            }else if( tagName.equals("description")){
                                xpp.next();
                                if(item!=null) item.setDesc(xpp.getText());
                            }else if( tagName.equals("image")){
                                xpp.next();
                                if(item!=null) item.setImage(xpp.getText());
                            }else if( tagName.equals("pubDate")){
                                xpp.next();
                                if(item!=null) item.setDate(xpp.getText());
                            }
                            break;

                        case XmlPullParser.TEXT:
                            break;

                        case XmlPullParser.END_TAG:
                            tagName= xpp.getName();
                            if(tagName.equals("item")){
                                //기사 하나의 데이터를 완성

                                //ArrayList에 추가
                                items.add(item);
                                item=null;

                                //스레드의 작업 중간중간
                                //UI갱신 작업이 필요하다면!!
                                publishProgress();

                                //억지로 느리게 하기 위해 스레드를 잠시 재우기!!!
//                                try {
//                                    Thread.sleep(1500);
//                                } catch (InterruptedException e) {
//                                    e.printStackTrace();
//                                }

                            }
                            break;
                    }

                    eventType= xpp.next();
                }//while..


            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }

            //파싱종료!!
            //작업완료된 내용을 화면에 띄어주는 작업!!!

            return "파싱종료";
        }//doInBackground()


        //publishProgress()메소들 실행하면
        //자동으로 호출되는 메소드...이 메소드 UI작업 가능
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            //리사이클러의 갱신작업
            adapter.notifyItemInserted(items.size());
        }

        //doInBackground()메소드가 종료된 후
        //UI변경 작업을 수행하기 위해 마련된 콜백 메소드(자동 호출)
        //이 메소드 안에서는 .UI변경작업이 가능함.
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //파라미터 : doInBackground()의 리턴값
            Toast.makeText(MainActivity.this, ""+s, Toast.LENGTH_SHORT).show();

            //리프레시레이아웃의 뱅글뱅글 아이콘 제거
            refreshLayout.setRefreshing(false);

            //리사이클러뷰의 화면 갱신!!!
            //adapter.notifyDataSetChanged();


            //파싱성공여부를 확인하기 위해
//            StringBuffer buffer= new StringBuffer();
//            for(Item t : items){
//                String str= t.getTitle();
//                buffer.append(str+"\n\n");
//            }

            //new AlertDialog.Builder(MainActivity.this).setMessage(buffer.toString()).create().show();

        }
    }//RssFeedTask class...

}//MainActivity class...










