package com.skripsi.yogi.planner.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.skripsi.yogi.planner.R;

public class NoConnectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_connection);
    }

    public void onReload(View view) {
        startActivity(new Intent(this, WelcomeActivity.class));
        onBackPressed();
    }
}
