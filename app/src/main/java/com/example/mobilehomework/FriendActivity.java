package com.example.mobilehomework;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class FriendActivity extends AppCompatActivity {

   public static GroupListFragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        fragment = new GroupListFragment();

        getFragmentManager().beginTransaction()
                .replace(R.id.fragContainer, fragment).commit();
    }
}
