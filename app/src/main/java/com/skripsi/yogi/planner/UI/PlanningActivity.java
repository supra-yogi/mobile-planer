package com.skripsi.yogi.planner.UI;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.skripsi.yogi.planner.Common.CurrencyFormat;
import com.skripsi.yogi.planner.Common.ResponseCallBack;
import com.skripsi.yogi.planner.Common.Session;
import com.skripsi.yogi.planner.DAL.PlanningAPI;
import com.skripsi.yogi.planner.Domain.Plannings.Planning;
import com.skripsi.yogi.planner.Domain.Users.User;
import com.skripsi.yogi.planner.R;

import org.json.JSONArray;

import java.math.BigDecimal;

public class PlanningActivity extends AppCompatActivity {

    private Session session;
    private PlanningAPI repository;
    private EditText txtGoalName, txtTimePeriod, txtCurrentCost, txtBiayaAdmin,
            txtPajakBunga, txtAlreadyInfest, txtInflasi, txtReturnInterest, txtRequiredRate;
    private static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planning);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        repository = new PlanningAPI(getApplicationContext());
        activity = this;
        session = new Session(this);

        txtGoalName = (EditText) findViewById(R.id.goalName);
        txtTimePeriod = (EditText) findViewById(R.id.timePeriod);
        txtBiayaAdmin = (EditText) findViewById(R.id.biayaAdmin);
        txtAlreadyInfest = (EditText) findViewById(R.id.alreadyInvest);
        txtCurrentCost = (EditText) findViewById(R.id.currentCost);
        txtInflasi = (EditText) findViewById(R.id.inflationRate);
        txtReturnInterest = (EditText) findViewById(R.id.interestRate);
        txtRequiredRate = (EditText) findViewById(R.id.requiredRate);
        txtPajakBunga = (EditText) findViewById(R.id.pajakBunga);

        //CurrencyFormat
        txtCurrentCost.addTextChangedListener(CurrencyFormat.onTextChangedListener(txtCurrentCost));
        txtBiayaAdmin.addTextChangedListener(CurrencyFormat.onTextChangedListener(txtBiayaAdmin));
        txtAlreadyInfest.addTextChangedListener(CurrencyFormat.onTextChangedListener(txtAlreadyInfest));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    public void onSave(View view) {
        if (txtGoalName.getText().toString().equals("")) {
            txtGoalName.setError("This field is required");
            txtGoalName.requestFocus();
        } else if (txtTimePeriod.getText().toString().equals("")) {
            txtTimePeriod.setError("This field is required");
            txtTimePeriod.requestFocus();
        } else if (Integer.parseInt(txtTimePeriod.getText().toString()) <= 0) {
            txtTimePeriod.setError("Harus lebih besar dari 0");
            txtTimePeriod.requestFocus();
        } else if (txtCurrentCost.getText().toString().equals("")) {
            txtCurrentCost.setError("This field is required");
            txtCurrentCost.requestFocus();
        } else if (CurrencyFormat.parseDouble(txtCurrentCost.getText().toString()) <= 1000) {
            txtCurrentCost.setError("Harus lebih besar dari 1000");
            txtCurrentCost.requestFocus();
        } else if (txtInflasi.getText().toString().equals("")) {
            txtInflasi.setError("This field is required");
            txtInflasi.requestFocus();
        } else if (txtRequiredRate.getText().toString().equals("")) {
            txtRequiredRate.setError("This field is required");
            txtRequiredRate.requestFocus();
        } else if (Integer.parseInt(txtRequiredRate.getText().toString()) <= 0 ||
                Integer.parseInt(txtRequiredRate.getText().toString()) > 10) {
            txtRequiredRate.setError("Harus lebih besar dari 0 atau kurang dari 11");
            txtRequiredRate.requestFocus();
        } else if (txtAlreadyInfest.getText().toString().equals("")) {
            txtAlreadyInfest.setError("This field is required");
            txtAlreadyInfest.requestFocus();
        } else if (CurrencyFormat.parseDouble(txtAlreadyInfest.getText().toString()) >= CurrencyFormat.parseDouble(txtCurrentCost.getText().toString())) {
            txtAlreadyInfest.setError("Tidak boleh melebihi dana yang ingin di capai");
            txtAlreadyInfest.requestFocus();
        } else if (txtBiayaAdmin.getText().toString().equals("")) {
            txtBiayaAdmin.setError("This field is required");
            txtBiayaAdmin.requestFocus();
        } else if (CurrencyFormat.parseDouble(txtBiayaAdmin.getText().toString()) < 1000) {
            txtBiayaAdmin.setError("Harus lebih besar dari 1000");
            txtBiayaAdmin.requestFocus();
        } else if (txtReturnInterest.getText().toString().equals("")) {
            txtReturnInterest.setError("This field is required");
            txtReturnInterest.requestFocus();
        } else {
            User user = new User();
            user.setId(session.getId());
            Planning planning = new Planning();
            planning.setUser(user);
            planning.setGoalName(txtGoalName.getText().toString());
            planning.setTimePeriod(Integer.valueOf(txtTimePeriod.getText().toString()));
            planning.setAlreadyInvest(BigDecimal.valueOf(CurrencyFormat.parseDouble(txtAlreadyInfest.getText().toString())));
            planning.setCurrentCost(BigDecimal.valueOf(CurrencyFormat.parseDouble(txtCurrentCost.getText().toString())));
            planning.setInflationRate(BigDecimal.valueOf(Double.valueOf(txtInflasi.getText().toString())));
            planning.setInterestRate(BigDecimal.valueOf(Double.valueOf(txtReturnInterest.getText().toString())));
            planning.setRequiredRate(Integer.valueOf(txtRequiredRate.getText().toString()));
            planning.setPajakBunga(BigDecimal.valueOf(Double.valueOf(txtPajakBunga.getText().toString())));
            planning.setBiayaAdmin(BigDecimal.valueOf(CurrencyFormat.parseDouble(txtBiayaAdmin.getText().toString())));

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.show();
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            repository.save(new ResponseCallBack() {
                @Override
                public void onResponse(JSONArray response) {
                }

                @Override
                public void onResponse(String response) {
                    progressDialog.hide();

                    startActivity(new Intent(getApplicationContext(), MainMenuActivity.class));
                    MainMenuActivity.getInstance().finish();
                    finish();
                }

                @Override
                public void onError(String error) {
                    progressDialog.hide();

                    Snackbar.make(findViewById(R.id.goalName), "Error: " + error, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }, planning);
        }
    }

    public static Activity getInstance() {
        return activity;
    }
}
