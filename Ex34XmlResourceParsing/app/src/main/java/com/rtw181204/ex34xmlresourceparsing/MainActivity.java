package com.rtw181204.ex34xmlresourceparsing;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayAdapter<String> adapter;
    ArrayList<String> datas = new ArrayList<>();


    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        listView = findViewById(R.id.listview);

        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,datas);

        listView.setAdapter(adapter);


    }

    public void clickBtn(View view) {

        datas.clear();

        //res폴더에 있는 xml문서를 읽어서
        //이를 분석(parse)하는 작업 수행

        //res창고관리자 객체 소환
        Resources res = getResources();

        //창고관리자에게 xml파서객체 얻어오기
        XmlResourceParser xrp = res.getXml(R.xml.movies);

        try {
            xrp.next();

            int eventType = xrp.getEventType();

            String tagName=null; //태그문 이름

            String data=null;
            String text=null;//태그문 사이의 글씨

            while (eventType!=XmlResourceParser.END_DOCUMENT){


                xrp.next();
                eventType= xrp.getEventType();

                switch (eventType){

                    case XmlResourceParser.START_DOCUMENT:
                        Toast.makeText(this,"파싱을 시작합니다.",Toast.LENGTH_SHORT).show();
                        break;

                    case XmlResourceParser.START_TAG:
                        tagName =xrp.getName();

                        if(tagName.equals("item")){
                            data="";
                        }else if(tagName.equals("no")){
                            data += "번호 : ";
                        }else if(tagName.equals("title")){
                            data += "제목 : ";
                        } else if (tagName.equals("genre")) {
                            data += "장르 : ";
                        }
                        break;

                    case XmlResourceParser.TEXT:
                        text = xrp.getText();
                        data += text +"\n";
                        break;

                    case XmlResourceParser.END_TAG:
                        tagName= xrp.getName();

                        if(tagName.equals("item")){
                            //하나의 아이템이 완성
                            //완성된 하나의 아이템을
                            //ArrayList에 추가가
                            datas.add(data);
                            //리스트뷰 화면 갱신
                            adapter.notifyDataSetChanged();
                        }
                        break;

                }
            }



            Toast.makeText(this,"파싱을 종료합니다.",Toast.LENGTH_SHORT).show();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }


    }
}
