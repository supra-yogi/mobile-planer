package com.skripsi.yogi.planner.UI;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.skripsi.yogi.planner.Common.ResponseCallBack;
import com.skripsi.yogi.planner.Common.Session;
import com.skripsi.yogi.planner.DAL.BatasanAPI;
import com.skripsi.yogi.planner.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BatasanActivity extends AppCompatActivity {

    private TextView waktuCepatFrom, waktuCepatTo, waktuLamaFrom, waktuLamaTo,
            biayaRendahFrom, biayaRendahTo, biayaSedangFrom, biayaSedangTo, biayaTinggiFrom, biayaTinggiTo,
            kebutuhanRendahFrom, kebutuhanRendahTo, kebutuhanTinggiFrom, kebutuhanTinggiTo;

    private BatasanAPI repository;
    private Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batasan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        repository = new BatasanAPI(this);
        session = new Session(this);

        waktuCepatFrom = (TextView) findViewById(R.id.waktuCepatFrom);
        waktuCepatTo = (TextView) findViewById(R.id.waktuCepatTo);
        waktuLamaFrom = (TextView) findViewById(R.id.waktuLamaFrom);
        waktuLamaTo = (TextView) findViewById(R.id.waktuLamaTo);

        biayaRendahFrom = (TextView) findViewById(R.id.biayaRendahFrom);
        biayaRendahTo = (TextView) findViewById(R.id.biayaRendahTo);
        biayaSedangFrom = (TextView) findViewById(R.id.biayaSedangFrom);
        biayaSedangTo = (TextView) findViewById(R.id.biayaSedangTo);
        biayaTinggiFrom = (TextView) findViewById(R.id.biayaTinggiFrom);
        biayaTinggiTo = (TextView) findViewById(R.id.biayaTinggiTo);

        kebutuhanRendahFrom = (TextView) findViewById(R.id.kebutuhanRendahFrom);
        kebutuhanRendahTo = (TextView) findViewById(R.id.kebutuhanRendahTo);
        kebutuhanTinggiFrom = (TextView) findViewById(R.id.kebutuhanTinggiFrom);
        kebutuhanTinggiTo = (TextView) findViewById(R.id.kebutuhanTinggiTo);

        repository.getBatasanByUserId(new ResponseCallBack() {
            @Override
            public void onResponse(JSONArray response) {
            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        waktuCepatFrom.setText(jsonObject.getString("waktuCepatFrom"));
                        waktuCepatTo.setText(jsonObject.getString("waktuCepatTo"));
                        waktuLamaFrom.setText(jsonObject.getString("waktuLamaFrom"));
                        waktuLamaTo.setText(jsonObject.getString("waktuLamaTo"));

                        biayaRendahFrom.setText(jsonObject.getString("biayaRendahFrom"));
                        biayaRendahTo.setText(jsonObject.getString("biayaRendahTo"));
                        biayaSedangFrom.setText(jsonObject.getString("biayaSedangFrom"));
                        biayaSedangTo.setText(jsonObject.getString("biayaSedangTo"));
                        biayaTinggiFrom.setText(jsonObject.getString("biayaTinggiFrom"));
                        biayaTinggiTo.setText(jsonObject.getString("biayaTinggiTo"));

                        kebutuhanRendahFrom.setText(jsonObject.getString("kebutuhanRendahFrom"));
                        kebutuhanRendahTo.setText(jsonObject.getString("kebutuhanRendahTo"));
                        kebutuhanTinggiFrom.setText(jsonObject.getString("kebutuhanTinggiFrom"));
                        kebutuhanTinggiTo.setText(jsonObject.getString("kebutuhanTinggiTo"));
                    }
                } catch (JSONException e) {
                    Log.d("Error: ", e.getMessage());
                }
            }

            @Override
            public void onError(String error) {
                Snackbar.make(findViewById(R.id.kebutuhanTinggiTo), "Error: " + error, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }, session.getId());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
