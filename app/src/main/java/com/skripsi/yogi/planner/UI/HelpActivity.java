package com.skripsi.yogi.planner.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.skripsi.yogi.planner.R;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    public void onViewIntroduction(View view) {
        startActivity(new Intent(this, IntroductionActivity.class));
    }

    public void onViewDeveloper(View view) {
        startActivity(new Intent(this, DeveloperActivity.class));
    }

    public void onViewAppInfo(View view) {
        startActivity(new Intent(this, AppInfoActivity.class));
    }
}
