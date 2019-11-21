package com.skripsi.yogi.planner.UI;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.skripsi.yogi.planner.Common.ResponseCallBack;
import com.skripsi.yogi.planner.Common.Session;
import com.skripsi.yogi.planner.DAL.PlanningAPI;
import com.skripsi.yogi.planner.DAL.UserAPI;
import com.skripsi.yogi.planner.Domain.Plannings.Planning;
import com.skripsi.yogi.planner.R;
import com.skripsi.yogi.planner.UI.adapter.PlanningAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;

public class MainMenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerView;
    private PlanningAdapter adapter;
    private PlanningAPI planningRepo;
    private UserAPI userRepo;
    private Session session;
    private NavigationView navigationView;

    private static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), PlanningActivity.class));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setChecked(true);
        navigationView.setNavigationItemSelectedListener(this);

        activity = this;
        session = new Session(this);
        planningRepo = new PlanningAPI(this);
        userRepo = new UserAPI(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PlanningAdapter(this, new ArrayList<Planning>());
        recyclerView.setAdapter(adapter);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        userRepo.getById(new ResponseCallBack() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    progressDialog.hide();

                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);

                        TextView username = (TextView) findViewById(R.id.username);
                        TextView email = (TextView) findViewById(R.id.email);

                        username.setText(jsonObject.getString("username"));
                        email.setText(jsonObject.getString("email"));
                    }

                    // Get data planning by user
                    planningRepo.getPlanningByUserId(new ResponseCallBack() {
                        @Override
                        public void onResponse(JSONArray response) {
                        }

                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    Planning planning = new Planning();
                                    planning.setId(jsonObject.getInt("id"));
                                    planning.setGoalName(jsonObject.getString("goalName"));
                                    planning.setTimePeriod(jsonObject.getInt("jangkaWaktu"));
                                    planning.setCurrentCost(new BigDecimal(jsonObject.getString("currentCost")));

                                    adapter.add(planning);
                                }
                            } catch (JSONException e) {
                                Log.d("Error", e.getMessage());
                            }
                        }

                        @Override
                        public void onError(String error) {
                            Snackbar.make(findViewById(R.id.email), "Error: " + error, Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    }, session.getId());

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

                Snackbar.make(findViewById(R.id.email), "Error: " + error, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }, session.getId());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            startActivity(new Intent(this, MainMenuActivity.class));
            finish();
        } else if (id == R.id.nav_listPriority) {
            startActivity(new Intent(this, ListPriorityPlanningActivity.class));
        } else if (id == R.id.nav_account) {
            startActivity(new Intent(this, AccountActivity.class));
        } else if (id == R.id.nav_batasan) {
            startActivity(new Intent(this, BatasanActivity.class));
        } else if (id == R.id.nav_help) {
            startActivity(new Intent(this, HelpActivity.class));
        } else if (id == R.id.nav_logout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure want to exit")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            session.logOut();
                            finish();

                        }
                    }).setNegativeButton("No", null);

            AlertDialog alert = builder.create();
            alert.show();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static Activity getInstance() {
        return activity;
    }
}
