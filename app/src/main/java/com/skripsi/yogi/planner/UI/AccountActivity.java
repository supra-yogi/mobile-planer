package com.skripsi.yogi.planner.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.skripsi.yogi.planner.Common.ResponseCallBack;
import com.skripsi.yogi.planner.Common.Session;
import com.skripsi.yogi.planner.DAL.UserAPI;
import com.skripsi.yogi.planner.Domain.Plannings.Planning;
import com.skripsi.yogi.planner.Domain.Users.User;
import com.skripsi.yogi.planner.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

public class AccountActivity extends AppCompatActivity {

    private EditText username, email, currentPassword, newPassword;
    private CheckBox changePassword;
    private boolean isChangePassword;
    private UserAPI repository;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        repository = new UserAPI(this);
        session = new Session(this);

        username = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email);
        currentPassword = (EditText) findViewById(R.id.currentPassword);
        newPassword = (EditText) findViewById(R.id.newPassword);
        changePassword = (CheckBox) findViewById(R.id.changePassword);
        isChangePassword = false;

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        repository.getById(new ResponseCallBack() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    progressDialog.hide();

                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);

                        username.setText(jsonObject.getString("username"));
                        email.setText(jsonObject.getString("email"));
                    }
                } catch (JSONException e) {
                    Log.d("Error: ", e.getMessage());
                }
            }

            @Override
            public void onResponse(String response) {
            }

            @Override
            public void onError(String error) {
                progressDialog.hide();

                Snackbar.make(findViewById(R.id.email), "Error: " + error, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }, session.getId());

        changePassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                currentPassword.setEnabled(isChecked);
                newPassword.setEnabled(isChecked);
                isChangePassword = isChecked;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    public void onSave(View view) {
        final String emailValidate = email.getText().toString().trim();
        final String emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        if (username.getText().toString().equals("")) {
            username.setError("This field is required");
            username.requestFocus();
        } else if (email.getText().toString().equals("")) {
            email.setError("This field is required");
            email.requestFocus();
        } else if (!emailValidate.matches(emailPattern)) {
            email.setError("Invalid email");
            email.requestFocus();
        } else {
            final User user = new User();
            user.setId(session.getId());
            user.setUsername(username.getText().toString());
            user.setEmail(email.getText().toString());
            user.setPassword(newPassword.getText().toString());
            user.setChangePassword(isChangePassword);

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.show();
            progressDialog.setMessage("Please wait");
            progressDialog.setCancelable(false);
            if (isChangePassword) {
                if (currentPassword.getText().toString().equals("")) {
                    currentPassword.setError("This field is required");
                } else if (newPassword.getText().toString().equals("")) {
                    newPassword.setError("This field is required");
                } else {
                    repository.checkPassword(new ResponseCallBack() {
                        @Override
                        public void onResponse(JSONArray response) {
                        }

                        @Override
                        public void onResponse(String response) {
                            try {
                                progressDialog.hide();

                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.getBoolean("isEqual")) {
                                    save(user);
                                } else {
                                    Snackbar.make(findViewById(R.id.email), "Current password doesn't match", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }
                            } catch (JSONException e) {
                                Log.d("Error: ", e.getMessage());
                            }
                        }

                        @Override
                        public void onError(String error) {
                            progressDialog.hide();

                            Snackbar.make(getWindow().getDecorView().getRootView(), "Error: " + error, Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    }, session.getId(), currentPassword.getText().toString());
                }
            } else {
                progressDialog.hide();

                save(user);
            }
        }
    }

    public void save(User user) {

        repository.save(new ResponseCallBack() {
            @Override
            public void onResponse(JSONArray response) {
            }

            @Override
            public void onResponse(String response) {
                startActivity(new Intent(getApplicationContext(), MainMenuActivity.class));
                MainMenuActivity.getInstance().finish();
                finish();
            }

            @Override
            public void onError(String error) {
                Snackbar.make(findViewById(R.id.email), "Error: " + error, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }, user);
    }
}
