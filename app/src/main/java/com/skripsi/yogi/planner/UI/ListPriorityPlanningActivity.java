package com.skripsi.yogi.planner.UI;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.skripsi.yogi.planner.Common.ResponseCallBack;
import com.skripsi.yogi.planner.Common.Session;
import com.skripsi.yogi.planner.DAL.PlanningAPI;
import com.skripsi.yogi.planner.Domain.Plannings.PriorityPlanning;
import com.skripsi.yogi.planner.R;
import com.skripsi.yogi.planner.UI.adapter.PriorityPlanningAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;

public class ListPriorityPlanningActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PriorityPlanningAdapter adapter;
    private PlanningAPI planningRepo;
    private Session session;
    private static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_priority_planning);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        activity = this;
        session = new Session(this);
        planningRepo = new PlanningAPI(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PriorityPlanningAdapter(this, new ArrayList<PriorityPlanning>());
        recyclerView.setAdapter(adapter);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);
        planningRepo.getPriorityPlanningByUserId(new ResponseCallBack() {
            @Override
            public void onResponse(JSONArray response) {
            }

            @Override
            public void onResponse(String response) {
                try {
                    progressDialog.hide();

                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        PriorityPlanning priorityPlanning = new PriorityPlanning();
                        priorityPlanning.setId(jsonObject.getInt("id"));
                        priorityPlanning.setGoalName(jsonObject.getString("goalName"));
                        priorityPlanning.setPriority(jsonObject.getInt("num"));
                        priorityPlanning.setRecommendation(BigDecimal.valueOf(jsonObject.getDouble("priority")));

                        adapter.add(priorityPlanning);
                    }
                } catch (JSONException e) {
                    Log.d("Error: ", e.getMessage());
                }
            }

            @Override
            public void onError(String error) {
                progressDialog.hide();

                Snackbar.make(findViewById(R.id.recyclerView), "Error: " + error, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }, session.getId());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    public static Activity getInstance() {
        return activity;
    }
}
