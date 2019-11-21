package com.skripsi.yogi.planner.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.skripsi.yogi.planner.Common.ResponseCallBack;
import com.skripsi.yogi.planner.Common.Session;
import com.skripsi.yogi.planner.DAL.UserAPI;
import com.skripsi.yogi.planner.Domain.Users.User;
import com.skripsi.yogi.planner.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    private UserAPI repository;
    private Session session;
    private EditText username, email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        repository = new UserAPI(this);
        session = new Session(this);

        username = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
    }

    public void onRegister(View view) {
        final String emailValidate = email.getText().toString().trim();
        final String emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        if (username.getText().toString().equals("")) {
            username.setError("Username is required");
            username.requestFocus();
        } else if (email.getText().toString().equals("")) {
            email.setError("Email is required");
            email.requestFocus();
        } else if (!emailValidate.matches(emailPattern)) {
            email.setError("Invalid email");
            email.requestFocus();
        } else if (password.getText().toString().equals("")) {
            password.setError("Password is required");
            password.requestFocus();
        } else {
            User user = new User(username.getText().toString(), email.getText().toString(), password.getText().toString());

            final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
            progressDialog.show();
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            repository.save(new ResponseCallBack() {
                @Override
                public void onResponse(JSONArray response) {
                }

                @Override
                public void onResponse(String response) {
                    try {
                        progressDialog.hide();

                        JSONObject jsonObject = new JSONObject(response);
                        session.setId(jsonObject.getInt("id"));
                        session.setEmail(jsonObject.getString("email"));

                        startActivity(new Intent(getApplicationContext(), MainMenuActivity.class));
                        LoginActivity.getInstance().finish();
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
            }, user);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        username.setText("");
        email.setText("");
        password.setText("");
    }

    public void onBack(View view) {
        onBackPressed();
    }
}
