package com.rtw181204.databasetest;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class NewPostActivity extends BaseActivity {

    private static final String TAG = "NewPostActivity";
    private static final String REQUIRED = "Required";

    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]

    private EditText mTitleField;
    private EditText mBodyField;
    private FloatingActionButton mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mTitleField = findViewById(R.id.fieldTitle);
        mBodyField = findViewById(R.id.fieldBody);
        mSubmitButton = findViewById(R.id.fabSubmitPost);
    }

    private void submitPost(){
        final String title = mTitleField.getText().toString();
        final String body = mBodyField.getText().toString();

        if(TextUtils.isEmpty(title)){
            mTitleField.setError(REQUIRED);
            return;
        }

        if (TextUtils.isEmpty(body)) {
            mBodyField.setError(REQUIRED);
            return;
        }

        setEditingEnabled(false);
        Toast.makeText(this, "Posting...", Toast.LENGTH_SHORT).show();

        final String userId = getUid();

        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                if(user == null){
                    Toast.makeText(NewPostActivity.this,
                            "Error: could not fetch user.",Toast.LENGTH_SHORT).show();
                }
                else{
                    writeNewPost(userId, user.username, title, body);
                }

                setEditingEnabled(true);
                finish();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                setEditingEnabled(true);
            }
        });



    }

    private void setEditingEnabled(boolean enabled){
        mTitleField.setEnabled(enabled);
        mBodyField.setEnabled(enabled);

        if(enabled){
            mSubmitButton.show();
        }
        else {
            mSubmitButton.hide();
        }
    }

    private void writeNewPost(String userId, String username, String title, String body) {

        String key = mDatabase.child("posts").push().getKey();
        Post post = new Post(userId, username, title, body);
        Map<String, Object> postValues = post.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/posts/"+key, postValues);
        childUpdates.put("/user-posts/"+userId +"/" + key, postValues);

        mDatabase.updateChildren(childUpdates);
    }

    public void clickSubmitPost(View view) {
        submitPost();
    }
}
