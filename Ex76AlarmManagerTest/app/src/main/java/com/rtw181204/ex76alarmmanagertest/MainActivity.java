package com.rtw181204.ex76alarmmanagertest;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

        //1. set() : 한번만 수행되는 알람 예약
        //2. setRepeating() : 반복 알람 예약
    }

    public void clickBtn(View view) {
        //10초후에 알람예약

        //알람이 울릴 때 실행할 Intent를 PendingIntent로 생성
        Intent intent = new Intent(this, AlarmActivity.class);
        //보류중인(Pending) 인텐트로 변환
        PendingIntent pendingIntent = PendingIntent.getActivity(this,10,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        //api버전 별로 설정하는 메소드가 바뀌었음
        //마시멜로우버전부터 Doz(낮잠)모드가 되서
        //백그라운드에 오래 있으면(1-2시간) 자동 프로세스가 죽음
        //심지어 알람매니저도 같이 잠자버림
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+10000,pendingIntent);


        }else if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            alarmManager.setExact(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+10000,pendingIntent);

        }else {
            alarmManager.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+10000,pendingIntent);
        }


    }

    public void clickBtn2(View view) {

        //반복알람설정
        //setRepeating() api19부터 없어짐
        //이를 구현하려면 새로운 방식 사용

        //반복알람시에 실행할 컴포넌트 PendingIntent(BroadcastReceiver)
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,20,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        //알람예약(반복알람)
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+10000,pendingIntent);

        }else if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            alarmManager.setExact(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+10000,pendingIntent);

        }else{
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+10000,20000,pendingIntent);
        }

    }

    public void clickBtn3(View view) {
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,20,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
    }

    //멤버변수
    int Year, Month, Day;
    int Hour, Min;

    public void clickBtn4(View view) {
        //특정 날짜와 시간에 알람예약

        //날짜 선택다이얼로그 : DatePickerDialog

        GregorianCalendar calendar = new GregorianCalendar();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(this,onDateSetListener,Year, Month, Day );
        dialog.show();

    }

    //날짜선택을 듣는 리스너객체

    DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            //날짜선택 완료하였을때 자동실행
            Year = year;
            Month = month;
            Day = dayOfMonth;


            //Toast.makeText(MainActivity.this, Year+"/"+Month+"/"+Day, Toast.LENGTH_SHORT).show();

            //시간선택 다이얼로그 보이기
            GregorianCalendar calendar = new GregorianCalendar();
            Hour = calendar.get(Calendar.HOUR_OF_DAY);
            Min = calendar.get(Calendar.MINUTE);
            TimePickerDialog dialog = new TimePickerDialog(MainActivity.this, timeSetListener,Hour,month,true);

            dialog.show();
        }
    };

    //시간선택 리스너 생성
    TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            Hour = hourOfDay;
            Min = minute;

            //지정된 날짜와 시간으로 알람예약

            Intent intent = new Intent(MainActivity.this,AlarmActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this,30,intent,PendingIntent.FLAG_UPDATE_CURRENT);


            //지정된 시간(Year, ... , Min) -> 밀리세컨드 시간으로 변경

            GregorianCalendar calendar = new GregorianCalendar(Year, Month, Day, Hour, Min);


            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);

            }else if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
                alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);

            }else{
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),20000,pendingIntent);
            }





        }
    };
}
