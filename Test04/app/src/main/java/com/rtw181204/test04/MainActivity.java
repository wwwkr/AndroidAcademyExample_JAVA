package com.rtw181204.test04;

import android.app.AlertDialog;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    Spinner spinner;
    ArrayAdapter adapter;
    ListView listView;

    MyAdapter myAdapter;
    ArrayList<Member> member = new ArrayList<Member>();
    TextView nodata;


    LinearLayout ll01;
    //////
    Button menu_btn01,menu_btn02;
    EditText menu_et01;
    RadioGroup menu_radio;




    ///


    android.support.v7.widget.SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView=findViewById(R.id.listview);


        ll01= findViewById(R.id.ll01);



        LayoutInflater inflater = getLayoutInflater();

        myAdapter = new MyAdapter(member,inflater);

        nodata=findViewById(R.id.nodata);

        listView.setEmptyView(nodata);


        listView.setAdapter(myAdapter);



        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                PopupMenu popupMenu = new PopupMenu(MainActivity.this,listView);



                Menu menu = popupMenu.getMenu();

                //Menu???????????? MenuItem??? ????????????
                MenuInflater inflater = getMenuInflater();

                inflater.inflate(R.menu.popup,menu);


                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {


                        switch (item.getItemId()){
                            case R.id.menu_info:


                                break;

                            case R.id.menu_delete:

                                member.remove(position);
                                myAdapter.notifyDataSetChanged();
                                break;

                            case R.id.menu_modify:

                                break;
                        }
                        return false;
                    }
                });

                popupMenu.show();

                return true;
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {



        getMenuInflater().inflate(R.menu.actionbar, menu);

        //menu????????? ?????? SearchView??? ????????? ??????
        // MenuItem????????? ????????????!!
        MenuItem item= menu.findItem(R.id.menu_search);
        // MenuItem?????? ?????? actionViewClass??? ?????????
        // SearchView??? ????????????
        searchView= (android.support.v7.widget.SearchView)item.getActionView();

        //?????? ????????????
        searchView.setQueryHint("Search Name");

        //???????????? ????????? ????????? ????????????...
        //???????????? ????????? ???????????? ?????? ???????????? ???????????? ?????????
        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            //???????????? ?????? ????????? ??? ???????????? ????????????????????? ??????????????????
            //'??????' ????????? ??????... ????????? ????????? ???????????? ?????????..
            @Override
            public boolean onQueryTextSubmit(String s) {

                for(int i =0 ; i <member.size();i++){
                    if(s.equals(member.get(i).name)){



                        myAdapter.notifyDataSetChanged();

                        //listView.smoothScrollToPosition(i);
                        listView.smoothScrollByOffset(i);



                        Toast.makeText(MainActivity.this, s+" ??? ???????????????.", Toast.LENGTH_SHORT).show();



                    }
                }



                //????????? ?????? ?????????
                searchView.setQuery("", true);
                searchView.setIconified(true);//????????????????????? ?????????
                return false;
            }

            //????????? ????????? ?????? ???????????? ?????????..
            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);

    }


    //???????????? ???????????????
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {




        switch (item.getItemId()){

            case R.id.menu_search:





                break;

            case R.id.menu_add:



                myAdapter.notifyDataSetChanged();
                nodata.setVisibility(View.GONE);
                listView.setVisibility(View.GONE);
                ll01.setVisibility(View.VISIBLE);

                spinner=findViewById(R.id.spinner);
                adapter=ArrayAdapter.createFromResource(this,R.array.nation,android.R.layout.simple_spinner_item);

                spinner.setAdapter(adapter);


                menu_btn01 = findViewById(R.id.menu_btn01);
                menu_btn02 = findViewById(R.id.menu_btn02);

                menu_et01 = findViewById(R.id.menu_et01);
                menu_radio = findViewById(R.id.menu_radio);

                menu_et01.setText("");


                menu_btn01.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        if(member.size()!=0)
                            nodata.setVisibility(View.GONE);



                        String name = menu_et01.getText()+"";
                        int checkid = menu_radio.getCheckedRadioButtonId();
                        RadioButton rbtn  = findViewById(checkid);
                        String nation = rbtn.getText()+"";

                        int spinner_num =spinner.getSelectedItemPosition();



                        int img_num=0;

                       if(rbtn.getText().equals("MALE")){

                           img_num = R.drawable.z_02;
                       }

                       else {
                           img_num=R.drawable.z_01;
                       }


                        member.add(0,new Member(name,nation,R.drawable.img_australia+spinner_num,img_num));

                        ll01.setVisibility(View.GONE);
                        if(member.size()!=0)

                        listView.setVisibility(View.VISIBLE);

                    }
                });

                menu_btn02.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ll01.setVisibility(View.GONE);
                        if(member.size()==0)
                            nodata.setVisibility(View.VISIBLE);
                        listView.setVisibility(View.VISIBLE);

                    }
                });
                break;
        }


        return super.onOptionsItemSelected(item);
    }
}
