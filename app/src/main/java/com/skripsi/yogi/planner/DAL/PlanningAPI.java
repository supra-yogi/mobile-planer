package com.skripsi.yogi.planner.DAL;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.skripsi.yogi.planner.Common.RequestHandler;
import com.skripsi.yogi.planner.Common.ResponseCallBack;
import com.skripsi.yogi.planner.Domain.Batasans.Batasan;
import com.skripsi.yogi.planner.Domain.Batasans.IBatasanRepo;
import com.skripsi.yogi.planner.Domain.Plannings.IPlanningRepo;
import com.skripsi.yogi.planner.Domain.Plannings.Planning;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yogi on 05/11/2017.
 */

public class PlanningAPI extends BaseAPI<Planning> implements IPlanningRepo {

    public PlanningAPI(Context context) {
        super(context);
    }

    @Override
    public void save(final ResponseCallBack responseCallBack, final Planning entity) {
        if (entity.getId() == 0) {
            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            responseCallBack.onResponse(response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            responseCallBack.onError(errorResponseHandler(error));
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("id", String.valueOf(entity.getId()));
                    params.put("userId", String.valueOf(entity.getUser().getId()));
                    params.put("goalName", entity.getGoalName());
                    params.put("timePeriod", String.valueOf(entity.getTimePeriod()));
                    params.put("biayaAdmin", String.valueOf(entity.getBiayaAdmin()));
                    params.put("pajakBunga", String.valueOf(entity.getPajakBunga()));
                    params.put("currentCost", entity.getCurrentCost().toString());
                    params.put("alreadyInvest", entity.getAlreadyInvest().toString());
                    params.put("requiredRate", String.valueOf(entity.getRequiredRate()));
                    params.put("inflationRate", entity.getInflationRate().toString());
                    params.put("interestRate", entity.getInterestRate().toString());

                    return params;
                }
            };
            RequestHandler.getInstance(context).addToRequestQueue(postRequest);
        } else {
            StringRequest putRequest = new StringRequest(Request.Method.PUT, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            responseCallBack.onResponse(response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            responseCallBack.onError(errorResponseHandler(error));
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("id", String.valueOf(entity.getId()));
                    params.put("goalName", entity.getGoalName());
                    params.put("timePeriod", String.valueOf(entity.getTimePeriod()));
                    params.put("biayaAdmin", String.valueOf(entity.getBiayaAdmin()));
                    params.put("pajakBunga", String.valueOf(entity.getPajakBunga()));
                    params.put("currentCost", entity.getCurrentCost().toString());
                    params.put("alreadyInvest", entity.getAlreadyInvest().toString());
                    params.put("requiredRate", String.valueOf(entity.getRequiredRate()));
                    params.put("inflationRate", entity.getInflationRate().toString());
                    params.put("interestRate", entity.getInterestRate().toString());

                    return params;
                }
            };
            RequestHandler.getInstance(context).addToRequestQueue(putRequest);
        }
    }

    @Override
    public void getPlanningByUserId(final ResponseCallBack responseCallBack, final int userId) {
        StringRequest postRequest = new StringRequest(Request.Method.POST, url + "getPlanningByUserId",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        responseCallBack.onResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        responseCallBack.onError(errorResponseHandler(error));
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("userId", String.valueOf(userId));

                return params;
            }
        };
        RequestHandler.getInstance(context).addToRequestQueue(postRequest);
    }

    @Override
    public void getRincianTabungan(final ResponseCallBack responseCallBack, final int planningId) {
        StringRequest postRequest = new StringRequest(Request.Method.POST, url + "getRincianTabungan",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        responseCallBack.onResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        responseCallBack.onError(errorResponseHandler(error));
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(planningId));

                return params;
            }
        };
        RequestHandler.getInstance(context).addToRequestQueue(postRequest);
    }

    @Override
    public void getPriorityPlanningByUserId(final ResponseCallBack responseCallBack, final int userId) {
        StringRequest postRequest = new StringRequest(Request.Method.POST, url + "getPriorityPlanningByUserId",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        responseCallBack.onResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        responseCallBack.onError(errorResponseHandler(error));
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("userId", String.valueOf(userId));

                return params;
            }
        };
        RequestHandler.getInstance(context).addToRequestQueue(postRequest);
    }

    @Override
    public Planning createNew() {
        return new Planning();
    }

    @Override
    public String getUrl() {
        return URLs.URL_PLANNING;
    }
}
