package com.rtw181204.test07;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    String apiKey = "4f7a627855786f643130364679615346";


    ArrayList<Member> members = new ArrayList<>();


    ListView listView;
    MyAdapter adapter;

    String[] str=new String[7];


    ImageView img;

    int maxPage=11;
    int minPage=1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listView = findViewById(R.id.listview);
        LayoutInflater inflater = getLayoutInflater();


        adapter= new MyAdapter(members,inflater,this);


        listView.setAdapter(adapter);








    }

    public void clickBtn(View view) {

        new Thread(){

            Bitmap bm;
            @Override
            public void run() {

                minPage+=10;
                maxPage+=10;
                String address = "http://openapi.seoul.go.kr:8088/"+apiKey+"/xml/SearchConcertDetailService/0/"+maxPage;

                try {
                    URL url = new URL(address);

                    InputStream is = url.openStream();
                    InputStreamReader isr = new InputStreamReader(is);

                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                    XmlPullParser xpp = factory.newPullParser();

                    xpp.setInput(isr);

                    int eventType = xpp.getEventType();

                    String tagName;

                    String text;

                    String date=null;
                    String prace=null;




                    while (eventType!=XmlPullParser.END_DOCUMENT){

                        switch (eventType){

                            case XmlPullParser.START_DOCUMENT:
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(MainActivity.this, "????????????", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                break;


                            case XmlPullParser.START_TAG:
                                tagName = xpp.getName();


                                if(tagName.equals("TITLE")){

                                     xpp.next();

                                    text = xpp.getText();

                                    str[0]=text;





                                }else if(tagName.equals("STRTDATE")) {
                                  xpp.next();

                                    date = xpp.getText();
                                }
                                else if(tagName.equals("END_DATE")) {
                               xpp.next();
                                text = xpp.getText();
                                    str[1]=date + " ~ " + text;
                                }

                                else if(tagName.equals("PLACE")){
                                xpp.next();

                                    prace =xpp.getText();
                                    str[2]=prace;
                                }
                                else if(tagName.equals("TIME")){
                                xpp.next();
                                text = xpp.getText();

                                    str[3]=text;

                                }
                                else if(tagName.equals("ORG_LINK")){
                                xpp.next();


                                str[4]=xpp.getText();

                                }
                                else if(tagName.equals("INQUIRY")){
                                xpp.next();


                                str[5]=xpp.getText();


                                }
                                else if(tagName.equals("MAIN_IMG")) {

                                    xpp.next();



                                    str[6]=xpp.getText();



                                }

                                break;

                            case XmlPullParser.TEXT:

                                break;

                            case XmlPullParser.END_TAG:

                                tagName=xpp.getName();



                                ////

                            if(tagName.equals("row")){
               //????????? ????????? ???????????? ?????????????????? ?????????



                                Member member = new Member(str[0],str[1],str[2],str[3],str[4],str[5],str[6]);
                                members.add(member);

                                new Thread() {
                                    @Override
                                    public void run() {

                                        //?????????????????? ???????????????(URL)

                                        //?????? ??????????????? ????????? ??????(Stream)??? ???????????? ??????
                                        //?????????(URL) ?????? ??????


                                        try {
                                            URL url = new URL(str[6]);

                                            //?????????(url)??? ?????? ???????????????(Stream) ????????????
                                            InputStream is = url.openStream();

                                            //???????????? ?????? ???????????? ???????????? Bitmap????????? ??????
                                            bm = BitmapFactory.decodeStream(is);
                                            //?????? ??????????????? ??????

                                          runOnUiThread(new Runnable() {
                                              @Override
                                              public void run() {
                                                  img.setImageBitmap(bm);
                                                  adapter.notifyDataSetChanged();
                                              }
                                          });





                                        } catch (MalformedURLException e) {


                                        } catch (IOException e) {

                                        }

                                    }//run method
                                };
                                //???????????? ????????????
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapter.notifyDataSetChanged();
                                    }
                                });

                            }


                        }

                        xpp.next();
                        eventType=xpp.getEventType();
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "?????????!!!", Toast.LENGTH_SHORT).show();
                        }
                    });


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });

    }
}
