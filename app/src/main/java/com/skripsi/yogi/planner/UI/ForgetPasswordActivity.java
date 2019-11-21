package com.skripsi.yogi.planner.UI;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.skripsi.yogi.planner.Common.ResponseCallBack;
import com.skripsi.yogi.planner.DAL.UserAPI;
import com.skripsi.yogi.planner.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ForgetPasswordActivity extends AppCompatActivity {

    private UserAPI repository;
    private EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        repository = new UserAPI(this);
        email = (EditText) findViewById(R.id.email);
    }

    public void onBack(View view) {
        onBackPressed();
    }

    public void onResetPassword(View view) {
        final String emailValidate = email.getText().toString().trim();
        final String emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        if (email.getText().toString().equals("")) {
            email.setError("Email is required");
            email.requestFocus();
        } else if (!emailValidate.matches(emailPattern)) {
            email.setError("Invalid email");
            email.requestFocus();
        } else {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.show();
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            repository.resetPassword(new ResponseCallBack() {
                @Override
                public void onResponse(JSONArray response) {
                }

                @Override
                public void onResponse(String response) {
                    try {
                        progressDialog.hide();

                        JSONObject jsonObject = new JSONObject(response);

                        Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(String error) {
                    progressDialog.hide();

                    Snackbar.make(findViewById(R.id.email), "Error: " + error, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }, email.getText().toString());
        }
    }
}
