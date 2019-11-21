package com.skripsi.yogi.planner.UI;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.skripsi.yogi.planner.Common.ResponseCallBack;
import com.skripsi.yogi.planner.Common.Session;
import com.skripsi.yogi.planner.DAL.UserAPI;
import com.skripsi.yogi.planner.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private UserAPI repository;
    private Session session;
    private static Activity activity;
    private EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        repository = new UserAPI(this);
        session = new Session(this);
        activity = this;
    }

    @Override
    public void onResume() {
        super.onResume();

        email.setText("");
        password.setText("");
    }

    public void onLogin(View view) {
        final String emailValidate = email.getText().toString().trim();
        final String emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        if (email.getText().toString().equals("")) {
            email.setError("Email is required");
            email.requestFocus();
        } else if (!emailValidate.matches(emailPattern)) {
            email.setError("Invalid email");
            email.requestFocus();
        } else if (password.getText().toString().equals("")) {
            password.setError("Password is required");
            password.requestFocus();
        } else {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.show();
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            repository.login(new ResponseCallBack() {
                @Override
                public void onResponse(JSONArray response) {
                }

                @Override
                public void onResponse(String response) {
                    progressDialog.hide();

                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            session.setId(jsonObject.getInt("id"));
                            session.setEmail(jsonObject.getString("email"));
                        }
                        startActivity(new Intent(getApplicationContext(), MainMenuActivity.class));
                        finish();
                    } catch (JSONException e) {
                        Log.d("Error: ", e.getMessage());
                    }
                }

                @Override
                public void onError(String error) {
                    progressDialog.hide();

                    Snackbar.make(findViewById(R.id.email), "Error: " + error, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }, email.getText().toString(), password.getText().toString());
        }
    }

    public void onRegister(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    public void onForgetPassword(View view) {
        startActivity(new Intent(this, ForgetPasswordActivity.class));
    }

    public static Activity getInstance() {
        return activity;
    }
}
