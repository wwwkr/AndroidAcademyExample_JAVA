package com.rtw181204.ex40datastoragedatabase;

import android.app.AlertDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText etName, etAge, etEmail;

    //하나의 DB파일안에 테이블이 여러개 있을 수 있음.
    String dbName = "test.db";//DB파일이름
    String tableName = "member";//테이블(표)이름

    //데이터베이스파일을 제어하는 객체 참조변수
    SQLiteDatabase db;



   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.et_name);
        etAge = findViewById(R.id.et_age);
        etEmail= findViewById(R.id.et_email);

        //dbName(test.db)으로 데이터베이스 파일 생성 또는 열기
        //Database파일을 제어할 수 있는 객체를 리턴시켜줌
        //이 객체를 이용하여 여러 작업(insert, select, update, delete)수행 가능
        db = openOrCreateDatabase(dbName,MODE_PRIVATE,null);

        //일단 DB파일안에 테이블(표) 생성 작업 실행
        //DB에 요청하는 명령을 SQL(구조화된 질의 언어)이라고 함.
        db.execSQL("CREATE TABLE IF NOT EXISTS "+tableName+"(num integer primary key autoincrement, name text not null, age integer, email text);");


    }
    public void clickInsert(View view) {


       String name = etName.getText().toString();
       int age = Integer.parseInt(etAge.getText().toString());
       String email = etEmail.getText().toString();

       //테이블에 데이터 삽입(insert)시키는 SQL문
        db.execSQL("INSERT INTO "+tableName+"(name, age, email) VALUES('"+name+"','"+age+"','"+email+"');");


        etName.setText("");
        etAge.setText("");
        etEmail.setText("");
    }


    public void clickSelectAll(View view) {

       //DB에서 데이터를 가져오기 요청(Query:질의)
         Cursor cursor = db.rawQuery("SELECT * FROM "+tableName,null);

        //질의에 의한 결과표(테이블)을 가지고 있는 객체 Cursor
        if(cursor==null){
            return;
        }

        //총 몇줄(row)의 데이터가 있는지
        int rowCnt = cursor.getCount();

        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()){//커서를 다음칸으로 이동

            //현재 커서의 위치(row위치)에서 데이터를 얻어오기
            int num = cursor.getInt(0);
            String name = cursor.getString(1);
            int age = cursor.getInt(2);
            String email = cursor.getString(3);

            buffer.append(num+ " " + name + " "+ age + " "+ email+"\n");

        }//while

        //저장될 글씨들을 출력

        new AlertDialog.Builder(this).setMessage(buffer.toString()).create().show();


    }

    public void clickSelectByName(View view) {

       String name = etName.getText().toString();

       Cursor cursor = db.rawQuery("SELECT name, age FROM "+tableName+" WHERE name=?",new String[]{name});

       if(cursor==null) return;

       StringBuffer buffer = new StringBuffer();

       while(cursor.moveToNext()){

           String name2=cursor.getString(cursor.getColumnIndex("name"));
           int age =  cursor.getInt(cursor.getColumnIndex("age"));

           buffer.append(name2 + " " + age + " \n");
       }

       new AlertDialog.Builder(this).setMessage(buffer.toString()).create().show();

    }

    public void clickUpdateByName(View view) {
        String name = etName.getText().toString();

        db.execSQL("UPDATE "+tableName+" SET age=30, email='aa@aa' WHERE name=?",new String[]{name});

    }

    public void clickDeleteByName(View view) {

        String name = etName.getText().toString();
        db.execSQL("DELETE FROM "+ tableName+ " WHERE name=?", new String[]{name});
    }
}


