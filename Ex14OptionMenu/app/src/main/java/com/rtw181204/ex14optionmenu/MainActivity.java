package com.rtw181204.ex14optionmenu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    final int MENU_AA = 1;
    final int MENU_BB = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //onCreate()메소드가 실행된 후
    //자동으로 OptionMenu를 만드는 작업을 하는
    //메소드가 하나 호출됌


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

     /*   //여기서 메뉴아이템 추가작업을 작성
        menu.add(0,MENU_AA,0,"aa");
        menu.add(0,MENU_BB,1,"bb");

        //만약 아이콘을 넣고 싶다면
        //위에서 add()통해 자동으로 생성된 MenuItom객체를 따로 얻어와야함
        MenuItem item = menu.getItem(0);
        item.setIcon(R.drawable.ic_launcher_foreground);*/

        //이런식으로 자바언어에서 MenuItem을 만들어 추가하는 방식은 코드가 지저분함

        //그래서 메뉴아이템들은 xml언어를 이용해 설계하고자 함

        //res폴더 안에 menu폴더안에 있는 actionbar.xml문서를
        //자바에서의 객체로 생성(부풀리기 : inflate)시켜주는 능력을 가진 객체를 얻어와야됌

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar,menu);


       return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //선택된 메뉴항목(MenuItem)에 따라 동작 수행



        int ID= item.getItemId();

        switch (ID){
            case R.id.menu_search:
                Toast.makeText(this,"search",Toast.LENGTH_SHORT).show();

                break;

            case R.id.menu_add:
                Toast.makeText(this,"add",Toast.LENGTH_SHORT).show();
                break;

            case R.id.menu_helf:
                Toast.makeText(this,"helf",Toast.LENGTH_SHORT).show();
                break;
        }



        return super.onOptionsItemSelected(item);
    }

    //OptionMenu의 메뉴항목(MenuItem)을 선택헀을 때
    //자동으로 실행되는 콜백메소드가 이미 Activity에 존재함
}
