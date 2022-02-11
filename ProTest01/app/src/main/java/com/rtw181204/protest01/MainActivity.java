package com.rtw181204.protest01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText et_id, et_pass, et_passChk, et_nick;

    String sId, sPass, sPassChk, sNick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        et_id =findViewById(R.id.et_id);
        et_pass = findViewById(R.id.et_password);
        et_passChk = findViewById(R.id.et_passwordChk);
        et_nick = findViewById(R.id.et_nickname);






    }

    public void clickMem(View view) {

        sId = et_id.toString();
        sPass =et_pass.toString();
        sPassChk = et_passChk.toString();
        sNick = et_nick.toString();

        if(!sPass.equals(sPassChk)){
            Toast.makeText(this, "비밀번호 불일치", Toast.LENGTH_SHORT).show();
            return;
        }
        else{

        }
    }
}
