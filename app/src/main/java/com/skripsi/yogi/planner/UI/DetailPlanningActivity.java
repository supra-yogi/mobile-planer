package com.skripsi.yogi.planner.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.skripsi.yogi.planner.Common.CurrencyFormat;
import com.skripsi.yogi.planner.Common.ResponseCallBack;
import com.skripsi.yogi.planner.Common.Session;
import com.skripsi.yogi.planner.DAL.PlanningAPI;
import com.skripsi.yogi.planner.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailPlanningActivity extends AppCompatActivity {

    private Session session;
    private PlanningAPI repository;
    private int id;
    private TextView txtGoalName, txtJangkaWaktu, txtJangkaWaktu2, txtBiayaAdmin, txtPajakBunga, txtCurrentCost, txtFutureCost,
            txtAlreadyInfest, txtInflasi, txtReturnInterest, txtRequiredRate, txtMonthlyInvest, txtLumpsum, txtCreatedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_planning);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        session = new Session(this);
        repository = new PlanningAPI(this);

        txtGoalName = (TextView) findViewById(R.id.goalName);
        txtJangkaWaktu = (TextView) findViewById(R.id.jangkaWaktu);
        txtJangkaWaktu2 = (TextView) findViewById(R.id.jangkaWaktu2);
        txtBiayaAdmin = (TextView) findViewById(R.id.biayaAdmin);
        txtPajakBunga = (TextView) findViewById(R.id.pajakBunga);
        txtCurrentCost = (TextView) findViewById(R.id.currentCost);
        txtFutureCost = (TextView) findViewById(R.id.futureCost);
        txtAlreadyInfest = (TextView) findViewById(R.id.alreadyInvest);
        txtInflasi = (TextView) findViewById(R.id.inflationRate);
        txtReturnInterest = (TextView) findViewById(R.id.interestRate);
        txtRequiredRate = (TextView) findViewById(R.id.requiredRate);
        txtMonthlyInvest = (TextView) findViewById(R.id.monthlyInvest);
        txtLumpsum = (TextView) findViewById(R.id.lumpsum);
        txtCreatedDate = (TextView) findViewById(R.id.createdDate);

        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("id");

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);
        repository.getById(new ResponseCallBack() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    progressDialog.hide();

                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);

                        txtCreatedDate.setText(jsonObject.getString("created_date"));
                        txtGoalName.setText(jsonObject.getString("goalName"));
                        txtJangkaWaktu.setText(jsonObject.getString("jangkaWaktu"));
                        txtJangkaWaktu2.setText(jsonObject.getString("jangkaWaktu"));
                        txtBiayaAdmin.setText(jsonObject.getString("biayaAdmin"));
                        CurrencyFormat.onFillTextView(txtBiayaAdmin);
                        txtPajakBunga.setText(jsonObject.getString("pajakBunga"));
                        txtCurrentCost.setText(jsonObject.getString("currentCost"));
                        CurrencyFormat.onFillTextView(txtCurrentCost);
                        txtFutureCost.setText(jsonObject.getString("futureCost"));
                        CurrencyFormat.onFillTextView(txtFutureCost);
                        txtAlreadyInfest.setText(jsonObject.getString("alreadyInvest"));
                        CurrencyFormat.onFillTextView(txtAlreadyInfest);
                        txtInflasi.setText(jsonObject.getString("inflationRate"));
                        txtReturnInterest.setText(jsonObject.getString("interestRate"));
                        txtRequiredRate.setText(jsonObject.getString("requiredRate"));
                        txtMonthlyInvest.setText(jsonObject.getString("monthlyInvest"));
                        CurrencyFormat.onFillTextView(txtMonthlyInvest);
                        txtLumpsum.setText(jsonObject.getString("lumpsum"));
                        CurrencyFormat.onFillTextView(txtLumpsum);
                    }
                } catch (JSONException e) {
                    Log.d("Error", e.getMessage());
                }
            }

            @Override
            public void onResponse(String response) {
            }

            @Override
            public void onError(String error) {
                progressDialog.hide();

                Snackbar.make(findViewById(R.id.goalName), "Error: " + error, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }, id);
    }

    public void onShowDetailMonthlyInvest(View view) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        Intent detailMonthlyInvest = new Intent(getApplicationContext(), DetailMonthlyInvestActivity.class);
        detailMonthlyInvest.putExtras(bundle);
        startActivity(detailMonthlyInvest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
