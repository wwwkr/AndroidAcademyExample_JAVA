package com.mrhi2018.ex86firebasedb;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText et;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et= findViewById(R.id.et);
        tv= findViewById(R.id.tv);
    }

    public void clickSend(View view) {

        //EditText에 있는 글씨 얻어오기
        String text= et.getText().toString();


        //Firebase실시간 데이터베이스에 저장...

        //1. Firebase 실시간 데이터베이스 관리 객체 얻어오기
        FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();

        //2. 저장시킬 노드 참조객체 가져오기
        DatabaseReference rootRef= firebaseDatabase.getReference();//최상위 노드

        //각 노드에 값 대입해보기...

//        //1) 별도의 키[key : 식별자]없이 값[value]만 저장하기///
//        rootRef.setValue(text);
//
//        //저장된 ㄱ밧 읽어오기..
//        //별도의 읽어오기 버튼 없이..[실시간 데이터베이스]인 만큼
//        //데이터의 변경이 발생하면 이에 반응하는 리스너를 통해
//        //실시간으로 데이터를 읽어옴..!!!!!
//
//        //값이 변경되는 노드참조객체에 리스너를 추가
//        rootRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                //값이 변경될때 마다 실행되는 메소드
//
//                //파라미터로 전달된 DataSnapshot객체를 통해 데이터들을 가져올 수 있음.
//               //String data= (String)dataSnapshot.getValue();
//                //형변환이 귀찮으면 getValeu()메소드의 매개변수로 자료형.class 지정
//                String data= dataSnapshot.getValue(String.class);
//                tv.setText(data);
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                //값 변경 중 에러발생시 실행되는 메소드
//
//            }
//        });

//        //2) 위 1)번 방법은 값이 갱신됨. 누적하여 추가하고 싶다면...////////////
//        DatabaseReference childRef= rootRef.push();
//        childRef.setValue(text);
//
//        //읽어오기
//        rootRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                //최상위 노드에 바로 값이 있는 것이 아니라..
//                //자식노드들이 여러개 있으므로 String 벨류가 아님..
//                //String text= dataSnapshot.getValue(String.class);
//
//                StringBuffer buffer= new StringBuffer();
//                //각 자식노들에 foreach문을 이용해서 접근하여 값을 읽어오기
//                for( DataSnapshot snapshot : dataSnapshot.getChildren()){
//                    String text= snapshot.getValue(String.class);
//                    buffer.append(text+"\n");
//                }
//
//                tv.setText(buffer.toString());
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
        ////////////////////////////////////////////////////////////////

        //3) 자식노드들의 식별 키값을 부여하면서 데이터 관리하기////

        //rootRef아래에 'data'라는 이름의 자식노드 하나 추가
//        DatabaseReference dataRef= rootRef.child("data");//최상위 노드아래에 'data라는 이름의 키값에 접근(없으면 만들고, 있으면 그냥 참조)
//        //dataRef.setValue("aaa");//값이 갱신...
//        dataRef.push().setValue(text);
//
//        //rootRef가 아니라 dataRef에 리스너 추가
//        dataRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                //String s= dataSnapshot.getValue(String.class);
//                //값들이 여러개 이므로..foreach문 사용.
//                StringBuffer buffer= new StringBuffer();
//                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
//                    String str= snapshot.getValue(String.class);
//                    buffer.append(str+"\n");
//                }
//
//                tv.setText(buffer.toString());
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


        //4)하나의 노드에 벨류가 여러개되도록..
//        DatabaseReference memberRef= rootRef.child("member");
//        DatabaseReference itemRef= memberRef.push(); //"member"노드 아래에 임이의 식별자를 가진 자식노드가 생김
//        itemRef.child("name").setValue(text);
//        itemRef.child("age").setValue(20);
//
//
//        //'member'라는 이름의 노드(node)에 리스너 추가하기
//        memberRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                StringBuffer buffer=new StringBuffer();
//
//                //push()에 의한 자식노드들 하나씩 접근하기
//                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
//                    //각 자식노드들 마다 'name', 'age'라는 자식노드가 또 있으므로..
//                    for( DataSnapshot snap : snapshot.getChildren()){
//                        buffer.append( snap.getKey()+" : " + snap.getValue().toString()+"\n");
//                    }
//                    buffer.append("\n");
//                }
//
//                tv.setText(buffer.toString());
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        //5) 나만의 클래스[VO: value object]객체를 만들어서 한번에 멤버값들 저장하기
        // 멤버변수명으로 자동으로 자식노드들이 생성됨!!
        String name= et.getText().toString();
        int age= 25;
        String address= "SEOUL";

        //저장할 값들을 하나의 객체로 생성하기!!
        PersonVO person= new PersonVO(name, age, address);


        //'persons'라는 이름의 자식노드참조객체 생성 or 참조
        DatabaseReference personsRef= rootRef.child("persons");
        personsRef.push().setValue(person);

        //'persons'노드의 벨류가 변경되는 것을 듣는 리스너 추가!!
        personsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //persons노드아래에 자식노드들이 여러개이므로..
                StringBuffer buffer= new StringBuffer();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    PersonVO person= snapshot.getValue(PersonVO.class);
                    String name= person.name;
                    int age= person.age;
                    String address= person.address;

                    buffer.append(name+" , "+ age +" , "+ address+"\n");
                }

                tv.setText( buffer.toString() );

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        et.setText("");













    }





















}
