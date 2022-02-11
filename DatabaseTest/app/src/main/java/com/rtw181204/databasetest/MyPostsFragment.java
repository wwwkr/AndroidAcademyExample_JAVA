package com.rtw181204.databasetest;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class MyPostsFragment extends PostListFragment {

    public MyPostsFragment(){}

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        return databaseReference.child("user-posts").child(getUid());
    }
}
