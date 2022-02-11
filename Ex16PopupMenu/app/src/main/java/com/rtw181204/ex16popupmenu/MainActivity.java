package com.rtw181204.ex16popupmenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    Button btn,btn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn=findViewById(R.id.btn);
        btn2=findViewById(R.id.btn2);

        //btn에게 Long Click이벤트를 듣는 리스너를 추가
        //onClickListener 달듯이

        btn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                //PopupMenu만들기
                //Menu객체가 놓여질 수 있는 PopupMenu객체를 생성
                PopupMenu popupMenu=new PopupMenu(MainActivity.this,btn2);
                //PopupMenu객체 안에 menu객체가 존재함

                Menu menu = popupMenu.getMenu();

                //Menu객체에게 MenuItem을 추가하기
                MenuInflater inflater = getMenuInflater();

                inflater.inflate(R.menu.popup,menu);


                //팝업메뉴의 아이템 선택에 반응하기
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {


                        switch (item.getItemId()){
                            case R.id.menu_info:
                                Toast.makeText(MainActivity.this,"info",Toast.LENGTH_SHORT).show();
                                break;

                            case R.id.menu_delete:
                                Toast.makeText(MainActivity.this,"delete",Toast.LENGTH_SHORT).show();
                                break;

                            case R.id.menu_modify:
                                Toast.makeText(MainActivity.this,"modify",Toast.LENGTH_SHORT).show();
                                break;
                        }
                        return false;
                    }
                });

                popupMenu.show();

                Toast.makeText(MainActivity.this,"Long!!!!!!!!!!!!!!!",Toast.LENGTH_SHORT).show();
                //리턴값이 false면 롱클릭 후에 onClick()메소드가 자동 실행됌
                //만약 롱클릭 작업 후에 onClick()이 발동하지 않길 원한다면
                //리턴값을 true

                return true;
            }
        });
    }

    public void clickBtn(View view) {

        Toast.makeText(this,"click",Toast.LENGTH_SHORT).show();
    }
}
