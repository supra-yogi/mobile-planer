package com.skripsi.yogi.planner.UI;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.skripsi.yogi.planner.Common.Session;
import com.skripsi.yogi.planner.DAL.URLs;
import com.skripsi.yogi.planner.R;

import java.net.HttpURLConnection;
import java.net.URL;

public class WelcomeActivity extends AppCompatActivity {

    private ImageView icon;
    private TextView txtWelcome;
    private TextView txtDesc1, txtDesc2;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        session = new Session(this);

        //Animation
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.transition);
        icon = (ImageView) findViewById(R.id.icon);
        txtWelcome = (TextView) findViewById(R.id.txtWelcome);
        txtDesc1 = (TextView) findViewById(R.id.txtDesc1);
        txtDesc2 = (TextView) findViewById(R.id.txtDesc2);
        icon.startAnimation(anim);
        txtWelcome.startAnimation(anim);
        txtDesc1.startAnimation(anim);
        txtDesc2.startAnimation(anim);

        final Intent noConnection = new Intent(this, NoConnectionActivity.class);
        //Check Connection
        Thread connection = new Thread(new Runnable() {
            @Override
            public void run() {
                Thread timer = new Thread() {
                    public void run() {
                        try {
                            sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            if (isConnected()) {
                                if (session.getId() != 0) {
                                    startActivity(new Intent(getApplicationContext(), MainMenuActivity.class));
                                    finish();
                                } else {
                                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                    finish();
                                }
                            } else {
                                startActivity(new Intent(getApplicationContext(), NoConnectionActivity.class));
                                finish();
                            }
                        }
                    }
                };
                timer.start();
            }
        });
        connection.start();
    }

    public boolean isConnected() {
        try {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();

            if (netInfo != null && netInfo.isConnected()) {
                // Network is available but check if we can get access from the url.
                URL url = new URL(URLs.URL_SERVER);
                HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(3000); // Timeout 3 seconds.
                urlc.connect();

                if (urlc.getResponseCode() == 200) { // Successful response.
                    return true;
                } else {
                    Snackbar.make(findViewById(R.id.txtDesc2), "Url is down", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return false;
                }
            } else {
                Snackbar.make(findViewById(R.id.txtDesc2), "No Internet Connection", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
